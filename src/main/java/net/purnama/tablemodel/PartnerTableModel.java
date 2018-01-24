/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import net.purnama.model.PartnerEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PartnerTableModel extends AbstractTableModel {
    private ArrayList<PartnerEntity> partnerlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_CODE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NAME"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_TYPE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_MAXIMUM"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_BALANCE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STATUS")
    };
    
    public PartnerTableModel(ArrayList<PartnerEntity> partnerlist){
        super();
        
        this.partnerlist = partnerlist;
    }

    @Override
    public int getRowCount() {
        return partnerlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        PartnerEntity p = partnerlist.get(rowIndex);
        
        Object[] values = new Object[]{
            p.getCode(),
            p.getName(),
            p.getPartnertype().getName(),
            p.getFormattedMaximumbalance(),
            p.getFormattedBalance2(),
            p.isStatus()
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
    
    public List<PartnerEntity> getPartnerList(){
        return partnerlist;
    }
    
    public void setPartnerList(ArrayList<PartnerEntity> partnerlist){
        this.partnerlist = partnerlist;
        fireTableDataChanged();
    }
    
    public PartnerEntity getPartner(int index){
        return partnerlist.get(index);
    }
}
