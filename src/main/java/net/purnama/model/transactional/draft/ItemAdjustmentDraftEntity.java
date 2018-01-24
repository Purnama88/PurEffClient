/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.model.transactional.draft;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import net.purnama.model.ItemEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ItemAdjustmentDraftEntity implements Serializable {
    
    private String id;
    
    private double tstock;
    
    private double quantity;
    
    private String remark;
    
    private ItemEntity item;
    
    private AdjustmentDraftEntity adjustmentdraft;
    
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

    public AdjustmentDraftEntity getAdjustmentdraft() {
        return adjustmentdraft;
    }

    public void setAdjustmentdraft(AdjustmentDraftEntity adjustmentdraft) {
        this.adjustmentdraft = adjustmentdraft;
    }
    
    @JsonIgnore
    public double getDiff() {
        return getQuantity() - getTstock();
    }

    @JsonIgnore
    public String getFormattedTstock(){
        return GlobalFields.NUMBERFORMAT.format(getTstock());
    }
    
    @JsonIgnore
    public String getFormattedQuantity(){
        return GlobalFields.NUMBERFORMAT.format(getQuantity());
    }
    
    @JsonIgnore
    public String getFormattedDiff(){
        return GlobalFields.NUMBERFORMAT.format(getDiff());
    }
}
