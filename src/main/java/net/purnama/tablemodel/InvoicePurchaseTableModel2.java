/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.InvoicePurchaseEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class InvoicePurchaseTableModel2 extends AbstractTableModel{
    
    private ArrayList<InvoicePurchaseEntity> invoicepurchaselist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NUMBER"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PARTNER"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CURRENCY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_TOTAL"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")
    };
    
    public InvoicePurchaseTableModel2(ArrayList<InvoicePurchaseEntity> invoicepurchaselist){
        super();
        
        this.invoicepurchaselist = invoicepurchaselist;
    }
    
    @Override
    public int getRowCount() {
        return invoicepurchaselist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoicePurchaseEntity is = invoicepurchaselist.get(rowIndex);
        
        Object[] values = new Object[]{
            is.getNumber(),
            is.getFormatteddate(),  
            is.getPartner_name(), 
            is.getCurrency_code(),
            is.getFormattedtotal_after_tax(),
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
    
    public void setInvoicePurchaseList(ArrayList<InvoicePurchaseEntity> invoicepurchaselist){
        this.invoicepurchaselist = invoicepurchaselist;
        fireTableDataChanged();
    }
    
    public InvoicePurchaseEntity getInvoicePurchase(int index){
        return invoicepurchaselist.get(index);
    }
}
