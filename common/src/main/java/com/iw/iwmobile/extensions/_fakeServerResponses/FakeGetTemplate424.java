package com.iw.iwmobile.extensions._fakeServerResponses;

import com.iw.iwmobile.comm.IwHttpRequesterCallBack;
import com.iw.iwmobile.comm.MobRecordset;

import java.util.HashMap;
import java.util.Map;

// This class aims to mock a IW server call
// The method "execute" here, returns the same json response
// that is returned when the APP needs to get all information about the iw-template ID = 424.
// this response always returns the data of iw-template ID = 424 in dbIwIncoway database

public class FakeGetTemplate424 extends FakeBase {

    private final String RESOURCE_NAME = "/4_getTemplate_424.json";
    public void execute(IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
        String jsonResp = loadMockedJsonResponseFromResources(RESOURCE_NAME);
        Map<String,MobRecordset> result = createResult(jsonResp);
        callback.onSuccess(result);
    }

}
