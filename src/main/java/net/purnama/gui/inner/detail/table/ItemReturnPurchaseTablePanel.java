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
import net.purnama.model.transactional.ItemReturnPurchaseEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.ItemReturnPurchaseTableModel;

/**
 *
 * @author Purnama
 */
public class ItemReturnPurchaseTablePanel extends TablePanel{
    
    private final ItemReturnPurchaseTableModel itemreturnpurchasetablemodel;
    
    private ArrayList<ItemReturnPurchaseEntity> list;
    
    private final String returnpurchaseid;
    
    public ItemReturnPurchaseTablePanel(String returnpurchaseid){
        super();
        
        this.returnpurchaseid = returnpurchaseid;
        
        list = new ArrayList<>();
        
        itemreturnpurchasetablemodel = new ItemReturnPurchaseTableModel(list);
        
        table.setModel(itemreturnpurchasetablemodel);
        
        popupmenu.remove(menuitemdelete);
        
        load();
    }
    
    public ItemReturnPurchaseTableModel getItemReturnPurchaseTableModel(){
        return itemreturnpurchasetablemodel;
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getItemReturnPurchaseList?id="+returnpurchaseid);
                
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
                                        ItemReturnPurchaseEntity.class));
                        
                        itemreturnpurchasetablemodel.setItemReturnPurchaseList(list);
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
