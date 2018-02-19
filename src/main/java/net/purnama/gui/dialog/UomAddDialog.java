/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.dialog;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.SwingWorker;
import net.purnama.gui.inner.form.util.LabelDecimalTextFieldPanel;
import net.purnama.gui.inner.form.util.LabelPanel;
import net.purnama.gui.inner.form.util.LabelTextAreaPanel;
import net.purnama.gui.inner.form.util.LabelTextFieldErrorPanel;
import net.purnama.gui.inner.form.util.StatusPanel;
import net.purnama.gui.library.MyDialog;
import net.purnama.model.UomEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;
import net.purnama.util.GlobalFunctions;
import net.purnama.util.Label_Text;

/**
 *
 * @author Purnama
 */
public class UomAddDialog extends MyDialog implements ActionListener{
    
    protected UomEntity uom, parent;
    
    protected final LabelTextFieldErrorPanel parentpanel, namepanel;
    protected final LabelDecimalTextFieldPanel valuepanel;
    
    protected final StatusPanel statuspanel;
    
    protected final LabelTextAreaPanel notepanel;
    
    protected final LabelPanel remarkpanel, remarkpanel2;
    
    public UomAddDialog(UomEntity parent){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_UOMADD"), 750, 400);
        this.parent = parent;
        
        parentpanel = new LabelTextFieldErrorPanel(GlobalFields.PROPERTIES.getProperty("LABEL_PARENT"),
                parent.getName());
        parentpanel.setTextFieldEditable(false);
        parentpanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        namepanel = new LabelTextFieldErrorPanel(GlobalFunctions.toSuperscript(
                Label_Text.LABEL_REMARK_1, GlobalFields.PROPERTIES.getProperty("LABEL_NAME")), "");
        namepanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        valuepanel = new LabelDecimalTextFieldPanel(GlobalFunctions.toSuperscript(
                Label_Text.LABEL_REMARK_1+Label_Text.LABEL_REMARK_2,
                GlobalFields.PROPERTIES.getProperty("LABEL_VALUE")), 1);
        valuepanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statuspanel = new StatusPanel(GlobalFields.PROPERTIES.getProperty("LABEL_STATUS"));
        statuspanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        notepanel = new LabelTextAreaPanel(GlobalFields.PROPERTIES.getProperty("LABEL_NOTE"), "");
        notepanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        remarkpanel = new LabelPanel(GlobalFields.PROPERTIES.getProperty("REMARK_EMPTY"));
        remarkpanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        remarkpanel2 = new LabelPanel(GlobalFields.PROPERTIES.getProperty("REMARK_NONEDITABLE"));
        remarkpanel2.setAlignmentX(Component.CENTER_ALIGNMENT);
        
        box.add(parentpanel);
        box.add(namepanel);
        box.add(valuepanel);
        box.add(statuspanel);
        box.add(notepanel);
        box.add(remarkpanel);
        box.add(remarkpanel2);
        box.add(submitpanel);
        
        namepanel.setTextFieldActionListener(this);
        valuepanel.setTextFieldActionListener(this);
        
        submitpanel.getSubmitButton().addActionListener(this);
    }
    
    public UomEntity showDialog(){
        setVisible(true);
        return uom;
    }
    
    public boolean validateinput(){
        boolean status = GlobalFields.SUCCESS;
        
        if(namepanel.isTextFieldEmpty()){
            status = GlobalFields.FAIL;
            namepanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_EMPTY"));
        }
        else{
            namepanel.setErrorLabel("");
        }
        
        return status;
    }
    
//    
//    protected final LabelTextFieldErrorPanel parentpanel, namepanel;
//    protected final LabelDecimalTextFieldPanel valuepanel;
//    
//    protected final StatusPanel statuspanel;
//    
//    protected final LabelTextAreaPanel notepanel;
//    
//    protected final UomController uomController;
//    
//    protected final UomEntity parent;
//    protected final UomTableModel uomtablemodel;
//    
//    private final ItemController itemController;
//    
//    private final BuyPriceController buypriceController;
//    private final SellPriceController sellpriceController;
//    
//    public UomAddDialog(UomEntity parent, UomTableModel uomtablemodel){
//        super(GlobalFields.PROPERTIES.getProperty("LABEL_DIALOG_UOMADD"), 400, 460);
//        
//        this.parent = parent;
//        this.uomtablemodel = uomtablemodel;
//        uomController = new UomController();
//        
//        itemController = new ItemController();
//    
//        buypriceController = new BuyPriceController();
//        sellpriceController = new SellPriceController();
//
//        parentpanel = new LabelTextFieldErrorPanel(GlobalFields.PROPERTIES.getProperty("LABEL_PARENT"),
//                parent.getName());
//        parentpanel.setTextFieldEnabled(false);
//        namepanel = new LabelTextFieldErrorPanel(Label_Text.LABEL_REMARK + 
//                GlobalFields.PROPERTIES.getProperty("LABEL_NAME"), "");
//        valuepanel = new LabelDoubleFieldErrorPanel(Label_Text.LABEL_REMARK + 
//                Label_Text.LABEL_REMARK2 + GlobalFields.PROPERTIES.getProperty("LABEL_VALUE"), 1);
//        statuspanel = new StatusPanel();
//        notepanel = new LabelTextAreaPanel(GlobalFields.PROPERTIES.getProperty("LABEL_NOTE"), "");
//        
//        box.add(parentpanel);
//        box.add(namepanel);
//        box.add(valuepanel);
//        box.add(statuspanel);
//        box.add(notepanel);
//        box.add(new LabelPanel(GlobalFields.PROPERTIES.getProperty("REMARK_EMPTY")));
//        box.add(new LabelPanel(GlobalFields.PROPERTIES.getProperty("REMARK_NONEDITABLE")));
//        box.add(submitpanel);
//        
//        submitpanel.getSubmitButton().addActionListener((ActionEvent e) -> {
//            submit();
//        });
//        
//        namepanel.setTextFieldActionListener((ActionEvent e) -> {
//            submit();
//        });
//        
//        valuepanel.setTextFieldActionListener((ActionEvent e) -> {
//            submit();
//        });
//    }
//    
//    public void reset(){
//        namepanel.reset();
//        valuepanel.setTextFieldValue(1);
//        statuspanel.reset();
//        notepanel.reset();
//        
//        namepanel.setErrorLabel(Label_Text.DUMMYLABEL);
//    }
//    
//    public void uomExist(){
//        namepanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("ERROR_EXIST"));
//    }
//    
//    public void submit(){
//        if(validateinput()){
//            try{
//                String newid = uomController.addUom(
//                        namepanel.getTextFieldValue(),
//                        valuepanel.getTextFieldValue(),
//                        statuspanel.getSelectedValue(), parent,
//                        notepanel.getTextAreaValue()
//                );
//
//                UomEntity newuom = uomController.getUom(newid);
//                uomtablemodel.addRow(newuom);
//                
//                int result = JOptionPane.showConfirmDialog(GlobalFields.MAINFRAME,
//                        GlobalFields.PROPERTIES.getProperty("QUESTION_UOMUPDATEPRICE"),
//                        "", JOptionPane.YES_NO_OPTION);
//                
//                if(result == JOptionPane.YES_OPTION){
//                    
//                    List<ItemEntity> itemlist = itemController.getActiveItemList();
//                    
//                    for(ItemEntity item : itemlist){
//                        double buyprice = buypriceController.getBuyPrice(item.getId(), parent.getId());
//                        
//                        buypriceController.addBuyPrice(item, newuom, newuom.getValue()*buyprice);
//                        
//                        double sellprice = sellpriceController.getSellPrice(item.getId(), parent.getId());
//                        
//                        sellpriceController.addSellPrice(item, newuom, newuom.getValue()*sellprice);
//                    }
//                }
//                
//                dispose();
//            }
//            catch(Exception exception){
//                uomExist();
//            }
//        }
//    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(validateinput()){
            SwingWorker<Boolean, String> submitworker = new SwingWorker<Boolean, String>() {
            
                ClientResponse response;
                
                @Override
                protected Boolean doInBackground(){
                    UomEntity uom = new UomEntity();
                    uom.setName(namepanel.getTextFieldValue());
                    uom.setNote(notepanel.getTextAreaValue());
                    uom.setParent(parent);
                    uom.setStatus(statuspanel.getSelectedValue());
                    uom.setValue(valuepanel.getTextFieldValue());
                    
                    response = RestClient.post("addUom", uom);
                    
                    return true;
                }
                
                @Override
                protected void done() {
                    if(response == null){
                        namepanel.setErrorLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                    }
                    else if(response.getStatus() != 200) {
                        String output = response.getEntity(String.class);
                        
                        namepanel.setErrorLabel(output);
                    }
                    else{
                        String output = response.getEntity(String.class);

                        ObjectMapper mapper = new ObjectMapper();

                        try{
                            uom = mapper.readValue(output, UomEntity.class);
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
