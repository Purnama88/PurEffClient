/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import net.purnama.gui.library.MyButton;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.library.MyLabel;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class UpperPanel extends JPanel{
    
    private final JPanel leftpanel, middlepanel, rightpanel;
    
    private final MyLabel notiflabel, statuslabel;
    
    private final MyButton homebutton, editbutton, printpreviewbutton, exportbutton;
    
    private final JProgressBar progressbar;
    
    public UpperPanel(){
        super(new GridLayout(1, 3));
        
        setAlignmentX(Component.LEFT_ALIGNMENT);
        
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
    
        leftpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        middlepanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rightpanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
    
        homebutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Home_16.png"), 24, 24);
        homebutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_BACK"));
        editbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Edit_16.png"), 24, 24);
        editbutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_EDIT"));
        printpreviewbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/PrintPreview_16.png"), 24, 24);
        printpreviewbutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_PRINTPREVIEW"));
        exportbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Export_16.png"), 24, 24);
        exportbutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_EXPORT"));
        
        leftpanel.add(homebutton);
        
        statuslabel = new MyLabel("");
        middlepanel.add(statuslabel);
        
        progressbar = new JProgressBar();
        progressbar.setIndeterminate(true);
        progressbar.setVisible(false);
        
        notiflabel = new MyLabel("");
        rightpanel.add(notiflabel);
        rightpanel.add(progressbar);
        
        add(leftpanel);
        add(middlepanel);
        add(rightpanel);
    }
    
    public JPanel getMiddlePanel(){
        return middlepanel;
    }
        
    public void setStatusLabel(String value){
        statuslabel.setText(value);
    }
    
    public void setNotifLabel(String value){
        notiflabel.setText(value);
    }
    
    public void hideProgressBar(){
        progressbar.setVisible(false);
    }
    
    public void showProgressBar(){
        progressbar.setVisible(true);
    }
    
    public MyButton getHomeButton(){
        return homebutton;
    }
    
    public MyButton getEditButton(){
        return editbutton;
    }
    
    public void addEditButton(){
        leftpanel.add(editbutton);
    }
    
    public void removeEditButton(){
        leftpanel.remove(editbutton);
    }
    
    public MyButton getPrintPreviewButton(){
        return printpreviewbutton;
    }
    
    public void addPrintPreviewButton(){
        leftpanel.add(printpreviewbutton);
    }
    
    public void removePrintPreviewButton(){
        leftpanel.remove(printpreviewbutton);
    }
    
    public void addExportButton(){
        leftpanel.add(exportbutton); 
    }
    
    public MyButton getExportButton(){
        return exportbutton;
    }
}
