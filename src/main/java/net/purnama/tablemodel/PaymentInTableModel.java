/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.PaymentInEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PaymentInTableModel extends AbstractTableModel{
    
    private ArrayList<PaymentInEntity> paymentinlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NUMBER"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DUEDATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_WAREHOUSE"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PARTNER"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CURRENCY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_AMOUNT"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")
    };
    
    public PaymentInTableModel(ArrayList<PaymentInEntity> paymentinlist){
        super();
        
        this.paymentinlist = paymentinlist;
    }
    
    @Override
    public int getRowCount() {
        return paymentinlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PaymentInEntity is = paymentinlist.get(rowIndex);
        
        Object[] values = new Object[]{
            is.getNumber(),
            is.getFormatteddate(), 
            is.getFormattedduedate(),
            is.getWarehouse_code(), 
            is.getPartner_name(), 
            is.getCurrency_code(),
            is.getFormattedamount(),
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
    
    public void setPaymentInList(ArrayList<PaymentInEntity> paymentinlist){
        this.paymentinlist = paymentinlist;
        fireTableDataChanged();
    }
    
    public PaymentInEntity getPaymentIn(int index){
        return paymentinlist.get(index);
    }
}
