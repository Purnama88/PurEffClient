/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.ReturnPurchaseEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ReturnPurchaseTableModel extends AbstractTableModel{
    
    private ArrayList<ReturnPurchaseEntity> returnpurchaselist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NUMBER"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_WAREHOUSE"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PARTNER"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CURRENCY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_TOTAL"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_REMAINING"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")
    };
    
    public ReturnPurchaseTableModel(ArrayList<ReturnPurchaseEntity> returnpurchaselist){
        super();
        
        this.returnpurchaselist = returnpurchaselist;
    }
    
    @Override
    public int getRowCount() {
        return returnpurchaselist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ReturnPurchaseEntity is = returnpurchaselist.get(rowIndex);
        
        Object[] values = new Object[]{
            is.getNumber(),
            is.getFormatteddate(), 
            is.getWarehouse_code(), 
            is.getPartner_name(), 
            is.getCurrency_code(),
            is.getFormattedtotal_after_tax(),
            is.getFormattedRemaining(),
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
    
    public void setReturnPurchaseList(ArrayList<ReturnPurchaseEntity> returnpurchaselist){
        this.returnpurchaselist = returnpurchaselist;
        fireTableDataChanged();
    }
    
    public ReturnPurchaseEntity getReturnPurchase(int index){
        return returnpurchaselist.get(index);
    }
}
