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
import net.purnama.gui.inner.detail.CurrencyDetail;
import net.purnama.gui.inner.form.CurrencyAdd;
import net.purnama.gui.inner.form.CurrencyEdit;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.CurrencyEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.CurrencyTableModel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class CurrencyHome extends HomePanel{
    
    private final CurrencyTableModel currencytablemodel;
    
    private ArrayList<CurrencyEntity> list;
    
    public CurrencyHome(){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_CURRENCYHOME"));
        
        if(!GlobalFields.ROLE.isCurrency_add()){
            upperpanel.removeAddButton();
        }
        
        list = new ArrayList<>();
        
        currencytablemodel = new CurrencyTableModel(list);
        
        table.setModel(currencytablemodel);
        
        sorter = new TableRowSorter<>(table.getModel());
//        table.setRowSorter(sorter);
        
        filter();
    }

    @Override
    protected void detail() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        
        CurrencyEntity currency = currencytablemodel.getCurrency(table.
                    convertRowIndexToModel(table.getSelectedRow()));
        
        tabbedPane.changeTabPanel(getIndex(), new CurrencyDetail(currency.getId()));
    }

    @Override
    protected void edit() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        
        CurrencyEntity currency = currencytablemodel.getCurrency(table.
                    convertRowIndexToModel(table.getSelectedRow()));
        
        tabbedPane.changeTabPanel(getIndex(), new CurrencyEdit(currency.getId()));
    }

    @Override
    protected final void filter() {
        String keyword = upperpanel.getSearchKeyword();;
        
        loaddata();
        
        page = 1;
        upperpanel.setCurrentPageLabel(page + "");
        
        SwingWorker<Boolean, String> numworker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                response = RestClient.get("countCurrencyList?keyword=" + keyword);
                
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
        String keyword = upperpanel.getSearchKeyword();;
        int itemperpage = GlobalFields.ITEM_PER_PAGE;
        
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getCurrencyList?itemperpage=" + itemperpage + "&page=" + 
                        page + "&sort=" + "code"  + "&keyword=" + keyword);
                
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
                                        CurrencyEntity.class));
                        
                        currencytablemodel.setCurrencyList(list);
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
        
        tabbedPane.changeTabPanel(getIndex(), new CurrencyAdd());
    }
    
    @Override
    protected void openinnewtab() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        
        CurrencyEntity currency = currencytablemodel.getCurrency(table.
                    convertRowIndexToModel(table.getSelectedRow()));
        
        tabbedPane.insertTab(getIndex() + 1, new CurrencyDetail(currency.getId()));
    }
}
