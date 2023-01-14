/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.comm;

import com.iw.iwmobile.Brain;
import com.iw.iwmobile.entities.AccessConfig;

import java.util.Map;

/**
 *
 * @author hrugani
 */
public class IwExtContext implements IwExtContextInterface {
    
    private final long admissionId;
    private final String patientShorName;

    public IwExtContext(
            long admissionId,
            String patientShortName) {
        super();
        this.admissionId = admissionId;
        this.patientShorName = patientShortName;
    }

    @Override
    public long getCurrentAdmissionId() {
        return this.admissionId;
    }

    @Override
    public String getCurrentPatientShortName() {
        return this.patientShorName;
    }

    @Override
    public String getUserId() {
        return "fakeUserId";
    }

    @Override
    public String getUserName() {
        return "fake user name";
    }

    @Override
    public long getUserProfessionalId() {
        return 1234;
    }

    @Override
    public void execIwService(
            String projectName,
            String className,
            String serviceName,
            Map<String, Object> pMap,
            IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
         
        Brain
            .getInstance()
            .getIwServiceCaller()
            .execIwService(
                    projectName,
                    className,
                    serviceName,
                    pMap,
                    callback
            );
        
    }

    @Override
    public Map<String, MobRecordset> execIwService(
            String projectName,
            String className,
            String serviceName,
            Map<String, Object> pMap) throws IwCommException {
        
        return 
            Brain
                .getInstance()
                .getIwServiceCaller()
                .execIwService(
                        projectName,
                        className,
                        serviceName,
                        pMap
                );        
    }

    @Override
    public int getLastNotificationType() {
        return 0; //Brain.getInstance().getLastNoticationType();
    }

    @Override
    public boolean isOnlineMode() {
        return Brain.getInstance().isOnlineMode();
    }

    @Override
    public String getBaseURL4ExtContext() {
        
        AccessConfig aConfig =
            Brain.getInstance().getIwServiceCaller().getAccessConfig();
        
        StringBuilder resp = new StringBuilder();
        resp.append(aConfig.getProtocol())
            .append("://")
            .append(aConfig.getHost())
            .append(":")
            .append(aConfig.getPort());
        return resp.toString();
        
    }
 
}
