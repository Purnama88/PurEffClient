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
import net.purnama.gui.inner.detail.table.ItemExpensesTablePanel;
import net.purnama.gui.inner.detail.util.DatePanel;
import net.purnama.gui.inner.detail.util.LabelDecimalTextFieldPanel;
import net.purnama.gui.inner.detail.util.LabelTextFieldPanel;
import net.purnama.gui.inner.home.ExpensesHome;
import net.purnama.gui.library.MyPanel;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.ExpensesEntity;
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
public class ExpensesDetail extends InvoiceDetailPanel{
    
    private ExpensesEntity expenses;
    
    private final ItemExpensesTablePanel itemexpensestablepanel;
    
    private final String id;
    
    private final LabelTextFieldPanel partnerpanel, currencypanel;
    
    protected final LabelDecimalTextFieldPanel ratepanel;
    
    private final DatePanel duedatepanel;
    
    public ExpensesDetail(String id) {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_EXPENSESDETAIL"));
        
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
        
        itemexpensestablepanel = new ItemExpensesTablePanel(id);
        
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_CONTENT"),
                itemexpensestablepanel);
        
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
                
                response = RestClient.get("getExpenses?id=" + id);
                
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
                        expenses = mapper.readValue(output, ExpensesEntity.class);
                        
                        datepanel.setDate(expenses.getDate());
                        warehousepanel.setTextFieldValue(expenses.getWarehouse_code());
                        draftidpanel.setTextFieldValue(expenses.getDraftid());
                        partnerpanel.setTextFieldValue(expenses.getPartner_name());
                        duedatepanel.setDate(expenses.getDuedate());
                        numberingpanel.setTextFieldValue(expenses.getNumber());
                        numberingpanel.addTextField(expenses.getFormattedstatus(), false);
                        currencypanel.setTextFieldValue(expenses.getCurrency_code());
                        ratepanel.setTextFieldValue(expenses.getRate());
                        
                        expensespanel.setRounding(expenses.getRounding());
                        expensespanel.setFreight(expenses.getFreight());
                        expensespanel.setTax(expenses.getTax());
                        
                        discountsubtotalpanel.setSubtotal(expenses.getSubtotal());
                        discountsubtotalpanel.setDiscount(expenses.getDiscount());
                        
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
        
        tabbedPane.changeTabPanel(getIndex(), new ExpensesHome());
    }

    @Override
    protected void cancel() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("cancelExpenses?id=" + id);
                
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
                
                response = RestClient.get("closeExpenses?id=" + id);
                
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
                    map.put("DATE", expenses.getFormatteddate());
                    map.put("ID", expenses.getNumber());
                    map.put("CURRENCY", expenses.getCurrency_code());
                    map.put("WAREHOUSE", expenses.getWarehouse_code());
                    map.put("NOTE", expenses.getNote());
                    map.put("DUEDATE", expenses.getFormattedDueDate());
                    map.put("PARTNER", expenses.getPartner_name());
                    map.put("ADDRESS", expenses.getPartner_address());
                    map.put("SUBTOTAL", expenses.getFormattedSubtotal());
                    map.put("DISCOUNT", expenses.getFormattedDiscount());
                    map.put("ROUNDING", expenses.getFormattedRounding());
                    map.put("FREIGHT", expenses.getFormattedFreight());
                    map.put("TAX", expenses.getFormattedTax());
                    map.put("SAID", IndonesianNumberConvertion.numberToSaid(expenses.getTotal_after_tax()));
                    map.put("TOTAL", expenses.getFormattedtotal_after_tax());
                    map.put("RATE", expenses.getFormattedRate());    

                    ClassLoader cldr = this.getClass().getClassLoader();
                    URL imageURL = cldr.getResource("net/purnama/template/Expenses.jasper");

                    InputStream is = imageURL.openStream();
                    JasperReport jr = (JasperReport) JRLoader.loadObject(is);

                    JasperPrint jasperPrint = JasperFillManager.fillReport(jr, 
                            map,
                            new JRTableModelDataSource(itemexpensestablepanel.getItemExpensesTableModel()));
                    JRViewer jasperViewer = new JRViewer(jasperPrint);

                    tabbedPane.insertTab(getIndex()+1, 
                            GlobalFields.PROPERTIES.getProperty("LABEL_MENU_EXPENSES") + "-" + 
                                    expenses.getNumber(),
                            jasperViewer);
                    
                } 
                catch (JRException ex) {
                    ex.printStackTrace();
                } 
                catch (IOException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(ExpensesDetail.class.getName()).log(Level.SEVERE, null, ex);
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

                expenses.setNote(notepanel.getNote());
                
                response = RestClient.put("updateExpenses", expenses);

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
                    map.put("DATE", expenses.getFormatteddate());
                    map.put("ID", expenses.getNumber());
                    map.put("CURRENCY", expenses.getCurrency_code());
                    map.put("WAREHOUSE", expenses.getWarehouse_code());
                    map.put("NOTE", expenses.getNote());
                    map.put("DUEDATE", expenses.getFormattedDueDate());
                    map.put("PARTNER", expenses.getPartner_name());
                    map.put("ADDRESS", expenses.getPartner_address());
                    map.put("SUBTOTAL", expenses.getFormattedSubtotal());
                    map.put("DISCOUNT", expenses.getFormattedDiscount());
                    map.put("ROUNDING", expenses.getFormattedRounding());
                    map.put("FREIGHT", expenses.getFormattedFreight());
                    map.put("TAX", expenses.getFormattedTax());
                    map.put("SAID", IndonesianNumberConvertion.numberToSaid(expenses.getTotal_after_tax()));
                    map.put("TOTAL", expenses.getFormattedtotal_after_tax());
                    map.put("RATE", expenses.getFormattedRate());    

                    ClassLoader cldr = this.getClass().getClassLoader();
                    URL imageURL = cldr.getResource("net/purnama/template/Expenses.jasper");

                    InputStream is = imageURL.openStream();
                    JasperReport jr = (JasperReport) JRLoader.loadObject(is);

                    JasperPrint jasperPrint = JasperFillManager.fillReport(jr, 
                            map,
                            new JRTableModelDataSource(itemexpensestablepanel.getItemExpensesTableModel()));
                    File destFile = new File("temp/EX" + expenses.getNumber() + ".pdf");
                    JRPdfExporter exporter = new JRPdfExporter();

                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());

                    exporter.exportReport();
                    
                    MailController mail = new MailController("temp/EX" + expenses.getNumber() + ".pdf");
                } 
                catch (JRException ex) {
                    ex.printStackTrace();
                } 
                catch (IOException ex) {
                    ex.printStackTrace();
                    Logger.getLogger(ExpensesDetail.class.getName()).log(Level.SEVERE, null, ex);
                }

                return true;
            }
        };
            
        worker.execute();
    }
}
