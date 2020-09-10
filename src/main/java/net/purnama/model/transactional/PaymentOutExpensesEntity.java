/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.model.transactional;

import com.fasterxml.jackson.annotation.JsonIgnore;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PaymentOutExpensesEntity extends PaymentOutInvoiceEntity{
    
    private ExpensesEntity expenses;
    
    public ExpensesEntity getExpenses() {
        return expenses;
    }

    public void setExpenses(ExpensesEntity expenses) {
        this.expenses = expenses;
    }
    
    @JsonIgnore
    public String getFormattedAmount(){
        return GlobalFields.NUMBERFORMAT.format(getAmount());
    }
}
