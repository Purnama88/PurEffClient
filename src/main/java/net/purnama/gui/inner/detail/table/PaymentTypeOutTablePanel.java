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
import net.purnama.model.transactional.PaymentTypeOutEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.PaymentTypeOutTableModel;

/**
 *
 * @author Purnama
 */
public class PaymentTypeOutTablePanel extends TablePanel{
    
    private final PaymentTypeOutTableModel paymenttypeouttablemodel;
    
    private ArrayList<PaymentTypeOutEntity> list;
    
    private final String paymentoutid;
    
    public PaymentTypeOutTablePanel(String paymentoutid){
        
        this.paymentoutid = paymentoutid;
        
        list = new ArrayList<>();
        
        paymenttypeouttablemodel = new PaymentTypeOutTableModel(list);
        
        table.setModel(paymenttypeouttablemodel);
        
        load();
    }
    
    public PaymentTypeOutTableModel getPaymentTypeOutTableModel(){
        return paymenttypeouttablemodel;
    } 
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getPaymentTypeOutList?paymentid="+paymentoutid);
                
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
                                        PaymentTypeOutEntity.class));
                        
                        paymenttypeouttablemodel.setPaymentTypeOutList(list);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public final void save(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.post("savePaymentTypeOutList", 
                        paymenttypeouttablemodel.getPaymentTypeOutList());
                
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

                }
            }
        };
        
        worker.execute();
    }
}
