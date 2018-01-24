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
public class UserEntity implements Serializable{
    
    private String id;
    private String code;
    private String name;
    private String username;
    private String password;
    
    private boolean dateforward;
    private boolean datebackward;
    private boolean raise_buyprice;
    private boolean raise_sellprice;
    private boolean lower_buyprice;
    private boolean lower_sellprice;
    
    private double discount;
    
    private String note;
    private boolean status;
    private Calendar lastmodified;
    
    private RoleEntity role;
    
    private Set<WarehouseEntity> warehouses;

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isDateforward() {
        return dateforward;
    }

    public void setDateforward(boolean dateforward) {
        this.dateforward = dateforward;
    }

    public boolean isDatebackward() {
        return datebackward;
    }

    public void setDatebackward(boolean datebackward) {
        this.datebackward = datebackward;
    }

    public boolean isRaise_buyprice() {
        return raise_buyprice;
    }

    public void setRaise_buyprice(boolean raise_buyprice) {
        this.raise_buyprice = raise_buyprice;
    }

    public boolean isRaise_sellprice() {
        return raise_sellprice;
    }

    public void setRaise_sellprice(boolean raise_sellprice) {
        this.raise_sellprice = raise_sellprice;
    }

    public boolean isLower_buyprice() {
        return lower_buyprice;
    }

    public void setLower_buyprice(boolean lower_buyprice) {
        this.lower_buyprice = lower_buyprice;
    }

    public boolean isLower_sellprice() {
        return lower_sellprice;
    }

    public void setLower_sellprice(boolean lower_sellprice) {
        this.lower_sellprice = lower_sellprice;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
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

    public RoleEntity getRole() {
        return role;
    }

    public void setRole(RoleEntity role) {
        this.role = role;
    }

    public Set<WarehouseEntity> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(Set<WarehouseEntity> warehouses) {
        this.warehouses = warehouses;
    }
    
    // ---------------------------------------------
    
    @JsonIgnore
    public String getFormattedDiscount(){
        if(getDiscount() >= 0){
            return GlobalFields.NUMBERFORMAT.format(getDiscount()) + "%";
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
    public String getFormattedWarehouses(){
//        String ret = "<html><ul>"; 
        
        String perpc = "";
        
        for(WarehouseEntity warehouse : getWarehouses()){
//            String perpc = "<li>" + warehouse.getCode();
//            ret += perpc;
            perpc += warehouse.getCode();
            perpc += ", ";
            
        }
//        
//        ret += "</html></ul>";
        
        return perpc;
    }
}
