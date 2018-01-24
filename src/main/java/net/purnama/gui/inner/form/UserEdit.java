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
import net.purnama.gui.inner.form.util.ResetPasswordPanel;
import net.purnama.gui.library.MyPanel;
import net.purnama.model.UserEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class UserEdit extends UserAdd{
    
    private UserEntity user;
    
    private final String id;
    
    private final ResetPasswordPanel resetpasswordpanel;
    
    public UserEdit(String id){
        super();
        this.id = id;
        setName(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_USEREDIT"));
        
        detailpanel.remove(passwordpanel);
        detailpanel.remove(confirmpanel);
        
        resetpasswordpanel = new ResetPasswordPanel(id);
        
        detailpanel.add(resetpasswordpanel, 3);
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getUser?id=" + id);
                
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
                        user = mapper.readValue(output, UserEntity.class);
                        
                        namepanel.setTextFieldValue(user.getName());
                        usernamepanel.setTextFieldValue(user.getUsername());
                        rolepanel.setComboBoxValue(user.getRole());
                        discountpanel.setTextFieldValue(user.getDiscount());
                        statuspanel.setSelectedValue(user.isStatus());
                        forwardpanel.setSelectedValue(user.isDateforward());
                        backwardpanel.setSelectedValue(user.isDatebackward());
                        raise_buypricepanel.setSelectedValue(user.isRaise_buyprice());
                        lower_buypricepanel.setSelectedValue(user.isLower_buyprice());
                        raise_sellpricepanel.setSelectedValue(user.isRaise_sellprice());
                        lower_sellpricepanel.setSelectedValue(user.isLower_sellprice());
                        notepanel.setTextAreaValue(user.getNote());
                        warehousepanel.setCheckBoxValues(user.getWarehouses());
                        
                        upperpanel.setStatusLabel(user.getFormattedLastmodified());
                    
                        usernamepanel.setTextFieldEditable(false);
                        
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
    protected boolean validateinput() {
        boolean status = GlobalFields.SUCCESS;
        
        if(namepanel.isTextFieldEmpty()){
            status = GlobalFields.FAIL;
            namepanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_EMPTY"));
        }
        else{
            namepanel.setErrorLabel("");
        }
        
        if(rolepanel.getComboBoxValue() == null){
            status = GlobalFields.FAIL;
            rolepanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_COMBOBOXEMPTY"));
        }
        else{
            rolepanel.setErrorLabel("");
        }
        
        return status;
    }
    
    @Override
    protected void submit() {
        if(validateinput()){
            SwingWorker<Boolean, String> submitworker = new SwingWorker<Boolean, String>() {
            
                ClientResponse clientresponse;
                
                @Override
                protected Boolean doInBackground(){
                
                    submitpanel.loading();
                    
                    user.setName(namepanel.getTextFieldValue());
                    user.setRole(rolepanel.getComboBoxValue());
                    user.setDiscount(discountpanel.getTextFieldValue());
                    user.setDateforward(forwardpanel.getSelectedValue());
                    user.setDatebackward(backwardpanel.getSelectedValue());
                    user.setRaise_buyprice(raise_buypricepanel.getSelectedValue());
                    user.setLower_buyprice(lower_buypricepanel.getSelectedValue());
                    user.setRaise_sellprice(raise_sellpricepanel.getSelectedValue());
                    user.setLower_sellprice(lower_sellpricepanel.getSelectedValue());
                    user.setStatus(statuspanel.getSelectedValue());
                    user.setNote(notepanel.getTextAreaValue());
                    user.setWarehouses(warehousepanel.getCheckBoxValues());
                    
                    clientresponse = RestClient.put("updateUser", user);
                    
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
                            UserEntity user = mapper.readValue(output, UserEntity.class);
                            detail(user.getId());
                        }
                        catch(IOException e){
                            e.printStackTrace();
                        }
                    }
                }
            };
            
            submitworker.execute();
        }
    }
}