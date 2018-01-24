/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.model.transactional.draft;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Calendar;
import net.purnama.model.UserEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PaymentTypeInDraftEntity implements Serializable{
    
    private String id;
    
    private int type;
    
    private Calendar date;
    
    private Calendar duedate;
    
    private String bank;
    
    private String number;
    
    private String expirydate;
    
    private double amount;
    
    private boolean valid;
    
    private PaymentInDraftEntity paymentindraft;
    
    private String note;
    
    private boolean status;
    
    private Calendar lastmodified;
    
    @JsonIgnore
    private UserEntity lastmodifiedby;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public Calendar getDuedate() {
        return duedate;
    }

    public void setDuedate(Calendar duedate) {
        this.duedate = duedate;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getExpirydate() {
        return expirydate;
    }

    public void setExpirydate(String expirydate) {
        this.expirydate = expirydate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
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

    public UserEntity getLastmodifiedby() {
        return lastmodifiedby;
    }

    public void setLastmodifiedby(UserEntity lastmodifiedby) {
        this.lastmodifiedby = lastmodifiedby;
    }

    public PaymentInDraftEntity getPaymentindraft() {
        return paymentindraft;
    }

    public void setPaymentindraft(PaymentInDraftEntity paymentindraft) {
        this.paymentindraft = paymentindraft;
    }
    
    public String getFormattedDate(){
        return GlobalFields.DATEFORMAT.format(getDate().getTime());
    }
    
    public String getFormattedDueDate(){
        return GlobalFields.DATEFORMAT.format(getDuedate().getTime());
    }
    
    public String getFormattedAmount(){
        return GlobalFields.NUMBERFORMAT.format(getAmount());
    }
    
    public static final String [] PAYMENT_TYPE = {
        GlobalFields.PROPERTIES.getProperty("LABEL_CASH"),
        GlobalFields.PROPERTIES.getProperty("LABEL_TRANSFER"),
        GlobalFields.PROPERTIES.getProperty("LABEL_CREDITCARD"),
        GlobalFields.PROPERTIES.getProperty("LABEL_CHEQUE"),
    };
}
