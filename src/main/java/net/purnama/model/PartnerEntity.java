/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Calendar;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PartnerEntity implements Serializable{
    
    private String id;
    private String code;
    private String name;
    private String contactname;
    private String address;
    
    private int paymentdue;
    private double maximumdiscount;
    private double maximumbalance;
    private double balance;
    
    private PartnerTypeEntity partnertype;

    private String phonenumber;
    private String phonenumber2;
    private String faxnumber;
    private String mobilenumber;
    private String email;
    
    private String note;
    
    private boolean status;
    private Calendar lastmodified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContactname() {
        return contactname;
    }

    public void setContactname(String contactname) {
        this.contactname = contactname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getPaymentdue() {
        return paymentdue;
    }

    public void setPaymentdue(int paymentdue) {
        this.paymentdue = paymentdue;
    }

    public double getMaximumdiscount() {
        return maximumdiscount;
    }

    public void setMaximumdiscount(double maximumdiscount) {
        this.maximumdiscount = maximumdiscount;
    }

    public double getMaximumbalance() {
        return maximumbalance;
    }

    public void setMaximumbalance(double maximumbalance) {
        this.maximumbalance = maximumbalance;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public PartnerTypeEntity getPartnertype() {
        return partnertype;
    }

    public void setPartnertype(PartnerTypeEntity partnertype) {
        this.partnertype = partnertype;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public void setPhonenumber(String phonenumber) {
        this.phonenumber = phonenumber;
    }

    public String getPhonenumber2() {
        return phonenumber2;
    }

    public void setPhonenumber2(String phonenumber2) {
        this.phonenumber2 = phonenumber2;
    }

    public String getFaxnumber() {
        return faxnumber;
    }

    public void setFaxnumber(String faxnumber) {
        this.faxnumber = faxnumber;
    }

    public String getMobilenumber() {
        return mobilenumber;
    }

    public void setMobilenumber(String mobilenumber) {
        this.mobilenumber = mobilenumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    @Override
    public String toString(){
        return getName();
    }
    
    //-------------------------------------
    
    @JsonIgnore
    public String getFormattedMaximumdiscount(){
        if(getMaximumdiscount() >= 0){
            return GlobalFields.NUMBERFORMAT.format(getMaximumdiscount());
        }
        else{
            return GlobalFields.PROPERTIES.getProperty("LABEL_UNLIMITED");
        }
    }
    
    @JsonIgnore
    public String getFormattedMaximumbalance(){
        if(getMaximumbalance() >= 0){
            return GlobalFields.NUMBERFORMAT.format(getMaximumbalance());
        }
        else{
            return GlobalFields.PROPERTIES.getProperty("LABEL_UNLIMITED");
        }
    }
    
    @JsonIgnore
    public String getFormattedLastmodified(){
        return GlobalFields.PROPERTIES.getProperty("LABEL_LASTMODIFIED") 
                + " " + getLastmodified().getTime();
    }
    
    @JsonIgnore
    public String getFormattedBalance2(){
        return GlobalFields.NUMBERFORMAT.format(getBalance());
    }
    
    @JsonIgnore
    public String getFormattedBalance(){
        
        if(partnertype.getParent() == PartnerTypeEntity.CUSTOMER){
            if(getBalance() < 0){
                
                return "<HTML><FONT COLOR=RED>" + 
                        GlobalFields.NUMBERFORMAT.format(Math.abs(getBalance())) + 
                        "</FONT></HTML>";
            }
            else{
                return GlobalFields.NUMBERFORMAT.format(getBalance());
            }
        }
        else{
            if(getBalance() < 0){
                return GlobalFields.NUMBERFORMAT.format(Math.abs(getBalance()));
            }
            else{
                return "<HTML><FONT COLOR=RED>" + 
                        GlobalFields.NUMBERFORMAT.format(getBalance()) + 
                        "</FONT></HTML>";
            }
        }
    }
}
