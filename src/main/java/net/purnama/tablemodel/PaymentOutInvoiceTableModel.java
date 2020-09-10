/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.PaymentOutExpensesEntity;
import net.purnama.model.transactional.PaymentOutInvoiceEntity;
import net.purnama.model.transactional.PaymentOutReturnPurchaseEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PaymentOutInvoiceTableModel extends AbstractTableModel{
    
    private ArrayList<PaymentOutInvoiceEntity> paymentoutinvoicelist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NO"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_TYPE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NUMBER"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_WAREHOUSE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DUEDATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CURRENCY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_TOTAL"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_AMOUNT"),
        };
    
    public PaymentOutInvoiceTableModel(ArrayList<PaymentOutInvoiceEntity> paymentoutinvoicelist){
        super();
        
        this.paymentoutinvoicelist = paymentoutinvoicelist;
    }

    public void setPaymentOutInvoiceList(ArrayList<PaymentOutInvoiceEntity> paymentoutinvoicelist){
        this.paymentoutinvoicelist = paymentoutinvoicelist;
    }
    
    @Override
    public int getRowCount() {
        return paymentoutinvoicelist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PaymentOutInvoiceEntity n = paymentoutinvoicelist.get(rowIndex);
        
        String type = GlobalFields.PROPERTIES.getProperty("LABEL_INVOICE");
        
        if(n instanceof PaymentOutReturnPurchaseEntity){
            type = GlobalFields.PROPERTIES.getProperty("LABEL_RETURN");
        }
        else if(n instanceof PaymentOutExpensesEntity){
            type = GlobalFields.PROPERTIES.getProperty("LABEL_EXPENSE");
        }
        
        Object[] values = new Object[]{
            String.valueOf(rowIndex + 1),
            type,
            n.getInvoice_id(), 
            n.getInvoice_warehouse(), 
            n.getInvoice_date(), 
            n.getInvoice_duedate(),
            n.getInvoice_currency(),
            n.getInvoice_total(),
            n.getFormattedAmount()
            };
        return values[columnIndex];
    }
    
}
