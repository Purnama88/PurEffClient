/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.PaymentOutEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PaymentOutTableModel2 extends AbstractTableModel{
    
    private ArrayList<PaymentOutEntity> paymentoutlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NUMBER"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DUEDATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PARTNER"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_AMOUNT"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")
    };
    
    public PaymentOutTableModel2(ArrayList<PaymentOutEntity> paymentoutlist){
        super();
        
        this.paymentoutlist = paymentoutlist;
    }
    
    @Override
    public int getRowCount() {
        return paymentoutlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PaymentOutEntity is = paymentoutlist.get(rowIndex);
        
        Object[] values = new Object[]{
            is.getNumber(),
            is.getFormatteddate(), 
            is.getFormattedduedate(),
            is.getPartner_name(), 
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
    
    public void setPaymentOutList(ArrayList<PaymentOutEntity> paymentoutlist){
        this.paymentoutlist = paymentoutlist;
        fireTableDataChanged();
    }
    
    public PaymentOutEntity getPaymentOut(int index){
        return paymentoutlist.get(index);
    }
}
