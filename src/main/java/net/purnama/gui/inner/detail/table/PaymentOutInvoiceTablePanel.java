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
import net.purnama.model.transactional.PaymentOutExpensesEntity;
import net.purnama.model.transactional.PaymentOutInvoiceEntity;
import net.purnama.model.transactional.PaymentOutInvoicePurchaseEntity;
import net.purnama.model.transactional.PaymentOutReturnPurchaseEntity;
import net.purnama.rest.RestClient;
import net.purnama.tablemodel.PaymentOutInvoiceTableModel;

/**
 *
 * @author Purnama
 */
public class PaymentOutInvoiceTablePanel extends TablePanel{
    
    private final PaymentOutInvoiceTableModel paymentoutinvoicetablemodel;
    
    private final ArrayList<PaymentOutInvoiceEntity> paymentoutinvoicelist;
    
    private final String paymentoutid;
    
    public PaymentOutInvoiceTablePanel(String paymentoutid){
        this.paymentoutid = paymentoutid;
        
        paymentoutinvoicelist = new ArrayList<>();
        
        paymentoutinvoicetablemodel = new PaymentOutInvoiceTableModel(paymentoutinvoicelist);
        
        table.setModel(paymentoutinvoicetablemodel);
        
        load();
    }
    
    public final void load(){
        paymentoutinvoicelist.clear();
        loadinvoice();
        loadreturn();
        loadexpenses();
    }
    
    public final void loadinvoice(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getPaymentOutInvoicePurchaseList?paymentid="+paymentoutid);
                
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
                                        PaymentOutInvoicePurchaseEntity.class));
                        
                        paymentoutinvoicelist.addAll(list);
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
                
                response = RestClient.get("getPaymentOutReturnPurchaseList?paymentid="+paymentoutid);
                
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
                                        PaymentOutReturnPurchaseEntity.class));
                        
                        paymentoutinvoicelist.addAll(list);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public final void loadexpenses(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getPaymentOutExpensesList?paymentid="+paymentoutid);
                
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
                                        PaymentOutExpensesEntity.class));
                        
                        paymentoutinvoicelist.addAll(list);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
}
