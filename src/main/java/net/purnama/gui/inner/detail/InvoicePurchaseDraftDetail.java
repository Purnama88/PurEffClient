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
import java.util.Calendar;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.purnama.gui.inner.detail.table.ItemInvoicePurchaseDraftTablePanel;
import net.purnama.gui.inner.detail.util.CurrencyComboBoxPanel;
import net.purnama.gui.inner.detail.util.DatePanel;
import net.purnama.gui.inner.detail.util.LabelDecimalTextFieldPanel;
import net.purnama.gui.inner.detail.util.NumberingComboBoxPanel;
import net.purnama.gui.inner.detail.util.PartnerComboBoxPanel;
import net.purnama.gui.inner.home.InvoicePurchaseDraftHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.draft.InvoicePurchaseDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class InvoicePurchaseDraftDetail extends InvoiceDraftDetailPanel{
    
    private final DatePanel duedatepanel;
    private final LabelDecimalTextFieldPanel ratepanel;
    
    private final PartnerComboBoxPanel partnerpanel;
    private final CurrencyComboBoxPanel currencypanel;
    private final NumberingComboBoxPanel numberingpanel;
    
    private final ItemInvoicePurchaseDraftTablePanel iteminvoicepurchasedrafttablepanel;
    
    private InvoicePurchaseDraftEntity invoicepurchasedraft;
    
    private final String id;
    
    public InvoicePurchaseDraftDetail(String id) {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_INVOICEPURCHASEDRAFTDETAIL"));
        
        this.id = id;
        
        partnerpanel = new PartnerComboBoxPanel(false, true, false);
        duedatepanel = new DatePanel(
                Calendar.getInstance(),
                GlobalFields.PROPERTIES.getProperty("LABEL_DUEDATE"),
                true);
        middledetailpanel.add(partnerpanel);
        middledetailpanel.add(duedatepanel);
        
        numberingpanel = new NumberingComboBoxPanel(7);
        currencypanel = new CurrencyComboBoxPanel();
        ratepanel = new LabelDecimalTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_RATE"),
            1, true, this);
        
        rightdetailpanel.add(numberingpanel);
        rightdetailpanel.add(currencypanel);
        rightdetailpanel.add(ratepanel);
        
        iteminvoicepurchasedrafttablepanel = new ItemInvoicePurchaseDraftTablePanel(id, discountsubtotalpanel);
        
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_CONTENT"),
                iteminvoicepurchasedrafttablepanel);
        
        savebutton.addActionListener((ActionEvent e) -> {
            save();
            iteminvoicepurchasedrafttablepanel.submitItemInvoicePurchaseDraftList();
            iteminvoicepurchasedrafttablepanel.submitDeletedItemInvoicePurchaseDraftList();
            JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                    GlobalFields.PROPERTIES.getProperty("NOTIFICATION_SAVED"),
                "", JOptionPane.INFORMATION_MESSAGE);
        });
        
        closebutton.addActionListener((ActionEvent e) -> {
            save();
            iteminvoicepurchasedrafttablepanel.submitItemInvoicePurchaseDraftList();
            iteminvoicepurchasedrafttablepanel.submitDeletedItemInvoicePurchaseDraftList();
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
    protected final void close(){
        SwingWorker<Boolean, String> saveworker = new SwingWorker<Boolean, String>(){
            ClientResponse response;

            @Override
            protected Boolean doInBackground(){

                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));

                response = RestClient.get("closeInvoicePurchaseDraft?id=" + invoicepurchasedraft.getId());

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
    protected final void save(){
        SwingWorker<Boolean, String> saveworker = new SwingWorker<Boolean, String>(){
            ClientResponse response;

            @Override
            protected Boolean doInBackground(){

                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));

                invoicepurchasedraft.setCurrency(currencypanel.getComboBoxValue());
                invoicepurchasedraft.setDate(datepanel.getCalendar());
                invoicepurchasedraft.setDiscount(discountsubtotalpanel.getDiscount());
                invoicepurchasedraft.setDuedate(duedatepanel.getCalendar());
                invoicepurchasedraft.setFreight(expensespanel.getFreight());
                invoicepurchasedraft.setNote(notepanel.getNote());
                invoicepurchasedraft.setNumbering(numberingpanel.getComboBoxValue());
                invoicepurchasedraft.setPartner(partnerpanel.getComboBoxValue());
                invoicepurchasedraft.setRate(ratepanel.getTextFieldValue());
                invoicepurchasedraft.setRounding(expensespanel.getRounding());
                invoicepurchasedraft.setStatus(true);
                invoicepurchasedraft.setSubtotal(discountsubtotalpanel.getSubtotal());
                invoicepurchasedraft.setTax(expensespanel.getTax());

                response = RestClient.put("updateInvoicePurchaseDraft", invoicepurchasedraft);

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
    
    public final void load(){
        
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getInvoicePurchaseDraft?id=" + id);
                
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
                        invoicepurchasedraft = mapper.readValue(output, InvoicePurchaseDraftEntity.class);
                        
                        notepanel.setNote(invoicepurchasedraft.getNote());
                        datepanel.setDate(invoicepurchasedraft.getDate());
                        warehousepanel.setTextFieldValue(invoicepurchasedraft.getWarehouse().getCode());
                        idpanel.setTextFieldValue(invoicepurchasedraft.getId());
                        partnerpanel.setComboBoxValue(invoicepurchasedraft.getPartner());
                        duedatepanel.setDate(invoicepurchasedraft.getDuedate());
                        numberingpanel.setComboBoxValue(invoicepurchasedraft.getNumbering());
                        currencypanel.setComboBoxValue(invoicepurchasedraft.getCurrency());
                        ratepanel.setTextFieldValue(invoicepurchasedraft.getRate());
                        expensespanel.setRounding(invoicepurchasedraft.getRounding());
                        expensespanel.setFreight(invoicepurchasedraft.getFreight());
                        expensespanel.setTax(invoicepurchasedraft.getTax());
                        
                        discountsubtotalpanel.setSubtotal(invoicepurchasedraft.getSubtotal());
                        discountsubtotalpanel.setDiscount(invoicepurchasedraft.getDiscount());
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }

    @Override
    protected void home() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new InvoicePurchaseDraftHome());
    }

    @Override
    protected final void delete() {
        SwingWorker<Boolean, String> saveworker = new SwingWorker<Boolean, String>(){
            ClientResponse response;

            @Override
            protected Boolean doInBackground(){

                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));

                response = RestClient.delete("deleteInvoicePurchaseDraft?id=" + invoicepurchasedraft.getId());

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
    public void refresh(){
        partnerpanel.refresh();
        currencypanel.refresh();
        numberingpanel.refresh();
        load();
    }
    
    @Override
    protected void changepanel(String id) {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
        getAncestorOfClass(MainTabbedPane.class, this);

        tabbedPane.changeTabPanel(getIndex(), new InvoicePurchaseDetail(id));
    }
}
