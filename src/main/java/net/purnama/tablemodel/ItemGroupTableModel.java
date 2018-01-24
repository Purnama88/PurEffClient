/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.ItemGroupEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ItemGroupTableModel extends AbstractTableModel{
    private ArrayList<ItemGroupEntity> itemgrouplist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CODE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NAME"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")
    };
    
    public ItemGroupTableModel(ArrayList<ItemGroupEntity> itemgrouplist){
        super();
        
        this.itemgrouplist = itemgrouplist;
    }
    
    @Override
    public int getRowCount() {
        return itemgrouplist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemGroupEntity ig = itemgrouplist.get(rowIndex);
        
        Object[] values = new Object[]{
            ig.getCode(), 
            ig.getName(), 
            ig.isStatus()
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
    
    public List<ItemGroupEntity> getItemGroupList(){
        return itemgrouplist;
    }
    
    public void setItemGroupList(ArrayList<ItemGroupEntity> itemgrouplist){
        this.itemgrouplist = itemgrouplist;
        fireTableDataChanged();
    }
    
    public ItemGroupEntity getItemGroup(int index){
        return itemgrouplist.get(index);
    }
}
