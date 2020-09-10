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
import net.purnama.gui.inner.detail.table.ItemReturnPurchaseTablePanel;
import net.purnama.gui.inner.detail.util.DatePanel;
import net.purnama.gui.inner.detail.util.LabelDecimalTextFieldPanel;
import net.purnama.gui.inner.detail.util.LabelTextFieldPanel;
import net.purnama.gui.inner.home.ReturnPurchaseHome;
import net.purnama.gui.library.MyPanel;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.ReturnPurchaseEntity;
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
public class ReturnPurchaseDetail extends InvoiceDetailPanel{
    
    private ReturnPurchaseEntity returnpurchase;
    
    private final ItemReturnPurchaseTablePanel itemreturnpurchasetablepanel;
    
    private final String id;
    
    private final LabelTextFieldPanel partnerpanel, currencypanel;
    
    protected final LabelDecimalTextFieldPanel ratepanel;
    
    private final DatePanel duedatepanel;
    
    public ReturnPurchaseDetail(String id) {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_RETURNPURCHASEDETAIL"));
        
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
        
        itemreturnpurchasetablepanel = new ItemReturnPurchaseTablePanel(id);
        
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_CONTENT"),
                itemreturnpurchasetablepanel);
        
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
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getReturnPurchase?id=" + id);
                
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
                        returnpurchase = mapper.readValue(output, ReturnPurchaseEntity.class);
                        
                        datepanel.setDate(returnpurchase.getDate());
                        warehousepanel.setTextFieldValue(returnpurchase.getWarehouse_code());
                        draftidpanel.setTextFieldValue(returnpurchase.getDraftid());
                        partnerpanel.setTextFieldValue(returnpurchase.getPartner_name());
                        duedatepanel.setDate(returnpurchase.getDuedate());
                        numberingpanel.setTextFieldValue(returnpurchase.getNumber());
                        numberingpanel.addTextField(returnpurchase.getFormattedstatus(), false);
                        currencypanel.setTextFieldValue(returnpurchase.getCurrency_code());
                        ratepanel.setTextFieldValue(returnpurchase.getRate());
                        
                        expensespanel.setRounding(returnpurchase.getRounding());
                        expensespanel.setFreight(returnpurchase.getFreight());
                        expensespanel.setTax(returnpurchase.getTax());
                        
                        discountsubtotalpanel.setSubtotal(returnpurchase.getSubtotal());
                        discountsubtotalpanel.setDiscount(returnpurchase.getDiscount());
                        
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
        
        tabbedPane.changeTabPanel(getIndex(), new ReturnPurchaseHome());
    }

    @Override
    protected void cancel() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("cancelReturnPurchase?id=" + id);
                
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
                
                response = RestClient.get("closeReturnPurchase?id="+id);
                
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
                    map.put("DATE", returnpurchase.getFormatteddate());
                    map.put("ID", returnpurchase.getNumber());
                    map.put("CURRENCY", returnpurchase.getCurrency_code());
                    map.put("WAREHOUSE", returnpurchase.getWarehouse_code());
                    map.put("NOTE", returnpurchase.getNote());
                    map.put("DUEDATE", returnpurchase.getFormattedDueDate());
                    map.put("PARTNER", returnpurchase.getPartner_name());
                    map.put("ADDRESS", returnpurchase.getPartner_address());
                    map.put("SUBTOTAL", returnpurchase.getFormattedSubtotal());
                    map.put("DISCOUNT", returnpurchase.getFormattedDiscount());
                    map.put("ROUNDING", returnpurchase.getFormattedRounding());
                    map.put("FREIGHT", returnpurchase.getFormattedFreight());
                    map.put("TAX", returnpurchase.getFormattedTax());
                    map.put("SAID", IndonesianNumberConvertion.numberToSaid(returnpurchase.getTotal_after_tax()));
                    map.put("TOTAL", returnpurchase.getFormattedtotal_after_tax());
                    map.put("RATE", returnpurchase.getFormattedRate());    

                    ClassLoader cldr = this.getClass().getClassLoader();
                        URL imageURL = cldr.getResource("net/purnama/template/ReturnPurchase.jasper");

                    InputStream is = imageURL.openStream();
                    JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                        
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jr, 
                            map,
                            new JRTableModelDataSource(itemreturnpurchasetablepanel.getItemReturnPurchaseTableModel()));
                    JRViewer jasperViewer = new JRViewer(jasperPrint);

                    tabbedPane.insertTab(getIndex()+1, 
                            GlobalFields.PROPERTIES.getProperty("LABEL_MENU_RETURNPURCHASE") + "-" + 
                                    returnpurchase.getNumber(),
                            jasperViewer);
                } 
                catch (JRException ex) {
                    ex.printStackTrace();
                } 
                catch (IOException ex) {
                    Logger.getLogger(ReturnPurchaseDetail.class.getName()).log(Level.SEVERE, null, ex);
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

                returnpurchase.setNote(notepanel.getNote());
                
                response = RestClient.put("updateReturnPurchase", returnpurchase);

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
                    map.put("DATE", returnpurchase.getFormatteddate());
                    map.put("ID", returnpurchase.getNumber());
                    map.put("CURRENCY", returnpurchase.getCurrency_code());
                    map.put("WAREHOUSE", returnpurchase.getWarehouse_code());
                    map.put("NOTE", returnpurchase.getNote());
                    map.put("DUEDATE", returnpurchase.getFormattedDueDate());
                    map.put("PARTNER", returnpurchase.getPartner_name());
                    map.put("ADDRESS", returnpurchase.getPartner_address());
                    map.put("SUBTOTAL", returnpurchase.getFormattedSubtotal());
                    map.put("DISCOUNT", returnpurchase.getFormattedDiscount());
                    map.put("ROUNDING", returnpurchase.getFormattedRounding());
                    map.put("FREIGHT", returnpurchase.getFormattedFreight());
                    map.put("TAX", returnpurchase.getFormattedTax());
                    map.put("SAID", IndonesianNumberConvertion.numberToSaid(returnpurchase.getTotal_after_tax()));
                    map.put("TOTAL", returnpurchase.getFormattedtotal_after_tax());
                    map.put("RATE", returnpurchase.getFormattedRate());    

                    ClassLoader cldr = this.getClass().getClassLoader();
                        URL imageURL = cldr.getResource("net/purnama/template/ReturnPurchase.jasper");

                    InputStream is = imageURL.openStream();
                    JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                        
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jr, 
                            map,
                            new JRTableModelDataSource(itemreturnpurchasetablepanel.getItemReturnPurchaseTableModel()));
                    File destFile = new File("temp/RP" + returnpurchase.getNumber() + ".pdf");
                    JRPdfExporter exporter = new JRPdfExporter();

                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());

                    exporter.exportReport();
                    
                    MailController mail = new MailController("temp/RP" + returnpurchase.getNumber() + ".pdf");
                } 
                catch (JRException ex) {
                    ex.printStackTrace();
                } 
                catch (IOException ex) {
                    Logger.getLogger(ReturnPurchaseDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return true;
            }
        };
        
        worker.execute();
    }
}
