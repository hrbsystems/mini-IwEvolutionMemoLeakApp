/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.forms;

import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Button;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.GridLayout;
import com.codename1.ui.spinner.Picker;

import java.util.Date;

import static com.iw.iwmobile.IwConstantsInterface.TT_CANCEL;
import static com.iw.iwmobile.IwConstantsInterface.TT_CONFIRM;

/**
 *
 * @author helio
 */
public class IwFormInputDate1 extends IwFormBase {
    
    static public final int USER_ACTION_CONFIRM = 1;
    static public final int USER_ACTiON_CANCEL = 0;
    private int userAction = USER_ACTiON_CANCEL; // Default USer Action

    Picker picker;
    Button btnConfirm;
    Button btnCancel;
    
    String sInitDate;
    String sEditedDate;
    
    private String varName;

    public IwFormInputDate1(
            String varName,
            String sInitDate) {
        
        super();
        setTitle("Data:" + varName);
        this.sInitDate = sInitDate;
        this.sEditedDate = sInitDate;
        
        this.varName = varName;
        this.setScrollable(false);
        
        preInit();
        init();
        postInit();
    }
    
    public String getStrEditedDate() {
        return this.sEditedDate;
    }
    
    public int getUserAction() {
        return this.userAction;
    }

    private void preInit() {
        picker = createPicker(this.sInitDate);
        btnConfirm = createBtnConfirm();
        btnCancel = createBtnCancel();
    }

    private void init() {
        
        Container cButtons = new Container(new GridLayout(1,2));
            cButtons.addComponent(btnConfirm);
            cButtons.addComponent(btnCancel);

        Container  cPickerButtons = new Container(new BorderLayout());
        cPickerButtons.addComponent(BorderLayout.NORTH, picker);
        cPickerButtons.addComponent(BorderLayout.SOUTH, cButtons);

        Container c = getContentPane();
        c.setLayout(
            new BorderLayout(
                BorderLayout.CENTER_BEHAVIOR_CENTER_ABSOLUTE
            )
        );
        c.addComponent(BorderLayout.CENTER, cPickerButtons);
        
    }

    private void postInit() {
    }
    
    public void setVarName(String varName) {
        this.varName = varName;
    }
    
    public void setVarValue(String value) {
        
        this.sInitDate = value;
        this.sEditedDate = value;
        Date d;
        try {
            d = sdf.parse(value);
        } catch (Exception ex) {
            d = new Date();
        }
        picker.setDate(d);
    }

    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
    private Picker createPicker(String sDate) {
        Date d;
        try {
            d = sdf.parse(sDate);
        } catch (Exception ex) {
            d = new Date();
        }
        Picker p = new Picker();
        p.setType(Display.PICKER_TYPE_DATE);
        p.setDate(d);
        return p;
    }

//    private Button createBtnConfirm() {
//        Button btn = new Button(getIwTranslation(TT_CONFIRM));
//        btn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent evt) {
//                userAction = USER_ACTION_CONFIRM;
//                Date d = picker.getDate();
//                sEditedDate = sdf.format(d);
//                dispose();
//            }
//        });
//        return btn;
//    }
    
    private Button createBtnConfirm() {
        Button btn = new Button(getIwTranslation(TT_CONFIRM));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                
                userAction = USER_ACTION_CONFIRM;
                Date d = picker.getDate();
                sEditedDate = sdf.format(d);
                                
//                if (sEditedDate != null) {
//                    HashMap<String,String> map = new HashMap<String,String>();
//                    map.put(varName, sEditedDate);
//                    iwWebBrowser.setHtmlVariablesValues(map);                    
//                }                
                getBackCommand().actionPerformed(null);
                
            }
        });
        return btn;
    }
    

    private Button createBtnCancel() {
        Button btn = new Button(getIwTranslation(TT_CANCEL));
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                userAction = USER_ACTiON_CANCEL;
                //sInitDate = "";
                //sEditedDate = "";
                getBackCommand().actionPerformed(null);
            }
        });
        return btn;
    }
    
}
