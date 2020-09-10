/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.ItemReturnSalesEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ItemReturnSalesTableModel extends AbstractTableModel {
    
    private ArrayList<ItemReturnSalesEntity> itemreturnsaleslist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NO"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CODE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NAME"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_QUANTITY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_UOM"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PRICE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DISCOUNT"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PERCENTAGE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_TOTAL"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_REFERENCE")
    };
    
    public ItemReturnSalesTableModel(
            ArrayList<ItemReturnSalesEntity> itemreturnsaleslist){
        super();
        this.itemreturnsaleslist = itemreturnsaleslist;
    }
    
    @Override
    public int getRowCount() {
        return itemreturnsaleslist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemReturnSalesEntity iis = itemreturnsaleslist.get(rowIndex);

        Object[] values = new Object[]{
            String.valueOf(rowIndex + 1), 
            iis.getItem_code(),
            iis.getItem_name(),
            iis.getFormattedquantity(), 
            iis.getUom_name(),
            iis.getFormattedPrice(),
            iis.getFormattedDiscount(),
            "(" + iis.getFormattedDiscount_percentage() + "%)",
            iis.getFormattedtotal(),
            iis.getInvoice_ref()
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
    
    public List<ItemReturnSalesEntity> getItemReturnSalesList(){
        return itemreturnsaleslist;
    }
    
    public void setItemReturnSalesList(ArrayList<ItemReturnSalesEntity> itemreturnsaleslist){
        this.itemreturnsaleslist = itemreturnsaleslist;
        fireTableDataChanged();
    }
    
    public ItemReturnSalesEntity getItemReturnSales(int index){
        return itemreturnsaleslist.get(index);
    }
}
