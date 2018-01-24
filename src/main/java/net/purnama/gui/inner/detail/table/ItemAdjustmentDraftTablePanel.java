/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.table;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.SwingWorker;
import javax.swing.table.TableColumn;
import net.purnama.controller.ItemAdjustmentDraftController;
import net.purnama.gui.inner.detail.util.ItemTableCellEditor;
import net.purnama.model.transactional.draft.AdjustmentDraftEntity;
import net.purnama.model.transactional.draft.ItemAdjustmentDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.ItemAdjustmentDraftTableModel;

/**
 *
 * @author Purnama
 */
public class ItemAdjustmentDraftTablePanel extends TablePanel{
    private final ItemAdjustmentDraftController itemadjustmentdraftController;
    
    private final ItemAdjustmentDraftTableModel itemadjustmentdrafttablemodel;
    
    private ArrayList<ItemAdjustmentDraftEntity> list;
    
    private final ItemTableCellEditor itemcelleditor;
    
    private final String adjustmentdraftid;
    
    public ItemAdjustmentDraftTablePanel(String adjustmentdraftid){
        super();
        
        this.adjustmentdraftid = adjustmentdraftid;
        
        itemcelleditor = new ItemTableCellEditor();
        
        itemadjustmentdraftController = new ItemAdjustmentDraftController();
        
        AdjustmentDraftEntity adjustmentdraft = new AdjustmentDraftEntity();
        adjustmentdraft.setId(adjustmentdraftid);
        
        list = new ArrayList<>();
        
        itemadjustmentdrafttablemodel = new ItemAdjustmentDraftTableModel(list, adjustmentdraft);
        
        table.setModel(itemadjustmentdrafttablemodel);
        
        TableColumn column1 = table.getColumnModel().getColumn(1);
        column1.setCellEditor(itemcelleditor);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(25);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        
        menuitemdelete.addActionListener((ActionEvent e) -> {
            itemadjustmentdrafttablemodel.deleteRow(table.getSelectedRow());
        });
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getItemAdjustmentDraftList?id="+adjustmentdraftid);
                
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

                    try {
                        list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        ItemAdjustmentDraftEntity.class));
                        
                        itemadjustmentdrafttablemodel.setItemAdjustmentDraftList(list);
                        AdjustmentDraftEntity adjustmentdraft = new AdjustmentDraftEntity();
                        adjustmentdraft.setId(adjustmentdraftid);
                        
                        itemadjustmentdrafttablemodel.
                        addRow(itemadjustmentdraftController.createEmptyItemAdjustmentDraft(adjustmentdraft));
                    } 
                    catch (IOException ex) {
                        
                    }
                    
                }
            }
        };
        
        worker.execute();
    }
    
    public void submitItemAdjustmentDraftList(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.post("saveItemAdjustmentDraftList", 
                        itemadjustmentdrafttablemodel.getItemAdjustmentDraftList());
                
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

                    try {
                        list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        ItemAdjustmentDraftEntity.class));
                        
                        itemadjustmentdrafttablemodel.setItemAdjustmentDraftList(list);
                    } 
                    catch (IOException ex) {
                        
                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void submitDeletedItemAdjustmentDraftList(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.post("deleteItemAdjustmentDraftList",
                        itemadjustmentdrafttablemodel.getDeletedItemAdjustmentDraftList());
                
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
            }
        };
        
        worker.execute();
    }
}
