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
import net.purnama.controller.ItemDeliveryDraftController;
import net.purnama.model.transactional.draft.DeliveryDraftEntity;
import net.purnama.model.transactional.draft.ItemDeliveryDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.ItemDeliveryDraftTableModel;

/**
 *
 * @author Purnama
 */
public class ItemDeliveryDraftTablePanel extends TablePanel{
    
    private final ItemDeliveryDraftController itemdeliverydraftController;
    
    private final ItemDeliveryDraftTableModel itemdeliverydrafttablemodel;
    
    private ArrayList<ItemDeliveryDraftEntity> list;
    
    private final String deliverydraftid;
    
    private final DeliveryDraftEntity deliverydraft;
    
    public ItemDeliveryDraftTablePanel(String deliverydraftid){
        itemdeliverydraftController = new ItemDeliveryDraftController();
        
        this.deliverydraftid = deliverydraftid;
        
        deliverydraft = new DeliveryDraftEntity();
        deliverydraft.setId(deliverydraftid);
        
        list = new ArrayList<>();
        
        itemdeliverydrafttablemodel = new ItemDeliveryDraftTableModel(list, deliverydraft);
        
        table.setModel(itemdeliverydrafttablemodel);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(25);
        
        menuitemdelete.addActionListener((ActionEvent e) -> {
            itemdeliverydrafttablemodel.deleteRow(table.getSelectedRow());
        });
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getItemDeliveryDraftList?id="+deliverydraftid);
                
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
                        list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        ItemDeliveryDraftEntity.class));
                        
                        itemdeliverydrafttablemodel.setItemDeliveryDraftList(list);
                        itemdeliverydrafttablemodel.
                            addRow(itemdeliverydraftController.createEmptyItemDeliveryDraft(deliverydraft));
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void submitItemDeliveryDraftList(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.post("saveItemDeliveryDraftList", 
                        itemdeliverydrafttablemodel.getItemDeliveryDraftList());
                
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
                        list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        ItemDeliveryDraftEntity.class));
                        
                        itemdeliverydrafttablemodel.setItemDeliveryDraftList(list);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void submitDeletedItemDeliveryDraftList(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.post("deleteItemDeliveryDraftList",
                        itemdeliverydrafttablemodel.getDeletedItemDeliveryDraftList());
                
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
