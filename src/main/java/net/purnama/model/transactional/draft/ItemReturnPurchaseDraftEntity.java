/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.model.transactional.draft;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ItemReturnPurchaseDraftEntity extends ItemInvoiceDraftEntity {
   
    private double price;
    
    private double discount;
    
    private String invoice_ref;
    
    private ReturnPurchaseDraftEntity returnpurchasedraft;
    
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public String getInvoice_ref() {
        return invoice_ref;
    }

    public void setInvoice_ref(String invoice_ref) {
        this.invoice_ref = invoice_ref;
    }

    public ReturnPurchaseDraftEntity getReturnpurchasedraft() {
        return returnpurchasedraft;
    }

    public void setReturnpurchasedraft(ReturnPurchaseDraftEntity returnpurchasedraft) {
        this.returnpurchasedraft = returnpurchasedraft;
    }

    @JsonIgnore
    public double getSubtotal() {
        return getQuantity() * getPrice();
    }
    
    @JsonIgnore
    public double getDiscount_percentage() {
        return getDiscount() / getSubtotal() * 100;
    }
    
    @JsonIgnore
    public double getTotal() {
        return getSubtotal() - getDiscount();
    }
    
    @JsonIgnore
    public String getFormattedPrice(){
        return GlobalFields.NUMBERFORMAT.format(getPrice());
    }
    
    @JsonIgnore
    public String getFormattedDiscount(){
        return GlobalFields.NUMBERFORMAT.format(getDiscount());
    }
    
    @JsonIgnore
    public String getFormattedDiscount_percentage(){
        return "(" + GlobalFields.NUMBERFORMAT.format(getDiscount_percentage()) + "%)";
    }
    
    @JsonIgnore
    public String getFormattedTotal(){
        return GlobalFields.NUMBERFORMAT.format(getTotal());
    }
}
