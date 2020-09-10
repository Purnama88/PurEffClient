/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.form;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import net.purnama.gui.library.MyPanel;
import net.purnama.model.NumberingNameEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.CalendarUtil;
import net.purnama.util.DateUtils;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class NumberingNameEdit extends NumberingNameAdd{
    
    private NumberingNameEntity numberingname;
    
    private final String id;
    
    public NumberingNameEdit(String id){
        super();
        setName(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_NUMBERINGNAMEEDIT"));
        this.id = id;
        
        load();
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
        
        if(!DateUtils.isSameDay(enddatepanel.getCalendar(), numberingname.getEnd()) && 
                enddatepanel.getCalendar().before(Calendar.getInstance())){
            status = GlobalFields.FAIL;
            enddatepanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_DATEBACKWARD"));
        }
        else{
            enddatepanel.setErrorLabel("");
        }
        
        return status;
    }
    
    public final void load() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getNumberingName?id=" + id);
                
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
                        numberingname = mapper.readValue(output, NumberingNameEntity.class);
                        
                        namepanel.setTextFieldValue(numberingname.getName());
                        startdatepanel.setDate(numberingname.getBegin());
                        enddatepanel.setDate(numberingname.getEnd());
                        statuspanel.setSelectedValue(numberingname.isStatus());
                        notepanel.setTextAreaValue(numberingname.getNote());
                        
                        startdatepanel.disableDate();
                        
                        upperpanel.setStatusLabel(numberingname.getFormattedLastmodified());
                        
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
            
            SwingWorker<Boolean, String> submitworker = new SwingWorker<Boolean, String>() {
            
                ClientResponse clientresponse;
                
                @Override
                protected Boolean doInBackground(){
                
                    submitpanel.loading();
                    
                    numberingname.setBegin(CalendarUtil.toStartOfDay(startdatepanel.getCalendar()));
                    numberingname.setEnd(CalendarUtil.toEndofDay(enddatepanel.getCalendar()));
                    numberingname.setName(namepanel.getTextFieldValue());
                    numberingname.setNote(notepanel.getTextAreaValue());
                    numberingname.setStatus(statuspanel.getSelectedValue());
                    
                    clientresponse = RestClient.put("updateNumberingName", numberingname);

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
            
            submitworker.execute();
        }
    }
    
    @Override
    public void refresh(){
        load();
    }
}
