/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.controller.ItemExpensesDraftController;
import net.purnama.gui.inner.detail.util.DiscountSubtotalPanel;
import net.purnama.model.transactional.draft.ExpensesDraftEntity;
import net.purnama.model.transactional.draft.ItemExpensesDraftEntity;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;

/**
 *
 * @author Purnama
 */
public class ItemExpensesDraftTableModel extends AbstractTableModel {
    
    private final ItemExpensesDraftController itemexpensesdraftController;
    
    private final ExpensesDraftEntity expensesdraft;
    private ArrayList<ItemExpensesDraftEntity> itemexpensesdraftlist;
    private final ArrayList<ItemExpensesDraftEntity> deleteditemexpensesdraftlist;
    
    private final DiscountSubtotalPanel totaltable;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NO"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DESCRIPTION"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_QUANTITY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PRICE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PERCENTAGE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DISCOUNT"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_TOTAL")
    };
    
    public ItemExpensesDraftTableModel(
            ArrayList<ItemExpensesDraftEntity> itemexpensesdraftlist,
            ExpensesDraftEntity expensesdraft, DiscountSubtotalPanel totaltable) {
        super();
        itemexpensesdraftController = new ItemExpensesDraftController();
        deleteditemexpensesdraftlist = new ArrayList<>();
        this.itemexpensesdraftlist = itemexpensesdraftlist;
        this.expensesdraft = expensesdraft;
        this.totaltable = totaltable;
    }
    
    @Override
    public int getRowCount() {
        return itemexpensesdraftlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public void setValueAt(Object value, int row, int col){
        ItemExpensesDraftEntity iis = itemexpensesdraftlist.get(row);
        if(col == 1){
            iis.setDescription(String.valueOf(value));
        }
        else if(col == 2){
            totaltable.setSubtotal(totaltable.getSubtotal() - iis.getSubtotal());
            iis.setQuantity(GlobalFunctions.convertToQuantity(value.toString()));
            fireTableCellUpdated(row, 6);
            totaltable.setSubtotal(totaltable.getSubtotal() + iis.getSubtotal());
        }
        else if(col == 3){
            totaltable.setSubtotal(totaltable.getSubtotal() - iis.getSubtotal());
            iis.setPrice(GlobalFunctions.convertToDouble(value.toString()));
            fireTableCellUpdated(row, 6);
            totaltable.setSubtotal(totaltable.getSubtotal() + iis.getSubtotal());
        }
        else if(col == 5){
            totaltable.setDiscount(totaltable.getDiscount() - iis.getDiscount());
            iis.setDiscount(GlobalFunctions.convertToDouble(value.toString()));
            fireTableCellUpdated(row, 4);
            fireTableCellUpdated(row, 6);
            totaltable.setDiscount(totaltable.getDiscount() + iis.getDiscount());
        }
        else if(col == 4){
            totaltable.setDiscount(totaltable.getDiscount() - iis.getDiscount());
            double percentage = GlobalFunctions.convertToDouble(value.toString());
            iis.setDiscount(iis.getSubtotal() * percentage / 100);
            
            fireTableCellUpdated(row, 5);
            fireTableCellUpdated(row, 6);
            totaltable.setDiscount(totaltable.getDiscount() + iis.getDiscount());
        }
        
        if(row+1 == getRowCount()){
            ItemExpensesDraftEntity newiis = itemexpensesdraftController.
                    createEmptyItemExpensesDraft(expensesdraft);
            addRow(newiis);
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemExpensesDraftEntity iis = itemexpensesdraftlist.get(rowIndex);

        Object[] values = new Object[]{
            String.valueOf(rowIndex + 1), 
            iis.getDescription(),
            iis.getFormattedQuantity(), 
            iis.getFormattedPrice(),
            iis.getFormattedDiscount_percentage(),
            iis.getFormattedDiscount(),
            iis.getFormattedTotal()
        };
        return values[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
    @Override
    public boolean isCellEditable(int row, int col){
        if(getItemExpensesDraftList().get(row).getDescription().isEmpty()){
            return col == 1;
        }
        else{
            return col == 1 || col == 2 || col == 3 || col == 4 || col == 5;
        }
    }
    
    public void addRow(ItemExpensesDraftEntity iis) {
        itemexpensesdraftlist.add(iis);
    }
    
    public void deleteRow(int rownum){
          
        ItemExpensesDraftEntity iis = itemexpensesdraftlist.get(rownum);
        
        deleteditemexpensesdraftlist.add(iis);
        
        itemexpensesdraftlist.remove(rownum);
        fireTableRowsDeleted(rownum, rownum);
        
        totaltable.setSubtotal(totaltable.getSubtotal() - iis.getSubtotal());
        
        totaltable.setDiscount(totaltable.getDiscount() - iis.getDiscount());
        
        if(getRowCount() == 0){
            addRow(itemexpensesdraftController.createEmptyItemExpensesDraft(expensesdraft));
        }
    }
    
    public List<ItemExpensesDraftEntity> getItemExpensesDraftList(){
        return itemexpensesdraftlist;
    }
    
    public List<ItemExpensesDraftEntity> getDeletedItemExpensesDraftList(){
        return deleteditemexpensesdraftlist;
    }
    
    public void setItemExpensesDraftList(ArrayList<ItemExpensesDraftEntity> itemexpensesdraftlist){
        this.itemexpensesdraftlist = itemexpensesdraftlist;
        fireTableDataChanged();
    }
    
    public boolean isEmpty(){
        boolean status = true;
        
        for(ItemExpensesDraftEntity id : itemexpensesdraftlist){
            if(id.getDescription().isEmpty()){
                status = false;
            }
        }
        
        return status;
    }
}
