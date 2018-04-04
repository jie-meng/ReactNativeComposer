package com.jmengxy.rndroid.rn_bridge;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;
import com.facebook.react.bridge.WritableMap;

public class RNModule extends ReactContextBaseJavaModule {

    private final RNCallback rnCallback;

    public RNModule(ReactApplicationContext reactContext, RNCallback rnCallback) {
        super(reactContext);
        this.rnCallback = rnCallback;
    }

    @Override
    public String getName() {
        return "RNManager";
    }

    @ReactMethod
    void getUsername(Callback callback) {
        WritableMap map = Arguments.createMap();
        map.putString("username", rnCallback.getUsername());

        callback.invoke(null, map);
    }
}
