/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.draft.PaymentOutDraftEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PaymentOutDraftTableModel extends AbstractTableModel{
    
    private ArrayList<PaymentOutDraftEntity> paymentoutdraftlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_ID"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DUEDATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_WAREHOUSE"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PARTNER"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CURRENCY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_AMOUNT")
    };
    
    public PaymentOutDraftTableModel(ArrayList<PaymentOutDraftEntity> paymentoutdraftlist){
        super();
        
        this.paymentoutdraftlist = paymentoutdraftlist;
    }
    
    @Override
    public int getRowCount() {
        return paymentoutdraftlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PaymentOutDraftEntity is = paymentoutdraftlist.get(rowIndex);
        
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
    
    public void setPaymentOutDraftList(ArrayList<PaymentOutDraftEntity> paymentoutdraftlist){
        this.paymentoutdraftlist = paymentoutdraftlist;
        fireTableDataChanged();
    }
    
    public PaymentOutDraftEntity getPaymentOutDraft(int index){
        return paymentoutdraftlist.get(index);
    }
}
