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
import net.purnama.controller.ItemReturnSalesDraftController;
import net.purnama.gui.inner.detail.util.DiscountSubtotalPanel;
import net.purnama.gui.inner.detail.util.ItemTableCellEditor;
import net.purnama.gui.inner.detail.util.PercentageTableCellEditor;
import net.purnama.gui.inner.detail.util.UomReturnSalesTableCellEditor;
import net.purnama.model.transactional.draft.ItemReturnSalesDraftEntity;
import net.purnama.model.transactional.draft.ReturnSalesDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.ItemReturnSalesDraftTableModel;

/**
 *
 * @author Purnama
 */
public class ItemReturnSalesDraftTablePanel extends TablePanel{
    
    private final ItemReturnSalesDraftController itemreturnsalesdraftController;
    
    private final ItemReturnSalesDraftTableModel itemreturnsalesdrafttablemodel;
    
    private ArrayList<ItemReturnSalesDraftEntity> list;
    
    private final ItemTableCellEditor itemcelleditor;
    private final UomReturnSalesTableCellEditor uomcelleditor;
    private final PercentageTableCellEditor percentagecelleditor;
    
    private final String returnsalesdraftid;
    
    public ItemReturnSalesDraftTablePanel(String returnsalesdraftid, DiscountSubtotalPanel 
            discountsubtotalpanel){
    
        this.returnsalesdraftid = returnsalesdraftid;
        
        itemcelleditor = new ItemTableCellEditor();
        uomcelleditor = new UomReturnSalesTableCellEditor();
        percentagecelleditor = new PercentageTableCellEditor();
        
        itemreturnsalesdraftController = new ItemReturnSalesDraftController();
        
        ReturnSalesDraftEntity returnsalesdraft = new ReturnSalesDraftEntity();
        returnsalesdraft.setId(returnsalesdraftid);
        
        list = new ArrayList<>();
        
        itemreturnsalesdrafttablemodel = new ItemReturnSalesDraftTableModel(list, returnsalesdraft,
            discountsubtotalpanel);
        
        table.setModel(itemreturnsalesdrafttablemodel);
        
        TableColumn column1 = table.getColumnModel().getColumn(1);
        column1.setCellEditor(itemcelleditor);
        TableColumn column3 = table.getColumnModel().getColumn(3);
        column3.setCellEditor(uomcelleditor);
        TableColumn column5 = table.getColumnModel().getColumn(5);
        column5.setCellEditor(percentagecelleditor);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(25);
        table.getColumnModel().getColumn(1).setPreferredWidth(300);
        
        menuitemdelete.addActionListener((ActionEvent e) -> {
            itemreturnsalesdrafttablemodel.deleteRow(table.getSelectedRow());
        });
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getItemReturnSalesDraftList?id="+returnsalesdraftid);
                
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
                                        ItemReturnSalesDraftEntity.class));
                        
                        itemreturnsalesdrafttablemodel.setItemReturnSalesDraftList(list);
                        ReturnSalesDraftEntity returnsalesdraft = new ReturnSalesDraftEntity();
                        returnsalesdraft.setId(returnsalesdraftid);
        
                        itemreturnsalesdrafttablemodel.
                        addRow(itemreturnsalesdraftController.createEmptyItemReturnSalesDraft(returnsalesdraft));
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void submitItemReturnSalesDraftList(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.post("saveItemReturnSalesDraftList", 
                        itemreturnsalesdrafttablemodel.getItemReturnSalesDraftList());
                
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
                                        ItemReturnSalesDraftEntity.class));
                        
                        itemreturnsalesdrafttablemodel.setItemReturnSalesDraftList(list);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void submitDeletedItemReturnSalesDraftList(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.post("deleteItemReturnSalesDraftList",
                        itemreturnsalesdrafttablemodel.getDeletedItemReturnSalesDraftList());
                
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
