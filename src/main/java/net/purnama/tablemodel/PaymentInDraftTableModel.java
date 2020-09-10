/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.draft.PaymentInDraftEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PaymentInDraftTableModel extends AbstractTableModel{
    
    private ArrayList<PaymentInDraftEntity> paymentindraftlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_ID"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DUEDATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_WAREHOUSE"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PARTNER"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CURRENCY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_AMOUNT")
    };
    
    public PaymentInDraftTableModel(ArrayList<PaymentInDraftEntity> paymentindraftlist){
        super();
        
        this.paymentindraftlist = paymentindraftlist;
    }
    
    @Override
    public int getRowCount() {
        return paymentindraftlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PaymentInDraftEntity is = paymentindraftlist.get(rowIndex);
        
        String partner;
        
        if(is.getPartner() != null){
            partner = is.getPartner().getName();
        }
        else{
            partner = "";
        }
        
        Object[] values = new Object[]{
            is.getId(),
            is.getFormattedDate(), 
            is.getFormattedDueDate(),
            is.getWarehouse().getName(), 
            partner, 
            is.getCurrency().getCode(),
            is.getFormattedAmount()
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
    
    public void setPaymentInDraftList(ArrayList<PaymentInDraftEntity> paymentindraftlist){
        this.paymentindraftlist = paymentindraftlist;
        fireTableDataChanged();
    }
    
    public PaymentInDraftEntity getPaymentInDraft(int index){
        return paymentindraftlist.get(index);
    }
}
