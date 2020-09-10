/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.event.ActionEvent;
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
import net.purnama.controller.MailController;
import net.purnama.convertion.IndonesianNumberConvertion;
import net.purnama.gui.inner.detail.table.ItemInvoicePurchaseTablePanel;
import net.purnama.gui.inner.detail.util.DatePanel;
import net.purnama.gui.inner.detail.util.LabelDecimalTextFieldPanel;
import net.purnama.gui.inner.detail.util.LabelTextFieldPanel;
import net.purnama.gui.inner.home.InvoicePurchaseHome;
import net.purnama.gui.library.MyPanel;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.InvoicePurchaseEntity;
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
public class InvoicePurchaseDetail extends InvoiceDetailPanel{

    private InvoicePurchaseEntity invoicepurchase;
    
    private final ItemInvoicePurchaseTablePanel iteminvoicepurchasetablepanel;
    
    private final String id;
    
    private final LabelTextFieldPanel partnerpanel, currencypanel;
    
    protected final LabelDecimalTextFieldPanel ratepanel;
    
    private final DatePanel duedatepanel;
    
    public InvoicePurchaseDetail(String id) {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_INVOICEPURCHASEDETAIL"));
    
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
        
        iteminvoicepurchasetablepanel = new ItemInvoicePurchaseTablePanel(id);
        
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_CONTENT"),
                iteminvoicepurchasetablepanel);
        
        closebutton.addActionListener((ActionEvent e) -> {
            close();
        });
        
        load();
    }

    @Override
    public void refresh(){
        load();
    }
    
    @Override
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getInvoicePurchase?id=" + id);
                
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
                        invoicepurchase = mapper.readValue(output, InvoicePurchaseEntity.class);
                        
                        notepanel.setNote(invoicepurchase.getNote());
                        datepanel.setDate(invoicepurchase.getDate());
                        warehousepanel.setTextFieldValue(invoicepurchase.getWarehouse_code());
                        draftidpanel.setTextFieldValue(invoicepurchase.getDraftid());
                        partnerpanel.setTextFieldValue(invoicepurchase.getPartner_name());
                        duedatepanel.setDate(invoicepurchase.getDuedate());
                        numberingpanel.setTextFieldValue(invoicepurchase.getNumber());
                        numberingpanel.addTextField(invoicepurchase.getFormattedstatus(), false);
                        currencypanel.setTextFieldValue(invoicepurchase.getCurrency_code());
                        ratepanel.setTextFieldValue(invoicepurchase.getRate());
                        
                        expensespanel.setRounding(invoicepurchase.getRounding());
                        expensespanel.setFreight(invoicepurchase.getFreight());
                        expensespanel.setTax(invoicepurchase.getTax());
                        
                        discountsubtotalpanel.setSubtotal(invoicepurchase.getSubtotal());
                        discountsubtotalpanel.setDiscount(invoicepurchase.getDiscount());
                        
                        expensespanel.setTextFieldEnabled(false);
                        
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
    protected void home() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new InvoicePurchaseHome());
    }

    @Override
    protected void cancel() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("cancelInvoicePurchase?id=" + id);
                
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
    
    protected final void close() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("closeInvoicePurchase?id="+id);
                
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
                    refresh();
                    
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
                    map.put("DATE", invoicepurchase.getFormatteddate());
                    map.put("ID", invoicepurchase.getNumber());
                    map.put("CURRENCY", invoicepurchase.getCurrency_code());
                    map.put("WAREHOUSE", invoicepurchase.getWarehouse_code());
                    map.put("NOTE", invoicepurchase.getNote());
                    map.put("DUEDATE", invoicepurchase.getFormattedDueDate());
                    map.put("PARTNER", invoicepurchase.getPartner_name());
                    map.put("ADDRESS", invoicepurchase.getPartner_address());
                    map.put("SUBTOTAL", invoicepurchase.getFormattedSubtotal());
                    map.put("DISCOUNT", invoicepurchase.getFormattedDiscount());
                    map.put("ROUNDING", invoicepurchase.getFormattedRounding());
                    map.put("FREIGHT", invoicepurchase.getFormattedFreight());
                    map.put("TAX", invoicepurchase.getFormattedTax());
                    map.put("SAID", IndonesianNumberConvertion.numberToSaid(invoicepurchase.getTotal_after_tax()));
                    map.put("TOTAL", invoicepurchase.getFormattedtotal_after_tax());
                    map.put("RATE", invoicepurchase.getFormattedRate());    

                    ClassLoader cldr = this.getClass().getClassLoader();
                        URL imageURL = cldr.getResource("net/purnama/template/InvoicePurchase.jasper");

                    InputStream is = imageURL.openStream();
                    JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                        
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jr, 
                            map,
                            new JRTableModelDataSource(iteminvoicepurchasetablepanel.getItemInvoicePurchaseTableModel()));
                    JRViewer jasperViewer = new JRViewer(jasperPrint);

                    tabbedPane.insertTab(getIndex()+1, 
                            GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICEPURCHASE") + "-" + 
                                    invoicepurchase.getNumber(),
                            jasperViewer);
                } 
                catch (JRException ex) {
                    ex.printStackTrace();
                } 
                catch (IOException ex) {
                    Logger.getLogger(InvoicePurchaseDetail.class.getName()).log(Level.SEVERE, null, ex);
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

                invoicepurchase.setNote(notepanel.getNote());
                
                response = RestClient.put("updateInvoicePurchase", invoicepurchase);

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
                    map.put("DATE", invoicepurchase.getFormatteddate());
                    map.put("ID", invoicepurchase.getNumber());
                    map.put("CURRENCY", invoicepurchase.getCurrency_code());
                    map.put("WAREHOUSE", invoicepurchase.getWarehouse_code());
                    map.put("NOTE", invoicepurchase.getNote());
                    map.put("DUEDATE", invoicepurchase.getFormattedDueDate());
                    map.put("PARTNER", invoicepurchase.getPartner_name());
                    map.put("ADDRESS", invoicepurchase.getPartner_address());
                    map.put("SUBTOTAL", invoicepurchase.getFormattedSubtotal());
                    map.put("DISCOUNT", invoicepurchase.getFormattedDiscount());
                    map.put("ROUNDING", invoicepurchase.getFormattedRounding());
                    map.put("FREIGHT", invoicepurchase.getFormattedFreight());
                    map.put("TAX", invoicepurchase.getFormattedTax());
                    map.put("SAID", IndonesianNumberConvertion.numberToSaid(invoicepurchase.getTotal_after_tax()));
                    map.put("TOTAL", invoicepurchase.getFormattedtotal_after_tax());
                    map.put("RATE", invoicepurchase.getFormattedRate());    

                    ClassLoader cldr = this.getClass().getClassLoader();
                        URL imageURL = cldr.getResource("net/purnama/template/InvoicePurchase.jasper");

                    InputStream is = imageURL.openStream();
                    JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                        
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jr, 
                            map,
                            new JRTableModelDataSource(iteminvoicepurchasetablepanel.getItemInvoicePurchaseTableModel()));
                    File destFile = new File("temp/IP" + invoicepurchase.getNumber() + ".pdf");
                    JRPdfExporter exporter = new JRPdfExporter();

                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());

                    exporter.exportReport();
                    
                    MailController mail = new MailController("temp/IP" + invoicepurchase.getNumber() + ".pdf");
                } 
                catch (JRException ex) {
                    ex.printStackTrace();
                } 
                catch (IOException ex) {
                    Logger.getLogger(InvoicePurchaseDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return true;
            }
        };
        
        worker.execute();
    }
}
