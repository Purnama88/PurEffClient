/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import javax.swing.AbstractCellEditor;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingWorker;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import net.purnama.gui.inner.home.AdjustmentDraftHome;
import net.purnama.gui.inner.home.AdjustmentHome;
import net.purnama.gui.inner.home.CurrencyHome;
import net.purnama.gui.inner.home.DeliveryDraftHome;
import net.purnama.gui.inner.home.DeliveryHome;
import net.purnama.gui.inner.home.ExpensesDraftHome;
import net.purnama.gui.inner.home.ExpensesHome;
import net.purnama.gui.inner.home.InvoicePurchaseDraftHome;
import net.purnama.gui.inner.home.InvoicePurchaseHome;
import net.purnama.gui.inner.home.InvoiceSalesDraftHome;
import net.purnama.gui.inner.home.InvoiceSalesHome;
import net.purnama.gui.inner.home.InvoiceWarehouseInDraftHome;
import net.purnama.gui.inner.home.InvoiceWarehouseInHome;
import net.purnama.gui.inner.home.InvoiceWarehouseOutDraftHome;
import net.purnama.gui.inner.home.InvoiceWarehouseOutHome;
import net.purnama.gui.inner.home.ItemGroupHome;
import net.purnama.gui.inner.home.ItemHome;
import net.purnama.gui.inner.home.MenuHome;
import net.purnama.gui.inner.home.NumberingNameHome;
import net.purnama.gui.inner.home.PartnerHome;
import net.purnama.gui.inner.home.PartnerTypeHome;
import net.purnama.gui.inner.home.PaymentInDraftHome;
import net.purnama.gui.inner.home.PaymentInHome;
import net.purnama.gui.inner.home.PaymentOutDraftHome;
import net.purnama.gui.inner.home.PaymentOutHome;
import net.purnama.gui.inner.home.PaymentTypeInHome;
import net.purnama.gui.inner.home.PaymentTypeOutHome;
import net.purnama.gui.inner.home.ReportHome;
import net.purnama.gui.inner.home.ReturnPurchaseDraftHome;
import net.purnama.gui.inner.home.ReturnPurchaseHome;
import net.purnama.gui.inner.home.ReturnSalesDraftHome;
import net.purnama.gui.inner.home.ReturnSalesHome;
import net.purnama.gui.inner.home.RoleHome;
import net.purnama.gui.inner.home.UomHome;
import net.purnama.gui.inner.home.UserHome;
import net.purnama.gui.inner.home.WarehouseHome;
import net.purnama.gui.inner.home.WidgetHome;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class MainNavigation extends JPanel
//        JSplitPane
{
    private final JScrollPane upperscrollpane, lowerscrollpane;
    private final JTree navigationtree;
    
    protected final JPopupMenu popupmenu;
    
    protected final JMenuItem menucollapse, menuexpand;
    
    public MainNavigation(MainTabbedPane tabbedpane){
        super(new GridLayout(1, 1));
        
//        Icon icon = IconFontSwing.buildIcon(FontAwesome.SMILE_O, 10, new Color(0, 150, 0));
//        JLabel label = new JLabel(icon);
//        setOrientation(JSplitPane.VERTICAL_SPLIT);
        
        setMinimumSize(new Dimension(250, Integer.MAX_VALUE));
        
        upperscrollpane = new JScrollPane();
        lowerscrollpane = new JScrollPane();
        
//        add(label);
        add(upperscrollpane);
        
//        setTopComponent(upperscrollpane);
//        setBottomComponent(lowerscrollpane);
        
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("HelloPOS");
        
        DefaultMutableTreeNode master = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_MASTER"));
        
        DefaultMutableTreeNode sales = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_SALESDELIVERY"));
        
        DefaultMutableTreeNode purchase = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PURCHASEEXPENSES"));
        
        DefaultMutableTreeNode management = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_ITEMMANAGEMENT"));
        
        DefaultMutableTreeNode payment = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PAYMENT"));
        
        DefaultMutableTreeNode user = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_USER"),
                "/net/purnama/image/User_25.png"));
        
        DefaultMutableTreeNode role = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_ROLE"),
                "/net/purnama/image/Role_25.png"));
        
        DefaultMutableTreeNode menu = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_MENU"),
                "/net/purnama/image/Menu_25.png"));
        
        DefaultMutableTreeNode numberingname = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_NUMBERINGNAME"),
                "/net/purnama/image/Numbering_25.png"));
        
        DefaultMutableTreeNode warehouse = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_WAREHOUSE"),
                "/net/purnama/image/Warehouse_25.png"));
        
        DefaultMutableTreeNode partnertype = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PARTNERTYPE"),
                "/net/purnama/image/PartnerType_25.png"));
        
        DefaultMutableTreeNode partner = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PARTNER"),
                "/net/purnama/image/Partner_25.png"));
        
        DefaultMutableTreeNode uom = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_UOM"),
                "/net/purnama/image/Uom_25.png"));
        
        DefaultMutableTreeNode itemgroup = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_ITEMGROUP"),
                "/net/purnama/image/ItemGroup_25.png"));
        
        DefaultMutableTreeNode item = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_ITEM"),
                "/net/purnama/image/Item_25.png"));
        
        DefaultMutableTreeNode currency = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_CURRENCY"),
                "/net/purnama/image/Currency_25.png"));
        
        DefaultMutableTreeNode delivery = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_DELIVERY"),
                "/net/purnama/image/Delivery_25.jpg"));
        
        DefaultMutableTreeNode deliverydraft = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_DELIVERYDRAFT"),
                "/net/purnama/image/Delivery_25.jpg"));
        
        DefaultMutableTreeNode invoicesalesdraft = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICESALESDRAFT"),
                "/net/purnama/image/Invoice_25.jpg"));
        
        DefaultMutableTreeNode invoicesales = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICESALES"),
                "/net/purnama/image/Invoice_25.jpg"));
        
        DefaultMutableTreeNode invoicepurchasedraft = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICEPURCHASEDRAFT"),
                "/net/purnama/image/Invoice_25.jpg"));
        
        DefaultMutableTreeNode invoicepurchase = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICEPURCHASE"),
                "/net/purnama/image/Invoice_25.jpg"));
        
        DefaultMutableTreeNode returnsalesdraft = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_RETURNSALESDRAFT"),
                "/net/purnama/image/Return_25.png"));
        
        DefaultMutableTreeNode returnsales = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_RETURNSALES"),
                "/net/purnama/image/Return_25.png"));
        
        DefaultMutableTreeNode returnpurchasedraft = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_RETURNPURCHASEDRAFT"),
                "/net/purnama/image/Return_25.png"));
        
        DefaultMutableTreeNode returnpurchase = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_RETURNPURCHASE"),
                "/net/purnama/image/Return_25.png"));
        
        DefaultMutableTreeNode expensesdraft = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_EXPENSESDRAFT"),
                "/net/purnama/image/Expenses_25.png"));
        
        DefaultMutableTreeNode expenses = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_EXPENSES"),
                "/net/purnama/image/Expenses_25.png"));
        
        DefaultMutableTreeNode adjustmentdraft = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_ADJUSTMENTDRAFT"),
                "/net/purnama/image/Adjustment_25.png"));
        
        DefaultMutableTreeNode adjustment = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_ADJUSTMENT"),
                "/net/purnama/image/Adjustment_25.png"));
        
        DefaultMutableTreeNode invoicewarehouseindraft = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICEWAREHOUSEINDRAFT"),
                "/net/purnama/image/WarehouseIn_25.jpeg"));
        
        DefaultMutableTreeNode invoicewarehousein = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICEWAREHOUSEIN"),
                "/net/purnama/image/WarehouseIn_25.jpeg"));
        
        DefaultMutableTreeNode invoicewarehouseoutdraft = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICEWAREHOUSEOUTDRAFT"),
                "/net/purnama/image/WarehouseOut_25.jpeg"));
        
        DefaultMutableTreeNode invoicewarehouseout = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICEWAREHOUSEOUT"),
                "/net/purnama/image/WarehouseOut_25.jpeg"));
        
        DefaultMutableTreeNode paymentindraft = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PAYMENTINDRAFT"),
                "/net/purnama/image/Payment_25.png"));
        
        DefaultMutableTreeNode paymentin = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PAYMENTIN"),
                "/net/purnama/image/Payment_25.png"));
        
        DefaultMutableTreeNode paymentoutdraft = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PAYMENTOUTDRAFT"),
                "/net/purnama/image/Payment_25.png"));
        
        DefaultMutableTreeNode paymentout = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PAYMENTOUT"),
                "/net/purnama/image/Payment_25.png"));
        
        DefaultMutableTreeNode paymenttypein = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PAYMENTTYPEIN"),
                "/net/purnama/image/Payment_25.png"));
        
        DefaultMutableTreeNode paymenttypeout = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PAYMENTTYPEOUT"),
                "/net/purnama/image/Payment_25.png"));
        
        DefaultMutableTreeNode report = new DefaultMutableTreeNode(new Menu(
                GlobalFields.PROPERTIES.getProperty("LABEL_MENU_REPORT"),
                ""));
        
        root.add(master);
        
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
        
            @Override
            protected Boolean doInBackground(){
            
                if(GlobalFields.ROLE.isUser_home()){
                    master.add(user);
                }
                if(GlobalFields.ROLE.isRole_home()){
                    master.add(role);
                }
                if(GlobalFields.ROLE.isMenu_home()){
                    master.add(menu);
                }
                if(GlobalFields.ROLE.isCurrency_home()){
                    master.add(currency);
                }
                if(GlobalFields.ROLE.isWarehouse_home()){
                    master.add(warehouse);
                }
                if(GlobalFields.ROLE.isNumberingname_home()){
                    master.add(numberingname);
                }
                if(GlobalFields.ROLE.isPartnertype_home()){
                    master.add(partnertype);
                }
                if(GlobalFields.ROLE.isPartner_home()){
                    master.add(partner);
                }
                if(GlobalFields.ROLE.isUom_home()){
                    master.add(uom);
                }
                if(GlobalFields.ROLE.isItemgroup_home()){
                    master.add(itemgroup);
                }
                if(GlobalFields.ROLE.isItem_home()){
                    master.add(item);
                }

                root.add(sales);

                if(GlobalFields.ROLE.isInvoicesales_draft()){
                    sales.add(invoicesalesdraft);
                }
                if(GlobalFields.ROLE.isInvoicesales_home()){
                    sales.add(invoicesales);
                }
                if(GlobalFields.ROLE.isReturnsales_draft()){
                    sales.add(returnsalesdraft);
                }
                if(GlobalFields.ROLE.isReturnsales_home()){
                    sales.add(returnsales);
                }
                if(GlobalFields.ROLE.isDelivery_draft()){
                    sales.add(deliverydraft);
                }
                if(GlobalFields.ROLE.isDelivery_home()){
                    sales.add(delivery);
                }

                root.add(purchase);

                if(GlobalFields.ROLE.isInvoicepurchase_draft()){
                    purchase.add(invoicepurchasedraft);
                }
                if(GlobalFields.ROLE.isInvoicepurchase_home()){
                    purchase.add(invoicepurchase);
                }
                if(GlobalFields.ROLE.isReturnpurchase_draft()){
                    purchase.add(returnpurchasedraft);
                }
                if(GlobalFields.ROLE.isReturnpurchase_home()){
                        purchase.add(returnpurchase);
                }
                if(GlobalFields.ROLE.isExpenses_draft()){
                    purchase.add(expensesdraft);
                }
                if(GlobalFields.ROLE.isExpenses_home()){
                    purchase.add(expenses);
                }

                root.add(management);

                if(GlobalFields.ROLE.isAdjustment_draft()){
                    management.add(adjustmentdraft);
                }
                if(GlobalFields.ROLE.isAdjustment_home()){
                    management.add(adjustment);
                }
                if(GlobalFields.ROLE.isInvoicewarehousein_draft()){
                    management.add(invoicewarehouseindraft);
                }
                if(GlobalFields.ROLE.isInvoicewarehousein_home()){
                    management.add(invoicewarehousein);
                }
                if(GlobalFields.ROLE.isInvoicewarehouseout_draft()){
                    management.add(invoicewarehouseoutdraft);
                }
                if(GlobalFields.ROLE.isInvoicewarehouseout_home()){
                    management.add(invoicewarehouseout);
                }

                root.add(payment);

                if(GlobalFields.ROLE.isPaymentin_draft()){
                    payment.add(paymentindraft);
                }
                if(GlobalFields.ROLE.isPaymentin_home()){
                    payment.add(paymentin);
                }
                if(GlobalFields.ROLE.isPaymentout_draft()){
                    payment.add(paymentoutdraft);
                }
                if(GlobalFields.ROLE.isPaymentout_home()){
                    payment.add(paymentout);
                }
                if(GlobalFields.ROLE.isPaymenttypein_home()){
                    payment.add(paymenttypein);
                }
                if(GlobalFields.ROLE.isPaymenttypeout_home()){
                    payment.add(paymenttypeout);
                }

                root.add(report);
                
                return true;
            }
            
            @Override
            protected void done() {
                
            }
        };
        
        worker.execute();
        
        navigationtree = new JTree(root);
        navigationtree.setEditable(true);
        navigationtree.setOpaque(true);
        navigationtree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        navigationtree.setCellRenderer(new NavigationTreeCellRenderer());
        navigationtree.setCellEditor(new NavigationTreeCellEditor());
        navigationtree.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        
        popupmenu = new JPopupMenu();
        
        menucollapse = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_COLLAPSE"));
        menuexpand = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_EXPAND"));
        
        popupmenu.add(menucollapse);
        popupmenu.add(menuexpand);
        
        navigationtree.setComponentPopupMenu(popupmenu);
        
        menucollapse.addActionListener((ActionEvent e) -> {
        });
        
        menuexpand.addActionListener((ActionEvent e) -> {
            
        });
        
        MouseListener ml = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int selRow = navigationtree.getRowForLocation(e.getX(), e.getY());
                TreePath selPath = navigationtree.getPathForLocation(e.getX(), e.getY());
                if(selRow != -1) {
                    if(e.getClickCount() == 1) {
                        
                    }
                    else if(e.getClickCount() == 2) {
                        DefaultMutableTreeNode selectedNode = 
                                ((DefaultMutableTreeNode)selPath.getLastPathComponent());
                        
                        if(selectedNode.getUserObject() instanceof Menu){
                            Menu menu = (Menu)selectedNode.getUserObject();
                            
                            if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_USER"))){
                                tabbedpane.addTab(new UserHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_ROLE"))){
                                tabbedpane.addTab(new RoleHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_MENU"))){
                                tabbedpane.addTab(new MenuHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_CURRENCY"))){
                                tabbedpane.addTab(new CurrencyHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_WAREHOUSE"))){
                                tabbedpane.addTab(new WarehouseHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_NUMBERINGNAME"))){
                                tabbedpane.addTab(new NumberingNameHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PARTNERTYPE"))){
                                tabbedpane.addTab(new PartnerTypeHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PARTNER"))){
                                tabbedpane.addTab(new PartnerHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_UOM"))){
                                tabbedpane.addTab(new UomHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_ITEMGROUP"))){
                                tabbedpane.addTab(new ItemGroupHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_ITEM"))){
                                tabbedpane.addTab(new ItemHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_DELIVERYDRAFT"))){
                                tabbedpane.addTab(new DeliveryDraftHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_DELIVERY"))){
                                tabbedpane.addTab(new DeliveryHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICESALESDRAFT"))){
                                tabbedpane.addTab(new InvoiceSalesDraftHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICESALES"))){
                                tabbedpane.addTab(new InvoiceSalesHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICEPURCHASEDRAFT"))){
                                tabbedpane.addTab(new InvoicePurchaseDraftHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICEPURCHASE"))){
                                tabbedpane.addTab(new InvoicePurchaseHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_RETURNSALESDRAFT"))){
                                tabbedpane.addTab(new ReturnSalesDraftHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_RETURNSALES"))){
                                tabbedpane.addTab(new ReturnSalesHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_RETURNPURCHASEDRAFT"))){
                                tabbedpane.addTab(new ReturnPurchaseDraftHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_RETURNPURCHASE"))){
                                tabbedpane.addTab(new ReturnPurchaseHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_EXPENSESDRAFT"))){
                                tabbedpane.addTab(new ExpensesDraftHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_EXPENSES"))){
                                tabbedpane.addTab(new ExpensesHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_ADJUSTMENTDRAFT"))){
                                tabbedpane.addTab(new AdjustmentDraftHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_ADJUSTMENT"))){
                                tabbedpane.addTab(new AdjustmentHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICEWAREHOUSEINDRAFT"))){
                                tabbedpane.addTab(new InvoiceWarehouseInDraftHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICEWAREHOUSEIN"))){
                                tabbedpane.addTab(new InvoiceWarehouseInHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICEWAREHOUSEOUTDRAFT"))){
                                tabbedpane.addTab(new InvoiceWarehouseOutDraftHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_INVOICEWAREHOUSEOUT"))){
                                tabbedpane.addTab(new InvoiceWarehouseOutHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PAYMENTINDRAFT"))){
                                tabbedpane.addTab(new PaymentInDraftHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PAYMENTIN"))){
                                tabbedpane.addTab(new PaymentInHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PAYMENTOUTDRAFT"))){
                                tabbedpane.addTab(new PaymentOutDraftHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PAYMENTOUT"))){
                                tabbedpane.addTab(new PaymentOutHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PAYMENTTYPEIN"))){
                                tabbedpane.addTab(new PaymentTypeInHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_PAYMENTTYPEOUT"))){
                                tabbedpane.addTab(new PaymentTypeOutHome());
                            }
                            else if(menu.getName().equals(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_REPORT"))){
                                tabbedpane.addTab(new ReportHome());
                            }
                        }
                        else{
                            String menu = (String)selectedNode.getUserObject();
                            
                            if(menu.equals("HelloPOS")){
                                tabbedpane.addTab(new WidgetHome());
                            }
                            else{
                                navigationtree.expandPath(selPath);
                            }
                        }
                    }
                }
            }
        };
        
        navigationtree.addMouseListener(ml);
        
        upperscrollpane.getViewport().add(navigationtree);
    }
}

class NavigationTreeCellEditor extends AbstractCellEditor implements TreeCellEditor {
    
    private final JLabel label;
    
    NavigationTreeCellEditor() {
        label = new JLabel();
        label.setOpaque(true);
        label.setBackground(Color.LIGHT_GRAY);
        label.setFont(new Font("Arial", Font.BOLD, 15));
    }

    @Override
    public Component getTreeCellEditorComponent(JTree tree, 
            Object value, boolean isSelected, boolean expanded, boolean leaf, 
            int row) {
        Object o = ((DefaultMutableTreeNode) value).getUserObject();
        
        if (o instanceof Menu){
            
            Menu menu = (Menu) o;
            URL imageUrl = getClass().getResource(menu.getIcon());
            if (imageUrl != null) {
                label.setIcon(new ImageIcon(imageUrl));
            }
            label.setText(menu.getName());
        } 
        else {
            label.setIcon(null);
            
            label.setText("" + value);
        }
        isSelected = true;
        return label;
    }

    @Override
    public Object getCellEditorValue() {
        return null;
    }
}

class NavigationTreeCellRenderer implements TreeCellRenderer {
    private final JLabel label;

    NavigationTreeCellRenderer() {
        label = new JLabel();
        label.setFont(new Font("Arial", Font.BOLD, 15));
    }
    
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, 
            boolean selected, boolean expanded, 
            boolean leaf, int row, boolean hasFocus){
        Object o = ((DefaultMutableTreeNode) value).getUserObject();
        if (o instanceof Menu){
            Menu menu = (Menu) o;
            URL imageUrl = getClass().getResource(menu.getIcon());
            if (imageUrl != null) {
                label.setIcon(new ImageIcon(imageUrl));
            }
            label.setText(menu.getName());
        } 
        else {
            label.setIcon(null);
            label.setText("" + value);
        }
        return label;
    }
}


class Menu{
    private String name;
    private String icon;
    
    public Menu(String name, String icon){
        this.name = name;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public String getIcon() {
        return icon;
    }
        
    public void setName(String name){
        this.name = name;
    }
    
    public void setIcon(String icon){
        this.icon = icon;
    }
}