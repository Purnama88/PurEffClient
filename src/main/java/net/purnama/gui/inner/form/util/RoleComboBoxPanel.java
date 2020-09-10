/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.form.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.library.MyLabel;
import net.purnama.model.RoleEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class RoleComboBoxPanel extends JPanel{
    
     private ArrayList<RoleEntity> list;
    
    private final MyLabel label, loadinglabel, errorlabel;
    private final JComboBox combobox;
    
    public RoleComboBoxPanel(){
        super(new FlowLayout(FlowLayout.LEFT));
        
        list = new ArrayList<>();
        combobox = new JComboBox();
        
        label = new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_ROLE"), 150);
        combobox.setPreferredSize(new Dimension(250, 25));
        loadinglabel = new MyLabel(new MyImageIcon().getImage("net/purnama/image/Loading_16.gif"));
        
        errorlabel = new MyLabel("", 200);
        
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        setMaximumSize(new Dimension(700, 40));
        
        setAlignmentX(Component.LEFT_ALIGNMENT);
        
        add(label);
        add(combobox);
        add(loadinglabel);
        add(errorlabel);
        
        load();
    }
    
    public final void load(){
        SwingWorker<Boolean, String> refreshworker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                loadinglabel.setVisible(true);
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getActiveRoleList");
                
                return true;
            }
            
            @Override
            protected void process(List<String> chunks) {
                for (String number : chunks) {
                    setErrorLabel(number);
                }
            }
            
            @Override
            protected void done() {
                loadinglabel.setVisible(false);
                if(response == null){
                    setErrorLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                }
                else if(response.getStatus() != 200) {
                    setErrorLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                    + response.getStatus());
                }
                else{
                    errorlabel.setText("");
                    
                    String output = response.getEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        RoleEntity.class));
                        
                        DefaultComboBoxModel model = new DefaultComboBoxModel(list.toArray());

                        combobox.setModel(model);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        refreshworker.execute();
    }
    
    public RoleEntity getComboBoxValue(){
        RoleEntity role = (RoleEntity)combobox.getSelectedItem();
        return role;
    }
    
    public void setComboBoxValue(RoleEntity role){
        ComboBoxModel model = combobox.getModel();
        
        int size = model.getSize();
        
        for(int i = 0; i < size; i++){
            RoleEntity temp = (RoleEntity)model.getElementAt(i);
            if(role.getId().equals(temp.getId())){
                model.setSelectedItem(role);
            }
        }
    }
    
    public void setComboBoxEnabled(boolean value){
        combobox.setEnabled(value);
    }
    
    public void setErrorLabel(String value){
        errorlabel.setText("<HTML><FONT COLOR=RED>" + value + "</FONT></HTML>");
    }
    
    public void refresh(){
        SwingWorker<Boolean, String> refreshworker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            RoleEntity role = getComboBoxValue();
            
            @Override
            protected Boolean doInBackground(){
                
                loadinglabel.setVisible(true);
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getActiveRoleList");
                
                return true;
            }
            
            @Override
            protected void process(List<String> chunks) {
                for (String number : chunks) {
                    setErrorLabel(number);
                }
            }
            
            @Override
            protected void done() {
                loadinglabel.setVisible(false);
                if(response == null){
                    setErrorLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                }
                else if(response.getStatus() != 200) {
                    setErrorLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                    + response.getStatus());
                }
                else{
                    errorlabel.setText("");
                    
                    String output = response.getEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                        RoleEntity.class));
                        
                        DefaultComboBoxModel model = new DefaultComboBoxModel(list.toArray());

                        combobox.setModel(model);
                        
                        setComboBoxValue(role);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        refreshworker.execute();
    }
}