/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.forms;

import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.iw.iwmobile.components.IwFreeTextEditor;


/**
 *
 * @author helio
 */
public class IwDlgInputFreeText extends IwFormBase {
    
    static public final int USER_ACTION_CONFIRM = 1;
    static public final int USER_ACTION_CANCEL = 0;
    private int userAction = USER_ACTION_CANCEL;
    
    private IwFreeTextEditor editor;
    private Button btnConfirm;
    private Button btnCancel;
    
    private final String htmlPieceInit;
    
    private final String varName;

    public IwDlgInputFreeText(
            String varName,
            String htmlPieceInit) {
        
        super();
        setTitle(varName.substring("iwvar_".length()));
        this.varName = varName;
        this.htmlPieceInit = htmlPieceInit;
        this.setScrollable(false);
        preInit();
        init();
        postInit();    
    }
    
  
    public int getUserAction() {
        return this.userAction;
    }
    
    public String getEditedHtml(){
        return editor.getEditedHtml();
    }
    
    private void preInit() {
        editor = createEditor();
        btnConfirm = createBtnConfirm();
        btnCancel = createBtnCancel();
    }

    private void init() {
       Container pnlButtons = new Container(new GridLayout(1,2));
       pnlButtons.addComponent(btnConfirm);
       pnlButtons.addComponent(btnCancel);   
       Container pnlCenter = new Container(new BorderLayout());
       pnlCenter.setScrollableY(false);
       pnlCenter.addComponent(BorderLayout.CENTER, editor);
       getContentPane().setLayout(new BorderLayout());
       getContentPane().setScrollableY(true);
       getContentPane().addComponent(BorderLayout.CENTER, pnlCenter);
       getContentPane().addComponent(BorderLayout.SOUTH, pnlButtons);
    }

    private void postInit() {
        editor.requestFocus();
    }

    private IwFreeTextEditor createEditor() {
        return new IwFreeTextEditor(this.htmlPieceInit);
    }

    private Button createBtnConfirm() {
        Button btn = new Button(getIwTranslation(TT_CONFIRM));
        btn.addActionListener((ActionListener) (ActionEvent evt) -> {
            userAction = USER_ACTION_CONFIRM;
            String editedHtml = editor.getEditedHtml();
            if (editedHtml.contains("\\")) {
                String msgErr = "Barra invertida [\\]\nnão permitido.";
                Dialog.show("Erro", msgErr, "OK", null);
                return;
            }
            getBackCommand().actionPerformed(null);
        });
        return btn;
    }

    private Button createBtnCancel() {
        Button btn = new Button(getIwTranslation(TT_CANCEL));
        btn.addActionListener((ActionListener) (ActionEvent evt) -> {
            userAction = USER_ACTION_CANCEL;
            getBackCommand().actionPerformed(null);
        });
        return btn;
    }
    
}
