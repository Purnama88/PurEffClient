/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.InvoiceWarehouseOutEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class InvoiceWarehouseOutTableModel extends AbstractTableModel{
    
    private ArrayList<InvoiceWarehouseOutEntity> invoicewarehouseoutlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NUMBER"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_WAREHOUSE"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DESTINATION"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")
    };
    
    public InvoiceWarehouseOutTableModel(ArrayList<InvoiceWarehouseOutEntity> invoicewarehouseoutlist){
        super();
        
        this.invoicewarehouseoutlist = invoicewarehouseoutlist;
    }
    
    @Override
    public int getRowCount() {
        return invoicewarehouseoutlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceWarehouseOutEntity is = invoicewarehouseoutlist.get(rowIndex);
        
        Object[] values = new Object[]{
            is.getId(),
            is.getFormatteddate(),
            is.getWarehouse_code(),
            is.getDestination_code(),
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
    
    public void setInvoiceWarehouseOutList(ArrayList<InvoiceWarehouseOutEntity> invoicewarehouseoutlist){
        this.invoicewarehouseoutlist = invoicewarehouseoutlist;
        fireTableDataChanged();
    }
    
    public InvoiceWarehouseOutEntity getInvoiceWarehouseOut(int index){
        return invoicewarehouseoutlist.get(index);
    }
}
