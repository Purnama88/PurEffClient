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
import net.purnama.model.transactional.ItemInvoiceSalesEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.ItemInvoiceSalesTableModel;

/**
 *
 * @author Purnama
 */
public class ItemInvoiceSalesTablePanel extends TablePanel{
    
    private final ItemInvoiceSalesTableModel iteminvoicesalestablemodel;
    
    private ArrayList<ItemInvoiceSalesEntity> list;
    
    private final String invoicesalesid;
    
    public ItemInvoiceSalesTablePanel(String invoicesalesid){
        super();
        
        this.invoicesalesid = invoicesalesid;
        
        list = new ArrayList<>();
        
        iteminvoicesalestablemodel = new ItemInvoiceSalesTableModel(list);
        
        table.setModel(iteminvoicesalestablemodel);
        
        table.getColumnModel().getColumn(0).setPreferredWidth(25);
        table.getColumnModel().getColumn(1).setPreferredWidth(100);
        table.getColumnModel().getColumn(2).setPreferredWidth(200);
        
        popupmenu.remove(menuitemdelete);
        
        load();
    }
    
    public ItemInvoiceSalesTableModel getItemInvoiceSalesTableModel(){
        return iteminvoicesalestablemodel;
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getItemInvoiceSalesList?id="+invoicesalesid);
                
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
                                        ItemInvoiceSalesEntity.class));
                        
                        iteminvoicesalestablemodel.setItemInvoiceSalesList(list);
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
