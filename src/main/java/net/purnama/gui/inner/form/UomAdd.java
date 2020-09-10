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
import net.purnama.gui.inner.detail.UomDetail;
import net.purnama.gui.inner.form.util.LabelDecimalTextFieldPanel;
import net.purnama.gui.inner.form.util.LabelPanel;
import net.purnama.gui.inner.form.util.LabelTextAreaPanel;
import net.purnama.gui.inner.form.util.LabelTextFieldErrorPanel;
import net.purnama.gui.inner.form.util.StatusPanel;
import net.purnama.gui.inner.home.UomHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.UomEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;
import net.purnama.util.Label_Text;

/**
 *
 * @author Purnama
 */
public class UomAdd extends FormPanel{

    protected final LabelTextFieldErrorPanel namepanel;
    
    protected final LabelDecimalTextFieldPanel valuepanel;
    
    protected final StatusPanel statuspanel;
    
    protected final LabelTextAreaPanel notepanel;
    
    public UomAdd() {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_UOMADD"));
        
        namepanel = new LabelTextFieldErrorPanel(
                GlobalFunctions.toSuperscript(
                Label_Text.LABEL_REMARK_1, GlobalFields.PROPERTIES.getProperty("LABEL_NAME")), "");
        
        valuepanel = new LabelDecimalTextFieldPanel( 
                GlobalFields.PROPERTIES.getProperty("LABEL_VALUE"), 1);
        valuepanel.setTextFieldEditable(false);
        
        statuspanel = new StatusPanel(GlobalFields.PROPERTIES.getProperty("LABEL_STATUS"));
        
        notepanel = new LabelTextAreaPanel(GlobalFields.PROPERTIES.getProperty("LABEL_NOTE"), "");
        
        detailpanel.add(namepanel);
        detailpanel.add(valuepanel);
        detailpanel.add(statuspanel);
        detailpanel.add(notepanel);
        detailpanel.add(new LabelPanel(GlobalFields.PROPERTIES.getProperty("REMARK_EMPTY")));
        
        namepanel.setTextFieldActionListener(this);
        
        namepanel.setDocumentListener(this);
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
                    
                    UomEntity uom = new UomEntity();
                    uom.setName(namepanel.getTextFieldValue());
                    uom.setNote(notepanel.getTextAreaValue());
                    uom.setParent(null);
                    uom.setStatus(statuspanel.getSelectedValue());
                    uom.setValue(valuepanel.getTextFieldValue());

                    clientresponse = RestClient.post("addUom", uom);

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
                            UomEntity uom = mapper.readValue(output, UomEntity.class);
                            detail(uom.getId());
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
        
        tabbedPane.changeTabPanel(getIndex(), new UomHome());
    }
    
    protected void detail(String id) {
       MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new UomDetail(id));
    }
    
}
