/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.RateEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class RateTableModel extends AbstractTableModel{
    
    private ArrayList<RateEntity> ratelist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_VALUE")
    };
    
    public RateTableModel(ArrayList<RateEntity> ratelist){
        super();
        
        this.ratelist = ratelist;
    }
    
    @Override
    public int getRowCount() {
        return ratelist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RateEntity u = ratelist.get(rowIndex);
        
        Object[] values = new Object[]{
            u.getFormattedDate(),
            u.getFormattedValue()
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
    
    public List<RateEntity> getRateList(){
        return ratelist;
    }
    
    public void setRateList(ArrayList<RateEntity> ratelist){
        this.ratelist = ratelist;
        fireTableDataChanged();
    }
    
    public RateEntity getRate(int index){
        return ratelist.get(index);
    }
    
}
