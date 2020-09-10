/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package net.purnama.gui.inner.home;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import net.purnama.gui.inner.home.util.UpperPanel;
import net.purnama.gui.library.MyImageIcon;
import net.purnama.gui.library.MyPanel;
import net.purnama.gui.library.MyTable;
import net.purnama.util.GlobalFields;

/**
 *
 * @author Purnama
 */
public abstract class HomePanel extends MyPanel{

    protected UpperPanel upperpanel;
    
    protected final MyTable table;
    
    protected TableRowSorter<TableModel> sorter;
    
    protected final JScrollPane scrollpane;
    
    protected int page = 1, numofitem = 1;
    
    protected final JPopupMenu popupmenu;
    
    protected final JMenuItem menuitemedit, menuitemdetail, menuitemcopy, menuitemnewtab;
    
    public HomePanel(String name) {
        super(name);
        
        table = new MyTable();
        
        scrollpane = new JScrollPane();
        scrollpane.setBorder(null);
        scrollpane.getViewport().add(table);
        scrollpane.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        upperpanel = new UpperPanel();
        upperpanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        
        add(upperpanel);
        add(scrollpane);
        
        popupmenu = new JPopupMenu();
        
        menuitemedit = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_EDIT"),
                new MyImageIcon().getImage("net/purnama/image/Edit_16.png"));
        menuitemdetail = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_DETAIL"),
                new MyImageIcon().getImage("net/purnama/image/Detail_16.png"));
        menuitemcopy = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_COPY"),
                new MyImageIcon().getImage("net/purnama/image/Copy_16.png"));
        menuitemnewtab = new JMenuItem(GlobalFields.PROPERTIES.getProperty("LABEL_NEWTAB"),
                new MyImageIcon().getImage("net/purnama/image/NewTab_16.png"));
        
        popupmenu.add(menuitemcopy);
        popupmenu.addSeparator();
        popupmenu.add(menuitemdetail);
        popupmenu.add(menuitemnewtab);
        popupmenu.addSeparator();
        popupmenu.add(menuitemedit);
        
        table.setComponentPopupMenu(popupmenu);
        
        table.addMouseListener(new MouseAdapter(){
            @Override
            public void mousePressed(MouseEvent e){
                Point p = e.getPoint();
		if(SwingUtilities.isLeftMouseButton(e)){
                    if(e.getClickCount() ==2){
                        detail();
                    }
		}
		else if(SwingUtilities.isRightMouseButton(e)){
                    int rowNumber = table.rowAtPoint( p );
                    ListSelectionModel model = table.getSelectionModel();
                    model.setSelectionInterval(rowNumber, rowNumber);
		}
            }
        });
        
        menuitemcopy.addActionListener((ActionEvent e) -> {
            try{
                StringSelection stringselection = new StringSelection(table.
                        getValueAt(table.getSelectedRow(), table.getSelectedColumn()).toString());

                Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                clipboard.setContents(stringselection,
                        null);
            }
            catch(HeadlessException exp){
                exp.printStackTrace();
            }
        });
        
        upperpanel.getAddButton().addActionListener((ActionEvent e) -> {
            add();
        });
        
        menuitemdetail.addActionListener((ActionEvent e) -> {
            detail();
        });
        
        menuitemnewtab.addActionListener((ActionEvent e) -> {
            openinnewtab();
        });
        
        menuitemedit.addActionListener((ActionEvent e) -> {
            edit();
        });
        
        upperpanel.getFirstPageButton().addActionListener((ActionEvent e) -> {
            firstpage();
        });
        
        upperpanel.getPreviousPageButton().addActionListener((ActionEvent e) -> {
            previouspage();
        });
        
        upperpanel.getNextPageButton().addActionListener((ActionEvent e) -> {
            nextpage();
        });
        
        upperpanel.getLastPageButton().addActionListener((ActionEvent e) -> {
            lastpage();
        });
        
        upperpanel.getSearchTextField().getDocument().addDocumentListener(
        new DocumentListener() {
            @Override
            public void changedUpdate(DocumentEvent e) {
                filter();
            }
            @Override
            public void insertUpdate(DocumentEvent e) {
                filter();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                filter();
            }
        });
        
    }
    
    @Override
    public void refresh(){
        filter();
    }
    
    protected abstract void filter();
    protected abstract void loaddata();
    protected abstract void openinnewtab();
    protected abstract void detail();
    protected abstract void edit();
    protected abstract void add();
    
    protected final void firstpage() {
        if(page != 1){
            page = 1;
            upperpanel.setCurrentPageLabel(page + "");
            loaddata();
        }
    }

    protected final void previouspage() {
        if(page != 1){
            page -= 1;
            upperpanel.setCurrentPageLabel(page + "");
            loaddata();
        }
    }

    protected final void nextpage() {
        if(page != calculatepages()){
            page += 1;
            upperpanel.setCurrentPageLabel(page + "");
            loaddata();
        }
    }

    protected final void lastpage() {
        if(page != calculatepages()){
            page = calculatepages();
            upperpanel.setCurrentPageLabel(page + "");
            loaddata();
        }
    }
    
    protected int calculatepages(){
        if(numofitem == 0){
            return 1;
        }
        else{
            if(numofitem%GlobalFields.ITEM_PER_PAGE == 0){
                return numofitem/GlobalFields.ITEM_PER_PAGE;
            }
            else{
                return (numofitem/GlobalFields.ITEM_PER_PAGE)+1;
            }
        }
    }
}
