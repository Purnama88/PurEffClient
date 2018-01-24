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
import net.purnama.model.WarehouseEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class WarehouseEdit extends WarehouseAdd{
    
    private WarehouseEntity warehouse;
    
    private final String id;
    
    public WarehouseEdit(String id){
        super();
        setName(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_WAREHOUSEEDIT"));
        
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
                
                response = RestClient.get("getWarehouse?id=" + id);
                
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
                        warehouse = mapper.readValue(output, WarehouseEntity.class);
                        
                        namepanel.setTextFieldValue(warehouse.getName());
                        codepanel.setTextFieldValue(warehouse.getCode());
                        addresspanel.setTextAreaValue(warehouse.getAddress());
                        urlpanel.setTextFieldValue(warehouse.getUrl());
                        portpanel.setTextFieldValue(warehouse.getPort());
                        statuspanel.setSelectedValue(warehouse.isStatus());
                        notepanel.setTextAreaValue(warehouse.getNote());
                        
                        codepanel.setTextFieldEditable(false);
                        
                        upperpanel.setStatusLabel(warehouse.getFormattedLastmodified());
                        
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
                    
                    warehouse.setAddress(addresspanel.getTextAreaValue());
                    warehouse.setCode(codepanel.getTextFieldValue());
                    warehouse.setDefaultwarehouse(false);
                    warehouse.setName(namepanel.getTextFieldValue());
                    warehouse.setNote(notepanel.getTextAreaValue());
                    warehouse.setPort(portpanel.getTextFieldValue());
                    warehouse.setStatus(statuspanel.getSelectedValue());
                    warehouse.setUrl(urlpanel.getTextFieldValue());

                    clientresponse = RestClient.put("updateWarehouse", warehouse);

                    return true;
                }
                
                @Override
                protected void done() {
                    submitpanel.finish();
                    
                    if(clientresponse == null){
                        upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                    }
                    else if(clientresponse.getStatus() != 200) {
                        String output = clientresponse.getEntity(String.class);
                        
                        JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                        output, GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                        + clientresponse.getStatus(), 
                        JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        home();
                    }
                }
            };
            
            submitworker.execute();
        }
    }
}
