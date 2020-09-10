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
import net.purnama.model.transactional.ItemInvoiceWarehouseInEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.ItemInvoiceWarehouseInTableModel;

/**
 *
 * @author Purnama
 */
public class ItemInvoiceWarehouseInTablePanel extends TablePanel{
    
    private final ItemInvoiceWarehouseInTableModel iteminvoicewarehouseintablemodel;
    
    private ArrayList<ItemInvoiceWarehouseInEntity> list;
    
    private final String invoicewarehouseinid;
    
    public ItemInvoiceWarehouseInTablePanel(String invoicewarehouseinid){
        
        super();
        
        this.invoicewarehouseinid = invoicewarehouseinid;
        
        list = new ArrayList<>();
        
        iteminvoicewarehouseintablemodel = new ItemInvoiceWarehouseInTableModel(list);
        
        table.setModel(iteminvoicewarehouseintablemodel);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(25);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        
        popupmenu.remove(menuitemdelete);
        
        load();
    }
    
    public ItemInvoiceWarehouseInTableModel getItemInvoiceWarehouseInTableModel(){
        return iteminvoicewarehouseintablemodel;
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                response = RestClient.get("getItemInvoiceWarehouseInList?id="+invoicewarehouseinid);
                
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
                                        ItemInvoiceWarehouseInEntity.class));
                        
                        iteminvoicewarehouseintablemodel.setItemInvoiceWarehouseInList(list);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
}
