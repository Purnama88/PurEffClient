/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.purnama.gui.inner.home.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import net.purnama.controller.ExportController;
import net.purnama.model.ItemWarehouseEntity;
import net.purnama.model.SellPriceEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author purnama
 */
public class ExportPanel extends JPanel{
    
    private final FileChooserPanel filechooserpanel, filechooserpanel2, filechooserpanel3,
            filechooserpanel4, filechooserpanel5;
    
    public ExportPanel(){
        super();
        
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        
        filechooserpanel5 = new FileChooserPanel(GlobalFields.PROPERTIES.getProperty("LABEL_EXPORT_PARTNER"), 
                FileChooserPanel.SAVE);
        filechooserpanel4 = new FileChooserPanel(GlobalFields.PROPERTIES.getProperty("LABEL_EXPORT_ITEMGROUP"), 
                FileChooserPanel.SAVE);
        filechooserpanel =  new FileChooserPanel(GlobalFields.PROPERTIES.getProperty("LABEL_EXPORT_ITEM"), 
                FileChooserPanel.SAVE);
        filechooserpanel3 =  new FileChooserPanel(GlobalFields.PROPERTIES.getProperty("LABEL_EXPORT_SELLPRICE"), 
                FileChooserPanel.SAVE);
        filechooserpanel2 =  new FileChooserPanel(GlobalFields.PROPERTIES.getProperty("LABEL_EXPORT_STOCK"), 
                FileChooserPanel.SAVE);
        
        add(filechooserpanel5);
        add(filechooserpanel4);
        add(filechooserpanel);
        add(filechooserpanel3);
        add(filechooserpanel2);
        
        filechooserpanel4.getSubmitButton().addActionListener((ActionEvent e) ->{
            ExportController exportController = new ExportController(filechooserpanel4.getFilePath());
            boolean result = exportController.exportItemGroupTemplateToExcel();
            
            if(result){
                JOptionPane.showMessageDialog(null, GlobalFields.
                            PROPERTIES.getProperty("NOTIFICATION_EXPORT_SUCCESS"), "",
                            JOptionPane.INFORMATION_MESSAGE);
                filechooserpanel4.clearPath();
            }
            else{
                JOptionPane.showMessageDialog(null, GlobalFields.
                            PROPERTIES.getProperty("NOTIFICATION_EXPORT_FAIL"), "",
                            JOptionPane.ERROR_MESSAGE);
            }
        });
        
        filechooserpanel5.getSubmitButton().addActionListener((ActionEvent e) ->{
            ExportController exportController = new ExportController(filechooserpanel5.getFilePath());
            boolean result = exportController.exportPartnerTemplateToExcel();
            
            if(result){
                JOptionPane.showMessageDialog(null, GlobalFields.
                            PROPERTIES.getProperty("NOTIFICATION_EXPORT_SUCCESS"), "",
                            JOptionPane.INFORMATION_MESSAGE);
                
                filechooserpanel5.clearPath();
            }
            else{
                JOptionPane.showMessageDialog(null, GlobalFields.
                            PROPERTIES.getProperty("NOTIFICATION_EXPORT_FAIL"), "",
                            JOptionPane.ERROR_MESSAGE);
            }
        });
        
        filechooserpanel.getSubmitButton().addActionListener((ActionEvent e) ->{
            ExportController exportController = new ExportController(filechooserpanel.getFilePath());
            boolean result = exportController.exportItemTemplateToExcel();
            
            if(result){
                JOptionPane.showMessageDialog(null, GlobalFields.
                            PROPERTIES.getProperty("NOTIFICATION_EXPORT_SUCCESS"), "",
                            JOptionPane.INFORMATION_MESSAGE);
                
                filechooserpanel.clearPath();
            }
            else{
                JOptionPane.showMessageDialog(null, GlobalFields.
                            PROPERTIES.getProperty("NOTIFICATION_EXPORT_FAIL"), "",
                            JOptionPane.ERROR_MESSAGE);
            }
        });
        
        filechooserpanel2.getSubmitButton().addActionListener((ActionEvent e) ->{
            
            SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
                ClientResponse clientresponse;
                List<ItemWarehouseEntity> list;
                
                @Override
                protected Boolean doInBackground(){
                
                    
                    clientresponse = RestClient.get("getItemWarehouseList");
                    
                    return true;
                }
                
                
                @Override
                protected void done() {
                    if(clientresponse == null){
                        JOptionPane.showMessageDialog(null, GlobalFields.
                                            PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"), "",
                                            JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(clientresponse.getStatus() != 200) {
                    }
                    else{
                        String output = clientresponse.getEntity(String.class);

                        ObjectMapper mapper = new ObjectMapper();

                        try{
                            list = mapper.readValue(output,
                                    TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                            ItemWarehouseEntity.class));

                            ExportController exportController = new ExportController(filechooserpanel2.getFilePath());
                            boolean result = exportController.exportItemStockTemplateToExcel(list);
                            
                            if(result){
                                JOptionPane.showMessageDialog(null, GlobalFields.
                                            PROPERTIES.getProperty("NOTIFICATION_EXPORT_SUCCESS"), "",
                                            JOptionPane.INFORMATION_MESSAGE);
                                
                                filechooserpanel2.clearPath();
                            }
                            else{
                                JOptionPane.showMessageDialog(null, GlobalFields.
                                            PROPERTIES.getProperty("NOTIFICATION_EXPORT_FAIL"), "",
                                            JOptionPane.ERROR_MESSAGE);
                            }
                        }
                        catch(IOException e){
                            JOptionPane.showMessageDialog(null, GlobalFields.
                                            PROPERTIES.getProperty("NOTIFICATION_EXPORT_FAIL"), "",
                                            JOptionPane.ERROR_MESSAGE);
                        }
                    }
                };
            };
            
            worker.execute();
            
        });
        
        filechooserpanel3.getSubmitButton().addActionListener((ActionEvent e) ->{
            
            SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            
                ClientResponse clientresponse;
                List<SellPriceEntity> list;
                
                @Override
                protected Boolean doInBackground(){
                    clientresponse = RestClient.get("getSellPriceList");
                    
                    return true;
                }
                
                
                @Override
                protected void done() {
                    if(clientresponse == null){
                        JOptionPane.showMessageDialog(null, GlobalFields.
                                            PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"), "",
                                            JOptionPane.INFORMATION_MESSAGE);
                    }
                    else if(clientresponse.getStatus() != 200) {
                    }
                    else{
                        String output = clientresponse.getEntity(String.class);

                        ObjectMapper mapper = new ObjectMapper();

                        try{
                            list = mapper.readValue(output,
                                    TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                            SellPriceEntity.class));
                            
                            for(SellPriceEntity sellprice : list){
                                System.out.println(sellprice.getItem() + " " + sellprice.getUom()
                                + " " + sellprice.getValue());
                            }

//                            ExportController exportController = new ExportController(filechooserpanel3.getFilePath());
//                            boolean result = exportController.exportItemSellPriceTemplateToExcel(list);
//                            
//                            if(result){
//                                JOptionPane.showMessageDialog(null, GlobalFields.
//                                            PROPERTIES.getProperty("NOTIFICATION_EXPORT_SUCCESS"), "",
//                                            JOptionPane.INFORMATION_MESSAGE);
//                                
//                                filechooserpanel3.clearPath();
//                            }
//                            else{
//                                JOptionPane.showMessageDialog(null, GlobalFields.
//                                            PROPERTIES.getProperty("NOTIFICATION_EXPORT_FAIL"), "",
//                                            JOptionPane.ERROR_MESSAGE);
//                            }
                        }
                        catch(IOException e){
                            JOptionPane.showMessageDialog(null, GlobalFields.
                                            PROPERTIES.getProperty("NOTIFICATION_EXPORT_FAIL"), "",
                                            JOptionPane.ERROR_MESSAGE);
                        }
                    }
                };
            };
            
            worker.execute();
            
//            SwingWorker.StateValue.
        });
    }
}
