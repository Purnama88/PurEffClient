/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.model.transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import net.purnama.model.ItemEntity;
import net.purnama.model.UomEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ItemInvoiceWarehouseInEntity implements Serializable {
    
    private String id;
    
    private double quantity;
    
    private ItemEntity item;
    
    private UomEntity uom;
    
    private InvoiceWarehouseInEntity invoicewarehousein;
    
    private String item_code;
    
    private String item_name;
    
    private String uom_name;
    
    private String baseuom_name;
    
    private double basequantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
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

    public InvoiceWarehouseInEntity getInvoicewarehousein() {
        return invoicewarehousein;
    }

    public void setInvoicewarehousein(InvoiceWarehouseInEntity invoicewarehousein) {
        this.invoicewarehousein = invoicewarehousein;
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

    public String getUom_name() {
        return uom_name;
    }

    public void setUom_name(String uom_name) {
        this.uom_name = uom_name;
    }

    public String getBaseuom_name() {
        return baseuom_name;
    }

    public void setBaseuom_name(String baseuom_name) {
        this.baseuom_name = baseuom_name;
    }

    public double getBasequantity() {
        return basequantity;
    }

    public void setBasequantity(double basequantity) {
        this.basequantity = basequantity;
    }
    
    @JsonIgnore
    private String formattedquantity;
    
    @JsonIgnore
    public String getFormattedquantity(){
        return GlobalFields.NUMBERFORMAT.format(getQuantity());
    }

    @JsonIgnore
    public void setFormattedquantity(String formattedquantity) {
        this.formattedquantity = formattedquantity;
    }
}
