/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.controller;

import net.purnama.model.transactional.draft.ReturnSalesDraftEntity;
import net.purnama.model.transactional.draft.ItemReturnSalesDraftEntity;

/**
 *
 * @author Purnama
 */
public class ItemReturnSalesDraftController {
    
    public ItemReturnSalesDraftEntity createEmptyItemReturnSalesDraft(ReturnSalesDraftEntity returnsalesdraft){
        ItemReturnSalesDraftEntity newiis = new ItemReturnSalesDraftEntity();
        newiis.setQuantity(1);
        newiis.setPrice(0);
        newiis.setDiscount(0);
        newiis.setReturnsalesdraft(returnsalesdraft);
        newiis.setInvoice_ref("");
        
        return newiis;
    }
    
}
