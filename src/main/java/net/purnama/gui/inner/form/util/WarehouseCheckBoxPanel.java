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
import java.awt.GridLayout;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.library.MyLabel;
import net.purnama.model.WarehouseEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class WarehouseCheckBoxPanel extends JPanel{
    
    private final MyLabel label, loadinglabel, errorlabel;;
    
    private final JPanel panel;
    
    private final List<JCheckBox> checkboxlist;
    
    private ArrayList<WarehouseEntity> list;
    
    public WarehouseCheckBoxPanel(){
        super(new FlowLayout(FlowLayout.LEFT));
        
        panel = new JPanel();
        panel.setMaximumSize(new Dimension(250, 25));
        
        label = new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_WAREHOUSE"), 150);
        loadinglabel = new MyLabel(new MyImageIcon().getImage("net/purnama/image/Loading_16.gif"));
        errorlabel = new MyLabel("", 200);
        
        checkboxlist = new ArrayList<>();
        
        add(label);
        add(panel);
        add(loadinglabel);
        add(errorlabel);
        
        setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        setMaximumSize(new Dimension(700, 200));
        setAlignmentX(Component.LEFT_ALIGNMENT);
        
        load();
    }
    
    public final void load(){
        
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                loadinglabel.setVisible(true);
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getActiveWarehouseList");
                
                return true;
            }
            
            @Override
            protected void process(List<String> chunks) {
                for (String number : chunks) {
                    errorlabel.setText(number);
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
                                        WarehouseEntity.class));
                        
                        panel.setLayout(new GridLayout((list.size()+2)/2, 2));
                        panel.removeAll();
                        
                        list.stream().forEach((o) -> {
                            WarehouseEntity warehouse = (WarehouseEntity)o;

                            JCheckBox jcb = new JCheckBox(warehouse.getCode());
                            jcb.setActionCommand(warehouse.getId());
                            jcb.setToolTipText("<HTML>" + warehouse.getName() + "<BR/>" + 
                                    warehouse.getAddress() + "</HTML>");
                            checkboxlist.add(jcb);
                            panel.add(jcb);
                        });
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public Set<WarehouseEntity> getCheckBoxValues(){
        
        Set<WarehouseEntity> set = new HashSet<>();
        
        checkboxlist.stream().filter((jcb) -> (jcb.isSelected())).forEach((jcb) -> {
            WarehouseEntity warehouse = new WarehouseEntity();
            warehouse.setId(jcb.getActionCommand());
            set.add(warehouse);
        });
        
        return set;
    }
    
    public void setCheckBoxValues(Set<WarehouseEntity> warehouses){
        for(JCheckBox jcb : checkboxlist){
            for(WarehouseEntity warehouse : warehouses){
                if(warehouse.getId().equals(jcb.getActionCommand())){
                    jcb.setSelected(true);
                }
            }
        }
    }
    
    public void setCheckBoxEnabled(boolean status){
        for(JCheckBox jcb : checkboxlist){
            jcb.setEnabled(status);
        }
    }

    public void setErrorLabel(String value){
        errorlabel.setText("<HTML><FONT COLOR=RED>" + value + "</FONT></HTML>");
    }
    
    public final void refresh(){
        
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
            ClientResponse response;
            Set<WarehouseEntity> warehouseset = getCheckBoxValues();
            
            @Override
            protected Boolean doInBackground(){
                
                loadinglabel.setVisible(true);
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getActiveWarehouseList");
                
                return true;
            }
            
            @Override
            protected void process(List<String> chunks) {
                for (String number : chunks) {
                    errorlabel.setText(number);
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
                                        WarehouseEntity.class));
                        
                        panel.setLayout(new GridLayout((list.size()+2)/2, 2));
                        panel.removeAll();
                        
                        list.stream().forEach((o) -> {
                            WarehouseEntity warehouse = (WarehouseEntity)o;

                            JCheckBox jcb = new JCheckBox(warehouse.getCode());
                            jcb.setActionCommand(warehouse.getId());
                            jcb.setToolTipText("<HTML>" + warehouse.getName() + "<BR/>" + 
                                    warehouse.getAddress() + "</HTML>");
                            checkboxlist.add(jcb);
                            panel.add(jcb);
                        });
                        
                        setCheckBoxValues(warehouseset);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
}

