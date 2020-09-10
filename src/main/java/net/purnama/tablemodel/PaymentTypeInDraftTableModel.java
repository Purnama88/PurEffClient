/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.draft.PaymentTypeInDraftEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PaymentTypeInDraftTableModel extends AbstractTableModel{
    private ArrayList<PaymentTypeInDraftEntity> paymenttypeindraftlist;
    private final ArrayList<PaymentTypeInDraftEntity> deletedpaymenttypeindraftlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_TYPE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DUEDATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_BANK"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NUMBER"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_EXPIRY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_AMOUNT"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_VALID"),
    };
    
    public PaymentTypeInDraftTableModel(ArrayList<PaymentTypeInDraftEntity> paymenttypeindraftlist){
        super();
        deletedpaymenttypeindraftlist = new ArrayList<>();
        this.paymenttypeindraftlist = paymenttypeindraftlist;
    }
    
    @Override
    public int getRowCount() {
        return paymenttypeindraftlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PaymentTypeInDraftEntity ig = paymenttypeindraftlist.get(rowIndex);
        
        Object[] values = new Object[]{
            PaymentTypeInDraftEntity.PAYMENT_TYPE[ig.getType()], 
            ig.getFormattedDate(), 
            ig.getFormattedDueDate(),
            ig.getBank(),
            ig.getNumber(),
            ig.getExpirydate(),
            ig.getFormattedAmount(),
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
    
    public void addRow(PaymentTypeInDraftEntity paymenttypeindraft) {
        int rowCount = getRowCount();

        paymenttypeindraftlist.add(paymenttypeindraft);
        fireTableRowsInserted(rowCount, rowCount);
    }
    
    public List<PaymentTypeInDraftEntity> getPaymentTypeInDraftList(){
        return paymenttypeindraftlist;
    }
    
    public List<PaymentTypeInDraftEntity> getDeletedPaymentTypeInDraftList(){
        return deletedpaymenttypeindraftlist;
    }
    
    public void setPaymentTypeInDraftList(ArrayList<PaymentTypeInDraftEntity> paymenttypeindraftlist){
        this.paymenttypeindraftlist = paymenttypeindraftlist;
        fireTableDataChanged();
    }
    
    public PaymentTypeInDraftEntity getPaymentTypeInDraft(int index){
        return paymenttypeindraftlist.get(index);
    }
    
    public double deleteRow(int rownum){
        
        PaymentTypeInDraftEntity id = paymenttypeindraftlist.get(rownum);
        
        deletedpaymenttypeindraftlist.add(id);
        
        paymenttypeindraftlist.remove(rownum);
        fireTableRowsDeleted(rownum, rownum);
        
        
        return id.getAmount();
    }
}
