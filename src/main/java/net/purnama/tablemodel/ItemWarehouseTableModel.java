/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.ItemEntity;
import net.purnama.model.ItemWarehouseEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ItemWarehouseTableModel extends AbstractTableModel{
    private ArrayList<ItemWarehouseEntity> itemwarehouselist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CODE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NAME"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_GROUP"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STOCK"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")
    };
    
    public ItemWarehouseTableModel(ArrayList<ItemWarehouseEntity> itemwarehouselist){
        super();
        
        this.itemwarehouselist = itemwarehouselist;
    }
    
    @Override
    public int getRowCount() {
        return itemwarehouselist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemWarehouseEntity iw = itemwarehouselist.get(rowIndex);
        
        Object[] values = new Object[]{
            iw.getItem().getCode(),
            iw.getItem().getName(),
            iw.getItem().getItemgroup().getName(),
            String.valueOf(iw.getStock()),
            iw.getItem().isStatus()
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
    
    public ArrayList<ItemWarehouseEntity> getItemWarehouseList(){
        return itemwarehouselist;
    }
    
    public void setItemWarehouseList(ArrayList<ItemWarehouseEntity> itemwarehouselist){
        this.itemwarehouselist = itemwarehouselist;
        fireTableDataChanged();
    }
    
    public ItemWarehouseEntity getItemWarehouse(int index){
        return itemwarehouselist.get(index);
    }
    
    public ItemEntity getItem(int index){
        return itemwarehouselist.get(index).getItem();
    }
}
