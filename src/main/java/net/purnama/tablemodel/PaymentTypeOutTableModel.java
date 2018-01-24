/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.PaymentTypeOutEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PaymentTypeOutTableModel extends AbstractTableModel{
    private ArrayList<PaymentTypeOutEntity> paymenttypeoutlist;
    
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
    
    public PaymentTypeOutTableModel(ArrayList<PaymentTypeOutEntity> paymenttypeoutlist){
        super();
        
        this.paymenttypeoutlist = paymenttypeoutlist;
    }
    
    @Override
    public int getRowCount() {
        return paymenttypeoutlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public void setValueAt(Object value, int row, int col){
        PaymentTypeOutEntity ig = paymenttypeoutlist.get(row);
        
        if(col == 8){
            ig.setStatus(!ig.isStatus());
            
        }
        else if(col == 9){
            ig.setValid(!ig.isValid());
        }
    }
    
    @Override
    public boolean isCellEditable(int row, int col){
        return col == 8 || col == 9;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PaymentTypeOutEntity ig = paymenttypeoutlist.get(rowIndex);
        
        Object[] values = new Object[]{
            String.valueOf(rowIndex + 1), 
            PaymentTypeOutEntity.PAYMENT_TYPE[ig.getType()], 
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
    
    public void addRow(PaymentTypeOutEntity paymenttypeout) {
        int rowCount = getRowCount();

        paymenttypeoutlist.add(paymenttypeout);
        fireTableRowsInserted(rowCount, rowCount);
    }
    
    public List<PaymentTypeOutEntity> getPaymentTypeOutList(){
        return paymenttypeoutlist;
    }
    
    public void setPaymentTypeOutList(ArrayList<PaymentTypeOutEntity> paymenttypeoutlist){
        this.paymenttypeoutlist = paymenttypeoutlist;
        fireTableDataChanged();
    }
    
    public PaymentTypeOutEntity getPaymentTypeOut(int index){
        return paymenttypeoutlist.get(index);
    }
    
    public double deleteRow(int index){
        double value = paymenttypeoutlist.get(index).getAmount();
        paymenttypeoutlist.remove(index);
        fireTableDataChanged();
        
        return value;
    }
}
