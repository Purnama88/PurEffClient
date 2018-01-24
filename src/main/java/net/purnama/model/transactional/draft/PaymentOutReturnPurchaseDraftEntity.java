/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.model.transactional.draft;

import net.purnama.model.transactional.ReturnPurchaseEntity;

/**
 *
 * @author Purnama
 */
public class PaymentOutReturnPurchaseDraftEntity extends PaymentOutInvoiceDraftEntity{
   
    private ReturnPurchaseEntity returnpurchase;

    public ReturnPurchaseEntity getReturnpurchase() {
        return returnpurchase;
    }

    public void setReturnpurchase(ReturnPurchaseEntity returnpurchase) {
        this.returnpurchase = returnpurchase;
    }
    
}
