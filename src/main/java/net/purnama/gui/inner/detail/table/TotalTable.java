/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.table;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;

/**
 *
 * @author Purnama
 */
public class TotalTable extends JTable{
    
    
    public TotalTable(){
        super(
                new Object [][] {
                    {"Total", "145,000"}
                },
               new Object []{"", ""});
        
        
        setRowHeight(getRowHeight() + 15);
        getTableHeader().setUI(null);
        setEnabled(false);
        
//        getColumnModel().getColumn(0).setPreferredWidth(600);
        getColumnModel().getColumn(1).setMinWidth(150);
        getColumnModel().getColumn(1).setPreferredWidth(150);
        getColumnModel().getColumn(1).setMaxWidth(150);
        
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(JLabel.RIGHT);
        getColumnModel().getColumn(1).setCellRenderer(rightRenderer);
    }
    
    public double getTotal(){
        return GlobalFunctions.convertToDouble((String)getValueAt(0, 1));
    }
    
    public void setTotal(double value){
        setValueAt(GlobalFields.NUMBERFORMAT.format(value), 0, 1);
    }
}
