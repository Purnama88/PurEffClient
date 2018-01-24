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
import net.purnama.gui.inner.detail.ItemDetail;
import net.purnama.gui.inner.form.util.ItemGroupComboBoxPanel;
import net.purnama.gui.inner.form.util.LabelDecimalTextFieldPanel;
import net.purnama.gui.inner.form.util.LabelPanel;
import net.purnama.gui.inner.form.util.LabelTextAreaPanel;
import net.purnama.gui.inner.form.util.LabelTextFieldErrorPanel;
import net.purnama.gui.inner.form.util.StatusPanel;
import net.purnama.gui.inner.form.util.UomComboBoxPanel;
import net.purnama.gui.inner.home.ItemHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.ItemEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;
import net.purnama.util.Label_Text;

/**
 *
 * @author Purnama
 */
public class ItemAdd extends FormPanel{

    protected final LabelTextFieldErrorPanel codepanel, namepanel;
    
    protected final LabelDecimalTextFieldPanel costpanel;
    
    protected final UomComboBoxPanel buyuompanel, selluompanel;
    
    protected final StatusPanel statuspanel;
    
    protected final LabelTextAreaPanel notepanel;
    
    protected final ItemGroupComboBoxPanel itemgrouppanel;
    
    public ItemAdd() {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_ITEMADD"));
        
        codepanel = new LabelTextFieldErrorPanel(
                GlobalFunctions.toSuperscript(Label_Text.LABEL_REMARK_1 + Label_Text.LABEL_REMARK_2, 
                GlobalFields.PROPERTIES.getProperty("LABEL_CODE")), "");
        
        namepanel = new LabelTextFieldErrorPanel(
                GlobalFunctions.toSuperscript(Label_Text.LABEL_REMARK_1, 
                GlobalFields.PROPERTIES.getProperty("LABEL_NAME")), "");
        
        costpanel = new LabelDecimalTextFieldPanel( 
                GlobalFields.PROPERTIES.getProperty("LABEL_COST"), 0);
        
        buyuompanel = new UomComboBoxPanel();
        buyuompanel.setLabel(GlobalFields.PROPERTIES.getProperty("LABEL_BUYUOM"));
        
        selluompanel = new UomComboBoxPanel();
        selluompanel.setLabel(GlobalFields.PROPERTIES.getProperty("LABEL_SELLUOM"));
        
        notepanel = new LabelTextAreaPanel(GlobalFields.PROPERTIES.
                getProperty("LABEL_NOTE"), "");
        
        statuspanel = new StatusPanel(GlobalFields.PROPERTIES.getProperty("LABEL_STATUS"));
        
        itemgrouppanel = new ItemGroupComboBoxPanel();
        
        codepanel.setTextFieldActionListener(this);
        costpanel.setTextFieldActionListener(this);
        namepanel.setTextFieldActionListener(this);
        
        codepanel.setDocumentListener(this);
        costpanel.setDocumentListener(this);
        namepanel.setDocumentListener(this);
        notepanel.setDocumentListener(this);
        
        detailpanel.add(codepanel);
        detailpanel.add(namepanel);
        detailpanel.add(itemgrouppanel);
        detailpanel.add(buyuompanel);
        detailpanel.add(selluompanel);
        detailpanel.add(costpanel);
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
        
        if(itemgrouppanel.getComboBoxValue() == null){
            status = GlobalFields.FAIL;
            itemgrouppanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_COMBOBOXEMPTY"));
        }
        else{
            itemgrouppanel.setErrorLabel("");
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
                    
                    ItemEntity item = new ItemEntity();

                    item.setCode(codepanel.getTextFieldValue());
                    item.setName(namepanel.getTextFieldValue());
                    item.setItemgroup(itemgrouppanel.getComboBoxValue());
                    item.setBuyuom(buyuompanel.getComboBoxValue());
                    item.setSelluom(selluompanel.getComboBoxValue());
                    item.setCost(costpanel.getTextFieldValue());
                    item.setStatus(statuspanel.getSelectedValue());
                    item.setNote(notepanel.getTextAreaValue());
                    
                    clientresponse = RestClient.post("addItem", item);
                    
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
                            ItemEntity item = mapper.readValue(output, ItemEntity.class);
                            detail(item.getId());
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
        
        tabbedPane.changeTabPanel(getIndex(), new ItemHome());
    }
    
    protected void detail(String id) {
       MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new ItemDetail(id));
    }
    
    @Override
    public void refresh(){
        itemgrouppanel.refresh();
        buyuompanel.refresh();
        selluompanel.refresh();
    }
    
}
