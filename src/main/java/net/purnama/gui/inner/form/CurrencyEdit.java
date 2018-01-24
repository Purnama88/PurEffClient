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
import net.purnama.model.CurrencyEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class CurrencyEdit extends CurrencyAdd{
    
    private CurrencyEntity currency;
    
    private String id;
    
    public CurrencyEdit(String id){
        super();
        
        this.id = id;
        setName(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_CURRENCYEDIT"));
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
               response = RestClient.get("getCurrency?id=" + id);
                
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
                        currency = mapper.readValue(output, CurrencyEntity.class);
                        
                        codepanel.setTextFieldEditable(false);
                        
                        codepanel.setTextFieldValue(currency.getCode());
                        namepanel.setTextFieldValue(currency.getName());
                        descriptionpanel.setTextFieldValue(currency.getDescription());
                        statuspanel.setSelectedValue(currency.isStatus());
                        notepanel.setTextAreaValue(currency.getNote());
                        
                        
                        upperpanel.setStatusLabel(currency.getFormattedLastmodified());
                    
                        
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
                    
                    currency.setCode(codepanel.getTextFieldValue());
                    currency.setDefaultcurrency(false);
                    currency.setDescription(descriptionpanel.getTextFieldValue());
                    currency.setName(namepanel.getTextFieldValue());
                    currency.setNote(notepanel.getTextAreaValue());
                    currency.setStatus(statuspanel.getSelectedValue());

                    clientresponse = RestClient.put("updateCurrency", currency);
                    
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
                        detail(currency.getId());
                    }
                }
            };
            
            submitworker.execute();
            
        }
    }
}
