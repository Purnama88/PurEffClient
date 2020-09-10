/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.InvoiceWarehouseInEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class InvoiceWarehouseInTableModel extends AbstractTableModel{
    
    private ArrayList<InvoiceWarehouseInEntity> invoicewarehouseinlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NUMBER"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_WAREHOUSE"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_ORIGIN"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")
    };
    
    public InvoiceWarehouseInTableModel(ArrayList<InvoiceWarehouseInEntity> invoicewarehouseinlist){
        super();
        
        this.invoicewarehouseinlist = invoicewarehouseinlist;
    }
    
    @Override
    public int getRowCount() {
        return invoicewarehouseinlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceWarehouseInEntity is = invoicewarehouseinlist.get(rowIndex);
        
        Object[] values = new Object[]{
            is.getId(),
            is.getFormatteddate(),
            is.getWarehouse_code(),
            is.getOrigin_code(),
            is.getFormattedstatus()
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
    
    public void setInvoiceWarehouseInList(ArrayList<InvoiceWarehouseInEntity> invoicewarehouseinlist){
        this.invoicewarehouseinlist = invoicewarehouseinlist;
        fireTableDataChanged();
    }
    
    public InvoiceWarehouseInEntity getInvoiceWarehouseIn(int index){
        return invoicewarehouseinlist.get(index);
    }
}
