/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.purnama.gui.dialog.UomAddDialog;
import net.purnama.gui.dialog.UomEditDialog;
import net.purnama.gui.inner.detail.table.UomTablePanel;
import net.purnama.gui.inner.detail.util.SelectableLabelContentPanel;
import net.purnama.gui.inner.form.UomEdit;
import net.purnama.gui.inner.home.UomHome;
import net.purnama.gui.library.MyButton;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.UomEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class UomDetail extends DetailPanel{
    
    private UomEntity parent;
    
    private final SelectableLabelContentPanel idpanel, namepanel, valuepanel, statuspanel;
    
    private final UomTablePanel childuomtablepanel;
    
    private final MyButton addbutton;
    
    private final String id;
    
    public UomDetail(String id){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_UOMDETAIL"));
        
        this.id = id;
        
        idpanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_ID"),
                "");
        namepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_NAME"),
                "");
        valuepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_VALUE"),
                "");
        statuspanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_STATUS"),
                "");
        
        addbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Add_16.png"), 
                GlobalFields.PROPERTIES.getProperty("LABEL_ADD"), 100, 24);
        
//        detailpanel.add(idpanel);
        detailpanel.add(namepanel);
        detailpanel.add(valuepanel);
        detailpanel.add(statuspanel);
        
        if(GlobalFields.ROLE.isUom_add()){
            detailpanel.add(addbutton);
        }
         
        load();
        
        childuomtablepanel = new UomTablePanel(id);
        
        detailpanel.add(childuomtablepanel);
        
        addbutton.addActionListener((ActionEvent e) -> {
            UomAddDialog uad = new UomAddDialog(parent);
           
            UomEntity result = uad.showDialog();
            
            if(result != null){
                childuomtablepanel.addUom(result);
            }
        });
        
        childuomtablepanel.getMenuItemEdit().addActionListener((ActionEvent e) -> {
            UomEntity u = childuomtablepanel.getSelectedUom();
            
            UomEditDialog uad = new UomEditDialog(parent, u.getId());
           
            UomEntity result = uad.showDialog();
            
            childuomtablepanel.load();
        });
    }

    protected final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getUom?id=" + id);
               
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
                        parent = mapper.readValue(output, UomEntity.class);
                        idpanel.setContentValue(parent.getId());
                        namepanel.setContentValue(parent.getName());
                        valuepanel.setContentValue(parent.getFormattedValue());
                        statuspanel.setContentValue(parent.isStatus());
                        
                        notepanel.setNote(parent.getNote());
                        
                        upperpanel.setStatusLabel(parent.getFormattedLastmodified());
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
        
        tabbedPane.changeTabPanel(getIndex(), new UomHome());
    }

    @Override
    protected void edit() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new UomEdit(id));
    }
    
    @Override
    public void refresh(){
        childuomtablepanel.load();
        load();
    }
}
