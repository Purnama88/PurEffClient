/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.ClientResponse;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;
import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.WindowConstants;
import net.purnama.gui.library.MyButton;
import net.purnama.gui.library.MyFrame;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.library.MyLabel;
import net.purnama.gui.library.MyPasswordField;
import net.purnama.gui.library.MyTextField;
import net.purnama.gui.login.util.WarehouseComboBoxPanel;
import net.purnama.gui.main.MainFrame;
import net.purnama.model.LoginEntity;
import net.purnama.model.UserEntity;
import net.purnama.model.WarehouseEntity;
import net.purnama.rest.RestClient;
import net.purnama.util.GlobalFields;
import org.apache.commons.io.FilenameUtils;

/**
 *
 * @author Purnama
 */
public class LoginFrame extends MyFrame implements ActionListener{
    
    private final Box box;
    
    private final JPanel usernamepanel, passwordpanel, buttonpanel, logopanel,
            actionpanel, languagepanel;
    
    private final MyButton loginbutton, helpbutton, refreshbutton;
    
    private final JComboBox jcb;
    
    private final MyLabel logolabel;
    
    private final MyTextField textfield;
    
    private final MyPasswordField passwordfield;
    
    private final WarehouseComboBoxPanel warehousepanel;
    
    public LoginFrame(){
        super("HelloPOS");
        
        setMinimumSize(new Dimension(400, 350));
        
        actionpanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        helpbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Help_24.png"), 35, 35);
        helpbutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_HELP"));
        refreshbutton = new MyButton(new MyImageIcon().getImage("net/purnama/image/Refresh_24.png"), 35, 35);
        refreshbutton.setToolTipText(GlobalFields.PROPERTIES.getProperty("TOOLTIP_REFRESH"));
        
        languagepanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        languagepanel.setPreferredSize(new Dimension(300, 30));
        
        jcb = new JComboBox();
        
        try{
            URI uri = getClass().getClassLoader().getResource("net/purnama/language").toURI();
            Path myPath;
            if (uri.getScheme().equals("jar")) {
                FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.<String, Object>emptyMap());
                myPath = fileSystem.getPath("net/purnama/language");
            } 
            else {
                myPath = Paths.get(uri);
            }
            Stream<Path> walk = Files.walk(myPath, 1);
            
            for (Iterator<Path> it = walk.iterator(); it.hasNext();){
                Path temp = (Path)(it.next());
                File file = new File(temp.toString());
                if(file.isFile()){
                    jcb.addItem(FilenameUtils.removeExtension(temp.getFileName().toString()));
                }
            }
        }
        catch(URISyntaxException | IOException e){
            
        }
        
//        ClassLoader classLoader = getClass().getClassLoader();
//        URL url = classLoader.getResource("net/purnama/language");
//        
//        try {
//            System.out.println(classLoader.getResourceAsStream("net/purnama/language").read());
//        } catch (IOException ex) {
//            Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        File folder = new File(url.getPath());
//        System.out.println(folder.getAbsolutePath());
//        
//        File[] listOfFiles = folder.listFiles();
//
//        for (File listOfFile : listOfFiles) {
//            if (listOfFile.isFile()) {
//                String fileNameWithOutExt = FilenameUtils.removeExtension(listOfFile.getName());
//                jcb.addItem(fileNameWithOutExt);
//            }
//        }
        
        jcb.setSelectedItem(GlobalFields.LANGUAGE);
        
        setLocationToCenter();
        
        box = Box.createVerticalBox();
        
        languagepanel.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_LANGUAGE")));
        languagepanel.add(jcb);
        actionpanel.add(languagepanel);
        
        actionpanel.add(refreshbutton);
        actionpanel.add(helpbutton);
        
        logopanel = new JPanel();
        
        logolabel = new MyLabel(new MyImageIcon().getImage("net/purnama/image/Login_128.png"));
        
        logopanel.add(logolabel);
        
        warehousepanel = new WarehouseComboBoxPanel();
        
        usernamepanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        textfield = new MyTextField("", 150);
        textfield.addActionListener(this);
        usernamepanel.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_USERNAME")));
        usernamepanel.add(textfield);
        
        passwordpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        passwordfield = new MyPasswordField("", 150);
        passwordfield.addActionListener(this);
        passwordpanel.add(new MyLabel(GlobalFields.PROPERTIES.getProperty("LABEL_PASSWORD")));
        passwordpanel.add(passwordfield);
        
        buttonpanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        loginbutton = new MyButton(GlobalFields.PROPERTIES.getProperty("LABEL_SUBMIT"));
        loginbutton.addActionListener(this);
        buttonpanel.add(loginbutton);
        
        box.add(actionpanel);
        box.add(logopanel);
        box.add(warehousepanel);
        box.add(usernamepanel);
        box.add(passwordpanel);
        box.add(buttonpanel);
        
        add(box);
        
        setResizable(false);
        display();
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        pack();
        setLocationToCenter();
        
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent ce) {
              textfield.requestFocusInWindow();
            }
        });
        
        refreshbutton.addActionListener((ActionEvent e) -> {
            warehousepanel.load();
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        SwingWorker<Boolean, String> numworker = new SwingWorker<Boolean, String>() {
            ClientResponse response;
        
            @Override
            protected Boolean doInBackground(){
                WarehouseEntity warehouse = warehousepanel.getComboBoxValue();
                LoginEntity le = new LoginEntity();
                le.setUsername(textfield.getText());
                le.setPassword(passwordfield.getText());
                le.setWarehouseid(warehouse.getId());
                
                response = RestClient.postNonApi("login", le);
                
                return true;
            }
            
            @Override
            protected void done() {
                if(response == null){
                    passwordfield.setText("");
                    JOptionPane.showMessageDialog(null, GlobalFields.
                            PROPERTIES.getProperty("NOTIFICATION_TIMEDOUT"), "",
                            JOptionPane.ERROR_MESSAGE);
                }
                else if(response.getStatus() != 200) {
                    passwordfield.setText("");
                    String output = response.getEntity(String.class);
                    JOptionPane.showMessageDialog(null, 
                            output, GlobalFields.PROPERTIES.getProperty("NOTIFICATION_HTTPERROR")
                                            + response.getStatus(), 
                            JOptionPane.ERROR_MESSAGE);

                }
                else{
                    String output = response.getEntity(String.class);
                    ObjectMapper mapper = new ObjectMapper();
                    try {
                        GlobalFields.USER = mapper.readValue(output, UserEntity.class);
                        GlobalFields.ROLE = GlobalFields.USER.getRole();
                        String header = response.getHeaders().getFirst("Authorization");
                        String authToken = header.substring(7);
                        GlobalFields.TOKEN = authToken;
                        System.out.println(authToken);
                    } catch (IOException ex) {
                        Logger.getLogger(LoginFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    SwingUtilities.invokeLater(() -> {
                        MainFrame mainFrame = new MainFrame();
                        GlobalFields.MAINFRAME = mainFrame;
                    });

                    dispose();
                }
            }
        };
        
        numworker.execute();
    }
}
