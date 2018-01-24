/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.detail;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.io.IOException;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import net.purnama.gui.inner.detail.util.SelectableLabelContentPanel;
import net.purnama.gui.inner.form.PartnerEdit;
import net.purnama.gui.inner.home.PartnerHome;
import net.purnama.gui.main.MainTabbedPane;
import net.purnama.model.PartnerEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class PartnerDetail extends DetailPanel{
    
    private PartnerEntity partner;
    
    private final SelectableLabelContentPanel idpanel, codepanel, namepanel, contactnamepanel,
            typepanel, addresspanel,
            phonenumberpanel, phonenumber2panel, faxnumberpanel, mobilenumberpanel, emailpanel,
            balancepanel, maxbalancepanel, maxdiscountpanel, paymentduepanel, statuspanel;
    
    private final String id;
    
    public PartnerDetail(String id){
        super(GlobalFields.PROPERTIES.getProperty("LABEL_PANEL_PARTNERDETAIL"));
        
        this.id = id;
        
        idpanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_ID"),
                "");
        namepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_NAME"),
                "");
        codepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_CODE"),
                "");
        contactnamepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_CONTACTNAME"),
                "");
        typepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_PARTNERTYPE"),
                "");
        addresspanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_ADDRESS"),
                "");
        phonenumberpanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_PHONENUMBER"),
                "");
        phonenumber2panel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_PHONENUMBER"),
                "");
        faxnumberpanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_FAXNUMBER"),
                "");
        mobilenumberpanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MOBILENUMBER"),
                "");
        emailpanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_EMAIL"),
                "");
        balancepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_BALANCE"),
                "");
        maxbalancepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MAXBALANCE"),
                "");
        maxdiscountpanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_MAXDISCOUNT"),
                "");
        paymentduepanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_PAYMENTDUE"),
                "");
        statuspanel = new SelectableLabelContentPanel(GlobalFields.PROPERTIES.getProperty("LABEL_STATUS"),
                "");
        
//        detailpanel.add(idpanel);
        detailpanel.add(codepanel);
        detailpanel.add(namepanel);
        detailpanel.add(contactnamepanel);
        detailpanel.add(typepanel);
        detailpanel.add(addresspanel);
        detailpanel.add(phonenumberpanel);
        detailpanel.add(phonenumber2panel);
        detailpanel.add(faxnumberpanel);
        detailpanel.add(mobilenumberpanel);
        detailpanel.add(emailpanel);
        detailpanel.add(balancepanel);
        detailpanel.add(maxbalancepanel);
        detailpanel.add(maxdiscountpanel);
        detailpanel.add(paymentduepanel);
        detailpanel.add(statuspanel);
        
        load();
    }

    @Override
    public final void load(){
        SwingWorker<Boolean, String> worker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
            
            @Override
            protected Boolean doInBackground(){
                
                upperpanel.showProgressBar();
                
                publish(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_CONNECTING"));
                
                response = RestClient.get("getPartner?id=" + id);
               
                return true;
                
            }
            
            @Override
            protected void process(List<String> chunks) {
                for (String number : chunks) {
                upperpanel.setNotifLabel(number);
                }
            }
            
            @Override
            protected void done() {
                upperpanel.hideProgressBar();
                
                if(response == null){
                    upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"));
                }
                else if(response.getStatus() != 200) {
                    upperpanel.setNotifLabel(GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                    + response.getStatus());
                }
                else{
                    upperpanel.setNotifLabel("");
                    
                    String output = response.getEntity(String.class);

                    ObjectMapper mapper = new ObjectMapper();

                    try{
                        partner = mapper.readValue(output, PartnerEntity.class);
                        
                        idpanel.setContentValue(partner.getId());
                        codepanel.setContentValue(partner.getCode());
                        namepanel.setContentValue(partner.getName());
                        contactnamepanel.setContentValue(partner.getContactname());
                        typepanel.setContentValue(partner.getPartnertype().getName());
                        addresspanel.setContentValue(partner.getAddress());
                        phonenumberpanel.setContentValue(partner.getPhonenumber());
                        phonenumber2panel.setContentValue(partner.getPhonenumber2());
                        faxnumberpanel.setContentValue(partner.getFaxnumber());
                        mobilenumberpanel.setContentValue(partner.getMobilenumber());
                        emailpanel.setContentValue(partner.getEmail());
                        balancepanel.setContentValue(partner.getFormattedBalance2());
                        maxbalancepanel.setContentValue(partner.getFormattedMaximumbalance());
                        maxdiscountpanel.setContentValue(partner.getFormattedMaximumdiscount());
                        paymentduepanel.setContentValue(partner.getPaymentdue()+"");
                        statuspanel.setContentValue(partner.isStatus());
                        notepanel.setNote(partner.getNote());
                        
                        upperpanel.setStatusLabel(partner.getFormattedLastmodified());
                    }
                    catch(IOException e){

                    }
                }
            }
        };
        
        worker.execute();
    }
    
    @Override
    protected void home() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new PartnerHome());
    }

    @Override
    protected void edit() {
        MainTabbedPane tabbedPane = (MainTabbedPane)SwingUtilities.
                getAncestorOfClass(MainTabbedPane.class, this);
        
        tabbedPane.changeTabPanel(getIndex(), new PartnerEdit(id));
    }
    
    @Override
    public void refresh(){
        load();
    }
}
