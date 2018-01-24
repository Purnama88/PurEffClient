/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.report;

import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import net.purnama.gui.inner.form.util.SubmitPanel;

/**
 *
 * @author Purnama
 */
public abstract class ReportPanel extends JPanel{
    
    protected final SubmitPanel submitpanel;
    protected final int index;
    
    public ReportPanel(String title, int index){
        super();
        
        this.index = index;
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0), title));
        
        submitpanel = new SubmitPanel();
        
        submitpanel.getSubmitButton().addActionListener((ActionEvent e) -> {
            submit();
        });
    }
    
    protected abstract void submit();
}
