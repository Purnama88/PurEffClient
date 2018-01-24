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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.purnama.gui.dialog.PaymentTypeInDraftDialog;
import net.purnama.gui.inner.detail.table.PaymentInDraftTablePanel;
import net.purnama.gui.inner.detail.util.AmountPanel;
import net.purnama.gui.inner.detail.util.CurrencyComboBoxPanel;
import net.purnama.gui.inner.detail.util.DatePanel;
import net.purnama.gui.inner.detail.util.LabelDecimalTextFieldPanel;
import net.purnama.gui.inner.detail.util.NumberingComboBoxPanel;
import net.purnama.gui.inner.detail.util.PaidPanel;
import net.purnama.gui.inner.detail.util.PartnerComboBoxPanel;
import net.purnama.gui.inner.detail.util.RemainingPanel;
import net.purnama.gui.inner.home.PaymentInDraftHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.draft.PaymentInDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PaymentInDraftDetail extends InvoiceDraftDetailPanel{

    private final DatePanel duedatepanel;
    
    private final LabelDecimalTextFieldPanel ratepanel;
    
    private final AmountPanel amountpanel;
    private final PaidPanel paidpanel;
    private final RemainingPanel remainingpanel;
    
    private final NumberingComboBoxPanel numberingpanel;
    private final PartnerComboBoxPanel partnerpanel;
    private final CurrencyComboBoxPanel currencypanel;
    
    private final PaymentInDraftTablePanel paymentindrafttablepanel;
    
    private PaymentInDraftEntity paymentindraft;
    
    private final String id;
    
    public PaymentInDraftDetail(String id) {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_PAYMENTINDRAFTDETAIL"));
        
        this.id = id;
        
        partnerpanel = new PartnerComboBoxPanel(true, true, true);
        duedatepanel = new DatePanel(
                Calendar.getInstance(),
                GlobalFields.PROPERTIES.getProperty("LABEL_DUEDATE"),
                true);
        middledetailpanel.add(partnerpanel);
        middledetailpanel.add(duedatepanel);
        
        numberingpanel = new NumberingComboBoxPanel(15);
        currencypanel = new CurrencyComboBoxPanel();
        ratepanel = new LabelDecimalTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_RATE"),
            1, true, this);
        
        rightdetailpanel.add(numberingpanel);
        rightdetailpanel.add(currencypanel);
        rightdetailpanel.add(ratepanel);
        
        DocumentListener documentListener = new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                calculatetotal();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculatetotal();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calculatetotal();
            }
            
            private void calculatetotal(){
                double amount = amountpanel.getAmount();
                double paid = paidpanel.getPaid();

                double total = amount - paid;

                remainingpanel.setRemaining(
                        total
                );
            }
        };
        
        amountpanel = new AmountPanel();
        paidpanel = new PaidPanel();
        remainingpanel = new RemainingPanel();
        
        amountpanel.setDocumentListener(documentListener);
        paidpanel.setDocumentListener(documentListener);
        
        paymentindrafttablepanel = new PaymentInDraftTablePanel(id, paidpanel);
        
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_CONTENT"),
                paymentindrafttablepanel);
        
        rightsummarypanel.removeAll();
       
        rightsummarypanel.add(paidpanel);
        rightsummarypanel.add(amountpanel);
        rightsummarypanel.add(remainingpanel);
        
        savebutton.addActionListener((ActionEvent e) -> {
            save();
            paymentindrafttablepanel.saveselectedpaymentinvoice();
            paymentindrafttablepanel.saveselectedpaymentreturn();
        });
        
        closebutton.addActionListener((ActionEvent e) -> {
            save();
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
        
        amountpanel.getPaymentButton().addActionListener((ActionEvent e) -> {
           PaymentTypeInDraftDialog d = new PaymentTypeInDraftDialog(id, paymentindraft.getAmount(), -remainingpanel.getRemaining());
           double result = d.showDialog();
           paymentindraft.setAmount(result);
           amountpanel.setAmount(result);
           save();
        });
        
        partnerpanel.getComboBox().addActionListener((ActionEvent e) -> {
            paymentindrafttablepanel.load(partnerpanel.getComboBoxValue().getId(),
                    currencypanel.getComboBoxValue().getId());
        });
        
        load();
        
    }
    

    @Override
    protected void home() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new PaymentInDraftHome());
    }

    public final void load(){
        
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getPaymentInDraft?id=" + id);
                
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
                        paymentindraft = mapper.readValue(output, PaymentInDraftEntity.class);
                        
                        notepanel.setNote(paymentindraft.getNote());
                        datepanel.setDate(paymentindraft.getDate());
                        warehousepanel.setTextFieldValue(paymentindraft.getWarehouse().getCode());
                        idpanel.setTextFieldValue(paymentindraft.getId());
                        partnerpanel.setComboBoxValue(paymentindraft.getPartner());
                        duedatepanel.setDate(paymentindraft.getDuedate());
                        numberingpanel.setComboBoxValue(paymentindraft.getNumbering());
                        currencypanel.setComboBoxValue(paymentindraft.getCurrency());
                        ratepanel.setTextFieldValue(paymentindraft.getRate());
                        amountpanel.setAmount(paymentindraft.getAmount());
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    @Override
    protected final void save() {
        SwingWorker<Boolean, String> saveworker = new SwingWorker<Boolean, String>(){
            ClientResponse response;

            @Override
            protected Boolean doInBackground(){

                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));

                paymentindraft.setCurrency(currencypanel.getComboBoxValue());
                paymentindraft.setDate(datepanel.getCalendar());
                paymentindraft.setDuedate(duedatepanel.getCalendar());
                paymentindraft.setNote(notepanel.getNote());
                paymentindraft.setNumbering(numberingpanel.getComboBoxValue());
                paymentindraft.setPartner(partnerpanel.getComboBoxValue());
                paymentindraft.setRate(ratepanel.getTextFieldValue());
                paymentindraft.setStatus(true);

                response = RestClient.put("updatePaymentInDraft", paymentindraft);

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
    protected void close() {
        SwingWorker<Boolean, String> saveworker = new SwingWorker<Boolean, String>(){
            ClientResponse response;

            @Override
            protected Boolean doInBackground(){

                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));

                response = RestClient.get("closePaymentInDraft?id=" + paymentindraft.getId());

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
    protected void delete() {
        SwingWorker<Boolean, String> saveworker = new SwingWorker<Boolean, String>(){
            ClientResponse response;

            @Override
            protected Boolean doInBackground(){

                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));

                response = RestClient.delete("deletePaymentInDraft?id=" + paymentindraft.getId());

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

        tabbedPane.changeTabPanel(getIndex(), new PaymentInDetail(id));
    }
    
    @Override
    public void refresh(){
        partnerpanel.refresh();
        currencypanel.refresh();
        numberingpanel.refresh();
        load();
    }
}
