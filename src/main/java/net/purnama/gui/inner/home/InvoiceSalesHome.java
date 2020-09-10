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
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.TableRowSorter;
import net.purnama.gui.inner.detail.InvoiceSalesDetail;
import net.purnama.gui.inner.detail.InvoiceSalesDraftDetail;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.transactional.InvoiceSalesEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.InvoiceSalesTableModel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class InvoiceSalesHome extends HomePanel{
    
    private final InvoiceSalesTableModel invoicesalestablemodel;
    
    private ArrayList<InvoiceSalesEntity> list;
    
    public InvoiceSalesHome(){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_INVOICESALESHOME"));
        
        list = new ArrayList<>();
        
        invoicesalestablemodel = new InvoiceSalesTableModel(list);
        
        table.setModel(invoicesalestablemodel);
        
        sorter = new TableRowSorter<>(table.getModel());
//        table.setRowSorter(sorter);
        
        filter();
    }

    @Override
    protected void detail() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        
        InvoiceSalesEntity invoicesales = invoicesalestablemodel.getInvoiceSales(table.
                    convertRowIndexToModel(table.getSelectedRow()));
        
        tabbedPane.changeTabPanel(getIndex(), new InvoiceSalesDetail(invoicesales.getId()));
    }

    @Override
    protected void edit() {
        detail();
    }

    @Override
    protected final void filter() {
        String keyword = upperpanel.getSearchKeyword();
        
        loaddata();
        
        page = 1;
        upperpanel.setCurrentPageLabel(page + "");
        
        SwingWorker<Boolean, String> numworker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                response = RestClient.get("countInvoiceSalesList?keyword=" + keyword);
                
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
                    
                    System.out.println(numofitem);
                    
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
                
                response = RestClient.get("getInvoiceSalesList?itemperpage=" + itemperpage + "&page=" + 
                        page + "&sort=-date&keyword=" + keyword);
                
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
                                        InvoiceSalesEntity.class));
                        
                        System.out.println(list.size());
                        
                        invoicesalestablemodel.setInvoiceSalesList(list);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }

    protected void detail(String id){
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                        getAncestorOfClass(JTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new InvoiceSalesDraftDetail(id));
    }
    
    @Override
    protected void add() {
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("addInvoiceSalesDraft");
                
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
                    upperpanel.setNotifLabel("");
                    
                    String output = response.getEntity(String.class);
                        
                    JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                    output, GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                    + response.getStatus(), 
                    JOptionPane.ERROR_MESSAGE);
                }
                else{
                    upperpanel.setNotifLabel("");
                    
                    String output = response.getEntity(String.class);

                    detail(output);
                }
            }
        };
        
        worker.execute();
    }
    
    @Override
    protected void openinnewtab() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(JTabbedPane.class, this);
        
        InvoiceSalesEntity invoicesales = invoicesalestablemodel.getInvoiceSales(table.
                    convertRowIndexToModel(table.getSelectedRow()));
        
        tabbedPane.insertTab(getIndex() + 1, new InvoiceSalesDetail(invoicesales.getId()));
    }
}