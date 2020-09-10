/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.draft.PaymentTypeOutDraftEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PaymentTypeOutDraftTableModel extends AbstractTableModel{
    private ArrayList<PaymentTypeOutDraftEntity> paymenttypeoutdraftlist;
    private final ArrayList<PaymentTypeOutDraftEntity> deletedpaymenttypeoutdraftlist;
    
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
    
    public PaymentTypeOutDraftTableModel(ArrayList<PaymentTypeOutDraftEntity> paymenttypeoutdraftlist){
        super();
        deletedpaymenttypeoutdraftlist = new ArrayList<>();
        this.paymenttypeoutdraftlist = paymenttypeoutdraftlist;
    }
    
    @Override
    public int getRowCount() {
        return paymenttypeoutdraftlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PaymentTypeOutDraftEntity ig = paymenttypeoutdraftlist.get(rowIndex);
        
        Object[] values = new Object[]{
            PaymentTypeOutDraftEntity.PAYMENT_TYPE[ig.getType()], 
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
    
    public void addRow(PaymentTypeOutDraftEntity paymenttypeoutdraft) {
        int rowCount = getRowCount();

        paymenttypeoutdraftlist.add(paymenttypeoutdraft);
        fireTableRowsInserted(rowCount, rowCount);
    }
    
    public List<PaymentTypeOutDraftEntity> getPaymentTypeOutDraftList(){
        return paymenttypeoutdraftlist;
    }
    
    public List<PaymentTypeOutDraftEntity> getDeletedPaymentTypeOutDraftList(){
        return deletedpaymenttypeoutdraftlist;
    }
    
    public void setPaymentTypeOutDraftList(ArrayList<PaymentTypeOutDraftEntity> paymenttypeoutdraftlist){
        this.paymenttypeoutdraftlist = paymenttypeoutdraftlist;
        fireTableDataChanged();
    }
    
    public PaymentTypeOutDraftEntity getPaymentTypeOutDraft(int index){
        return paymenttypeoutdraftlist.get(index);
    }
    
    public double deleteRow(int rownum){
        PaymentTypeOutDraftEntity id = paymenttypeoutdraftlist.get(rownum);
        
        deletedpaymenttypeoutdraftlist.add(id);
        
        paymenttypeoutdraftlist.remove(rownum);
        fireTableRowsDeleted(rownum, rownum);
        
        
        return id.getAmount();
    }
}
