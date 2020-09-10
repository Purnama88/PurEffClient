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

public class ItemGroupEntity implements Serializable{
    
    private String id;
    private String code;
    
    private String name;
    
    private String note;
    
    private boolean status;
    private Calendar lastmodified;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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
    
    @JsonIgnore
    public String getFormattedLastmodified(){
        return GlobalFields.PROPERTIES.getProperty("LABEL_LASTMODIFIED") 
                + " " + getLastmodified().getTime();
    }
}
    
    
