/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.pureffclient;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import net.purnama.gui.login.LoginFrame;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public class Initialization {
    
    public Initialization(){
        Calendar calendar = Calendar.getInstance();
        
        Calendar limit = Calendar.getInstance();
        limit.set(2018, 2, 1);
        
        if(calendar.before(limit)){
            initLanguage();
            initTemp();
            initGui();
        }
    }
    
    public final void initTemp(){
        File file = new File("temp");
        file.mkdir();
    }
    
    public final void initGui(){
        SwingUtilities.invokeLater(() -> {
//            MainFrame mainFrame = new MainFrame();
            LoginFrame loginframe = new LoginFrame();
        });
    }
    
    public final void initLanguage(){
        Properties deflang = new Properties();
        Properties properties = new Properties();
	InputStream input = null;

	try{
            input = getClass().getResourceAsStream("/net/purnama/language/def/default.properties");
            // load a properties file
            deflang.load(input);
            
            String language = deflang.getProperty("DEFAULT_LANGUAGE");
            
            GlobalFields.LANGUAGE = language;
            
            input = getClass().getResourceAsStream("/net/purnama/language/" + language + ".properties");
            properties.load(input);
            
            GlobalFields.PROPERTIES = properties;
	}
        catch (IOException ex){
            ex.printStackTrace();
            
            JOptionPane.showMessageDialog(null, "Cannot find any configuration file", "Error", JOptionPane.ERROR_MESSAGE);
	}
        finally{
            if (input != null){
                try{
                    input.close();
                }
                catch (IOException e) {
                }
            }
	}
    }
}
