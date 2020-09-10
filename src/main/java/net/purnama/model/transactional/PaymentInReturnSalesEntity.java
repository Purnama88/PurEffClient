/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.model.transactional;


/**
 *
 * @author Purnama
 */
public class PaymentInReturnSalesEntity extends PaymentInInvoiceEntity{
    
    private ReturnSalesEntity returnsales;
    
    public ReturnSalesEntity getReturnsales() {
        return returnsales;
    }

    public void setReturnsales(ReturnSalesEntity returnsales) {
        this.returnsales = returnsales;
    }
}
