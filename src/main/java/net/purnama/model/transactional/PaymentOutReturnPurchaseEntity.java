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
public class PaymentOutReturnPurchaseEntity extends PaymentOutInvoiceEntity{
    
    private ReturnPurchaseEntity returnpurchase;
    
    public ReturnPurchaseEntity getReturnpurchase() {
        return returnpurchase;
    }

    public void setReturnpurchase(ReturnPurchaseEntity returnpurchase) {
        this.returnpurchase = returnpurchase;
    }
}
