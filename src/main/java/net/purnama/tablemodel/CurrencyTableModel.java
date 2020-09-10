/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.CurrencyEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class CurrencyTableModel extends AbstractTableModel{
    private ArrayList<CurrencyEntity> currencylist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CODE"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NAME"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DESCRIPTION"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")};
    
    public CurrencyTableModel(ArrayList<CurrencyEntity> currencylist){
        super();
        
        this.currencylist = currencylist;
    }
    
    @Override
    public int getRowCount() {
        return currencylist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CurrencyEntity currency = currencylist.get(rowIndex);
        
        Object[] values = new Object[]{
            currency.getCode(),
            currency.getName(),
            currency.getDescription(),
            currency.isStatus()
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
    
    public List<CurrencyEntity> getCurrencyList(){
        return currencylist;
    }
    
    public void setCurrencyList(ArrayList<CurrencyEntity> currencylist){
        this.currencylist = currencylist;
        fireTableDataChanged();
    }
    
    public CurrencyEntity getCurrency(int index){
        return currencylist.get(index);
    }
}
