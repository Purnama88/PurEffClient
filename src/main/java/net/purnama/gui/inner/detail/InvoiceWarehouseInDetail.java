/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.purnama.controller.MailController;
import net.purnama.gui.inner.detail.table.ItemInvoiceWarehouseInTablePanel;
import net.purnama.gui.inner.detail.util.LabelTextFieldPanel;
import net.purnama.gui.inner.home.InvoiceWarehouseInHome;
import net.purnama.gui.library.MyPanel;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.InvoiceWarehouseInEntity;
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
public class InvoiceWarehouseInDetail extends InvoiceDetailPanel{

    private InvoiceWarehouseInEntity invoicewarehousein;
    
    private final ItemInvoiceWarehouseInTablePanel iteminvoicewarehouseintablepanel;
    
    private final String id;
    
    private final LabelTextFieldPanel shippingnumberpanel, originpanel;
    
    public InvoiceWarehouseInDetail(String id) {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_INVOICEWAREHOUSEINDETAIL"));
                  
        this.id = id;
        
        originpanel = new LabelTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_ORIGIN"),
           "", false);
        
        shippingnumberpanel = new LabelTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_SHIPPINGNO"),
           "", false);
        
        middledetailpanel.add(originpanel);
        middledetailpanel.add(shippingnumberpanel);
        
        iteminvoicewarehouseintablepanel = new ItemInvoiceWarehouseInTablePanel(id);
        
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_CONTENT"),
                iteminvoicewarehouseintablepanel);
        
        rightbuttonpanel.remove(closebutton);
        rightsummarypanel.removeAll();
        
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
                
                response = RestClient.get("getInvoiceWarehouseIn?id=" + id);
                
                return true;
                
            }
            
            @Override
            protected void done() {
                if(response == null){
                    upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                }
                else if(response.getStatus() != 200) {
                    upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                    + response.getStatus());
                }
                else{
                    String output = response.getEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        invoicewarehousein = mapper.readValue(output, InvoiceWarehouseInEntity.class);
                        
                        datepanel.setDate(invoicewarehousein.getDate());
                        warehousepanel.setTextFieldValue(invoicewarehousein.getWarehouse_code());
                        draftidpanel.setTextFieldValue(invoicewarehousein.getDraftid());
                        originpanel.setTextFieldValue(invoicewarehousein.getOrigin_code());
                        shippingnumberpanel.setTextFieldValue(invoicewarehousein.getShipping_number());
                        numberingpanel.setTextFieldValue(invoicewarehousein.getNumber());
                        numberingpanel.addTextField(invoicewarehousein.getFormattedstatus(), false);
                        notepanel.setNote(invoicewarehousein.getNote());
                        
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
        
        tabbedPane.changeTabPanel(getIndex(), new InvoiceWarehouseInHome());
    }

    @Override
    protected void cancel() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("cancelInvoiceWarehouseIn?id=" + id);
                
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
                    map.put("DATE", invoicewarehousein.getFormatteddate());
                    map.put("ID", invoicewarehousein.getNumber());
                    map.put("NOTE", invoicewarehousein.getNote());
                    map.put("ORIGIN", invoicewarehousein.getOrigin_code());
                    map.put("DESTINATION", invoicewarehousein.getWarehouse_code());

                    ClassLoader cldr = this.getClass().getClassLoader();
                        URL imageURL = cldr.getResource("net/purnama/template/InventoryTransfer.jasper");

                    InputStream is = imageURL.openStream();
                    JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                        
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jr, 
                            map,
                            new JRTableModelDataSource(iteminvoicewarehouseintablepanel.
                                    getItemInvoiceWarehouseInTableModel()));
                    JRViewer jasperViewer = new JRViewer(jasperPrint);

                    tabbedPane.insertTab(getIndex()+1, 
                            GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICEWAREHOUSEIN") + "-" + 
                                    invoicewarehousein.getNumber(),
                            jasperViewer);
                } 
                catch (JRException ex) {
                    ex.printStackTrace();
                } 
                catch (IOException ex) {
                    Logger.getLogger(InvoiceWarehouseInDetail.class.getName()).log(Level.SEVERE, null, ex);
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

                invoicewarehousein.setNote(notepanel.getNote());
                
                response = RestClient.put("updateInvoiceWarehouseIn", invoicewarehousein);

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
                    map.put("DATE", invoicewarehousein.getFormatteddate());
                    map.put("ID", invoicewarehousein.getNumber());
                    map.put("NOTE", invoicewarehousein.getNote());
                    map.put("ORIGIN", invoicewarehousein.getOrigin_code());
                    map.put("DESTINATION", invoicewarehousein.getWarehouse_code());

                    ClassLoader cldr = this.getClass().getClassLoader();
                        URL imageURL = cldr.getResource("net/purnama/template/InventoryTransfer.jasper");

                    InputStream is = imageURL.openStream();
                    JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                        
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jr, 
                            map,
                            new JRTableModelDataSource(iteminvoicewarehouseintablepanel.
                                    getItemInvoiceWarehouseInTableModel()));
                    
                    File destFile = new File("temp/II" + invoicewarehousein.getNumber() + ".pdf");
                    JRPdfExporter exporter = new JRPdfExporter();

                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());

                    exporter.exportReport();
                    
                    MailController mail = new MailController("temp/II" + invoicewarehousein.getNumber() + ".pdf");
                } 
                catch (JRException ex) {
                    ex.printStackTrace();
                } 
                catch (IOException ex) {
                    Logger.getLogger(InvoiceWarehouseInDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return true;
            }
        };
        worker.execute();
    }
}
