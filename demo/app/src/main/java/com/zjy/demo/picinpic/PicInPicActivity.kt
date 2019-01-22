package com.zjy.demo.picinpic

import android.annotation.TargetApi
import android.app.Activity
import android.app.PendingIntent
import android.app.PictureInPictureParams
import android.app.RemoteAction
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.graphics.Rect
import android.graphics.drawable.Icon
import android.media.session.MediaSession
import android.os.Build
import android.os.Bundle
import android.support.annotation.DrawableRes
import android.support.annotation.RequiresApi
import android.text.TextUtils
import android.util.Log
import android.util.Rational
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.zjy.demo.R


@TargetApi(Build.VERSION_CODES.O)
class PicInPicActivity: Activity() {
    val TAG:String = PicInPicActivity::class.java.simpleName
    var picText: TextView? = null

    var ACTION: String = "action_control_test"
    var CONTROL:String? = "control"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.pic_in_pic_activity)

        picText = findViewById(R.id.pic_text)
        picText?.setOnClickListener(object : View.OnClickListener {
            @RequiresApi(Build.VERSION_CODES.O)
            @TargetApi(Build.VERSION_CODES.N)
            override fun onClick(v: View?) {
                var rational = Rational(3,3)
                var rect = Rect(100,100, 500, 500)

                mPictureInPictureParamsBuilder.setAspectRatio(rational)
                mPictureInPictureParamsBuilder.setSourceRectHint(rect)
                enterPictureInPictureMode(mPictureInPictureParamsBuilder.build())
            }
        })
        updatePictureInPictureActions(R.drawable.ic_info_24dp, "test", 1,1)
    }

    /**
     * pip回调广播
     */
    var mReceiver:BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.e(TAG, "action = "+intent?.action)
            var action = intent?.action
            if(TextUtils.equals(action, ACTION)) {
                var type = intent?.getIntExtra(CONTROL, 0)
                when(type) {
                    1-> {
                        Log.e(TAG, "type = "+type)
                    }
                    2-> {
                        Log.e(TAG, "type = "+type)
                    }
                }
            }
        }
    }

    override fun onPictureInPictureModeChanged(isInPictureInPictureMode: Boolean, newConfig: Configuration?) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig)
        if(isInPictureInPictureMode) {
            // 画中画，注册broadcast
            // 想要接受广播，manifest中一定要android:configChanges="screenSize|smallestScreenSize|screenLayout|orientation"
            // 屏幕切换的时候保持不重新创建这样注册的才会生效
            var filter = IntentFilter(ACTION)
            registerReceiver(mReceiver, filter)

            // 接受焦点
            var session: MediaSession = MediaSession(this@PicInPicActivity, "se")
            var seIntent:PendingIntent = PendingIntent.getBroadcast(
                this@PicInPicActivity,
                1,
                Intent("").putExtra("", ""),
                0
            );
            session.setMediaButtonReceiver(seIntent)
        } else {
            unregisterReceiver(mReceiver)
        }
        Log.e(TAG, "isInPictureInPictureMode = "+isInPictureInPictureMode)
        Log.e(TAG, "newConfig = "+newConfig.toString())
    }

    private val mPictureInPictureParamsBuilder = PictureInPictureParams.Builder()
    internal fun updatePictureInPictureActions(
        @DrawableRes iconId: Int, title: String, controlType: Int, requestCode: Int
    ) {
        val actions = ArrayList<RemoteAction>()
        val intent = PendingIntent.getBroadcast(
            this@PicInPicActivity,
            requestCode,
            Intent(ACTION).putExtra(CONTROL, controlType),
            0
        )
        val icon = Icon.createWithResource(this@PicInPicActivity, iconId)
        actions.add(RemoteAction(icon, title, title, intent))

        // Another action item. This is a fixed action.
//        actions.add(
//            RemoteAction(
//                Icon.createWithResource(this@MainActivity, R.drawable.ic_info_24dp),
//                getString(R.string.info),
//                getString(R.string.info_description),
//                PendingIntent.getActivity(
//                    this@MainActivity,
//                    REQUEST_INFO,
//                    Intent(
//                        Intent.ACTION_VIEW,
//                        Uri.parse(getString(R.string.info_uri))
//                    ),
//                    0
//                )
//            )
//        )

        mPictureInPictureParamsBuilder.setActions(actions)
        // This is how you can update action items (or aspect ratio) for Picture-in-Picture mode.
        // Note this call can happen even when the app is not in PiP mode. In that case, the
        // arguments will be used for at the next call of #enterPictureInPictureMode.
        setPictureInPictureParams(mPictureInPictureParamsBuilder.build())
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        Log.e(TAG, "onNewIntent")
    }

    override fun onRestart() {
        super.onRestart()
        Log.e(TAG, "onRestart")
    }

    override fun onStart() {
        super.onStart()
        Log.e(TAG, "onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.e(TAG, "onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.e(TAG, "onPause")
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(mReceiver)
    }
}