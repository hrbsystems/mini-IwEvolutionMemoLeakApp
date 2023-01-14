/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.iw.iwmobile.comm;

import com.codename1.io.Externalizable;
import com.codename1.io.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author helio-ubu1404
 */
public class MobRow implements Externalizable {

    
    public List<MobField> fields;
    
    public MobRow() {
        this.fields = new ArrayList<MobField>();
    }

    public MobRow(HashMap fieldsMap) {
        
        this.fields = new ArrayList<MobField>();
        
        ArrayList fieldList = (ArrayList) fieldsMap.get("fields");
        MobField f;
        for (Object fieldMap : fieldList ) {
            f = new MobField((HashMap) fieldMap);
            addField(f);
        }
    }
    
    public boolean changed() {
        boolean value = false;
        for (MobField f : fields) {
            value = f.changed();
            if (value) {
                break;
            }
        }
        return value;
    }
    
    public void addField(MobField f) {
        this.fields.add(f);
    }

    String toJson() {
        
        if (fields == null || fields.isEmpty()) {
            return "{\"fields\":[]}"; // Empty MobRecordset ===> {"rows":[]}
        }
        
        StringBuilder resp = new StringBuilder(256);
        
        resp.append("{\"fields\":[");
        
        MobField f;
        for (Iterator<MobField> iFields = fields.iterator(); iFields.hasNext();) {
            f = iFields.next();
            resp.append(f.toJson());
            if (iFields.hasNext()) {
                resp.append(",");
            }
        }

        resp.append("]}"); 

        return resp.toString();
        
    }
    
    public MobField field(String fName) {
        for (MobField f : this.fields) {
            if (f.getName().equalsIgnoreCase(fName)) {
                return f;
            }
        }
        return null;
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
        Util.writeObject(fields, out);
    }

    @Override
    public void internalize(int version, DataInputStream in)
            throws IOException {
        this.fields = (List<MobField>) Util.readObject(in);
    }

    @Override
    public String getObjectId() {
        return "MobRow";
    }
    
}
