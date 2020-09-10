/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import com.sun.jersey.api.client.ClientResponse;
import java.util.ArrayList;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.SellPriceEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;

/**
 *
 * @author Purnama
 */
public class SellPriceTableModel extends AbstractTableModel{
    
    private ArrayList<SellPriceEntity> sellpricelist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_UOM"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PRICE"),
    };
    
    public SellPriceTableModel(ArrayList<SellPriceEntity> sellpricelist){
        super();
        
        this.sellpricelist = sellpricelist;
    }
    
    @Override
    public int getRowCount() {
        return sellpricelist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public void setValueAt(Object value, int row, int col){
        SellPriceEntity ia = sellpricelist.get(row);
        if(col == 1){
            double d = GlobalFunctions.convertToDouble(value.toString());
            ia.setValue(d);
            
            SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
                    ClientResponse response;

                @Override
                protected Boolean doInBackground(){

                    response = RestClient.put("updateSellPrice", ia);

                    return true;
                }
                
                @Override
                protected void done() {
                    if(response == null){
                    }
                    else if(response.getStatus() != 200) {
                    }
                    else{
                    }
                    
                    fireTableCellUpdated(row, 1);
                }
            };
            worker.execute();
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        SellPriceEntity sellprice = sellpricelist.get(rowIndex);
        
        Object[] values = new Object[]{
            sellprice.getUom().getName(),
            sellprice.getFormattedValue()
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
    
    public void setSellPriceList(ArrayList<SellPriceEntity> sellpricelist){
        this.sellpricelist = sellpricelist;
        fireTableDataChanged();
    }
    
    public SellPriceEntity getSellPrice(int index){
        return sellpricelist.get(index);
    }
    
    @Override
    public boolean isCellEditable(int row, int col){
        return col == 1;
    }
}

