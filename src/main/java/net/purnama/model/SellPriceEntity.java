/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.model;

import java.io.Serializable;
import java.util.Calendar;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class SellPriceEntity implements Serializable{
    
    private String id;
    
    private double value;
    
    private ItemEntity item;
    
    private UomEntity uom;
    
    private String note;
    
    private boolean status;
    
    private Calendar lastmodified;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getValue() {
        return value;
    }

    public String getFormattedValue(){
        return GlobalFields.NUMBERFORMAT.format(getValue());
    }
    
    public void setValue(double value) {
        this.value = value;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public UomEntity getUom() {
        return uom;
    }

    public void setUom(UomEntity uom) {
        this.uom = uom;
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
}
