/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.controller.ItemDeliveryDraftController;
import net.purnama.model.transactional.draft.DeliveryDraftEntity;
import net.purnama.model.transactional.draft.ItemDeliveryDraftEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ItemDeliveryDraftTableModel extends AbstractTableModel{
    
    private final ItemDeliveryDraftController itemdeliverydraftController;
    private ArrayList<ItemDeliveryDraftEntity> itemdeliverydraftlist;
    private final ArrayList<ItemDeliveryDraftEntity> deleteditemdeliverydraftlist;
    private final DeliveryDraftEntity deliverydraft;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NO"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_QUANTITY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DESCRIPTION"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_REMARK")};
    
    public ItemDeliveryDraftTableModel(ArrayList<ItemDeliveryDraftEntity> itemdeliverydraftlist,
            DeliveryDraftEntity deliverydraft) {
        super();

        itemdeliverydraftController = new ItemDeliveryDraftController();
        deleteditemdeliverydraftlist = new ArrayList<>();
        this.itemdeliverydraftlist = itemdeliverydraftlist;
        this.deliverydraft = deliverydraft;
    }
    
    @Override
    public int getRowCount() {
        return itemdeliverydraftlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public void setValueAt(Object value, int row, int col) {
        ItemDeliveryDraftEntity id = itemdeliverydraftlist.get(row);
        String cont = String.valueOf(value);
        if(col == 1){
            id.setQuantity(cont);
        }
        else if(col == 2){
            id.setDescription(cont);
        }
        else{
            id.setRemark(cont);
        }
        
//        GlobalFields.STATE = false;
        
        if(row+1 == getRowCount()){
            itemdeliverydraftlist.add(itemdeliverydraftController.
                    createEmptyItemDeliveryDraft(deliverydraft));
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemDeliveryDraftEntity id = itemdeliverydraftlist.get(rowIndex);

        Object[] values = new Object[]{
            String.valueOf(rowIndex + 1), 
            id.getQuantity(), 
            id.getDescription(),
            id.getRemark()};
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
        return col > 0;
    }
    
    public void addRow(ItemDeliveryDraftEntity id) {
        itemdeliverydraftlist.add(id);
    }
    
    public void deleteRow(int rownum){
          
        ItemDeliveryDraftEntity id = itemdeliverydraftlist.get(rownum);
        
        deleteditemdeliverydraftlist.add(id);
        
        itemdeliverydraftlist.remove(rownum);
        fireTableRowsDeleted(rownum, rownum);
        
        if(getRowCount() == 0){
            addRow(itemdeliverydraftController.createEmptyItemDeliveryDraft(deliverydraft));
        }
    }
    
    public List<ItemDeliveryDraftEntity> getItemDeliveryDraftList(){
        return itemdeliverydraftlist;
    }
    
    public List<ItemDeliveryDraftEntity> getDeletedItemDeliveryDraftList(){
        return deleteditemdeliverydraftlist;
    }
    
    public void setItemDeliveryDraftList(ArrayList<ItemDeliveryDraftEntity> itemdeliverydraftlist){
        this.itemdeliverydraftlist = itemdeliverydraftlist;
        fireTableDataChanged();
    }
    
    public boolean isEmpty(){
        boolean status = true;
        
        for(ItemDeliveryDraftEntity id : itemdeliverydraftlist){
            if(!id.getDescription().isEmpty()){
                status = false;
            }
        }
        
        return status;
    }
}
