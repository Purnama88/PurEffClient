/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.tablemodel;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;
import javax.swing.table.AbstractTableModel;
import net.purnama.controller.ItemAdjustmentDraftController;
import net.purnama.model.ItemEntity;
import net.purnama.model.ItemWarehouseEntity;
import net.purnama.model.transactional.draft.AdjustmentDraftEntity;
import net.purnama.model.transactional.draft.ItemAdjustmentDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;

/**
 *
 * @author Purnama
 */
public class ItemAdjustmentDraftTableModel extends AbstractTableModel{
    
    private final ItemAdjustmentDraftController itemadjustmentdraftController;
    private final AdjustmentDraftEntity adjustmentdraft;
    private ArrayList<ItemAdjustmentDraftEntity> itemadjustmentdraftlist;
    private final ArrayList<ItemAdjustmentDraftEntity> deleteditemadjustmentdraftlist;
    
    String[] columnNames = new String[]{
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NO"), 
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DESCRIPTION"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_STOCK"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_DIFFERENCE"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_NEWQUANTITY"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TABLE_REMARK")
    };
    
    public ItemAdjustmentDraftTableModel(ArrayList<ItemAdjustmentDraftEntity> itemadjustmentdraftlist,
            AdjustmentDraftEntity adjustmentdraft){
        super();
        itemadjustmentdraftController = new ItemAdjustmentDraftController();
        deleteditemadjustmentdraftlist = new ArrayList<>();
        this.itemadjustmentdraftlist = itemadjustmentdraftlist;
        this.adjustmentdraft = adjustmentdraft;
    }
    
    @Override
    public int getRowCount() {
        return itemadjustmentdraftlist.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }
    
    @Override
    public void setValueAt(Object value, int row, int col){
        ItemAdjustmentDraftEntity ia = itemadjustmentdraftlist.get(row);
        if(col == 1){
            ItemEntity item = (ItemEntity)value;
            
            if(item == null){
                
            }
            else{
                ia.setItem(item);

                SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
                        ClientResponse response;

                    @Override
                    protected Boolean doInBackground(){

                        response = RestClient.get("getItemWarehouse?itemid=" + item.getId());

                        return true;
                    }

                    @Override
                    protected void done() {
                        if(response == null){
                        }
                        else if(response.getStatus() != 200) {
                        }
                        else{
                            String output = response.getEntity(String.class);

                            ObjectMapper mapper = new ObjectMapper();

                            try{
                                ItemWarehouseEntity itemwarehouse = mapper.
                                        readValue(output, ItemWarehouseEntity.class);

                                ia.setTstock(itemwarehouse.getStock());
                            }
                            catch(IOException e){
                                ia.setTstock(0); 
                            }
                        }

                        fireTableCellUpdated(row, 2);
                        fireTableCellUpdated(row, 4);
                    }
                };
                worker.execute();
            }
        }
//        else if(col == 3){
//            try{
//                double d = GlobalFunctions.convertToDouble(value.toString());
//                double c = GlobalFunctions.convertToDouble((String)getValueAt(row, 2));
//                ia.setQuantity(d-c);
//                fireTableCellUpdated(row, 4);
//            }
//            catch(Exception exp){
//                exp.printStackTrace();
//            }
//        }
        else if(col == 4){
            double d = GlobalFunctions.convertToDouble(value.toString());
            ia.setQuantity(d);
            fireTableCellUpdated(row, 3);
        }
        else if(col == 5){
            ia.setRemark(value.toString());
        }
        
        if(row+1 == getRowCount()){
            itemadjustmentdraftlist.add(itemadjustmentdraftController.
                    createEmptyItemAdjustmentDraft(adjustmentdraft));
        }
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ItemAdjustmentDraftEntity id = itemadjustmentdraftlist.get(rowIndex);
        
        String description = "";
        
        if(id.getItem() != null){
            description = id.getItem().getCode() + " - " + id.getItem().getName();
        }
        
        Object[] values = new Object[]{
            String.valueOf(rowIndex + 1), 
            description,
            id.getFormattedTstock(),
            id.getFormattedDiff(),
            id.getFormattedQuantity(),
            id.getRemark()
            };
        return values[columnIndex];
    }
    
    @Override
    public String getColumnName(int column)
    {
        return columnNames[column];
    }
    
    @Override
    public boolean isCellEditable(int row, int col){
        if(getItemAdjustmentDraftList().get(row) != null){
            return col == 1 || col == 4 || col == 5;
        }
        else{
            return col == 1;
        }
    }
    
    @Override
    public Class getColumnClass(int c) {  
        return getValueAt(0, c).getClass();  
    }
    
    public void addRow(ItemAdjustmentDraftEntity id) {
        itemadjustmentdraftlist.add(id);
    }
    
    public void deleteRow(int rownum){
          
        ItemAdjustmentDraftEntity id = itemadjustmentdraftlist.get(rownum);
        
        deleteditemadjustmentdraftlist.add(id);
        
        itemadjustmentdraftlist.remove(rownum);
        fireTableRowsDeleted(rownum, rownum);
    }
    
    public void setItemAdjustmentDraftList(ArrayList<ItemAdjustmentDraftEntity> itemadjustmentdraftlist){
        this.itemadjustmentdraftlist = itemadjustmentdraftlist;
        fireTableDataChanged();
    }
    
    public ItemAdjustmentDraftEntity getItemAdjustment(int index){
        return itemadjustmentdraftlist.get(index);
    }
    
    public List<ItemAdjustmentDraftEntity> getItemAdjustmentDraftList(){
        return itemadjustmentdraftlist;
    }
    
    public List<ItemAdjustmentDraftEntity> getDeletedItemAdjustmentDraftList(){
        return deleteditemadjustmentdraftlist;
    }
    
    public boolean isEmpty(){
        boolean status = true;
        
        for(ItemAdjustmentDraftEntity iis : itemadjustmentdraftlist){
            if(iis.getItem() != null){
                status = false;
            }
        }
        
        return status;
    }
}
