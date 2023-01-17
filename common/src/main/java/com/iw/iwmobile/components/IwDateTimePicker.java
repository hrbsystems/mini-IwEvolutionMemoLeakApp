/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.components;

import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.spinner.Picker;

import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Gustavo 18/06/2019 
 * 
 * This component was created to solve problem with DateTime Picker component on Android devices.
 * Date, Time, Numbers and Strings are supported on Android but DateTime is only supported on iOS
 * 
 */
public class IwDateTimePicker extends Container {

    private Picker pckDate;
    private Picker pckTime;
    private Label labelPrefix;
    private Font font;
    private int dataType = 0;
    public static final int DATATYPE_DATETIME = 0;        
    public static final int DATATYPE_DATE = 1;
    public static final int DATATYPE_TIME = 2;
            
    public IwDateTimePicker() {
        this(new Date(), Font.createSystemFont(
                Font.FACE_PROPORTIONAL,
                Font.STYLE_PLAIN,
                Font.SIZE_MEDIUM), 0, null);
    }

    public IwDateTimePicker(Date date) {
        this(date, Font.createSystemFont(
                Font.FACE_PROPORTIONAL,
                Font.STYLE_PLAIN,
                Font.SIZE_MEDIUM), 0, null);
    }

    public IwDateTimePicker(int dataType) {
        this(new Date(), Font.createSystemFont(
                Font.FACE_PROPORTIONAL,
                Font.STYLE_PLAIN,
                Font.SIZE_MEDIUM), dataType, null);
        
    }

    public IwDateTimePicker(Date date, Font font, int dataType) {
        this(date, font, dataType, null);
    }    
    
    public IwDateTimePicker(Date date, Font font, int dataType, String prefix) {
        
        setShouldCalcPreferredSize(false);
        
        this.font = font;
        this.dataType = dataType;
        pckDate = createPckDate(date==null);
        pckTime = createPckTime(date==null);
        pckDate.addPointerPressedListener((ev) -> {
            pckDate.setType(Display.PICKER_TYPE_DATE);
            pckTime.setType(Display.PICKER_TYPE_TIME);
            if (this.dataType!=1&&pckTime.getText().trim().length()==0){
                Calendar c = Calendar.getInstance();
                c.setTime(new Date());
                pckTime.setTime(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
            }
            pckDate.repaint();
            pckTime.repaint();
        });        
        pckTime.addPointerPressedListener((ev) -> {
            pckTime.setType(Display.PICKER_TYPE_TIME);
            if (this.dataType<2&&pckDate.getDate()==null){
                pckDate.setType(Display.PICKER_TYPE_DATE);
                pckDate.setDate(new Date());
            }
            pckDate.repaint();
            pckTime.repaint();
        });        
        
        setDateTime(date);
        
        this.setLayout(BoxLayout.xCenter());
        
        if (prefix != null) {
            labelPrefix = new Label(prefix);
            labelPrefix.getAllStyles().setFont(font);
//            labelPrefix.setOpaque(true);
            this.add(labelPrefix);
        }
        if (dataType==DATATYPE_DATE){
            this.add(pckDate);
        } 
        else if (dataType==DATATYPE_TIME){
            this.add(pckTime);
        }
        else{
            this.add(pckDate);
            this.add(pckTime);
        }
        
        this.setScrollableX(false);
        this.setScrollableY(false);
        
    }

    public IwDateTimePicker(Font font, int dataType) {
        this(new Date(), font, dataType);
    }
        
    public void setForeGroundColor(int color) {
        pckDate.getAllStyles().setFgColor(color);
        pckTime.getAllStyles().setFgColor(color);
        if (labelPrefix != null) {
            labelPrefix.getAllStyles().setFgColor(color);
        }
    }

    public void setBackGroundColor(int color) {
        pckDate.getAllStyles().setBgColor(color);
        pckTime.getAllStyles().setBgColor(color);
        if (labelPrefix != null) {
            labelPrefix.getAllStyles().setBgColor(color);
        }
    }
    
    public void setFont(Font font){
        pckDate.getAllStyles().setFont(font);
        pckTime.getAllStyles().setFont(font);
    }

    public void setDateTime(Date date){
        if (date!=null){
            pckDate.setType(Display.PICKER_TYPE_DATE);
            pckTime.setType(Display.PICKER_TYPE_TIME);
            pckDate.setDate(date);
            Calendar c = Calendar.getInstance();
            c.setTime(pckDate.getDate());
            pckTime.setTime(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE));
            pckDate.repaint();
            pckTime.repaint();
        }
        else{
            pckDate.setType(Display.PICKER_TYPE_STRINGS);
            pckTime.setType(Display.PICKER_TYPE_STRINGS);
            pckDate.setEditingDelegate(null);
            pckTime.setEditingDelegate(null);
            pckDate.setText("                   ");
            pckTime.setText("          ");
            pckDate.repaint();
            pckTime.repaint();
        }
    }

    public Date getDateTime(){
        if ((this.dataType<2&&pckDate.getType()==Display.PICKER_TYPE_STRINGS)||pckDate.getDate()==null){
            return null;
        }
        else if (this.dataType==2&&pckTime.getType()==Display.PICKER_TYPE_STRINGS){
            return null;
        }

        Calendar c = Calendar.getInstance();
        c.setTime(pckDate.getDate());
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        int timeps = pckTime.getTime();
        long timeinmillis = 60000*timeps;
        Date dtps = c.getTime();
        dtps.setTime(dtps.getTime()+timeinmillis);
        return dtps;
    }

    private Picker createPckDate(boolean emptyDate) {
        Picker picker = new Picker();
        picker.getAllStyles().setFont(this.font);
        
        picker.setType(Display.PICKER_TYPE_DATE);
        picker.setShowMeridiem(false);
        picker.setFormatter(new SimpleDateFormat("dd/MM/yyyy"));
        if (emptyDate){
            picker.setType(Display.PICKER_TYPE_STRINGS);
        }
        picker.getAllStyles().setPadding(1,1,2,2);
        Border b0 = Border.createLineBorder(1);
        Border bEmpty = Border.createEmpty();
        int rightmargin = 0;
        if (this.dataType==DATATYPE_DATE){
            rightmargin = 2;
            bEmpty = b0;
        }
        picker.getAllStyles().setMargin(2,2,2,rightmargin);
        picker.getAllStyles().setBorder(Border.createCompoundBorder(b0, b0, b0, bEmpty));
        picker.setAutoSizeMode(false);
        return picker;
    }

    private Picker createPckTime(boolean emptyTime) {
        Picker picker = new Picker();
        picker.getAllStyles().setFont(this.font);
        picker.setType(Display.PICKER_TYPE_TIME);
        if (emptyTime){
            picker.setType(Display.PICKER_TYPE_STRINGS);
        }
        picker.getAllStyles().setPadding(1,1,2,2);
        Border b0 = Border.createLineBorder(1);
        Border bEmpty = Border.createEmpty();
        int leftmargin = 0;
        if (this.dataType==DATATYPE_TIME){
            leftmargin = 2;
            bEmpty = b0;
        }
        picker.getAllStyles().setMargin(2,2,leftmargin,2);
        picker.getAllStyles().setBorder(Border.createCompoundBorder(b0, b0, bEmpty, b0));
        picker.setAutoSizeMode(false);
        return picker;
    }
    
    public Picker getpickerDate() {
        return pckDate;
    }
    public Picker getPickerTime() {
        return pckTime;
    }
}
