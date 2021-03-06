/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.ReturnSalesEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ReturnSalesTableModel2 extends AbstractTableModel{
    
    private ArrayList<ReturnSalesEntity> returnsaleslist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NUMBER"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PARTNER"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CURRENCY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_TOTAL"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")
    };
    
    public ReturnSalesTableModel2(ArrayList<ReturnSalesEntity> returnsaleslist){
        super();
        
        this.returnsaleslist = returnsaleslist;
    }
    
    @Override
    public int getRowCount() {
        return returnsaleslist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ReturnSalesEntity is = returnsaleslist.get(rowIndex);
        
        Object[] values = new Object[]{
            is.getNumber(),
            is.getFormatteddate(),  
            is.getPartner_name(), 
            is.getCurrency_code(),
            is.getFormattedtotal_after_tax(),
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
    
    public void setReturnSalesList(ArrayList<ReturnSalesEntity> returnsaleslist){
        this.returnsaleslist = returnsaleslist;
        fireTableDataChanged();
    }
    
    public ReturnSalesEntity getReturnSales(int index){
        return returnsaleslist.get(index);
    }
}
