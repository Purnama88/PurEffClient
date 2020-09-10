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
import net.purnama.gui.inner.detail.table.ItemInvoiceSalesTablePanel;
import net.purnama.gui.inner.detail.util.DatePanel;
import net.purnama.gui.inner.detail.util.ExportPanel;
import net.purnama.gui.inner.detail.util.LabelDecimalTextFieldPanel;
import net.purnama.gui.inner.detail.util.LabelTextFieldPanel;
import net.purnama.gui.inner.home.InvoiceSalesHome;
import net.purnama.gui.library.MyPanel;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.InvoiceSalesEntity;
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
public class InvoiceSalesDetail extends InvoiceDetailPanel{
    
    private InvoiceSalesEntity invoicesales;
    
    private final ItemInvoiceSalesTablePanel iteminvoicesalestablepanel;
    
    private final String id;
    
    private final LabelTextFieldPanel partnerpanel, currencypanel;
//            statuspanel;
    
    protected final LabelDecimalTextFieldPanel ratepanel;
    
    private final DatePanel duedatepanel;
    
    public InvoiceSalesDetail(String id) {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_INVOICESALESDETAIL"));
        
        this.id = id;
        
        upperpanel.addExportButton();
        
        partnerpanel = new LabelTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_PARTNER"),
            "", false);
        duedatepanel = new DatePanel(
                Calendar.getInstance(),
                GlobalFields.PROPERTIES.getProperty("LABEL_DUEDATE"),
                false);
//        statuspanel = new LabelTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_STATUS"),
//                "", false);
        middledetailpanel.add(partnerpanel);
        middledetailpanel.add(duedatepanel);
//        middledetailpanel.add(statuspanel);
        
        currencypanel = new LabelTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_CURRENCY"),
            "", false);
        ratepanel = new LabelDecimalTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_RATE"),
            1, false);
        rightdetailpanel.add(currencypanel);
        rightdetailpanel.add(ratepanel);
        
        iteminvoicesalestablepanel = new ItemInvoiceSalesTablePanel(id);
        
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_CONTENT"),
                iteminvoicesalestablepanel);
        
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
    public void print(){
        System.out.println("print");
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
                    map.put("DATE", invoicesales.getFormatteddate());
                    map.put("ID", invoicesales.getNumber());
                    map.put("CURRENCY", invoicesales.getCurrency_code());
                    map.put("WAREHOUSE", invoicesales.getWarehouse_code());
                    map.put("NOTE", invoicesales.getNote());
                    map.put("DUEDATE", invoicesales.getFormattedDueDate());
                    map.put("PARTNER", invoicesales.getPartner_name());
                    map.put("ADDRESS", invoicesales.getPartner_address());
                    map.put("SUBTOTAL", invoicesales.getFormattedSubtotal());
                    map.put("DISCOUNT", invoicesales.getFormattedDiscount());
                    map.put("ROUNDING", invoicesales.getFormattedRounding());
                    map.put("FREIGHT", invoicesales.getFormattedFreight());
                    map.put("TAX", invoicesales.getFormattedTax());
                    map.put("SAID", IndonesianNumberConvertion.numberToSaid(invoicesales.getTotal_after_tax()));
                    map.put("TOTAL", invoicesales.getFormattedtotal_after_tax());
                    map.put("RATE", invoicesales.getFormattedRate());    

                    ClassLoader cldr = this.getClass().getClassLoader();
                        URL imageURL = cldr.getResource("net/purnama/template/InvoiceSales.jasper");

                    InputStream is = imageURL.openStream();
                    JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                        
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jr, 
                            map,
                            new JRTableModelDataSource(iteminvoicesalestablepanel.getItemInvoiceSalesTableModel()));

                    JRViewer jasperViewer = new JRViewer(jasperPrint);
                    
                    tabbedPane.insertTab(getIndex()+1, 
                            GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICESALES") + "-" + 
                                    invoicesales.getNumber(),
                            jasperViewer);
                } 
                catch (JRException ex) {
                    ex.printStackTrace();
                } 
                catch (IOException ex) {
                    Logger.getLogger(InvoiceSalesDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return true;
            }
        };
        
        worker.execute();
    }

    @Override
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getInvoiceSales?id=" + id);
                
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
                        invoicesales = mapper.readValue(output, InvoiceSalesEntity.class);
                        
                        datepanel.setDate(invoicesales.getDate());
                        warehousepanel.setTextFieldValue(invoicesales.getWarehouse_code());
                        draftidpanel.setTextFieldValue(invoicesales.getDraftid());
                        partnerpanel.setTextFieldValue(invoicesales.getPartner_name());
                        duedatepanel.setDate(invoicesales.getDuedate());
                        numberingpanel.setTextFieldValue(invoicesales.getNumber());
                        numberingpanel.addTextField(invoicesales.getFormattedstatus(), false);
                        currencypanel.setTextFieldValue(invoicesales.getCurrency_code());
                        ratepanel.setTextFieldValue(invoicesales.getRate());
                        notepanel.setNote(invoicesales.getNote());
                        
                        expensespanel.setRounding(invoicesales.getRounding());
                        expensespanel.setFreight(invoicesales.getFreight());
                        expensespanel.setTax(invoicesales.getTax());
                        
                        discountsubtotalpanel.setSubtotal(invoicesales.getSubtotal());
                        discountsubtotalpanel.setDiscount(invoicesales.getDiscount());
                        
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
        
        tabbedPane.changeTabPanel(getIndex(), new InvoiceSalesHome());
    }

    @Override
    protected void cancel() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("cancelInvoiceSales?id=" + id);
                
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
                
                response = RestClient.get("closeInvoiceSales?id=" + id);
                
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
    protected void save() {
        SwingWorker<Boolean, String> saveworker = new SwingWorker<Boolean, String>(){
            ClientResponse response;

            @Override
            protected Boolean doInBackground(){

                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));

                invoicesales.setNote(notepanel.getNote());
                
                response = RestClient.put("updateInvoiceSales", invoicesales);

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
                    map.put("DATE", invoicesales.getFormatteddate());
                    map.put("ID", invoicesales.getNumber());
                    map.put("CURRENCY", invoicesales.getCurrency_code());
                    map.put("WAREHOUSE", invoicesales.getWarehouse_code());
                    map.put("NOTE", invoicesales.getNote());
                    map.put("DUEDATE", invoicesales.getFormattedDueDate());
                    map.put("PARTNER", invoicesales.getPartner_name());
                    map.put("ADDRESS", invoicesales.getPartner_address());
                    map.put("SUBTOTAL", invoicesales.getFormattedSubtotal());
                    map.put("DISCOUNT", invoicesales.getFormattedDiscount());
                    map.put("ROUNDING", invoicesales.getFormattedRounding());
                    map.put("FREIGHT", invoicesales.getFormattedFreight());
                    map.put("TAX", invoicesales.getFormattedTax());
                    map.put("SAID", IndonesianNumberConvertion.numberToSaid(invoicesales.getTotal_after_tax()));
                    map.put("TOTAL", invoicesales.getFormattedtotal_after_tax());
                    map.put("RATE", invoicesales.getFormattedRate());    

                    ClassLoader cldr = this.getClass().getClassLoader();
                        URL imageURL = cldr.getResource("net/purnama/template/InvoiceSales.jasper");

                    InputStream is = imageURL.openStream();
                    JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                        
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jr, 
                            map,
                            new JRTableModelDataSource(iteminvoicesalestablepanel.getItemInvoiceSalesTableModel()));

                    File destFile = new File("temp/IS" + invoicesales.getNumber() + ".pdf");
                    JRPdfExporter exporter = new JRPdfExporter();

                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());

                    exporter.exportReport();
                    
                    MailController mail = new MailController("temp/IS" + invoicesales.getNumber() + ".pdf");
                      
                } 
                catch (JRException ex) {
                    ex.printStackTrace();
                } 
                catch (IOException ex) {
                    Logger.getLogger(InvoiceSalesDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return true;
            }
        };
        
        worker.execute();
    }
    
//    @Override
//    public void export(){
//        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
//                getAncestorOfClass(JTabbedPane.class, this);
//        
//        tabbedPane.insertTab(getIndex()+1, 
//                new ExportPanel(iteminvoicesalestablepanel.getItemInvoiceSalesTableModel().getItemInvoiceSalesList()));
//    }
}
