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
import net.purnama.gui.inner.detail.util.SelectableLabelContentPanel;
import net.purnama.gui.inner.form.UserEdit;
import net.purnama.gui.inner.home.UserHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.UserEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class UserDetail extends DetailPanel{
    
    private UserEntity user;
    
    private final SelectableLabelContentPanel idpanel, codepanel, namepanel, usernamepanel, rolepanel,
            warehousepanel,
            maxdiscountpanel, statuspanel, backwardpanel, forwardpanel,
            raise_buypricepanel, lower_buypricepanel, raise_sellpricepanel, 
            lower_sellpricepanel;
    
    private final String id;
    
    public UserDetail(String id){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_USERDETAIL"));
        
        this.id = id;
        
        idpanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_ID"),
                "");
        codepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_CODE"),
                "");
        namepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_NAME"),
                "");
        usernamepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_USERNAME"),
                "");
        rolepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_ROLE"),
                "");
        warehousepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_WAREHOUSE"),
                "");
        maxdiscountpanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MAXDISCOUNT"),
                "");
        statuspanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_STATUS"),
                "");
        
        forwardpanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_DATEFORWARD"),
                "");
        
        backwardpanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_DATEBACKWARD"),
                "");
        
        raise_buypricepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_RAISEBUYPRICE"),
                "");
        
        lower_buypricepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_LOWERBUYPRICE"),
                "");
        
        raise_sellpricepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_RAISESELLPRICE"),
                "");
        
        lower_sellpricepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_LOWERSELLPRICE"),
                "");
        
//        detailpanel.add(idpanel);
//        detailpanel.add(codepanel);
        detailpanel.add(namepanel);
        detailpanel.add(usernamepanel);
        detailpanel.add(rolepanel);
        detailpanel.add(warehousepanel);
        detailpanel.add(maxdiscountpanel);
        detailpanel.add(forwardpanel);
        detailpanel.add(backwardpanel);
        detailpanel.add(raise_buypricepanel);
        detailpanel.add(lower_buypricepanel);
        detailpanel.add(raise_sellpricepanel);
        detailpanel.add(lower_sellpricepanel);
        detailpanel.add(statuspanel);
        
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
                
                response = RestClient.get("getUser?id=" + id);
               
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
                        user = mapper.readValue(output, UserEntity.class);
                        
                        idpanel.setContentValue(user.getId());
                        codepanel.setContentValue(user.getCode());
                        namepanel.setContentValue(user.getName());
                        usernamepanel.setContentValue(user.getUsername());
                        rolepanel.setContentValue(user.getRole().getName());
                        maxdiscountpanel.setContentValue(user.getFormattedDiscount());
                        warehousepanel.setContentValue(user.getFormattedWarehouses());
                        notepanel.setNote(user.getNote());
                        forwardpanel.setContentValue(user.isDateforward());
                        backwardpanel.setContentValue(user.isDatebackward());
                        raise_buypricepanel.setContentValue(user.isRaise_buyprice());
                        lower_buypricepanel.setContentValue(user.isLower_buyprice());
                        raise_sellpricepanel.setContentValue(user.isRaise_sellprice());
                        lower_sellpricepanel.setContentValue(user.isLower_sellprice());
                        statuspanel.setContentValue(user.isStatus());
                        
                        upperpanel.setStatusLabel(user.getFormattedLastmodified());
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
        
        tabbedPane.changeTabPanel(getIndex(), new UserHome());
    }

    @Override
    protected void edit() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new UserEdit(id));
    }
    
    @Override
    public void refresh(){
        load();
    }
    
}
