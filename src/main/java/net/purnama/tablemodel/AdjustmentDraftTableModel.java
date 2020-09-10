/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.draft.AdjustmentDraftEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class AdjustmentDraftTableModel extends AbstractTableModel{
    
    private ArrayList<AdjustmentDraftEntity> adjustmentdraftlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_ID"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_WAREHOUSE")
    };
    
    public AdjustmentDraftTableModel(ArrayList<AdjustmentDraftEntity> adjustmentdraftlist){
        super();
        
        this.adjustmentdraftlist = adjustmentdraftlist;
    }
    
    @Override
    public int getRowCount() {
        return adjustmentdraftlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        AdjustmentDraftEntity adjustment = adjustmentdraftlist.get(rowIndex);
        
        Object[] values = new Object[]{
            adjustment.getId(),
            adjustment.getFormattedDate(), 
            adjustment.getWarehouse().getName(),
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
    
    public void setAdjustmentDraftList(ArrayList<AdjustmentDraftEntity> adjustmentdraftlist){
        this.adjustmentdraftlist = adjustmentdraftlist;
        fireTableDataChanged();
    }
    
    public ArrayList<AdjustmentDraftEntity> getAdjustmentDraftList(){
        return adjustmentdraftlist;
    }
    
    public AdjustmentDraftEntity getAdjustmentDraft(int index){
        return adjustmentdraftlist.get(index);
    }
}
