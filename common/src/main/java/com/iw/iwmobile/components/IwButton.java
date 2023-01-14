/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.components;

import com.codename1.ui.Button;
import com.codename1.ui.Image;

/**
 *
 * @author hrugani
 */
public class IwButton extends Button{
    
    private int BG_COLOR = 0x0000; 

    public IwButton() {
        super();
        setUIID("IwButtonStyle");
//        getStyle().setBackgroundType(getStyle().BACKGROUND_IMAGE_SCALED_FILL, false);
//        getStyle().setFgColor(BG_COLOR,true);
//        getStyle().setBgColor(BG_COLOR,true);
//        getStyle().setPadding(1,1,1,1);
   }
    
    public IwButton(Image icon) {
        super(icon);
        setUIID("IwButtonStyle");
//        getStyle().setBackgroundType(getStyle().BACKGROUND_IMAGE_SCALED_FILL, false);
//        getStyle().setFgColor(BG_COLOR,true);
//        getStyle().setBgColor(BG_COLOR,true);
//        getStyle().setPadding(1,1,1,1);
    }
    
    public IwButton(String text) {
        super(text);
        setUIID("IwButtonStyle");
//        getStyle().setBackgroundType(getStyle().BACKGROUND_IMAGE_SCALED_FILL, false);
//        getStyle().setFgColor(BG_COLOR,true);
//        getStyle().setBgColor(BG_COLOR,true);
//        getStyle().setPadding(1,1,1,1);
    }

    public IwButton(String text, Image icon) {
        super(text, icon);
        setUIID("IwButtonStyle");
//        getStyle().setBackgroundType(getStyle().BACKGROUND_IMAGE_SCALED_FILL, false);
//        getStyle().setFgColor(BG_COLOR,true);
//        getStyle().setBgColor(BG_COLOR,true);
//        getStyle().setPadding(1,1,1,1);
    }
    
}
