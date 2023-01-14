/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iw.iwmobile.entities;

import com.codename1.io.Externalizable;
import com.codename1.io.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author helio-mint32-2
 */
public class AccessToken implements Externalizable {
    
    // default values for Pages Address:
    final private String DEFAULT_HOME_PAGE_URL =
      "http://www.iwsoftware.com.br"; 
    final private String DEFAULT_IOS_MANUAL_PAGE_URL =
      "http://iw.iwsoftware.com.br:9090//IwHelp/IW_Mobile_Manual.html"; 
    final private String DEFAULT_MANUAL_PAGE_URL =
      "http://iw.iwsoftware.com.br:9090//IwHelp/IW_Mobile_Manual.html";
    final private String DEFAULT_IWMOBILE_SECURITY_POLICY_URL =
      "http://iw.iwsoftware.com.br:9090//IwHelp/IW_Mobile_Security_Policy.html";

    final private String DEFAULT_IWMOBILE_IWCHANNEL_URL = ""; 
    
    final private String DEFAULT_IWMOBILE_CACHE_OFFLINE_PERIOD = ""; 
            
    final private String DEFAULT_IWMOBILE_GPS_ACCESS_MSG = ".";
    
    final private String DEFAULT_IWMOBILE_NAVIGATOR_ACCESS_MSG = ".";
    
    final private String DEFAULT_IW_DIGITAL_SIGN_URL = "";
    // Default Values for Images Sizes for Upload 
    // Sum of all sizes of all images that need to be upload.
    // default = 1 Mbyte
    private final int DEFAULT_IWMOBILE_IMAGE_TOTAL_SIZE = 1024;
    
    // Width of image that photos should be reduced
    private final int DEFAULT_IWMOBILE_IMAGE_WIDTH = 480; // 480 pixels  
    private final int DEFAULT_IWMOBILE_EVOL_HORIZ = -1;   // -1 = Unknown
    private final int DEFAULT_IWMOBILE_CODBAR_EAN13 = 0;   // -1 = Unknown
    
    private String id;
    private User user;
    private List<String> iwAuthList;
    
    // Not persisted attributes.
    // May be they could be saved in future.
    private String iwSysGoogleMapsApiKey = "";
    private int bRule_AllowUploadProfGeoLoc = 0;
    private int bRuleVibrateWhenUnreadNofifications = 0;
    private int bRuleCanAddAgenda = 0;
    private int bRuleCanAccessMyPatients = 1;
    private int bRuleCanViewEquipments = 0;
    private int bRuleCanViewEquipmentKits = 0;
    private int bRuleCanViewGasRecharge = 0;
    private int bRuleCanViewMatMedConsumption = 0;
    private int bRuleCanViewTestRequest = 0;
    private int bRuleMustUseElectronicSignature = 0;
    private int bRuleCanViewNurseMedCheck = 0;
    private int bRuleCanViewCleanningActivities = 0;
    private int bRuleCanEditPatientPhoto = 0;
    private int bRuleCanDoMultipleCheckin = 0;
    private int bRuleCanInsertDocument = 0;
    private int bRuleCanEditAdmissionQrCode = 0;
    private int bRuleCanDoCareStaffNotification = 0;
    private int bRuleCanDoMatDeliveryProtocol = 0;
    private int iwSecPasswordPolicy = 0;
    private int iwSecPasswordMinLength = 0;
    private int iwSecPasswordExpiration = 0;
    // End Not persisted attributes
    
    
    private String iwSoftwareHomePageUrl = DEFAULT_HOME_PAGE_URL;
    private String iwMobileManualPageUrl = DEFAULT_MANUAL_PAGE_URL;
    private String iwMobileIOSManualPageUrl = DEFAULT_IOS_MANUAL_PAGE_URL;
    private String iwMobileIwChannelUrl = DEFAULT_IWMOBILE_IWCHANNEL_URL;
    private String iwMobileCacheOfflinePeriod = DEFAULT_IWMOBILE_CACHE_OFFLINE_PERIOD;
    private String iwMobileGPSAccessMsg = DEFAULT_IWMOBILE_GPS_ACCESS_MSG;
    private String iwMobileNavigatorAccessMsg = DEFAULT_IWMOBILE_NAVIGATOR_ACCESS_MSG;
            
    private String iwMobileSecurityPolicyUrl = DEFAULT_IWMOBILE_SECURITY_POLICY_URL;

    private int iwMobileImageTotalSize = DEFAULT_IWMOBILE_IMAGE_TOTAL_SIZE;
    private int iwMobileImageWidth = DEFAULT_IWMOBILE_IMAGE_WIDTH;
    private int iwMobileEvolHoriz = DEFAULT_IWMOBILE_EVOL_HORIZ;
    private String iwDigitalSignUrl = DEFAULT_IW_DIGITAL_SIGN_URL;
    private int iwMobileCodBarEAN13 = DEFAULT_IWMOBILE_CODBAR_EAN13;
    
    // Define whether default values was
    // used for IwParameters or not. 
    private boolean usingDefaultValues = true;
    

    /**
     * Empty Constructor = Necessary for Storage.
     */
    public AccessToken() {
    }
    
    
    /**
     * @return the id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the user
     */
    public User getUser() {
        return user;
    }

    /**
     * @param user the user to set
     */
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the iwAuthList
     */
    public List<String> getIwAuthList() {
        return iwAuthList;
    }

    /**
     * @param iwAuthList the iwAuthList to set
     */
    public void setIwAuthList(List<String> iwAuthList) {
        this.iwAuthList = iwAuthList;
    }

    @Override
    public int getVersion() {
        
        // Version 1: Only 3 first attributes 
        // String id;
        // User user;
        // List<String> iwAuthList;
        
        // version 2: Added 6 new attributes.
        // Current Version.
        // String iwSoftwareHomePageUrl;
        // String iwMobileManualPageUrl;
        // String iwMobileIOSManualPageUrl;  
        // int iwMobileImageTotalSize;
        // int iwMobileImageWidth;
        // boolean usingDefaultValues;       
        
        //version 3: Added iwchannel attribute
        //version 4: Added cacheOfflinePeriod attribute
        //version 5: Added iwMobileGPSAccessMsg attribute
        //version 6: Added iwMobileNavigatorAccessMsg attribute
        return 6;
        
    }

    @Override
    public void externalize(DataOutputStream out) throws IOException {
        
        Util.writeUTF(getId(), out);
        Util.writeObject(getUser(), out);
        Util.writeObject(getIwAuthList(), out);
        
        Util.writeUTF(getIwSoftwareHomePageUrl(), out);
        Util.writeUTF(getIwMobileManualPageUrl(), out);
        Util.writeUTF(getIwMobileIOSManualPageUrl(), out);
        out.writeInt(getIwMobileImageTotalSize());
        out.writeInt(getIwMobileImageWidth());
        out.writeBoolean(isUsingDefaultValues());
        Util.writeUTF(getIwMobileIwChannelUrl(), out);
        Util.writeUTF(getIwMobileCacheOfflinePeriod(), out);
        Util.writeUTF(getIwMobileGPSAccessMsg(), out);
        Util.writeUTF(getIwMobileNavigatorAccessMsg(), out);
    }

    @Override
    public void internalize(
            int version,
            DataInputStream in) throws IOException {
        
        if (version == 1) {
            setId(Util.readUTF(in));
            setUser((User) Util.readObject(in));
            setIwAuthList((List<String>)Util.readObject(in));
        }
        else { 
            
            setId(Util.readUTF(in));
            setUser((User) Util.readObject(in));
            setIwAuthList((List<String>)Util.readObject(in));

            // added 6 new attributes.
            setIwSoftwareHomePageUrl(Util.readUTF(in));
            setIwMobileManualPageUrl(Util.readUTF(in));
            setIwMobileIOSManualPageUrl(Util.readUTF(in));
            setIwMobileImageTotalSize(in.readInt());
            setIwMobileImageWidth(in.readInt());
            setUsingDefaultValues(in.readBoolean());
            setIwMobileIwChannelUrl(Util.readUTF(in));
            
            // v4
            setIwMobileCacheOfflinePeriod(Util.readUTF(in));
            
            //v5
            setIwMobileGPSAccessMsg(Util.readUTF(in));
            //v6
            setIwMobileNavigatorAccessMsg(Util.readUTF(in));
        }
    }

    @Override
    public String getObjectId() {
        return "AccessToken";
    }

    /**
     * @return the iwsoftwareHomePageUrl
     */
    public String getIwSoftwareHomePageUrl() {
        return iwSoftwareHomePageUrl;
    }

    /**
     * @param iwSoftwareHomePageUrl
     */
    public void setIwSoftwareHomePageUrl(String iwSoftwareHomePageUrl) {
        this.iwSoftwareHomePageUrl = iwSoftwareHomePageUrl;
    }
    
    public void setIwSysGoogleMapsApiKey(String iwSysGoogleMapsApiKey) {
        this.iwSysGoogleMapsApiKey = iwSysGoogleMapsApiKey;
    }    
    public String getIwSysGoogleMapsApiKey() {
        return this.iwSysGoogleMapsApiKey;
    }

    /**
     * @return the iwMobileManualPageUrl
     */
    public String getIwMobileManualPageUrl() {
        return iwMobileManualPageUrl;
    }

    /**
     * @param iwMobileManualPageUrl the iwMobileManualPageUrl to set
     */
    public void setIwMobileManualPageUrl(String iwMobileManualPageUrl) {
        this.iwMobileManualPageUrl = iwMobileManualPageUrl;
    }

    /**
     * @return the iwMobileIOSManualPageUrl
     */
    public String getIwMobileIOSManualPageUrl() {
        return iwMobileIOSManualPageUrl;
    }

    /**
     * @param iwMobileIOSManualPageUrl the iwMobileIOSManualPageUrl to set
     */
    public void setIwMobileIOSManualPageUrl(String iwMobileIOSManualPageUrl) {
        this.iwMobileIOSManualPageUrl = iwMobileIOSManualPageUrl;
    }

    public String getIwMobileIwChannelUrl() {
        return iwMobileIwChannelUrl;
    }

    public void setIwMobileIwChannelUrl(String iwMobileIwChannelUrl) {
        this.iwMobileIwChannelUrl = iwMobileIwChannelUrl;
    }

    /**
     */
    public String getIwMobileCacheOfflinePeriod() {
        return iwMobileCacheOfflinePeriod;
    }

    public void setIwMobileCacheOfflinePeriod(String iwMobileCacheOfflinePeriod) {
        this.iwMobileCacheOfflinePeriod = iwMobileCacheOfflinePeriod;
    }

    
    
    /**
     */
    public String getIwMobileGPSAccessMsg() {
        return iwMobileGPSAccessMsg;
    }

    public void setIwMobileGPSAccessMsg(String iwMobileGPSAccessMsg) {
        this.iwMobileGPSAccessMsg = iwMobileGPSAccessMsg;
    }

    /**
     */
    public String getIwMobileNavigatorAccessMsg() {
        return iwMobileNavigatorAccessMsg;
    }

    public void setIwMobileNavigatorAccessMsg(String iwMobileNavigatorAccessMsg) {
        this.iwMobileNavigatorAccessMsg = iwMobileNavigatorAccessMsg;
    }
    
    /**
     * @return the iwMobileSecurityPolicyUrl
     */
    public String getIwMobileSecurityPolicyUrl() {
        return iwMobileSecurityPolicyUrl;
    }

    /**
     * @param iwMobileSecurityPolicyUrl the iwMobileSecurityPolicyUrl to set
     */
    public void setIwMobileSecurityPolicyUrl(String iwMobileSecurityPolicyUrl) {
        this.iwMobileSecurityPolicyUrl = iwMobileSecurityPolicyUrl;
    }

    
    
    /**
     * @return the iwMobileImageTotalSize
     */
    public int getIwMobileImageTotalSize() {
        return iwMobileImageTotalSize;
    }

    /**
     * @param iwMobileImageTotalSize the iwMobileImageTotalSize to set
     */
    public void setIwMobileImageTotalSize(int iwMobileImageTotalSize) {
        this.iwMobileImageTotalSize = iwMobileImageTotalSize;
    }

    /**
     * @return the iwMobileImageWidth
     */
    public int getIwMobileImageWidth() {
        return iwMobileImageWidth;
    }

    /**
     * @param iwMobileImageWidth the iwMobileImageWidth to set
     */
    public void setIwMobileImageWidth(int iwMobileImageWidth) {
        this.iwMobileImageWidth = iwMobileImageWidth;
    }
    
    /**
     * @return the iwMobileEvolHoriz
     */
    public int getIwMobileEvolHoriz() {
        return this.iwMobileEvolHoriz;
    }
    
    /**
     * @param evolHoriz the iwMobileEvolHoriz to set
     */
    public void setIwMobileEvolHoriz(int evolHoriz) {
        this.iwMobileEvolHoriz = evolHoriz;
    }

    public String getIwDigitalSignUrl() {
        return this.iwDigitalSignUrl;
    }
    
    public void setIwDigitalSignUrl(String digitalSignUrl){
        this.iwDigitalSignUrl = digitalSignUrl;
    }

    /**
     * @return the iwMobileCodBarEAN13
     */
    public int getIwMobileCodBarEAN13() {
        return this.iwMobileCodBarEAN13;
    }
    
    /**
     * @param codBarEAN13 the iwMobileCodBarEAN13 to set
     */
    public void setIwMobileCodBarEAN13(int codBarEAN13) {
        this.iwMobileCodBarEAN13 = codBarEAN13;
    }
    
    /**
     * @return the usingDefaultValues
     */
    public boolean isUsingDefaultValues() {
        return usingDefaultValues;
    }

    /**
     * @param usingDefaultValues the usingDefaultValues to set
     */
    public void setUsingDefaultValues(boolean usingDefaultValues) {
        this.usingDefaultValues = usingDefaultValues;
    }

    /**
     * @return the bRule_AllowUploadProfGeoLoc
     */


    public int getbRuleAllowUploadProfGeoLoc() {
        return bRule_AllowUploadProfGeoLoc;
    }
    /**
     * @param bRule_AllowUploadProfGeoLoc the bRule_AllowUploadProfGeoLoc to set
     */
    public void setbRuleAllowUploadProfGeoLoc(int bRule_AllowUploadProfGeoLoc) {
        this.bRule_AllowUploadProfGeoLoc = bRule_AllowUploadProfGeoLoc;
    }

    /**
     * @return the iwSecPasswordPolicy
     */
    public int getIwSecPasswordPolicy() {
        return iwSecPasswordPolicy;
    }

    /**
     * @param iwSecPasswordPolicy the iwSecPasswordPolicy to set
     */
    public void setIwSecPasswordPolicy(int iwSecPasswordPolicy) {
        this.iwSecPasswordPolicy = iwSecPasswordPolicy;
    }

    /**
     * @return the iwSecPasswordMinLength
     */
    public int getIwSecPasswordMinLength() {
        return iwSecPasswordMinLength;
    }

    /**
     * @param iwSecPasswordMinLength the iwSecPasswordMinLength to set
     */
    public void setIwSecPasswordMinLength(int iwSecPasswordMinLength) {
        this.iwSecPasswordMinLength = iwSecPasswordMinLength;
    }

    /**
     * @return the iwSecPasswordExpiration
     */
    public int getIwSecPasswordExpiration() {
        return iwSecPasswordExpiration;
    }

    /**
     * @param iwSecPasswordExpiration the iwSecPasswordExpiration to set
     */
    public void setIwSecPasswordExpiration(int iwSecPasswordExpiration) {
        this.iwSecPasswordExpiration = iwSecPasswordExpiration;
    }

    /**
     * @return the bRuleVibrateWhenUnreadNofifications
     */
    public int getbRuleVibrateWhenUnreadNofifications() {
        return bRuleVibrateWhenUnreadNofifications;
    }

    /**
     * @param bRuleVibrateWhenUnreadNofifications the bRuleVibrateWhenUnreadNofifications to set
     */
    public void setbRuleVibrateWhenUnreadNofifications(int bRuleVibrateWhenUnreadNofifications) {
        this.bRuleVibrateWhenUnreadNofifications = bRuleVibrateWhenUnreadNofifications;
    }

    /**
     * @param bRuleCanAddAgenda the bRuleCanAddAgenda to set
     */
    public void setbRuleCanAddAgenda(int bRuleCanAddAgenda) {
        this.bRuleCanAddAgenda = bRuleCanAddAgenda;
    }
    public boolean getCanAddAgenda() {
        return this.bRuleCanAddAgenda == 1;
    }
    
    public void setbRuleCanAccessMyPatients(int rule) {
        this.bRuleCanAccessMyPatients = rule ;
    }
    public boolean getCanAccessMyPatients() {
        return this.bRuleCanAccessMyPatients == 1;
    }

    public void setbRuleCanViewEquipments(int rule) {
        this.bRuleCanViewEquipments = rule;
    }
    public boolean getCanViewEquipments() {
        return this.bRuleCanViewEquipments == 1;
    }

    public void setbRuleCanViewEquipmentKits(int rule) {
        this.bRuleCanViewEquipmentKits = rule;
    }
    public boolean getCanViewEquipmentKits() {
        return this.bRuleCanViewEquipmentKits == 1;
    }
            
    public void setbRuleCanViewGasRecharge(int rule) {
        this.bRuleCanViewGasRecharge = rule;
    }
    public boolean getCanViewGasRecharge() {
        return this.bRuleCanViewGasRecharge == 1;
    }

    public void setbRuleCanViewMatMedConsumption(int rule) {
        this.bRuleCanViewMatMedConsumption = rule;
    }
    public boolean getCanViewMatMedConsumption() {
        return this.bRuleCanViewMatMedConsumption == 1;
    }

    public void setbRuleCanViewTestRequest(int rule) {
        this.bRuleCanViewTestRequest = rule;
    }
    public boolean getCanViewTestRequest() {
        return this.bRuleCanViewTestRequest == 1;
    }
    
    public boolean getMustUseElectronicSignature() {
        return this.bRuleMustUseElectronicSignature == 1;
    }
    public void setbRuleMustUseElectronicSignature(int rule) {
        this.bRuleMustUseElectronicSignature = rule;
    }
    
    public boolean getCanViewNurseMedCheck() { //IWMobile_Block_Nurse_Med_Check disable
        return this.bRuleCanViewNurseMedCheck == 1;
    }
    public void setbRuleCanViewNurseMedCheck(int rule) {
        this.bRuleCanViewNurseMedCheck = rule;
    }

    public boolean getCanViewCleanningActivities() { //IWMobile_Block_Cleanning_Activities disable
        return this.bRuleCanViewCleanningActivities == 1;
    }
    public void setbRuleCanViewCleanningActivities(int rule) {
        this.bRuleCanViewCleanningActivities = rule;
    }
    
    public void setbRuleCanEditPatientPhoto(int rule) {
        this.bRuleCanEditPatientPhoto = rule;
    }
    public boolean getCanEditPatientPhoto() {
        return this.bRuleCanEditPatientPhoto == 1;
    }

    public void setbRuleCanDoMultipleCheckin(int rule) {
        this.bRuleCanDoMultipleCheckin = rule;
    }
    public boolean getCanDoMultipleCheckin() {
        return this.bRuleCanDoMultipleCheckin == 1;
    }
    
    public void setbRuleCanInsertDocument(int rule) {
        this.bRuleCanInsertDocument = rule;
    }
    public boolean getCanInsertDocument() {
        return this.bRuleCanInsertDocument == 1;
    }

    //IWMobile_Block_QRCode_Edition
    public void setbRuleCanEditAdmissionQrCode(int rule) {
        this.bRuleCanEditAdmissionQrCode = rule;
    }
    public boolean getCanEditAdmissionQrCode() {
        return this.bRuleCanEditAdmissionQrCode == 1;
    }

    //IWMobile_Block_CareStaff_Notification
    public void setbRuleCanDoCareStaffNotification(int rule) {
        this.bRuleCanDoCareStaffNotification = rule;
    }
    public boolean getCanDoCareStaffNotification() {
        return this.bRuleCanDoCareStaffNotification == 1;
    }

    //IWMobile_Block_Deliv_Protocol
    public void setbRuleCanDoMatDeliveryProtocol(int rule) {
        this.bRuleCanDoMatDeliveryProtocol = rule;
    }
    public boolean getCanDoMatDeliveryProtocol() {
        return this.bRuleCanDoMatDeliveryProtocol == 1;
    }
    
}
