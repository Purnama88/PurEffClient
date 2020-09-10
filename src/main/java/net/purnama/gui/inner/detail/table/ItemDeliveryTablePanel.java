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
import javax.swing.table.TableModel;
import net.purnama.model.transactional.ItemDeliveryEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.ItemDeliveryTableModel;

/**
 *
 * @author Purnama
 */
public class ItemDeliveryTablePanel extends TablePanel{
    
    private final ItemDeliveryTableModel itemdeliverytablemodel;
    
    private ArrayList<ItemDeliveryEntity> list;
    
    private final String deliveryid;
    
    public ItemDeliveryTablePanel(String deliveryid){
        
        this.deliveryid = deliveryid;
        
        list = new ArrayList<>();
        
        itemdeliverytablemodel = new ItemDeliveryTableModel(list);
        
        table.setModel(itemdeliverytablemodel);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(25);
        
        popupmenu.remove(menuitemdelete);
        
        load();
    }
    
    public TableModel getTableModel(){
        return itemdeliverytablemodel;
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getItemDeliveryList?id="+deliveryid);
                
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
                                        ItemDeliveryEntity.class));
                        
                        itemdeliverytablemodel.setItemDeliveryList(list);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
}
