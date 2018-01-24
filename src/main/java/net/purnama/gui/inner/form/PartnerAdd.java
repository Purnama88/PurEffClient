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
import net.purnama.gui.inner.detail.PartnerDetail;
import net.purnama.gui.inner.form.util.LabelDecimalTextFieldPanel;
import net.purnama.gui.inner.form.util.LabelPanel;
import net.purnama.gui.inner.form.util.LabelTextAreaPanel;
import net.purnama.gui.inner.form.util.LabelTextFieldErrorPanel;
import net.purnama.gui.inner.form.util.PartnerTypeComboBoxPanel;
import net.purnama.gui.inner.form.util.StatusPanel;
import net.purnama.gui.inner.home.PartnerHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.PartnerEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;
import net.purnama.util.Label_Text;

/**
 *
 * @author Purnama
 */
public class PartnerAdd extends FormPanel{

    protected final LabelTextFieldErrorPanel codepanel, namepanel, contactnamepanel,
            phonenumberpanel, phonenumber2panel, faxnumberpanel, mobilenumberpanel, emailpanel;
    
    protected final LabelDecimalTextFieldPanel maxdiscountpanel, maxbalancepanel, paymentduepanel;
    
    protected final StatusPanel statuspanel;
    
    protected final LabelTextAreaPanel addresspanel, notepanel;
    
    protected final PartnerTypeComboBoxPanel partnertypepanel;
    
    public PartnerAdd(){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_PARTNERADD"));
        
        codepanel = new LabelTextFieldErrorPanel(
                GlobalFunctions.toSuperscript(Label_Text.LABEL_REMARK_1 + Label_Text.LABEL_REMARK_2, 
                GlobalFields.PROPERTIES.getProperty("LABEL_CODE")), "");
        
        namepanel = new LabelTextFieldErrorPanel(
                GlobalFunctions.toSuperscript(Label_Text.LABEL_REMARK_1, 
                GlobalFields.PROPERTIES.getProperty("LABEL_NAME")), "");
        
        contactnamepanel = new LabelTextFieldErrorPanel(
                GlobalFunctions.toSuperscript(Label_Text.LABEL_REMARK_1, 
                GlobalFields.PROPERTIES.getProperty("LABEL_CONTACTNAME")), "");
        
        phonenumberpanel = new LabelTextFieldErrorPanel( 
                GlobalFields.PROPERTIES.getProperty("LABEL_PHONENUMBER"), "");
        
        phonenumber2panel = new LabelTextFieldErrorPanel(
                GlobalFields.PROPERTIES.getProperty("LABEL_PHONENUMBER"), "");
        
        mobilenumberpanel = new LabelTextFieldErrorPanel(
                GlobalFields.PROPERTIES.getProperty("LABEL_MOBILENUMBER"), "");
        
        faxnumberpanel = new LabelTextFieldErrorPanel(
                GlobalFields.PROPERTIES.getProperty("LABEL_FAXNUMBER"), "");
        
        emailpanel = new LabelTextFieldErrorPanel(
                GlobalFields.PROPERTIES.getProperty("LABEL_EMAIL"), "");
        
        paymentduepanel = new LabelDecimalTextFieldPanel(
                GlobalFields.PROPERTIES.getProperty("LABEL_PAYMENTDUE"), 0);
        
        maxdiscountpanel = new LabelDecimalTextFieldPanel(
                GlobalFunctions.toSuperscript(Label_Text.LABEL_REMARK_3, 
                GlobalFields.PROPERTIES.getProperty("LABEL_MAXDISCOUNT")), 0);
        
        maxbalancepanel = new LabelDecimalTextFieldPanel(
                GlobalFunctions.toSuperscript(Label_Text.LABEL_REMARK_3, 
                GlobalFields.PROPERTIES.getProperty("LABEL_MAXBALANCE")), 0);
        
        addresspanel = new LabelTextAreaPanel(GlobalFields.PROPERTIES.
                getProperty("LABEL_ADDRESS"), "");
        
        notepanel = new LabelTextAreaPanel(GlobalFields.PROPERTIES.
                getProperty("LABEL_NOTE"), "");
        
        statuspanel = new StatusPanel(GlobalFields.PROPERTIES.getProperty("LABEL_STATUS"));
        
        partnertypepanel = new PartnerTypeComboBoxPanel();
    
        codepanel.setTextFieldActionListener(this);
        namepanel.setTextFieldActionListener(this);
        contactnamepanel.setTextFieldActionListener(this);
        phonenumberpanel.setTextFieldActionListener(this);
        phonenumber2panel.setTextFieldActionListener(this);
        faxnumberpanel.setTextFieldActionListener(this);
        emailpanel.setTextFieldActionListener(this);
        mobilenumberpanel.setTextFieldActionListener(this);
        maxdiscountpanel.setTextFieldActionListener(this);
        maxbalancepanel.setTextFieldActionListener(this);
        paymentduepanel.setTextFieldActionListener(this);
        namepanel.setTextFieldActionListener(this);
        
        codepanel.setDocumentListener(this);
        namepanel.setDocumentListener(this);
        contactnamepanel.setDocumentListener(this);
        phonenumberpanel.setDocumentListener(this);
        phonenumber2panel.setDocumentListener(this);
        faxnumberpanel.setDocumentListener(this);
        emailpanel.setDocumentListener(this);
        mobilenumberpanel.setDocumentListener(this);
        maxdiscountpanel.setDocumentListener(this);
        maxbalancepanel.setDocumentListener(this);
        paymentduepanel.setDocumentListener(this);
        addresspanel.setDocumentListener(this);
        notepanel.setDocumentListener(this);
        
        detailpanel.add(codepanel);
        detailpanel.add(namepanel);
        detailpanel.add(contactnamepanel);
        detailpanel.add(partnertypepanel);
        detailpanel.add(addresspanel);
        detailpanel.add(phonenumberpanel);
        detailpanel.add(phonenumber2panel);
        detailpanel.add(mobilenumberpanel);
        detailpanel.add(faxnumberpanel);
        detailpanel.add(emailpanel);
        detailpanel.add(maxdiscountpanel);
        detailpanel.add(maxbalancepanel);
        detailpanel.add(paymentduepanel);
        detailpanel.add(statuspanel);
        detailpanel.add(notepanel);
        detailpanel.add(new LabelPanel(GlobalFields.PROPERTIES.getProperty("REMARK_EMPTY")));
        detailpanel.add(new LabelPanel(GlobalFields.PROPERTIES.getProperty("REMARK_NONEDITABLE")));
        detailpanel.add(new LabelPanel(GlobalFields.PROPERTIES.getProperty("REMARK_NOLIMIT")));
    }
    
    @Override
    protected boolean validateinput() {
        boolean status = GlobalFields.SUCCESS;
        
        if(codepanel.isTextFieldEmpty()){
            status = GlobalFields.FAIL;
            codepanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_EMPTY"));
        }
        else if(!codepanel.isTextFieldLongBetween(GlobalFields.CODE_MINIMUM_LENGTH, 
                GlobalFields.CODE_MAXIMUM_LENGTH)){
            status = GlobalFields.FAIL;
            codepanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_CODE"));
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
        
        if(contactnamepanel.isTextFieldEmpty()){
            status = GlobalFields.FAIL;
            contactnamepanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_EMPTY"));
        }
        else{
            contactnamepanel.setErrorLabel("");
        }
        
        if(partnertypepanel.getComboBoxValue() == null){
            status = GlobalFields.FAIL;
            partnertypepanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_COMBOBOXEMPTY"));
        }
        else{
            partnertypepanel.setErrorLabel("");
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
                    
                    PartnerEntity partner = new PartnerEntity();
                    
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
                    
                    clientresponse = RestClient.post("addPartner", partner);
                    
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

    @Override
    protected void home() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new PartnerHome());
    }
    
    protected void detail(String id) {
       MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new PartnerDetail(id));
    }
    
    @Override
    public void refresh(){
        partnertypepanel.refresh();
    }
}
