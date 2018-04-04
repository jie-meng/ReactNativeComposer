package com.jmengxy.rndroid.react_native;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.ReactRootView;
import com.facebook.react.bridge.Callback;
import com.facebook.react.modules.core.PermissionListener;

import java.util.List;

/**
 * Delegate class for {@link ReactFragment}.
 */
public class ReactFragmentDelegate {

    @Nullable
    private final ReactApplicationDelegate mReactApplicationDelegate;
    @Nullable
    private final ReactFragment mReactFragment;
    private final
    @Nullable
    String mMainComponentName;

    private
    @Nullable
    ReactRootView mReactRootView;
    private
    @Nullable
    PermissionListener mPermissionListener;
    private
    @Nullable
    Callback mPermissionsCallback;

    public ReactFragmentDelegate(ReactFragment reactFragment, @Nullable String mainComponentName, List<ReactPackage> additionalPackages) {
        mReactFragment = reactFragment;
        mMainComponentName = mainComponentName;
        mReactApplicationDelegate = new ReactApplicationDelegate((Application) reactFragment.getContext().getApplicationContext(), additionalPackages);
    }

    protected
    @Nullable
    Bundle getLaunchOptions() {
        return null;
    }

    protected ReactRootView createRootView() {
        return new ReactRootView(mReactFragment.getContext());
    }

    /**
     * Get the {@link ReactNativeHost} used by this app. By default, assumes
     * {@link Activity#getApplication()} is an instance of {@link ReactApplication} and calls
     * {@link ReactApplication#getReactNativeHost()}. Override this method if your application class
     * does not implement {@code ReactApplication} or you simply have a different mechanism for
     * storing a {@code ReactNativeHost}, e.g. as a static field somewhere.
     */
    protected ReactNativeHost getReactNativeHost() {
        return mReactApplicationDelegate.getReactNativeHost();
    }

    public ReactInstanceManager getReactInstanceManager() {
        return getReactNativeHost().getReactInstanceManager();
    }

    protected void onCreate(Bundle savedInstanceState) {
        boolean needsOverlayPermission = false;
        if (mMainComponentName != null && !needsOverlayPermission) {
            loadApp(mMainComponentName);
        }
    }

    protected void loadApp(String appKey) {
        if (mReactRootView != null) {
            throw new IllegalStateException("Cannot loadApp while app is already running.");
        }
        mReactRootView = createRootView();
        mReactRootView.startReactApplication(
                getReactNativeHost().getReactInstanceManager(),
                appKey,
                getLaunchOptions());
        mReactFragment.setContentView(mReactRootView);
    }

    protected void onPause() {
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager().onHostPause(mReactFragment.getActivity());
        }
    }

    protected void onResume() {
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager().onHostResume(
                    mReactFragment.getActivity(),
                    mReactFragment);
        }

        if (mPermissionsCallback != null) {
            mPermissionsCallback.invoke();
            mPermissionsCallback = null;
        }
    }

    protected void onDestroy() {
        if (mReactRootView != null) {
            mReactRootView.unmountReactApplication();
            mReactRootView = null;
        }
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager().onHostDestroy(mReactFragment.getActivity());
        }
    }

    public boolean onBackPressed() {
        if (getReactNativeHost().hasInstance()) {
            getReactNativeHost().getReactInstanceManager().onBackPressed();
            return true;
        }
        return false;
    }

    @TargetApi(Build.VERSION_CODES.M)
    public void requestPermissions(
            String[] permissions,
            int requestCode,
            PermissionListener listener) {
        mPermissionListener = listener;
        mReactFragment.getActivity().requestPermissions(permissions, requestCode);
    }

    public void onRequestPermissionsResult(
            final int requestCode,
            final String[] permissions,
            final int[] grantResults) {
        mPermissionsCallback = new Callback() {
            @Override
            public void invoke(Object... args) {
                if (mPermissionListener != null && mPermissionListener.onRequestPermissionsResult(requestCode, permissions, grantResults)) {
                    mPermissionListener = null;
                }
            }
        };
    }
}

