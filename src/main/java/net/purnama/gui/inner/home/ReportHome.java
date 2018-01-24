/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.home;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;
import net.purnama.gui.inner.report.AdjustmentReport;
import net.purnama.gui.inner.report.DeliveryDetailReport;
import net.purnama.gui.inner.report.ExpensesDetailReport;
import net.purnama.gui.inner.report.ExpensesReport;
import net.purnama.gui.inner.report.InvoicePurchaseDetailReport;
import net.purnama.gui.inner.report.InvoicePurchaseReport;
import net.purnama.gui.inner.report.InvoiceSalesDetailReport;
import net.purnama.gui.inner.report.InvoiceSalesReport;
import net.purnama.gui.inner.report.InvoiceWarehouseInReport;
import net.purnama.gui.inner.report.InvoiceWarehouseOutReport;
import net.purnama.gui.inner.report.ItemStockReport;
import net.purnama.gui.inner.report.PartnerReport;
import net.purnama.gui.inner.report.PaymentInDetailReport;
import net.purnama.gui.inner.report.PaymentInReport;
import net.purnama.gui.inner.report.PaymentOutDetailReport;
import net.purnama.gui.inner.report.PaymentOutReport;
import net.purnama.gui.inner.report.PaymentTypeInReport;
import net.purnama.gui.inner.report.PaymentTypeOutReport;
import net.purnama.gui.inner.report.ReturnPurchaseDetailReport;
import net.purnama.gui.inner.report.ReturnPurchaseReport;
import net.purnama.gui.inner.report.ReturnSalesDetailReport;
import net.purnama.gui.inner.report.ReturnSalesReport;
import net.purnama.gui.library.MyPanel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ReportHome extends MyPanel{
    
    private final JSplitPane splitpane;
    
    private final JPanel rightpanel;
    
    private final JScrollPane leftscrollpane;
    
    private final JTree navigationtree;
    
    public ReportHome() {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_REPORT"));
        
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_MENU_REPORT"));
        
        DefaultMutableTreeNode itemstockreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_ITEMSTOCK"));
        DefaultMutableTreeNode itemreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_ITEM"));
        DefaultMutableTreeNode partnerreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_PARTNER"));
        DefaultMutableTreeNode invoicesalesreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_INVOICESALES"));
        DefaultMutableTreeNode invoicesalesdetailreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_INVOICESALESDETAIL"));
        DefaultMutableTreeNode returnsalesreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_RETURNSALES"));
        DefaultMutableTreeNode returnsalesdetailreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_RETURNSALESDETAIL"));
        DefaultMutableTreeNode deliverydetailreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_DELIVERYDETAIL"));
        DefaultMutableTreeNode expensesreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_EXPENSES"));
        DefaultMutableTreeNode expensesdetailreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_EXPENSESDETAIL"));
        DefaultMutableTreeNode invoicepurchasereport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_INVOICEPURCHASE"));
        DefaultMutableTreeNode invoicepurchasedetailreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_INVOICEPURCHASEDETAIL"));
        DefaultMutableTreeNode returnpurchasereport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_RETURNPURCHASE"));
        DefaultMutableTreeNode returnpurchasedetailreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_RETURNPURCHASEDETAIL"));
        DefaultMutableTreeNode adjustmentreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_ADJUSTMENTDETAIL"));
        DefaultMutableTreeNode invoicewarehouseinreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_INVOICEWAREHOUSEINDETAIL"));
        DefaultMutableTreeNode invoicewarehouseoutreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_INVOICEWAREHOUSEOUTDETAIL"));
        DefaultMutableTreeNode paymentinreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_PAYMENTIN"));
        DefaultMutableTreeNode paymentindetailreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_PAYMENTINDETAIL"));
        DefaultMutableTreeNode paymentoutreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_PAYMENTOUT"));
        DefaultMutableTreeNode paymentoutdetailreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_PAYMENTOUTDETAIL"));
        DefaultMutableTreeNode paymenttypeinreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_PAYMENTTYPEIN"));
        DefaultMutableTreeNode paymenttypeoutreport = 
                new DefaultMutableTreeNode(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_PAYMENTTYPEOUT"));
        
        root.add(itemstockreport);
//        root.add(itemreport);
        root.add(partnerreport);
        root.add(invoicesalesreport);
        root.add(invoicesalesdetailreport);
        root.add(returnsalesreport);
        root.add(returnsalesdetailreport);
        root.add(deliverydetailreport);
        root.add(expensesreport);
        root.add(expensesdetailreport);
        root.add(invoicepurchasereport);
        root.add(invoicepurchasedetailreport);
        root.add(returnpurchasereport);
        root.add(returnpurchasedetailreport);
        root.add(adjustmentreport);
        root.add(invoicewarehouseinreport);
        root.add(invoicewarehouseoutreport);
        root.add(paymentinreport);
        root.add(paymentindetailreport);
        root.add(paymentoutreport);
        root.add(paymentoutdetailreport);
        root.add(paymenttypeinreport);
        root.add(paymenttypeoutreport);
        
        navigationtree = new JTree(root);
        navigationtree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        navigationtree.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));
        
        leftscrollpane = new JScrollPane();
        leftscrollpane.setMinimumSize(new Dimension(250, Integer.MAX_VALUE));
        rightpanel = new JPanel();
        
        leftscrollpane.getViewport().add(navigationtree);
        
        splitpane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftscrollpane, rightpanel);
        
        add(splitpane, BorderLayout.CENTER);
        
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
                        
                        if(selectedNode.getUserObject() instanceof String){
                            String value = (String)selectedNode.getUserObject();
                            
                            if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_ITEMSTOCK"))){
                                splitpane.setRightComponent(new ItemStockReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_PARTNER"))){
                                splitpane.setRightComponent(new PartnerReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_DELIVERYDETAIL"))){
                                splitpane.setRightComponent(new DeliveryDetailReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_INVOICESALES"))){
                                splitpane.setRightComponent(new InvoiceSalesReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_INVOICESALESDETAIL"))){
                                splitpane.setRightComponent(new InvoiceSalesDetailReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_RETURNSALES"))){
                                splitpane.setRightComponent(new ReturnSalesReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_RETURNSALESDETAIL"))){
                                splitpane.setRightComponent(new ReturnSalesDetailReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_EXPENSES"))){
                                splitpane.setRightComponent(new ExpensesReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_EXPENSESDETAIL"))){
                                splitpane.setRightComponent(new ExpensesDetailReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_INVOICEPURCHASE"))){
                                splitpane.setRightComponent(new InvoicePurchaseReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_INVOICEPURCHASEDETAIL"))){
                                splitpane.setRightComponent(new InvoicePurchaseDetailReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_RETURNPURCHASE"))){
                                splitpane.setRightComponent(new ReturnPurchaseReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_RETURNPURCHASEDETAIL"))){
                                splitpane.setRightComponent(new ReturnPurchaseDetailReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_ADJUSTMENTDETAIL"))){
                                splitpane.setRightComponent(new AdjustmentReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_INVOICEWAREHOUSEINDETAIL"))){
                                splitpane.setRightComponent(new InvoiceWarehouseInReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_INVOICEWAREHOUSEOUTDETAIL"))){
                                splitpane.setRightComponent(new InvoiceWarehouseOutReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_PAYMENTIN"))){
                                splitpane.setRightComponent(new PaymentInReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_PAYMENTINDETAIL"))){
                                splitpane.setRightComponent(new PaymentInDetailReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_PAYMENTOUT"))){
                                splitpane.setRightComponent(new PaymentOutReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_PAYMENTOUTDETAIL"))){
                                splitpane.setRightComponent(new PaymentOutDetailReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_PAYMENTTYPEIN"))){
                                splitpane.setRightComponent(new PaymentTypeInReport(getIndex()));
                            }
                            else if(value.equals(GlobalFields.PROPERTIES.getProperty("LABEL_REPORT_PAYMENTTYPEOUT"))){
                                splitpane.setRightComponent(new PaymentTypeOutReport(getIndex()));
                            }
                        }
                        else{
                            
                        }
                    }
                }
            }
        };
        
        navigationtree.addMouseListener(ml);
    }
}
