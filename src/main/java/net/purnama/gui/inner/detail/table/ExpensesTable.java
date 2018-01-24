/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.table;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;

/**
 *
 * @author Purnama
 */
public class ExpensesTable extends JTable{
    
    public ExpensesTable(){
        super(
            new Object [][] {
                {"Rounding", "5,000", "Freight" , "5,000", "Tax", "5,000", "10,000"}
            },
            new Object []{"", "", "", "", "", "", ""});
        
        setRowHeight(getRowHeight() + 15);
        getTableHeader().setUI(null);
        setEnabled(false);
        
        getColumnModel().getColumn(6).setMinWidth(150);
        getColumnModel().getColumn(6).setPreferredWidth(150);
        getColumnModel().getColumn(6).setMaxWidth(150);
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        getColumnModel().getColumn(6).setCellRenderer(rightRenderer);
        
    }
    
    public double getRounding(){
        return GlobalFunctions.convertToDouble((String)getValueAt(0, 1));
    }
    
    public double getFreight(){
        return GlobalFunctions.convertToDouble((String)getValueAt(0, 3));
    }
    
    public double getTax(){
        return GlobalFunctions.convertToDouble((String)getValueAt(0, 5));
    }
    
    public void setRounding(double value){
        setValueAt(GlobalFields.NUMBERFORMAT.format(value), 0, 1);
    }
    
    public void setFreight(double value){
        setValueAt(GlobalFields.NUMBERFORMAT.format(value), 0, 3);
    }
    
    public void setTax(double value){
        setValueAt(GlobalFields.NUMBERFORMAT.format(value), 0, 5);
    }
}

class TableModel extends AbstractTableModel {
    
    public TableModel(){
        
    }

    @Override
    public int getRowCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getColumnCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

