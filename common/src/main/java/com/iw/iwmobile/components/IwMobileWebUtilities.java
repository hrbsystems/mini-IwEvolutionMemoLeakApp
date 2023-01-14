/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.iw.iwmobile.components;

import com.codename1.components.InfiniteProgress;
import com.codename1.io.ConnectionRequest;
import com.codename1.io.NetworkManager;
import com.codename1.io.URL;
import com.codename1.ui.Dialog;
import com.codename1.ui.events.ActionListener;
import com.codename1.util.regex.RE;
import com.codename1.util.regex.REUtil;
import com.iw.iwmobile.comm.IwCommException;
//import org.bouncycastle.crypto.digests.SHA256Digest;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;


/**
 *
 * @author mint
 */
public class IwMobileWebUtilities {
    
        
    public boolean downloadUrlToStorage(
            String iwUserRequester,
            String url,
            String fileName,
            boolean showProgress) {
        
        return downloadUrlTo(
                iwUserRequester,
                url,
                fileName,
                showProgress,
                false,
                true,
                null);
        
    }
        
    public boolean downloadUrlToFile(
            String iwUserRequester,
            String url,
            String fileName,
            boolean showProgress) {
        
        return downloadUrlTo(
                iwUserRequester,
                url,
                fileName,
                showProgress,
                false,
                false,
                null);
        
    }    
    
    private boolean downloadUrlTo(
            String iwUserRequester,
            String url,
            String fileName,
            boolean showProgress,
            boolean background,
            boolean storage,
            ActionListener callback) {
    
        try {
            return downloadUrlToImpl(
                iwUserRequester,
                url,
                fileName,
                showProgress,
                background,
                storage,
                null,
                callback);
        }
        catch (URISyntaxException | IwCommException e) {
            System.out.println("error in downloadUrlToImpl: " + e.getMessage());
        }
        return false;
    }
    
    public boolean downloadUrlToStorageOp(String iwUserRequester,
            String url,
            String fileName,
            boolean showProgress,
            String op){
        try {
            return downloadUrlToImpl(
                iwUserRequester,
                url,
                fileName,
                showProgress,
                false,
                true,
                op,
                null);
        }
        catch (URISyntaxException | IwCommException e) {
            System.out.println("error in downloadUrlToImpl_Op: " + e.getMessage());
        }
        return false;
    }

    private boolean downloadUrlToImpl(
            String iwUserRequester,
            String url,
            String fileName,
            boolean showProgress,
            boolean background,
            boolean storage,
            String op,
            ActionListener callback)
                throws URISyntaxException, IwCommException {
            
        String token = getDynamicToken(url, iwUserRequester);
        String encryptedToken = createEncrypetdToken(token);
        
        String urlWithDynanicToken = addTokenToUrl(url, encryptedToken);
        if (op!=null&&op.equals("1")){
            urlWithDynanicToken = addOpToUrl(urlWithDynanicToken, op);
        }
        ConnectionRequest cr = new ConnectionRequest();
        cr.setPost(false);
        cr.setFailSilently(true);
        cr.setReadResponseForErrors(false);
        cr.setDuplicateSupported(true);
        cr.setUrl(urlWithDynanicToken);
        if(callback != null) {
            cr.addResponseListener(callback);
        }
        if(storage) {
            cr.setDestinationStorage(fileName);
        } else {
            cr.setDestinationFile(fileName);
        }
        if(background) {
            NetworkManager.getInstance().addToQueue(cr);
            return true;
        } 
        if(showProgress) {
            InfiniteProgress ip = new InfiniteProgress();
            Dialog d = ip.showInfiniteBlocking();
            NetworkManager.getInstance().addToQueueAndWait(cr);
            d.dispose();
        } else {
            NetworkManager.getInstance().addToQueueAndWait(cr);
        }
        int rc = cr.getResponseCode();
        return rc == 200 || rc == 201;
    }
    

    // url model that must be returned:
    // getIwWeb1AppUrl(sUrl) + "/IdleServlet";
    private String getDynamicAccessTokenUrl(String sUrl)
            throws URISyntaxException {
        
        RE re = REUtil.createRE("/");
        String iwEvaluation = re.split(sUrl)[3];
                
        URL url = new URL(sUrl);
        String resp =
                url.getProtocol() + "://"
                + url.getHost() + ":" + url.getPort()
                + "/" + iwEvaluation 
                + "/IdleServlet";
        return resp;
        
    }    
    
    private String getDynamicToken(String sUrlWithQuery, String iwUserRequester)
            throws IwCommException, URISyntaxException {
        
        // Dynamictoken request must be a HTTP POST
        
        String DynamicTokenUrl = getDynamicAccessTokenUrl(sUrlWithQuery);
        
        ConnectionRequest cr = new ConnectionRequest();
        cr.setPost(true);
        cr.setFailSilently(true);
        cr.setReadResponseForErrors(false);
        cr.setDuplicateSupported(true);
        cr.setUrl(DynamicTokenUrl);
        cr.addArgument("Profile", getIwProfileFromUrl(sUrlWithQuery));
        cr.addArgument("IW_URL_USED_BY_RESQUESTER", sUrlWithQuery);
        cr.addArgument("IW_USER_REQUESTER", iwUserRequester);
        
                
        InfiniteProgress ip = new InfiniteProgress();
        Dialog d = ip.showInfiniteBlocking();
        NetworkManager.getInstance().addToQueueAndWait(cr);
        d.dispose();

        int rc = cr.getResponseCode();
        
        if (rc == 200 || rc == 201) {
            String resp = new String(cr.getResponseData());
            return resp;
        }
        
        throw new IwCommException(
            "IwCommExcpetion http code:"
            + rc
            + " returned in Endpoint: "
            + sUrlWithQuery
        );
        
    }
    
    private String getIwProfileFromUrl(String sUrl) {
        String resp = "";
        try {

            URL imageURL = new URL(sUrl);
            RE re = REUtil.createRE("&");
            RE re1 = REUtil.createRE("=");

            String urlQuery = imageURL.getQuery();
            String[] nameValuePars = re.split(urlQuery);

            for (String nameValue : nameValuePars) {
                String[] s = re1.split(nameValue);
                if("PROFILE".equals(s[0])) {
                    return s[1];
                }
            }
        } catch (URISyntaxException ex) {
            resp = "";
        }
        return resp;
    }

    private final String IW_ACCESS_TOKEN_PARAM_NAME = "AccessToken";
    private String addTokenToUrl(String url, String tokenValue) {        
        return url + "&" + IW_ACCESS_TOKEN_PARAM_NAME + "=" + tokenValue;
    }

    private final String IW_OP_PARAM_NAME = "op";
    private String addOpToUrl(String url, String opValue) {        
        return url + "&" + IW_OP_PARAM_NAME + "=" + opValue;
    }
    
    private String createEncrypetdToken(String token) {
        return encodeSHA256("DYNAMIC_" + token);
    }
    
    private String encodeSHA256(String s) {

        String encoded = "";

//        try{
//
//            SHA256Digest d = new SHA256Digest();
//            d.update(s.getBytes("ISO8859_1"), 0, s.getBytes("ISO8859_1").length);
//            byte[] result = new byte[d.getDigestSize()];
//            d.doFinal(result, 0);
//            encoded = getHexString(result);
//
//        } catch (UnsupportedEncodingException ueex) {}

        return encoded;
    }

    private String getHexString(byte[] digest) {

        String hexString = "";

        for (int i = 0; i < digest.length; i++) {

            int b = digest[i] & 0xff;
            String hex = Integer.toHexString(b);
            if (hex.length() == 1) {
                hexString += "0";
            }
            hexString += hex;

        }

        return hexString;

    }
    
        
}
