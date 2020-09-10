/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.model.transactional.draft;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Calendar;
import net.purnama.model.CurrencyEntity;
import net.purnama.model.NumberingEntity;
import net.purnama.model.PartnerEntity;
import net.purnama.model.WarehouseEntity;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;

/**
 *
 * @author Purnama
 */
public class ReturnSalesDraftEntity implements Serializable{
    
    private String id;
    
    private NumberingEntity numbering;
    
    private Calendar date;
    
    private WarehouseEntity warehouse;
    
    private Calendar duedate;
    
    private double subtotal;
    
    private double discount;
    
    private double rounding;
    
    private double freight;
    
    private double tax;
    
    private double rate;
    
    private CurrencyEntity currency;
    
    private PartnerEntity partner;
    
    private String note;
    
    private boolean status;
    
    private Calendar lastmodified;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NumberingEntity getNumbering() {
        return numbering;
    }

    public void setNumbering(NumberingEntity numbering) {
        this.numbering = numbering;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public WarehouseEntity getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseEntity warehouse) {
        this.warehouse = warehouse;
    }

    public Calendar getDuedate() {
        return duedate;
    }

    public void setDuedate(Calendar duedate) {
        this.duedate = duedate;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(double subtotal) {
        this.subtotal = subtotal;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getRounding() {
        return rounding;
    }

    public void setRounding(double rounding) {
        this.rounding = rounding;
    }

    public double getFreight() {
        return freight;
    }

    public void setFreight(double freight) {
        this.freight = freight;
    }

    public double getTax() {
        return tax;
    }

    public void setTax(double tax) {
        this.tax = tax;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

    public PartnerEntity getPartner() {
        return partner;
    }

    public void setPartner(PartnerEntity partner) {
        this.partner = partner;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Calendar getLastmodified() {
        return lastmodified;
    }

    public void setLastmodified(Calendar lastmodified) {
        this.lastmodified = lastmodified;
    }

    @JsonIgnore
    public String getFormattedDate(){
        return GlobalFields.DATEFORMAT.format(getDate().getTime());
    }
    
    @JsonIgnore
    public String getFormattedDueDate(){
        return GlobalFields.DATEFORMAT.format(getDuedate().getTime());
    }
    
    @JsonIgnore
    public double getDiscount_percentage() {
        return GlobalFunctions.round(getDiscount()/getSubtotal() * 100);
    }

    @JsonIgnore
    public double getTotal_before_tax() {
        return GlobalFunctions.round(getSubtotal() - getDiscount() - getRounding() + getFreight());
    }

    @JsonIgnore
    public double getTax_percentage() {
        return GlobalFunctions.round(getTax()/getTotal_before_tax() *100);
    }

    @JsonIgnore
    public double getTotal_after_tax() {
        return GlobalFunctions.round(getTotal_before_tax() + getTax());
    }

    @JsonIgnore
    public String getFormattedTotal_after_tax(){
        return GlobalFields.NUMBERFORMAT.format(getTotal_after_tax());
    }
    
    @JsonIgnore
    public double getTotal_defaultcurrency() {
        return GlobalFunctions.round(getTotal_after_tax() * getRate());
    }
}
