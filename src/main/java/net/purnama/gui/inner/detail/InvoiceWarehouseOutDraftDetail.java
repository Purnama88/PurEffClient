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
import net.purnama.gui.inner.detail.table.ItemInvoiceWarehouseOutDraftTablePanel;
import net.purnama.gui.inner.detail.util.LabelTextFieldPanel;
import net.purnama.gui.inner.detail.util.NumberingComboBoxPanel;
import net.purnama.gui.inner.detail.util.WarehouseComboBoxPanel;
import net.purnama.gui.inner.home.InvoiceWarehouseOutDraftHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.draft.InvoiceWarehouseOutDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class InvoiceWarehouseOutDraftDetail extends InvoiceDraftDetailPanel{

    private final WarehouseComboBoxPanel destinationpanel;
    
    private final LabelTextFieldPanel shippingnumberpanel;
    
    private final NumberingComboBoxPanel numberingpanel;
    
    private final ItemInvoiceWarehouseOutDraftTablePanel iteminvoicewarehouseoutdrafttablepanel;
    
    private InvoiceWarehouseOutDraftEntity invoicewarehouseoutdraft;
    
    private final String id;
    
    public InvoiceWarehouseOutDraftDetail(String id) {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_INVOICEWAREHOUSEOUTDRAFTDETAIL"));
        
        this.id = id;
        
        destinationpanel = new WarehouseComboBoxPanel();
        destinationpanel.setLabelValue(GlobalFields.PROPERTIES.getProperty("LABEL_DESTINATION"));
        
        shippingnumberpanel = new LabelTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_SHIPPINGNO"),
           "", true, this);
        
        middledetailpanel.add(destinationpanel);
        middledetailpanel.add(shippingnumberpanel);
        
        numberingpanel = new NumberingComboBoxPanel(6);
        
        rightdetailpanel.add(numberingpanel);
        
        iteminvoicewarehouseoutdrafttablepanel = new ItemInvoiceWarehouseOutDraftTablePanel(id);
        
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_CONTENT"),
                iteminvoicewarehouseoutdrafttablepanel);
        
        rightsummarypanel.removeAll();
        
        savebutton.addActionListener((ActionEvent e) -> {
            save();
            iteminvoicewarehouseoutdrafttablepanel.submitItemInvoiceWarehouseOutDraftList();
            iteminvoicewarehouseoutdrafttablepanel.submitDeletedItemInvoiceWarehouseOutDraftList();
            JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                    GlobalFields.PROPERTIES.getProperty("NOTIFICATION_SAVED"),
                "", JOptionPane.INFORMATION_MESSAGE);
        });
        
        closebutton.addActionListener((ActionEvent e) -> {
            save();
            iteminvoicewarehouseoutdrafttablepanel.submitItemInvoiceWarehouseOutDraftList();
            iteminvoicewarehouseoutdrafttablepanel.submitDeletedItemInvoiceWarehouseOutDraftList();
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

    public final void load(){
        
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getInvoiceWarehouseOutDraft?id=" + id);
                
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
                        invoicewarehouseoutdraft = mapper.readValue(output, InvoiceWarehouseOutDraftEntity.class);
                        
                        datepanel.setDate(invoicewarehouseoutdraft.getDate());
                        warehousepanel.setTextFieldValue(invoicewarehouseoutdraft.getWarehouse().getCode());
                        idpanel.setTextFieldValue(invoicewarehouseoutdraft.getId());
                        destinationpanel.setComboBoxValue(invoicewarehouseoutdraft.getDestination());
                        shippingnumberpanel.setTextFieldValue(invoicewarehouseoutdraft.getShipping_number());
                        numberingpanel.setComboBoxValue(invoicewarehouseoutdraft.getNumbering());
                        notepanel.setNote(invoicewarehouseoutdraft.getNote());
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    @Override
    protected final void home() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new InvoiceWarehouseOutDraftHome());
    }

    @Override
    protected final void save(){
        SwingWorker<Boolean, String> saveworker = new SwingWorker<Boolean, String>(){
            ClientResponse response;

            @Override
            protected Boolean doInBackground(){

                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));

                invoicewarehouseoutdraft.setDate(datepanel.getCalendar());
                invoicewarehouseoutdraft.setNote(notepanel.getNote());
                invoicewarehouseoutdraft.setShipping_number(shippingnumberpanel.getTextFieldValue());
                invoicewarehouseoutdraft.setNumbering(numberingpanel.getComboBoxValue());
                invoicewarehouseoutdraft.setDestination(destinationpanel.getComboBoxValue());
                invoicewarehouseoutdraft.setStatus(true);

                response = RestClient.put("updateInvoiceWarehouseOutDraft", invoicewarehouseoutdraft);

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

                response = RestClient.get("closeInvoiceWarehouseOutDraft?id=" + invoicewarehouseoutdraft.getId());

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

    @Override
    protected final void delete() {
        SwingWorker<Boolean, String> saveworker = new SwingWorker<Boolean, String>(){
            ClientResponse response;

            @Override
            protected Boolean doInBackground() {

                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));

                response = RestClient.delete("deleteInvoiceWarehouseOutDraft?id=" + invoicewarehouseoutdraft.getId());

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

        tabbedPane.changeTabPanel(getIndex(), new InvoiceWarehouseOutDetail(id));
    }
    
    @Override
    public void refresh(){
        numberingpanel.refresh();
        destinationpanel.refresh();
        load();
    }
    
}
