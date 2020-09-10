/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.RoleEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class RoleTableModel extends AbstractTableModel{
    private ArrayList<RoleEntity> rolelist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NAME"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")
    };
    
    public RoleTableModel(ArrayList<RoleEntity> rolelist){
        super();
        
        this.rolelist = rolelist;
    }

    @Override
    public int getRowCount() {
        return rolelist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        RoleEntity role = rolelist.get(rowIndex);
        
        Object[] values = new Object[]{
            role.getName(),
            role.isStatus()};
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
    
    public List<RoleEntity> getRoleList(){
        return rolelist;
    }
    
    public void setRoleList(ArrayList<RoleEntity> rolelist){
        this.rolelist = rolelist;
        fireTableDataChanged();
    }
    
    public RoleEntity getRole(int index){
        return rolelist.get(index);
    }
}
