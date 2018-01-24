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
import net.purnama.gui.inner.form.util.StringComboBoxPanel;
import net.purnama.gui.inner.form.util.WarehouseComboBoxPanel;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.PaymentTypeInEntity;
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
public class PaymentTypeInReport extends ReportPanel{

    private final DatePanel startdatepanel, enddatepanel;
    
    private final StatusPanel acceptpanel, validpanel;
    
    private final WarehouseComboBoxPanel warehousepanel;
    
    private final PartnerComboBoxPanel partnerpanel;
    
    private final CurrencyComboBoxPanel currencypanel;
    
    private final StringComboBoxPanel typepanel;
    
    public PaymentTypeInReport(int index) {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_PAYMENTTYPEIN"), index);
        
        startdatepanel = new DatePanel(
                GlobalFields.PROPERTIES.getProperty("LABEL_START"),
                Calendar.getInstance()
               );
        
        enddatepanel = new DatePanel(
                GlobalFields.PROPERTIES.getProperty("LABEL_END"),
                Calendar.getInstance()
                );
        
        warehousepanel = new WarehouseComboBoxPanel();
        
        partnerpanel = new PartnerComboBoxPanel(true, true, true);
        
        currencypanel = new CurrencyComboBoxPanel();
        
        acceptpanel = new StatusPanel(GlobalFields.PROPERTIES.getProperty("LABEL_ACCEPTED"));
        acceptpanel.setButtonGroupLabel(GlobalFields.PROPERTIES.getProperty("LABEL_ACTIVE"),
                GlobalFields.PROPERTIES.getProperty("LABEL_INACTIVE"));
        
        validpanel = new StatusPanel(GlobalFields.PROPERTIES.getProperty("LABEL_VALID"));
        validpanel.setButtonGroupLabel(GlobalFields.PROPERTIES.getProperty("LABEL_ACTIVE"),
                GlobalFields.PROPERTIES.getProperty("LABEL_INACTIVE"));
        
        typepanel= new StringComboBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_TYPE"), PaymentTypeInEntity.PAYMENT_TYPE);
        typepanel.addItem(GlobalFields.PROPERTIES.getProperty("LABEL_ALL"));
        
        add(startdatepanel);
        add(enddatepanel);
        add(typepanel);
        add(warehousepanel);
        add(partnerpanel);
        add(currencypanel);
        add(acceptpanel);
        add(validpanel);
        add(submitpanel);
    }

    @Override
    protected void submit() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse clientresponse;
            String start = new SimpleDateFormat("MMddyyyy").format(startdatepanel.getCalendar().getTime());
            String end = new SimpleDateFormat("MMddyyyy").format(enddatepanel.getCalendar().getTime());
            
            @Override
            protected Boolean doInBackground() {

                submitpanel.loading();

                clientresponse = RestClient.get("getPaymentTypeInList?startdate=" + start
                        + "&enddate=" + end
                        + "&warehouseid=" + warehousepanel.getComboBoxValue().getId()
                        + "&partnerid=" + partnerpanel.getComboBoxValue().getId()
                        + "&currencyid=" + currencypanel.getComboBoxValue().getId()
                        + "&accepted=" + acceptpanel.getSelectedValue()
                        + "&valid=" + validpanel.getSelectedValue()
                        + "&type=" + typepanel.getSelectedIndex());

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
                        ArrayList<PaymentTypeInEntity> list = mapper.readValue(output,
                            TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                    PaymentTypeInEntity.class));
                        
                            double total = 0;

                            for(PaymentTypeInEntity invoice : list){
                                total += invoice.getAmount();
                            }

                            JRBeanCollectionDataSource beanColDataSource =
                                new JRBeanCollectionDataSource(list);

                            Map parameters = new HashMap();

                            parameters.put("WAREHOUSE", warehousepanel.getComboBoxValue().getCode());
                            parameters.put("START", GlobalFields.DATEFORMAT.format(startdatepanel.getCalendar().getTime()));
                            parameters.put("END", GlobalFields.DATEFORMAT.format(enddatepanel.getCalendar().getTime()));
                            parameters.put("DATE", GlobalFields.DATEFORMAT.format(new Date()));
                            parameters.put("CURRENCY", currencypanel.getComboBoxValue().getCode());
                            parameters.put("NUMOFINVOICES", String.valueOf(list.size()));
                            parameters.put("TOTAL", GlobalFields.NUMBERFORMAT.format(total));
                            parameters.put("VALID", String.valueOf(validpanel.getSelectedValue()));
                            parameters.put("ACCEPTED", String.valueOf(acceptpanel.getSelectedValue()));
                            
                            ClassLoader cldr = this.getClass().getClassLoader();
                            URL imageURL = cldr.getResource("net/purnama/template/PaymentTypeInReport.jasper");

                            InputStream is = imageURL.openStream();
                            JasperReport jr = (JasperReport) JRLoader.loadObject(is);

                            JasperPrint jasperPrint = JasperFillManager.fillReport(
                            jr, parameters, beanColDataSource);

                            JRViewer jasperViewer = new JRViewer(jasperPrint);
                                    
                            tabbedPane.insertTab(index+1,
                                GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_PAYMENTINDETAIL"),
                                jasperViewer);
                    }
                    catch(IOException e){

                    } catch (JRException ex) {
                        Logger.getLogger(PaymentInReport.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        };

        worker.execute();
    }
    
}
