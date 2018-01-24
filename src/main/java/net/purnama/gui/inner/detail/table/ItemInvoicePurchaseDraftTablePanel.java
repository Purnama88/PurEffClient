/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.table;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.SwingWorker;
import javax.swing.table.TableColumn;
import net.purnama.controller.ItemInvoicePurchaseDraftController;
import net.purnama.gui.inner.detail.util.DiscountSubtotalPanel;
import net.purnama.gui.inner.detail.util.ItemTableCellEditor;
import net.purnama.gui.inner.detail.util.PercentageTableCellEditor;
import net.purnama.gui.inner.detail.util.UomInvoicePurchaseTableCellEditor;
import net.purnama.model.transactional.draft.InvoicePurchaseDraftEntity;
import net.purnama.model.transactional.draft.ItemInvoicePurchaseDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.ItemInvoicePurchaseDraftTableModel;

/**
 *
 * @author Purnama
 */
public class ItemInvoicePurchaseDraftTablePanel extends TablePanel{
    
    private final ItemInvoicePurchaseDraftController iteminvoicepurchasedraftController;
    
    private final ItemInvoicePurchaseDraftTableModel iteminvoicepurchasedrafttablemodel;
    
    private ArrayList<ItemInvoicePurchaseDraftEntity> list;
    
    private final ItemTableCellEditor itemcelleditor;
    private final UomInvoicePurchaseTableCellEditor uomcelleditor;
    private final PercentageTableCellEditor percentagecelleditor;
    
    private final String invoicepurchasedraftid;
    
    public ItemInvoicePurchaseDraftTablePanel(String invoicepurchasedraftid,
            DiscountSubtotalPanel discountsubtotalpanel){
    
        this.invoicepurchasedraftid = invoicepurchasedraftid;
        
        itemcelleditor = new ItemTableCellEditor();
        uomcelleditor = new UomInvoicePurchaseTableCellEditor();
        percentagecelleditor = new PercentageTableCellEditor();
        
        iteminvoicepurchasedraftController = new ItemInvoicePurchaseDraftController();
        
        InvoicePurchaseDraftEntity invoicepurchasedraft = new InvoicePurchaseDraftEntity();
        invoicepurchasedraft.setId(invoicepurchasedraftid);
        
        list = new ArrayList<>();
        
        iteminvoicepurchasedrafttablemodel = new ItemInvoicePurchaseDraftTableModel(list,
                invoicepurchasedraft, discountsubtotalpanel);
        
        table.setModel(iteminvoicepurchasedrafttablemodel);
        
        TableColumn column1 = table.getColumnModel().getColumn(1);
        column1.setCellEditor(itemcelleditor);
        TableColumn column3 = table.getColumnModel().getColumn(3);
        column3.setCellEditor(uomcelleditor);
        TableColumn column5 = table.getColumnModel().getColumn(5);
        column5.setCellEditor(percentagecelleditor);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(25);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        
        menuitemdelete.addActionListener((ActionEvent e) -> {
            iteminvoicepurchasedrafttablemodel.deleteRow(table.getSelectedRow());
        });
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getItemInvoicePurchaseDraftList?id="+invoicepurchasedraftid);
                
                return true;
            }
            
            @Override
            protected void done() {
                if(response == null){
                }
                else if(response.getStatus() != 200) {
                }
                else{
                    String output = response.getEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        ItemInvoicePurchaseDraftEntity.class));
                        
                        iteminvoicepurchasedrafttablemodel.setItemInvoicePurchaseDraftList(list);
                        InvoicePurchaseDraftEntity invoicepurchasedraft = new InvoicePurchaseDraftEntity();
                        invoicepurchasedraft.setId(invoicepurchasedraftid);
        
                        iteminvoicepurchasedrafttablemodel.
                        addRow(iteminvoicepurchasedraftController.createEmptyItemInvoicePurchaseDraft(invoicepurchasedraft));
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void submitItemInvoicePurchaseDraftList(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.post("saveItemInvoicePurchaseDraftList", 
                        iteminvoicepurchasedrafttablemodel.getItemInvoicePurchaseDraftList());
                
                return true;
            }
            
            @Override
            protected void done() {
                
                if(response == null){
                }
                else if(response.getStatus() != 200) {
                }
                else{
                    String output = response.getEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        ItemInvoicePurchaseDraftEntity.class));
                        
                        iteminvoicepurchasedrafttablemodel.setItemInvoicePurchaseDraftList(list);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void submitDeletedItemInvoicePurchaseDraftList(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.post("deleteItemInvoicePurchaseDraftList",
                        iteminvoicepurchasedrafttablemodel.getDeletedItemInvoicePurchaseDraftList());
                
                return true;
            }
            
            @Override
            protected void done() {
                
                if(response == null){
                }
                else if(response.getStatus() != 200) {
                }
                else{
                }
            }
        };
        
        worker.execute();
    }
}
