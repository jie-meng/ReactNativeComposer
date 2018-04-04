package com.jmengxy.rndroid.react_native;


import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;
import com.jmengxy.rndroid.react_native.keyevent_module.KeyEventPackage;

import java.util.ArrayList;
import java.util.List;

public class ReactApplicationDelegate implements ReactApplication {
    private final ReactNativeHost mReactNativeHost;

    public ReactApplicationDelegate(Application application, final List<ReactPackage> additionalPackages) {
        mReactNativeHost = new ReactNativeHost(application) {
            @Override
            protected List<ReactPackage> getPackages() {
                ArrayList<ReactPackage> reactPackages = new ArrayList<>();
                reactPackages.add(new MainReactPackage());
                reactPackages.add(new KeyEventPackage());
                reactPackages.addAll(additionalPackages);
                return reactPackages;
            }

            @Override
            protected String getJSMainModuleName() {
                return "index";
            }

            @Override
            public boolean getUseDeveloperSupport() {
                return false;
            }
        };
        SoLoader.init(application, false);
    }

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
    }
}