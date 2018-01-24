/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.controller.ItemInvoiceWarehouseOutDraftController;
import net.purnama.model.ItemEntity;
import net.purnama.model.UomEntity;
import net.purnama.model.transactional.draft.InvoiceWarehouseOutDraftEntity;
import net.purnama.model.transactional.draft.ItemInvoiceWarehouseOutDraftEntity;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;

/**
 *
 * @author Purnama
 */
public class ItemInvoiceWarehouseOutDraftTableModel extends AbstractTableModel {
    
    private final ItemInvoiceWarehouseOutDraftController iteminvoicewarehouseoutdraftController;
    private final InvoiceWarehouseOutDraftEntity invoicewarehouseoutdraft;
    private ArrayList<ItemInvoiceWarehouseOutDraftEntity> iteminvoicewarehouseoutdraftlist;
    private final ArrayList<ItemInvoiceWarehouseOutDraftEntity> deletediteminvoicewarehouseoutdraftlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NO"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DESCRIPTION"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_QUANTITY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_UOM")
    };
    
    public ItemInvoiceWarehouseOutDraftTableModel(
            ArrayList<ItemInvoiceWarehouseOutDraftEntity> iteminvoicewarehouseoutdraftlist,
            InvoiceWarehouseOutDraftEntity invoicewarehouseoutdraft){
        super();
        iteminvoicewarehouseoutdraftController = new ItemInvoiceWarehouseOutDraftController();
        deletediteminvoicewarehouseoutdraftlist = new ArrayList<>();
        this.iteminvoicewarehouseoutdraftlist = iteminvoicewarehouseoutdraftlist;
        this.invoicewarehouseoutdraft = invoicewarehouseoutdraft;
    }
    
    @Override
    public int getRowCount() {
        return iteminvoicewarehouseoutdraftlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public void setValueAt(Object value, int row, int col){
        ItemInvoiceWarehouseOutDraftEntity iis = iteminvoicewarehouseoutdraftlist.get(row);
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
            ItemInvoiceWarehouseOutDraftEntity newiis = iteminvoicewarehouseoutdraftController.
                    createEmptyItemInvoiceWarehouseOutDraft(invoicewarehouseoutdraft);
            addRow(newiis);
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemInvoiceWarehouseOutDraftEntity iis = iteminvoicewarehouseoutdraftlist.get(rowIndex);

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
        if(getItemInvoiceWarehouseOutDraftList().get(row).getItem() != null){
            return col == 1 || col == 2 || col == 3;
        }
        else{
            return col == 1;
        }
    }
    
    public void addRow(ItemInvoiceWarehouseOutDraftEntity iis) {
        iteminvoicewarehouseoutdraftlist.add(iis);
    }
    
    public void deleteRow(int rownum){
          
        ItemInvoiceWarehouseOutDraftEntity iis = iteminvoicewarehouseoutdraftlist.get(rownum);
        
        deletediteminvoicewarehouseoutdraftlist.add(iis);
        
        iteminvoicewarehouseoutdraftlist.remove(rownum);
        fireTableRowsDeleted(rownum, rownum);
        
        if(getRowCount() == 0){
            addRow(iteminvoicewarehouseoutdraftController.
                    createEmptyItemInvoiceWarehouseOutDraft(invoicewarehouseoutdraft));
        }
    }
    
    public List<ItemInvoiceWarehouseOutDraftEntity> getItemInvoiceWarehouseOutDraftList(){
        return iteminvoicewarehouseoutdraftlist;
    }
    
    public List<ItemInvoiceWarehouseOutDraftEntity> getDeletedItemInvoiceWarehouseOutDraftList(){
        return deletediteminvoicewarehouseoutdraftlist;
    }
    
    public void setItemInvoiceWarehouseOutDraftList(ArrayList<ItemInvoiceWarehouseOutDraftEntity> iteminvoicewarehouseoutdraftlist){
        this.iteminvoicewarehouseoutdraftlist= iteminvoicewarehouseoutdraftlist;
        fireTableDataChanged();
    }
    
    public boolean isEmpty(){
        boolean status = true;
        
        for(ItemInvoiceWarehouseOutDraftEntity id : iteminvoicewarehouseoutdraftlist){
            if(id.getItem() == null){
                status = false;
            }
        }
        
        return status;
    }
    
    public ItemInvoiceWarehouseOutDraftEntity getItemInvoiceWarehouseOutDraft(int index){
        return iteminvoicewarehouseoutdraftlist.get(index);
    }
}

