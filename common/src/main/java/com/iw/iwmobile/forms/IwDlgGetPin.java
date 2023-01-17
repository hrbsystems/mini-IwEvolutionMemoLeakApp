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

/**
 *
 * @author helio
 */
public class IwDlgGetPin extends IwDialogBase {
    
    static public final int USER_ACTION_CONFIRM = 1;
    static public final int USER_ACTION_CANCEL = 0;
    private int userAction = USER_ACTION_CANCEL; // Default user action
    
    TextField txtPin;
    Button btnConfirm;
    Button btnCancel;
    
    
    public IwDlgGetPin() {
        super();
        setTitle(getIwTranslation(TT_PIN));
        preInit();
        init();
        postInit();       
    }
    
    public String getPin() {
        return txtPin.getText();
    }
    
    public int getUserAction() {
        return this.userAction;
    }
    
    private void preInit() {
        txtPin = createTxtPin();
        btnConfirm = createBtnConfirm();
        btnCancel = createBtnCancel();
    }

    private void init() {
        setLayout(new BoxLayout(BoxLayout.Y_AXIS));
        
        Container c = new Container(new FlowLayout(CENTER));
        c.addComponent(btnConfirm);
        c.addComponent(btnCancel);
        
        getContentPane().addComponent(txtPin);
        getContentPane().addComponent(c);
    }

    private void postInit() {
    }

    private TextField createTxtPin() {
        final TextField txt = new TextField();
        //txt.setConstraint(TextField.NUMERIC);
        txt.addDataChangedListener(new DataChangedListener() {
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
        btn.addActionListener((ActionListener) (ActionEvent evt) -> {
            userAction = USER_ACTION_CONFIRM;
            dispose();
        });
        btn.setEnabled(false); // Init state = disabled.
        return btn;
    }

    private Button createBtnCancel() {
        Button btn = new Button("Cancelar");
        btn.addActionListener((ActionListener) (ActionEvent evt) -> {
            userAction = USER_ACTION_CANCEL;
            dispose();
        });
        return btn;
    }
    
}
