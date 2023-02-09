/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.extensions.evolution;

import com.codename1.components.FloatingActionButton;
import com.codename1.components.InfiniteProgress;
import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.layouts.LayeredLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.UIManager;
import com.iw.iwmobile.Brain;
import com.iw.iwmobile.components.IwButton;
import com.iw.iwmobile.components.mockedComponents.IwWebBrowser1;
import com.iw.iwmobile.forms.IwDialogBase;
import com.iw.iwmobile.forms.IwFormBase;
import com.iw.iwmobile._fakelibs.Toast;
import java.util.HashMap;
import java.util.Map;

import static com.iw.iwmobile.IwConstantsInterface.TT_BACK;

/**
 *
 * @author helio
 */
public class IwFormAddEvolution21 extends IwFormBase {
    
    private final long idAdmssion;
    private IwWebBrowser1 webBrowser;

    private FloatingActionButton fabOptions;
    
    ActionListener saveListener;
    
    @Override
    protected Component createStatusBar() {
        Component c = super.createStatusBar();
        c.getUnselectedStyle().setPadding(0, 0, 0, 0);
        return c;
    }    
    
    public IwFormAddEvolution21(
            long idAdmission,
            ActionListener saveListener) {
        
        String title = getIwTranslation(TT_ADD_EVOLUTION);
        setTitle(title);
                
        this.idAdmssion = idAdmission;
        this.saveListener = saveListener;
                
        preInit(saveListener);
        init();
        postInit();
        
    }

    private void preInit(ActionListener saveListener) {
        webBrowser = createWebBrowser();
        fabOptions = createFabOptions(saveListener);
    }

    @Override
    protected void onShowCompleted() {
        super.onShowCompleted();
        if (!webBrowser.isReady()) {
            try{
                Toast.makeText(
                        Brain.getInstance().getContext(),
                        "buscando template ...",
                        Toast.LENGTH_SHORT);
            }
            catch (Exception e) {}       
        }
    }
    
    private void init() {
        Container c = getContentPane();
        c.setLayout(new GridLayout(1,1));
        c.setScrollableY(false);
        fabOptions.getAllStyles().setMarginTop(70);
        c.addComponent(
            LayeredLayout.encloseIn(
                webBrowser,
                FlowLayout.encloseRight(fabOptions)
            )
        );
    }
    
    private void postInit() {
    }

    private IwWebBrowser1 createWebBrowser() {
        IwWebBrowser1 b = new IwWebBrowser1(this);
        return b;
    }
    
    public IwWebBrowser1 getWebBrowser() {
        return this.webBrowser;
    }    

    private final int MY_BLUE_COLOR = 0x0000ff;
    private final int MY_WINE_COLOR = 0xC90909;
    private final int MY_GREEN_COLOR = 0x13af5f;
    private final int MY_GRAY_COLOR = 0x13af5f;

    FloatingActionButton createFabOptions(final ActionListener saveListener) {
        
        FloatingActionButton fab =
            FloatingActionButton.createFAB(
                    FontImage.MATERIAL_MENU
            );
        fab.getStyle().setBgColor(MY_GRAY_COLOR);
       
        
        fab.addActionListener((ActionListener) (ActionEvent evt) -> {
            MyOptionsDialog dlg = new MyOptionsDialog();
            dlg.show();
            dlg.repaint();
        });
        
        return fab;
        
    }

    @Override
    public void setBackCommand(Command backCommand) {
        super.setBackCommand(backCommand);
    }
    
    
    private final int MY_MARGIN_LEFT = 80;
    private final int MY_MARGIN_RIGHT = 80;
    private final int MY_MARGIN_TOP = 5;
    private final int MY_MARGIN_BOTTON = 5;
    private final int MY_IMG_SCALE_WIDTH = 110;
    class MyOptionsDialog extends IwDialogBase {

        class MyButton extends IwButton {
            
            public MyButton(String text, Image icon) {
                super("  " + text, icon.scaledWidth(MY_IMG_SCALE_WIDTH));
                this.getAllStyles().setAlignment(LEFT);
                this.getAllStyles().setMargin(
                    MY_MARGIN_TOP,
                    MY_MARGIN_BOTTON,
                    MY_MARGIN_LEFT,
                    MY_MARGIN_RIGHT
                );
                int BLACK = 0;
                this.getAllStyles().setBorder(Border.createLineBorder(1, BLACK));
            }

            @Override
            public void setText(String t) {
                super.setText(" " + t);
            }
            
        }

        private final MyButton btnOnlineSave;

        private final Button btnCancel;
        
        public MyOptionsDialog() {

            setTitle(getIwTranslation(TT_SELECT));
            btnOnlineSave = createBtnOnlineSave();
            btnCancel = createBtnCancel();
            
            setLayout(new GridLayout(1,1));

            addComponent(
                    BoxLayout.encloseY(
                            btnOnlineSave,
                            new Label(" "),
                            btnCancel
                    )
            );
        }

        private MyButton createBtnOnlineSave() {

            com.codename1.ui.plaf.Style myStyle =
                UIManager.getInstance().getComponentStyle("Button");
            myStyle.setBgColor(MY_BLUE_COLOR);
            myStyle.setFgColor(MY_BLUE_COLOR);
            
            final MyButton btn = new MyButton(
                "Salvar Evolução Online",
                FontImage.createMaterial(
                    FontImage.MATERIAL_SAVE,
                    myStyle
                )
            );            
            
            btn.addActionListener((ActionListener) (ActionEvent evt) -> {
                
                Brain brain = Brain.getInstance();
                int ON_LINE = 0;
                int OFF_LINE = 1;
                
                // save current mode
                int currentMode = (brain.isOnlineMode())? ON_LINE : OFF_LINE;
                
                // Force ON_LINE
                boolean isModeChanged = false;
                if(currentMode == OFF_LINE) {
                    brain.setOfflineMode(false); // Online
                    isModeChanged = true;
                }
                
                Dialog ip = new InfiniteProgress().showInifiniteBlocking();
                saveListener.actionPerformed(null);
                ip.dispose();
                
                //Restore current mode
                if (isModeChanged) {
                    brain.setOfflineMode(true); 
                }
                
                // Only with save routine runs successful
                // go back to previous form.
                if ( saveListener
                        instanceof
                     IwFormAddEvolution11.MySaveActionListener) {

                     IwFormAddEvolution11.MySaveActionListener sl =
                         (IwFormAddEvolution11.MySaveActionListener)
                             saveListener;

                    if (sl.isSuccessful()) {
                        
                        MyOptionsDialog.this.dispose();
                        IwFormAddEvolution21.this
                           .getBackCommand().actionPerformed(null);
                        brain.getFormAddEvolutionInstance()
                           .getBackCommand().actionPerformed(
                               new ActionEvent(new Button())
                           );
                        
                        // resets in Brain Object data for go back to 
                        // the right form after a successful persistence
                        // of  an evolution. (After consume them.
                        // The IwFormEvolutionNavig set them up
                        // on bt_Add action handler.
                        brain.setFormAddEvolutionInstance(null);
                        brain.setFormEvolutionNavigInstance(null);
                        
                        // This was the first attampt to recover
                        // it failed
                        //brain.getFormEvolutionNavigInstance().showBack();

                    }
                    else {
                        MyOptionsDialog.this.dispose();
                    }                 

                }
                else {
                    MyOptionsDialog.this.dispose();
                }

            });
            
            return btn;
            
        }

        private Button createBtnCancel() {
            Button btn = new Button(getIwTranslation(TT_CLOSE));
            btn.addActionListener((ActionListener) (ActionEvent evt) -> {
               MyOptionsDialog.this.dispose();
            });
            return btn;
        }
        
    }

        
}
