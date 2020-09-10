/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.model.transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Calendar;
import net.purnama.model.CurrencyEntity;
import net.purnama.model.PartnerEntity;
import net.purnama.model.WarehouseEntity;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;

/**
 *
 * @author Purnama
 */
public class InvoiceEntity implements Serializable{
    private String id;
    
    private String number;
    
    private Calendar date;
    
    private Calendar duedate;
    
    private int printed;
    
    private WarehouseEntity warehouse;
    
    private String draftid;
    
    private CurrencyEntity currency;
    
    private double rate;
    
    private PartnerEntity partner;
    
    private String currency_code;
    
    private String currency_name;
    
    private String partner_name;
    
    private String partner_code;
    
    private String partner_address;
    
    private String user_code;
    
    private String warehouse_code;
    
    private double subtotal;
    
    private double discount;
    
    private double rounding;
    
    private double freight;
    
    private double tax;
    
    private double paid;
    
    private String note;
    
    private boolean status;
    
    private Calendar lastmodified;
    
    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public int getPrinted() {
        return printed;
    }

    public void setPrinted(int printed) {
        this.printed = printed;
    }

    public WarehouseEntity getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseEntity warehouse) {
        this.warehouse = warehouse;
    }

    public String getDraftid() {
        return draftid;
    }

    public void setDraftid(String draftid) {
        this.draftid = draftid;
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

    public CurrencyEntity getCurrency() {
        return currency;
    }

    public void setCurrency(CurrencyEntity currency) {
        this.currency = currency;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public PartnerEntity getPartner() {
        return partner;
    }

    public void setPartner(PartnerEntity partner) {
        this.partner = partner;
    }

    public String getCurrency_code() {
        return currency_code;
    }

    public void setCurrency_code(String currency_code) {
        this.currency_code = currency_code;
    }

    public String getCurrency_name() {
        return currency_name;
    }

    public void setCurrency_name(String currency_name) {
        this.currency_name = currency_name;
    }

    public String getPartner_name() {
        return partner_name;
    }

    public void setPartner_name(String partner_name) {
        this.partner_name = partner_name;
    }

    public String getPartner_code() {
        return partner_code;
    }

    public void setPartner_code(String partner_code) {
        this.partner_code = partner_code;
    }

    public String getPartner_address() {
        return partner_address;
    }

    public void setPartner_address(String partner_address) {
        this.partner_address = partner_address;
    }

    public String getUser_code() {
        return user_code;
    }

    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }

    public String getWarehouse_code() {
        return warehouse_code;
    }

    public void setWarehouse_code(String warehouse_code) {
        this.warehouse_code = warehouse_code;
    }

    public double getPaid() {
        return paid;
    }

    public void setPaid(double paid) {
        this.paid = paid;
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
    public double getTotal_defaultcurrency() {
        return GlobalFunctions.round(getTotal_after_tax() * getRate());
    }
    
    @JsonIgnore
    public double getRemaining() {
        return GlobalFunctions.round(getTotal_after_tax() - getPaid());
    }
    
    @JsonIgnore
    public String getFormattedRemaining(){
        return GlobalFields.NUMBERFORMAT.format(getRemaining());
    }
    
    @JsonIgnore
    public String getFormattedFreight(){
        return GlobalFields.NUMBERFORMAT.format(getFreight());
    }
    
    @JsonIgnore
    public String getFormattedTax() {
        return GlobalFields.NUMBERFORMAT.format(getTax());
    }
    
    @JsonIgnore
    public String getFormattedSubtotal(){
        return GlobalFields.NUMBERFORMAT.format(getSubtotal());
    }
    
    @JsonIgnore
    public String getFormattedDiscount(){
        return GlobalFields.NUMBERFORMAT.format(getDiscount());
    }
    
    @JsonIgnore
    public String getFormattedRate(){
        return GlobalFields.NUMBERFORMAT.format(getRate());
    }
    
    @JsonIgnore
    public String getFormattedRounding(){
        return GlobalFields.NUMBERFORMAT.format(getRounding());
    }
    
    @JsonIgnore
    private String formatteddate;

    @JsonIgnore
    private String formattedstatus;
    
    @JsonIgnore
    private String formattedtotal_after_tax;
    
    @JsonIgnore
    public String getFormatteddate() {
        return GlobalFields.DATEFORMAT.format(getDate().getTime());
    }

    @JsonIgnore
    public void setFormatteddate(String formatteddate) {
        this.formatteddate = formatteddate;
    }
    
    @JsonIgnore
    public String getFormattedstatus(){
        
        if(getRemaining() == 0){
            return GlobalFields.PROPERTIES.getProperty("LABEL_CLOSED");
        }
        else if(isStatus()){
            return GlobalFields.PROPERTIES.getProperty("LABEL_OPEN");
        }
        else{
            return GlobalFields.PROPERTIES.getProperty("LABEL_CANCELED");
        }
    }
    
    @JsonIgnore
    public void setFormattedstatus(String formattedstatus){
        this.formattedstatus = formattedstatus;
    }

    @JsonIgnore
    public String getFormattedtotal_after_tax() {
        return GlobalFields.NUMBERFORMAT.format(getTotal_after_tax());
    }

    @JsonIgnore
    public void setFormattedtotal_after_tax(String formattedtotal_after_tax) {
        this.formattedtotal_after_tax = formattedtotal_after_tax;
    }
    
}
