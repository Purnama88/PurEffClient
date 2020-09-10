/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.model.transactional.draft;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PaymentOutInvoiceDraftEntity implements Serializable{
    
    private String id;
    
    private double amount;
    
    private PaymentOutDraftEntity paymentoutdraft;
    
    public boolean selected;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public PaymentOutDraftEntity getPaymentoutdraft() {
        return paymentoutdraft;
    }

    public void setPaymentoutdraft(PaymentOutDraftEntity paymentoutdraft) {
        this.paymentoutdraft = paymentoutdraft;
    }
    
    @JsonIgnore
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
    
    @JsonIgnore
    public String getFormattedAmount(){
        return GlobalFields.NUMBERFORMAT.format(getAmount());
    }
}
