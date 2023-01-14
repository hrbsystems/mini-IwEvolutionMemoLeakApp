/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iw.iwmobile.forms;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
//import com.codename1.ui.events.ActionListener;
//import com.codename1.ui.events.FocusListener;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.plaf.UIManager;
import com.iw.iwmobile.Brain;
import com.iw.iwmobile.IwColorUtil;
import com.iw.iwmobile.IwConstantsInterface;
//import com.iw.iwmobile.comm.IwExtContext;
//import com.iw.iwmobile.comm.IwServiceCallerOffline;
//import com.iw.iwmobile.entities.AccessConfig;
//import com.iw.iwmobile.extensions.notifications.*;

import java.util.Iterator;

import static com.codename1.ui.ComponentSelector.$;

/**
 *
 * @author helio
 */
public class IwFormBase extends Form implements IwConstantsInterface {    
            
    static final float MY_ICON__SIZE = 4.5f;
    
    Command cmdNotifications;
    Button btnNofications = new Button();
    Command cmdNurseMedCheckSync;
    //Button btnNurseMedCheckSync = new Button();
    
    public IwFormBase(String title) {
        //super(title);
        super();
        myInit();
    }
    
    public IwFormBase() {
        //super();
        myInit();
        
//        addShowListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                IwFormBase.this.updateNotificationIcon(
//                    Brain.getInstance().getLastNotificationColor()
//                );
//                IwFormBase.this.updateNurseMedCheckSyncIcon(Brain.getInstance().getIsSynchronizing());
//            }
//        });
        
//        addFocusListener(new FocusListener() {
//
//            @Override
//            public void focusGained(Component cmp) {
//                IwFormBase.this.updateNotificationIcon(
//                    Brain.getInstance().getLastNotificationColor()
//                );
//            }
//
//            @Override
//            public void focusLost(Component cmp) {
//            }
//
//        });
        
    }
    
    private void myInit() {

//        IwServiceCallerOffline caller =
//            Brain.getInstance().getIwServiceCallerOffline();
//
//        AccessConfig aConfig = null;
//        String userName = null;
//        if (caller != null) {
//            aConfig = caller.getAccessConfig();
//        }
//        if (aConfig != null) {
//            userName = aConfig.getUserName();
//        }
//        configTitleBar1(userName);
//
//        updateNotificationIcon(Brain.getInstance().getLastNotificationColor());
//
//        revalidate();
        
    }
    
    private final int COLOR_NOTIFICATION_RED = 0xcc3300;
    private final int COLOR_NOTIFICATION_YELLOW = 0xdcb429;// f0f000 old color
    private final int COLOR_NOTIFICATION_GREEN = 0x009900;
    private final int COLOR_NOTIFICATION_BLACK = 0x000000;
    public void updateNotificationIcon(final String colorName) {
        
        if (btnNofications != null) {
            Display.getInstance().callSerially(new Runnable() {
                @Override
                public void run() {
                    
                    if ("red".equals(colorName)) {
                        
                        $(btnNofications)
                            .selectAllStyles()
                            .setFgColor(COLOR_NOTIFICATION_RED)
                            .setIcon(
                                FontImage.MATERIAL_NOTIFICATIONS_ACTIVE,
                                MY_ICON__SIZE
                            );
                        
                    }
                    else if ("yellow".equals(colorName)) {
                        
                        $(btnNofications)
                            .selectAllStyles()
                            .setFgColor(COLOR_NOTIFICATION_YELLOW)
                            .setIcon(
                                FontImage.MATERIAL_NOTIFICATIONS_ACTIVE,
                                MY_ICON__SIZE
                            );
                        
                    }
                    else if ("green".equals(colorName)) {
                        
                        $(btnNofications)
                            .selectAllStyles()
                            .setFgColor(COLOR_NOTIFICATION_GREEN)
                            .setIcon(
                                FontImage.MATERIAL_NOTIFICATIONS_ACTIVE,
                                MY_ICON__SIZE
                            );
                        
                    }
                    else {

                        $(btnNofications)
                            .selectAllStyles()
                            .setFgColor(COLOR_NOTIFICATION_BLACK)
                            .setIcon(
                                FontImage.MATERIAL_NOTIFICATIONS_NONE,
                                MY_ICON__SIZE
                            );
                        
                    }
                    
                    IwFormBase.this.repaint();
                    IwFormBase.this.revalidate();
                    
                }
            });
        }
    }

    public void addNurseMedCheckIconToolbar(){
        try{
            removeNurseMedCheckIconToolbar();
            
            Toolbar toolbar = getToolbar();
            Style myStyle = UIManager.getInstance().getComponentStyle("Button");
            myStyle.setBgColor(IwColorUtil.BLACK);
            myStyle.setFgColor(IwColorUtil.BLACK);
            Image icon = FontImage.createMaterial(FontImage.MATERIAL_SYNC, myStyle); 
            this.cmdNurseMedCheckSync = toolbar.addCommandToRightBar(
                "",
                icon,
                (ActionEvent e) -> {
                }
            );
            toolbar.repaint();
            toolbar.forceRevalidate();
            this.repaint();
            this.forceRevalidate();
        }
        catch(Exception e){
        }
    }
    
    public void removeNurseMedCheckIconToolbar(){
        Toolbar toolbar = getToolbar();
        Button b = toolbar.findCommandComponent(this.cmdNurseMedCheckSync);
        if (b!=null){
            toolbar.removeComponent(b);
            toolbar.removeCommand(this.cmdNurseMedCheckSync);
        }
        else{
            for (Iterator i = toolbar.getRightBarCommands().iterator();i.hasNext();){
                Command c = (Command)i.next();
                if (c == this.cmdNurseMedCheckSync){
                    b = toolbar.findCommandComponent(c);
                    toolbar.removeComponent(b);
                    toolbar.removeCommand(this.cmdNurseMedCheckSync);
                }
            }
        }
        toolbar.repaint();
        toolbar.forceRevalidate();
        this.repaint();
        this.forceRevalidate();
    }
    
    public void updateNurseMedCheckSyncIcon(boolean isSynchronizing){
//        try{
//            if (this instanceof IwFormLogin) return;
//            if (isSynchronizing){
//                addNurseMedCheckIconToolbar();
//            }
//            else{
//                removeNurseMedCheckIconToolbar();
//            }
//        }
//        catch(Exception e){//for overall purposes
//            System.out.println(e.getMessage());
//        }
    }
            
    private void configTitleBar1(String userName) {
        // removed all code
    }
    
    @Override
    public String getIwTranslation(String token) {
        return Brain.getInstance().getIwTranslation(token);
    }    
    
    final private int DEFAULT_BG_GRADIENT_START_COLOR = 0x6495ED; // Blue tone
    final private int DEFAULT_BG_GRADIENT_END_COLOR = 0xFFFFFF;   // white
    protected void setDefaultBgColor(Container c) {
        Style style = c.getStyle();
        style.setBackgroundType(Style.BACKGROUND_GRADIENT_LINEAR_VERTICAL);
        style.setBackgroundGradientStartColor(0x6495ED);
        style.setBackgroundGradientEndColor(0xFFFFFF);
    }
    
    public void drawForm() {
    }
    
        
    private void showIwNotificationForm () {

//        final Form previousForm = Display.getInstance().getCurrent();
//
//        final IwFormNotifications f =
//            new IwFormNotifications(
//                new IwExtContext(-1, ""),
//                "Notificações"
//            );
//
//
//        String backTitle = getIwTranslation(TT_BACK);
//        Command backCommand = new Command(backTitle) {
//            @Override
//            public void actionPerformed(ActionEvent ev) {
//                previousForm.showBack();
//            }
//        };
//
//        f.setBackCommand(backCommand);
//        f.show();

    }

//                    this.addActionListener((ActionListener) (ActionEvent evt) -> {

}
