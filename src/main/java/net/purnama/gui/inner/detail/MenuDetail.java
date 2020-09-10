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
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.purnama.gui.dialog.NumberingAddDialog;
import net.purnama.gui.dialog.NumberingEditDialog;
import net.purnama.gui.inner.detail.table.NumberingTablePanel;
import net.purnama.gui.inner.detail.util.SelectableLabelContentPanel;
import net.purnama.gui.inner.home.MenuHome;
import net.purnama.gui.library.MyButton;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.MenuEntity;
import net.purnama.model.NumberingEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class MenuDetail extends DetailPanel{
    
    private MenuEntity menu;
    
    private final SelectableLabelContentPanel namepanel, numberpanel;
    
    private final NumberingTablePanel numberingtablepanel;
    
    private final MyButton addbutton;
    
    private final int id;
    
    public MenuDetail(int id){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_MENUDETAIL"));
        
        this.id = id;
        
        upperpanel.removeEditButton();
        
        namepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_NAME"),
                "");
        numberpanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_NUMBERING"),
                "");
        
        addbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Add_16.png"), 
                GlobalFields.PROPERTIES.getProperty("LABEL_ADD"), 100, 24);
        
        detailpanel.add(namepanel);
        detailpanel.add(numberpanel);
        if(GlobalFields.ROLE.isNumbering_add()){
            detailpanel.add(addbutton);
        }
        
         
        numberingtablepanel = new NumberingTablePanel(id);
        
        detailpanel.add(numberingtablepanel);
    
        addbutton.addActionListener((ActionEvent e) -> {
            NumberingAddDialog nad = new NumberingAddDialog(id);
           
            NumberingEntity result = nad.showDialog();
            
            if(result != null){
                numberingtablepanel.addNumbering(result);
            }
        });
        
        numberingtablepanel.getMenuItemEdit().addActionListener((ActionEvent e) -> {
            edit();
        });
        
        numberingtablepanel.getMenuItemDefault().addActionListener((ActionEvent e) -> {
            defaultnumbering();
        });
        
        load();
    }

    @Override
    protected void home() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new MenuHome());
    }

    @Override
    protected final void edit() {
        
        NumberingEntity numbering = numberingtablepanel.getNumbering();
        
        NumberingEditDialog ned = new NumberingEditDialog(menu.getId(), numbering.getId());
           
        NumberingEntity result = ned.showDialog();

        numberingtablepanel.load();
    }
    
    protected final void defaultnumbering(){
        NumberingEntity numbering = numberingtablepanel.getNumbering();
                        
        SwingWorker<Boolean, String> submitworker = new SwingWorker<Boolean, String>() {
            ClientResponse response;

            @Override
            protected Boolean doInBackground(){

                upperpanel.showProgressBar();

                response = RestClient.get("setDefaultNumbering?id=" + numbering.getId());

                return true;

            }

            @Override
            protected void done() {
                upperpanel.hideProgressBar();
                if(response == null){
                    upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                }
                else if(response.getStatus() != 200) {
                    upperpanel.setNotifLabel("");
                    String output = response.getEntity(String.class);
                        
                    JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                    output, GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                    + response.getStatus(), 
                    JOptionPane.ERROR_MESSAGE);
                    
                    numberingtablepanel.load();
                }
                else{
                    upperpanel.setNotifLabel("");

                    numberingtablepanel.load();
                }
            }
        };

        submitworker.execute();
    }
    
    @Override
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getMenu?id=" + id);
               
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
                        menu = mapper.readValue(output, MenuEntity.class);
                        
                        namepanel.setContentValue(menu.getName());
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    @Override
    public void refresh(){
        numberingtablepanel.load();
        load();
    }
}
