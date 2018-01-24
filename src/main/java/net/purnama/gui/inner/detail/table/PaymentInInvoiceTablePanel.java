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
import java.util.List;
import javax.swing.SwingWorker;
import net.purnama.model.transactional.PaymentInInvoiceEntity;
import net.purnama.model.transactional.PaymentInInvoiceSalesEntity;
import net.purnama.model.transactional.PaymentInReturnSalesEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.PaymentInInvoiceTableModel;

/**
 *
 * @author Purnama
 */
public class PaymentInInvoiceTablePanel extends TablePanel{
    
    private final PaymentInInvoiceTableModel paymentininvoicetablemodel;
    
    private final ArrayList<PaymentInInvoiceEntity> paymentininvoicelist;
    
    private final String paymentinid;
    
    public PaymentInInvoiceTablePanel(String paymentinid){
        this.paymentinid = paymentinid;
        
        paymentininvoicelist = new ArrayList<>();
        
        paymentininvoicetablemodel = new PaymentInInvoiceTableModel(paymentininvoicelist);
        
        table.setModel(paymentininvoicetablemodel);
        
        load();
    }
    
    public final void load(){
        paymentininvoicelist.clear();
        loadinvoice();
        loadreturn();
    }
    
    public final void loadinvoice(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getPaymentInInvoiceSalesList?paymentid="+paymentinid);
                
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
                        List list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        PaymentInInvoiceSalesEntity.class));
                        
                        paymentininvoicelist.addAll(list);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public final void loadreturn(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getPaymentInReturnSalesList?paymentid="+paymentinid);
                
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
                        List list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        PaymentInReturnSalesEntity.class));
                        
                        paymentininvoicelist.addAll(list);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
}
