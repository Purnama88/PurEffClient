/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.library;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class MyDialog extends JDialog{
    
    protected final Box box;
    
    protected final JPanel submitpanel;
    
    protected final JPanel okpanel;
    
    protected final MyButton submitbutton, cancelbutton, okbutton;
    
    public MyDialog(String title, int width, int height){
        super(GlobalFields.MAINFRAME, title, Dialog.ModalityType.DOCUMENT_MODAL);
        
        setPreferredSize(new Dimension(width, height));
        
        box = Box.createVerticalBox();
        box.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        submitpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        okpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        
        submitbutton = new MyButton(GlobalFields.PROPERTIES.getProperty("LABEL_SUBMIT"));
        cancelbutton = new MyButton(GlobalFields.PROPERTIES.getProperty("LABEL_CANCEL"));
        okbutton = new MyButton(GlobalFields.PROPERTIES.getProperty("LABEL_OK"));
        submitpanel.add(submitbutton);
        submitpanel.add(cancelbutton);
        
        okpanel.add(okbutton);
        
        add(box);
        
        pack();
        
        setLocationRelativeTo(GlobalFields.MAINFRAME);
        
        cancelbutton.addActionListener((ActionEvent e) -> {
           dispose();
        });
        
        okbutton.addActionListener((ActionEvent e) -> {
           dispose();
        });
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }
}