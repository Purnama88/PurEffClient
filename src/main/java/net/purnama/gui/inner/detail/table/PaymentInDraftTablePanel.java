/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.table;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Spring;
import javax.swing.SpringLayout;
import javax.swing.SwingWorker;
import net.purnama.gui.inner.detail.util.ItemPaymentPanel2;
import net.purnama.gui.inner.detail.util.PaidPanel;
import net.purnama.gui.inner.detail.util.PaymentPanel;
import net.purnama.gui.library.MyButton;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.model.transactional.draft.PaymentInInvoiceDraftEntity;
import net.purnama.model.transactional.draft.PaymentInInvoiceSalesDraftEntity;
import net.purnama.model.transactional.draft.PaymentInReturnSalesDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;

/**
 *
 * @author Purnama
 */
public class PaymentInDraftTablePanel extends JPanel{
    
    private final JScrollPane leftscrollpane, rightscrollpane;
    private final JPanel middlepanel;
    
    private final MyButton previousbutton, nextbutton;
    
    private final PaymentPanel leftpaymentpanel, rightpaymentpanel;
    
    private String id, partnerid, currencyid;
    
    private final Map<String, PaymentInInvoiceSalesDraftEntity> paymentininvoicesaleslist, 
            selectedpaymentininvoicesaleslist;
    private final Map<String, PaymentInReturnSalesDraftEntity> paymentinreturnsaleslist, 
            selectedpaymentinreturnsaleslist;
    
    private final PaidPanel paidpanel;
    
    public PaymentInDraftTablePanel(String id, PaidPanel paidpanel){
        super();
        
//        GroupLayout layout = new GroupLayout(this);
//        setLayout(layout);
        
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        
        Spring pw = layout.getConstraint(SpringLayout.WIDTH,  this);
        Spring ph = layout.getConstraint(SpringLayout.HEIGHT, this);
        
        this.id = id;
        this.paidpanel = paidpanel;
        
        paymentininvoicesaleslist = new HashMap<>();
        selectedpaymentininvoicesaleslist = new HashMap<>();
        paymentinreturnsaleslist = new HashMap<>();
        selectedpaymentinreturnsaleslist = new HashMap<>();
        
        leftscrollpane = new JScrollPane();
        rightscrollpane = new JScrollPane();
        
        leftpaymentpanel = new PaymentPanel();
        rightpaymentpanel = new PaymentPanel();
        
        leftscrollpane.getViewport().add(leftpaymentpanel, null);
        rightscrollpane.getViewport().add(rightpaymentpanel, null);
        
        middlepanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
//        middlepanel.setPreferredSize(new Dimension(35, 100));
        
        previousbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Arrow1Left_16.png"), 
                24, 24);
        previousbutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_NAVIGATION_MOVE"));
        
        nextbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Arrow1Right_16.png"), 
                24, 24);
        nextbutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_NAVIGATION_MOVE"));
        
        middlepanel.add(nextbutton);
        middlepanel.add(previousbutton);
        
        GlobalFunctions.setPercentage(layout.getConstraints(leftscrollpane), pw, ph, .0f, .0f, .475f, 1.0f);
        GlobalFunctions.setPercentage(layout.getConstraints(middlepanel), pw, ph, .475f, .0f, .05f, 1.0f);
        GlobalFunctions.setPercentage(layout.getConstraints(rightscrollpane), pw, ph, .525f, .0f, .475f, 1.0f);
        
        add(leftscrollpane);
        add(middlepanel);
        add(rightscrollpane);
        
//        layout.setAutoCreateGaps(true);
//        layout.setAutoCreateContainerGaps(true);
//        
//        layout.setHorizontalGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
//            .addGroup(layout.createSequentialGroup()
//            .addComponent(leftscrollpane,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//            .addComponent(middlepanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
//            .addComponent(rightscrollpane, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//        ));
//        
//        layout.setVerticalGroup(layout.createSequentialGroup()
//            .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
//            .addComponent(leftscrollpane,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
//            .addComponent(middlepanel, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
//            .addComponent(rightscrollpane,GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
//        );
     
        nextbutton.addActionListener((ActionEvent e) -> {
            for(Component component : leftpaymentpanel.getComponents()){
                ItemPaymentPanel2 ipp2 = (ItemPaymentPanel2)component;
                
                if(ipp2.isSelected()){
                    PaymentInInvoiceDraftEntity p = ipp2.getPaymentInInvoiceDraft();
                    
                    if(p instanceof PaymentInInvoiceSalesDraftEntity){
                        PaymentInInvoiceSalesDraftEntity piisd = (PaymentInInvoiceSalesDraftEntity)p;
                        
                        PaymentInInvoiceSalesDraftEntity selectedpiisd = 
                                selectedpaymentininvoicesaleslist.get(piisd.getInvoicesales().getNumber());
                        
                        if(selectedpiisd == null){
                            
                            double diff = ipp2.getDifference();
                            
                            //pindah full
                            if(diff == 0){
                                paidpanel.setPaid(paidpanel.getPaid()+ piisd.getAmount());
                                paymentininvoicesaleslist.remove(piisd.getInvoicesales().getNumber());

                                selectedpaymentininvoicesaleslist.put(piisd.getInvoicesales().getNumber(),
                                        piisd);
                            }
                            //pindah sebagian
                            else if(diff > 0){
                                paidpanel.setPaid(paidpanel.getPaid()+ piisd.getAmount());
                                paymentininvoicesaleslist.remove(piisd.getInvoicesales().getNumber());

                                selectedpaymentininvoicesaleslist.put(piisd.getInvoicesales().getNumber(),
                                        piisd);
                                
                                selectedpiisd = new PaymentInInvoiceSalesDraftEntity();
                                selectedpiisd.setInvoicesales(piisd.getInvoicesales());
                                selectedpiisd.setPaymentindraft(piisd.getPaymentindraft());
                                selectedpiisd.setAmount(piisd.getInvoicesales().getRemaining() - piisd.getAmount());
                                paymentininvoicesaleslist.put(selectedpiisd.
                                        getInvoicesales().getNumber(),
                                        selectedpiisd);
                            }
                            else{
                                //error diemin aja 
                            }
                        }
                        else{
                            double both = piisd.getAmount() + selectedpiisd.getAmount();
                            double unpaid = piisd.getInvoicesales().getRemaining();
                            
                            //pindah full
                            if(both == unpaid){
                                paidpanel.setPaid(paidpanel.getPaid()+ piisd.getAmount());
                                paymentininvoicesaleslist.remove(piisd.getInvoicesales().getNumber());
                                
                                selectedpiisd.setAmount(selectedpiisd.getAmount() + piisd.getAmount());
                            }
                            //pindah sebagian
                            else if(both < unpaid){
                                paidpanel.setPaid(paidpanel.getPaid()+ piisd.getAmount());
                                selectedpiisd.setAmount(selectedpiisd.getAmount() + piisd.getAmount());
                                
                                piisd.setAmount(unpaid - selectedpiisd.getAmount());
                            }
                            //error kelebihan
                            else{
                                
                            }
                        }
                    }
                    else{
                        PaymentInReturnSalesDraftEntity pirsd = (PaymentInReturnSalesDraftEntity)p;
                        
                        PaymentInReturnSalesDraftEntity selectedpirsd = 
                                selectedpaymentinreturnsaleslist.get(pirsd.getReturnsales().getNumber());
                        
                        
                        if(selectedpirsd == null){
                            
                            double diff = ipp2.getDifference();
                            
                            //pindah full
                            if(diff == 0){
                                paidpanel.setPaid(paidpanel.getPaid()- pirsd.getAmount());
                                paymentinreturnsaleslist.remove(pirsd.getReturnsales().getNumber());

                                selectedpaymentinreturnsaleslist.put(pirsd.getReturnsales().getNumber(),
                                        pirsd);
                            }
                            //pindah sebagian
                            else if(diff > 0){
                                paidpanel.setPaid(paidpanel.getPaid()- pirsd.getAmount());
                                paymentinreturnsaleslist.remove(pirsd.getReturnsales().getNumber());

                                selectedpaymentinreturnsaleslist.put(pirsd.getReturnsales().getNumber(),
                                        pirsd);
                                
                                selectedpirsd = new PaymentInReturnSalesDraftEntity();
                                selectedpirsd.setReturnsales(pirsd.getReturnsales());
                                selectedpirsd.setPaymentindraft(pirsd.getPaymentindraft());
                                selectedpirsd.setAmount(pirsd.getReturnsales().getRemaining() - pirsd.getAmount());
                                paymentinreturnsaleslist.put(selectedpirsd.
                                        getReturnsales().getNumber(),
                                        selectedpirsd);
                            }
                            else{
                                //error diemin aja 
                            }
                        }
                        else{
                            double both = pirsd.getAmount() + selectedpirsd.getAmount();
                            double unpaid = pirsd.getReturnsales().getRemaining();
                            
                            //pindah full
                            if(both == unpaid){
                                paidpanel.setPaid(paidpanel.getPaid()- pirsd.getAmount());
                                paymentinreturnsaleslist.remove(pirsd.getReturnsales().getNumber());
                                
                                selectedpirsd.setAmount(selectedpirsd.getAmount() + pirsd.getAmount());
                            }
                            //pindah sebagian
                            else if(both < unpaid){
                                paidpanel.setPaid(paidpanel.getPaid()- pirsd.getAmount());
                                selectedpirsd.setAmount(selectedpirsd.getAmount() + pirsd.getAmount());
                                
                                pirsd.setAmount(unpaid - selectedpirsd.getAmount());
                            }
                            //error kelebihan
                            else{
                                
                            }
                        }
                    }
                }
            }
            
            render();
        });
        
        previousbutton.addActionListener((ActionEvent e) -> {
            for(Component component : rightpaymentpanel.getComponents()){
                ItemPaymentPanel2 ipp2 = (ItemPaymentPanel2)component;
                
                if(ipp2.isSelected()){
                    PaymentInInvoiceDraftEntity p = ipp2.getPaymentInInvoiceDraft();
                    
                    if(p instanceof PaymentInInvoiceSalesDraftEntity){
                        PaymentInInvoiceSalesDraftEntity selectedpiisd = (PaymentInInvoiceSalesDraftEntity)p;
                        
                        PaymentInInvoiceSalesDraftEntity piisd = paymentininvoicesaleslist.
                                get(selectedpiisd.getInvoicesales().getNumber());
                        
                        paidpanel.setPaid(paidpanel.getPaid() - selectedpiisd.getAmount());
                        
                        if(piisd == null){
                            selectedpaymentininvoicesaleslist.remove(selectedpiisd.
                                    getInvoicesales().getNumber());

                            paymentininvoicesaleslist.put(selectedpiisd.getInvoicesales().getNumber(),selectedpiisd);
                        }
                        else{
                            selectedpaymentininvoicesaleslist.remove(selectedpiisd.
                                    getInvoicesales().getNumber());
                            
                            piisd.setAmount(piisd.getAmount() + selectedpiisd.getAmount());
                        }
                    }
                    else{
                        PaymentInReturnSalesDraftEntity selectedpirsd = (PaymentInReturnSalesDraftEntity)p;
                        
                        PaymentInReturnSalesDraftEntity pirsd = paymentinreturnsaleslist.
                                get(selectedpirsd.getReturnsales().getNumber());
                        
                        paidpanel.setPaid(paidpanel.getPaid() + selectedpirsd.getAmount());
                        
                        if(pirsd == null){
                            selectedpaymentinreturnsaleslist.remove(selectedpirsd.
                                    getReturnsales().getNumber());

                            paymentinreturnsaleslist.put(selectedpirsd.getReturnsales().getNumber(),selectedpirsd);
                        }
                        else{
                            selectedpaymentinreturnsaleslist.remove(selectedpirsd.
                                    getReturnsales().getNumber());
                            
                            pirsd.setAmount(pirsd.getAmount() - selectedpirsd.getAmount());
                        }
                    }
                }
            }
            
            render();
        });
    }
    
    public final void render(){
        leftpaymentpanel.removeAll();
        rightpaymentpanel.removeAll();
        
        paymentininvoicesaleslist.forEach((number, paymentininvoicesalesdraft) -> {
            ItemPaymentPanel2 ipp2 = new ItemPaymentPanel2(paymentininvoicesalesdraft);
            leftpaymentpanel.add(ipp2);
        });
        
        selectedpaymentininvoicesaleslist.forEach((number, paymentininvoicesalesdraft) -> {
            ItemPaymentPanel2 ipp2 = new ItemPaymentPanel2(paymentininvoicesalesdraft);
            ipp2.setTextFieldEditable(false);
            rightpaymentpanel.add(ipp2);
        });
        
        paymentinreturnsaleslist.forEach((number, paymentinreturnsalesdraft) -> {
            ItemPaymentPanel2 ipp2 = new ItemPaymentPanel2(paymentinreturnsalesdraft);
            
            leftpaymentpanel.add(ipp2);
        });
        
        selectedpaymentinreturnsaleslist.forEach((number, paymentinreturnsalesdraft) -> {
            ItemPaymentPanel2 ipp2 = new ItemPaymentPanel2(paymentinreturnsalesdraft);
            ipp2.setTextFieldEditable(false);
            rightpaymentpanel.add(ipp2);
        });
        
        refresh();
    }
    
    public void load(String partnerid, String currencyid){
        clear();
        
        this.partnerid = partnerid;
        this.currencyid = currencyid;
        
        loadselectedpaymentinvoice();
        loadselectedpaymentreturn();
        loadpaymentinvoice();
        loadpaymentreturn();
    }
    
    public void clear(){
        paymentininvoicesaleslist.clear();
        paymentinreturnsaleslist.clear();
        selectedpaymentininvoicesaleslist.clear();
        selectedpaymentinreturnsaleslist.clear();
        
        leftpaymentpanel.removeAll();
        rightpaymentpanel.removeAll();
    }
   
    public void refresh(){
        leftpaymentpanel.refresh();
        rightpaymentpanel.refresh();
    }
    
    public final void loadselectedpaymentinvoice(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                response = RestClient.get("getSelectedPaymentInInvoiceSalesDraftList?"
                        + "paymentid=" + id + "&partnerid=" + partnerid + "&currencyid=" + currencyid);
                
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
                        ArrayList<PaymentInInvoiceSalesDraftEntity> list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        PaymentInInvoiceSalesDraftEntity.class));
                        
                        for(PaymentInInvoiceSalesDraftEntity paymentininvoicesalesdraft : list){
                            selectedpaymentininvoicesaleslist.put(paymentininvoicesalesdraft.
                                    getInvoicesales().getNumber(), paymentininvoicesalesdraft);
                            ItemPaymentPanel2 ipp2 = new ItemPaymentPanel2(paymentininvoicesalesdraft);
                            ipp2.setTextFieldEditable(false);
                            paidpanel.setPaid(paidpanel.getPaid() + ipp2.getTextFieldValue());
                            rightpaymentpanel.add(ipp2);
                        }
                        
                        refresh();
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public final void loadselectedpaymentreturn(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                response = RestClient.get("getSelectedPaymentInReturnSalesDraftList?"
                        + "paymentid=" + id + "&partnerid=" + partnerid + "&currencyid=" + currencyid);
                
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
                        ArrayList<PaymentInReturnSalesDraftEntity> list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        PaymentInReturnSalesDraftEntity.class));
                        
                        for(PaymentInReturnSalesDraftEntity paymentinreturnsalesdraft : list){
                            selectedpaymentinreturnsaleslist.put(paymentinreturnsalesdraft.
                                    getReturnsales().getNumber(), paymentinreturnsalesdraft);
                            ItemPaymentPanel2 ipp2 = new ItemPaymentPanel2(paymentinreturnsalesdraft);
                            ipp2.setTextFieldEditable(false);
                            paidpanel.setPaid(paidpanel.getPaid() - ipp2.getTextFieldValue());
                            rightpaymentpanel.add(ipp2);
                        }
                        
                        refresh();
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public final void loadpaymentinvoice(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                response = RestClient.get("getPaymentInInvoiceSalesDraftList?"
                        + "paymentid=" + id + "&partnerid=" + partnerid + "&currencyid=" + currencyid);
                
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
                        ArrayList<PaymentInInvoiceSalesDraftEntity> list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        PaymentInInvoiceSalesDraftEntity.class));
                        
                        for(PaymentInInvoiceSalesDraftEntity paymentininvoicesalesdraft : list){
                            paymentininvoicesaleslist.put(paymentininvoicesalesdraft.
                                    getInvoicesales().getNumber(), paymentininvoicesalesdraft);
                            
                            ItemPaymentPanel2 ipp2 = new ItemPaymentPanel2(paymentininvoicesalesdraft);

                            leftpaymentpanel.add(ipp2);
                        }
                        
                        refresh();
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public final void loadpaymentreturn(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                response = RestClient.get("getPaymentInReturnSalesDraftList?"
                        + "paymentid=" + id + "&partnerid=" + partnerid + "&currencyid=" + currencyid);
                
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
                        ArrayList<PaymentInReturnSalesDraftEntity> list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        PaymentInReturnSalesDraftEntity.class));
                        
                        for(PaymentInReturnSalesDraftEntity paymentinreturnsalesdraft : list){
                            paymentinreturnsaleslist.put(paymentinreturnsalesdraft.
                                    getReturnsales().getNumber(), paymentinreturnsalesdraft);
                            ItemPaymentPanel2 ipp2 = new ItemPaymentPanel2(paymentinreturnsalesdraft);
                            leftpaymentpanel.add(ipp2);
                        }
                        
                        refresh();
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public final void saveselectedpaymentreturn(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                List<PaymentInReturnSalesDraftEntity> ls = new ArrayList(
                        selectedpaymentinreturnsaleslist.values());
                
                response = RestClient.post("savePaymentInReturnSalesDraftList?paymentid="+id, ls) ;
                
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
    
    public final void saveselectedpaymentinvoice(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                List<PaymentInInvoiceSalesDraftEntity> ls = new ArrayList(
                        selectedpaymentininvoicesaleslist.values());
                
                response = RestClient.post("savePaymentInInvoiceSalesDraftList?paymentid="+id, ls) ;
                
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
