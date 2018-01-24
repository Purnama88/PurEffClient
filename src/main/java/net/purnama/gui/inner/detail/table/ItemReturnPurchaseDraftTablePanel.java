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
import net.purnama.controller.ItemReturnPurchaseDraftController;
import net.purnama.gui.inner.detail.util.DiscountSubtotalPanel;
import net.purnama.gui.inner.detail.util.ItemTableCellEditor;
import net.purnama.gui.inner.detail.util.PercentageTableCellEditor;
import net.purnama.gui.inner.detail.util.UomReturnPurchaseTableCellEditor;
import net.purnama.model.transactional.draft.ItemReturnPurchaseDraftEntity;
import net.purnama.model.transactional.draft.ReturnPurchaseDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.ItemReturnPurchaseDraftTableModel;

/**
 *
 * @author Purnama
 */
public class ItemReturnPurchaseDraftTablePanel extends TablePanel{
    
    private final ItemReturnPurchaseDraftController itemreturnpurchasedraftController;
    
    private final ItemReturnPurchaseDraftTableModel itemreturnpurchasedrafttablemodel;
    
    private ArrayList<ItemReturnPurchaseDraftEntity> list;
    
    private final ItemTableCellEditor itemcelleditor;
    private final UomReturnPurchaseTableCellEditor uomcelleditor;
    private final PercentageTableCellEditor percentagecelleditor;
    
    private final String returnpurchasedraftid;
    
    public ItemReturnPurchaseDraftTablePanel(String returnpurchasedraftid, DiscountSubtotalPanel
            discountsubtotalpanel){
    
        this.returnpurchasedraftid = returnpurchasedraftid;
        
        itemcelleditor = new ItemTableCellEditor();
        uomcelleditor = new UomReturnPurchaseTableCellEditor();
        percentagecelleditor = new PercentageTableCellEditor();
        
        itemreturnpurchasedraftController = new ItemReturnPurchaseDraftController();
        
        ReturnPurchaseDraftEntity returnpurchasedraft = new ReturnPurchaseDraftEntity();
        returnpurchasedraft.setId(returnpurchasedraftid);
        
        list = new ArrayList<>();
        
        itemreturnpurchasedrafttablemodel = new ItemReturnPurchaseDraftTableModel(list, returnpurchasedraft,
            discountsubtotalpanel);
        
        table.setModel(itemreturnpurchasedrafttablemodel);
        
        TableColumn column1 = table.getColumnModel().getColumn(1);
        column1.setCellEditor(itemcelleditor);
        TableColumn column3 = table.getColumnModel().getColumn(3);
        column3.setCellEditor(uomcelleditor);
        TableColumn column5 = table.getColumnModel().getColumn(5);
        column5.setCellEditor(percentagecelleditor);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(25);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        
        menuitemdelete.addActionListener((ActionEvent e) -> {
            itemreturnpurchasedrafttablemodel.deleteRow(table.getSelectedRow());
        });
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getItemReturnPurchaseDraftList?id="+returnpurchasedraftid);
                
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
                                        ItemReturnPurchaseDraftEntity.class));
                        
                        itemreturnpurchasedrafttablemodel.setItemReturnPurchaseDraftList(list);
                        ReturnPurchaseDraftEntity returnpurchasedraft = new ReturnPurchaseDraftEntity();
                        returnpurchasedraft.setId(returnpurchasedraftid);
        
                        itemreturnpurchasedrafttablemodel.
                        addRow(itemreturnpurchasedraftController.createEmptyItemReturnPurchaseDraft(returnpurchasedraft));
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void submitItemReturnPurchaseDraftList(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.post("saveItemReturnPurchaseDraftList", 
                        itemreturnpurchasedrafttablemodel.getItemReturnPurchaseDraftList());
                
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
                                        ItemReturnPurchaseDraftEntity.class));
                        
                        itemreturnpurchasedrafttablemodel.setItemReturnPurchaseDraftList(list);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void submitDeletedItemReturnPurchaseDraftList(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.post("deleteItemReturnPurchaseDraftList",
                        itemreturnpurchasedrafttablemodel.getDeletedItemReturnPurchaseDraftList());
                
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
