/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.dialog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.Calendar;
import javax.swing.SwingWorker;
import net.purnama.model.NumberingEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class NumberingEditDialog extends NumberingAddDialog{
    
    public NumberingEditDialog(int menuid, String numberingid){
        super(menuid);
        
        this.setTitle(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_NUMBERINGEDIT"));
        
        loadNumbering(numberingid);
        
        numberingprefixtf.setEditable(false);
        numberingstarttf.setEditable(false);
    }
    
    public final void loadNumbering(String numberingid){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getNumbering?id=" + numberingid);
                
                return true;
            }
            
            @Override
            protected void done() {
                loadinglabel.setVisible(false);
                if(response == null){
                }
                else if(response.getStatus() != 200) {
                }
                else{
                    String output = response.getEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        numbering = mapper.readValue(output, NumberingEntity.class);
                        
                        System.out.println(numbering.getNumberingname().getEnd().before(Calendar.getInstance()));
                        
                        numberingprefixtf.setText(numbering.getPrefix());
                        numberingstarttf.setText(numbering.getStart() + "");
                        numberingendtf.setText(numbering.getEnd() + "");
                        numberingcurrenttf.setText(numbering.getCurrent() + "");
                        numberingnotetextarea.setText(numbering.getNote());
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(validateinput()){
            
            SwingWorker<Boolean, String> submitworker = new SwingWorker<Boolean, String>() {
            
                ClientResponse response;
                
                @Override
                protected Boolean doInBackground(){
                    numbering.setEnd(getNumberingEnd());
                    numbering.setNote(getNumberingNote());
                    numbering.setNumberingname(getNumberingName());
                    
                    response = RestClient.put("updateNumbering", numbering);
                    
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
                            numbering = mapper.readValue(output, NumberingEntity.class);
                            dispose();
                        }
                       catch(IOException e){

                        }
                    }
                }
            };
            
            submitworker.execute();
        }
    }
}
