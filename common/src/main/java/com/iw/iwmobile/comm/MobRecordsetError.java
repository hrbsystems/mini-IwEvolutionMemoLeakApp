/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iw.iwmobile.comm;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author helio
 */
public class MobRecordsetError extends MobRecordset {
    
    private String IwCareErrorMsg;
    
    private static HashMap<Integer, String> httpErrorMap = new HashMap<Integer, String>();
    static {
        httpErrorMap.put(-2, "Connection Error");
        httpErrorMap.put(400, "Bad Request");
        httpErrorMap.put(403, "Forbidden");
        httpErrorMap.put(404, "Not Found");
        httpErrorMap.put(500, "Server Error");
    }

    public MobRecordsetError() {
        this.rows = new ArrayList<MobRow>();
        MobRow rError = new MobRow();
        rError.addField(new MobField("code", MobField.INTEGER, -1));
        rError.addField(new MobField("httpCode", MobField.INTEGER, -1));
        rError.addField(new MobField("message", MobField.STRING, ""));
        rError.addField(new MobField("httpMsg", MobField.STRING, ""));        
        rError.addField(new MobField("translation", MobField.STRING, ""));
        this.rows.add(rError);
    }
    
    public MobRecordsetError(int code, int httpCode, String message, String translation) {
        this.rows = new ArrayList<MobRow>();
        MobRow rError = new MobRow();
        rError.addField(new MobField("code", MobField.INTEGER, code));
        rError.addField(new MobField("httpCode", MobField.INTEGER, httpCode));
        rError.addField(new MobField("message", MobField.STRING, message));
        rError.addField(new MobField("httpMsg", MobField.STRING, getHttpErrorDescription(httpCode)));        
        rError.addField(new MobField("translation", MobField.STRING, translation));
        this.rows.add(rError);
    }

    private Object getHttpErrorDescription(int httpCode) {
        String desc = httpErrorMap.get(httpCode);
        return (desc == null)? "" : desc;
    }
    
    public String getTranslation() {
        return this.rows.get(0).field("translation").getValue();
    }
    
    public void setIwCareErrorMsg(String error){
        this.IwCareErrorMsg = error;
    }
    
    public String getIwCareErrorMsg(){
        if (IwCareErrorMsg==null){
            return "Unknown Error";
        }
        else return this.IwCareErrorMsg;
    }
    
}
