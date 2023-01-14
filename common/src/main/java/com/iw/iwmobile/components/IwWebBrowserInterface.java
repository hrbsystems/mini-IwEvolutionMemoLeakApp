/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.components;

import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.events.ActionListener;
import com.iw.iwmobile.comm.IwCommException;
import com.iw.iwmobile.comm.IwHttpRequesterCallBack;
import com.iw.iwmobile.comm.MobRecordset;
import com.iw.iwmobile.entities.TDaddEvolutionOffline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author hrugani
 */
public interface IwWebBrowserInterface {
    
    String CTRL_VAR_CONSIST_RESULT = "ConsistResult";
    String CTRL_VAR_DISPLAY_ERROR = "displayError";

    Map<String, String> execConsistFunction() throws IwCommException;

    void execConsistFunctionAsync(IwHttpRequesterCallBack<Map<String, MobRecordset>> callback);

    ArrayList getBooleanVarNames();

    String getDynamicTableName();

    HashMap<String, String> getFormFieldTypesMap();

    String getHtml();

    HashMap<String, String> getHtmlVariableValues();

    String getIdAdmission();

    String getIdRowOfDynTable();

    String getIdTemplate();

    ArrayList getImageVarNames();

    HashMap<String, String> getInicialVariablesMap();
    
    HashMap<String,String> getScaledImagePathsMap();

    ArrayList getPdfVarNames();

    String getTemplateIdText();

    String getTimeStampCtr();

    ArrayList<String> getVarNamesList();

    boolean isReady();

    void resetAngularJSCtrlFlag();

    void setHtmlVariablesValues(Map<String, String> veriableMap);

    void setInitialVariablesMap(HashMap<String, String> map);

    void setIwTemplate(Map<String, MobRecordset> resultMap, String baseUrl);

    void setIwTemplate(TDaddEvolutionOffline td, String baseUrl);

    void setLoadCompletedListener(ActionListener<ActionEvent> actionListner);

    boolean templateHasConsistFunc();
    
    void setPage(String html, String baseUrl);
    
}
