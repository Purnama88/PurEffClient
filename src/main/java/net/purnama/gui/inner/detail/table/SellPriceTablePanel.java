/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.table;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.SwingWorker;
import net.purnama.model.SellPriceEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.SellPriceTableModel;

/**
 *
 * @author Purnama
 */
public class SellPriceTablePanel extends TablePanel{
    private final SellPriceTableModel sellpricetablemodel;
    
    private ArrayList<SellPriceEntity> list;
    
    private final String itemid;
    
    public SellPriceTablePanel(String itemid){
        super();
        
        this.itemid = itemid;
        
//        setMaximumSize(new Dimension(700, 400));
        
        list = new ArrayList<>();
        
        sellpricetablemodel = new SellPriceTableModel (list);
        
        table.setModel(sellpricetablemodel);
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getSellPriceList?itemid="+itemid);
                
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
                                        SellPriceEntity.class));
                        
                        sellpricetablemodel.setSellPriceList(list);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
}
