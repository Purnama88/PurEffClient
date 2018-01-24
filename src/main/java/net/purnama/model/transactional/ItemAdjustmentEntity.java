/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.model.transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import net.purnama.model.ItemEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ItemAdjustmentEntity implements Serializable{
    
    private String id;
    
    private double tstock;
    
    private double quantity;
    
    private String remark;
    
    private ItemEntity item;
    
    private AdjustmentEntity adjustment;
    
    private String item_code;
    
    private String item_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getTstock() {
        return tstock;
    }

    public void setTstock(double tstock) {
        this.tstock = tstock;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public AdjustmentEntity getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(AdjustmentEntity adjustment) {
        this.adjustment = adjustment;
    }

    public String getItem_code() {
        return item_code;
    }

    public void setItem_code(String item_code) {
        this.item_code = item_code;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    @JsonIgnore
    public double getDiff() {
        return getQuantity() - getTstock();
    }
    
    @JsonIgnore
    private String formattedtstock;
    
    @JsonIgnore
    private String formattedquantity;

    @JsonIgnore
    public String getFormattedtstock() {
        return GlobalFields.NUMBERFORMAT.format(getTstock());
    }

    @JsonIgnore
    public void setFormattedtstock(String formattedtstock) {
        this.formattedtstock = formattedtstock;
    }

    @JsonIgnore
    public String getFormattedquantity() {
        return GlobalFields.NUMBERFORMAT.format(getQuantity());
    }

    @JsonIgnore
    public void setFormattedquantity(String formattedquantity) {
        this.formattedquantity = formattedquantity;
    }
    
    @JsonIgnore
    public String getFormattedDiff(){
        return GlobalFields.NUMBERFORMAT.format(getDiff());
    }
}
