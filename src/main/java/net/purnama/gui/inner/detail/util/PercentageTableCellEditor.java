/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.util;

import java.awt.Component;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableCellEditor;
import net.purnama.gui.library.MyTextField;

/**
 *
 * @author Purnama
 */
public class PercentageTableCellEditor extends AbstractCellEditor implements TableCellEditor{

    private final JTextField component;
    
    public PercentageTableCellEditor(){
        super();
        
        component = new JTextField();
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, 
            boolean isSelected, int rowIndex, int vColIndex){
        String value2 = value.toString();
        
        value2 = value2.replace("(", "");
        value2 = value2.replace("%)", "");
        
        component.setText(value2);

        return component;
    }

    @Override
    public Object getCellEditorValue() {
      return component.getText();
    }
}