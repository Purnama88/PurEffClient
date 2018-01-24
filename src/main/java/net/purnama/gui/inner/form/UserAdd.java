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
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.purnama.gui.inner.detail.UserDetail;
import net.purnama.gui.inner.form.util.LabelDecimalTextFieldPanel;
import net.purnama.gui.inner.form.util.LabelPanel;
import net.purnama.gui.inner.form.util.LabelPasswordFieldErrorPanel;
import net.purnama.gui.inner.form.util.LabelTextAreaPanel;
import net.purnama.gui.inner.form.util.LabelTextFieldErrorPanel;
import net.purnama.gui.inner.form.util.RoleComboBoxPanel;
import net.purnama.gui.inner.form.util.StatusPanel;
import net.purnama.gui.inner.form.util.WarehouseCheckBoxPanel;
import net.purnama.gui.inner.home.UserHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.UserEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;
import net.purnama.util.Label_Text;

/**
 *
 * @author Purnama
 */
public class UserAdd extends FormPanel{
    
    protected final LabelTextFieldErrorPanel namepanel, usernamepanel;
    
    protected final LabelPasswordFieldErrorPanel passwordpanel, confirmpanel;
    
    protected final LabelDecimalTextFieldPanel discountpanel;
    
    protected final StatusPanel statuspanel, backwardpanel, forwardpanel,
            raise_buypricepanel, lower_buypricepanel, raise_sellpricepanel, 
            lower_sellpricepanel;
    
    protected final LabelTextAreaPanel notepanel;
    
    protected final RoleComboBoxPanel rolepanel;
    
    protected final WarehouseCheckBoxPanel warehousepanel;
    
    public UserAdd(){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_USERADD"));
        
        namepanel = new LabelTextFieldErrorPanel(
                GlobalFunctions.toSuperscript(
                Label_Text.LABEL_REMARK_1, GlobalFields.PROPERTIES.getProperty("LABEL_NAME")), "");
        
        usernamepanel = new LabelTextFieldErrorPanel(
                GlobalFunctions.toSuperscript(
                        Label_Text.LABEL_REMARK_1 + Label_Text.LABEL_REMARK_2,
                GlobalFields.PROPERTIES.getProperty("LABEL_USERNAME")), "");
    
        passwordpanel = new LabelPasswordFieldErrorPanel(
                GlobalFunctions.toSuperscript(Label_Text.LABEL_REMARK_1, 
                GlobalFields.PROPERTIES.getProperty("LABEL_PASSWORD")), "");
        
        confirmpanel = new LabelPasswordFieldErrorPanel(
                GlobalFunctions.toSuperscript(Label_Text.LABEL_REMARK_1, 
                GlobalFields.PROPERTIES.getProperty("LABEL_CONFIRM")), "");
        
        discountpanel = new LabelDecimalTextFieldPanel(
                GlobalFunctions.toSuperscript(Label_Text.LABEL_REMARK_3, 
                GlobalFields.PROPERTIES.getProperty("LABEL_MAXDISCOUNT")), 0);
    
        statuspanel = new StatusPanel(GlobalFields.PROPERTIES.getProperty("LABEL_STATUS"));
        
        forwardpanel = new StatusPanel(GlobalFields.PROPERTIES.getProperty("LABEL_DATEFORWARD"));
        
        backwardpanel = new StatusPanel(GlobalFields.PROPERTIES.getProperty("LABEL_DATEBACKWARD"));
        
        raise_buypricepanel = new StatusPanel(GlobalFields.PROPERTIES.getProperty("LABEL_RAISEBUYPRICE"));
        
        lower_buypricepanel = new StatusPanel(GlobalFields.PROPERTIES.getProperty("LABEL_LOWERBUYPRICE"));
        
        raise_sellpricepanel = new StatusPanel(GlobalFields.PROPERTIES.getProperty("LABEL_RAISESELLPRICE"));
        
        lower_sellpricepanel = new StatusPanel(GlobalFields.PROPERTIES.getProperty("LABEL_LOWERSELLPRICE"));
        
        notepanel = new LabelTextAreaPanel(GlobalFields.PROPERTIES.getProperty("LABEL_NOTE"), "");
        
        rolepanel = new RoleComboBoxPanel();
        
        warehousepanel = new WarehouseCheckBoxPanel();
        
        detailpanel.add(namepanel);
        detailpanel.add(usernamepanel);
        detailpanel.add(passwordpanel);
        detailpanel.add(confirmpanel);
        detailpanel.add(rolepanel);
        detailpanel.add(warehousepanel);
        detailpanel.add(discountpanel);
        detailpanel.add(forwardpanel);
        detailpanel.add(backwardpanel);
        detailpanel.add(raise_buypricepanel);
        detailpanel.add(lower_buypricepanel);
        detailpanel.add(raise_sellpricepanel);
        detailpanel.add(lower_sellpricepanel);
        detailpanel.add(statuspanel);
        detailpanel.add(notepanel);
        detailpanel.add(new LabelPanel(GlobalFields.PROPERTIES.getProperty("REMARK_EMPTY")));
        detailpanel.add(new LabelPanel(GlobalFields.PROPERTIES.getProperty("REMARK_NONEDITABLE")));
        detailpanel.add(new LabelPanel(GlobalFields.PROPERTIES.getProperty("REMARK_NOLIMIT")));
        
        namepanel.setTextFieldActionListener(this);
        usernamepanel.setTextFieldActionListener(this);
        passwordpanel.setPasswordFieldActionListener(this);
        confirmpanel.setPasswordFieldActionListener(this);
        discountpanel.setTextFieldActionListener(this);
        
        namepanel.setDocumentListener(this);
        usernamepanel.setDocumentListener(this);
        passwordpanel.setDocumentListener(this);
        confirmpanel.setDocumentListener(this);
        discountpanel.setDocumentListener(this);
        notepanel.setDocumentListener(this);
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
        
        if(usernamepanel.isTextFieldEmpty()){
            status = GlobalFields.FAIL;
            usernamepanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_EMPTY"));
        }
        else{
            usernamepanel.setErrorLabel("");
        }
        
        if(passwordpanel.isPasswordFieldEmpty()){
            status = GlobalFields.FAIL;
            passwordpanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_EMPTY"));
        }
        else if(!confirmpanel.getPasswordFieldValue()
                .equals(passwordpanel.getPasswordFieldValue())){
            status = GlobalFields.FAIL;
            passwordpanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_PASSWORDMISMATCH"));
        }
        else{
            passwordpanel.setErrorLabel("");
        }
        
        if(confirmpanel.isPasswordFieldEmpty()){
            status = GlobalFields.FAIL;
            confirmpanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_EMPTY"));
        }
        else if(!confirmpanel.getPasswordFieldValue()
                .equals(passwordpanel.getPasswordFieldValue())){
            status = GlobalFields.FAIL;
            confirmpanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_PASSWORDMISMATCH"));
        }
        else{
            confirmpanel.setErrorLabel("");
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
            SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
                ClientResponse clientresponse;
                
                @Override
                protected Boolean doInBackground(){
                
                    submitpanel.loading();
                    
                    UserEntity user = new UserEntity();

                    user.setName(namepanel.getTextFieldValue());
                    user.setUsername(usernamepanel.getTextFieldValue());
                    user.setPassword(passwordpanel.getPasswordFieldValue());
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
                    
                    clientresponse = RestClient.post("addUser", user);
                    
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
                            UserEntity user = mapper.readValue(output, UserEntity.class);
                            detail(user.getId());
                        }
                        catch(IOException e){

                        }
                    }
                }
            };
            
            worker.execute();
        }
    }

    @Override
    protected void home() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new UserHome());
    }
    
    protected void detail(String id) {
       MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new UserDetail(id));
    }
    
    @Override
    public void refresh(){
        rolepanel.refresh();
        warehousepanel.refresh();
    }
}
