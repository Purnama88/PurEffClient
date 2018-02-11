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
import net.purnama.model.PartnerEntity;
import net.purnama.tablemodel.PartnerTableModel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author purnama
 */
public class PartnerImportDialog extends MyDialog{
    
    private final MyTable table;
    private final PartnerTableModel partnertablemodel;
    private final JScrollPane scrollpane;
    
    public PartnerImportDialog(ArrayList<PartnerEntity> list){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_DIALOG_OTHERS"), 450, 250);
        
        table = new MyTable();
        partnertablemodel = new PartnerTableModel(list);
        
        scrollpane = new JScrollPane();
        scrollpane.setBorder(null);
        scrollpane.getViewport().add(table);
        
        table.setModel(partnertablemodel);
        
        box.add(scrollpane);
    }
    
    public void showDialog(){
        setVisible(true);
    }
}
