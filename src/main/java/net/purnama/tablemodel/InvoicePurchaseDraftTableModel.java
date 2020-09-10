/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.draft.InvoicePurchaseDraftEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class InvoicePurchaseDraftTableModel extends AbstractTableModel{
    
    private ArrayList<InvoicePurchaseDraftEntity> invoicepurchasedraftlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_ID"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_WAREHOUSE"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PARTNER"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CURRENCY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_TOTAL")
    };
    
    public InvoicePurchaseDraftTableModel(ArrayList<InvoicePurchaseDraftEntity> invoicepurchasedraftlist){
        super();
        
        this.invoicepurchasedraftlist = invoicepurchasedraftlist;
    }
    
    @Override
    public int getRowCount() {
        return invoicepurchasedraftlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        InvoicePurchaseDraftEntity is = invoicepurchasedraftlist.get(rowIndex);
        
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
            is.getWarehouse().getName(), 
            partner, 
            is.getCurrency().getCode(),
            is.getFormattedTotal_after_tax()
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
    
    public void setInvoicePurchaseDraftList(ArrayList<InvoicePurchaseDraftEntity> invoicepurchasedraftlist){
        this.invoicepurchasedraftlist = invoicepurchasedraftlist;
        fireTableDataChanged();
    }
    
    public ArrayList<InvoicePurchaseDraftEntity> getInvoicePurchaseDraftList(){
        return invoicepurchasedraftlist;
    }
    
    public InvoicePurchaseDraftEntity getInvoicePurchaseDraft(int index){
        return invoicepurchasedraftlist.get(index);
    }
}
