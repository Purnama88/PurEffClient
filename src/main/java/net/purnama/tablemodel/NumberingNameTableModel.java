/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.NumberingNameEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class NumberingNameTableModel extends AbstractTableModel{
    private ArrayList<NumberingNameEntity> numberingnamelist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NAME"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_START"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_END"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")};
    
    public NumberingNameTableModel(ArrayList<NumberingNameEntity> numberingnamelist){
        super();
        
        this.numberingnamelist = numberingnamelist;
    }

    @Override
    public int getRowCount() {
        return numberingnamelist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        NumberingNameEntity n = numberingnamelist.get(rowIndex);
        
        Object[] values = new Object[]{
            n.getName(),
            n.getFormattedBegin(),
            n.getFormattedEnd(),
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
    
    public List<NumberingNameEntity> getNumberingNameList(){
        return numberingnamelist;
    }
    
    public void setNumberingNameList(ArrayList<NumberingNameEntity> numberingnamelist){
        this.numberingnamelist = numberingnamelist;
        fireTableDataChanged();
    }
    
    public NumberingNameEntity getNumberingName(int index){
        return numberingnamelist.get(index);
    }
}
