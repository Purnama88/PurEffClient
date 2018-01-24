/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.library;

import java.awt.EventQueue;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JTextField;

/**
 *
 * @author Purnama
 */
public class AutoSuggestComboBox extends JComboBox{
    
    private final ComboKeyHandler ckh;
    private final JTextField field;
    
    public AutoSuggestComboBox(Object [] items){
        super(items);
        
        ckh = new ComboKeyHandler(this);
        
        setEditable(true);
        setSelectedIndex(-1);
        
        field = (JTextField)getEditor().getEditorComponent();
        field.setText("");
        field.addKeyListener(ckh);
    }
    
    public void reset(){
        ckh.reset();
    }
    
    public JTextField getTextField(){
        return field;
    }
}

class ComboKeyHandler extends KeyAdapter {
    private final JComboBox<Object> comboBox;
    private final List<Object> list = new ArrayList<>();
    private boolean shouldHide;
    
    public ComboKeyHandler(JComboBox<Object> combo) {
        super();
        this.comboBox = combo;
        for (int i = 0; i < comboBox.getModel().getSize(); i++) {
            list.add((Object) comboBox.getItemAt(i));
        }
    }
    
    @Override public void keyTyped(final KeyEvent e) {
        EventQueue.invokeLater(() -> {
            String text = ((JTextField) e.getComponent()).getText();
            ComboBoxModel<Object> m;
            if (text.isEmpty()) {
                Object[] array = list.toArray(new Object[list.size()]);
                m = new DefaultComboBoxModel<>(array);
                setSuggestionModel(comboBox, m, "");
                comboBox.hidePopup();
            } else {
                m = getSuggestedModel(list, text);
                if (m.getSize() == 0 || shouldHide) {
                    comboBox.hidePopup();
                } else {
                    setSuggestionModel(comboBox, m, text);
                    comboBox.showPopup();
                }
            }
        });
    }
    
    @Override public void keyPressed(KeyEvent e) {
        JTextField textField = (JTextField) e.getComponent();
        String text = textField.getText();
        shouldHide = false;
        switch (e.getKeyCode()) {
          case KeyEvent.VK_RIGHT:
            for (Object s: list) {
                if (s.toString().startsWith(text)) {
                    textField.setText(s.toString());
                    return;
                }
            }
            break;
//          case KeyEvent.VK_ENTER:
//            if (!list.contains(text)) {
//                list.add(text);
//                Collections.sort(list);
//                setSuggestionModel(comboBox, getSuggestedModel(list, text), text);
//            }
//            shouldHide = true;
//            break;
          case KeyEvent.VK_ESCAPE:
            shouldHide = true;
            break;
          default:
            break;
        }
    }
    
    private static void setSuggestionModel(JComboBox<Object> comboBox, ComboBoxModel<Object> mdl, String str) {
        comboBox.setModel(mdl);
        comboBox.setSelectedIndex(-1);
        ((JTextField) comboBox.getEditor().getEditorComponent()).setText(str);
    }
    
    private static ComboBoxModel<Object> getSuggestedModel(List<Object> list, String text) {
        DefaultComboBoxModel<Object> m = new DefaultComboBoxModel<>();
        for (Object s: list) {
            if (s.toString().toLowerCase().contains(text.toLowerCase())) {
                m.addElement(s);
            }
        }
        return m;
    }
    
    public void reset(){
        Object[] array = list.toArray(new Object[list.size()]);
        ComboBoxModel<Object> m = new DefaultComboBoxModel<>(array);
        setSuggestionModel(comboBox, m, "");
        comboBox.hidePopup();
    }
}