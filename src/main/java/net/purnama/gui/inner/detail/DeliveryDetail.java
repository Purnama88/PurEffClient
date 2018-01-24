/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.purnama.controller.MailController;
import net.purnama.gui.inner.detail.table.ItemDeliveryTablePanel;
import net.purnama.gui.inner.detail.util.DestinationPanel;
import net.purnama.gui.inner.detail.util.LabelTextFieldPanel;
import net.purnama.gui.inner.home.DeliveryHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.DeliveryEntity;
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
public class DeliveryDetail extends InvoiceDetailPanel{

    private final LabelTextFieldPanel cartypepanel, carnumberpanel;
    
    private final DestinationPanel destinationpanel;
    
    private final ItemDeliveryTablePanel itemdeliverytablepanel; 
    
    private DeliveryEntity delivery;
    
    private final String id;
    
    public DeliveryDetail(String id) {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_DELIVERYDETAIL"));
        
        this.id = id;
        
        middledetailpanel.setLayout(new GridLayout(1,1));
        middledetailpanel.
                setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(),
                GlobalFields.PROPERTIES.getProperty("LABEL_DESTINATION")));
        destinationpanel = new DestinationPanel("", false, null);
        
        middledetailpanel.add(destinationpanel);
        
        cartypepanel = new LabelTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_CARTYPE"),
            "", false, null);
        carnumberpanel = new LabelTextFieldPanel(GlobalFields.PROPERTIES.getProperty("LABEL_CARNUMBER"),
            "", false, null);
        
        rightdetailpanel.add(cartypepanel);
        rightdetailpanel.add(carnumberpanel);
        
        itemdeliverytablepanel = new ItemDeliveryTablePanel(id);
        
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_CONTENT"),
                itemdeliverytablepanel);
        
        rightbuttonpanel.remove(closebutton);
        
        rightsummarypanel.removeAll();
        
        mailbutton.addActionListener((ActionEvent e) -> {
            mail();
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
                
                response = RestClient.get("getDelivery?id=" + id);
                
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

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        delivery = mapper.readValue(output, DeliveryEntity.class);
                        
                        datepanel.setDate(delivery.getDate());
                        warehousepanel.setTextFieldValue(delivery.getWarehouse().getCode());
                        draftidpanel.setTextFieldValue(delivery.getDraftid());
                        destinationpanel.setDestination(delivery.getDestination());
                        notepanel.setNote(delivery.getNote());
                        numberingpanel.setTextFieldValue(delivery.getNumber());
                        numberingpanel.addTextField(delivery.getFormattedstatus(), false);
                        cartypepanel.setTextFieldValue(delivery.getVehicletype());
                        carnumberpanel.setTextFieldValue(delivery.getVehiclenumber());
                        
                        if(!delivery.isStatus()){
                            upperpanel.setStatusLabel(GlobalFields.PROPERTIES.getProperty("LABEL_CANCELED"));
                        }
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
        
        tabbedPane.changeTabPanel(getIndex(), new DeliveryHome());
    }

    @Override
    protected void cancel() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("cancelDelivery?id=" + id);
                
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
    public void mail(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground(){
                try {
                    HashMap map = new HashMap();
                    map.put("DATE", delivery.getFormatteddate());
                    map.put("ID", delivery.getNumber());
                    map.put("WAREHOUSE", delivery.getWarehouse_code());
                    map.put("NOTE", delivery.getNote());
                    map.put("DESTINATION", delivery.getDestination());
                    map.put("CARTYPE", delivery.getVehicletype());
                    map.put("CARNUMBER", delivery.getVehiclenumber());

                    ClassLoader cldr = this.getClass().getClassLoader();
                    URL imageURL = cldr.getResource("net/purnama/template/Delivery.jasper");

                    InputStream is = imageURL.openStream();
                    JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                    
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jr, 
                            map,
                            new JRTableModelDataSource(itemdeliverytablepanel.getTableModel()));
                    
                    File destFile = new File("temp/DL" + delivery.getNumber() + ".pdf");
                    JRPdfExporter exporter = new JRPdfExporter();

                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());

                    exporter.exportReport();
                    
                    MailController mail = new MailController("temp/DL" + delivery.getNumber() + ".pdf");
                }
                catch (JRException ex) {
                    ex.printStackTrace();
                } 
                catch (IOException ex) {
                    Logger.getLogger(DeliveryDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return true;
            }
        };
        
        worker.execute();
    }
    
    @Override
    public void printpreview() {
        
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            @Override
            protected Boolean doInBackground(){
                try {
                    HashMap map = new HashMap();
                    map.put("DATE", delivery.getFormatteddate());
                    map.put("ID", delivery.getNumber());
                    map.put("WAREHOUSE", delivery.getWarehouse_code());
                    map.put("NOTE", delivery.getNote());
                    map.put("DESTINATION", delivery.getDestination());
                    map.put("CARTYPE", delivery.getVehicletype());
                    map.put("CARNUMBER", delivery.getVehiclenumber());

                    ClassLoader cldr = this.getClass().getClassLoader();
                    URL imageURL = cldr.getResource("net/purnama/template/Delivery.jasper");

                    InputStream is = imageURL.openStream();
                    JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                    
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jr, 
                            map,
                            new JRTableModelDataSource(itemdeliverytablepanel.getTableModel()));
                    
                    JRViewer jasperViewer = new JRViewer(jasperPrint);
                    
                    tabbedPane.insertTab(getIndex()+1, 
                            GlobalFields.PROPERTIES.getProperty("LABEL_MENU_DELIVERY") + "-" + delivery.getNumber(),
                            jasperViewer);
                }
                catch (JRException ex) {
                    ex.printStackTrace();
                } 
                catch (IOException ex) {
                    Logger.getLogger(DeliveryDetail.class.getName()).log(Level.SEVERE, null, ex);
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

                delivery.setNote(notepanel.getNote());
                
                response = RestClient.put("updateDelivery", delivery);

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
}
