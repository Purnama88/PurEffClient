/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.util;

import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentListener;
import net.purnama.gui.library.MyTextArea;

/**
 *
 * @author Purnama
 */
public class DestinationPanel extends JScrollPane{
    
    private final MyTextArea textarea;
    
    public DestinationPanel(String destination, boolean editabled, DocumentListener dl){
        
        textarea = new MyTextArea(destination);
        textarea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        getViewport().add(textarea, null);
        getViewport().setOpaque(false);
        
        setTextAreaEnabled(editabled);
        setDocumentListener(dl);
    }
    
    public String getDestination(){
        return textarea.getText();
    }
    
    public void setDestination(String destination){
        textarea.setText(destination);
    }
    
    public final void setDocumentListener(DocumentListener dl){
        textarea.getDocument().addDocumentListener(dl);
    }
    
    public final void setTextAreaEnabled(boolean value){
        textarea.setEditable(value);
    }
}
