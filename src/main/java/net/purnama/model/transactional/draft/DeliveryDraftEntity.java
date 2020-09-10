/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.model.transactional.draft;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Calendar;
import net.purnama.model.NumberingEntity;
import net.purnama.model.UserEntity;
import net.purnama.model.WarehouseEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class DeliveryDraftEntity implements Serializable{
    
    private String id;
    
    private NumberingEntity numbering;
    
    private Calendar date;
    
    private WarehouseEntity warehouse;
    
    private String destination;
    
    private String vehicletype;
    
    private String vehiclenumber;
    
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

    public NumberingEntity getNumbering() {
        return numbering;
    }

    public void setNumbering(NumberingEntity numbering) {
        this.numbering = numbering;
    }

    public Calendar getDate() {
        return date;
    }

    @JsonIgnore
    public String getFormattedDate(){
        return GlobalFields.DATEFORMAT.format(getDate().getTime());
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

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getVehicletype() {
        return vehicletype;
    }

    public void setVehicletype(String vehicletype) {
        this.vehicletype = vehicletype;
    }

    public String getVehiclenumber() {
        return vehiclenumber;
    }

    public void setVehiclenumber(String vehiclenumber) {
        this.vehiclenumber = vehiclenumber;
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
}
