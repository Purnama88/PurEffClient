/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.UomEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class UomTableModel extends AbstractTableModel {
    private ArrayList<UomEntity> uomlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NAME"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_VALUE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PARENT"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")
    };
    
    public UomTableModel(ArrayList<UomEntity> uomlist){
        super();
        
        this.uomlist = uomlist;
    }

    @Override
    public int getRowCount() {
        return uomlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        UomEntity uom = uomlist.get(rowIndex);
        
        String parent = "";
        
        if(uom.getParent() != null){
            parent = uom.getParent().getName();
        }
        
        Object[] values = new Object[]{
            uom.getName(),
            uom.getFormattedValue(),
            parent,
            uom.isStatus()
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
    
    public List<UomEntity> getUomList(){
        return uomlist;
    }
    
    public void setUomList(ArrayList<UomEntity> uomlist){
        this.uomlist = uomlist;
        fireTableDataChanged();
    }
    
    public UomEntity getUom(int index){
        return uomlist.get(index);
    }

    public void addRow(UomEntity uom) {
        int rowCount = getRowCount();

        uomlist.add(uom);
        fireTableRowsInserted(rowCount, rowCount);
    }
}