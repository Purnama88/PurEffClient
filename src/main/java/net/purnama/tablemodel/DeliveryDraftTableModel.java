/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.tablemodel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.transactional.draft.DeliveryDraftEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class DeliveryDraftTableModel extends AbstractTableModel{
    
    private ArrayList<DeliveryDraftEntity> deliverydraftlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_ID"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DATE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_WAREHOUSE"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DESTINATION")};
    
    public DeliveryDraftTableModel(ArrayList<DeliveryDraftEntity> deliverydraftlist){
        super();
        
        this.deliverydraftlist = deliverydraftlist;
    }
    
    @Override
    public int getRowCount() {
        return deliverydraftlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        DeliveryDraftEntity delivery = deliverydraftlist.get(rowIndex);
        
        Object[] values = new Object[]{
            delivery.getId(),
            delivery.getFormattedDate(), 
            delivery.getWarehouse().getName(),
            delivery.getDestination(),
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
    
    public void setDeliveryDraftList(ArrayList<DeliveryDraftEntity> deliverydraftlist){
        this.deliverydraftlist = deliverydraftlist;
        fireTableDataChanged();
    }
    
    public ArrayList<DeliveryDraftEntity> getDeliveryDraftList(){
        return deliverydraftlist;
    }
    
    public DeliveryDraftEntity getDeliveryDraft(int index){
        return deliverydraftlist.get(index);
    }
}
