package com.iw.iwmobile.extensions._fakeServerResponses;

import com.codename1.io.JSONParser;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.iw.iwmobile.comm.MobRecordset;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class FakeBase {
    protected String loadMockedJsonResponseFromResources(String resourceName) {
        String resp = null;
        InputStream in =
                Display.getInstance().getResourceAsStream(Form.class, resourceName);

        if (in != null){
            try {
                resp = com.codename1.io.Util.readToString(in);
                in.close();
            } catch (IOException ex) {
                System.out.println(ex);
                resp = "Read Error";
            }
        }

        return resp;

    }

    protected Map<String, MobRecordset> createResult(String json) {
        Map<String, MobRecordset> resp = new HashMap<String, MobRecordset>();
        try {
            ByteArrayInputStream bais = new ByteArrayInputStream(json.getBytes("UTF-8"));
            InputStreamReader input = new InputStreamReader(bais);
            JSONParser p = new JSONParser();
            Map<String, Object> parsedJson = p.parseJSON(input);
            resp = buildResponseMap(parsedJson);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return resp;
    }

    private Map<String, MobRecordset> buildResponseMap(Map<String, Object> mapIn) {

        HashMap<String, MobRecordset> mapOut = new HashMap<String,MobRecordset>();

        MobRecordset rs;
        for(Map.Entry<String, Object> entry : mapIn.entrySet()) {
            mapOut.put(entry.getKey(), new MobRecordset((HashMap) entry.getValue()));
        }

        return mapOut;
    }


}
