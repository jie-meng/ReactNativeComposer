package com.jmengxy.rndroid.react_native.keyevent_module;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.List;

public class KeyEventPackage implements ReactPackage {
    public KeyEventPackage() {
    }

    @Override
    public List<NativeModule> createNativeModules(ReactApplicationContext reactContext) {
        ArrayList<NativeModule> nativeModules = new ArrayList<>();
        nativeModules.add(KeyEventModule.initKeyEventModule(reactContext));
        return nativeModules;
    }

    @Override
    public List<ViewManager> createViewManagers(ReactApplicationContext reactContext) {
        ArrayList<ViewManager> viewManagers = new ArrayList<>();
        viewManagers.add(new DelKeyEventTextInputManager());
        return viewManagers;
    }
}