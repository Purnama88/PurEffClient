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
import net.purnama.model.transactional.PaymentTypeInEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.PaymentTypeInTableModel;

/**
 *
 * @author Purnama
 */
public class PaymentTypeInTablePanel extends TablePanel{
    
    private final PaymentTypeInTableModel paymenttypeintablemodel;
    
    private ArrayList<PaymentTypeInEntity> list;
    
    private final String paymentinid;
    
    public PaymentTypeInTablePanel(String paymentinid){
        
        this.paymentinid = paymentinid;
        
        list = new ArrayList<>();
        
        paymenttypeintablemodel = new PaymentTypeInTableModel(list);
        
        table.setModel(paymenttypeintablemodel);
        
        load();
    }
    
    public PaymentTypeInTableModel getPaymentTypeInTableModel(){
        return paymenttypeintablemodel;
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getPaymentTypeInList?paymentid="+paymentinid);
                
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
                                        PaymentTypeInEntity.class));
                        
                        paymenttypeintablemodel.setPaymentTypeInList(list);
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
                
                response = RestClient.post("savePaymentTypeInList",
                        paymenttypeintablemodel.getPaymentTypeInList());
                
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
