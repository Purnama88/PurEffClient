/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.table;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.SwingWorker;
import net.purnama.model.transactional.ItemExpensesEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.ItemExpensesTableModel;

/**
 *
 * @author Purnama
 */
public class ItemExpensesTablePanel extends TablePanel{
    
    private final ItemExpensesTableModel itemexpensestablemodel;
    
    private ArrayList<ItemExpensesEntity> list;
    
    private final String expensesid;
    
    public ItemExpensesTablePanel(String expensesid){
        super();
        
        this.expensesid = expensesid;
        
        list = new ArrayList<>();
        
        itemexpensestablemodel = new ItemExpensesTableModel(list);
        
        table.setModel(itemexpensestablemodel);
        
        popupmenu.remove(menuitemdelete);
        
        load();
    }
    
    public ItemExpensesTableModel getItemExpensesTableModel(){
        return itemexpensestablemodel;
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getItemExpensesList?id="+expensesid);
                
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
                                        ItemExpensesEntity.class));
                        
                        itemexpensestablemodel.setItemExpensesList(list);
                    }
                    catch(IOException e){
                        System.out.println(e);
                    }
                }
            }
        };
        
        worker.execute();
    }
}
