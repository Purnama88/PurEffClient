/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.form;

import com.sun.jersey.api.client.ClientResponse;
import java.awt.event.ItemEvent;
import javax.swing.JCheckBox;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.purnama.gui.inner.form.util.CheckBoxPanel;
import net.purnama.gui.inner.form.util.LabelPanel;
import net.purnama.gui.inner.form.util.LabelTextAreaPanel;
import net.purnama.gui.inner.form.util.LabelTextFieldErrorPanel;
import net.purnama.gui.inner.form.util.StatusPanel;
import net.purnama.gui.inner.home.RoleHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.RoleEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;
import net.purnama.util.Label_Text;

/**
 *
 * @author Purnama
 */
public class RoleAdd extends FormPanel{

    protected final LabelTextFieldErrorPanel namepanel;
    
    protected final StatusPanel statuspanel;
    
    protected final LabelTextAreaPanel notepanel;
    
    protected final CheckBoxPanel 
            masterpanel,
            userpanel, rolepanel, menupanel, numberingpanel,
            currencypanel, ratepanel, warehousepanel, numberingnamepanel, partnertypepanel,
            partnerpanel, uompanel, itemgrouppanel, itempanel, buypricepanel, sellpricepanel,
            salespanel,
            invoicesalespanel, returnsalespanel, deliverypanel,
            purchasepanel,
            invoicepurchasepanel, returnpurchasepanel, expensespanel,
            managementpanel,
            adjustmentpanel, invoicewarehouseinpanel, invoicewarehouseoutpanel,
            paymentpanel,
            paymentinpanel, paymentoutpanel,
            paymenttypeinpanel, paymenttypeoutpanel;
    
    protected final JCheckBox 
            master,
            user_home, user_add, user_edit, user_detail,
            role_home, role_add, role_edit,
            menu_home, menu_detail,
            numbering_add, numbering_edit,
            currency_home, currency_add, currency_edit, currency_detail,
            rate_add, rate_detail,
            warehouse_home, warehouse_add, warehouse_edit,
            numberingname_home, numberingname_add, numberingname_edit,
            partnertype_home, partnertype_add, partnertype_edit,
            partner_home, partner_add, partner_edit, partner_detail,
            uom_home, uom_add, uom_edit,
            itemgroup_home, itemgroup_add, itemgroup_edit,
            item_home, item_add, item_edit, item_detail,
            buyprice_edit,
            sellprice_edit,
            sales, sales2,
            invoicesales_home, invoicesales_close, invoicesales_cancel, invoicesales_draft,
            returnsales_home, returnsales_close, returnsales_cancel, returnsales_draft,
            delivery_home, delivery_close, delivery_cancel, delivery_draft,
            purchase, purchase2,
            invoicepurchase_home, invoicepurchase_close, invoicepurchase_cancel, invoicepurchase_draft,
            returnpurchase_home, returnpurchase_close, returnpurchase_cancel, returnpurchase_draft,
            expenses_home, expenses_close, expenses_cancel, expenses_draft,
            management, management2,
            adjustment_home, adjustment_close, adjustment_cancel, adjustment_draft,
            invoicewarehousein_home, invoicewarehousein_close, invoicewarehousein_cancel, invoicewarehousein_draft,
            invoicewarehouseout_home, invoicewarehouseout_close, invoicewarehouseout_cancel, invoicewarehouseout_draft,
            payment, payment2,
            paymentin_home, paymentin_close, paymentin_cancel, paymentin_draft,
            paymentout_home, paymentout_close, paymentout_cancel, paymentout_draft,
            paymenttypein_home, paymenttypein_cancel,
            paymenttypeout_home, paymenttypeout_cancel;
    
    public RoleAdd() {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_ROLEADD"));
        
        namepanel = new LabelTextFieldErrorPanel(
                GlobalFunctions.toSuperscript(Label_Text.LABEL_REMARK_1, 
                GlobalFields.PROPERTIES.
                getProperty("LABEL_NAME")), "");
        
        notepanel = new LabelTextAreaPanel(GlobalFields.PROPERTIES.
                getProperty("LABEL_NOTE"), "");
        
        statuspanel = new StatusPanel(GlobalFields.PROPERTIES.getProperty("LABEL_STATUS"));
        
        namepanel.setTextFieldActionListener(this);
        
        namepanel.setDocumentListener(this);
        notepanel.setDocumentListener(this);
        
        detailpanel.add(namepanel);
        detailpanel.add(statuspanel);
        detailpanel.add(notepanel);
        detailpanel.add(new LabelPanel(GlobalFields.PROPERTIES.getProperty("REMARK_EMPTY")));
        
        masterpanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_MASTER"));
        master = new JCheckBox("", true);
        masterpanel.addCheckBox(master);
        
        detailpanel.add(masterpanel);
        
        userpanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_USER"));
        user_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        user_add = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_ADD"), true);
        user_edit = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_EDIT"), true);
        user_detail = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_DETAIL"), true);
        userpanel.addCheckBox(user_home);
        userpanel.addCheckBox(user_add);
        userpanel.addCheckBox(user_edit);
        userpanel.addCheckBox(user_detail);
        
        user_home.addItemListener((ItemEvent e) ->{
            if(user_home.isSelected()){
                user_add.setSelected(true);
                user_edit.setSelected(true);
                user_detail.setSelected(true);
                
                user_add.setEnabled(true);
                user_edit.setEnabled(true);
                user_detail.setEnabled(true);
            }
            else{
                user_add.setSelected(false);
                user_edit.setSelected(false);
                user_detail.setSelected(false);
                
                user_add.setEnabled(false);
                user_edit.setEnabled(false);
                user_detail.setEnabled(false);
            }
        });
        
        detailpanel.add(userpanel);
        
        rolepanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_ROLE"));
        role_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        role_add = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_ADD"), true);
        role_edit = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_EDIT"), true);
        rolepanel.addCheckBox(role_home);
        rolepanel.addCheckBox(role_add);
        rolepanel.addCheckBox(role_edit);
        
        role_home.addItemListener((ItemEvent e) ->{
            if(role_home.isSelected()){
                role_add.setSelected(true);
                role_edit.setSelected(true);
                
                role_add.setEnabled(true);
                role_edit.setEnabled(true);
            }
            else{
                role_add.setSelected(false);
                role_edit.setSelected(false);
                
                role_add.setEnabled(false);
                role_edit.setEnabled(false);
            }
        });
        
        detailpanel.add(rolepanel);
        
        menupanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_MENU"));
        menu_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        menu_detail = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_DETAIL"), true);
        menupanel.addCheckBox(menu_home);
        menupanel.addSpace();
        menupanel.addSpace();
        menupanel.addCheckBox(menu_detail);
        
        menu_home.addItemListener((ItemEvent e) ->{
            if(menu_home.isSelected()){
                menu_detail.setSelected(true);
                
                menu_detail.setEnabled(true);
            }
            else{
                menu_detail.setSelected(false);
                
                menu_detail.setEnabled(false);
            }
        });
        
        detailpanel.add(menupanel);
        
        numberingpanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_NUMBERING"));
        numbering_add = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_ADD"), true);
        numbering_edit = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_EDIT"), true);
        numberingpanel.addSpace();
        numberingpanel.addCheckBox(numbering_add);
        numberingpanel.addCheckBox(numbering_edit);
        
        menu_detail.addItemListener((ItemEvent e) ->{
            if(menu_detail.isSelected()){
                numbering_add.setSelected(true);
                numbering_edit.setSelected(true);
                
                numbering_add.setEnabled(true);
                numbering_edit.setEnabled(true);
            }
            else{
                numbering_add.setSelected(false);
                numbering_edit.setSelected(false);
                
                numbering_add.setEnabled(false);
                numbering_edit.setEnabled(false);
            }
        });
        
        detailpanel.add(numberingpanel);
        
        currencypanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_CURRENCY"));
        currency_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        currency_add = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_ADD"), true);
        currency_edit = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_EDIT"), true);
        currency_detail = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_DETAIL"), true);
        currencypanel.addCheckBox(currency_home);
        currencypanel.addCheckBox(currency_add);
        currencypanel.addCheckBox(currency_edit);
        currencypanel.addCheckBox(currency_detail);
        
        currency_home.addItemListener((ItemEvent e) ->{
            if(currency_home.isSelected()){
                currency_add.setSelected(true);
                currency_edit.setSelected(true);
                currency_detail.setSelected(true);
                
                currency_add.setEnabled(true);
                currency_edit.setEnabled(true);
                currency_detail.setEnabled(true);
                
            }
            else{
                currency_add.setSelected(false);
                currency_edit.setSelected(false);
                currency_detail.setSelected(false);
                
                currency_add.setEnabled(false);
                currency_edit.setEnabled(false);
                currency_detail.setEnabled(false);
            }
        });
        
        detailpanel.add(currencypanel);
        
        ratepanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_RATE"));
        rate_add = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_ADD"), true);
        rate_detail = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_DETAIL"), true);
        ratepanel.addSpace();
        ratepanel.addCheckBox(rate_add);
        ratepanel.addSpace();
        ratepanel.addCheckBox(rate_detail);
        
        currency_detail.addItemListener((ItemEvent e) ->{
            if(currency_detail.isSelected()){
                rate_add.setSelected(true);
                rate_detail.setSelected(true);
                
                rate_add.setEnabled(true);
                rate_detail.setEnabled(true);
            }
            else{
                rate_add.setSelected(false);
                rate_detail.setSelected(false);
                
                rate_add.setEnabled(false);
                rate_detail.setEnabled(false);
            }
        });
        
        detailpanel.add(ratepanel);
        
        warehousepanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_WAREHOUSE"));
        warehouse_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        warehouse_add = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_ADD"), true);
        warehouse_edit = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_EDIT"), true);
        warehousepanel.addCheckBox(warehouse_home);
        warehousepanel.addCheckBox(warehouse_add);
        warehousepanel.addCheckBox(warehouse_edit);
        
        warehouse_home.addItemListener((ItemEvent e) ->{
            if(warehouse_home.isSelected()){
                warehouse_add.setSelected(true);
                warehouse_edit.setSelected(true);
                
                warehouse_add.setEnabled(true);
                warehouse_edit.setEnabled(true);
            }
            else{
                warehouse_add.setSelected(false);
                warehouse_edit.setSelected(false);
                
                warehouse_add.setEnabled(false);
                warehouse_edit.setEnabled(false);
            }
        });
        
        detailpanel.add(warehousepanel);
        
        numberingnamepanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_NUMBERINGNAME"));
        numberingname_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        numberingname_add = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_ADD"), true);
        numberingname_edit = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_EDIT"), true);
        numberingnamepanel.addCheckBox(numberingname_home);
        numberingnamepanel.addCheckBox(numberingname_add);
        numberingnamepanel.addCheckBox(numberingname_edit);
        
        numberingname_home.addItemListener((ItemEvent e) ->{
            if(numberingname_home.isSelected()){
                numberingname_add.setSelected(true);
                numberingname_edit.setSelected(true);
                
                numberingname_add.setEnabled(true);
                numberingname_edit.setEnabled(true);
            }
            else{
                numberingname_add.setSelected(false);
                numberingname_edit.setSelected(false);
                
                numberingname_add.setEnabled(false);
                numberingname_edit.setEnabled(false);
            }
        });
        
        detailpanel.add(numberingnamepanel);
        
        partnertypepanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PARTNERTYPE"));
        partnertype_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        partnertype_add = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_ADD"), true);
        partnertype_edit = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_EDIT"), true);
        partnertypepanel.addCheckBox(partnertype_home);
        partnertypepanel.addCheckBox(partnertype_add);
        partnertypepanel.addCheckBox(partnertype_edit);
        
        partnertype_home.addItemListener((ItemEvent e) ->{
            if(partnertype_home.isSelected()){
                partnertype_add.setSelected(true);
                partnertype_edit.setSelected(true);
                
                partnertype_add.setEnabled(true);
                partnertype_edit.setEnabled(true);
            }
            else{
                partnertype_add.setSelected(false);
                partnertype_edit.setSelected(false);
                
                partnertype_add.setEnabled(false);
                partnertype_edit.setEnabled(false);
            }
        });
        
        detailpanel.add(partnertypepanel);
        
        partnerpanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PARTNER"));
        partner_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        partner_add = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_ADD"), true);
        partner_edit = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_EDIT"), true);
        partner_detail = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_DETAIL"), true);
        partnerpanel.addCheckBox(partner_home);
        partnerpanel.addCheckBox(partner_add);
        partnerpanel.addCheckBox(partner_edit);
        partnerpanel.addCheckBox(partner_detail);
        
        partner_home.addItemListener((ItemEvent e) ->{
            if(partner_home.isSelected()){
                partner_add.setSelected(true);
                partner_edit.setSelected(true);
                partner_detail.setSelected(true);
                
                partner_add.setEnabled(true);
                partner_edit.setEnabled(true);
                partner_detail.setEnabled(true);
            }
            else{
                partner_add.setSelected(false);
                partner_edit.setSelected(false);
                partner_detail.setSelected(false);
                
                partner_add.setEnabled(false);
                partner_edit.setEnabled(false);
                partner_detail.setEnabled(false);
            }
        });
        
        detailpanel.add(partnerpanel);
        
        uompanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_UOM"));
        uom_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        uom_add = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_ADD"), true);
        uom_edit = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_EDIT"), true);
        uompanel.addCheckBox(uom_home);
        uompanel.addCheckBox(uom_add);
        uompanel.addCheckBox(uom_edit);
        
        uom_home.addItemListener((ItemEvent e) ->{
            if(uom_home.isSelected()){
                uom_add.setSelected(true);
                uom_edit.setSelected(true);
                
                uom_add.setEnabled(true);
                uom_edit.setEnabled(true);
            }
            else{
                uom_add.setSelected(false);
                uom_edit.setSelected(false);
                
                uom_add.setEnabled(false);
                uom_edit.setEnabled(false);
            }
        });
        
        detailpanel.add(uompanel);
        
        itemgrouppanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_ITEMGROUP"));
        itemgroup_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        itemgroup_add = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_ADD"), true);
        itemgroup_edit = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_EDIT"), true);
        itemgrouppanel.addCheckBox(itemgroup_home);
        itemgrouppanel.addCheckBox(itemgroup_add);
        itemgrouppanel.addCheckBox(itemgroup_edit);
        
        itemgroup_home.addItemListener((ItemEvent e) ->{
            if(itemgroup_home.isSelected()){
                itemgroup_add.setSelected(true);
                itemgroup_edit.setSelected(true);
                
                itemgroup_add.setEnabled(true);
                itemgroup_edit.setEnabled(true);
            }
            else{
                itemgroup_add.setSelected(false);
                itemgroup_edit.setSelected(false);
                
                itemgroup_add.setEnabled(false);
                itemgroup_edit.setEnabled(false);
            }
        });
        
        detailpanel.add(itemgrouppanel);
        
        itempanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_ITEM"));
        item_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        item_add = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_ADD"), true);
        item_edit = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_EDIT"), true);
        item_detail = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_DETAIL"), true);
        itempanel.addCheckBox(item_home);
        itempanel.addCheckBox(item_add);
        itempanel.addCheckBox(item_edit);
        itempanel.addCheckBox(item_detail);
        
        item_home.addItemListener((ItemEvent e) ->{
            if(item_home.isSelected()){
                item_add.setSelected(true);
                item_edit.setSelected(true);
                item_detail.setSelected(true);
                
                item_add.setEnabled(true);
                item_edit.setEnabled(true);
                item_detail.setEnabled(true);
            }
            else{
                item_add.setSelected(false);
                item_edit.setSelected(false);
                item_detail.setSelected(false);
                
                item_add.setEnabled(false);
                item_edit.setEnabled(false);
                item_detail.setEnabled(false);
            }
        });
        
        detailpanel.add(itempanel);
        
        buypricepanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_BUYPRICE"));
        buyprice_edit = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_EDIT"), true);
        buypricepanel.addSpace();
        buypricepanel.addSpace();
        buypricepanel.addCheckBox(buyprice_edit);
        
        detailpanel.add(buypricepanel);
        
        sellpricepanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_SELLPRICE"));
        sellprice_edit = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_EDIT"), true);
        sellpricepanel.addSpace();
        sellpricepanel.addSpace();
        sellpricepanel.addCheckBox(sellprice_edit);
        
        detailpanel.add(sellpricepanel);
        
        item_detail.addItemListener((ItemEvent e) ->{
            if(item_detail.isSelected()){
                sellprice_edit.setSelected(true);
                buyprice_edit.setSelected(true);
                
                sellprice_edit.setEnabled(true);
                buyprice_edit.setEnabled(true);
            }
            else{
                sellprice_edit.setSelected(false);
                buyprice_edit.setSelected(false);
                
                sellprice_edit.setEnabled(false);
                buyprice_edit.setEnabled(false);
            }
        });
        
        master.addItemListener((ItemEvent e) ->{
            if(master.isSelected()){
                user_home.setSelected(true);
                role_home.setSelected(true);
                menu_home.setSelected(true);
                currency_home.setSelected(true);
                warehouse_home.setSelected(true);
                numberingname_home.setSelected(true);
                partnertype_home.setSelected(true);
                partner_home.setSelected(true);
                uom_home.setSelected(true);
                itemgroup_home.setSelected(true);
                item_home.setSelected(true);
                
                user_home.setEnabled(true);
                role_home.setEnabled(true);
                menu_home.setEnabled(true);
                currency_home.setEnabled(true);
                warehouse_home.setEnabled(true);
                numberingname_home.setEnabled(true);
                partnertype_home.setEnabled(true);
                partner_home.setEnabled(true);
                uom_home.setEnabled(true);
                itemgroup_home.setEnabled(true);
                item_home.setEnabled(true);
            }
            else{
                user_home.setSelected(false);
                role_home.setSelected(false);
                menu_home.setSelected(false);
                currency_home.setSelected(false);
                warehouse_home.setSelected(false);
                numberingname_home.setSelected(false);
                partnertype_home.setSelected(false);
                partner_home.setSelected(false);
                uom_home.setSelected(false);
                itemgroup_home.setSelected(false);
                item_home.setSelected(false);
                
                user_home.setEnabled(false);
                role_home.setEnabled(false);
                menu_home.setEnabled(false);
                currency_home.setEnabled(false);
                warehouse_home.setEnabled(false);
                numberingname_home.setEnabled(false);
                partnertype_home.setEnabled(false);
                partner_home.setEnabled(false);
                uom_home.setEnabled(false);
                itemgroup_home.setEnabled(false);
                item_home.setEnabled(false);
            }
        });
        
        salespanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_SALESDELIVERY"));
        sales = new JCheckBox("", true);
        sales2 = new JCheckBox("", true);
        salespanel.addCheckBox(sales);
        salespanel.addSpace();
        salespanel.addCheckBox(sales2);
        
        detailpanel.add(salespanel);
        
        invoicesalespanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICESALES"));
        invoicesales_draft = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_DRAFT"), true);
        invoicesales_close = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CLOSE"), true);
        invoicesales_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        invoicesales_cancel = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CANCEL"), true);
        
        invoicesalespanel.addCheckBox(invoicesales_draft);
        invoicesalespanel.addCheckBox(invoicesales_close);
        invoicesalespanel.addCheckBox(invoicesales_home);
        invoicesalespanel.addCheckBox(invoicesales_cancel);
        
        invoicesales_draft.addItemListener((ItemEvent e) ->{
            if(invoicesales_draft.isSelected()){
                invoicesales_close.setSelected(true);
                
                invoicesales_close.setEnabled(true);
            }
            else{
                invoicesales_close.setSelected(false);
                
                invoicesales_close.setEnabled(false);
            }
        });
        
        invoicesales_home.addItemListener((ItemEvent e) ->{
            if(invoicesales_home.isSelected()){
                invoicesales_cancel.setSelected(true);
                
                invoicesales_cancel.setEnabled(true);
            }
            else{
                invoicesales_cancel.setSelected(false);
                
                invoicesales_cancel.setEnabled(false);
            }
        });
        
        detailpanel.add(invoicesalespanel);
        
        returnsalespanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_RETURNSALES"));
        returnsales_draft = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_DRAFT"), true);
        returnsales_close = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CLOSE"), true);
        returnsales_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        returnsales_cancel = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CANCEL"), true);
        
        returnsalespanel.addCheckBox(returnsales_draft);
        returnsalespanel.addCheckBox(returnsales_close);
        returnsalespanel.addCheckBox(returnsales_home);
        returnsalespanel.addCheckBox(returnsales_cancel);
        
        returnsales_draft.addItemListener((ItemEvent e) ->{
            if(returnsales_draft.isSelected()){
                returnsales_close.setSelected(true);
                
                returnsales_close.setEnabled(true);
            }
            else{
                returnsales_close.setSelected(false);
                
                returnsales_close.setEnabled(false);
            }
        });
        
        returnsales_home.addItemListener((ItemEvent e) ->{
            if(returnsales_home.isSelected()){
                returnsales_cancel.setSelected(true);
                
                returnsales_cancel.setEnabled(true);
            }
            else{
                returnsales_cancel.setSelected(false);
                
                returnsales_cancel.setEnabled(false);
            }
        });
        
        detailpanel.add(returnsalespanel);
        
        deliverypanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_DELIVERY"));
        delivery_draft = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_DRAFT"), true);
        delivery_close = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CLOSE"), true);
        delivery_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        delivery_cancel = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CANCEL"), true);
        
        deliverypanel.addCheckBox(delivery_draft);
        deliverypanel.addCheckBox(delivery_close);
        deliverypanel.addCheckBox(delivery_home);
        deliverypanel.addCheckBox(delivery_cancel);
        
        delivery_draft.addItemListener((ItemEvent e) ->{
            if(delivery_draft.isSelected()){
                delivery_close.setSelected(true);
                
                delivery_close.setEnabled(true);
            }
            else{
                delivery_close.setSelected(false);
                
                delivery_close.setEnabled(false);
            }
        });
        
        delivery_home.addItemListener((ItemEvent e) ->{
            if(delivery_home.isSelected()){
                delivery_cancel.setSelected(true);
                
                delivery_cancel.setEnabled(true);
            }
            else{
                delivery_cancel.setSelected(false);
                
                delivery_cancel.setEnabled(false);
            }
        });
        
        detailpanel.add(deliverypanel);
        
        sales.addItemListener((ItemEvent e) ->{
            if(sales.isSelected()){
                invoicesales_draft.setSelected(true);
                returnsales_draft.setSelected(true);
                delivery_draft.setSelected(true);
                
                invoicesales_draft.setEnabled(true);
                returnsales_draft.setEnabled(true);
                delivery_draft.setEnabled(true);
            }
            else{
                invoicesales_draft.setSelected(false);
                returnsales_draft.setSelected(false);
                delivery_draft.setSelected(false);
                
                invoicesales_draft.setEnabled(false);
                returnsales_draft.setEnabled(false);
                delivery_draft.setEnabled(false);
            }
        });
        
        sales2.addItemListener((ItemEvent e) ->{
            if(sales2.isSelected()){
                invoicesales_home.setSelected(true);
                returnsales_home.setSelected(true);
                delivery_home.setSelected(true);
                
                invoicesales_home.setEnabled(true);
                returnsales_home.setEnabled(true);
                delivery_home.setEnabled(true);
            }
            else{
                invoicesales_home.setSelected(false);
                returnsales_home.setSelected(false);
                delivery_home.setSelected(false);
                
                invoicesales_home.setEnabled(false);
                returnsales_home.setEnabled(false);
                delivery_home.setEnabled(false);
            }
        });
        
        purchasepanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PURCHASEEXPENSES"));
        purchase = new JCheckBox("", true);
        purchase2 = new JCheckBox("", true);
        purchasepanel.addCheckBox(purchase);
        purchasepanel.addSpace();
        purchasepanel.addCheckBox(purchase2);
        
        detailpanel.add(purchasepanel);
        
        invoicepurchasepanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICEPURCHASE"));
        invoicepurchase_draft = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_DRAFT"), true);
        invoicepurchase_close = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CLOSE"), true);
        invoicepurchase_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        invoicepurchase_cancel = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CANCEL"), true);
        
        invoicepurchasepanel.addCheckBox(invoicepurchase_draft);
        invoicepurchasepanel.addCheckBox(invoicepurchase_close);
        invoicepurchasepanel.addCheckBox(invoicepurchase_home);
        invoicepurchasepanel.addCheckBox(invoicepurchase_cancel);
        
        invoicepurchase_draft.addItemListener((ItemEvent e) ->{
            if(invoicepurchase_draft.isSelected()){
                invoicepurchase_close.setSelected(true);
                
                invoicepurchase_close.setEnabled(true);
            }
            else{
                invoicepurchase_close.setSelected(false);
                
                invoicepurchase_close.setEnabled(false);
            }
        });
        
        invoicepurchase_home.addItemListener((ItemEvent e) ->{
            if(invoicepurchase_home.isSelected()){
                invoicepurchase_cancel.setSelected(true);
                
                invoicepurchase_cancel.setEnabled(true);
            }
            else{
                invoicepurchase_cancel.setSelected(false);
                
                invoicepurchase_cancel.setEnabled(false);
            }
        });
        
        detailpanel.add(invoicepurchasepanel);
        
        returnpurchasepanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_RETURNPURCHASE"));
        returnpurchase_draft = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_DRAFT"), true);
        returnpurchase_close = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CLOSE"), true);
        returnpurchase_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        returnpurchase_cancel = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CANCEL"), true);
        
        returnpurchasepanel.addCheckBox(returnpurchase_draft);
        returnpurchasepanel.addCheckBox(returnpurchase_close);
        returnpurchasepanel.addCheckBox(returnpurchase_home);
        returnpurchasepanel.addCheckBox(returnpurchase_cancel);
        
        returnpurchase_draft.addItemListener((ItemEvent e) ->{
            if(returnpurchase_draft.isSelected()){
                returnpurchase_close.setSelected(true);
                
                returnpurchase_close.setEnabled(true);
            }
            else{
                returnpurchase_close.setSelected(false);
                
                returnpurchase_close.setEnabled(false);
            }
        });
        
        returnpurchase_home.addItemListener((ItemEvent e) ->{
            if(returnpurchase_home.isSelected()){
                returnpurchase_cancel.setSelected(true);
                
                returnpurchase_cancel.setEnabled(true);
            }
            else{
                returnpurchase_cancel.setSelected(false);
                
                returnpurchase_cancel.setEnabled(false);
            }
        });
        
        detailpanel.add(returnpurchasepanel);
        
        expensespanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_EXPENSES"));
        expenses_draft = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_DRAFT"), true);
        expenses_close = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CLOSE"), true);
        expenses_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        expenses_cancel = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CANCEL"), true);
        
        expensespanel.addCheckBox(expenses_draft);
        expensespanel.addCheckBox(expenses_close);
        expensespanel.addCheckBox(expenses_home);
        expensespanel.addCheckBox(expenses_cancel);
        
        expenses_draft.addItemListener((ItemEvent e) ->{
            if(expenses_draft.isSelected()){
                expenses_close.setSelected(true);
                
                expenses_close.setEnabled(true);
            }
            else{
                expenses_close.setSelected(false);
                
                expenses_close.setEnabled(false);
            }
        });
        
        expenses_home.addItemListener((ItemEvent e) ->{
            if(expenses_home.isSelected()){
                expenses_cancel.setSelected(true);
                
                expenses_cancel.setEnabled(true);
            }
            else{
                expenses_cancel.setSelected(false);
                
                expenses_cancel.setEnabled(false);
            }
        });
        
        detailpanel.add(expensespanel);
        
        purchase.addItemListener((ItemEvent e) ->{
            if(purchase.isSelected()){
                invoicepurchase_draft.setSelected(true);
                returnpurchase_draft.setSelected(true);
                expenses_draft.setSelected(true);
                
                invoicepurchase_draft.setEnabled(true);
                returnpurchase_draft.setEnabled(true);
                expenses_draft.setEnabled(true);
            }
            else{
                invoicepurchase_draft.setSelected(false);
                returnpurchase_draft.setSelected(false);
                expenses_draft.setSelected(false);
                
                invoicepurchase_draft.setEnabled(false);
                returnpurchase_draft.setEnabled(false);
                expenses_draft.setEnabled(false);
            }
        });
        
        purchase2.addItemListener((ItemEvent e) ->{
            if(purchase2.isSelected()){
                invoicepurchase_home.setSelected(true);
                returnpurchase_home.setSelected(true);
                expenses_home.setSelected(true);
                
                invoicepurchase_home.setEnabled(true);
                returnpurchase_home.setEnabled(true);
                expenses_home.setEnabled(true);
            }
            else{
                invoicepurchase_home.setSelected(false);
                returnpurchase_home.setSelected(false);
                expenses_home.setSelected(false);
                
                invoicepurchase_home.setEnabled(false);
                returnpurchase_home.setEnabled(false);
                expenses_home.setEnabled(false);
            }
        });
        
        managementpanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_ITEMMANAGEMENT"));
        management = new JCheckBox("", true);
        management2 = new JCheckBox("", true);
        managementpanel.addCheckBox(management);
        managementpanel.addSpace();
        managementpanel.addCheckBox(management2);
        
        detailpanel.add(managementpanel);
        
        adjustmentpanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_ADJUSTMENT"));
        adjustment_draft = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_DRAFT"), true);
        adjustment_close = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CLOSE"), true);
        adjustment_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        adjustment_cancel = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CANCEL"), true);
        
        adjustmentpanel.addCheckBox(adjustment_draft);
        adjustmentpanel.addCheckBox(adjustment_close);
        adjustmentpanel.addCheckBox(adjustment_home);
        adjustmentpanel.addCheckBox(adjustment_cancel);
        
        adjustment_draft.addItemListener((ItemEvent e) ->{
            if(adjustment_draft.isSelected()){
                adjustment_close.setSelected(true);
                
                adjustment_close.setEnabled(true);
            }
            else{
                adjustment_close.setSelected(false);
                
                adjustment_close.setEnabled(false);
            }
        });
        
        adjustment_home.addItemListener((ItemEvent e) ->{
            if(adjustment_home.isSelected()){
                adjustment_cancel.setSelected(true);
                
                adjustment_cancel.setEnabled(true);
            }
            else{
                adjustment_cancel.setSelected(false);
                
                adjustment_cancel.setEnabled(false);
            }
        });
        
        detailpanel.add(adjustmentpanel);
        
        invoicewarehouseinpanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICEWAREHOUSEIN"));
        invoicewarehousein_draft = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_DRAFT"), true);
        invoicewarehousein_close = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CLOSE"), true);
        invoicewarehousein_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        invoicewarehousein_cancel = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CANCEL"), true);
        
        invoicewarehouseinpanel.addCheckBox(invoicewarehousein_draft);
        invoicewarehouseinpanel.addCheckBox(invoicewarehousein_close);
        invoicewarehouseinpanel.addCheckBox(invoicewarehousein_home);
        invoicewarehouseinpanel.addCheckBox(invoicewarehousein_cancel);
        
        invoicewarehousein_draft.addItemListener((ItemEvent e) ->{
            if(invoicewarehousein_draft.isSelected()){
                invoicewarehousein_close.setSelected(true);
                
                invoicewarehousein_close.setEnabled(true);
            }
            else{
                invoicewarehousein_close.setSelected(false);
                
                invoicewarehousein_close.setEnabled(false);
            }
        });
        
        invoicewarehousein_home.addItemListener((ItemEvent e) ->{
            if(invoicewarehousein_home.isSelected()){
                invoicewarehousein_cancel.setSelected(true);
                
                invoicewarehousein_cancel.setEnabled(true);
            }
            else{
                invoicewarehousein_cancel.setSelected(false);
                
                invoicewarehousein_cancel.setEnabled(false);
            }
        });
        
        detailpanel.add(invoicewarehouseinpanel);
        
        invoicewarehouseoutpanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICEWAREHOUSEOUT"));
        invoicewarehouseout_draft = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_DRAFT"), true);
        invoicewarehouseout_close = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CLOSE"), true);
        invoicewarehouseout_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        invoicewarehouseout_cancel = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CANCEL"), true);
        
        invoicewarehouseoutpanel.addCheckBox(invoicewarehouseout_draft);
        invoicewarehouseoutpanel.addCheckBox(invoicewarehouseout_close);
        invoicewarehouseoutpanel.addCheckBox(invoicewarehouseout_home);
        invoicewarehouseoutpanel.addCheckBox(invoicewarehouseout_cancel);
        
        invoicewarehouseout_draft.addItemListener((ItemEvent e) ->{
            if(invoicewarehouseout_draft.isSelected()){
                invoicewarehouseout_close.setSelected(true);
                
                invoicewarehouseout_close.setEnabled(true);
            }
            else{
                invoicewarehouseout_close.setSelected(false);
                
                invoicewarehouseout_close.setEnabled(false);
            }
        });
        
        invoicewarehouseout_home.addItemListener((ItemEvent e) ->{
            if(invoicewarehouseout_home.isSelected()){
                invoicewarehouseout_cancel.setSelected(true);
                
                invoicewarehouseout_cancel.setEnabled(true);
            }
            else{
                invoicewarehouseout_cancel.setSelected(false);
                
                invoicewarehouseout_cancel.setEnabled(false);
            }
        });
        
        detailpanel.add(invoicewarehouseoutpanel);
        
        management.addItemListener((ItemEvent e) ->{
            if(management.isSelected()){
                adjustment_draft.setSelected(true);
                invoicewarehousein_draft.setSelected(true);
                invoicewarehouseout_draft.setSelected(true);
                
                adjustment_draft.setEnabled(true);
                invoicewarehousein_draft.setEnabled(true);
                invoicewarehouseout_draft.setEnabled(true);
            }
            else{
                adjustment_draft.setSelected(false);
                invoicewarehousein_draft.setSelected(false);
                invoicewarehouseout_draft.setSelected(false);
                
                adjustment_draft.setEnabled(false);
                invoicewarehousein_draft.setEnabled(false);
                invoicewarehouseout_draft.setEnabled(false);
            }
        });
        
        management2.addItemListener((ItemEvent e) ->{
            if(management2.isSelected()){
                adjustment_home.setSelected(true);
                invoicewarehousein_home.setSelected(true);
                invoicewarehouseout_home.setSelected(true);
                
                adjustment_home.setEnabled(true);
                invoicewarehousein_home.setEnabled(true);
                invoicewarehouseout_home.setEnabled(true);
            }
            else{
                adjustment_home.setSelected(false);
                invoicewarehousein_home.setSelected(false);
                invoicewarehouseout_home.setSelected(false);
                
                adjustment_home.setEnabled(false);
                invoicewarehousein_home.setEnabled(false);
                invoicewarehouseout_home.setEnabled(false);
            }
        });
        
        paymentpanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PAYMENT"));
        payment = new JCheckBox("", true);
        payment2 = new JCheckBox("", true);
        paymentpanel.addCheckBox(payment);
        paymentpanel.addSpace();
        paymentpanel.addCheckBox(payment2);
        
        detailpanel.add(paymentpanel);
        
        paymentinpanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PAYMENTIN"));
        paymentin_draft = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_DRAFT"), true);
        paymentin_close = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CLOSE"), true);
        paymentin_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        paymentin_cancel = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CANCEL"), true);
        
        paymentinpanel.addCheckBox(paymentin_draft);
        paymentinpanel.addCheckBox(paymentin_close);
        paymentinpanel.addCheckBox(paymentin_home);
        paymentinpanel.addCheckBox(paymentin_cancel);
        
        paymentin_draft.addItemListener((ItemEvent e) ->{
            if(paymentin_draft.isSelected()){
                paymentin_close.setSelected(true);
                
                paymentin_close.setEnabled(true);
            }
            else{
                paymentin_close.setSelected(false);
                
                paymentin_close.setEnabled(false);
            }
        });
        
        paymentin_home.addItemListener((ItemEvent e) ->{
            if(paymentin_home.isSelected()){
                paymentin_cancel.setSelected(true);
                
                paymentin_cancel.setEnabled(true);
            }
            else{
                paymentin_cancel.setSelected(false);
                
                paymentin_cancel.setEnabled(false);
            }
        });
        
        detailpanel.add(paymentinpanel);
        
        paymentoutpanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PAYMENTOUT"));
        paymentout_draft = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_DRAFT"), true);
        paymentout_close = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CLOSE"), true);
        paymentout_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        paymentout_cancel = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CANCEL"), true);
        
        paymentoutpanel.addCheckBox(paymentout_draft);
        paymentoutpanel.addCheckBox(paymentout_close);
        paymentoutpanel.addCheckBox(paymentout_home);
        paymentoutpanel.addCheckBox(paymentout_cancel);
        
        paymentout_draft.addItemListener((ItemEvent e) ->{
            if(paymentout_draft.isSelected()){
                paymentout_close.setSelected(true);
                
                paymentout_close.setEnabled(true);
            }
            else{
                paymentout_close.setSelected(false);
                
                paymentout_close.setEnabled(false);
            }
        });
        
        paymentout_home.addItemListener((ItemEvent e) ->{
            if(paymentout_home.isSelected()){
                paymentout_cancel.setSelected(true);
                
                paymentout_cancel.setEnabled(true);
            }
            else{
                paymentout_cancel.setSelected(false);
                
                paymentout_cancel.setEnabled(false);
            }
        });
        
        detailpanel.add(paymentoutpanel);
        
        paymenttypeinpanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PAYMENTTYPEIN"));
        paymenttypein_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        paymenttypein_cancel = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CANCEL"), true);
        
        paymenttypeinpanel.addSpace();
        paymenttypeinpanel.addSpace();
        paymenttypeinpanel.addCheckBox(paymenttypein_home);
        paymenttypeinpanel.addCheckBox(paymenttypein_cancel);
        
        detailpanel.add(paymenttypeinpanel);
        
        paymenttypein_home.addItemListener((ItemEvent e) ->{
            if(paymenttypein_home.isSelected()){
                paymenttypein_cancel.setSelected(true);
                
                paymenttypein_cancel.setEnabled(true);
            }
            else{
                paymenttypein_cancel.setSelected(false);
                
                paymenttypein_cancel.setEnabled(false);
            }
        });
        
        paymenttypeoutpanel = new CheckBoxPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PAYMENTTYPEOUT"));
        paymenttypeout_home = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_HOME"), true);
        paymenttypeout_cancel = new JCheckBox(GlobalFields.PROPERTIES.getProperty("LABEL_CANCEL"), true);
        
        paymenttypeoutpanel.addSpace();
        paymenttypeoutpanel.addSpace();
        paymenttypeoutpanel.addCheckBox(paymenttypeout_home);
        paymenttypeoutpanel.addCheckBox(paymenttypeout_cancel);
        
        detailpanel.add(paymenttypeoutpanel);
        
        paymenttypeout_home.addItemListener((ItemEvent e) ->{
            if(paymenttypeout_home.isSelected()){
                paymenttypeout_cancel.setSelected(true);
                
                paymenttypeout_cancel.setEnabled(true);
            }
            else{
                paymenttypeout_cancel.setSelected(false);
                
                paymenttypeout_cancel.setEnabled(false);
            }
        });
        
        payment.addItemListener((ItemEvent e) ->{
            if(payment.isSelected()){
                paymentin_draft.setSelected(true);
                paymentout_draft.setSelected(true);
                
                paymentin_draft.setEnabled(true);
                paymentout_draft.setEnabled(true);
            }
            else{
                paymentin_draft.setSelected(false);
                paymentout_draft.setSelected(false);
                
                paymentin_draft.setEnabled(false);
                paymentout_draft.setEnabled(false);
            }
        });
        
        payment2.addItemListener((ItemEvent e) ->{
            if(payment2.isSelected()){
                paymentin_home.setSelected(true);
                paymentout_home.setSelected(true);
                paymenttypein_home.setSelected(true);
                paymenttypeout_home.setSelected(true);
                
                paymentin_home.setEnabled(true);
                paymentout_home.setEnabled(true);
                paymenttypein_home.setEnabled(true);
                paymenttypeout_home.setEnabled(true);
            }
            else{
                paymentin_home.setSelected(false);
                paymentout_home.setSelected(false);
                paymenttypein_home.setSelected(false);
                paymenttypeout_home.setSelected(false);
                
                paymentin_home.setEnabled(false);
                paymentout_home.setEnabled(false);
                paymenttypein_home.setEnabled(false);
                paymenttypeout_home.setEnabled(false);
            }
        });
    }

    @Override
    protected boolean validateinput() {
        boolean status = GlobalFields.SUCCESS;
        
        if(namepanel.isTextFieldEmpty()){
            status = GlobalFields.FAIL;
            namepanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_EMPTY"));
        }
        else{
            namepanel.setErrorLabel("");
        }
        
        return status;
    }

    @Override
    protected void submit() {
        if(validateinput()){
            
            SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
                ClientResponse clientresponse;
                
                @Override
                protected Boolean doInBackground(){
                    submitpanel.loading();
                    
                    RoleEntity role = new RoleEntity();
                    
                    role.setName(namepanel.getTextFieldValue());
                    role.setStatus(statuspanel.getSelectedValue());
                    role.setDefaultrole(false);
                    role.setStatus(statuspanel.getSelectedValue());
                    role.setNote(notepanel.getTextAreaValue());
                    
                    role.setUser_home(user_home.isSelected());
                    role.setUser_add(user_add.isSelected());
                    role.setUser_edit(user_edit.isSelected());
                    role.setUser_detail(user_detail.isSelected());
                    
                    role.setRole_home(role_home.isSelected());
                    role.setRole_add(role_add.isSelected());
                    role.setRole_edit(role_edit.isSelected());
                    
                    role.setMenu_home(menu_home.isSelected());
                    role.setMenu_detail(menu_detail.isSelected());
                    
                    role.setNumbering_add(numbering_add.isSelected());
                    role.setNumbering_edit(numbering_edit.isSelected());
                    
                    role.setCurrency_home(currency_home.isSelected());
                    role.setCurrency_add(currency_add.isSelected());
                    role.setCurrency_edit(currency_edit.isSelected());
                    role.setCurrency_detail(currency_detail.isSelected());
                    
                    role.setRate_add(rate_add.isSelected());
                    role.setRate_detail(rate_detail.isSelected());
                    
                    role.setWarehouse_home(warehouse_home.isSelected());
                    role.setWarehouse_add(warehouse_add.isSelected());
                    role.setWarehouse_edit(warehouse_edit.isSelected());
                    
                    role.setNumberingname_home(numberingname_home.isSelected());
                    role.setNumberingname_add(numberingname_add.isSelected());
                    role.setNumberingname_edit(numberingname_edit.isSelected());
                    
                    role.setPartnertype_home(partnertype_home.isSelected());
                    role.setPartnertype_add(partnertype_add.isSelected());
                    role.setPartnertype_edit(partnertype_edit.isSelected());
                    
                    role.setPartner_home(partner_home.isSelected());
                    role.setPartner_add(partner_add.isSelected());
                    role.setPartner_edit(partner_edit.isSelected());
                    role.setPartner_detail(partner_detail.isSelected());
                    
                    role.setUom_home(uom_home.isSelected());
                    role.setUom_add(uom_add.isSelected());
                    role.setUom_edit(uom_edit.isSelected());
                    
                    role.setItemgroup_home(itemgroup_home.isSelected());
                    role.setItemgroup_add(itemgroup_add.isSelected());
                    role.setItemgroup_edit(itemgroup_edit.isSelected());
                    
                    role.setItem_home(item_home.isSelected());
                    role.setItem_add(item_add.isSelected());
                    role.setItem_edit(item_edit.isSelected());
                    role.setItem_detail(item_detail.isSelected());
                    
                    role.setBuyprice_edit(buyprice_edit.isSelected());
                    
                    role.setSellprice_edit(sellprice_edit.isSelected());
                    
                    role.setInvoicesales_home(invoicesales_home.isSelected());
                    role.setInvoicesales_draft(invoicesales_draft.isSelected());
                    role.setInvoicesales_cancel(invoicesales_cancel.isSelected());
                    role.setInvoicesales_close(invoicesales_close.isSelected());
                    
                    role.setReturnsales_home(returnsales_home.isSelected());
                    role.setReturnsales_draft(returnsales_draft.isSelected());
                    role.setReturnsales_cancel(returnsales_cancel.isSelected());
                    role.setReturnsales_close(returnsales_close.isSelected());
                    
                    role.setDelivery_home(delivery_home.isSelected());
                    role.setDelivery_draft(delivery_draft.isSelected());
                    role.setDelivery_cancel(delivery_cancel.isSelected());
                    role.setDelivery_close(delivery_close.isSelected());
                    
                    role.setInvoicepurchase_home(invoicepurchase_home.isSelected());
                    role.setInvoicepurchase_draft(invoicepurchase_draft.isSelected());
                    role.setInvoicepurchase_cancel(invoicepurchase_cancel.isSelected());
                    role.setInvoicepurchase_close(invoicepurchase_close.isSelected());
                    
                    role.setReturnpurchase_home(returnpurchase_home.isSelected());
                    role.setReturnpurchase_draft(returnpurchase_draft.isSelected());
                    role.setReturnpurchase_cancel(returnpurchase_cancel.isSelected());
                    role.setReturnpurchase_close(returnpurchase_close.isSelected());
                    
                    role.setExpenses_home(expenses_home.isSelected());
                    role.setExpenses_draft(expenses_draft.isSelected());
                    role.setExpenses_cancel(expenses_cancel.isSelected());
                    role.setExpenses_close(expenses_close.isSelected());
                    
                    role.setAdjustment_home(adjustment_home.isSelected());
                    role.setAdjustment_draft(adjustment_draft.isSelected());
                    role.setAdjustment_cancel(adjustment_cancel.isSelected());
                    role.setAdjustment_close(adjustment_close.isSelected());
                    
                    role.setInvoicewarehouseout_home(invoicewarehouseout_home.isSelected());
                    role.setInvoicewarehouseout_draft(invoicewarehouseout_draft.isSelected());
                    role.setInvoicewarehouseout_cancel(invoicewarehouseout_cancel.isSelected());
                    role.setInvoicewarehouseout_close(invoicewarehouseout_close.isSelected());
                    
                    role.setInvoicewarehousein_home(invoicewarehousein_home.isSelected());
                    role.setInvoicewarehousein_draft(invoicewarehousein_draft.isSelected());
                    role.setInvoicewarehousein_cancel(invoicewarehousein_cancel.isSelected());
                    role.setInvoicewarehousein_close(invoicewarehousein_close.isSelected());
                    
                    role.setPaymentout_home(paymentout_home.isSelected());
                    role.setPaymentout_draft(paymentout_draft.isSelected());
                    role.setPaymentout_cancel(paymentout_cancel.isSelected());
                    role.setPaymentout_close(paymentout_close.isSelected());
                    
                    role.setPaymentin_home(paymentin_home.isSelected());
                    role.setPaymentin_draft(paymentin_draft.isSelected());
                    role.setPaymentin_cancel(paymentin_cancel.isSelected());
                    role.setPaymentin_close(paymentin_close.isSelected());
                    
                    role.setPaymenttypein_home(paymenttypein_home.isSelected());
                    role.setPaymenttypein_invalidate(paymenttypein_cancel.isSelected());
                    
                    role.setPaymenttypeout_home(paymenttypeout_home.isSelected());
                    role.setPaymenttypeout_invalidate(paymenttypeout_cancel.isSelected());
                    
                    clientresponse = RestClient.post("addRole", role);
                    
                    return true;
                }
                
                
                @Override
                protected void done() {
                    
                    submitpanel.finish();
                    
                    if(clientresponse == null){
                        upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                    }
                    else if(clientresponse.getStatus() != 200) {
                        String output = clientresponse.getEntity(String.class);
                        
                        JOptionPane.showMessageDialog(GlobalFields.MAINFRAME, 
                        output, GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                        + clientresponse.getStatus(), 
                        JOptionPane.ERROR_MESSAGE);
                    }
                    else{
                        home();
                    }
                }
            };
            
            worker.execute();
        }        
    }

    @Override
    protected void home() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new RoleHome());
    }
    
}
