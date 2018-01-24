/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.report;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.purnama.gui.inner.form.util.CurrencyComboBoxPanel;
import net.purnama.gui.inner.form.util.DatePanel;
import net.purnama.gui.inner.form.util.PartnerComboBoxPanel;
import net.purnama.gui.inner.form.util.StatusPanel;
import net.purnama.gui.inner.form.util.WarehouseComboBoxPanel;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.ItemInvoiceSalesEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Purnama
 */
public class InvoiceSalesDetailReport extends ReportPanel{
    
    private final CurrencyComboBoxPanel currencypanel;
    private final WarehouseComboBoxPanel warehousepanel;
    private final PartnerComboBoxPanel partnerpanel;
    private final DatePanel startdatepanel, enddatepanel;
    private final StatusPanel statuspanel;
    
    public InvoiceSalesDetailReport(int index){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_INVOICESALESDETAIL"), index);
        
        partnerpanel = new PartnerComboBoxPanel(true, false, false);
        
        warehousepanel = new WarehouseComboBoxPanel();
        
        currencypanel = new CurrencyComboBoxPanel();
        
        startdatepanel = new DatePanel(
                GlobalFields.PROPERTIES.getProperty("LABEL_START"),
                Calendar.getInstance()
               );
        
        enddatepanel = new DatePanel(
                GlobalFields.PROPERTIES.getProperty("LABEL_END"),
                Calendar.getInstance()
                );
        
        statuspanel = new StatusPanel(GlobalFields.PROPERTIES.getProperty("LABEL_STATUS"));
        statuspanel.setButtonGroupLabel(GlobalFields.PROPERTIES.getProperty("LABEL_ACTIVE"),
                GlobalFields.PROPERTIES.getProperty("LABEL_CANCELED"));
        
        add(startdatepanel);
        add(enddatepanel);
        add(warehousepanel);
        add(partnerpanel);
        add(currencypanel);
        add(statuspanel);
        add(submitpanel);
    }

    @Override
    protected void submit() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse clientresponse;
            String start = new SimpleDateFormat("MMddyyyy").format(startdatepanel.getCalendar().getTime().getTime());
            String end = new SimpleDateFormat("MMddyyyy").format(enddatepanel.getCalendar().getTime().getTime());
            
            @Override
            protected Boolean doInBackground() {

                submitpanel.loading();

                clientresponse = RestClient.get("getItemInvoiceSalesList?startdate=" + start
                        + "&enddate=" + end
                        + "&warehouseid=" + warehousepanel.getComboBoxValue().getId()
                        + "&partnerid=" + partnerpanel.getComboBoxValue().getId()
                        + "&currencyid=" + currencypanel.getComboBoxValue().getId()
                        + "&status=" + statuspanel.getSelectedValue());

                return true;
            }
            
            @Override
            protected void done() {
                submitpanel.finish();

                if(clientresponse == null){
                }
                else if(clientresponse.getStatus() != 200) {
                    String output = clientresponse.getEntity(String.class);

                    JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                    output, GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                    + clientresponse.getStatus(), 
                    JOptionPane.ERROR_MESSAGE);
                }
                else{
                    String output = clientresponse.getEntity(String.class);
                    
                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        ArrayList<ItemInvoiceSalesEntity> list = mapper.readValue(output,
                            TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                    ItemInvoiceSalesEntity.class));
                        
                        JRBeanCollectionDataSource beanColDataSource =
                            new JRBeanCollectionDataSource(list);

                        Map parameters = new HashMap();
                        
                        parameters.put("WAREHOUSE", warehousepanel.getComboBoxValue().getCode());
                        parameters.put("START", GlobalFields.DATEFORMAT.format(startdatepanel.getCalendar().getTime().getTime()));
                        parameters.put("END", GlobalFields.DATEFORMAT.format(enddatepanel.getCalendar().getTime().getTime()));
                        parameters.put("DATE", GlobalFields.DATEFORMAT.format(new Date()));
                        parameters.put("CURRENCY", currencypanel.getComboBoxValue().getCode());
                        
                        ClassLoader cldr = this.getClass().getClassLoader();
                        URL imageURL = cldr.getResource("net/purnama/template/InvoiceSalesDetailReport.jasper");
                          
                        InputStream is = imageURL.openStream();
                        JasperReport jr = (JasperReport) JRLoader.loadObject(is);
                        
                        JasperPrint jasperPrint = JasperFillManager.fillReport(
                        jr, parameters, beanColDataSource);
                        
                        JRViewer jasperViewer = new JRViewer(jasperPrint);
                        tabbedPane.insertTab(index+1,
                            GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_INVOICESALESDETAIL"),
                            jasperViewer);
                    }
                    catch(IOException e){

                    } 
                    catch (JRException ex) {
                        Logger.getLogger(InvoiceSalesReport.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };
        worker.execute();
    }
}

