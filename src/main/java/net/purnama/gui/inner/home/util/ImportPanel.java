/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.gui.inner.home.util;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import net.purnama.controller.ImportController;
import net.purnama.gui.dialog.ItemGroupImportDialog;
import net.purnama.model.ItemEntity;
import net.purnama.model.ItemGroupEntity;
import net.purnama.model.PartnerEntity;
import net.purnama.util.GlobalFields;

/**
 *
 * @author purnama
 */
public class ImportPanel extends JPanel{
    
    private final FileChooserPanel filechooserpanel1, filechooserpanel2, filechooserpanel3;
    
    public ImportPanel(){
        super();
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        filechooserpanel1 = new FileChooserPanel(GlobalFields.PROPERTIES.getProperty("LABEL_IMPORT_PARTNER"), 
                FileChooserPanel.OPEN);
        
        filechooserpanel2 = new FileChooserPanel(GlobalFields.PROPERTIES.getProperty("LABEL_IMPORT_ITEMGROUP"), 
                FileChooserPanel.OPEN);
        
        filechooserpanel3 = new FileChooserPanel(GlobalFields.PROPERTIES.getProperty("LABEL_IMPORT_ITEM"), 
                FileChooserPanel.OPEN);
        
        add(filechooserpanel1);
        add(filechooserpanel2);
        add(filechooserpanel3);
        
        filechooserpanel1.getSubmitButton().addActionListener((ActionEvent e) ->{
            ImportController importcontroller = new ImportController(filechooserpanel2.getFilePath());
            List<PartnerEntity> partnerlist = importcontroller.importPartner();
        });
        
        filechooserpanel2.getSubmitButton().addActionListener((ActionEvent e) ->{
            ImportController importcontroller = new ImportController(filechooserpanel1.getFilePath());
            ArrayList<ItemGroupEntity> itemgrouplist = importcontroller.importItemGroup();
            
            ItemGroupImportDialog igid = new ItemGroupImportDialog(itemgrouplist);
            igid.showDialog();
            
        });
        
        filechooserpanel3.getSubmitButton().addActionListener((ActionEvent e) ->{
            ImportController importcontroller = new ImportController(filechooserpanel3.getFilePath());
            List<ItemEntity> itemlist = importcontroller.importItem();
        });
    }
}
