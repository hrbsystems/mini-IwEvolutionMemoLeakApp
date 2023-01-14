/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.entities;

import com.codename1.io.Externalizable;
import com.codename1.io.Storage;
import com.codename1.io.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 *
 * @author hrugani
 */
public class TDaddEvolutionOffline implements Externalizable {
    
    private String storageKey;
    private long idGroupTransaction;
    private long created;
    
    private ArrayList<String> varNamesList;
    private ArrayList<String> booleanVarNamesList;

    private String patientShortName;
    private String profName;
    private String templateName;
    private long startDate;
    private long endDate;
    private long idAdmission;
    private long idTemplate;
    private long idText;
    private String tableName;
    private int persistType;            
    private long idCapAdmProfCheckin;
    
    private String html;
    
    private HashMap<String, String> hmVariables;
    private HashMap<String,String> hmFormFieldTypes;

    public TDaddEvolutionOffline() {
    }

    ////////////////////////////////////////////////////////////////////////////
    // Externalizable Interface Implementation
    ////////////////////////////////////////////////////////////////////////////
   
    @Override
    public int getVersion() {
        return 1;
    }
    
    @Override
    public void externalize(DataOutputStream out) throws IOException {
        
        Util.writeUTF(getStorageKey(), out);
        out.writeLong(getIdGroupTransaction());
        out.writeLong(getCreated());
        
        Util.writeUTF(getPatientShortName(), out);
        Util.writeUTF(getProfName(), out);
        Util.writeUTF(getTemplateName(), out);
        
        Util.writeObject(getVarNamesList(), out);
        Util.writeObject(getBooleanVarNamesList(), out);
        
        out.writeLong(getStartDate());
        out.writeLong(getEndDate());
        out.writeLong(getIdAdmission());
        out.writeLong(getIdTemplate());
        out.writeLong(getIdText());
        Util.writeUTF(getTableName(), out);
        out.writeInt(getPersistType());
        out.writeLong(getIdCapAdmProfCheckin());
        
        //Util.writeUTF(html, out);
        myWriteUTF(html, out);
        
        Util.writeObject(getHmVariables(), out);
        Util.writeObject(getHmFormFieldTypes(), out);
        
    }

    @Override
    public void internalize(int version, DataInputStream in) throws IOException {

        setStorageKey(Util.readUTF(in));
        setIdGroupTransaction(in.readLong());
        setCreated(in.readLong());
        
        setPatientShortName(Util.readUTF(in));
        setProfName(Util.readUTF(in));
        setTemplateName(Util.readUTF(in));
        
        setVarNamesList((ArrayList<String>) Util.readObject(in));
        setBooleanVarNamesList((ArrayList<String>) Util.readObject(in));
        
        setStartDate(in.readLong());
        setEndDate(in.readLong());
        setIdAdmission(in.readLong());
        setIdTemplate(in.readLong());
        setIdText(in.readLong());
        setTableName(Util.readUTF(in));
        setPersistType(in.readInt());
        setIdCapAdmProfCheckin(in.readLong());
        
        //setHtml(Util.readUTF(in));
        setHtml(myReadUTF(in));
        
        setHmVariables((HashMap<String,String>) Util.readObject(in));
        setHmFormFieldTypes((HashMap<String,String>) Util.readObject(in));
    
    }
    
    @Override
    public String getObjectId() {
        return "TDaddEvolutionOffline";
    }
    
    /**
     * @return the idGroupTransaction
     */
    public long getIdGroupTransaction() {
        return idGroupTransaction;
    }
    
    /**
     * @param idGroupTransaction the idGroupTransaction to set
     */
    public void setIdGroupTransaction(long idGroupTransaction) {
        this.idGroupTransaction = idGroupTransaction;
    }

    /**
     * @return the hmVariables
     */
    public HashMap<String, String> getHmVariables() {
        return hmVariables;
    }

    /**
     * @param hmVariables the hmVariables to set
     */
    public void setHmVariables(HashMap<String, String> hmVariables) {
        this.hmVariables = hmVariables;
    }

    /**
     * @return the startDate
     */
    public long getStartDate() {
        return startDate;
    }

    /**
     * @param startDate the startDate to set
     */
    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    /**
     * @return the endDate
     */
    public long getEndDate() {
        return endDate;
    }

    /**
     * @param endDate the endDate to set
     */
    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    /**
     * @return the idAdmission
     */
    public long getIdAdmission() {
        return idAdmission;
    }

    /**
     * @param idAdmission the idAdmission to set
     */
    public void setIdAdmission(long idAdmission) {
        this.idAdmission = idAdmission;
    }

    /**
     * @return the idTemplate
     */
    public long getIdTemplate() {
        return idTemplate;
    }

    /**
     * @param idTemplate the idTemplate to set
     */
    public void setIdTemplate(long idTemplate) {
        this.idTemplate = idTemplate;
    }

    /**
     * @return the idText
     */
    public long getIdText() {
        return idText;
    }

    /**
     * @param idText the idText to set
     */
    public void setIdText(long idText) {
        this.idText = idText;
    }

    /**
     * @return the tableName
     */
    public String getTableName() {
        return tableName;
    }

    /**
     * @param tableName the tableName to set
     */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    /**
     * @return the persistType
     */
    public int getPersistType() {
        return persistType;
    }

    /**
     * @param persistType the persistType to set
     */
    public void setPersistType(int persistType) {
        this.persistType = persistType;
    }

    /**
     * @return the idCapAdmProfCheckin
     */
    public long getIdCapAdmProfCheckin() {
        return idCapAdmProfCheckin;
    }

    /**
     * @param idCapAdmProfCheckin the idCapAdmProfCheckin to set
     */
    public void setIdCapAdmProfCheckin(long idCapAdmProfCheckin) {
        this.idCapAdmProfCheckin = idCapAdmProfCheckin;
    }

    /**
     * @return the created
     */
    public long getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(long created) {
        this.created = created;
    }

    /**
     * @return the profName
     */
    public String getProfName() {
        return profName;
    }

    /**
     * @param profName the profName to set
     */
    public void setProfName(String profName) {
        this.profName = profName;
    }

    /**
     * @return the templateName
     */
    public String getTemplateName() {
        return templateName;
    }

    /**
     * @param templateName the templateName to set
     */
    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    /**
     * @return the patientShortName
     */
    public String getPatientShortName() {
        return patientShortName;
    }

    /**
     * @param patientShortName the patientShortName to set
     */
    public void setPatientShortName(String patientShortName) {
        this.patientShortName = patientShortName;
    }

    /**
     * @return the storageKey
     */
    public String getStorageKey() {
        return storageKey;
    }

    /**
     * @param storageKey the storageKey to set
     */
    public void setStorageKey(String storageKey) {
        this.storageKey = storageKey;
    }

    /**
     * @return the html
     */
    public String getHtml() {
        return html;
    }

    /**
     * @param html the html to set
     */
    public void setHtml(String html) {
        this.html = html;
    }

    /**
     * @return the varNamesList
     */
    public ArrayList<String> getVarNamesList() {
        return varNamesList;
    }

    /**
     * @param varNamesList the varNamesList to set
     */
    public void setVarNamesList(ArrayList<String> varNamesList) {
        this.varNamesList = varNamesList;
    }

    /**
     * @return the booleanVarNamesList
     */
    public ArrayList<String> getBooleanVarNamesList() {
        return booleanVarNamesList;
    }

    /**
     * @param booleanVarNamesList the booleanVarNamesList to set
     */
    public void setBooleanVarNamesList(ArrayList<String> booleanVarNamesList) {
        this.booleanVarNamesList = booleanVarNamesList;
    }

    /**
     * @return the hmFormFieldTypes
     */
    public HashMap<String,String> getHmFormFieldTypes() {
        return hmFormFieldTypes;
    }

    /**
     * @param hmFormFieldTypes the hmFormFieldTypes to set
     */
    public void setHmFormFieldTypes(HashMap<String,String> hmFormFieldTypes) {
        this.hmFormFieldTypes = hmFormFieldTypes;
    }
    
    ////////////////////////////////////////////////////////////////////////////
    //  BEGIN - Code for persit big Strings
    ////////////////////////////////////////////////////////////////////////////

    final int STRING_SIZE_LIMIT = 32 * 1024;    
    final private String STO_PREFIX = "StringTDaddEvolutionOfflineRef_";
//        new StringBuilder()
//            .append(IwOfflineCache.getInstance().getEvolOfflineStoKeyPrefix())
//            .append(IwOfflineCache.getInstance().DELIMITER)
//            .append("LongStringTDaddEvolutionOfflineRef_")
//            .toString() ;
    private void myWriteUTF(
            String strIn,
            DataOutputStream out) throws IOException {
        
        if (strIn != null && strIn.length() > STRING_SIZE_LIMIT) {
            String stoRef = createStorageRef();
            ArrayList<String> breakedStr = breakString(strIn, STRING_SIZE_LIMIT);
            StringBuilder sbPointers = new StringBuilder();
            int count = 0;
            for (String s : breakedStr) {
                String ref = stoRef + "_" + count; 
                boolean b = Storage.getInstance().writeObject(ref, s);
                if (b) {
                    sbPointers.append(ref).append("|");
                    count++;
                }
            }
            Util.writeUTF(sbPointers.toString(), out);
            System.out.println(sbPointers.toString());
        }
        else {
            Util.writeUTF(strIn, out);
        }    
    }
    
     
    private String createStorageRef() {
        return this.STO_PREFIX + new Date().getTime();
    } 
    
    private String myReadUTF(DataInputStream in) throws IOException {
        
        String sIn = Util.readUTF(in);
        StringBuilder sbResp = new StringBuilder();
        if (sIn != null && sIn.startsWith(this.STO_PREFIX)) {
            
            StringTokenizer st = new StringTokenizer(sIn,"|");
            String tokenPointer;
            while (st.hasMoreTokens()) {
                tokenPointer = st.nextToken();
                if (tokenPointer.startsWith(this.STO_PREFIX)) {
                    sbResp.append(
                        Storage.getInstance().readObject(tokenPointer)
                    );
                }
            }
            //System.out.println("Recuperado:\n" + sbResp.toString());
            return sbResp.toString();
            
        }
        else {
            
            return sIn;
            
        }
        
    }

    private static ArrayList<String> breakString(String sIn, int sizeLimit) {
        ArrayList<String> aList = new ArrayList<String>();
        int count = 0;
        StringBuilder sb = new StringBuilder();
        int size = sIn.length();
        for (int i = 0; i < size; i++) {
            sb.append(sIn.charAt(i));
            count++;
            if (sb.length() >= sizeLimit) {
                aList.add(sb.toString());
                sb = new StringBuilder();
                count = 0;
            }
        }
        aList.add(sb.toString());
        return aList;
    }

    ////////////////////////////////////////////////////////////////////////////
    //  END - Code for persit big Strings
    ////////////////////////////////////////////////////////////////////////////

    // This attibute doesn need tobe persisted
    // It is necessary only to mail this file to Iw Support. 
    private String fileName;
    public void setFileName(String name) {
        this.fileName = name;
    }
    public String getFileName() {
        return this.fileName;
    }
    
}
