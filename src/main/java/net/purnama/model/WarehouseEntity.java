/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Set;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class WarehouseEntity implements Serializable{
    
    private String id;
    private String name;
    private String code;
    private String address;
    private boolean defaultwarehouse;
    
    private String url;
    private String port;
    
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public boolean isDefaultwarehouse() {
        return defaultwarehouse;
    }

    public void setDefaultwarehouse(boolean defaultwarehouse) {
        this.defaultwarehouse = defaultwarehouse;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
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
        return getCode();
    }
    
    @JsonIgnore
    public String getFormattedLastmodified(){
        return GlobalFields.PROPERTIES.getProperty("LABEL_LASTMODIFIED") 
                + " " + getLastmodified().getTime();
    }
}
