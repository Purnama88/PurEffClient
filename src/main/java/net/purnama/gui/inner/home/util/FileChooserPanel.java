/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.gui.inner.home.util;

import javax.swing.JPanel;
import net.purnama.gui.library.MyButton;
import net.purnama.gui.library.MyFileChooser;
import net.purnama.util.GlobalFields;

/**
 *
 * @author purnama
 */
public class FileChooserPanel extends JPanel{
    
    private final MyFileChooser filechooser;
    private final MyButton submitbutton;
    
    public FileChooserPanel(String title){
        
        filechooser = new MyFileChooser();
        submitbutton = new MyButton(GlobalFields.PROPERTIES.getProperty("LABEL_SUBMIT"));
        
        add(filechooser);
        add(submitbutton);
    }
    
    public String getFilePath(){
        return filechooser.getSelectedFile().getAbsolutePath();
    }
    
    public MyButton getSubmitButton(){
        return submitbutton;
    }
}
