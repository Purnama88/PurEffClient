/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.PartnerTypeEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PartnerTypeTableModel extends AbstractTableModel {
    private ArrayList<PartnerTypeEntity> partnertypelist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NAME"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PARENT"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")
    };
    
    public PartnerTypeTableModel(ArrayList<PartnerTypeEntity> partnertypelist){
        super();
        
        this.partnertypelist = partnertypelist;
    }

    @Override
    public int getRowCount() {
        return partnertypelist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PartnerTypeEntity p = partnertypelist.get(rowIndex);
        
        Object[] values = new Object[]{
            p.getName(),
            PartnerTypeEntity.PARENT_TYPE[p.getParent()],
            p.isStatus()
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
    
    public List<PartnerTypeEntity> getPartnerTypeList(){
        return partnertypelist;
    }
    
    public void setPartnerTypeList(ArrayList<PartnerTypeEntity> partnertypelist){
        this.partnertypelist = partnertypelist;
        fireTableDataChanged();
    }
    
    public PartnerTypeEntity getPartnerType(int index){
        return partnertypelist.get(index);
    }
}
