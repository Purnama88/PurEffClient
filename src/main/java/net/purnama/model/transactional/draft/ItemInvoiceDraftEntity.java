/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.model.transactional.draft;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import net.purnama.model.ItemEntity;
import net.purnama.model.UomEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ItemInvoiceDraftEntity implements Serializable{
    protected String id;
    
    protected ItemEntity item;
    
    protected UomEntity uom;
    
    protected double quantity;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public ItemEntity getItem() {
        return item;
    }

    public void setItem(ItemEntity item) {
        this.item = item;
    }

    public UomEntity getUom() {
        return uom;
    }

    public void setUom(UomEntity uom) {
        this.uom = uom;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }
    
    @JsonIgnore
    public String getFormattedQuantity(){
        return GlobalFields.NUMBERFORMAT.format(getQuantity());
    }
}
