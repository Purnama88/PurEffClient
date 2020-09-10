/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.form;

import com.sun.jersey.api.client.ClientResponse;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.purnama.gui.inner.form.util.LabelPanel;
import net.purnama.gui.inner.form.util.LabelTextAreaPanel;
import net.purnama.gui.inner.form.util.LabelTextFieldErrorPanel;
import net.purnama.gui.inner.form.util.StatusPanel;
import net.purnama.gui.inner.home.ItemGroupHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.ItemGroupEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;
import net.purnama.util.Label_Text;

/**
 *
 * @author Purnama
 */
public class ItemGroupAdd extends FormPanel{
    
    protected final LabelTextFieldErrorPanel codepanel, namepanel;
    
    protected final LabelTextAreaPanel notepanel;
    
    protected final StatusPanel statuspanel;
    
    public ItemGroupAdd(){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_ITEMGROUPADD"));
        
        codepanel = new LabelTextFieldErrorPanel(
                GlobalFunctions.toSuperscript(Label_Text.LABEL_REMARK_1 + Label_Text.LABEL_REMARK_2, 
                GlobalFields.PROPERTIES.
                getProperty("LABEL_CODE")), "");
        
        namepanel = new LabelTextFieldErrorPanel(
                GlobalFunctions.toSuperscript(Label_Text.LABEL_REMARK_1, 
                GlobalFields.PROPERTIES.
                getProperty("LABEL_NAME")) , "");
        
        notepanel = new LabelTextAreaPanel(GlobalFields.PROPERTIES.
                getProperty("LABEL_NOTE"), "");
        
        statuspanel = new StatusPanel(GlobalFields.PROPERTIES.getProperty("LABEL_STATUS"));
        
        codepanel.setTextFieldActionListener(this);
        namepanel.setTextFieldActionListener(this);
        
        codepanel.setDocumentListener(this);
        namepanel.setDocumentListener(this);
        notepanel.setDocumentListener(this);
        
        detailpanel.add(codepanel);
        detailpanel.add(namepanel);
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
                    
                    ItemGroupEntity ig = new ItemGroupEntity();
                    ig.setCode(codepanel.getTextFieldValue());
                    ig.setName(namepanel.getTextFieldValue());
                    ig.setStatus(statuspanel.getSelectedValue());
                    ig.setNote(notepanel.getTextAreaValue());

                    clientresponse = RestClient.post("addItemGroup", ig);
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
                        home();
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
        
        tabbedPane.changeTabPanel(getIndex(), new ItemGroupHome());
    }
}
