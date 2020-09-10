/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.io.IOException;
import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import net.purnama.gui.library.MyButton;
import net.purnama.gui.library.MyDecimalTextField;
import net.purnama.gui.library.MyLabel;
import net.purnama.model.CurrencyEntity;
import net.purnama.model.RateEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class RatePanel extends JPanel{
    
    private final MyDecimalTextField textfield;
    private final MyLabel label;
    
    private final MyButton submitbutton;
    
    private final String currencyid;
    
    public RatePanel(String currencyid){
        super(new FlowLayout(FlowLayout.LEFT));
        
        this.currencyid = currencyid;
        
        setMaximumSize(new Dimension(700, 40));
        setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, Color.BLACK));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        
        textfield = new MyDecimalTextField(1, 200);
        label = new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_RATE"), 100);
        submitbutton = new MyButton(GlobalFields.PROPERTIES.getProperty("LABEL_SUBMIT"));
        
        add(label);
        add(textfield);
        add(submitbutton);
        
        load();
        
        submitbutton.addActionListener((ActionEvent e) -> {
            addRate();
        });
    }
    
    public final void load(){
        SwingWorker<Boolean, String> worker= new SwingWorker<Boolean, String>(){
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getLastRate?currencyid=" + currencyid);
                
                return true;
            }
            
            @Override
            protected void done() {
                if(response == null){
                }
                else if(response.getStatus() != 200) {
                }
                else{
                    String output = response.getEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        RateEntity rate = mapper.readValue(output, RateEntity.class);
                        
                        textfield.setValue(rate.getValue());
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public final void addRate(){
        SwingWorker<Boolean, String> submitworker = new SwingWorker<Boolean, String>(){
            ClientResponse cr;

            @Override
            protected Boolean doInBackground(){

                System.out.println("test");

                CurrencyEntity currency = new CurrencyEntity();
                currency.setId(currencyid);

                RateEntity rate = new RateEntity();
                rate.setCurrency(currency);
                rate.setNote("");
                rate.setValue(((Number)textfield.getValue()).doubleValue());
                rate.setStatus(true);

                cr = RestClient.post("addRate", rate);

                return true;
            }

            @Override
            protected void done() {
                if(cr == null){
                    System.out.println("null");
                }
                else if(cr.getStatus() != 200) {
                    System.out.println("error");
                }
                else{
                    String output = cr.getEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        RateEntity rate = mapper.readValue(output, RateEntity.class);

                        textfield.setValue(rate.getValue());
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        submitworker.execute();
    }
}
