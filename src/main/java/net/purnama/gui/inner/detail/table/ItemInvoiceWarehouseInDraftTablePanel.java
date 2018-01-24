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
import net.purnama.controller.ItemInvoiceWarehouseInDraftController;
import net.purnama.gui.inner.detail.util.ItemTableCellEditor;
import net.purnama.gui.inner.detail.util.UomInvoiceWarehouseInTableCellEditor;
import net.purnama.model.transactional.draft.InvoiceWarehouseInDraftEntity;
import net.purnama.model.transactional.draft.ItemInvoiceWarehouseInDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.ItemInvoiceWarehouseInDraftTableModel;

/**
 *
 * @author Purnama
 */
public class ItemInvoiceWarehouseInDraftTablePanel extends TablePanel{
    
    private final ItemInvoiceWarehouseInDraftController iteminvoicewarehouseindraftController;
    
    private final ItemInvoiceWarehouseInDraftTableModel iteminvoicewarehouseindrafttablemodel;
    
    private ArrayList<ItemInvoiceWarehouseInDraftEntity> list;
    
    private final ItemTableCellEditor itemcelleditor;
    private final UomInvoiceWarehouseInTableCellEditor uomcelleditor;
    
    private final String invoicewarehouseindraftid;
    
    public ItemInvoiceWarehouseInDraftTablePanel(String invoicewarehouseindraftid){
        super();
        
        this.invoicewarehouseindraftid = invoicewarehouseindraftid;
        
        itemcelleditor = new ItemTableCellEditor();
        uomcelleditor = new UomInvoiceWarehouseInTableCellEditor();
        
        iteminvoicewarehouseindraftController = new ItemInvoiceWarehouseInDraftController();
        
        InvoiceWarehouseInDraftEntity invoicewarehouseindraft = new InvoiceWarehouseInDraftEntity();
        invoicewarehouseindraft.setId(invoicewarehouseindraftid);
        
        list = new ArrayList<>();
        
        iteminvoicewarehouseindrafttablemodel = new ItemInvoiceWarehouseInDraftTableModel(list, invoicewarehouseindraft);
        
        table.setModel(iteminvoicewarehouseindrafttablemodel);
        
        TableColumn column1 = table.getColumnModel().getColumn(1);
        column1.setCellEditor(itemcelleditor);
        TableColumn column3 = table.getColumnModel().getColumn(3);
        column3.setCellEditor(uomcelleditor);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(25);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        
        menuitemdelete.addActionListener((ActionEvent e) -> {
            iteminvoicewarehouseindrafttablemodel.deleteRow(table.getSelectedRow());
        });
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getItemInvoiceWarehouseInDraftList?id="+invoicewarehouseindraftid);
                
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
                                        ItemInvoiceWarehouseInDraftEntity.class));
                        
                        iteminvoicewarehouseindrafttablemodel.setItemInvoiceWarehouseInDraftList(list);
                        InvoiceWarehouseInDraftEntity invoicewarehouseindraft = new InvoiceWarehouseInDraftEntity();
                        invoicewarehouseindraft.setId(invoicewarehouseindraftid);
        
                        iteminvoicewarehouseindrafttablemodel.
                        addRow(iteminvoicewarehouseindraftController.createEmptyItemInvoiceWarehouseInDraft(invoicewarehouseindraft));
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void submitItemInvoiceWarehouseInDraftList(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                response = RestClient.post("saveItemInvoiceWarehouseInDraftList", 
                        iteminvoicewarehouseindrafttablemodel.getItemInvoiceWarehouseInDraftList());
                
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
                                        ItemInvoiceWarehouseInDraftEntity.class));
                        
                        iteminvoicewarehouseindrafttablemodel.setItemInvoiceWarehouseInDraftList(list);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void submitDeletedItemInvoiceWarehouseInDraftList(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.post("deleteItemInvoiceWarehouseInDraftList",
                        iteminvoicewarehouseindrafttablemodel.getDeletedItemInvoiceWarehouseInDraftList());
                
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
