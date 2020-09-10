/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.gui.dialog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.SwingWorker;
import net.purnama.model.UomEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class UomEditDialog extends UomAddDialog{

    public UomEditDialog(UomEntity parent, String id) {
        super(parent);
        
        this.setTitle(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_UOMEDIT"));
        
        parentpanel.setTextFieldValue(parent.getName());
        
        load(id);
    }
    
    public final void load(String id){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getUom?id=" + id);
                
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
                        uom = mapper.readValue(output, UomEntity.class);
                        
                        namepanel.setTextFieldValue(uom.getName());
                        valuepanel.setTextFieldValue(uom.getValue());
                        valuepanel.setTextFieldEditable(false);
                        notepanel.setTextAreaValue(uom.getNote());
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(validateinput()){
            SwingWorker<Boolean, String> submitworker = new SwingWorker<Boolean, String>() {
            
                ClientResponse response;
                
                @Override
                protected Boolean doInBackground(){
                    uom.setName(namepanel.getTextFieldValue());
                    uom.setNote(notepanel.getTextAreaValue());
                    uom.setParent(parent);
                    uom.setStatus(statuspanel.getSelectedValue());
                    uom.setValue(valuepanel.getTextFieldValue());
                    
                    response = RestClient.put("updateUom", uom);
                    
                    return true;
                }
                
                @Override
                protected void done() {
                    if(response == null){
                        namepanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                    }
                    else if(response.getStatus() != 200) {
                        String output = response.getEntity(String.class);
                        
                        namepanel.setErrorLabel(output);
                    }
                    else{
                        String output = response.getEntity(String.class);

                        ObjectMapper mapper = new ObjectMapper();

                        try{
                            uom = mapper.readValue(output, UomEntity.class);
                            dispose();
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
