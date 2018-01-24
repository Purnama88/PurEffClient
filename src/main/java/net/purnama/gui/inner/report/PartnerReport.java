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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.purnama.gui.inner.form.util.PartnerTypeComboBoxPanel;
import net.purnama.gui.inner.form.util.StatusPanel;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.PartnerEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.PartnerTableModel;
import net.purnama.util.GlobalFields;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRTableModelDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.swing.JRViewer;

/**
 *
 * @author Purnama
 */
public class PartnerReport extends ReportPanel{
    
    private final PartnerTypeComboBoxPanel partnertypepanel;
    
    private final StatusPanel statuspanel;
    
    public PartnerReport(int index){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_PARTNER"), index);
        
        partnertypepanel = new PartnerTypeComboBoxPanel();
        
        statuspanel = new StatusPanel(GlobalFields.PROPERTIES.getProperty("LABEL_STATUS"));

        add(partnertypepanel);
        add(statuspanel);
        add(submitpanel);
    }

    @Override
    protected void submit() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse clientresponse;
            
            @Override
            protected Boolean doInBackground() {

                submitpanel.loading();

                clientresponse = RestClient.get("getPartnerList?"
                        +"partnertypeid=" + partnertypepanel.getComboBoxValue().getId()
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
                        ArrayList<PartnerEntity> list = mapper.readValue(output,
                            TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                    PartnerEntity.class));
                        
                        HashMap map = new HashMap();
                        map.put("DATE", GlobalFields.DATEFORMAT.format(new Date()));
                        map.put("PARTNERTYPE", partnertypepanel.getComboBoxValue().getName());
                        if(statuspanel.getSelectedValue()){
                            map.put("STATUS", "ACTIVE");
                        }
                        else{
                            map.put("STATUS", "INACTIVE");
                        }

                        ClassLoader cldr = this.getClass().getClassLoader();
                        URL imageURL = cldr.getResource("net/purnama/template/PartnerBalanceReport.jasper");

                        InputStream is = imageURL.openStream();
                        JasperReport jr = (JasperReport) JRLoader.loadObject(is);

                        JasperPrint jasperPrint = JasperFillManager.fillReport(jr,
                            map, new JRTableModelDataSource(new PartnerTableModel(list)));
                        JRViewer jasperViewer = new JRViewer(jasperPrint);
                        
                        tabbedPane.insertTab(index+1,
                            GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_PARTNER"),
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
