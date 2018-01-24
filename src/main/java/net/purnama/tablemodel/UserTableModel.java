/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.UserEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class UserTableModel extends AbstractTableModel{
    private ArrayList<UserEntity> userlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_USERNAME"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NAME"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_ROLE"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")
    };
    
    public UserTableModel(ArrayList<UserEntity> userlist){
        super();
        
        this.userlist = userlist;
    }

    @Override
    public int getRowCount() {
        return userlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        UserEntity u = userlist.get(rowIndex);
        
        Object[] values = new Object[]{
            u.getUsername(),
            u.getName(),
            u.getRole().getName(),
            u.isStatus()
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
    
    public List<UserEntity> getUserList(){
        return userlist;
    }
    
    public void setUserList(ArrayList<UserEntity> userlist){
        this.userlist = userlist;
        fireTableDataChanged();
    }
    
    public UserEntity getUser(int index){
        return userlist.get(index);
    }
}