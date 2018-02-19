/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.ItemEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author purnama
 */
public class ItemTableModel extends AbstractTableModel{
    private ArrayList<ItemEntity> itemlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CODE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NAME"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_GROUP"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_BUYUOM"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_SELLUOM"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_COST"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")
    };
    
    public ItemTableModel(ArrayList<ItemEntity> itemlist){
        super();
        
        this.itemlist = itemlist;
    }
    
    @Override
    public int getRowCount() {
        return itemlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemEntity i = itemlist.get(rowIndex);
        
        Object[] values = new Object[]{
            i.getCode(),
            i.getName(),
            i.getItemgroup().getCode(),
            i.getBuyuom().getName(),
            i.getSelluom().getName(),
            i.getFormattedCost(),
            i.isStatus()
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
    
    public ArrayList<ItemEntity> getItemList(){
        return itemlist;
    }
    
    public void setItemList(ArrayList<ItemEntity> itemwarehouselist){
        this.itemlist = itemwarehouselist;
        fireTableDataChanged();
    }
    
    public ItemEntity getItem(int index){
        return itemlist.get(index);
    }
}
