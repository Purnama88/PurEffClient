/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.model.transactional.draft;

import java.io.Serializable;

/**
 *
 * @author Purnama
 */
public class ItemDeliveryDraftEntity implements Serializable {
    
    private String id;
    
    private String description;
    
    private String quantity;
    
    private String remark;
    
    private DeliveryDraftEntity deliverydraft;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public DeliveryDraftEntity getDeliverydraft() {
        return deliverydraft;
    }

    public void setDeliverydraft(DeliveryDraftEntity deliverydraft) {
        this.deliverydraft = deliverydraft;
    }
    
}
