/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.form;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import net.purnama.gui.library.MyPanel;
import net.purnama.model.UomEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class UomEdit extends UomAdd{
    private UomEntity uom;
    
    private String id;
    
    public UomEdit(String id){
        super();
        
        this.id = id;
        
        setName(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_UOMEDIT"));
        
        load();
        
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getUom?id=" + id);
                
                return true;
                
            }
            
            @Override
            protected void done() {
                if(response == null){
                    upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                }
                else if(response.getStatus() != 200) {
                    upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                    + response.getStatus());
                }
                else{
                    String output = response.getEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        uom = mapper.readValue(output, UomEntity.class);
                        
                        namepanel.setTextFieldValue(uom.getName());
                        valuepanel.setTextFieldValue(uom.getValue());
                        statuspanel.setSelectedValue(uom.isStatus());
                        notepanel.setTextAreaValue(uom.getNote());
                        
                        
                        upperpanel.setStatusLabel(uom.getFormattedLastmodified());
                    
                        
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
                    
                    uom.setName(namepanel.getTextFieldValue());
                    uom.setNote(notepanel.getTextAreaValue());
                    uom.setParent(null);
                    uom.setStatus(statuspanel.getSelectedValue());
                    uom.setValue(valuepanel.getTextFieldValue());

                    clientresponse = RestClient.put("updateUom", uom);

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
                        String output = clientresponse.getEntity(String.class);

                        ObjectMapper mapper = new ObjectMapper();
                        
                        try{
                            UomEntity uom = mapper.readValue(output, UomEntity.class);
                            detail(uom.getId());
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
