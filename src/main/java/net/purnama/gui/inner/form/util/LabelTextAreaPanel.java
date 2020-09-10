/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.form.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentListener;
import net.purnama.gui.library.MyLabel;
import net.purnama.gui.library.MyTextArea;

/**
 *
 * @author Purnama
 */
public class LabelTextAreaPanel extends JPanel{
    
    private final MyLabel label;
    private final MyTextArea textarea;
    private final JScrollPane scrollpane;
    
    public LabelTextAreaPanel(String labelcontent, String textareacontent){
        super(new FlowLayout(FlowLayout.LEFT));
        
        scrollpane = new JScrollPane();
        
        textarea = new MyTextArea(textareacontent, 5, 20);
        
        scrollpane.setViewportView(textarea);
        scrollpane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        
        label = new MyLabel(labelcontent, 150);
        
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        setMaximumSize(new Dimension(700, 105));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        
        add(label);
        add(scrollpane);
    }
    
    public String getLabelValue(){
        return label.getText();
    }
    
    public void setLabelValue(String value){
        label.setText(value);
    }
    
    public String getTextAreaValue(){
        return textarea.getText();
    }
    
    public void setTextAreaValue(String value){
        textarea.setText(value);
    }
    
    public void setTextAreaEditable(boolean status){
        textarea.setEditable(status);
    }
    
    public void reset(){
        textarea.setText("");
    }
    
    public void setDocumentListener(DocumentListener dl){
        textarea.getDocument().addDocumentListener(dl);
    }
}
