package com.iw.iwmobile.extensions._fakeServerResponses;

import com.iw.iwmobile.comm.IwHttpRequesterCallBack;
import com.iw.iwmobile.comm.MobRecordset;

import java.util.Map;

// This class aims to mock a IW server call
// The method "execute" here, returns the same json response
// that is returned when the APP needs to open a Add Evolution form.
// this response always returns the dame data (when the user is usuario.mobile accessing profile dbIwIncoway)


public class FakeGetData4AddEvolution extends FakeBase {

    private final String RESOURCE_NAME = "/2_getData4AddEvolution_898.json";

    public void execute(IwHttpRequesterCallBack<Map<String, MobRecordset>> callback) {
        String jsonResp = loadMockedJsonResponseFromResources(RESOURCE_NAME);
        Map<String,MobRecordset> result = createResult(jsonResp);
        callback.onSuccess(result);
    }

}
