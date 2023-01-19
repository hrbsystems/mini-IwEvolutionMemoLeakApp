package com.iw.iwmobile.extensions._fakeServerResponses;

import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.iw.iwmobile.comm.MobRecordset;

import java.io.IOException;
import java.io.InputStream;
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

    protected Map<String, MobRecordset> createResult(String jsonResp) {
        return new HashMap<String, MobRecordset>();
    }

}
