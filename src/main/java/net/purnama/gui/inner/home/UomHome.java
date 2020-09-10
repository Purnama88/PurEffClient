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
import javax.swing.table.TableRowSorter;
import net.purnama.gui.inner.detail.UomDetail;
import net.purnama.gui.inner.form.UomAdd;
import net.purnama.gui.inner.form.UomEdit;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.UomEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.UomTableModel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class UomHome extends HomePanel{
    
    private final UomTableModel uomtablemodel;
    
    private ArrayList<UomEntity> list;
    
    public UomHome(){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_UOMHOME"));
        
        if(!GlobalFields.ROLE.isUom_add()){
            upperpanel.removeAddButton();
        }
        
        list = new ArrayList<>();
        
        uomtablemodel = new UomTableModel(list);
        
        table.setModel(uomtablemodel);
        sorter = new TableRowSorter<>(table.getModel());
//        table.setRowSorter(sorter);
        
        filter();
        
    }

    @Override
    protected void detail() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        
        UomEntity uom = uomtablemodel.getUom(table.
                    convertRowIndexToModel(table.getSelectedRow()));
        
        tabbedPane.changeTabPanel(getIndex(), new UomDetail(uom.getId()));
    }

    @Override
    protected void edit() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        
        UomEntity uom = uomtablemodel.getUom(table.
                    convertRowIndexToModel(table.getSelectedRow()));
        
        tabbedPane.changeTabPanel(getIndex(), new UomEdit(uom.getId()));
    }

    @Override
    protected void filter() {
        String keyword = upperpanel.getSearchKeyword();
        
        loaddata();
        
        page = 1;
        upperpanel.setCurrentPageLabel(page + "");
        
        SwingWorker<Boolean, String> numworker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                response = RestClient.get("countParentUomList?keyword=" + keyword);
                
                return true;
            }
            
            @Override
            protected void done() {
                if(response == null){
                    upperpanel.setTotalPageLabel("1");
                }
                else if(response.getStatus() != 200) {
                    upperpanel.setTotalPageLabel("1");
                }
                else{
                    String output = response.getEntity(String.class);

                    numofitem = Integer.parseInt(output);
                    
                    upperpanel.setTotalPageLabel(calculatepages() + "");
                }
                    
            }
        };
        
        numworker.execute();
    }

    @Override
    protected void loaddata() {
        String keyword = upperpanel.getSearchKeyword();
        int itemperpage = GlobalFields.ITEM_PER_PAGE;
        
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getParentUomList?itemperpage=" + itemperpage + "&page=" + 
                        page + "&sort=" + "name"  + "&keyword=" + keyword);
                
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
                                        UomEntity.class));
                        
                        uomtablemodel.setUomList(list);
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
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new UomAdd());
    }
    
    @Override
    protected void openinnewtab() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        
        UomEntity uom = uomtablemodel.getUom(table.
                    convertRowIndexToModel(table.getSelectedRow()));
        
        tabbedPane.insertTab(getIndex() + 1, new UomDetail(uom.getId()));
    }
}
