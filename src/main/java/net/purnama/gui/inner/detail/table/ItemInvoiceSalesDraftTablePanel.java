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
import net.purnama.controller.ItemInvoiceSalesDraftController;
import net.purnama.gui.inner.detail.util.DiscountSubtotalPanel;
import net.purnama.gui.inner.detail.util.ItemTableCellEditor;
import net.purnama.gui.inner.detail.util.PercentageTableCellEditor;
import net.purnama.gui.inner.detail.util.UomInvoiceSalesTableCellEditor;
import net.purnama.model.transactional.draft.InvoiceSalesDraftEntity;
import net.purnama.model.transactional.draft.ItemInvoiceSalesDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.ItemInvoiceSalesDraftTableModel;

/**
 *
 * @author Purnama
 */
public class ItemInvoiceSalesDraftTablePanel extends TablePanel{
    
    private final ItemInvoiceSalesDraftController iteminvoicesalesdraftController;
    
    private final ItemInvoiceSalesDraftTableModel iteminvoicesalesdrafttablemodel;
    
    private ArrayList<ItemInvoiceSalesDraftEntity> list;
    
    private final ItemTableCellEditor itemcelleditor;
    private final UomInvoiceSalesTableCellEditor uomcelleditor;
    private final PercentageTableCellEditor percentagecelleditor;
    
    private final String invoicesalesdraftid;
    
    public ItemInvoiceSalesDraftTablePanel(String invoicesalesdraftid, 
            DiscountSubtotalPanel discountsubtotalpanel){
        
        super();
        
        this.invoicesalesdraftid = invoicesalesdraftid;
        
        itemcelleditor = new ItemTableCellEditor();
        uomcelleditor = new UomInvoiceSalesTableCellEditor();
        percentagecelleditor = new PercentageTableCellEditor();
        
        iteminvoicesalesdraftController = new ItemInvoiceSalesDraftController();
        
        InvoiceSalesDraftEntity invoicesalesdraft = new InvoiceSalesDraftEntity();
        invoicesalesdraft.setId(invoicesalesdraftid);
        
        list = new ArrayList<>();
        
        iteminvoicesalesdrafttablemodel = new ItemInvoiceSalesDraftTableModel(list, invoicesalesdraft,
                discountsubtotalpanel);
        
        table.setModel(iteminvoicesalesdrafttablemodel);
        
        TableColumn column1 = table.getColumnModel().getColumn(1);
        column1.setCellEditor(itemcelleditor);
        TableColumn column3 = table.getColumnModel().getColumn(3);
        column3.setCellEditor(uomcelleditor);
        TableColumn column5 = table.getColumnModel().getColumn(5);
        column5.setCellEditor(percentagecelleditor);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(25);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        
        menuitemdelete.addActionListener((ActionEvent e) -> {
            iteminvoicesalesdrafttablemodel.deleteRow(table.getSelectedRow());
        });
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getItemInvoiceSalesDraftList?id="+invoicesalesdraftid);
                
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
                                        ItemInvoiceSalesDraftEntity.class));
                        
                        iteminvoicesalesdrafttablemodel.setItemInvoiceSalesDraftList(list);
                        InvoiceSalesDraftEntity invoicesalesdraft = new InvoiceSalesDraftEntity();
                        invoicesalesdraft.setId(invoicesalesdraftid);
        
                        iteminvoicesalesdrafttablemodel.
                        addRow(iteminvoicesalesdraftController.createEmptyItemInvoiceSalesDraft(invoicesalesdraft));
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void submitItemInvoiceSalesDraftList(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                response = RestClient.post("saveItemInvoiceSalesDraftList", 
                        iteminvoicesalesdrafttablemodel.getItemInvoiceSalesDraftList());
                
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
                                        ItemInvoiceSalesDraftEntity.class));
                        
                        iteminvoicesalesdrafttablemodel.setItemInvoiceSalesDraftList(list);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void submitDeletedItemInvoiceSalesDraftList(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.post("deleteItemInvoiceSalesDraftList",
                        iteminvoicesalesdrafttablemodel.getDeletedItemInvoiceSalesDraftList());
                
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
