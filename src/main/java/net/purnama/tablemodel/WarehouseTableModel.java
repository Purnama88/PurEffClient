/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.WarehouseEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class WarehouseTableModel extends AbstractTableModel{
    private ArrayList<WarehouseEntity> warehouselist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CODE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NAME"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")
    };
    
    public WarehouseTableModel(ArrayList<WarehouseEntity> warehouselist){
        super();
        
        this.warehouselist = warehouselist;
    }

    @Override
    public int getRowCount() {
        return warehouselist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        WarehouseEntity warehouse = warehouselist.get(rowIndex);
        
        Object[] values = new Object[]{
            warehouse.getCode(), 
            warehouse.getName(),
            warehouse.isStatus()
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
    
    public List<WarehouseEntity> getWarehouseList(){
        return warehouselist;
    }
    
    public void setWarehouseList(ArrayList<WarehouseEntity> warehouselist){
        this.warehouselist = warehouselist;
        fireTableDataChanged();
    }
    
    public WarehouseEntity getWarehouse(int index){
        return warehouselist.get(index);
    }
}
