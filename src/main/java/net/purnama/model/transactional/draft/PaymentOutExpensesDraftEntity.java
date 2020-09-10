/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.model.transactional.draft;

import java.io.Serializable;
import net.purnama.model.transactional.ExpensesEntity;

/**
 *
 * @author Purnama
 */
public class PaymentOutExpensesDraftEntity extends PaymentOutInvoiceDraftEntity{
    
    private ExpensesEntity expenses;

    public ExpensesEntity getExpenses() {
        return expenses;
    }

    public void setExpenses(ExpensesEntity expenses) {
        this.expenses = expenses;
    }
    
}
