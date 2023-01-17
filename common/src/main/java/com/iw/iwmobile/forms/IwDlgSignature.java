/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.forms;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.TextField;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.events.DataChangedListener;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.iw.iwmobile.Brain;
import com.iw.iwmobile.comm.IwCommException;
import com.iw.iwmobile.comm.IwServiceCallerInterface;

/**
 *
 * @author helio
 */
public class IwDlgSignature extends IwDialogBase {
    
    static public final int USER_ACTION_CONFIRM = 1;
    static public final int USER_ACTION_CANCEL = 0;
    private int userAction = USER_ACTION_CANCEL; // Default USer Action
    private boolean isPasswordChecked = false;
    private String info = ".";
    
    TextField txtSignature;
    Button btnConfirm;
    Button btnCancel;
    

    public IwDlgSignature() {
        super();
        setTitle(getIwTranslation(TT_SIGNATURE));
        preInit();
        init();
        postInit();
          
    }
    
    public boolean isPasswordChecked() {
        return this.isPasswordChecked;
    }
    
    public boolean isPasswordEmpty() {
        String pwd = this.txtSignature.getText();
        return pwd == null || "".equals(pwd);
    }
    
    public int getUserAction() {
        return this.userAction;
    }
    
    public String getInfo() {
        return this.info;
    }

    private void preInit() {
        txtSignature = createTxtSignature();
        btnConfirm = createBtnConfirm();
        btnCancel = createBtnCancel();
    }

    private void init() {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
        Container c = new Container(new FlowLayout(CENTER));
        c.addComponent(btnConfirm);
        c.addComponent(btnCancel);
        
        getContentPane().addComponent(txtSignature);
        getContentPane().addComponent(c);
    }

    private void postInit() {
    }

    private TextField createTxtSignature() {
        final TextField txt = new TextField();
        txt.setConstraint(TextField.PASSWORD);
        txt.addDataChangeListener(new DataChangedListener() {
            @Override
            public void dataChanged(int type, int index) {
                String text = txt.getText();
                if (text != null && !"".equals(text)) {
                    btnConfirm.setEnabled(true);
                }
                else {
                    btnConfirm.setEnabled(false);
                }
            }
        });
        return txt;
    }

    private Button createBtnConfirm() {
        Button btn = new Button(getIwTranslation(TT_CONFIRM));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                userAction = USER_ACTION_CONFIRM;
                //validatePassword();
                localValidatePassword();
                dispose();
            }
            
            private void localValidatePassword() {
                
                if (Brain
                        .getInstance()
                        .getIwServiceCaller()
                        .getAccessConfig()
                        .getPassword().equals(txtSignature.getText())) {
                    
                    info = "OK";
                    isPasswordChecked = true;
                }
                else {
                    
                    info = "Senha não confere";
                    isPasswordChecked = false;
                }
                
            }
            
            private void validatePassword() {
                
                IwServiceCallerInterface sCaller =
                        Brain.getInstance().getIwServiceCaller();
                
                try {
                    
                    boolean bResp = 
                            sCaller.validatePassword(txtSignature.getText());
                                        
                    if (bResp) {
                        info = "OK";
                    }
                    else {
                        info = "Senha não Confere";
                    }
                    isPasswordChecked = bResp;
                    
                } catch (IwCommException ex) {
                    info = "Erro de Comunicação";
                    isPasswordChecked = false;
                }
            }
        });
        btn.setEnabled(false); // Init state disabled.
        return btn;
    }

    private Button createBtnCancel() {
        Button btn = new Button("Cancelar");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                userAction = USER_ACTION_CANCEL;
                dispose();
            }
        });
        return btn;
    }
    
}
