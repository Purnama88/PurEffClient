/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.form;

import com.sun.jersey.api.client.ClientResponse;
import java.util.Calendar;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.purnama.gui.inner.form.util.DatePanel;
import net.purnama.gui.inner.form.util.LabelPanel;
import net.purnama.gui.inner.form.util.LabelTextAreaPanel;
import net.purnama.gui.inner.form.util.LabelTextFieldErrorPanel;
import net.purnama.gui.inner.form.util.StatusPanel;
import net.purnama.gui.inner.home.NumberingNameHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.NumberingNameEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;
import net.purnama.util.Label_Text;

/**
 *
 * @author Purnama
 */
public class NumberingNameAdd extends FormPanel{
    
    protected final LabelTextFieldErrorPanel namepanel;
    
    protected final DatePanel startdatepanel, enddatepanel;
    
    protected final StatusPanel statuspanel;
    
    protected final LabelTextAreaPanel notepanel;
    
    public NumberingNameAdd(){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_NUMBERINGNAMEADD"));
    
        namepanel = new LabelTextFieldErrorPanel(
                GlobalFunctions.toSuperscript(Label_Text.LABEL_REMARK_1, 
                GlobalFields.PROPERTIES.getProperty("LABEL_NAME")), "");
        
        startdatepanel = new DatePanel(
                GlobalFunctions.toSuperscript(Label_Text.LABEL_REMARK_2, 
                GlobalFields.PROPERTIES.getProperty("LABEL_START")), Calendar.getInstance());
        
        enddatepanel = new DatePanel( 
                GlobalFields.PROPERTIES.getProperty("LABEL_END"), Calendar.getInstance());
        
        statuspanel = new StatusPanel(GlobalFields.PROPERTIES.getProperty("LABEL_STATUS"));
        
        notepanel = new LabelTextAreaPanel(GlobalFields.PROPERTIES.
                getProperty("LABEL_NOTE"), "");
        
        detailpanel.add(namepanel);
        detailpanel.add(startdatepanel);
        detailpanel.add(enddatepanel);
        detailpanel.add(statuspanel);
        detailpanel.add(notepanel);
        detailpanel.add(new LabelPanel(GlobalFields.PROPERTIES.getProperty("REMARK_EMPTY")));
        detailpanel.add(new LabelPanel(GlobalFields.PROPERTIES.getProperty("REMARK_NONEDITABLE")));
        
        namepanel.setTextFieldActionListener(this);
        
        namepanel.setDocumentListener(this);
        startdatepanel.setDocumentListener(this);
        enddatepanel.setDocumentListener(this);
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
        
        if(startdatepanel.getCalendar().after(enddatepanel.getCalendar())){
            status = GlobalFields.FAIL;
            startdatepanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_DATESTARTBIGGER"));
            enddatepanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_DATESTARTBIGGER"));
        }
        else{
            startdatepanel.setErrorLabel("");
            enddatepanel.setErrorLabel("");
        }
        
        if(enddatepanel.getCalendar().before(Calendar.getInstance())){
            status = GlobalFields.FAIL;
            enddatepanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_DATEBACKWARD"));
        }
        else{
            enddatepanel.setErrorLabel("");
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
                    
                    NumberingNameEntity numberingname = new NumberingNameEntity();

                    numberingname.setBegin(startdatepanel.getCalendar());
                    numberingname.setEnd(enddatepanel.getCalendar());
                    numberingname.setName(namepanel.getTextFieldValue());
                    numberingname.setNote(notepanel.getTextAreaValue());
                    numberingname.setStatus(statuspanel.getSelectedValue());
                    
                    clientresponse = RestClient.post("addNumberingName", numberingname);

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
        
        tabbedPane.changeTabPanel(getIndex(), new NumberingNameHome());
    }
}
