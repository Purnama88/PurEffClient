/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.home.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import net.purnama.gui.inner.form.util.LabelPasswordFieldErrorPanel;
import net.purnama.gui.inner.form.util.SubmitPanel;
import net.purnama.model.UserEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ChangePasswordPanel extends JPanel{
    
    private final LabelPasswordFieldErrorPanel oldpasswordpanel, newpasswordpanel, confirmpanel;
    
    private final SubmitPanel submitpanel;
    
    public ChangePasswordPanel(){
        super();
        
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        oldpasswordpanel = new LabelPasswordFieldErrorPanel(GlobalFields.PROPERTIES.getProperty("LABEL_OLDPASSWORD"), "");
        newpasswordpanel = new LabelPasswordFieldErrorPanel(GlobalFields.PROPERTIES.getProperty("LABEL_NEWPASSWORD"), "");
        confirmpanel = new LabelPasswordFieldErrorPanel(GlobalFields.PROPERTIES.getProperty("LABEL_CONFIRM"), "");
        
        submitpanel = new SubmitPanel();
        
        add(oldpasswordpanel);
        add(newpasswordpanel);
        add(confirmpanel);
        add(submitpanel);
        
        submitpanel.getSubmitButton().addActionListener((ActionEvent e) -> {
            submit();
        });
    }
    
    public boolean validateinput(){
        boolean status = GlobalFields.SUCCESS;
        
//        if(oldpasswordpanel.isPasswordFieldEmpty()){
//            status = GlobalFields.FAIL;
//            oldpasswordpanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_EMPTY"));
//        }
//        else if(!oldpasswordpanel.getPasswordFieldValue()
//                .equals(GlobalFields.USER.getPassword())){
//            status = GlobalFields.FAIL;
//            oldpasswordpanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_PASSWORDMISMATCH"));
//        }
//        
//        else{
//            oldpasswordpanel.setErrorLabel("");
//        }
        
        if(newpasswordpanel.isPasswordFieldEmpty()){
            status = GlobalFields.FAIL;
            newpasswordpanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_EMPTY"));
        }
        else if(!confirmpanel.getPasswordFieldValue()
                .equals(newpasswordpanel.getPasswordFieldValue())){
            status = GlobalFields.FAIL;
            newpasswordpanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_PASSWORDMISMATCH"));
        }
        else{
            newpasswordpanel.setErrorLabel("");
        }
        
        if(confirmpanel.isPasswordFieldEmpty()){
            status = GlobalFields.FAIL;
            confirmpanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_EMPTY"));
        }
        else if(!confirmpanel.getPasswordFieldValue()
                .equals(newpasswordpanel.getPasswordFieldValue())){
            status = GlobalFields.FAIL;
            confirmpanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_PASSWORDMISMATCH"));
        }
        else{
            confirmpanel.setErrorLabel("");
        }
        System.out.println(status);
        return status;
    }
    
    public void submit(){
        if(validateinput()){
            SwingWorker<Boolean, String> submitworker = new SwingWorker<Boolean, String>() {
            
                ClientResponse clientresponse;
                
                @Override
                protected Boolean doInBackground(){
                
                    submitpanel.loading();
                    
                    GlobalFields.USER.setPassword(newpasswordpanel.getPasswordFieldValue());
                    
                    clientresponse = RestClient.put("changeUserPassword", GlobalFields.USER);
                    
                    return true;
                }
                
                @Override
                protected void done() {
                    submitpanel.finish();
                    
                    if(clientresponse == null){
                        String output = clientresponse.getEntity(String.class);
                        
                        JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                        output, GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT")
                                        + clientresponse.getStatus(), 
                        JOptionPane.ERROR_MESSAGE);
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
                            GlobalFields.USER = mapper.readValue(output, UserEntity.class);
                            
                            oldpasswordpanel.reset();
                            newpasswordpanel.reset();
                            confirmpanel.reset();
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
