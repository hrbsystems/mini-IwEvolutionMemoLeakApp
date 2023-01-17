/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.forms;

import com.codename1.ui.Dialog;
import com.iw.iwmobile.Brain;
import com.iw.iwmobile.IwConstantsInterface;

/**
 *
 * @author helio
 */
public class IwDialogBase extends Dialog implements IwConstantsInterface {

    public IwDialogBase(String title) {
        super(title);
    }
    
    public IwDialogBase() {
        super();
    }   

    @Override
    public String getIwTranslation(String token) {
        return Brain.getInstance().getIwTranslation(token);
    }
            
    
}
