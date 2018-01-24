/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.draft.InvoiceWarehouseInDraftEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class InvoiceWarehouseInDraftTableModel extends AbstractTableModel{
    
    private ArrayList<InvoiceWarehouseInDraftEntity> invoicewarehouseindraftlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_ID"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_WAREHOUSE"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_ORIGIN")
    };
    
    public InvoiceWarehouseInDraftTableModel(ArrayList<InvoiceWarehouseInDraftEntity> invoicewarehouseindraftlist){
        super();
        
        this.invoicewarehouseindraftlist = invoicewarehouseindraftlist;
    }
    
    @Override
    public int getRowCount() {
        return invoicewarehouseindraftlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceWarehouseInDraftEntity is = invoicewarehouseindraftlist.get(rowIndex);
        
        String origin;
        
        if(is.getOrigin()== null){
            origin = "";
        }
        else{
            origin = is.getOrigin().getName();
        }
        
        Object[] values = new Object[]{
            is.getId(),
            is.getFormattedDate(),
            is.getWarehouse().getName(),
            origin, 
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
    
    public void setInvoiceWarehouseInDraftList(ArrayList<InvoiceWarehouseInDraftEntity> invoicewarehouseindraftlist){
        this.invoicewarehouseindraftlist = invoicewarehouseindraftlist;
        fireTableDataChanged();
    }
    
    public ArrayList<InvoiceWarehouseInDraftEntity> getInvoiceWarehouseInDraftList(){
        return invoicewarehouseindraftlist;
    }
    
    public InvoiceWarehouseInDraftEntity getInvoiceWarehouseInDraft(int index){
        return invoicewarehouseindraftlist.get(index);
    }
}
