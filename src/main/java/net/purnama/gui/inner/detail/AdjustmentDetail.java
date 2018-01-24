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
import net.purnama.gui.inner.detail.table.ItemAdjustmentTablePanel;
import net.purnama.gui.inner.home.AdjustmentHome;
import net.purnama.gui.library.MyPanel;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.AdjustmentEntity;
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
public class AdjustmentDetail extends InvoiceDetailPanel{

    private AdjustmentEntity adjustment;
    
    private final ItemAdjustmentTablePanel itemadjustmenttablepanel;
    
    private final String id;
    
    public AdjustmentDetail(String id) {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_ADJUSTMENTDETAIL"));
        
        this.id = id;
        
        itemadjustmenttablepanel = new ItemAdjustmentTablePanel(id);
        
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_CONTENT"),
                itemadjustmenttablepanel);
        
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
                
                response = RestClient.get("getAdjustment?id=" + id);
                
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
                        adjustment = mapper.readValue(output, AdjustmentEntity.class);
                        
                        datepanel.setDate(adjustment.getDate());
                        warehousepanel.setTextFieldValue(adjustment.getWarehouse_code());
                        draftidpanel.setTextFieldValue(adjustment.getDraftid());
                        numberingpanel.setTextFieldValue(adjustment.getNumber());
                        numberingpanel.addTextField(adjustment.getFormattedstatus(), false);
                        notepanel.setNote(adjustment.getNote());
                        
                        setState(MyPanel.SAVED);
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
        
        tabbedPane.changeTabPanel(getIndex(), new AdjustmentHome());
    }

    @Override
    protected void cancel() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("cancelAdjustment?id=" + id);
                
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
                    map.put("DATE", adjustment.getFormatteddate());
                    map.put("ID", adjustment.getNumber());
                    map.put("WAREHOUSE", adjustment.getWarehouse_code());
                    map.put("NOTE", adjustment.getNote());

                    ClassLoader cldr = this.getClass().getClassLoader();
                    URL imageURL = cldr.getResource("net/purnama/template/Adjustment.jasper");

                    InputStream is = imageURL.openStream();
                    JasperReport jr = (JasperReport) JRLoader.loadObject(is);

                    JasperPrint jasperPrint = JasperFillManager.fillReport(jr,
                            map,
                            new JRTableModelDataSource(itemadjustmenttablepanel.getItemAdjustmentTableModel()));
                    JRViewer jasperViewer = new JRViewer(jasperPrint);

                    tabbedPane.insertTab(getIndex()+1, 
                            GlobalFields.PROPERTIES.getProperty("LABEL_MENU_ADJUSTMENT") + "-" + 
                                    adjustment.getNumber(),
                            jasperViewer);
                } 
                catch (JRException ex) {
                    ex.printStackTrace();
                } 
                catch (IOException ex) {
                    Logger.getLogger(AdjustmentDetail.class.getName()).log(Level.SEVERE, null, ex);
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

                adjustment.setNote(notepanel.getNote());
                
                response = RestClient.put("updateAdjustment", adjustment);

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
                    map.put("DATE", adjustment.getFormatteddate());
                    map.put("ID", adjustment.getNumber());
                    map.put("WAREHOUSE", adjustment.getWarehouse_code());
                    map.put("NOTE", adjustment.getNote());

                    ClassLoader cldr = this.getClass().getClassLoader();
                    URL imageURL = cldr.getResource("net/purnama/template/Adjustment.jasper");

                    InputStream is = imageURL.openStream();
                    JasperReport jr = (JasperReport) JRLoader.loadObject(is);

                    JasperPrint jasperPrint = JasperFillManager.fillReport(jr,
                            map,
                            new JRTableModelDataSource(itemadjustmenttablepanel.getItemAdjustmentTableModel()));
                    
                    File destFile = new File("temp/AJ" + adjustment.getNumber() + ".pdf");
                    JRPdfExporter exporter = new JRPdfExporter();

                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFile.toString());

                    exporter.exportReport();
                    
                    MailController mail = new MailController("temp/AJ" + adjustment.getNumber() + ".pdf");
                            
                } 
                catch (JRException ex) {
                    ex.printStackTrace();
                } 
                catch (IOException ex) {
                    Logger.getLogger(AdjustmentDetail.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                return true;
            }
        };
                
        worker.execute();
    }
}
