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
import net.purnama.gui.inner.detail.table.ItemInvoiceWarehouseInDraftTablePanel;
import net.purnama.gui.inner.detail.util.LabelTextFieldPanel;
import net.purnama.gui.inner.detail.util.NumberingComboBoxPanel;
import net.purnama.gui.inner.detail.util.WarehouseComboBoxPanel;
import net.purnama.gui.inner.home.InvoiceWarehouseInDraftHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.draft.InvoiceWarehouseInDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class InvoiceWarehouseInDraftDetail extends InvoiceDraftDetailPanel{

    private final WarehouseComboBoxPanel originpanel;
    
    private final LabelTextFieldPanel shippingnumberpanel;
    
    private final NumberingComboBoxPanel numberingpanel;
    
    private final ItemInvoiceWarehouseInDraftTablePanel iteminvoicewarehouseindrafttablepanel;
    
    private InvoiceWarehouseInDraftEntity invoicewarehouseindraft;
    
    private final String id;
    
    public InvoiceWarehouseInDraftDetail(String id) {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_INVOICEWAREHOUSEINDRAFTDETAIL"));
        
        this.id = id;
        
        originpanel = new WarehouseComboBoxPanel();
        originpanel.setLabelValue(GlobalFields.PROPERTIES.getProperty("LABEL_ORIGIN"));
        
        shippingnumberpanel = new LabelTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_SHIPPINGNO"),
           "", true, this);
        
        middledetailpanel.add(originpanel);
        middledetailpanel.add(shippingnumberpanel);
        
        numberingpanel = new NumberingComboBoxPanel(5);
        
        rightdetailpanel.add(numberingpanel);
        
        iteminvoicewarehouseindrafttablepanel = new ItemInvoiceWarehouseInDraftTablePanel(id);
        
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_CONTENT"),
                iteminvoicewarehouseindrafttablepanel);
        
        rightsummarypanel.removeAll();
        
        savebutton.addActionListener((ActionEvent e) -> {
            save();
            iteminvoicewarehouseindrafttablepanel.submitItemInvoiceWarehouseInDraftList();
            iteminvoicewarehouseindrafttablepanel.submitDeletedItemInvoiceWarehouseInDraftList();
            JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                    GlobalFields.PROPERTIES.getProperty("NOTIFICATION_SAVED"),
                "", JOptionPane.INFORMATION_MESSAGE);
        });
        
        closebutton.addActionListener((ActionEvent e) -> {
            save();
            iteminvoicewarehouseindrafttablepanel.submitItemInvoiceWarehouseInDraftList();
            iteminvoicewarehouseindrafttablepanel.submitDeletedItemInvoiceWarehouseInDraftList();
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
                
                response = RestClient.get("getInvoiceWarehouseInDraft?id=" + id);
                
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
                        invoicewarehouseindraft = mapper.readValue(output, InvoiceWarehouseInDraftEntity.class);
                        
                        datepanel.setDate(invoicewarehouseindraft.getDate());
                        warehousepanel.setTextFieldValue(invoicewarehouseindraft.getWarehouse().getCode());
                        idpanel.setTextFieldValue(invoicewarehouseindraft.getId());
                        originpanel.setComboBoxValue(invoicewarehouseindraft.getOrigin());
                        shippingnumberpanel.setTextFieldValue(invoicewarehouseindraft.getShipping_number());
                        numberingpanel.setComboBoxValue(invoicewarehouseindraft.getNumbering());
                        notepanel.setNote(invoicewarehouseindraft.getNote());
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
        
        tabbedPane.changeTabPanel(getIndex(), new InvoiceWarehouseInDraftHome());
    }

    @Override
    protected final void save(){
        SwingWorker<Boolean, String> saveworker = new SwingWorker<Boolean, String>(){
            ClientResponse response;

            @Override
            protected Boolean doInBackground(){

                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));

                invoicewarehouseindraft.setDate(datepanel.getCalendar());
                invoicewarehouseindraft.setNote(notepanel.getNote());
                invoicewarehouseindraft.setShipping_number(shippingnumberpanel.getTextFieldValue());
                invoicewarehouseindraft.setNumbering(numberingpanel.getComboBoxValue());
                invoicewarehouseindraft.setOrigin(originpanel.getComboBoxValue());
                invoicewarehouseindraft.setStatus(true);

                response = RestClient.put("updateInvoiceWarehouseInDraft", invoicewarehouseindraft);

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

                response = RestClient.get("closeInvoiceWarehouseInDraft?id=" + invoicewarehouseindraft.getId());

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

                response = RestClient.delete("deleteInvoiceWarehouseInDraft?id=" + invoicewarehouseindraft.getId());

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

        tabbedPane.changeTabPanel(getIndex(), new InvoiceWarehouseInDetail(id));
    }
    
    @Override
    public void refresh(){
        numberingpanel.refresh();
        originpanel.refresh();
        load();
    }
}
