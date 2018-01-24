/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.controller;

import java.util.Calendar;
import net.purnama.model.transactional.draft.PaymentOutDraftEntity;
import net.purnama.model.transactional.draft.PaymentTypeOutDraftEntity;

/**
 *
 * @author Purnama
 */
public class PaymentTypeOutDraftController {
    
    public PaymentTypeOutDraftEntity addPaymentTypeOutDraft(int type, 
            Calendar duedate,
            String bank, String number, String expiry, double amount, 
            String paymentoutdraftid){
        PaymentTypeOutDraftEntity ptide = new PaymentTypeOutDraftEntity();
        ptide.setType(type);
        ptide.setDate(Calendar.getInstance());
        ptide.setDuedate(duedate);
        ptide.setBank(bank);
        ptide.setNumber(number);
        ptide.setExpirydate(expiry);
        ptide.setAmount(amount);
        
        PaymentOutDraftEntity paymentoutdraft = new PaymentOutDraftEntity();
        paymentoutdraft.setId(paymentoutdraftid);
        
        ptide.setPaymentoutdraft(paymentoutdraft);
        ptide.setStatus(false);
        ptide.setValid(true);
        ptide.setNote("");
        
        return ptide;
    }
    
}
