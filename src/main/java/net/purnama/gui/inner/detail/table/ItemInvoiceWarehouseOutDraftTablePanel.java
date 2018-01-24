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
import net.purnama.controller.ItemInvoiceWarehouseOutDraftController;
import net.purnama.gui.inner.detail.util.ItemTableCellEditor;
import net.purnama.gui.inner.detail.util.UomInvoiceWarehouseOutTableCellEditor;
import net.purnama.model.transactional.draft.InvoiceWarehouseOutDraftEntity;
import net.purnama.model.transactional.draft.ItemInvoiceWarehouseOutDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.ItemInvoiceWarehouseOutDraftTableModel;

/**
 *
 * @author Purnama
 */
public class ItemInvoiceWarehouseOutDraftTablePanel extends TablePanel{
    
    private final ItemInvoiceWarehouseOutDraftController iteminvoicewarehouseoutdraftController;
    
    private final ItemInvoiceWarehouseOutDraftTableModel iteminvoicewarehouseoutdrafttablemodel;
    
    private ArrayList<ItemInvoiceWarehouseOutDraftEntity> list;
    
    private final ItemTableCellEditor itemcelleditor;
    private final UomInvoiceWarehouseOutTableCellEditor uomcelleditor;
    
    private final String invoicewarehouseoutdraftid;
    
    public ItemInvoiceWarehouseOutDraftTablePanel(String invoicewarehouseoutdraftid){
        super();
        
        this.invoicewarehouseoutdraftid = invoicewarehouseoutdraftid;
        
        itemcelleditor = new ItemTableCellEditor();
        uomcelleditor = new UomInvoiceWarehouseOutTableCellEditor();
        
        iteminvoicewarehouseoutdraftController = new ItemInvoiceWarehouseOutDraftController();
        
        InvoiceWarehouseOutDraftEntity invoicewarehouseoutdraft = new InvoiceWarehouseOutDraftEntity();
        invoicewarehouseoutdraft.setId(invoicewarehouseoutdraftid);
        
        list = new ArrayList<>();
        
        iteminvoicewarehouseoutdrafttablemodel = new ItemInvoiceWarehouseOutDraftTableModel(list, invoicewarehouseoutdraft);
        
        table.setModel(iteminvoicewarehouseoutdrafttablemodel);
        
        TableColumn column1 = table.getColumnModel().getColumn(1);
        column1.setCellEditor(itemcelleditor);
        TableColumn column3 = table.getColumnModel().getColumn(3);
        column3.setCellEditor(uomcelleditor);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(25);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        
        menuitemdelete.addActionListener((ActionEvent e) -> {
            iteminvoicewarehouseoutdrafttablemodel.deleteRow(table.getSelectedRow());
        });
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getItemInvoiceWarehouseOutDraftList?id="+invoicewarehouseoutdraftid);
                
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
                                        ItemInvoiceWarehouseOutDraftEntity.class));
                        
                        iteminvoicewarehouseoutdrafttablemodel.setItemInvoiceWarehouseOutDraftList(list);
                        InvoiceWarehouseOutDraftEntity invoicewarehouseoutdraft = new InvoiceWarehouseOutDraftEntity();
                        invoicewarehouseoutdraft.setId(invoicewarehouseoutdraftid);
        
                        iteminvoicewarehouseoutdrafttablemodel.
                        addRow(iteminvoicewarehouseoutdraftController.createEmptyItemInvoiceWarehouseOutDraft(invoicewarehouseoutdraft));
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void submitItemInvoiceWarehouseOutDraftList(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                response = RestClient.post("saveItemInvoiceWarehouseOutDraftList", 
                        iteminvoicewarehouseoutdrafttablemodel.getItemInvoiceWarehouseOutDraftList());
                
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
                                        ItemInvoiceWarehouseOutDraftEntity.class));
                        
                        iteminvoicewarehouseoutdrafttablemodel.setItemInvoiceWarehouseOutDraftList(list);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void submitDeletedItemInvoiceWarehouseOutDraftList(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.post("deleteItemInvoiceWarehouseOutDraftList",
                        iteminvoicewarehouseoutdrafttablemodel.getDeletedItemInvoiceWarehouseOutDraftList());
                
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
