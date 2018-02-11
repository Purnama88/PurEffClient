/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.gui.dialog;

import java.util.ArrayList;
import javax.swing.JScrollPane;
import net.purnama.gui.library.MyDialog;
import net.purnama.gui.library.MyTable;
import net.purnama.model.ItemGroupEntity;
import net.purnama.tablemodel.ItemGroupTableModel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author purnama
 */
public class ItemGroupImportDialog extends MyDialog{
    
    private final MyTable table;
    private final ItemGroupTableModel itemgrouptablemodel;
    private final JScrollPane scrollpane;
    
    public ItemGroupImportDialog(ArrayList<ItemGroupEntity> list){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_DIALOG_OTHERS"), 450, 250);
        
        table = new MyTable();
        itemgrouptablemodel = new ItemGroupTableModel(list);
        
        scrollpane = new JScrollPane();
        scrollpane.setBorder(null);
        scrollpane.getViewport().add(table);
        
        table.setModel(itemgrouptablemodel);
        
        box.add(scrollpane);
    }
    
    public void showDialog(){
        setVisible(true);
    }
}
