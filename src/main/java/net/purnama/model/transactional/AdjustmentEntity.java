/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.model.transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Calendar;
import net.purnama.model.WarehouseEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class AdjustmentEntity implements Serializable{
    
    private String id;
    
    private String number;
    
    private Calendar date;
    
    private int printed;
    
    private WarehouseEntity warehouse;
    
    private String draftid;
    
    private String user_code;
    
    private String warehouse_code;
    
    private String note;
    
    private boolean status;
    
    private Calendar lastmodified;
    
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

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
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
    
    private String formatteddate;
    private String formattedstatus;
    
    @JsonIgnore
    public String getFormatteddate(){
        return GlobalFields.DATEFORMAT.format(getDate().getTime());
    }
    
    @JsonIgnore
    public String getFormattedstatus(){
        if(isStatus()){
            return GlobalFields.PROPERTIES.getProperty("LABEL_CLOSED");
        }
        else{
            return GlobalFields.PROPERTIES.getProperty("LABEL_CANCELED");
        }
    }

    public void setFormatteddate(String formatteddate) {
        this.formatteddate = formatteddate;
    }

    public void setFormattedstatus(String formattedstatus) {
        this.formattedstatus = formattedstatus;
    }
}
