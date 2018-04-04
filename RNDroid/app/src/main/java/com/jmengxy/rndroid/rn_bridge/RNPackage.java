package com.jmengxy.rndroid.rn_bridge;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.List;

public class RNPackage implements ReactPackage {

    private RNCallback rnCallback;

    public RNPackage(RNCallback rnCallback) {
        this.rnCallback = rnCallback;
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        ArrayList<NativeModule> nativeModules = new ArrayList<>();
        nativeModules.add(new RNModule(reactContext, rnCallback));
        return nativeModules;
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        return new ArrayList<>();
    }
}
