/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.form.util;

import com.sun.jersey.api.client.ClientResponse;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import net.purnama.gui.dialog.NewPasswordDialog;
import net.purnama.gui.library.MyButton;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.library.MyLabel;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class ResetPasswordPanel extends JPanel{
    
    private final MyLabel label;
    private final MyButton resetbutton;
    
    private final String userid;
    
    public ResetPasswordPanel(String userid){
        
        super(new FlowLayout(FlowLayout.LEFT));
        
        this.userid = userid;
        
        setMaximumSize(new Dimension(700, 40));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        
        label = new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_PASSWORD"), 150);
        
        resetbutton  = new MyButton(new MyImageIcon().getImage("net/purnama/image/Reset_16.png"),
                GlobalFields.PROPERTIES.getProperty("LABEL_RESET"), 100, 24);
        
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        add(label);
        add(resetbutton);
        
        resetbutton.addActionListener((ActionEvent e) -> {
            int result = JOptionPane.showConfirmDialog(null, GlobalFields.
                PROPERTIES.getProperty("QUESTION_RESETPASSWORD"),
                "", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION){
                    reset(userid);
                }
        });
    }
    
    private void reset(String userid){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("resetUserPassword?id=" + userid);
                
                return true;
            }
            
            @Override
            protected void done() {
                if(response == null){
                }
                else if(response.getStatus() != 200) {
                    String output = response.getEntity(String.class);
                    JOptionPane.showMessageDialog(null, 
                            output, GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                            + response.getStatus(), 
                            JOptionPane.ERROR_MESSAGE);
                    
                }
                else{
                    String output = response.getEntity(String.class);
                    
                    new NewPasswordDialog(output).showDialog();
                }
            }
        };
        
        worker.execute();
    }
}
