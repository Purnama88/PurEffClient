/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.dialog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import net.purnama.gui.library.MyDialog;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.library.MyLabel;
import net.purnama.gui.library.MyTextField;
import net.purnama.model.MenuEntity;
import net.purnama.model.NumberingEntity;
import net.purnama.model.NumberingNameEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class NumberingAddDialog extends MyDialog implements DocumentListener, ActionListener{
    
    protected final JPanel numberingnamepanel, numberingvaluepanel, 
            numberingpreviewpanel, numberingnotepanel, numberingbuttonpanel;
    
    protected final JScrollPane numberingnotescrollpane;
    
    protected final JTextArea numberingnotetextarea;
    
    protected final MyLabel numberingpreviewlabel, loadinglabel;
    
    protected final MyTextField numberingprefixtf ,
            numberingstarttf, numberingendtf, numberingcurrenttf;
    
    protected JComboBox numberingnamecombobox;
    protected ArrayList<NumberingNameEntity> list;
    
    protected int menuid;
    protected boolean status;
    
    protected NumberingEntity numbering;
    
    public NumberingAddDialog(int menuid){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_NUMBERINGADD"), 400, 430);
        
        this.menuid = menuid;
        status = GlobalFields.FAIL;
        
        numberingnamepanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        numberingnamepanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(GlobalFields.PROPERTIES.getProperty("LABEL_NAME")), 
                BorderFactory.createEmptyBorder(0, 0, 5, 0)));
        numberingnamepanel.setPreferredSize(new Dimension(365, 60));
        
        list = new ArrayList<>();
        numberingnamecombobox = new JComboBox();
        
        loadinglabel = new MyLabel(new MyImageIcon().getImage("net/purnama/image/Loading_16.gif"));
        
        numberingnamepanel.add(numberingnamecombobox);
        numberingnamepanel.add(loadinglabel);
        
        box.add(numberingnamepanel);
        
        numberingvaluepanel = new JPanel(new GridLayout(2, 4, 10, 5));
        numberingvaluepanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(GlobalFields.PROPERTIES.getProperty("LABEL_VALUE")), 
                BorderFactory.createEmptyBorder(0, 5, 5, 5)));
        numberingvaluepanel.setPreferredSize(new Dimension(365, 80));
        
        numberingprefixtf = new MyTextField("", 100);
        numberingstarttf = new MyTextField("1", 100);
        numberingendtf = new MyTextField("999999", 100);
        numberingcurrenttf = new MyTextField("1", 100);
        numberingcurrenttf.setEnabled(false);
        
        numberingvaluepanel.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_PREFIX")));
        numberingvaluepanel.add(numberingprefixtf);
        numberingvaluepanel.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_START")));
        numberingvaluepanel.add(numberingstarttf);
        numberingvaluepanel.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_END")));
        numberingvaluepanel.add(numberingendtf);
        numberingvaluepanel.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_CURRENT")));
        numberingvaluepanel.add(numberingcurrenttf);
        
        box.add(numberingvaluepanel);
        
        numberingpreviewpanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        numberingpreviewpanel.setBorder(
                BorderFactory.createTitledBorder(GlobalFields.PROPERTIES.getProperty("LABEL_PREVIEW"))
                );
        numberingpreviewpanel.setPreferredSize(new Dimension(365, 60));
        numberingpreviewlabel = new MyLabel(GlobalFields.PROPERTIES.getProperty("ERROR_EMPTY"));
        numberingpreviewpanel.add(numberingpreviewlabel);
        box.add(numberingpreviewpanel);
        
        numberingnotepanel = new JPanel();
        numberingnotepanel.setPreferredSize(new Dimension(365, 125));
        numberingnotepanel.setBorder(BorderFactory.
                createTitledBorder(GlobalFields.PROPERTIES.getProperty("LABEL_NOTE")));
        
        numberingnotetextarea = new JTextArea();
        
        numberingnotescrollpane = new JScrollPane();
        numberingnotescrollpane.setPreferredSize(new Dimension(315, 90));
        numberingnotescrollpane.setViewportView(numberingnotetextarea);
        numberingnotescrollpane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        numberingnotepanel.add(numberingnotescrollpane);
        
        box.add(numberingnotepanel);
        
        numberingbuttonpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        numberingbuttonpanel.setPreferredSize(new Dimension(365, 40));
        numberingbuttonpanel.add(submitpanel);
        
        box.add(numberingbuttonpanel);
        
        numberingprefixtf.getDocument().addDocumentListener(this);
        numberingstarttf.getDocument().addDocumentListener(this);
        numberingendtf.getDocument().addDocumentListener(this);
        
        numberingprefixtf.addActionListener(this);
        numberingstarttf.addActionListener(this);
        numberingendtf.addActionListener(this);
        
        submitbutton.addActionListener(this);
        
        loadNames();
    }
    
    public final void loadNames(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>(){
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                response = RestClient.get("getActiveNumberingNameList");
                
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
                        list = mapper.readValue(output,
                                TypeFactory.defaultInstance().constructCollectionType(ArrayList.class,
                                         NumberingNameEntity.class));
                        
                        DefaultComboBoxModel model = new DefaultComboBoxModel(list.toArray());

                        numberingnamecombobox.setModel(model);
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    public void reset(){
        numberingprefixtf.setText("");
        numberingstarttf.setText("1");
        numberingendtf.setText("999999");
        numberingcurrenttf.setText("1");
        numberingpreviewlabel.setText(GlobalFields.PROPERTIES.getProperty("ERROR_EMPTY"));
    }
    
    public NumberingEntity showDialog(){
        setVisible(true);
        return numbering;
    }
    
    public NumberingNameEntity getNumberingName(){
        return (NumberingNameEntity)numberingnamecombobox.getSelectedItem();
    }
    
    public String getNumberingPrefix(){
        return numberingprefixtf.getText();
    }
    
    public int getNumberingStart(){
        return Integer.parseInt(numberingstarttf.getText());
    }
    
    public int getNumberingEnd(){
        return Integer.parseInt(numberingendtf.getText());
    }
    
    public int getNumberingCurrent(){
        return Integer.parseInt(numberingcurrenttf.getText());
    }
    
    public String getNumberingNote(){
        return numberingnotetextarea.getText();
    }

    public boolean validateinput(){
        return status;
    }
    
    public void changeSampleLabel(){
        status = GlobalFields.SUCCESS;
        
        try{
            if(numberingprefixtf.isEmpty()){
                numberingpreviewlabel.setText(GlobalFields.PROPERTIES.getProperty("ERROR_EMPTY"));
                status = GlobalFields.FAIL;
            }
            else if(!numberingprefixtf.isLongBetween(4,
                    4)){
                numberingpreviewlabel.setText(GlobalFields.PROPERTIES.getProperty("ERROR_NUMBERINGPREFIX"));
                status = GlobalFields.FAIL;
            }
            else{
                try{
                numberingpreviewlabel.setText(numberingprefixtf.getText() 
                        + String.format(getFormat(), Integer.parseInt(numberingstarttf.getText()))
                        + " - " + numberingprefixtf.getText()
                        + String.format(getFormat(), Integer.parseInt(numberingendtf.getText())));
                }
                catch(Exception exception){

                }

                if(numberingstarttf.isEmpty()){
                    numberingpreviewlabel.setText(GlobalFields.PROPERTIES.getProperty("ERROR_EMPTY"));
                    status = GlobalFields.FAIL;
                }
                else if(!numberingstarttf.isNumeric()){
                    numberingpreviewlabel.setText(GlobalFields.PROPERTIES.getProperty("ERROR_NUMERIC"));
                    status = GlobalFields.FAIL;
                }
                else{
                    numberingcurrenttf.setText(numberingstarttf.getText());
                }

                if(numberingendtf.isEmpty()){
                    numberingpreviewlabel.setText(GlobalFields.PROPERTIES.getProperty("ERROR_EMPTY"));
                    status = GlobalFields.FAIL;
                }
                else if(!numberingendtf.isNumeric()){
                    numberingpreviewlabel.setText(GlobalFields.PROPERTIES.getProperty("ERROR_NUMERIC"));
                    status = GlobalFields.FAIL;
                }

                if(!numberingstarttf.isEmpty() && !numberingendtf.isEmpty() && 
                    Integer.parseInt(numberingstarttf.getText()) >
                    Integer.parseInt(numberingendtf.getText())){
                    numberingpreviewlabel.setText(GlobalFields.PROPERTIES.
                            getProperty("ERROR_NUMBERINGSTARTBIGGER"));
                    status = GlobalFields.FAIL;
                }

                if(!numberingstarttf.isEmpty() && !numberingendtf.isEmpty() && 
                        Integer.parseInt(numberingstarttf.getText()) ==
                        Integer.parseInt(numberingendtf.getText())){
                    numberingpreviewlabel.setText(GlobalFields.PROPERTIES.
                            getProperty("ERROR_NUMBERINGSAMESTARTEND"));
                    status = GlobalFields.FAIL;
                }
            }
        }
        catch(Exception e){
            numberingpreviewlabel.setText("");
            status = GlobalFields.FAIL;
        }
    }
    
    public String getFormat(){
        return "%0" + getLength() + "d";
    }
    
    public int getLength(){
        try{
            return numberingendtf.getText().length();
        }
        catch(Exception e){
            return 1;
        }
    }
    
    @Override
    public void insertUpdate(DocumentEvent e) {
        changeSampleLabel();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
        changeSampleLabel();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {
        changeSampleLabel();
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        if(validateinput()){
            
            SwingWorker<Boolean, String> submitworker = new SwingWorker<Boolean, String>() {
            
                ClientResponse response;
                
                @Override
                protected Boolean doInBackground(){
                    NumberingEntity numbering = new NumberingEntity();
                    numbering.setCurrent(getNumberingCurrent());
                    numbering.setEnd(getNumberingEnd());
                    MenuEntity menu = new MenuEntity();
                    menu.setId(menuid);
                    numbering.setMenu(menu);
                    numbering.setNote(getNumberingNote());
                    numbering.setNumberingname(getNumberingName());
                    numbering.setPrefix(getNumberingPrefix());
                    numbering.setStart(getNumberingStart());
                    numbering.setStatus(false);
                    
                    response = RestClient.post("addNumbering", numbering);
                    
                    return true;
                }
                
                @Override
                protected void done() {
                    if(response == null){
                        numberingpreviewlabel.setText(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                    }
                    else if(response.getStatus() != 200) {
                        String output = response.getEntity(String.class);
                        numberingpreviewlabel.setText(output);
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