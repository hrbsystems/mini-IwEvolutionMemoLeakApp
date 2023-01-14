/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iw.iwmobile.comm;

// Commented. For this mini-app we dont't need to generate jsons payloads
import ca.weblite.codename1.json.JSONObject;
import com.codename1.io.Externalizable;
import com.codename1.io.Storage;
import com.codename1.io.Util;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.util.StringUtil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

/**
 *
 * @author helio-ubu1404
 */
public class MobField implements Externalizable {
    
    public static final String STRING  = "String";
    public static final String LONG    = "Long";
    public static final String INTEGER = "Integer";
    public static final String FLOAT   = "Float";
    public static final String DOUBLE  = "Double";
    public static final String DATE    = "Date";
    public static final String UNKNOWN = "Unknown";
    
    static final public String SEARCH_OPERATOR_EQUAL         = "=";
    static final public String SEARCH_OPERATOR_LESS          = "<";
    static final public String SEARCH_OPERATOR_GREATER       = ">";
    static final public String SEARCH_OPERATOR_LESS_EQUAL    = "<=";
    static final public String SEARCH_OPERATOR_GREATER_EQUAL = ">=";
    static final public String SEARCH_OPERATOR_IS_NULL       = "is null";
    static final public String SEARCH_OPERATOR_IS_NOT_NULL   = "is not null";    

    private String name;
    private String type;
    private String value; // always has a string representation of the object value. Olny one exception is null value
    private String searchOperator;  // Only accept SEARCH_OPERATOR constants values. 
    
    private boolean changed = false;
    private boolean readOnly = false;

    public MobField() {
    }
    
    public MobField(
            String name,
            String type,
            Object value,
            String searchOperator) {
        
        this.name = name;
        this.type = type;
        this.value = null;
        this.searchOperator = searchOperator;
        setType(value);
        setValue(value);
        changed = false;           
    } 
    
    public MobField(String name, String type, Object value) {
        this.name = name;
        this.type = type;
        this.value = null;
        this.searchOperator = null;
        setType(value);
        setValue(value);
        changed = false;        
    }
    
    public String getName() {
        return this.name.toString();
    } 
    
    public void setType(Object value) {
        if (value instanceof String) {
            this.type = STRING;
        }
        else if (value instanceof Integer) {
            this.type = INTEGER;
        }
        else if (value instanceof Long) {
            this.type = LONG;
        }
        else if (value instanceof Float) {
            this.type = FLOAT;
        }
        else if (value instanceof Double) {
            this.type = DOUBLE;
        }
        else if (value instanceof Date) {
            this.type = DATE;
        }
        else {
            if (type == null) {
                this.type = UNKNOWN;
            }
        }
    }
    
    public String getType() {
        return this.type.toString();
    }
    
    public String getValue() {
        return this.value;
    }
    
    public boolean changed() {
        return this.changed;
    }

    public void setChanged(boolean value) {
        if (isReadOnly()) {
            this.changed = false;
        } else {
            this.changed = value;
        }
    }

    public boolean isReadOnly() {
        return this.readOnly;
    }

    public void setReadOnly(boolean value) {
        this.readOnly = value;
    }
    
    public void setValue(Object newValue) {
        String oldValue = this.value;
        if (newValue != null) {
            if (newValue instanceof Date) {
//                this.value = new Long(((Date) newValue).getTime()).toString();
                this.value = new SimpleDateFormat("ddMMyyyyHHmmss").format((Date) newValue);
            }
            else {
                this.value = newValue.toString();
            }
            if (!this.value.equals(oldValue) && !isReadOnly()) {
                changed = true;
            }
        } else {
            if (this.value != null && !isReadOnly()) {
                changed = true;
            }
            this.value = null;
        }
    }
    
    public String getSearchOperator() {
        return this.searchOperator;
    }
    
    public Object objValue() {
        
        if (this.value == null) return null;
        
        if (this.type.equals(STRING)) {
            return this.value;
        }
        if (this.type.equals(INTEGER)) {
            return Integer.parseInt(this.value);
        }
        if (this.type.equals(LONG)) {
            return Long.parseLong(this.value);
        }
        if (this.type.equals(FLOAT)) {
            return Float.parseFloat(this.value);
        }
        if (this.type.equals(DOUBLE)) {
            return Double.parseDouble(this.value);
        }
        if (this.type.equals(DATE)) {
            try {
                // return new Date(Long.parseLong(this.value));
                // return new SimpleDateFormat("ddMMyyyyHHmmss").parse(this.value);
                // Paramos de usar o SimpleDateFormat parse, porque em determinando momento
                // possivelmente apos atualizacao de libs do codename,  o parse da data zero
                // "30121899000000" gerava um DATE com diferença de segundos em relação a data 
                // representada pela String
                
                // O metodo parseStringToDate implementado obtem o Date partindo de um Calendar
                return parseStringToDate();
            } catch (Exception ex) {
                return null;
            }
        }
        else {
            return this.type;
        }

    }
    
    private Date parseStringToDate() throws Exception {
        String day = value.substring(0, 2);
        String month = value.substring(2, 4);
        String year = value.substring(4, 8);
        String hour = value.substring(8,10);
        String minute = value.substring(10,12);
        String second = value.substring(12, 14);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, Integer.parseInt(year));
        c.set(Calendar.MONTH, Integer.parseInt(month)-1);
        c.set(Calendar.DAY_OF_MONTH, Integer.parseInt(day));
        c.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        c.set(Calendar.MINUTE, Integer.parseInt(minute));
        c.set(Calendar.SECOND, Integer.parseInt(second));
        return c.getTime();
    }
    
    public String toJson() {

        String sValue;

        if (this.value == null) {
            sValue = "null";
        }
        else {
            // sValue = quotes(this.value); old impl. is only this line
            sValue = _adjustJsonVarValue(this.value);
            sValue = quotes(sValue);
        }
        
        return (this.searchOperator == null)? 
            "{"  + quotes("name")  + ":" + quotes(this.name) + "," 
                 + quotes("type")  + ":" + quotes(this.type) + ","
                 + quotes("value") + ":" + sValue + "}" 
            :
            "{"  + quotes("name")  + ":" + quotes(this.name) + "," 
                 + quotes("type")  + ":" + quotes(this.type) + ","
                 + quotes("value") + ":" + sValue + ","
                 + quotes("searchOperator")  + ":" + quotes(this.searchOperator) + "}";

    }
    
    private String quotes(String s) {
        return "\"" + s + "\"";
    }
    

    public MobField(HashMap fieldsMap) {
        this.name  = (String) fieldsMap.get("name");
        this.type  = (String) fieldsMap.get("type");
        this.value = (String) fieldsMap.get("value");
    }
    
    
    public String _adjustJsonVarValue(String s) {
        
        String resp = "";
        
        try {
            JSONObject oJson = new JSONObject();
            oJson.put("value", s);
            resp = oJson.toString();
            resp = resp.substring(10, resp.length()-2);
        }
        catch (Exception e) {
            resp = s;
        }
        
        return resp;
    }

    public String _adjustJsonVarValue_old(String aText){

        final StringBuilder sb = new StringBuilder();

        for (char c : aText.toCharArray()) {

            if( c == '\"' ){
                sb.append("\\\"");
            }
            else if(c == '\\'){
                sb.append("\\\\");
            }
            else if(c == '/'){
                sb.append("\\/");
            }
            else if(c == '\b'){
                sb.append("\\b");
            }
            else if(c == '\f'){
                sb.append("\\f");
            }
            else if(c == '\n'){
                sb.append("\\n");
            }
            else if(c == '\r'){
                sb.append("\\r");
           }
            else if(c == '\t'){
                sb.append("\\t");
            }
            else {
             //the char is not a special one
             //add it to the result as is
                sb.append(c);
            }    

        }

        // Additional protections
        String s = StringUtil.replaceAll(sb.toString(), "\\2",  "/2");
        s = StringUtil.replaceAll(s, "\\3",  "/3");
        s = StringUtil.replaceAll(s, "\\4",  "/4");
        s = StringUtil.replaceAll(s, "\\5",  "/5");
        s = StringUtil.replaceAll(s, "\\6",  "/6");
        s = StringUtil.replaceAll(s, "\\7",  "/7");
        s = StringUtil.replaceAll(s, "\\8",  "/8");
        s = StringUtil.replaceAll(s, "\\9",  "/9");

        //return sb.toString();  
        return s;

     } 
    
    /**
     * Unfortunately IW-CARE allows peaces of HTML code inside variable's values.
     * This causes a lot of bad collateral effects.
     * In addition the JSON format don't allow quotations marks inside STRING contents.
     * Quotation marks are used as delimiter and breaks the variable value causing mess. 
     * Here 2 protections was coded:
     * 1) scape char \ used before all quotes
     * 2) change all carriage return by empty String.
     * 
     * @param value
     * String value of IW-HTML Variable 
     * 
     * @return
     *  the variable value with adequate substitutions. 
     * 
     */    
    private String _adjustJsonVarValue_old_old(String value) {
        
        //return value.replaceAll(Pattern.quote("\""), "\\\\\"").replaceAll(Pattern.quote("\n"), "");
        //return value.replaceAll("\\Q\"\\E", "\\\\\"").replaceAll(Pattern.quote("\n"), "");
        
        StringBuilder sb = new StringBuilder();
        for (char c : value.toCharArray()) {
            if (c == '"') {
                sb.append("\\").append(c);
            }
            // Backslash cause problems in communication layer
            // Currently all backslash caracteres are substituted by slash
            // in IwMobile communication layer.
            else if (c == '\\') {
                //sb.append("/");
                sb.append("\\\\");
            }
            
            else if (c == '\n') {
                // do nithing
            }
            else {
                sb.append(c);
            }
        }
        
        return sb.toString();

    }
    
     
//  /**
//     * Unfortunately IW-CARE allows peaces of HTML code inside variable's values.
//     * This causes a lot of bad collateral effects.
//     * In addition the JSON format don't allow quotations marks inside STRING contents.
//     * Quotation marks are used as delimiter and breaks the variable value causing mess. 
//     * Here 2 protections was coded:
//     * 1) scape char \ used before all quotes
//     * 2) change all carriage return by empty String.
//     * 
//     * @param value
//     * String value of IW-HTML Variable 
//     * 
//     * @return
//     *  the variable value with adequate substitutions. 
//     * 
//     */    
//    protected String _adjustJsonVarValue(String value) {
//        String resp  = value
//                .replaceAll(Pattern.quote("\""), "\\\\\"")
//                .replaceAll("\n", "\\\\\\n")
//                .replaceAll("\r", "\\\\\\r")
//                .replaceAll(Pattern.quote("\\1"), "/1")
//                .replaceAll(Pattern.quote("\\2"), "/2")
//                .replaceAll(Pattern.quote("\\3"), "/3")
//                .replaceAll(Pattern.quote("\\4"), "/4")
//                .replaceAll(Pattern.quote("\\5"), "/5")
//                .replaceAll(Pattern.quote("\\6"), "/6")
//                .replaceAll(Pattern.quote("\\7"), "/7")
//                .replaceAll(Pattern.quote("\\8"), "/8")
//                .replaceAll(Pattern.quote("\\9"), "/9")
//                .trim();
//        
//        return resp; //MobField._scapeCharsProtection(resp); 
//    }
// 
    
    ////////////////////////////////////////////////////////////////////////////
    //  Begin - Codde of Externalizable Interface
    ////////////////////////////////////////////////////////////////////////////
    
    @Override
    public int getVersion() {
        return 1;
    }

    @Override
    public void externalize(DataOutputStream out) throws IOException {
        Util.writeUTF(getName(), out);
        Util.writeUTF(getType(), out);
        myWriteUTF(getValue(), out);
        Util.writeUTF(getSearchOperator(), out);
    }

    @Override
    public void internalize(
            int version,
            DataInputStream in) throws IOException {
        
        this.name = Util.readUTF(in);
        this.type = Util.readUTF(in);
        this.value = myReadUTF(in);
        this.searchOperator = Util.readUTF(in);

    }

    @Override
    public String getObjectId() {
        return "MobField";
    }
     
    ////////////////////////////////////////////////////////////////////////////
    //  End - Codde of Externalizable Interface
    ////////////////////////////////////////////////////////////////////////////
    
    ////////////////////////////////////////////////////////////////////////////
    //  BEGIN - Code for persit big Strings
    ////////////////////////////////////////////////////////////////////////////

    final int STRING_SIZE_LIMIT = 32 * 1024;
    final private String STO_PREFIX = "StringMobFieldRef_";
    
// this value for STO_PREFIX cause nulpointer at login time    
//     
//        new StringBuilder() 
//            .append(IwOfflineCache.getInstance().getEvolOfflineStoKeyPrefix())
//            .append(IwOfflineCache.getInstance().DELIMITER)
//            .append("StringMobFieldRef_")
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
    
    ArrayList<String> persistValueFileNamesList = new ArrayList<String>();
    private String myReadUTF(DataInputStream in) throws IOException {
        
        String sIn = Util.readUTF(in);
        StringBuilder sbResp = new StringBuilder();
        if (sIn != null && sIn.startsWith(this.STO_PREFIX)) {
            
            StringTokenizer st = new StringTokenizer(sIn,"|");
            String tokenPointer;
            while (st.hasMoreTokens()) {
                tokenPointer = st.nextToken();
                if (tokenPointer.startsWith(this.STO_PREFIX)) {
                    this.persistValueFileNamesList.add(tokenPointer);
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
    
    public ArrayList<String> getAllPersistValueFilesNamesList() {
        return this.persistValueFileNamesList;
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
    
    
}
