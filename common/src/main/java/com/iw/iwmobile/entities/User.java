/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iw.iwmobile.entities;

import com.codename1.io.Externalizable;
import com.codename1.io.Util;
import com.iw.iwmobile.comm.MobRecordset;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author helio-mint32-2
 */
public class User implements Externalizable {
    
    private String id;
    private String name;
    private String password;
    private long idPerson;
    private boolean sysAdministrator;
    private boolean canDoParameterizedSearch = false; // not serialized yet
    private boolean canDoParameterizedSearch2 = false; // not serialized yet
    private boolean canDoPrescription = false; // not serialized yet
    private boolean canDoException = false; // not serialized yet
    private boolean canViewContracts = false; // not serialized yet
    private boolean isMatDeliveryCtr = false; // not serialized yet
    private boolean canAccessMyPatients = true;
    private List<String> secRules;
    
    private long idProfessional = -1L;
    private int registryType = -1;
    private String registryNumber = "";
    private int digitalSign = 0;
    
    private MobRecordset rsHealthProviders;
    public void setRsRealthProviders (MobRecordset rs) {
        this.rsHealthProviders = rs;
    }
    public MobRecordset getRsHealthProviders() {
        return this.rsHealthProviders;
    }

    private MobRecordset rsDepartments;
    public void setRsDepartments (MobRecordset rs) {
        this.rsDepartments = rs;
    }
    public MobRecordset getRsDepartments() {
        return this.rsDepartments;
    }
    
    
    public User() {
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
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the secRules
     */
    public List<String> getSecRules() {
        return secRules;
    }

    /**
     * @param secRules the secRules to set
     */
    public void setSecRules(List<String> secRules) {
        this.secRules = secRules;
    }

    /**
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the idPerson
     */
    public long getIdPerson() {
        return idPerson;
    }

    /**
     * @param idPerson the idPerson to set
     */
    public void setIdPerson(long idPerson) {
        this.idPerson = idPerson;
    }

    /**
     * @return the isSysAdministrator
     */
    public boolean isSysAdministrator() {
        return sysAdministrator;
    }

    /**
     * @param isSysAdministrator the isSysAdministrator to set
     */
    public void setSysAdministrator(boolean isSysAdministrator) {
        this.sysAdministrator = isSysAdministrator;
    }

    @Override
    public int getVersion() {
        return 2;
    }

    @Override
    public void externalize(DataOutputStream out) throws IOException {
        Util.writeUTF(getId(), out);
        Util.writeUTF(getName(), out);
        Util.writeUTF(getPassword(), out);
        out.writeLong(getIdPerson());
        out.writeBoolean(isSysAdministrator());

        // Properties added in version 2
        out.writeLong(getIdProfessional());
        out.writeInt(getRegistryType());
        Util.writeUTF(getRegistryNumber(), out);
        
        Util.writeObject(secRules, out);
    }

    @Override
    public void internalize(int version, DataInputStream in) throws IOException {
        setId(Util.readUTF(in));
        setName(Util.readUTF(in));
        setPassword(Util.readUTF(in));
        setIdPerson(in.readLong());
        setSysAdministrator(in.readBoolean());
        
        if (version == 2) {
            // prorperties added in version 2
            setIdProfessional(in.readLong());
            setRegistryType(in.readInt());
            setRegistryNumber(Util.readUTF(in));
        }
        
        setSecRules((List<String>)Util.readObject(in));
        
        
    }

    @Override
    public String getObjectId() {
        return "User";
    }

    /**
     * @return the idProfessional
     */
    public long getIdProfessional() {
        return idProfessional;
    }
    
    

    /**
     * @param idProfessional the idProfessional to set
     */
    public void setIdProfessional(long idProfessional) {
        this.idProfessional = idProfessional;
    }

    /**
     * @return the registryType
     */
    public int getRegistryType() {
        return registryType;
    }

    /**
     * @param registryType the registryType to set
     */
    public void setRegistryType(int registryType) {
        this.registryType = registryType;
    }

    /**
     * @return the registryNumber
     */
    public String getRegistryNumber() {
        return registryNumber;
    }
    
    /**
     * @param registryNumber the registryNumber to set
     */
    public void setRegistryNumber(String registryNumber) {
        this.registryNumber = registryNumber;
    }

    public int getDigitalSign() {
        return digitalSign;
    }
    
    public void setDigitalSign(Integer digitalSign) {
        this.digitalSign = digitalSign;
    }
   
    public boolean getCanDoParameterizedSearch() {
        return this.canDoParameterizedSearch; 
    }

    public void setCanDoParameterizedSearch(boolean bValue) {
        this.canDoParameterizedSearch = bValue; 
    }

    public boolean getCanDoParameterizedSearch2() {
        return this.canDoParameterizedSearch2; 
    }

    public void setCanDoParameterizedSearch2(boolean bValue) {
        this.canDoParameterizedSearch2 = bValue; 
    }

    public boolean getCanDoException() {
        return this.canDoException;
    }
    
    public void setCanDoException(boolean bValue) {
        this.canDoException = bValue; 
    }
    
    public boolean getCanDoPrescription() {
        return this.canDoPrescription; 
    }

    public void setCanDoPrescription(boolean bValue) {
        this.canDoPrescription = bValue; 
    }
    
    public boolean getCanViewContracts() {
        return this.canViewContracts; 
    }

    public void setCanViewContracts(boolean bValue) {
        this.canViewContracts = bValue; 
    }

    public boolean getIsMatDeliveryCtr() {
        return this.isMatDeliveryCtr; 
    }

    public void setIsMatDeliveryCtr(boolean bValue) {
        this.isMatDeliveryCtr = bValue; 
    }
    
    /**
     * @return the canAccessMyPatients
     */
    public boolean getCanAccessMyPatients() {
        return canAccessMyPatients;
    }

    /**
     * @param canAccessMyPatients the canAccessMyPatients to set
     */
    public void setCanAccessMyPatients(boolean canAccessMyPatients) {
        this.canAccessMyPatients = canAccessMyPatients;
    }


}
