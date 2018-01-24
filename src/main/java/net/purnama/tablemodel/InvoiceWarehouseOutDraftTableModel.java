/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.draft.InvoiceWarehouseOutDraftEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class InvoiceWarehouseOutDraftTableModel extends AbstractTableModel{
    
    private ArrayList<InvoiceWarehouseOutDraftEntity> invoicewarehouseoutdraftlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_ID"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_WAREHOUSE"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DESTINATION")
    };
    
    public InvoiceWarehouseOutDraftTableModel(ArrayList<InvoiceWarehouseOutDraftEntity> invoicewarehouseoutdraftlist){
        super();
        
        this.invoicewarehouseoutdraftlist = invoicewarehouseoutdraftlist;
    }
    
    @Override
    public int getRowCount() {
        return invoicewarehouseoutdraftlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceWarehouseOutDraftEntity is = invoicewarehouseoutdraftlist.get(rowIndex);
        
        String destination;
        
        if(is.getDestination()== null){
            destination = "";
        }
        else{
            destination = is.getDestination().getName();
        }
        
        Object[] values = new Object[]{
            is.getId(),
            is.getFormattedDate(),
            is.getWarehouse().getName(),
            destination, 
            };
        return values[columnIndex];
    }
    
    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }
    
    @Override
    public Class getColumnClass(int c) {  
        return getValueAt(0, c).getClass();  
    }
    
    public void setInvoiceWarehouseOutDraftList(ArrayList<InvoiceWarehouseOutDraftEntity> 
            invoicewarehouseoutdraftlist){
        this.invoicewarehouseoutdraftlist = invoicewarehouseoutdraftlist;
        fireTableDataChanged();
    }
    
    public ArrayList<InvoiceWarehouseOutDraftEntity> getInvoiceWarehouseOutDraftList(){
        return invoicewarehouseoutdraftlist;
    }
    
    public InvoiceWarehouseOutDraftEntity getInvoiceWarehouseOutDraft(int index){
        return invoicewarehouseoutdraftlist.get(index);
    }
}
