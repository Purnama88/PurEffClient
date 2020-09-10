/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.ItemInvoiceSalesEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ItemInvoiceSalesTableModel extends AbstractTableModel {
    
    private ArrayList<ItemInvoiceSalesEntity> iteminvoicesaleslist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NO"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CODE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NAME"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_QUANTITY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_UOM"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PRICE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DISCOUNT"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PERCENTAGE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_TOTAL")
    };
    
    public ItemInvoiceSalesTableModel(
            ArrayList<ItemInvoiceSalesEntity> iteminvoicesaleslist){
        super();
        this.iteminvoicesaleslist = iteminvoicesaleslist;
    }
    
    @Override
    public int getRowCount() {
        return iteminvoicesaleslist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemInvoiceSalesEntity iis = iteminvoicesaleslist.get(rowIndex);

        Object[] values = new Object[]{
            String.valueOf(rowIndex + 1), 
            iis.getItem_code(),
            iis.getItem_name(),
            iis.getFormattedquantity(), 
            iis.getUom_name(),
            iis.getFormattedPrice(),
            iis.getFormattedDiscount(),
            "(" + iis.getFormattedDiscount_percentage() + "%)",
            iis.getFormattedtotal()};
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
    
    public List<ItemInvoiceSalesEntity> getItemInvoiceSalesList(){
        return iteminvoicesaleslist;
    }
    
    public void setItemInvoiceSalesList(ArrayList<ItemInvoiceSalesEntity> iteminvoicesaleslist){
        this.iteminvoicesaleslist = iteminvoicesaleslist;
        fireTableDataChanged();
    }
}
