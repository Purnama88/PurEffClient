/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.controller.ItemInvoiceWarehouseInDraftController;
import net.purnama.model.ItemEntity;
import net.purnama.model.UomEntity;
import net.purnama.model.transactional.draft.InvoiceWarehouseInDraftEntity;
import net.purnama.model.transactional.draft.ItemInvoiceWarehouseInDraftEntity;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;

/**
 *
 * @author Purnama
 */
public class ItemInvoiceWarehouseInDraftTableModel extends AbstractTableModel {
    
    private final ItemInvoiceWarehouseInDraftController iteminvoicewarehouseindraftController;
    private final InvoiceWarehouseInDraftEntity invoicewarehouseindraft;
    private ArrayList<ItemInvoiceWarehouseInDraftEntity> iteminvoicewarehouseindraftlist;
    private final ArrayList<ItemInvoiceWarehouseInDraftEntity> deletediteminvoicewarehouseindraftlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NO"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DESCRIPTION"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_QUANTITY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_UOM")
    };
    
    public ItemInvoiceWarehouseInDraftTableModel(
            ArrayList<ItemInvoiceWarehouseInDraftEntity> iteminvoicewarehouseindraftlist,
            InvoiceWarehouseInDraftEntity invoicewarehouseindraft){
        super();
        iteminvoicewarehouseindraftController = new ItemInvoiceWarehouseInDraftController();
        deletediteminvoicewarehouseindraftlist = new ArrayList<>();
        this.iteminvoicewarehouseindraftlist = iteminvoicewarehouseindraftlist;
        this.invoicewarehouseindraft = invoicewarehouseindraft;
    }
    
    @Override
    public int getRowCount() {
        return iteminvoicewarehouseindraftlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public void setValueAt(Object value, int row, int col){
        ItemInvoiceWarehouseInDraftEntity iis = iteminvoicewarehouseindraftlist.get(row);
        if(col == 1){
            ItemEntity item = (ItemEntity)value;
            if(item == null){
                
            }
            else{
                iis.setItem(item);
                iis.setUom(item.getSelluom());

                fireTableCellUpdated(row, 2);
                fireTableCellUpdated(row, 3);
            }
        }
        else if(col == 2){
            iis.setQuantity(GlobalFunctions.convertToQuantity(value.toString()));
        }
        else if(col == 3){
            UomEntity uom = (UomEntity)value;
            iis.setUom(uom);
        }
        
        if(row+1 == getRowCount()){
            ItemInvoiceWarehouseInDraftEntity newiis = iteminvoicewarehouseindraftController.
                    createEmptyItemInvoiceWarehouseInDraft(invoicewarehouseindraft);
            addRow(newiis);
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemInvoiceWarehouseInDraftEntity iis = iteminvoicewarehouseindraftlist.get(rowIndex);

        String description = "";
        String uom = "";
        
        if(iis.getItem() != null){
            description = iis.getItem().getCode() + " - " + iis.getItem().getName();
        }
        
        if(iis.getUom() != null){
            uom = iis.getUom().toString();
        }
        
        Object[] values = new Object[]{
            String.valueOf(rowIndex + 1), 
            description, 
            iis.getFormattedQuantity(), 
            uom
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
        if(getItemInvoiceWarehouseInDraftList().get(row).getItem() != null){
            return col == 1 || col == 2 || col == 3;
        }
        else{
            return col == 1;
        }
    }
    
    public void addRow(ItemInvoiceWarehouseInDraftEntity iis) {
        iteminvoicewarehouseindraftlist.add(iis);
    }
    
    public void deleteRow(int rownum){
          
        ItemInvoiceWarehouseInDraftEntity iis = iteminvoicewarehouseindraftlist.get(rownum);
        
        deletediteminvoicewarehouseindraftlist.add(iis);
        
        iteminvoicewarehouseindraftlist.remove(rownum);
        fireTableRowsDeleted(rownum, rownum);
        
        if(getRowCount() == 0){
            addRow(iteminvoicewarehouseindraftController.
                    createEmptyItemInvoiceWarehouseInDraft(invoicewarehouseindraft));
        }
    }
    
    public List<ItemInvoiceWarehouseInDraftEntity> getItemInvoiceWarehouseInDraftList(){
        return iteminvoicewarehouseindraftlist;
    }
    
    public List<ItemInvoiceWarehouseInDraftEntity> getDeletedItemInvoiceWarehouseInDraftList(){
        return deletediteminvoicewarehouseindraftlist;
    }
    
    public void setItemInvoiceWarehouseInDraftList(ArrayList<ItemInvoiceWarehouseInDraftEntity> iteminvoicewarehouseindraftlist){
        this.iteminvoicewarehouseindraftlist= iteminvoicewarehouseindraftlist;
        fireTableDataChanged();
    }
    
    public boolean isEmpty(){
        boolean status = true;
        
        for(ItemInvoiceWarehouseInDraftEntity id : iteminvoicewarehouseindraftlist){
            if(id.getItem() == null){
                status = false;
            }
        }
        
        return status;
    }
    
    public ItemInvoiceWarehouseInDraftEntity getItemInvoiceWarehouseInDraft(int index){
        return iteminvoicewarehouseindraftlist.get(index);
    }
}

