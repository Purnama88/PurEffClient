/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.ItemInvoiceWarehouseInEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ItemInvoiceWarehouseInTableModel extends AbstractTableModel {
    
    private ArrayList<ItemInvoiceWarehouseInEntity> iteminvoicewarehouseinlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NO"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CODE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NAME"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_QUANTITY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_UOM")
    };
    
    public ItemInvoiceWarehouseInTableModel(
            ArrayList<ItemInvoiceWarehouseInEntity> iteminvoicewarehouseinlist){
        super();
        this.iteminvoicewarehouseinlist = iteminvoicewarehouseinlist;
    }
    
    @Override
    public int getRowCount() {
        return iteminvoicewarehouseinlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemInvoiceWarehouseInEntity iis = iteminvoicewarehouseinlist.get(rowIndex);
        
        Object[] values = new Object[]{
            String.valueOf(rowIndex + 1), 
            iis.getItem_code(),
            iis.getItem_name(),
            iis.getFormattedquantity(), 
            iis.getUom_name()
        };
        return values[columnIndex];
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
    
    public ArrayList<ItemInvoiceWarehouseInEntity> getItemInvoiceWarehouseInList(){
        return iteminvoicewarehouseinlist;
    }
    
    public void setItemInvoiceWarehouseInList(ArrayList<ItemInvoiceWarehouseInEntity> iteminvoicewarehouseinlist){
        this.iteminvoicewarehouseinlist = iteminvoicewarehouseinlist;
        fireTableDataChanged();
    }
}
