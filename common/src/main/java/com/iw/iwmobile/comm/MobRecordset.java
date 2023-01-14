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
public class MobRecordset  implements Externalizable {
    
    public List<MobRow> rows;

    public MobRecordset() {
        this.rows = new ArrayList<MobRow>();
    }

    public void addRow(MobRow r) {
        this.rows.add(r);
    }
  
    public boolean changed() {
        boolean value = false;
        for (MobRow r : rows) {
            value = r.changed();
            if (value) {
                break;
            }
        }
        return value;
    }
    
    public String toJson() {

      if (rows == null || rows.isEmpty()) {
          return "{\"rows\":[]}"; // Empty MobRecordset ===> {"rows":[]}
      }

     
      StringBuilder resp = new StringBuilder(512);
      
      resp.append("{\"rows\":["); 
      
      MobRow r;
      for (Iterator<MobRow> iRows = rows.iterator(); iRows.hasNext();) {
          r = iRows.next();
          resp.append(r.toJson());
          if (iRows.hasNext()) {
              resp.append(",");
          }
      }

      resp.append("]}"); 

      return resp.toString();

  }
    
  public MobRecordset(HashMap hmRows) {
      
    this.rows = new ArrayList<MobRow>();

    ArrayList rowList = (ArrayList) hmRows.get("rows");

    MobRow r;
    for (Object row : rowList) {
        r = new MobRow((HashMap) row);
        addRow(r);
    }
        
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
        Util.writeObject(rows, out);
    }

    @Override
    public void internalize(int version, DataInputStream in)
            throws IOException {
        this.rows = (List<MobRow>) Util.readObject(in);
    }

    @Override
    public String getObjectId() {
        return "MobRecordset";
    }
    
    public String showTxt() {
        String s = "";
        int cont = 0;
        for (MobRow r : rows) {
            s += "Row " + (cont++) + ":\n";
            for (MobField f : r.fields) {
                s += "  " + f.getName() + "(" + f.getType() + "): " + f.getValue() + " [changed="+ f.changed()+ "]\n";
            }
            s += "\n";
        }
        return s;
    }
    
    
    public static MobRecordset create(MobField[][] rsFields) {
        
        if (rsFields == null) {
            return  null;
        }
        
        MobRecordset rsResp = new MobRecordset();
        
        MobRow mobRow;
        for (MobField[] rFields : rsFields) {
            mobRow = new MobRow();
            for (MobField f : rFields) {
                mobRow.addField(f);
            }
            rsResp.rows.add(mobRow);
        }
        
        return rsResp;

    }
    
    
}
