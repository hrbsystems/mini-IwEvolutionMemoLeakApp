/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.comm;

import java.util.Map;

/**
 *
 * @author hrugani
 */
public interface IwExtDirectServiceCallInterface {
    
    /**
     * Asynchronous call.
     * @param projectName
     * @param className
     * @param serviceName
     * @param pMap
     * @param callback
     */
    void execIwService(
            String projectName,
            String className,
            String serviceName,
            Map<String, Object> pMap,
            IwHttpRequesterCallBack<Map<String, MobRecordset>> callback
    );

    /**
     * Synchronous call
     * @param projectName
     * @param pMap receive a map of parameters. The map can receive as value only the following
     * types of classes: Integer, Long, Float, Double, Date and MobRecordset.
     * Only those classes can be serialized into requests.
     * @param className
     * @param serviceName
     * @return List of MobRecordset.
     * @throws com.iw.iwmobile.comm.IwCommException
     */
    Map<String, MobRecordset> execIwService(
            String projectName,
            String className,
            String serviceName,
            Map<String, Object> pMap) throws IwCommException;
    

}
