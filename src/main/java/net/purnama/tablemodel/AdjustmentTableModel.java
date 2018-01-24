/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.AdjustmentEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class AdjustmentTableModel extends AbstractTableModel{
    
    private ArrayList<AdjustmentEntity> adjustmentlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NUMBER"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_WAREHOUSE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")
    };
    
    public AdjustmentTableModel(ArrayList<AdjustmentEntity> adjustmentlist){
        super();
        
        this.adjustmentlist = adjustmentlist;
    }
    
    @Override
    public int getRowCount() {
        return adjustmentlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AdjustmentEntity adjustment = adjustmentlist.get(rowIndex);
        
        Object[] values = new Object[]{
            adjustment.getNumber(),
            adjustment.getFormatteddate(), 
            adjustment.getWarehouse_code(),
            adjustment.getFormattedstatus()
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
    
    public void setAdjustmentList(ArrayList<AdjustmentEntity> adjustmentlist){
        this.adjustmentlist = adjustmentlist;
        fireTableDataChanged();
    }
    
    public AdjustmentEntity getAdjustment(int index){
        return adjustmentlist.get(index);
    }
}
