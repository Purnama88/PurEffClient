/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.gui.inner.home.util;

import java.awt.event.ActionEvent;
import java.util.ArrayList;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import net.purnama.controller.ImportController;
import net.purnama.gui.dialog.ItemGroupImportDialog;
import net.purnama.gui.dialog.ItemImportDialog;
import net.purnama.gui.dialog.PartnerImportDialog;
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
            ImportController importcontroller = new ImportController(filechooserpanel1.getFilePath());
            ArrayList<PartnerEntity> partnerlist = importcontroller.importPartner();
            
            if(partnerlist != null){
                PartnerImportDialog pid = new PartnerImportDialog(partnerlist);
                pid.showDialog();
            }
        });
        
        filechooserpanel2.getSubmitButton().addActionListener((ActionEvent e) ->{
            ImportController importcontroller = new ImportController(filechooserpanel2.getFilePath());
            ArrayList<ItemGroupEntity> itemgrouplist = importcontroller.importItemGroup();
            
            if(itemgrouplist != null){
                ItemGroupImportDialog igid = new ItemGroupImportDialog(itemgrouplist);
                igid.showDialog();
            }
            
        });
        
        filechooserpanel3.getSubmitButton().addActionListener((ActionEvent e) ->{
            ImportController importcontroller = new ImportController(filechooserpanel3.getFilePath());
            ArrayList<ItemEntity> itemlist = importcontroller.importItem();
            
            if(itemlist != null){
                ItemImportDialog iid = new ItemImportDialog(itemlist);
                iid.showDialog();
            }
        });
    }
}
