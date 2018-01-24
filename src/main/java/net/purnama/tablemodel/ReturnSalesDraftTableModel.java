/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.draft.ReturnSalesDraftEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ReturnSalesDraftTableModel extends AbstractTableModel{
    
    private ArrayList<ReturnSalesDraftEntity> returnsalesdraftlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_ID"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_WAREHOUSE"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_PARTNER"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CURRENCY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_TOTAL")
    };
    
    public ReturnSalesDraftTableModel(ArrayList<ReturnSalesDraftEntity> returnsalesdraftlist){
        super();
        
        this.returnsalesdraftlist = returnsalesdraftlist;
    }
    
    @Override
    public int getRowCount() {
        return returnsalesdraftlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ReturnSalesDraftEntity is = returnsalesdraftlist.get(rowIndex);
        
        String partner;
        
        if(is.getPartner() != null){
            partner = is.getPartner().getName();
        }
        else{
            partner = "";
        }
        
        Object[] values = new Object[]{
            is.getId(),
            is.getFormattedDate(), 
            is.getWarehouse().getName(), 
            partner, 
            is.getCurrency().getCode(),
            is.getFormattedTotal_after_tax()
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
    
    public void setReturnSalesDraftList(ArrayList<ReturnSalesDraftEntity> returnsalesdraftlist){
        this.returnsalesdraftlist = returnsalesdraftlist;
        fireTableDataChanged();
    }
    
    public ArrayList<ReturnSalesDraftEntity> getReturnSalesDraftList(){
        return returnsalesdraftlist;
    }
    
    public ReturnSalesDraftEntity getReturnSalesDraft(int index){
        return returnsalesdraftlist.get(index);
    }
}
