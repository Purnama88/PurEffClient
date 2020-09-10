/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.DeliveryEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class DeliveryTableModel extends AbstractTableModel{
    
    private ArrayList<DeliveryEntity> deliverylist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NUMBER"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_WAREHOUSE"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DESTINATION"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")};
    
    public DeliveryTableModel(ArrayList<DeliveryEntity> deliverylist){
        super();
        
        this.deliverylist = deliverylist;
    }
    
    @Override
    public int getRowCount() {
        return deliverylist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DeliveryEntity delivery = deliverylist.get(rowIndex);
        
        Object[] values = new Object[]{
            delivery.getNumber(),
            delivery.getFormatteddate(), 
            delivery.getWarehouse_code(),
            delivery.getDestination(),
            delivery.getFormattedstatus()
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
    
    public void setDeliveryList(ArrayList<DeliveryEntity> deliverylist){
        this.deliverylist = deliverylist;
        fireTableDataChanged();
    }
    
    public DeliveryEntity getDelivery(int index){
        return deliverylist.get(index);
    }
}
