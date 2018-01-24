/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.table;

import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BoxLayout;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.purnama.gui.inner.detail.util.DiscountSubtotalPanel;
import net.purnama.gui.inner.detail.util.ExpensesPanel;
import net.purnama.gui.inner.detail.util.TotalPanel;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.library.MyTable;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class InvoiceTablePanel extends JPanel{
    
    protected final MyTable table;
//    protected final SubtotalDiscountTable subtotaldiscounttable;
//    protected final ExpensesTable expensestable;
//    protected final TotalTable totaltable;
    
    protected final DiscountSubtotalPanel discountsubtotalpanel;
    protected final ExpensesPanel expensespanel;
    protected final TotalPanel totalpanel;
    
    protected final JPopupMenu popupmenu;
    
    protected final JMenuItem menuitemdelete, menuitemcopy;
    
    protected final JScrollPane scrollpane;
    
    public InvoiceTablePanel(){
        super();
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        scrollpane = new JScrollPane();
        
        table = new MyTable();
        scrollpane.setBorder(null);
        scrollpane.getViewport().add(table);
        
//        subtotaldiscounttable = new SubtotalDiscountTable();
//        expensestable = new ExpensesTable();
//        totaltable = new TotalTable();
        
        discountsubtotalpanel = new DiscountSubtotalPanel();
        expensespanel = new ExpensesPanel();
        totalpanel = new TotalPanel();
        
        popupmenu = new JPopupMenu();
        
        menuitemdelete = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_DELETE"),
                new MyImageIcon().getImage("net/purnama/image/Delete_16.png"));
        menuitemcopy = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_COPY"),
                new MyImageIcon().getImage("net/purnama/image/Copy_16.png"));
        
        popupmenu.add(menuitemcopy);
        popupmenu.addSeparator();
        popupmenu.add(menuitemdelete);
        
        table.setComponentPopupMenu(popupmenu);
        
        table.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                Point p = e.getPoint();
		if(SwingUtilities.isRightMouseButton(e)){
                    int rowNumber = table.rowAtPoint( p );
                    ListSelectionModel model = table.getSelectionModel();
                    model.setSelectionInterval(rowNumber, rowNumber);
		}
            }
        });
        
        menuitemcopy.addActionListener((ActionEvent e) -> {
            try{
                StringSelection stringselection = new StringSelection(table.
                        getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString());

                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringselection,
                        null);
            }
            catch(HeadlessException exp){
                exp.printStackTrace();
            }
        });
        
        add(scrollpane);
//        add(subtotaldiscounttable);
//        add(expensestable);
//        add(totaltable);
    }
    
    public void disableExpensesPanel(){
        expensespanel.setTextFieldEnabled(false);
    }
    
    public MyTable getTable(){
        return table;
    }
    
    public double getSubtotal(){
        return discountsubtotalpanel.getSubtotal();
    }
    
    public void setSubtotal(double value){
        discountsubtotalpanel.setSubtotal(value);
    }
    
    public double getDiscount(){
        return discountsubtotalpanel.getDiscount();
    }
    
    public void setDiscount(double value){
        discountsubtotalpanel.setDiscount(value);
    }
    
    public double getRounding(){
        return expensespanel.getRounding();
    }
    
    public void setRounding(double value){
        expensespanel.setRounding(value);
    }
    
    public double getFreight(){
        return expensespanel.getFreight();
    }
    
    public void setFreight(double value){
        expensespanel.setFreight(value);
    }
    
    public double getTax(){
        return expensespanel.getTax();
    }
    
    public void setTax(double value){
        expensespanel.setTax(value);
    }
    
    public double getTotal(){
        return totalpanel.getTotal();
    }
    
    public void setTotal(double value){
        totalpanel.setTotal(value);
    }
}
