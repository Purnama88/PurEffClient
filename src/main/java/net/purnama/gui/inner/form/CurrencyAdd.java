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
import net.purnama.gui.inner.detail.CurrencyDetail;
import net.purnama.gui.inner.form.util.LabelPanel;
import net.purnama.gui.inner.form.util.LabelTextAreaPanel;
import net.purnama.gui.inner.form.util.LabelTextFieldErrorPanel;
import net.purnama.gui.inner.form.util.StatusPanel;
import net.purnama.gui.inner.home.CurrencyHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.CurrencyEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;
import net.purnama.util.Label_Text;

/**
 *
 * @author Purnama
 */
public class CurrencyAdd extends FormPanel{

    protected final LabelTextFieldErrorPanel codepanel, namepanel, descriptionpanel;
    
    protected final LabelTextAreaPanel notepanel;
    
    protected final StatusPanel statuspanel;
    
    public CurrencyAdd(){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_CURRENCYADD"));
        
        codepanel = new LabelTextFieldErrorPanel(
                GlobalFunctions.toSuperscript(Label_Text.LABEL_REMARK_1 + Label_Text.LABEL_REMARK_2, 
                GlobalFields.PROPERTIES.
                getProperty("LABEL_CODE")), "");
        
        namepanel = new LabelTextFieldErrorPanel(
                GlobalFunctions.toSuperscript(Label_Text.LABEL_REMARK_1, 
                GlobalFields.PROPERTIES.
                getProperty("LABEL_NAME")) , "");
        
        descriptionpanel = new LabelTextFieldErrorPanel(
                GlobalFunctions.toSuperscript(Label_Text.LABEL_REMARK_1, 
                GlobalFields.PROPERTIES.
                getProperty("LABEL_DESCRIPTION")) , "");
        
        notepanel = new LabelTextAreaPanel(GlobalFields.PROPERTIES.
                getProperty("LABEL_NOTE"), "");
        
        statuspanel = new StatusPanel(GlobalFields.PROPERTIES.getProperty("LABEL_STATUS"));
        
        codepanel.setTextFieldActionListener(this);
        namepanel.setTextFieldActionListener(this);
        descriptionpanel.setTextFieldActionListener(this);
        
        codepanel.setDocumentListener(this);
        namepanel.setDocumentListener(this);
        descriptionpanel.setDocumentListener(this);
        notepanel.setDocumentListener(this);
        
        detailpanel.add(codepanel);
        detailpanel.add(namepanel);
        detailpanel.add(descriptionpanel);
        detailpanel.add(statuspanel);
        detailpanel.add(notepanel);
        detailpanel.add(new LabelPanel(GlobalFields.PROPERTIES.getProperty("REMARK_EMPTY")));
        detailpanel.add(new LabelPanel(GlobalFields.PROPERTIES.getProperty("REMARK_NONEDITABLE")));
    }
    
    @Override
    protected boolean validateinput() {
        boolean status = GlobalFields.SUCCESS;
        
        if(codepanel.isTextFieldEmpty()){
            status = GlobalFields.FAIL;
            codepanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_EMPTY"));
        }
        else if(!codepanel.isTextFieldLongBetween(3, 3)){
            status = GlobalFields.FAIL;
            codepanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_CODECURRENCY"));
        }
        else{
            codepanel.setErrorLabel("");
        }
        
        if(namepanel.isTextFieldEmpty()){
            status = GlobalFields.FAIL;
            namepanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_EMPTY"));
        }
        else{
            namepanel.setErrorLabel("");
        }
        
        if(descriptionpanel.isTextFieldEmpty()){
            status = GlobalFields.FAIL;
            descriptionpanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_EMPTY"));
        }
        else{
            descriptionpanel.setErrorLabel("");
        }
        
        return status;
        
    }

    @Override
    protected void submit() {
        if(validateinput()){
            SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
                ClientResponse clientresponse;
                
                @Override
                protected Boolean doInBackground() {
                
                    submitpanel.loading();
                    
                    CurrencyEntity currency = new CurrencyEntity();

                    currency.setCode(codepanel.getTextFieldValue());
                    currency.setDefaultcurrency(false);
                    currency.setDescription(descriptionpanel.getTextFieldValue());
                    currency.setName(namepanel.getTextFieldValue());
                    currency.setNote(notepanel.getTextAreaValue());
                    currency.setStatus(statuspanel.getSelectedValue());

                    clientresponse = RestClient.post("addCurrency", currency);
                    
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
                            CurrencyEntity currency = mapper.readValue(output, CurrencyEntity.class);
                            detail(currency.getId());
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
        
        tabbedPane.changeTabPanel(getIndex(), new CurrencyHome());
    }
    
    protected void detail(String id) {
       MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new CurrencyDetail(id));
    }
}
