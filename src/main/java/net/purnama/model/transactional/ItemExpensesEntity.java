/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.model.transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ItemExpensesEntity implements Serializable{
    
    private String id;
    
    private double quantity;
    
    private double price;
    
    private double discount;
    
    private String description;
    
    private ExpensesEntity expenses;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ExpensesEntity getExpenses() {
        return expenses;
    }

    public void setExpenses(ExpensesEntity expenses) {
        this.expenses = expenses;
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
    public String getFormattedDiscount_percentage(){
        return GlobalFields.NUMBERFORMAT.format(getDiscount_percentage());
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
    private String formattedquantity;
    
    @JsonIgnore
    private String formattedtotal;
    
    @JsonIgnore
    public String getFormattedquantity(){
        return GlobalFields.NUMBERFORMAT.format(getQuantity());
    }
    
    @JsonIgnore
    public void setFormattedquantity(String formattedquantity){
        this.formattedquantity =  formattedquantity;
    }
    
    @JsonIgnore
    public String getFormattedtotal(){
        return GlobalFields.NUMBERFORMAT.format(getTotal());
    }
    
    @JsonIgnore
    public void setFormattedtotal(String formattedtotal){
        this.formattedtotal = formattedtotal;
    }
}
