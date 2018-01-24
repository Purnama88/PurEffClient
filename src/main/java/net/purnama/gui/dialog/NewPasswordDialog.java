/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.dialog;

import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import net.purnama.gui.library.MyButton;
import net.purnama.gui.library.MyDialog;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.library.MyLabel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class NewPasswordDialog extends MyDialog{
    
    private final JPanel panel1, panel2;
    
    private final MyButton copybutton;
    
    public NewPasswordDialog(String password) {
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PASSWORD"), 300, 150);
        
        panel1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panel2 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        copybutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Copy_16.png"), 
                24, 24);
        copybutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("LABEL_COPY"));
        
        panel1.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CHANGEPASSWORD")));
        panel2.add(new MyLabel(password));
        panel2.add(copybutton);
        
        box.add(panel1);
        box.add(panel2);
        box.add(okpanel);
        
        copybutton.addActionListener((ActionEvent e) -> {
            try{
                StringSelection stringselection = new StringSelection(password);

                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringselection,
                        null);
            }
            catch(HeadlessException exp){
                exp.printStackTrace();
            }
        });
    }
    
    public void showDialog(){
        setVisible(true);
    }
    
}

