/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.main;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import net.purnama.gui.dialog.NewFileDialog;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.login.LoginFrame;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class MainMenuBar extends JMenuBar{
    
    private final JMenu file, help;
    
    private final JMenuItem newfile, about, exit;
    
    public MainMenuBar(MainFrame mainframe, MainTabbedPane tabbedpane){
        super();
        
        file = new JMenu(GlobalFields.PROPERTIES.getProperty("LABEL_FILE"));
        file.setMnemonic(KeyEvent.VK_F);
        help = new JMenu(GlobalFields.PROPERTIES.getProperty("LABEL_HELP"));
        help.setMnemonic(KeyEvent.VK_H);
        
        newfile = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_NEW"),
                         new MyImageIcon().getImage("net/purnama/image/NewFile_16.png"));
        exit = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_LOGOUT"));
        file.add(newfile);
        file.addSeparator();
        file.add(exit);
        
        about = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_ABOUT"));
        help.add(about);
        
        add(file);
        add(help);
        
        newfile.addActionListener((ActionEvent e) -> {
            NewFileDialog nfd = new NewFileDialog();
            nfd.showDialog();
        });
        
        exit.addActionListener((ActionEvent e) -> {
            mainframe.dispose();
            GlobalFields.TOKEN = "";
            LoginFrame loginframe = new LoginFrame();
        });
    }
    
}
