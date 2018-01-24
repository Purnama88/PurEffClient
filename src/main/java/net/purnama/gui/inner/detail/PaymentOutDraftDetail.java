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
import net.purnama.gui.dialog.PaymentTypeOutDraftDialog;
import net.purnama.gui.inner.detail.table.PaymentOutDraftTablePanel;
import net.purnama.gui.inner.detail.util.AmountPanel;
import net.purnama.gui.inner.detail.util.CurrencyComboBoxPanel;
import net.purnama.gui.inner.detail.util.DatePanel;
import net.purnama.gui.inner.detail.util.LabelDecimalTextFieldPanel;
import net.purnama.gui.inner.detail.util.NumberingComboBoxPanel;
import net.purnama.gui.inner.detail.util.PaidPanel;
import net.purnama.gui.inner.detail.util.PartnerComboBoxPanel;
import net.purnama.gui.inner.detail.util.RemainingPanel;
import net.purnama.gui.inner.home.PaymentOutDraftHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.draft.PaymentOutDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PaymentOutDraftDetail extends InvoiceDraftDetailPanel{

    private final DatePanel duedatepanel;
    
    private final LabelDecimalTextFieldPanel ratepanel;
    
    private final AmountPanel amountpanel;
    private final PaidPanel paidpanel;
    private final RemainingPanel remainingpanel;
    
    private final NumberingComboBoxPanel numberingpanel;
    private final PartnerComboBoxPanel partnerpanel;
    private final CurrencyComboBoxPanel currencypanel;
    
    private final PaymentOutDraftTablePanel paymentoutdrafttablepanel;
    
    private PaymentOutDraftEntity paymentoutdraft;
    
    private final String id;
    
    public PaymentOutDraftDetail(String id) {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_PAYMENTOUTDRAFTDETAIL"));
        
        this.id = id;
        
        partnerpanel = new PartnerComboBoxPanel(true, true, true);
        duedatepanel = new DatePanel(
                Calendar.getInstance(),
                GlobalFields.PROPERTIES.getProperty("LABEL_DUEDATE"),
                true);
        middledetailpanel.add(partnerpanel);
        middledetailpanel.add(duedatepanel);
        
        numberingpanel = new NumberingComboBoxPanel(16);
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

                remainingpanel.setRemaining(total);
            }
        };
        
        amountpanel = new AmountPanel();
        paidpanel = new PaidPanel();
        remainingpanel = new RemainingPanel();
        
        amountpanel.setDocumentListener(documentListener);
        paidpanel.setDocumentListener(documentListener);
        
        paymentoutdrafttablepanel = new PaymentOutDraftTablePanel(id, paidpanel);
        
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_CONTENT"),
                paymentoutdrafttablepanel);
        
        rightsummarypanel.removeAll();
        
        rightsummarypanel.add(paidpanel);
        rightsummarypanel.add(amountpanel);
        rightsummarypanel.add(remainingpanel);
        
        savebutton.addActionListener((ActionEvent e) -> {
            save();
            paymentoutdrafttablepanel.saveselectedpaymentexpenses();
            paymentoutdrafttablepanel.saveselectedpaymentinvoice();
            paymentoutdrafttablepanel.saveselectedpaymentreturn();
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
           PaymentTypeOutDraftDialog d = new PaymentTypeOutDraftDialog(id, paymentoutdraft.getAmount(),paidpanel.getPaid());
           double result = d.showDialog();
           paymentoutdraft.setAmount(result);
           amountpanel.setAmount(result);
           save();
        });
        
        partnerpanel.getComboBox().addActionListener((ActionEvent e) -> {
           
            paymentoutdrafttablepanel.clear();
            paymentoutdrafttablepanel.load(partnerpanel.getComboBoxValue().getId(),
                    currencypanel.getComboBoxValue().getId());
            
        });
        
        load();
    }

    @Override
    protected void home() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new PaymentOutDraftHome());
    }

    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getPaymentOutDraft?id=" + id);
                
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
                        paymentoutdraft = mapper.readValue(output, PaymentOutDraftEntity.class);
                        
                        notepanel.setNote(paymentoutdraft.getNote());
                        datepanel.setDate(paymentoutdraft.getDate());
                        warehousepanel.setTextFieldValue(paymentoutdraft.getWarehouse().getCode());
                        idpanel.setTextFieldValue(paymentoutdraft.getId());
                        partnerpanel.setComboBoxValue(paymentoutdraft.getPartner());
                        duedatepanel.setDate(paymentoutdraft.getDuedate());
                        numberingpanel.setComboBoxValue(paymentoutdraft.getNumbering());
                        currencypanel.setComboBoxValue(paymentoutdraft.getCurrency());
                        ratepanel.setTextFieldValue(paymentoutdraft.getRate());
                        amountpanel.setAmount(paymentoutdraft.getAmount());
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

                paymentoutdraft.setCurrency(currencypanel.getComboBoxValue());
                paymentoutdraft.setDate(datepanel.getCalendar());
                paymentoutdraft.setDuedate(duedatepanel.getCalendar());
                paymentoutdraft.setNote(notepanel.getNote());
                paymentoutdraft.setNumbering(numberingpanel.getComboBoxValue());
                paymentoutdraft.setPartner(partnerpanel.getComboBoxValue());
                paymentoutdraft.setRate(ratepanel.getTextFieldValue());
                paymentoutdraft.setStatus(true);

                response = RestClient.put("updatePaymentOutDraft", paymentoutdraft);

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

                response = RestClient.get("closePaymentOutDraft?id=" + paymentoutdraft.getId());

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

                response = RestClient.delete("deletePaymentOutDraft?id=" + paymentoutdraft.getId());

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

        tabbedPane.changeTabPanel(getIndex(), new PaymentOutDetail(id));
    }
    
    @Override
    public void refresh(){
        partnerpanel.refresh();
        currencypanel.refresh();
        numberingpanel.refresh();
        load();
    }
    
}
