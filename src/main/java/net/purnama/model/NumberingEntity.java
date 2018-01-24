/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.Calendar;

/**
 *
 * @author Purnama
 */
public class NumberingEntity implements Serializable{
    
    private String id;
    private String prefix;
    private int start;
    private int end;
    private int current;

    private NumberingNameEntity numberingname;
    
    private MenuEntity menu;
    
    private WarehouseEntity warehouse;
    
    private String note;
    
    private boolean status;
    
    private Calendar lastmodified;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public NumberingNameEntity getNumberingname() {
        return numberingname;
    }

    public void setNumberingname(NumberingNameEntity numberingname) {
        this.numberingname = numberingname;
    }

    public MenuEntity getMenu() {
        return menu;
    }

    public void setMenu(MenuEntity menu) {
        this.menu = menu;
    }

    public WarehouseEntity getWarehouse() {
        return warehouse;
    }

    public void setWarehouse(WarehouseEntity warehouse) {
        this.warehouse = warehouse;
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
        String x = "";
        for(int i = 0; i < getLength(); i++){
            x += "X";
        }
        
        return getPrefix() +  x; 
    }
    
    //--------------------------------------------
    
    @JsonIgnore
    public String getFormat(){
        return "%0" + getLength() + "d";
    }
    
    @JsonIgnore
    public int getLength(){
        try{
            return String.valueOf(getEnd()).length();
        }
        catch(Exception e){
            return 1;
        }
    }
    
    @JsonIgnore
    public String getFormattedStart(){
        return String.format(getFormat(), getStart());
    }
    
    @JsonIgnore
    public String getFormattedEnd(){
        return String.format(getFormat(), getEnd());
    }
    
    @JsonIgnore
    public String getFormattedCurrent(){
        return String.format(getFormat(), getCurrent());
    }
    
    @JsonIgnore
    public String getCurrentId(){
        return getPrefix() + getFormattedCurrent();
    }
}
