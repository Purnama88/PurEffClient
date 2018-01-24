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
import net.purnama.model.PartnerEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PartnerEdit extends PartnerAdd{
    
    private final String id;
    
    private PartnerEntity partner;
    
    public PartnerEdit(String id){
        super();
        
        setName(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_PARTNEREDIT"));
        
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
                
                response = RestClient.get("getPartner?id=" + id);
                
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
                        partner = mapper.readValue(output, PartnerEntity.class);
                        
                        namepanel.setTextFieldValue(partner.getName());
                        codepanel.setTextFieldValue(partner.getCode());
                        contactnamepanel.setTextFieldValue(partner.getContactname());
                        partnertypepanel.setComboBoxValue(partner.getPartnertype());
                        addresspanel.setTextAreaValue(partner.getAddress());
                        phonenumberpanel.setTextFieldValue(partner.getPhonenumber());
                        phonenumber2panel.setTextFieldValue(partner.getPhonenumber2());
                        faxnumberpanel.setTextFieldValue(partner.getFaxnumber());
                        emailpanel.setTextFieldValue(partner.getEmail());
                        mobilenumberpanel.setTextFieldValue(partner.getMobilenumber());
                        maxdiscountpanel.setTextFieldValue(partner.getMaximumdiscount());
                        maxbalancepanel.setTextFieldValue(partner.getMaximumbalance());
                        paymentduepanel.setTextFieldValue(partner.getPaymentdue());
                        statuspanel.setSelectedValue(partner.isStatus());
                        notepanel.setTextAreaValue(partner.getNote());
                        
                        upperpanel.setStatusLabel(partner.getFormattedLastmodified());
                        
                        codepanel.setTextFieldEditable(false);
                        
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
            SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
                
                ClientResponse clientresponse;
                
                @Override
                protected Boolean doInBackground(){
                
                    submitpanel.loading();
                    
                    partner.setCode(codepanel.getTextFieldValue());
                    partner.setName(namepanel.getTextFieldValue());
                    partner.setContactname(contactnamepanel.getTextFieldValue());
                    partner.setPartnertype(partnertypepanel.getComboBoxValue());
                    partner.setAddress(addresspanel.getTextAreaValue());
                    partner.setPhonenumber(phonenumberpanel.getTextFieldValue());
                    partner.setPhonenumber2(phonenumber2panel.getTextFieldValue());
                    partner.setFaxnumber(faxnumberpanel.getTextFieldValue());
                    partner.setEmail(emailpanel.getTextFieldValue());
                    partner.setMobilenumber(mobilenumberpanel.getTextFieldValue());
                    partner.setMaximumdiscount(maxdiscountpanel.getTextFieldValue());
                    partner.setMaximumbalance(maxbalancepanel.getTextFieldValue());
                    partner.setPaymentdue((int) paymentduepanel.getTextFieldValue());
                    partner.setStatus(statuspanel.getSelectedValue());
                    partner.setNote(notepanel.getTextAreaValue());
                    
                    clientresponse = RestClient.put("updatePartner", partner);
                    
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
                            PartnerEntity partner = mapper.readValue(output, PartnerEntity.class);
                            detail(partner.getId());
                        }
                        catch(IOException e){

                        }
                    }
                }
            };
            
            worker.execute();
        }
    }
}

