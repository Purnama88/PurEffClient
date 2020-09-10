/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.controller;

import net.purnama.model.transactional.draft.ExpensesDraftEntity;
import net.purnama.model.transactional.draft.ItemExpensesDraftEntity;

/**
 *
 * @author Purnama
 */
public class ItemExpensesDraftController {
    
    public ItemExpensesDraftEntity createEmptyItemExpensesDraft(ExpensesDraftEntity expensesdraft){
        ItemExpensesDraftEntity newiis = new ItemExpensesDraftEntity();
        newiis.setQuantity(1);
        newiis.setDescription("");
        newiis.setPrice(0);
        newiis.setDiscount(0);
        newiis.setExpensesdraft(expensesdraft);
        
        return newiis;
    }
    
}
