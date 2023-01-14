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

/**
 *
 * @author helio-ubu1404
 */
public class AccessConfig implements Externalizable{
    
    private String protocol = "http";
    private long id = -1;
    private String name;
    private String host;
    private int port;
    private String profile;
    private String userName;
    private String password;
    
    // Empty Contructor = Mecessary for Storage
    public AccessConfig() {
    }

    public AccessConfig(
            String name,
            String host,
            int port,
            String profile,
            String userName,
            String password,
            boolean https) {
        
        setName(name);
        setHost(host);
        setPort(port);
        setProfile(profile);
        setUserName(userName);
        setPassword(password);
        this.protocol = (https)? "https":"http";
        
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
    private void setName(String name) {
        this.name = name;
    }


    /**
     * @return the profile
     */
    public String getProfile() {
        return profile;
    }

    /**
     * @param profile the profile to set
     */
    private void setProfile(String profile) {
        this.profile = profile;
    }

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    private void setUserName(String userName) {
        this.userName = userName;
    }

    public int getVersion() {
        return 2;
    }

    public void externalize(DataOutputStream out) throws IOException {
        out.writeLong(getId());
        Util.writeUTF(getName(), out);
        Util.writeUTF(getHost(), out);
        out.writeInt(port);
        Util.writeUTF(getProfile(), out);
        Util.writeUTF(getUserName(), out);
        Util.writeUTF(getPassword(), out);
        Util.writeUTF(getProtocol(), out);
    }

    public void internalize(
            int version,
            DataInputStream in) throws IOException {

        if (version == 1) {
            setId(in.readLong());
            setName(Util.readUTF(in));
            setHost(Util.readUTF(in));
            setPort(in.readInt());
            setProfile(Util.readUTF(in));
            setUserName(Util.readUTF(in));
            setPassword(Util.readUTF(in));
        }
        else {
            setId(in.readLong());
            setName(Util.readUTF(in));
            setHost(Util.readUTF(in));
            setPort(in.readInt());
            setProfile(Util.readUTF(in));
            setUserName(Util.readUTF(in));
            setPassword(Util.readUTF(in));
            setProtocol(Util.readUTF(in));
        }
    }

    public String getObjectId() {
        return "AccessConfig";
    }

    /**
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * @param host the host to set
     */
    private void setHost(String host) {
        this.host = host;
    }

    /**
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port the port to set
     */
    private void setPort(int port) {
        this.port = port;
    }

    @Override
    public String toString() {
        return getName();
    }

    public String getUrl(String s, boolean isHttps) {
        
        // IMPORTANTE
        // On IwMoble dev., cause historical problems
        // exists 2 important methods used to compound 
        // URL to reach the Server.
        // getUrl (this one, exists only here.
        // getBaseURL() that exists in all classes that implements
        // IwServerCallerInterface.
        //
        // Currently, Some clients use URL without port number.
        // By convention port = 0 means that
        // the port number doesn't be included in URL.
        //
        // This modifiction must be efectivated in all
        // methods that permorms URL contructions.  
        // That is, this classe and all IwServiceCallers
        
        int port = getPort();
        String strPort = (port == 0)? "" : ":" + port; 
        
        return new StringBuilder(getProtocol())
                .append("://")
                .append(getHost())
                //.append(":")
                //.append(getPort())
                .append(strPort) 
                .append(s)
                .toString();
        
    }
    
    public String getBaseURL() {

        // Some clients use URL without port number.
        // By convention port = 0 means that
        // the port number doesn't be included in URL.
        int port = getPort();
        String strPort = (port == 0)? "" : ":" + port; 
        
        StringBuilder resp = new StringBuilder();
        resp.append(getProtocol())
            .append("://")
            .append(getHost())
            //.append(":")
            //.append(aConfig.getPort())
            .append(strPort)    
            .append("/IwRestAPI/ws");
        return resp.toString();
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
    private void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return the protocol
     */
    public String getProtocol() {
        return protocol;
    }

    /**
     * @param protocol the protocol to set
     */
    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }
    
}
