/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.comm;

/**
 *
 * @author hrugani
 */
public interface IwExtContextInterface
        extends IwExtDirectServiceCallInterface {
    
   long getCurrentAdmissionId();
   String getCurrentPatientShortName();
   String getUserId();
   String getUserName();
   long getUserProfessionalId();
   int getLastNotificationType();
   boolean isOnlineMode(); 
   String getBaseURL4ExtContext();
   
}
