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
public class PartnerTypeEntity implements Serializable{
    
    public static int CUSTOMER  = 0;
    public static int VENDOR    = 1;
    public static int NONTRADE  = 2;
    
    private String id;
    private String name;
    private int parent;

    private String note;
    private boolean status;
    
    private Calendar lastmodified;
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public static final String [] PARENT_TYPE = {
        GlobalFields.PROPERTIES.getProperty("PARTNERTYPE_CUSTOMER"),
        GlobalFields.PROPERTIES.getProperty("PARTNERTYPE_VENDOR"),
        GlobalFields.PROPERTIES.getProperty("PARTNERTYPE_NONTRADE")
    };
    
//    public static String getParentbyIndex(int i){
//        return PARENT_TYPE[i];
//    }
//    
//    public static int getParentIndex(String value){
//        for(int i = 0; i < PARENT_TYPE.length; i++){
//            if(value.equals(PARENT_TYPE[i])){
//                break;
//            }
//            return i;
//        }
//        return 0;
//    }
    
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
