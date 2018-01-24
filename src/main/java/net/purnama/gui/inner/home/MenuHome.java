/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.home;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.purnama.gui.inner.detail.MenuDetail;
import net.purnama.gui.inner.home.util.UpperPanel;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.MenuEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.MenuTableModel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class MenuHome extends HomePanel{
    
    private final MenuTableModel menutablemodel;
    
    private ArrayList<MenuEntity> list;
    
    public MenuHome(){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_MENUHOME"));
        
        upperpanel.removeAddButton();
        upperpanel.removeSearchTextField();
        upperpanel.removePagination();
        
        list = new ArrayList<>();
        
        menutablemodel = new MenuTableModel(list);
        
        table.setModel(menutablemodel);
        
        loaddata();
    }

    @Override
    protected void detail() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);

        MenuEntity menu = menutablemodel.getMenu(table.
                convertRowIndexToModel(table.getSelectedRow()));

        tabbedPane.changeTabPanel(getIndex(), new MenuDetail(menu.getId()));
    }

    @Override
    protected void edit() {
        detail();
    }

    @Override
    protected void filter() {
        loaddata();
    }

    @Override
    protected final void loaddata() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getTransactionalMenuList");
                
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
                        list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        MenuEntity.class));
                        
                        menutablemodel.setMenuList(list);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }

    @Override
    protected void add() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    protected void openinnewtab() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);

        MenuEntity menu = menutablemodel.getMenu(table.
                convertRowIndexToModel(table.getSelectedRow()));

        tabbedPane.insertTab(getIndex() + 1, new MenuDetail(menu.getId()));
    }
    
}
