/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.form;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import net.purnama.gui.library.MyPanel;
import net.purnama.model.ItemEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ItemEdit extends ItemAdd{
    private final String id;
    private ItemEntity item;
    
    public ItemEdit(String id){
        super();
        setName(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_ITEMEDIT"));
        
        this.id = id;
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getItem?id=" + id);
                
                return true;
                
            }
            
            @Override
            protected void process(List<String> chunks) {
                for (String number : chunks) {
                upperpanel.setNotifLabel(number);
                }
            }
            
            @Override
            protected void done() {
                upperpanel.hideProgressBar();
                
                if(response == null){
                    upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                }
                else if(response.getStatus() != 200) {
                    upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                    + response.getStatus());
                }
                else{
                    upperpanel.setNotifLabel("");
                    
                    String output = response.getEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        item = mapper.readValue(output, ItemEntity.class);
                        
                        namepanel.setTextFieldValue(item.getName());
                        codepanel.setTextFieldValue(item.getCode());
                        itemgrouppanel.setComboBoxValue(item.getItemgroup());
                        costpanel.setTextFieldValue(item.getCost());
                        buyuompanel.setComboBoxValue(item.getBuyuom());
                        selluompanel.setComboBoxValue(item.getSelluom());
                        statuspanel.setSelectedValue(item.isStatus());
                        notepanel.setTextAreaValue(item.getNote());
                        
                        codepanel.setTextFieldEditable(false);
                        
                        
                        upperpanel.setStatusLabel(item.getFormattedLastmodified());
                    
                        
                        setState(MyPanel.SAVED);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    @Override
    protected void submit() {
        if(validateinput()){
            SwingWorker<Boolean, String> submitworker = new SwingWorker<Boolean, String>() {
            
                ClientResponse clientresponse;
                
                @Override
                protected Boolean doInBackground(){
                
                    submitpanel.loading();
                    
                    item.setCode(codepanel.getTextFieldValue());
                    item.setName(namepanel.getTextFieldValue());
                    item.setItemgroup(itemgrouppanel.getComboBoxValue());
                    item.setCost(costpanel.getTextFieldValue());
                    item.setStatus(statuspanel.getSelectedValue());
                    item.setNote(notepanel.getTextAreaValue());
                    item.setBuyuom(buyuompanel.getComboBoxValue());
                    item.setSelluom(selluompanel.getComboBoxValue());
                    
                    clientresponse = RestClient.put("updateItem", item);
                    
                    return true;
                }
                
                
                @Override
                protected void done() {
                    submitpanel.finish();
                    
                    if(clientresponse == null){
                        upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                    }
                    else if(clientresponse.getStatus() != 200) {
                        upperpanel.setNotifLabel("");
                        
                        String output = clientresponse.getEntity(String.class);
                        
                        JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                        output, GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                        + clientresponse.getStatus(), 
                        JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        String output = clientresponse.getEntity(String.class);

                        ObjectMapper mapper = new ObjectMapper();
                        
                        try{
                            ItemEntity item = mapper.readValue(output, ItemEntity.class);
                            detail(item.getId());
                        }
                        catch(IOException e){

                        }
                    }
                }
            };
            
            submitworker.execute();
        }
    }
}
