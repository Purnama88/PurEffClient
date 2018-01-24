/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.home.util;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import net.purnama.gui.library.MyButton;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.library.MyLabel;
import net.purnama.gui.library.MyTextField;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class UpperPanel extends JPanel{
    
    private final JPanel leftpanel, middlepanel, rightpanel;
    
    private final MyLabel notiflabel;
    private final JProgressBar progressbar;
    
    private final MyTextField textfield;
    private final MyLabel searchlabel, pagelabel, currentpagelabel, oflabel,
            totalpagelabel;
    
    private final MyButton addbutton, firstbutton, previousbutton, nextbutton, lastbutton;
    
    public UpperPanel(){
        super(new GridLayout(1, 3));
        
        setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));
        
        leftpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        middlepanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rightpanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        textfield = new MyTextField("", 150);
        searchlabel = new MyLabel(new MyImageIcon().getImage("net/purnama/image/Search_16.png"));
        
        addbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Add_16.png"), 
                24, 24);
        addbutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_ADD"));
        firstbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Arrow2Left_16.png"), 
                24, 24);
        firstbutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_FIRSTPAGE"));
        
        previousbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Arrow1Left_16.png"), 
                24, 24);
        previousbutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_PREVIOUSPAGE"));
        
        nextbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Arrow1Right_16.png"), 
                24, 24);
        nextbutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_NEXTPAGE"));
        
        lastbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Arrow2Right_16.png"), 
                24, 24);
        lastbutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_LASTPAGE"));
        
        pagelabel = new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_PAGE"));
        currentpagelabel = new MyLabel();
        oflabel = new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_OF"));
        totalpagelabel = new MyLabel();
        
        leftpanel.add(addbutton);
        leftpanel.add(searchlabel);
        leftpanel.add(textfield);
        
        middlepanel.add(firstbutton);
        middlepanel.add(previousbutton);
        middlepanel.add(pagelabel);
        middlepanel.add(currentpagelabel);
        middlepanel.add(oflabel);
        middlepanel.add(totalpagelabel);
        middlepanel.add(nextbutton);
        middlepanel.add(lastbutton);
        
        progressbar = new JProgressBar();
        progressbar.setIndeterminate(true);
        progressbar.setVisible(false);
        
        notiflabel = new MyLabel();
        
        rightpanel.add(notiflabel);
        rightpanel.add(progressbar);
        
        add(leftpanel);
        add(middlepanel);
        add(rightpanel);
    }
    
    public JPanel getLeftPanel(){
        return leftpanel;
    }
    
    public JPanel getMiddlePanel(){
        return middlepanel;
    }
    
    public MyTextField getSearchTextField(){
        return textfield;
    }
    
    public String getSearchKeyword(){
        String text = textfield.getText();
        String newtext = text.replaceAll(" ", "%20");
        return newtext;
    }
    
    public void setCurrentPageLabel(String page){
        currentpagelabel.setText(page);
    }
    
    public void setTotalPageLabel(String totalpage){
        totalpagelabel.setText(totalpage);
    }
    
    public MyButton getAddButton(){
        return addbutton;
    }
    
    public MyButton getFirstPageButton(){
        return firstbutton;
    }
    
    public MyButton getPreviousPageButton(){
        return previousbutton;
    }
    
    public MyButton getNextPageButton(){
        return nextbutton;
    }
    
    public MyButton getLastPageButton(){
        return lastbutton;
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
    
    public void removeAddButton(){
        leftpanel.remove(addbutton);
    }
    
    public void removeSearchTextField(){
        leftpanel.remove(searchlabel);
        leftpanel.remove(textfield);
    }
    
    public void removePagination(){
        middlepanel.removeAll();
    }
    
}
