/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.MenuEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class MenuTableModel extends AbstractTableModel{
    private ArrayList<MenuEntity> menulist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NAME"),
        //GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NUMBER"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_TRANSACTIONAL") };
    
    public MenuTableModel(ArrayList<MenuEntity> menulist){
        super();
        this.menulist = menulist;
    }

    @Override
    public int getRowCount() {
        return menulist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String num = "";
        
        MenuEntity menu = menulist.get(rowIndex);
        
//        if(menu.getNumbering() != null){
//            num = menu.getNumbering().getCurrentId();
//        }
        
        Object[] values = new Object[]{
            menu.getName(), 
            //num,
            menu.isTransactional()
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
    
    public void setMenuList(ArrayList<MenuEntity> menulist){
        this.menulist = menulist;
        fireTableDataChanged();
    }
    
    public MenuEntity getMenu(int index){
        return menulist.get(index);
    }
}