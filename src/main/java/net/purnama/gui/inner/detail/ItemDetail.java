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
import net.purnama.gui.inner.detail.table.BuyPriceTablePanel;
import net.purnama.gui.inner.detail.table.PriceTablePanel;
import net.purnama.gui.inner.detail.table.SellPriceTablePanel;
import net.purnama.gui.inner.detail.util.SelectableLabelContentPanel;
import net.purnama.gui.inner.form.ItemEdit;
import net.purnama.gui.inner.home.ItemHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.ItemEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ItemDetail extends DetailPanel{
    
    private ItemEntity item;
    
    private final SelectableLabelContentPanel idpanel, codepanel, namepanel, itemgrouppanel,
            buyuompanel, selluompanel, costpanel, statuspanel;
    
    private final PriceTablePanel pricepanel;
    
    private final String id;
    
    public ItemDetail(String id){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_ITEMDETAIL"));
        
        this.id = id;
        
        idpanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_ID"),
                "");
        namepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_NAME"),
                "");
        codepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_CODE"),
                "");
        itemgrouppanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_ITEMGROUP"),
                "");
        buyuompanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_BUYUOM"),
                "");
        selluompanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_SELLUOM"),
                "");
        costpanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_COST"),
                "");
        statuspanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_STATUS"),
                "");
        
        pricepanel = new PriceTablePanel(id);
        
//        detailpanel.add(idpanel);
        detailpanel.add(codepanel);
        detailpanel.add(namepanel);
        detailpanel.add(itemgrouppanel);
        detailpanel.add(buyuompanel);
        detailpanel.add(selluompanel);
        detailpanel.add(costpanel);
        detailpanel.add(statuspanel);
        detailpanel.add(pricepanel);
        
        load();
    }

    @Override
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getItem?id=" + id);
               
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
                        item = mapper.readValue(output, ItemEntity.class);
                        
                        idpanel.setContentValue(item.getId());
                        codepanel.setContentValue(item.getCode());
                        namepanel.setContentValue(item.getName());
                        itemgrouppanel.setContentValue(item.getItemgroup().getName());
                        buyuompanel.setContentValue(item.getBuyuom().getName());
                        selluompanel.setContentValue(item.getSelluom().getName());
                        costpanel.setContentValue(item.getFormattedCost());
                        statuspanel.setContentValue(item.isStatus());
                        notepanel.setNote(item.getNote());
                        
                        upperpanel.setStatusLabel(item.getFormattedLastmodified());
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
        
        tabbedPane.changeTabPanel(getIndex(), new ItemHome());
    }

    @Override
    protected void edit() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new ItemEdit(id));
    }
    
    @Override
    public void refresh(){
        pricepanel.load();
        load();
    }
}
