/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.PaymentTypeInEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PaymentTypeInTableModel extends AbstractTableModel{
    private ArrayList<PaymentTypeInEntity> paymenttypeinlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NO"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_TYPE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DUEDATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_BANK"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NUMBER"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_EXPIRY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_AMOUNT"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_ACCEPTED"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_VALID"),
    };
    
    public PaymentTypeInTableModel(ArrayList<PaymentTypeInEntity> paymenttypeinlist){
        super();
        
        this.paymenttypeinlist = paymenttypeinlist;
    }
    
    @Override
    public int getRowCount() {
        return paymenttypeinlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public void setValueAt(Object value, int row, int col){
        PaymentTypeInEntity ig = paymenttypeinlist.get(row);
        
        if(col == 8){
            ig.setStatus(!ig.isStatus());   
        }
    }
    
    @Override
    public boolean isCellEditable(int row, int col){
        if(getPaymentTypeIn(row).isValid()){
            return col == 8;
        }
        else{
            return false;
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PaymentTypeInEntity ig = paymenttypeinlist.get(rowIndex);
        
        Object[] values = new Object[]{
            String.valueOf(rowIndex + 1), 
            PaymentTypeInEntity.PAYMENT_TYPE[ig.getType()], 
            ig.getFormatteddate(), 
            ig.getFormattedduedate(),
            ig.getBank(),
            ig.getNumber(),
            ig.getExpirydate(),
            ig.getFormattedamount(),
            ig.isStatus(),
            ig.isValid()
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
    
    public void addRow(PaymentTypeInEntity paymenttypein) {
        int rowCount = getRowCount();

        paymenttypeinlist.add(paymenttypein);
        fireTableRowsInserted(rowCount, rowCount);
    }
    
    public List<PaymentTypeInEntity> getPaymentTypeInList(){
        return paymenttypeinlist;
    }
    
    public void setPaymentTypeInList(ArrayList<PaymentTypeInEntity> paymenttypeinlist){
        this.paymenttypeinlist = paymenttypeinlist;
        fireTableDataChanged();
    }
    
    public PaymentTypeInEntity getPaymentTypeIn(int index){
        return paymenttypeinlist.get(index);
    }
    
    public double deleteRow(int index){
        double value = paymenttypeinlist.get(index).getAmount();
        paymenttypeinlist.remove(index);
        fireTableDataChanged();
        
        return value;
    }
}
