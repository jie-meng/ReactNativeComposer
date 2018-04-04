package com.jmengxy.rndroid

import android.content.Context
import com.facebook.react.ReactPackage
import com.jmengxy.rndroid.react_native.ReactFragment
import com.jmengxy.rndroid.rn_bridge.RNCallback
import com.jmengxy.rndroid.rn_bridge.RNPackage
import java.util.*

class RNFragment : ReactFragment(), RNCallback {

    override fun getUsername(): String {
        return "TestUser";
    }

    override fun getMainComponentName(): String? {
        return "AwesomeRN";

    }

    override fun getAdditionalPackages(): List<ReactPackage> {
        return Arrays.asList(RNPackage(this))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).setReactFragmentDelegate(mDelegate)
    }

    override fun onDetach() {
        super.onDetach()
        (activity as MainActivity).setReactFragmentDelegate(null)
    }
}