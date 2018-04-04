package com.jmengxy.rndroid.react_native;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.modules.core.DefaultHardwareBackBtnHandler;
import com.jmengxy.rndroid.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Base Fragment for React Native applications.
 */
public abstract class ReactFragment extends Fragment
        implements DefaultHardwareBackBtnHandler {

    protected ReactFragmentDelegate mDelegate;

    private boolean backTriggered = false;

    protected ReactFragment() {

    }

    /**
     * Returns the name of the main component registered from JavaScript.
     * This is used to schedule rendering of the component.
     * e.g. "MoviesApp"
     */
    protected
    @Nullable
    String getMainComponentName() {
        return null;
    }

    /**
     * Returns the additional reactnative packages.
     */
    protected @NonNull
    List<ReactPackage> getAdditionalPackages() {
        return new ArrayList<>();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mDelegate = createReactFragmentDelegate();
    }

    /**
     * Called at construction time, override if you have a custom delegate implementation.
     */
    protected ReactFragmentDelegate createReactFragmentDelegate() {
        return new ReactFragmentDelegate(this, getMainComponentName(), getAdditionalPackages());
    }

    @Override
    public void onPause() {
        super.onPause();
        mDelegate.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        mDelegate.onResume();
        backTriggered = false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mDelegate.onDestroy();
    }

    public boolean handleBackPressed() {
        if (backTriggered)
            return false;
        else
            mDelegate.onBackPressed();
        return true;
    }

    @Override
    public void invokeDefaultOnBackPressed() {
        backTriggered = true;
        getActivity().onBackPressed();
    }

    protected final ReactNativeHost getReactNativeHost() {
        return mDelegate.getReactNativeHost();
    }

    protected final ReactInstanceManager getReactInstanceManager() {
        return mDelegate.getReactInstanceManager();
    }

    protected final void loadApp(String appKey) {
        mDelegate.loadApp(appKey);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_react_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDelegate.onCreate(savedInstanceState);
    }

    public void setContentView(ReactRootView rootView) {
        ViewGroup container = getView().findViewById(R.id.content_frame);
        container.removeAllViews();
        container.addView(rootView);
    }
}