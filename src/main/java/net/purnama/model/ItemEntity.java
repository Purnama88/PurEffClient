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
public class ItemEntity implements Serializable{
    
    private String id;
    
    private String code;
    
    private String name;
    
    private double cost;
    
    private ItemGroupEntity itemgroup;

    private UomEntity selluom;
    
    private UomEntity buyuom;
    
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

    public double getCost() {
        return cost;
    }
    
    public void setCost(double cost) {
        this.cost = cost;
    }

    public ItemGroupEntity getItemgroup() {
        return itemgroup;
    }

    public void setItemgroup(ItemGroupEntity itemgroup) {
        this.itemgroup = itemgroup;
    }

    public UomEntity getSelluom() {
        return selluom;
    }

    public void setSelluom(UomEntity selluom) {
        this.selluom = selluom;
    }

    public UomEntity getBuyuom() {
        return buyuom;
    }

    public void setBuyuom(UomEntity buyuom) {
        this.buyuom = buyuom;
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
        return getCode() +  " - "+ getName();
    }
    
    
    //---------------------------------------
    
    @JsonIgnore
    public String getFormattedCost(){
        return GlobalFields.NUMBERFORMAT.format(getCost());
    }
    
    @JsonIgnore
    public String getFormattedLastmodified(){
        return GlobalFields.PROPERTIES.getProperty("LABEL_LASTMODIFIED") 
                + " " + getLastmodified().getTime();
    }

}
