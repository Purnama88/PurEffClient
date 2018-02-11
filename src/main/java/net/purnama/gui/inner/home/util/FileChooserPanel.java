/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.gui.inner.home.util;

import java.awt.event.ActionEvent;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileFilter;
import net.purnama.gui.library.MyButton;
import net.purnama.gui.library.MyFileChooser;
import net.purnama.gui.library.MyLabel;
import net.purnama.gui.library.MyTextField;
import net.purnama.util.GlobalFields;

/**
 *
 * @author purnama
 */
public class FileChooserPanel extends JPanel{
    
    private final MyTextField textfield;
    private final MyLabel label;
    
    private final MyFileChooser filechooser;
    private final MyButton submitbutton, browsebutton;
    
    public static final int OPEN = 0;
    public static final int SAVE = 1;
    
    public FileChooserPanel(String title, int option){
        
        textfield = new MyTextField("", 250);
        label = new MyLabel(title);
        
        filechooser = new MyFileChooser();
        submitbutton = new MyButton(GlobalFields.PROPERTIES.getProperty("LABEL_SUBMIT"));
        browsebutton = new MyButton(GlobalFields.PROPERTIES.getProperty("LABEL_BROWSE"));
        
//        add(filechooser);
        add(label);
        add(textfield);
        add(browsebutton);
        add(submitbutton);
        
        browsebutton.addActionListener((ActionEvent e) -> {
            int returnValue;
            
            if(option == FileChooserPanel.OPEN){
                FileFilter filter1 = new ExtensionFileFilter("XLSX", new String[] {"XLSX" });
                filechooser.setFileFilter(filter1);
                
		returnValue = filechooser.showOpenDialog(null);   
            }
            else{
                returnValue = filechooser.showSaveDialog(null);
            }

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = filechooser.getSelectedFile();
                textfield.setText(selectedFile.getAbsolutePath());
            }
        });
    }
    
    public String getFilePath(){
        return filechooser.getSelectedFile().getAbsolutePath();
    }
    
    public MyButton getSubmitButton(){
        return submitbutton;
    }
    
    public void clearPath(){
        textfield.setText("");
    }
}

class ExtensionFileFilter extends FileFilter {
  String description;

  String extensions[];

  public ExtensionFileFilter(String description, String extension) {
    this(description, new String[] { extension });
  }

  public ExtensionFileFilter(String description, String extensions[]) {
    if (description == null) {
      this.description = extensions[0];
    } else {
      this.description = description;
    }
    this.extensions = (String[]) extensions.clone();
    toLower(this.extensions);
  }

  private void toLower(String array[]) {
    for (int i = 0, n = array.length; i < n; i++) {
      array[i] = array[i].toLowerCase();
    }
  }

  public String getDescription() {
    return description;
  }

  public boolean accept(File file) {
    if (file.isDirectory()) {
      return true;
    } else {
      String path = file.getAbsolutePath().toLowerCase();
      for (int i = 0, n = extensions.length; i < n; i++) {
        String extension = extensions[i];
        if ((path.endsWith(extension) && (path.charAt(path.length() - extension.length() - 1)) == '.')) {
          return true;
        }
      }
    }
    return false;
  }
}
