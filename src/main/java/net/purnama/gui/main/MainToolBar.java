/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.main;

import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import javax.swing.Box;
import javax.swing.JToolBar;
import net.purnama.gui.dialog.InfoDialog;
import net.purnama.gui.dialog.NewFileDialog;
import net.purnama.gui.inner.home.SettingHome;
import net.purnama.gui.library.MyButton;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.library.MyPanel;
import net.purnama.gui.login.LoginFrame;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class MainToolBar extends JToolBar{
    
    private final MyButton newfilebutton, savebutton, printpreviewbutton, printbutton, helpbutton, 
            settingbutton, calculatorbutton,
            infobutton, importbutton, exportbutton, logoutbutton;
    
    public MainToolBar(MainFrame mainframe, MainTabbedPane tabbedpane){
        super();
        
        setFloatable(false);
        setRollover(true);
        
        setMinimumSize(new Dimension(Integer.MAX_VALUE, 40));
        setPreferredSize(new Dimension(Integer.MAX_VALUE, 40));
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
        
        newfilebutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/NewFile_24.png"), 30, 30);
        savebutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Save_24.png"), 30, 30);
        printpreviewbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/PrintPreview_24.png"), 30, 30);
        printbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Print_24.png"), 30, 30);
        helpbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Help_24.png"), 30, 30);
        infobutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Info_24.png"), 30, 30);
        settingbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Setting_24.png"), 30, 30);
        calculatorbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Calculator_24.png"), 30, 30);
        importbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Import_24.png"), 30, 30);
        exportbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Export_24.png"), 30, 30);
        logoutbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Logout_24.png"), 30, 30);
        
        newfilebutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_NEW"));
        savebutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_SAVE"));
        printpreviewbutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_PRINTPREVIEW"));
        printbutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_PRINT"));
        helpbutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_HELP"));
        infobutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_ABOUT"));
        settingbutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_SETTING"));
        importbutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_IMPORT"));
        exportbutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_EXPORT"));
        exportbutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_LOGOUT"));
        
//        MouseEvent me = new MouseEvent();
//        
//        newfilebutton.addMouseListener(me);
        
        add(newfilebutton);
        add(savebutton);
        addSeparator();
        add(printpreviewbutton);
//        add(printbutton);
        addSeparator();
//        add(importbutton);
        add(exportbutton);
        addSeparator();
        add(calculatorbutton);
        addSeparator();
        add(settingbutton);
        addSeparator();
        add(helpbutton);
        add(infobutton);
        
        add(Box.createHorizontalGlue());
        
        add(logoutbutton);
        
        helpbutton.addActionListener((ActionEvent e) -> {
            if (Desktop.isDesktopSupported()) {
                try {
                    File myFile = new File("HelloPOS-UserManual.pdf");
                    Desktop.getDesktop().open(myFile);
                } catch (IOException ex) {
                    // no application registered for PDFs
                }
            }
        });
        
        newfilebutton.addActionListener((ActionEvent e) -> {
            NewFileDialog nfd = new NewFileDialog();
            nfd.showDialog();
        });
        
        printbutton.addActionListener((ActionEvent e) -> {
            MyPanel panel = (MyPanel)tabbedpane.getComponentAt(tabbedpane.getSelectedIndex());
            panel.print();
        });
        
        printpreviewbutton.addActionListener((ActionEvent e) -> {
            MyPanel panel = (MyPanel)tabbedpane.getComponentAt(tabbedpane.getSelectedIndex());
            panel.printpreview();
        });
        
        importbutton.addActionListener((ActionEvent e) -> {
//            MyPanel panel = (MyPanel)tabbedpane.getComponentAt(tabbedpane.getSelectedIndex());
//            panel.import();
        });
        
        exportbutton.addActionListener((ActionEvent e) -> {
            MyPanel panel = (MyPanel)tabbedpane.getComponentAt(tabbedpane.getSelectedIndex());
            panel.export();
        });
        
        settingbutton.addActionListener((ActionEvent e) -> {
            tabbedpane.addTab(new SettingHome());
        });
        
        infobutton.addActionListener((ActionEvent e) -> {
            new InfoDialog().showDialog();
        });
        
        logoutbutton.addActionListener((ActionEvent e) ->{
            mainframe.dispose();
            GlobalFields.TOKEN = "";
            GlobalFields.ROLE = null;
            GlobalFields.USER = null;
            LoginFrame loginframe = new LoginFrame();
        });
    }
    
//    protected JButton makeNavigationButton(String imageName,
//                                       String actionCommand,
//                                       String toolTipText,
//                                       String altText) {
//    //Look for the image.
//    String imgLocation = "net/purnama/gui/image/"
//                         + imageName
//                         + ".gif";
//    URL imageURL = ToolBarDemo.class.getResource(imgLocation);
//
//    //Create and initialize the button.
//    JButton button = new JButton();
//    button.setActionCommand(actionCommand);
//    button.setToolTipText(toolTipText);
//    button.addActionListener(this);
//
//    if (imageURL != null) {                      //image found
//        button.setIcon(new ImageIcon(imageURL, altText));
//    } else {                                     //no image found
//        button.setText(altText);
//        System.err.println("Resource not found: " + imgLocation);
//    }

//    return button;
//}
//}
//
//class MouseEvent implements MouseListener{
//
//    @Override
//    public void mouseClicked(java.awt.event.MouseEvent e) {
//        
//    }
//
//    @Override
//    public void mousePressed(java.awt.event.MouseEvent e) {
//        
//    }
//
//    @Override
//    public void mouseReleased(java.awt.event.MouseEvent e) {
//        
//    }
//
//    @Override
//    public void mouseEntered(java.awt.event.MouseEvent e) {
//        MyButton button = (MyButton)e.getComponent();
//        System.out.println("masuk");
//        button.setOpaque(false);
//        button.setBorder(null);
//    }
//
//    @Override
//    public void mouseExited(java.awt.event.MouseEvent e) {
//        MyButton button = (MyButton)e.getComponent();
//        System.out.println("keluar");
//        button.setOpaque(false);
//        button.setBorder(null);
//    }
}