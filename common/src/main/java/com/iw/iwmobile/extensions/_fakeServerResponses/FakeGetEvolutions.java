package com.iw.iwmobile.extensions._fakeServerResponses;

import com.codename1.io.File;
import com.codename1.io.BufferedInputStream;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Util;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import com.codename1.util.regex.RE;
import com.iw.iwmobile.comm.IwHttpRequesterCallBack;
import com.iw.iwmobile.comm.MobRecordset;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class FakeGetEvolutions extends FakeBase {

    private final String RESOURCE_NAME = "/1_getEvolutions_898.json";
    public void execute(IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
        String jsonResp = loadMockedJsonResponseFromResources(RESOURCE_NAME);
        Map<String,MobRecordset> result = createResult(jsonResp);
        callback.onSuccess(result);
    }

}
