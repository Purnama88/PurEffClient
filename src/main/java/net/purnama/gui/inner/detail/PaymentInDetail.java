/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.purnama.controller.MailController;
import net.purnama.convertion.IndonesianNumberConvertion;
import net.purnama.gui.dialog.PaymentTypeInDialog;
import net.purnama.gui.inner.detail.table.PaymentInInvoiceTablePanel;
import net.purnama.gui.inner.detail.table.PaymentTypeInTablePanel;
import net.purnama.gui.inner.detail.util.AmountPanel;
import net.purnama.gui.inner.detail.util.DatePanel;
import net.purnama.gui.inner.detail.util.LabelDecimalTextFieldPanel;
import net.purnama.gui.inner.detail.util.LabelTextFieldPanel;
import net.purnama.gui.inner.detail.util.PaidPanel;
import net.purnama.gui.inner.detail.util.RemainingPanel;
import net.purnama.gui.inner.home.PaymentInHome;
import net.purnama.gui.library.MyPanel;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.PaymentInEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Purnama
 */
public class PaymentInDetail extends InvoiceDetailPanel{
    private PaymentInEntity paymentin;
    
    private final PaymentTypeInTablePanel paymenttypeintablepanel;
    private final PaymentInInvoiceTablePanel paymentininvoicetablepanel;
    
    private final String id;
    
    private final LabelTextFieldPanel partnerpanel, currencypanel;
    
    protected final LabelDecimalTextFieldPanel ratepanel;
    
    protected final AmountPanel amountpanel;
    protected final PaidPanel paidpanel;
    protected final RemainingPanel remainingpanel;
    
    private final DatePanel duedatepanel;
    
    public PaymentInDetail(String id) {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_PAYMENTINDETAIL"));
        
        this.id = id;
        
        partnerpanel = new LabelTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_PARTNER"),
            "", false);
        duedatepanel = new DatePanel(
                Calendar.getInstance(),
                GlobalFields.PROPERTIES.getProperty("LABEL_DUEDATE"),
                false);
        middledetailpanel.add(partnerpanel);
        middledetailpanel.add(duedatepanel);
        
        currencypanel = new LabelTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_CURRENCY"),
            "", false);
        ratepanel = new LabelDecimalTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_RATE"),
            1, false);
        rightdetailpanel.add(currencypanel);
        rightdetailpanel.add(ratepanel);
        
        paymenttypeintablepanel = new PaymentTypeInTablePanel(id);
        paymentininvoicetablepanel = new PaymentInInvoiceTablePanel(id);
        
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_CONTENT"),
                paymenttypeintablepanel);
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_INVOICE"),
                paymentininvoicetablepanel);
        tabbedpane.setSelectedIndex(0);
        
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
        
        rightsummarypanel.removeAll();
        
        rightsummarypanel.add(amountpanel);
        rightsummarypanel.add(paidpanel);
        rightsummarypanel.add(remainingpanel);
        
        rightbuttonpanel.remove(closebutton);
        
        load();
        
        amountpanel.getPaymentButton().addActionListener((ActionEvent e) -> {
            PaymentTypeInDialog d = new PaymentTypeInDialog(id, paymentin.getAmount(), paymentin.isStatus());
            d.addWindowListener(new WindowAdapter(){
               @Override
               public void windowClosed(WindowEvent e){
                   paymenttypeintablepanel.load();
               }
            });
            d.showDialog();
        });
        
        savebutton.addActionListener((ActionEvent e) -> {
            save();
            paymenttypeintablepanel.save();
        });
    }

    @Override
    public void refresh(){
        load();
        paymenttypeintablepanel.load();
        paymentininvoicetablepanel.load();
    }
    
    @Override
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getPaymentIn?id=" + id);
                
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
                        paymentin = mapper.readValue(output, PaymentInEntity.class);
                        
                        datepanel.setDate(paymentin.getDate());
                        warehousepanel.setTextFieldValue(paymentin.getWarehouse_code());
                        draftidpanel.setTextFieldValue(paymentin.getDraftid());
                        partnerpanel.setTextFieldValue(paymentin.getPartner_name());
                        duedatepanel.setDate(paymentin.getDuedate());
                        numberingpanel.setTextFieldValue(paymentin.getNumber());
                        numberingpanel.addTextField(paymentin.getFormattedstatus(), false);
                        currencypanel.setTextFieldValue(paymentin.getCurrency_code());
                        ratepanel.setTextFieldValue(paymentin.getRate());
                        amountpanel.setAmount(paymentin.getAmount());
                        
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
        
        setState(MyPanel.SAVED);
    }
    
    @Override
    protected void home() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new PaymentInHome());
    }

    @Override
    protected void cancel() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("cancelPaymentIn?id=" + id);
                
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
                    
                    upperpanel.setStatusLabel(GlobalFields.PROPERTIES.getProperty("LABEL_CANCELED"));
                    
                }
            }
        };
        
        worker.execute();
    }
    
    @Override
    public void printpreview(){
        
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground(){
        
                try {
                    HashMap map = new HashMap();
                    map.put("DATE", paymentin.getFormatteddate());
                    map.put("ID", paymentin.getNumber());
                    map.put("CURRENCY", paymentin.getCurrency_code());
                    map.put("WAREHOUSE", paymentin.getWarehouse_code());
                    map.put("NOTE", paymentin.getNote());
                    map.put("PARTNER", paymentin.getPartner_name());
                    map.put("ADDRESS", paymentin.getPartner_address());
                    map.put("SAID", IndonesianNumberConvertion.numberToSaid(paymentin.getAmount()));
                    map.put("RATE", paymentin.getFormattedrate());
                    map.put("AMOUNT", paymentin.getFormattedamount());

                    ClassLoader cldr = this.getClass().getClassLoader();
                        URL imageURL = cldr.getResource("net/purnama/template/PaymentIn.jasper");

                    InputStream is = imageURL.openStream();
                    JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                        
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jr, 
                            map,
                            new JRTableModelDataSource(paymenttypeintablepanel.
                                    getPaymentTypeInTableModel()));
                    JRViewer jasperViewer = new JRViewer(jasperPrint);

                    tabbedPane.insertTab(getIndex()+1, 
                            GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PAYMENTIN") + "-" + 
                                    paymentin.getNumber(),
                            jasperViewer);
                } 
                catch (JRException ex) {
                    ex.printStackTrace();
                } 
                catch (IOException ex) {
                    Logger.getLogger(PaymentInDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return true;
            }
        };
        
        worker.execute();
    }
    
    @Override
    protected void save() {
        SwingWorker<Boolean, String> saveworker = new SwingWorker<Boolean, String>(){
            ClientResponse response;

            @Override
            protected Boolean doInBackground(){

                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));

                paymentin.setNote(notepanel.getNote());
                
                response = RestClient.put("updatePaymentIn", paymentin);

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
    protected void mail() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground(){
        
                try {
                    HashMap map = new HashMap();
                    map.put("DATE", paymentin.getFormatteddate());
                    map.put("ID", paymentin.getNumber());
                    map.put("CURRENCY", paymentin.getCurrency_code());
                    map.put("WAREHOUSE", paymentin.getWarehouse_code());
                    map.put("NOTE", paymentin.getNote());
                    map.put("PARTNER", paymentin.getPartner_name());
                    map.put("ADDRESS", paymentin.getPartner_address());
                    map.put("SAID", IndonesianNumberConvertion.numberToSaid(paymentin.getAmount()));
                    map.put("RATE", paymentin.getFormattedrate());
                    map.put("AMOUNT", paymentin.getFormattedamount());

                    ClassLoader cldr = this.getClass().getClassLoader();
                        URL imageURL = cldr.getResource("net/purnama/template/PaymentIn.jasper");

                    InputStream is = imageURL.openStream();
                    JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                        
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jr, 
                            map,
                            new JRTableModelDataSource(paymenttypeintablepanel.
                                    getPaymentTypeInTableModel()));
                    File destFile = new File("temp/PI" + paymentin.getNumber() + ".pdf");
                    JRPdfExporter exporter = new JRPdfExporter();

                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());

                    exporter.exportReport();
                    
                    MailController mail = new MailController("temp/PI" + paymentin.getNumber() + ".pdf");
                } 
                catch (JRException ex) {
                    ex.printStackTrace();
                } 
                catch (IOException ex) {
                    Logger.getLogger(PaymentInDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return true;
            }
        };
        
        worker.execute();
    }
}
