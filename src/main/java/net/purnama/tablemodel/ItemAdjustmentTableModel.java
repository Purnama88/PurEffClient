/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.ItemAdjustmentEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ItemAdjustmentTableModel extends AbstractTableModel{
    
    private ArrayList<ItemAdjustmentEntity> itemadjustmentlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NO"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CODE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NAME"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STOCK"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DIFFERENCE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NEWQUANTITY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_REMARK")
    };
    
    public ItemAdjustmentTableModel(ArrayList<ItemAdjustmentEntity> itemadjustmentlist){
        super();
        
        this.itemadjustmentlist = itemadjustmentlist;
    }
    
    @Override
    public int getRowCount() {
        return itemadjustmentlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemAdjustmentEntity id = itemadjustmentlist.get(rowIndex);
        
        Object[] values = new Object[]{
            String.valueOf(rowIndex + 1), 
            id.getItem_code(),
            id.getItem_name(),
            id.getFormattedtstock(),
            id.getFormattedDiff(),
            id.getFormattedquantity(),
            id.getRemark()
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
    
    public void setItemAdjustmentList(ArrayList<ItemAdjustmentEntity> itemadjustmentlist){
        this.itemadjustmentlist = itemadjustmentlist;
        fireTableDataChanged();
    }
    
    public ArrayList<ItemAdjustmentEntity> getItemAdjustmentList(){
        return itemadjustmentlist;
    }
    
    public ItemAdjustmentEntity getItemAdjustment(int index){
        return itemadjustmentlist.get(index);
    }
}
