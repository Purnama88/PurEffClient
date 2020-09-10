/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.ItemExpensesEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ItemExpensesTableModel extends AbstractTableModel {
    
    private ArrayList<ItemExpensesEntity> itemexpenseslist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NO"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DESCRIPTION"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_QUANTITY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PRICE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DISCOUNT"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PERCENTAGE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_TOTAL")
    };
    
    public ItemExpensesTableModel(
            ArrayList<ItemExpensesEntity> itemexpenseslist){
        super();
        this.itemexpenseslist = itemexpenseslist;
    }
    
    @Override
    public int getRowCount() {
        return itemexpenseslist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemExpensesEntity iis = itemexpenseslist.get(rowIndex);

        Object[] values = new Object[]{
            String.valueOf(rowIndex + 1), 
            iis.getDescription(),
            iis.getFormattedquantity(), 
            iis.getFormattedPrice(),
            iis.getFormattedDiscount(),
            "(" + iis.getFormattedDiscount_percentage() + "%)",
            iis.getFormattedtotal()
        };
        return values[columnIndex];
    }

    public ArrayList<ItemExpensesEntity> getItemExpensesList(){
        return itemexpenseslist;
    }
    
    public void setItemExpensesList(ArrayList<ItemExpensesEntity> itemexpenseslist){
        this.itemexpenseslist = itemexpenseslist;
        fireTableDataChanged();
    }
    
    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}
