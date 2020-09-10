/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.purnama.gui.inner.detail.table.ItemAdjustmentDraftTablePanel;
import net.purnama.gui.inner.detail.util.NumberingComboBoxPanel;
import net.purnama.gui.inner.home.AdjustmentDraftHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.draft.AdjustmentDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class AdjustmentDraftDetail extends InvoiceDraftDetailPanel{
    
    private final ItemAdjustmentDraftTablePanel itemadjustmentdrafttablepanel; 
    
    private AdjustmentDraftEntity adjustmentdraft;
    
    private final String id;
    
    private final NumberingComboBoxPanel numberingpanel;
    
    public AdjustmentDraftDetail(String id) {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_ADJUSTMENTDRAFTDETAIL"));
        
        this.id = id;
        
        numberingpanel = new NumberingComboBoxPanel(1);
        rightdetailpanel.add(numberingpanel);
        
        itemadjustmentdrafttablepanel = new ItemAdjustmentDraftTablePanel(id);
        
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_CONTENT"),
                itemadjustmentdrafttablepanel);
        
        rightsummarypanel.removeAll();
        
        savebutton.addActionListener((ActionEvent e) -> {
            save();
            itemadjustmentdrafttablepanel.submitItemAdjustmentDraftList();
            itemadjustmentdrafttablepanel.submitDeletedItemAdjustmentDraftList();
            JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                    GlobalFields.PROPERTIES.getProperty("NOTIFICATION_SAVED"),
                "", JOptionPane.INFORMATION_MESSAGE);
        });
        
        closebutton.addActionListener((ActionEvent e) -> {
            save();
            itemadjustmentdrafttablepanel.submitItemAdjustmentDraftList();
            itemadjustmentdrafttablepanel.submitDeletedItemAdjustmentDraftList();
            int result = JOptionPane.showConfirmDialog(null, GlobalFields.
                PROPERTIES.getProperty("QUESTION_CLOSEINVOICE"),
                "", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    close();
                }
        });
        
        deletebutton.addActionListener((ActionEvent e) -> {
            int result = JOptionPane.showConfirmDialog(null, GlobalFields.
                PROPERTIES.getProperty("QUESTION_DELETEINVOICE"),
                "", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    delete();
                }
        });
        
        load();
    }

    @Override
    protected final void home() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new AdjustmentDraftHome());
    }

    @Override
    protected final void save(){
        SwingWorker<Boolean, String> saveworker = new SwingWorker<Boolean, String>(){
            ClientResponse response;

            @Override
            protected Boolean doInBackground(){

                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));

                adjustmentdraft.setDate(datepanel.getCalendar());
                adjustmentdraft.setNote(notepanel.getNote());
                adjustmentdraft.setNumbering(numberingpanel.getComboBoxValue());
                adjustmentdraft.setStatus(true);

                response = RestClient.put("updateAdjustmentDraft", adjustmentdraft);

                return true;
            }

            @Override
            protected void process(List<String> chunks) {
                chunks.stream().forEach((number) -> {
                    upperpanel.setNotifLabel(number);
                });
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
                }
            }
        };
        saveworker.execute();
    }

    @Override
    protected final void close(){
        SwingWorker<Boolean, String> saveworker = new SwingWorker<Boolean, String>(){
            ClientResponse response;

            @Override
            protected Boolean doInBackground(){

                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));

                response = RestClient.get("closeAdjustmentDraft?id=" + adjustmentdraft.getId());

                return true;
            }

            @Override
            protected void process(List<String> chunks) {
                chunks.stream().forEach((number) -> {
                    upperpanel.setNotifLabel(number);
                });
            }
            
            @Override
            protected void done() {
                upperpanel.hideProgressBar();
                
                if(response == null){
                    upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                }
                else if(response.getStatus() != 200) {
                    upperpanel.setNotifLabel("");
                    String output = response.getEntity(String.class);
                        
                    JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                    output, GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                    + response.getStatus(), 
                    JOptionPane.ERROR_MESSAGE);
                }
                else{
                    upperpanel.setNotifLabel("");
                    
                    String output = response.getEntity(String.class);
                    changepanel(output);
                }
            }
        };
        saveworker.execute();
    }

    public final void load(){
        
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getAdjustmentDraft?id=" + id);
                
                return true;
            }
            
            @Override
            protected void process(List<String> chunks) {
                chunks.stream().forEach((number) -> {
                    upperpanel.setNotifLabel(number);
                });
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
                        adjustmentdraft = mapper.readValue(output, AdjustmentDraftEntity.class);
                        
                        datepanel.setDate(adjustmentdraft.getDate());
                        warehousepanel.setTextFieldValue(adjustmentdraft.getWarehouse().getCode());
                        idpanel.setTextFieldValue(adjustmentdraft.getId());
                        numberingpanel.setComboBoxValue(adjustmentdraft.getNumbering());
                        notepanel.setNote(adjustmentdraft.getNote());
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    @Override
    protected void delete() {
        SwingWorker<Boolean, String> saveworker = new SwingWorker<Boolean, String>(){
            ClientResponse response;

            @Override
            protected Boolean doInBackground(){

                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));

                response = RestClient.delete("deleteAdjustmentDraft?id=" + adjustmentdraft.getId());

                return true;
            }

            @Override
            protected void process(List<String> chunks) {
                chunks.stream().forEach((number) -> {
                    upperpanel.setNotifLabel(number);
                });
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
                    home();
                }
            }
        };
        saveworker.execute();
    }

    @Override
    protected void changepanel(String id) {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
        getAncestorOfClass(MainTabbedPane.class, this);

        tabbedPane.changeTabPanel(getIndex(), new AdjustmentDetail(id));
    }
    
    @Override
    public void refresh(){
        numberingpanel.refresh();
        load();
    }
}
