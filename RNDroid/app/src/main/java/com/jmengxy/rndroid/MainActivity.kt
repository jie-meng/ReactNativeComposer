package com.jmengxy.rndroid

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.facebook.react.modules.core.PermissionAwareActivity
import com.facebook.react.modules.core.PermissionListener
import com.jmengxy.rndroid.react_native.ReactFragmentDelegate

class MainActivity : AppCompatActivity(), PermissionAwareActivity {

    private var reactFragmentDelegate: ReactFragmentDelegate? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    private fun init() {
        val manager = supportFragmentManager
        val transaction = manager.beginTransaction()
        transaction.add(R.id.content_frame, RNFragment())
        transaction.commit()
    }

    fun setReactFragmentDelegate(reactFragmentDelegate: ReactFragmentDelegate?) {
        this.reactFragmentDelegate = reactFragmentDelegate
    }

    override fun requestPermissions(permissions: Array<String>, requestCode: Int, listener: PermissionListener) {
        if (reactFragmentDelegate != null) {
            reactFragmentDelegate!!.requestPermissions(permissions, requestCode, listener)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (reactFragmentDelegate != null) {
            reactFragmentDelegate!!.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}
