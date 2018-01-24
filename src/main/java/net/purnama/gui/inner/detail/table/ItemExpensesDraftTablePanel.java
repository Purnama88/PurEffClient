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
import net.purnama.controller.ItemExpensesDraftController;
import net.purnama.gui.inner.detail.util.DiscountSubtotalPanel;
import net.purnama.gui.inner.detail.util.PercentageTableCellEditor;
import net.purnama.model.transactional.draft.ExpensesDraftEntity;
import net.purnama.model.transactional.draft.ItemExpensesDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.ItemExpensesDraftTableModel;

/**
 *
 * @author Purnama
 */
public class ItemExpensesDraftTablePanel extends TablePanel{
    
    private final ItemExpensesDraftController itemexpensesdraftController;
    
    private final ItemExpensesDraftTableModel itemexpensesdrafttablemodel;
    
    private ArrayList<ItemExpensesDraftEntity> list;
    
    private final PercentageTableCellEditor percentagecelleditor;
    
    private final String expensesdraftid;
    
    public ItemExpensesDraftTablePanel(String expensesdraftid, DiscountSubtotalPanel discountsubtotalpanel){
    
        this.expensesdraftid = expensesdraftid;
        
        percentagecelleditor = new PercentageTableCellEditor();
        
        itemexpensesdraftController = new ItemExpensesDraftController();
        
        ExpensesDraftEntity expensesdraft = new ExpensesDraftEntity();
        expensesdraft.setId(expensesdraftid);
        
        list = new ArrayList<>();
        
        itemexpensesdrafttablemodel = new ItemExpensesDraftTableModel(list, expensesdraft,
            discountsubtotalpanel);
        
        table.setModel(itemexpensesdrafttablemodel);
        
        TableColumn column4 = table.getColumnModel().getColumn(4);
        column4.setCellEditor(percentagecelleditor);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(25);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        
        menuitemdelete.addActionListener((ActionEvent e) -> {
            itemexpensesdrafttablemodel.deleteRow(table.getSelectedRow());
        });
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getItemExpensesDraftList?id="+expensesdraftid);
                
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
                                        ItemExpensesDraftEntity.class));
                        
                        itemexpensesdrafttablemodel.setItemExpensesDraftList(list);
                        ExpensesDraftEntity expensesdraft = new ExpensesDraftEntity();
                        expensesdraft.setId(expensesdraftid);
        
                        itemexpensesdrafttablemodel.
                        addRow(itemexpensesdraftController.createEmptyItemExpensesDraft(expensesdraft));
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void submitItemExpensesDraftList(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.post("saveItemExpensesDraftList", 
                        itemexpensesdrafttablemodel.getItemExpensesDraftList());
                
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
                                        ItemExpensesDraftEntity.class));
                        
                        itemexpensesdrafttablemodel.setItemExpensesDraftList(list);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void submitDeletedItemExpensesDraftList(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.post("deleteItemExpensesDraftList",
                        itemexpensesdrafttablemodel.getDeletedItemExpensesDraftList());
                
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
