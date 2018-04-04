package com.jmengxy.rndroid.react_native.keyevent_module;


import android.text.InputType;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;

import com.facebook.react.bridge.ReactContext;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.uimanager.PixelUtil;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewDefaults;
import com.facebook.react.views.textinput.ReactEditText;
import com.facebook.react.views.textinput.ReactTextInputManager;

public class DelKeyEventTextInputManager extends ReactTextInputManager {
    @Override
    public ReactEditText createViewInstance(final ThemedReactContext context) {
        ReactEditText editText = new ReactEditText(context) {
            @Override
            public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
                return new DelListenerInputConnection(super.onCreateInputConnection(outAttrs), true, context);
            }
        };
        int inputType = editText.getInputType();
        editText.setInputType(inputType & (~InputType.TYPE_TEXT_FLAG_MULTI_LINE));
        editText.setReturnKeyType("done");
        editText.setTextSize(
                TypedValue.COMPLEX_UNIT_PX,
                (int) Math.ceil(PixelUtil.toPixelFromSP(ViewDefaults.FONT_SIZE_SP)));
        return editText;
    }

    private class DelListenerInputConnection extends InputConnectionWrapper {

        private ReactContext mReactContext;
        private DeviceEventManagerModule.RCTDeviceEventEmitter mJSModule;

        DelListenerInputConnection(InputConnection target, boolean mutable, ReactContext reactContext) {
            super(target, mutable);
            mReactContext = reactContext;
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_DEL) {
                if (mJSModule == null) {
                    mJSModule = mReactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class);
                }
                if(mJSModule != null) {
                    if (event.getAction() == KeyEvent.ACTION_DOWN) {
                        mJSModule.emit("onKeyDown", event.getKeyCode());
                    }
                    if(event.getAction() == KeyEvent.ACTION_UP) {
                        mJSModule.emit("onKeyUp", event.getKeyCode());
                    }
                }
            }
            return super.sendKeyEvent(event);
        }

    }

}
