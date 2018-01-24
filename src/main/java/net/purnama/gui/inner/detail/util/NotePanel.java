/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.util;

import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentListener;

/**
 *
 * @author Purnama
 */
public class NotePanel extends JScrollPane{
    
    private final JTextArea textarea;
    
    public NotePanel(String note, boolean value, DocumentListener dl){
        super();
        
        textarea = new JTextArea(note);
        textarea.setLineWrap(true);
        textarea.setWrapStyleWord(true);
        textarea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textarea.setOpaque(false);
        
        getViewport().add(textarea, null);
        getViewport().setOpaque(false);
        setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        setTextAreaEnabled(value);
        setDocumentListener(dl);
    }
    
    public String getNote(){
        return textarea.getText();
    }
    
    public void setNote(String note){
        textarea.setText(note);
    }
    
    public final void setDocumentListener(DocumentListener dl){
        textarea.getDocument().addDocumentListener(dl);
    }
    
    public final void setTextAreaEnabled(boolean value){
        textarea.setEditable(value);
    }
}
