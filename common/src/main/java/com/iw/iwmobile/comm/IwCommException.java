/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iw.iwmobile.comm;

/**
 *
 * @author helio
 */
public class IwCommException extends Exception {
    
    MobRecordsetError rsError;

    public IwCommException(MobRecordsetError rsError ) {
        super(rsError.getTranslation());
        this.rsError = rsError;
    }
    
    public IwCommException(String offLineOpName) {
        super(offLineOpName + ": operation not allowed offline");
        this.rsError = new MobRecordsetError(
                -2000,
                404,
                offLineOpName,
                offLineOpName
        );
    }
    
    public MobRecordsetError getRsError() {
        return this.rsError;
    }
    
}
