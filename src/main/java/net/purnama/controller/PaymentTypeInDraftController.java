/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.controller;

import java.util.Calendar;
import net.purnama.model.transactional.draft.PaymentInDraftEntity;
import net.purnama.model.transactional.draft.PaymentTypeInDraftEntity;

/**
 *
 * @author Purnama
 */
public class PaymentTypeInDraftController {
    
    public PaymentTypeInDraftEntity addPaymentTypeInDraft(int type, 
            Calendar duedate,
            String bank, String number, String expiry, double amount, 
            String paymentindraftid){
        PaymentTypeInDraftEntity ptide = new PaymentTypeInDraftEntity();
        ptide.setType(type);
        ptide.setDate(Calendar.getInstance());
        ptide.setDuedate(duedate);
        ptide.setBank(bank);
        ptide.setNumber(number);
        ptide.setExpirydate(expiry);
        ptide.setAmount(amount);
        
        PaymentInDraftEntity paymentindraft = new PaymentInDraftEntity();
        paymentindraft.setId(paymentindraftid);
        
        ptide.setPaymentindraft(paymentindraft);
        ptide.setStatus(false);
        ptide.setValid(true);
        ptide.setNote("");
        
        return ptide;
    }
}
