/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.purnama.gui.inner.detail.util.RatePanel;
import net.purnama.gui.inner.detail.util.SelectableLabelContentPanel;
import net.purnama.gui.inner.form.CurrencyEdit;
import net.purnama.gui.inner.home.CurrencyHome;
import net.purnama.gui.inner.home.RateHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.CurrencyEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class CurrencyDetail extends DetailPanel{
    
    private CurrencyEntity currency;
    
    private final SelectableLabelContentPanel idpanel, namepanel, codepanel, descriptionpanel,
            defaultpanel, statuspanel;
    
    private final RatePanel ratepanel;
    
    private final String id;
    
    private final RateHome ratehome;
    
    public CurrencyDetail(String id){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_CURRENCYDETAIL"));
        
        this.id = id;
        
        idpanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_ID"),
                "");
        namepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_NAME"),
                "");
        codepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_CODE"),
                "");
        descriptionpanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_DESCRIPTION"),
                "");
        defaultpanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_DEFAULT"),
                "");
        statuspanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_STATUS"),
                "");
        ratepanel = new RatePanel(id);
        
//        detailpanel.add(idpanel);
        detailpanel.add(codepanel);
        detailpanel.add(namepanel);
        detailpanel.add(descriptionpanel);
        detailpanel.add(defaultpanel);
        detailpanel.add(statuspanel);
        
        if(GlobalFields.ROLE.isRate_add()){
            detailpanel.add(ratepanel);
        }
        
        ratehome = new RateHome(id);
        tabbedpane.addTab(GlobalFields.PROPERTIES.getProperty("LABEL_HISTORY"), ratehome);
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getCurrency?id=" + id);
               
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
                        currency = mapper.readValue(output, CurrencyEntity.class);
                        
                        idpanel.setContentValue(currency.getId());
                        codepanel.setContentValue(currency.getCode());
                        namepanel.setContentValue(currency.getName());
                        descriptionpanel.setContentValue(currency.getDescription());
                        defaultpanel.setContentValue(currency.isDefaultcurrency());
                        statuspanel.setContentValue(currency.isStatus());
                        notepanel.setNote(currency.getNote());
                        
                        upperpanel.setStatusLabel(currency.getFormattedLastmodified());
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
        
        tabbedPane.changeTabPanel(getIndex(), new CurrencyHome());
    }

    @Override
    protected void edit() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new CurrencyEdit(id));
    }
    
    @Override
    public void refresh(){
        ratepanel.load();
        load();
    }
}
