/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.gui.library;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author purnama
 */
public class MyFileChooser extends JFileChooser{
    
    public MyFileChooser(){
        super(FileSystemView.getFileSystemView().getHomeDirectory());
    }
    
}
