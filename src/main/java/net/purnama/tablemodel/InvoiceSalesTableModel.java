/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.InvoiceSalesEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class InvoiceSalesTableModel extends AbstractTableModel{
    
    private ArrayList<InvoiceSalesEntity> invoicesaleslist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NUMBER"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_WAREHOUSE"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PARTNER"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CURRENCY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_TOTAL"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_REMAINING"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")
    };
    
    public InvoiceSalesTableModel(ArrayList<InvoiceSalesEntity> invoicesaleslist){
        super();
        
        this.invoicesaleslist = invoicesaleslist;
    }
    
    @Override
    public int getRowCount() {
        return invoicesaleslist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoiceSalesEntity is = invoicesaleslist.get(rowIndex);
        
        Object[] values = new Object[]{
            is.getNumber(),
            is.getFormatteddate(), 
            is.getWarehouse_code(), 
            is.getPartner_name(), 
            is.getCurrency_code(),
            is.getFormattedtotal_after_tax(),
            is.getFormattedRemaining(),
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
    
    public void setInvoiceSalesList(ArrayList<InvoiceSalesEntity> invoicesaleslist){
        this.invoicesaleslist = invoicesaleslist;
        fireTableDataChanged();
    }
    
    public InvoiceSalesEntity getInvoiceSales(int index){
        return invoicesaleslist.get(index);
    }
}
