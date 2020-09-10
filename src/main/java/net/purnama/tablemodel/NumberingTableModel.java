/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.NumberingEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class NumberingTableModel extends AbstractTableModel{
    private ArrayList<NumberingEntity> numberinglist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NAME"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PREFIX"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_START"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_END"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CURRENT"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")
        };
    
    public NumberingTableModel(ArrayList<NumberingEntity> numberinglist){
        super();
        
        this.numberinglist = numberinglist;
    }

    @Override
    public int getRowCount() {
        return numberinglist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        NumberingEntity n = numberinglist.get(rowIndex);
        
        Object[] values = new Object[]{
            n.getNumberingname().getName(),
            n.getPrefix(), 
            n.getFormattedStart(), 
            n.getFormattedEnd(), 
            n.getFormattedCurrent(),
            n.isStatus()
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
    
    public void addRow(NumberingEntity numbering) {
        int rowCount = getRowCount();

        numberinglist.add(numbering);
        fireTableRowsInserted(rowCount, rowCount);
    }
    
    public List<NumberingEntity> getNumberingList(){
        return numberinglist;
    }
    
    public void setNumberingList(ArrayList<NumberingEntity> numberinglist){
        this.numberinglist = numberinglist;
        fireTableDataChanged();
    }
    
    public NumberingEntity getNumbering(int rowindex){
        return numberinglist.get(rowindex);
    }
}
