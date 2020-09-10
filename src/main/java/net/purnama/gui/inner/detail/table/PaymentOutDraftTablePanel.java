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
import java.awt.Dimension;
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
import net.purnama.gui.inner.detail.util.ItemPaymentPanel;
import net.purnama.gui.inner.detail.util.PaidPanel;
import net.purnama.gui.inner.detail.util.PaymentPanel;
import net.purnama.gui.library.MyButton;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.model.transactional.draft.PaymentOutExpensesDraftEntity;
import net.purnama.model.transactional.draft.PaymentOutInvoiceDraftEntity;
import net.purnama.model.transactional.draft.PaymentOutInvoicePurchaseDraftEntity;
import net.purnama.model.transactional.draft.PaymentOutReturnPurchaseDraftEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;

/**
 *
 * @author Purnama
 */
public class PaymentOutDraftTablePanel extends JPanel{
    
    private final JScrollPane leftscrollpane, rightscrollpane;
    private final JPanel middlepanel;
    
    private final MyButton previousbutton, nextbutton;
    
    private final PaymentPanel leftpaymentpanel, rightpaymentpanel;
    
    private String id, partnerid, currencyid;
    
    private final Map<String, PaymentOutInvoicePurchaseDraftEntity> paymentoutinvoicepurchaselist, 
            selectedpaymentoutinvoicepurchaselist;
    private final Map<String, PaymentOutReturnPurchaseDraftEntity> paymentoutreturnpurchaselist, 
            selectedpaymentoutreturnpurchaselist;
    private final Map<String, PaymentOutExpensesDraftEntity> paymentoutexpenseslist, 
            selectedpaymentoutexpenseslist;
    
    private final PaidPanel paidpanel;
    
    public PaymentOutDraftTablePanel(String id, PaidPanel paidpanel){
        super();
        
        SpringLayout layout = new SpringLayout();
        setLayout(layout);
        
        Spring pw = layout.getConstraint(SpringLayout.WIDTH,  this);
        Spring ph = layout.getConstraint(SpringLayout.HEIGHT, this);
        
        this.id = id;
        this.paidpanel = paidpanel;
        
        paymentoutinvoicepurchaselist = new HashMap<>();
        selectedpaymentoutinvoicepurchaselist = new HashMap<>();
        paymentoutreturnpurchaselist = new HashMap<>();
        selectedpaymentoutreturnpurchaselist = new HashMap<>();
        paymentoutexpenseslist = new HashMap<>();
        selectedpaymentoutexpenseslist = new HashMap<>();
        
        leftscrollpane = new JScrollPane();
        leftscrollpane.setPreferredSize(new Dimension(400, 300));
        rightscrollpane = new JScrollPane();
        rightscrollpane.setPreferredSize(new Dimension(400, 300));
        
        leftpaymentpanel = new PaymentPanel();
        rightpaymentpanel = new PaymentPanel();
        
        leftscrollpane.getViewport().add(leftpaymentpanel, null);
        rightscrollpane.getViewport().add(rightpaymentpanel, null);
        
        middlepanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
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
        
        nextbutton.addActionListener((ActionEvent e) -> {
            for(Component component : leftpaymentpanel.getComponents()){
                ItemPaymentPanel ipp = (ItemPaymentPanel)component;
                
                if(ipp.isSelected()){
                    PaymentOutInvoiceDraftEntity p = ipp.getPaymentOutInvoiceDraft();
                    
                    if(p instanceof PaymentOutInvoicePurchaseDraftEntity){
                        PaymentOutInvoicePurchaseDraftEntity piisd = (PaymentOutInvoicePurchaseDraftEntity)p;
                        
                        PaymentOutInvoicePurchaseDraftEntity selectedpiisd = 
                                selectedpaymentoutinvoicepurchaselist.get(piisd.getInvoicepurchase().getNumber());
                        
                        if(selectedpiisd == null){
                            
                            double diff = ipp.getDifference();
                            
                            //pindah full
                            if(diff == 0){
                                paidpanel.setPaid(paidpanel.getPaid() + piisd.getAmount());
                                paymentoutinvoicepurchaselist.remove(piisd.getInvoicepurchase().getNumber());

                                selectedpaymentoutinvoicepurchaselist.put(piisd.getInvoicepurchase().getNumber(),
                                        piisd);
                            }
                            //pindah sebagian
                            else if(diff > 0){
                                paidpanel.setPaid(paidpanel.getPaid() + piisd.getAmount());
                                paymentoutinvoicepurchaselist.remove(piisd.getInvoicepurchase().getNumber());

                                selectedpaymentoutinvoicepurchaselist.put(piisd.getInvoicepurchase().getNumber(),
                                        piisd);
                                
                                selectedpiisd = new PaymentOutInvoicePurchaseDraftEntity();
                                selectedpiisd.setInvoicepurchase(piisd.getInvoicepurchase());
                                selectedpiisd.setPaymentoutdraft(piisd.getPaymentoutdraft());
                                selectedpiisd.setAmount(piisd.getInvoicepurchase().getRemaining() - piisd.getAmount());
                                paymentoutinvoicepurchaselist.put(selectedpiisd.
                                        getInvoicepurchase().getNumber(),
                                        selectedpiisd);
                            }
                            else{
                                //error diemin aja 
                            }
                        }
                        else{
                            double both = piisd.getAmount() + selectedpiisd.getAmount();
                            double unpaid = piisd.getInvoicepurchase().getRemaining();
                            
                            //pindah full
                            if(both == unpaid){
                                paidpanel.setPaid(paidpanel.getPaid() + piisd.getAmount());
                                paymentoutinvoicepurchaselist.remove(piisd.getInvoicepurchase().getNumber());
                                
                                selectedpiisd.setAmount(selectedpiisd.getAmount() + piisd.getAmount());
                            }
                            //pindah sebagian
                            else if(both < unpaid){
                                paidpanel.setPaid(paidpanel.getPaid() + piisd.getAmount());
                                selectedpiisd.setAmount(selectedpiisd.getAmount() + piisd.getAmount());
                                
                                piisd.setAmount(unpaid - selectedpiisd.getAmount());
                            }
                            //error kelebihan
                            else{
                                
                            }
                        }
                    }
                    else if(p instanceof PaymentOutExpensesDraftEntity){
                        PaymentOutExpensesDraftEntity piisd = (PaymentOutExpensesDraftEntity)p;
                        
                        PaymentOutExpensesDraftEntity selectedpiisd = 
                                selectedpaymentoutexpenseslist.get(piisd.getExpenses().getNumber());
                        
                        if(selectedpiisd == null){
                            
                            double diff = ipp.getDifference();
                            
                            //pindah full
                            if(diff == 0){
                                paidpanel.setPaid(paidpanel.getPaid() + piisd.getAmount());
                                paymentoutexpenseslist.remove(piisd.getExpenses().getNumber());

                                selectedpaymentoutexpenseslist.put(piisd.getExpenses().getNumber(),
                                        piisd);
                            }
                            //pindah sebagian
                            else if(diff > 0){
                                paidpanel.setPaid(paidpanel.getPaid() + piisd.getAmount());
                                paymentoutexpenseslist.remove(piisd.getExpenses().getNumber());

                                selectedpaymentoutexpenseslist.put(piisd.getExpenses().getNumber(),
                                        piisd);
                                
                                selectedpiisd = new PaymentOutExpensesDraftEntity();
                                selectedpiisd.setExpenses(piisd.getExpenses());
                                selectedpiisd.setPaymentoutdraft(piisd.getPaymentoutdraft());
                                selectedpiisd.setAmount(piisd.getExpenses().getRemaining() - piisd.getAmount());
                                paymentoutexpenseslist.put(selectedpiisd.
                                        getExpenses().getNumber(),
                                        selectedpiisd);
                            }
                            else{
                                //error diemin aja 
                            }
                        }
                        else{
                            double both = piisd.getAmount() + selectedpiisd.getAmount();
                            double unpaid = piisd.getExpenses().getRemaining();
                            
                            //pindah full
                            if(both == unpaid){
                                paidpanel.setPaid(paidpanel.getPaid() + piisd.getAmount());
                                paymentoutexpenseslist.remove(piisd.getExpenses().getNumber());
                                
                                selectedpiisd.setAmount(selectedpiisd.getAmount() + piisd.getAmount());
                            }
                            //pindah sebagian
                            else if(both < unpaid){
                                paidpanel.setPaid(paidpanel.getPaid() + piisd.getAmount());
                                selectedpiisd.setAmount(selectedpiisd.getAmount() + piisd.getAmount());
                                
                                piisd.setAmount(unpaid - selectedpiisd.getAmount());
                            }
                            //error kelebihan
                            else{
                                
                            }
                        }
                    }
                    else{
                        PaymentOutReturnPurchaseDraftEntity pirsd = (PaymentOutReturnPurchaseDraftEntity)p;
                        
                        PaymentOutReturnPurchaseDraftEntity selectedpirsd = 
                                selectedpaymentoutreturnpurchaselist.get(pirsd.getReturnpurchase().getNumber());
                        
                        if(selectedpirsd == null){
                            
                            double diff = ipp.getDifference();
                            
                            //pindah full
                            if(diff == 0){
                                paidpanel.setPaid(paidpanel.getPaid() - pirsd.getAmount());
                                paymentoutreturnpurchaselist.remove(pirsd.getReturnpurchase().getNumber());

                                selectedpaymentoutreturnpurchaselist.put(pirsd.getReturnpurchase().getNumber(),
                                        pirsd);
                            }
                            //pindah sebagian
                            else if(diff > 0){
                                paidpanel.setPaid(paidpanel.getPaid() - pirsd.getAmount());
                                paymentoutreturnpurchaselist.remove(pirsd.getReturnpurchase().getNumber());

                                selectedpaymentoutreturnpurchaselist.put(pirsd.getReturnpurchase().getNumber(),
                                        pirsd);
                                
                                selectedpirsd = new PaymentOutReturnPurchaseDraftEntity();
                                selectedpirsd.setReturnpurchase(pirsd.getReturnpurchase());
                                selectedpirsd.setPaymentoutdraft(pirsd.getPaymentoutdraft());
                                selectedpirsd.setAmount(pirsd.getReturnpurchase().getRemaining() - pirsd.getAmount());
                                paymentoutreturnpurchaselist.put(selectedpirsd.
                                        getReturnpurchase().getNumber(),
                                        selectedpirsd);
                            }
                            else{
                                //error diemin aja 
                            }
                        }
                        else{
                            double both = pirsd.getAmount() + selectedpirsd.getAmount();
                            double unpaid = pirsd.getReturnpurchase().getRemaining();
                            
                            //pindah full
                            if(both == unpaid){
                                paidpanel.setPaid(paidpanel.getPaid() - pirsd.getAmount());
                                paymentoutreturnpurchaselist.remove(pirsd.getReturnpurchase().getNumber());
                                
                                selectedpirsd.setAmount(selectedpirsd.getAmount() + pirsd.getAmount());
                            }
                            //pindah sebagian
                            else if(both < unpaid){
                                paidpanel.setPaid(paidpanel.getPaid() - pirsd.getAmount());
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
                ItemPaymentPanel ipp = (ItemPaymentPanel)component;
                
                if(ipp.isSelected()){
                    PaymentOutInvoiceDraftEntity p = ipp.getPaymentOutInvoiceDraft();
                    
                    if(p instanceof PaymentOutInvoicePurchaseDraftEntity){
                        PaymentOutInvoicePurchaseDraftEntity selectedpiisd = (PaymentOutInvoicePurchaseDraftEntity)p;
                        
                        PaymentOutInvoicePurchaseDraftEntity piisd = paymentoutinvoicepurchaselist.
                                get(selectedpiisd.getInvoicepurchase().getNumber());
                        
                        paidpanel.setPaid(paidpanel.getPaid() - selectedpiisd.getAmount());
                        
                        if(piisd == null){
                            selectedpaymentoutinvoicepurchaselist.remove(selectedpiisd.
                                    getInvoicepurchase().getNumber());

                            paymentoutinvoicepurchaselist.put(selectedpiisd.getInvoicepurchase().getNumber(),selectedpiisd);
                        }
                        else{
                            selectedpaymentoutinvoicepurchaselist.remove(selectedpiisd.
                                    getInvoicepurchase().getNumber());
                            
                            piisd.setAmount(piisd.getAmount() + selectedpiisd.getAmount());
                        }
                    }
                    else if(p instanceof PaymentOutExpensesDraftEntity){
                        PaymentOutExpensesDraftEntity selectedpiisd = (PaymentOutExpensesDraftEntity)p;
                        
                        PaymentOutExpensesDraftEntity piisd = paymentoutexpenseslist.
                                get(selectedpiisd.getExpenses().getNumber());
                        
                        paidpanel.setPaid(paidpanel.getPaid() - selectedpiisd.getAmount());
                        
                        if(piisd == null){
                            selectedpaymentoutexpenseslist.remove(selectedpiisd.
                                    getExpenses().getNumber());

                            paymentoutexpenseslist.put(selectedpiisd.getExpenses().getNumber(),selectedpiisd);
                        }
                        else{
                            selectedpaymentoutexpenseslist.remove(selectedpiisd.
                                    getExpenses().getNumber());
                            
                            piisd.setAmount(piisd.getAmount() + selectedpiisd.getAmount());
                        }
                    }
                    else{
                        PaymentOutReturnPurchaseDraftEntity selectedpirsd = (PaymentOutReturnPurchaseDraftEntity)p;
                        
                        PaymentOutReturnPurchaseDraftEntity pirsd = paymentoutreturnpurchaselist.
                                get(selectedpirsd.getReturnpurchase().getNumber());
                        
                        paidpanel.setPaid(paidpanel.getPaid() + selectedpirsd.getAmount());
                        
                        if(pirsd == null){
                            selectedpaymentoutreturnpurchaselist.remove(selectedpirsd.
                                    getReturnpurchase().getNumber());

                            paymentoutreturnpurchaselist.put(selectedpirsd.getReturnpurchase().getNumber(),selectedpirsd);
                        }
                        else{
                            selectedpaymentoutreturnpurchaselist.remove(selectedpirsd.
                                    getReturnpurchase().getNumber());
                            
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
        
        paymentoutinvoicepurchaselist.forEach((number, paymentoutinvoicepurchasedraft) -> {
            ItemPaymentPanel ipp = new ItemPaymentPanel(paymentoutinvoicepurchasedraft);
            
            leftpaymentpanel.add(ipp);
        });
        
        selectedpaymentoutinvoicepurchaselist.forEach((number, paymentoutinvoicepurchasedraft) -> {
            ItemPaymentPanel ipp = new ItemPaymentPanel(paymentoutinvoicepurchasedraft);
            ipp.setTextFieldEditable(false);
            rightpaymentpanel.add(ipp);
        });
        
        paymentoutreturnpurchaselist.forEach((number, paymentoutreturnpurchasedraft) -> {
            ItemPaymentPanel ipp = new ItemPaymentPanel(paymentoutreturnpurchasedraft);
            
            leftpaymentpanel.add(ipp);
        });
        
        selectedpaymentoutreturnpurchaselist.forEach((number, paymentoutreturnpurchasedraft) -> {
            ItemPaymentPanel ipp = new ItemPaymentPanel(paymentoutreturnpurchasedraft);
            ipp.setTextFieldEditable(false);
            rightpaymentpanel.add(ipp);
        });
        
        paymentoutexpenseslist.forEach((number, paymentoutexpensesdraft) -> {
            ItemPaymentPanel ipp = new ItemPaymentPanel(paymentoutexpensesdraft);
            
            leftpaymentpanel.add(ipp);
        });
        
        selectedpaymentoutexpenseslist.forEach((number, paymentoutexpensesdraft) -> {
            ItemPaymentPanel ipp = new ItemPaymentPanel(paymentoutexpensesdraft);
            ipp.setTextFieldEditable(false);
            rightpaymentpanel.add(ipp);
        });
        
        refresh();
    }
    
    public void load(String partnerid, String currencyid){
        clear();
        
        this.partnerid = partnerid;
        this.currencyid = currencyid;
        
        loadselectedpaymentinvoice();
        loadselectedpaymentreturn();
        loadselectedpaymentexpenses();
        loadpaymentinvoice();
        loadpaymentreturn();
        loadpaymentexpenses();
    }
    
    public void clear(){
        paymentoutinvoicepurchaselist.clear();
        paymentoutreturnpurchaselist.clear();
        paymentoutexpenseslist.clear();
        selectedpaymentoutinvoicepurchaselist.clear();
        selectedpaymentoutreturnpurchaselist.clear();
        selectedpaymentoutexpenseslist.clear();
        
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
                
                response = RestClient.get("getSelectedPaymentOutInvoicePurchaseDraftList?"
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
                        ArrayList<PaymentOutInvoicePurchaseDraftEntity> list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        PaymentOutInvoicePurchaseDraftEntity.class));
                        
                        for(PaymentOutInvoicePurchaseDraftEntity paymentoutinvoicepurchasedraft : list){
                            selectedpaymentoutinvoicepurchaselist.put(paymentoutinvoicepurchasedraft.
                                    getInvoicepurchase().getNumber(), paymentoutinvoicepurchasedraft);
                            ItemPaymentPanel ipp = new ItemPaymentPanel(paymentoutinvoicepurchasedraft);
                            ipp.setTextFieldEditable(false);
                            paidpanel.setPaid(paidpanel.getPaid() + ipp.getTextFieldValue());
                            rightpaymentpanel.add(ipp);
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
                response = RestClient.get("getSelectedPaymentOutReturnPurchaseDraftList?"
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
                        ArrayList<PaymentOutReturnPurchaseDraftEntity> list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        PaymentOutReturnPurchaseDraftEntity.class));
                        
                        for(PaymentOutReturnPurchaseDraftEntity paymentoutreturnpurchasedraft : list){
                            selectedpaymentoutreturnpurchaselist.put(paymentoutreturnpurchasedraft.
                                    getReturnpurchase().getNumber(), paymentoutreturnpurchasedraft);
                            ItemPaymentPanel ipp = new ItemPaymentPanel(paymentoutreturnpurchasedraft);
                            ipp.setTextFieldEditable(false);
                            paidpanel.setPaid(paidpanel.getPaid() - ipp.getTextFieldValue());
                            rightpaymentpanel.add(ipp);
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
    
    public final void loadselectedpaymentexpenses(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                response = RestClient.get("getSelectedPaymentOutExpensesDraftList?"
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
                        ArrayList<PaymentOutExpensesDraftEntity> list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        PaymentOutExpensesDraftEntity.class));
                        
                        for(PaymentOutExpensesDraftEntity paymentoutexpensesdraft : list){
                            selectedpaymentoutexpenseslist.put(paymentoutexpensesdraft.
                                    getExpenses().getNumber(), paymentoutexpensesdraft);
                            ItemPaymentPanel ipp = new ItemPaymentPanel(paymentoutexpensesdraft);
                            ipp.setTextFieldEditable(false);
                            paidpanel.setPaid(paidpanel.getPaid() + ipp.getTextFieldValue());
                            rightpaymentpanel.add(ipp);
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
                
                response = RestClient.get("getPaymentOutInvoicePurchaseDraftList?"
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
                        ArrayList<PaymentOutInvoicePurchaseDraftEntity> list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        PaymentOutInvoicePurchaseDraftEntity.class));
                    
                        for(PaymentOutInvoicePurchaseDraftEntity paymentoutinvoicepurchasedraft : list){
                            paymentoutinvoicepurchaselist.put(paymentoutinvoicepurchasedraft.
                                    getInvoicepurchase().getNumber(), paymentoutinvoicepurchasedraft);
                            
                            ItemPaymentPanel ipp = new ItemPaymentPanel(paymentoutinvoicepurchasedraft);

                            leftpaymentpanel.add(ipp);
                        }
                        refresh();
                    }
                    catch(Exception e){
                        e.printStackTrace();
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
                
                response = RestClient.get("getPaymentOutReturnPurchaseDraftList?"
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
                        ArrayList<PaymentOutReturnPurchaseDraftEntity> list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        PaymentOutReturnPurchaseDraftEntity.class));
                        
                        for(PaymentOutReturnPurchaseDraftEntity paymentoutreturnpurchasedraft : list){
                            paymentoutreturnpurchaselist.put(paymentoutreturnpurchasedraft.
                                    getReturnpurchase().getNumber(), paymentoutreturnpurchasedraft);
                            ItemPaymentPanel ipp = new ItemPaymentPanel(paymentoutreturnpurchasedraft);
                            leftpaymentpanel.add(ipp);
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
    
    public final void loadpaymentexpenses(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                response = RestClient.get("getPaymentOutExpensesDraftList?"
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
                        ArrayList<PaymentOutExpensesDraftEntity> list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        PaymentOutExpensesDraftEntity.class));
                        
                        for(PaymentOutExpensesDraftEntity paymentoutexpensesdraft : list){
                            paymentoutexpenseslist.put(paymentoutexpensesdraft.
                                    getExpenses().getNumber(), paymentoutexpensesdraft);
                            ItemPaymentPanel ipp = new ItemPaymentPanel(paymentoutexpensesdraft);
                            leftpaymentpanel.add(ipp);
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
    
    public final void saveselectedpaymentinvoice(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                List<PaymentOutInvoicePurchaseDraftEntity> ls = new ArrayList(
                        selectedpaymentoutinvoicepurchaselist.values());
                
                response = RestClient.post("savePaymentOutInvoicePurchaseDraftList?paymentid="+id, ls) ;
                
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
    
    public final void saveselectedpaymentreturn(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                List<PaymentOutReturnPurchaseDraftEntity> ls = new ArrayList(
                        selectedpaymentoutreturnpurchaselist.values());
                
                response = RestClient.post("savePaymentOutReturnPurchaseDraftList?paymentid="+id, ls) ;
                
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
    
    public final void saveselectedpaymentexpenses(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground() {
                
                List<PaymentOutExpensesDraftEntity> ls = new ArrayList(
                        selectedpaymentoutexpenseslist.values());
                
                response = RestClient.post("savePaymentOutExpensesDraftList?paymentid="+id, ls) ;
                
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
