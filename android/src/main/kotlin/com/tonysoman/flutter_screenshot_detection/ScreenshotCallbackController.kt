package com.tonysoman.flutter_screenshot_detection

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Activity.ScreenCaptureCallback
import android.app.AlertDialog
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodChannel

@SuppressLint("LongLogTag")
class ScreenshotCallbackController(val activity: Activity): EventChannel.StreamHandler  {

    private var screenshotCallback: ScreenCaptureCallback? = null
    private var eventSinks: EventChannel.EventSink? = null

    init {
        Log.d("ScreenshotCallbackController", "init!")
    }

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        Log.d("ScreenshotCallbackController", "onListen!")
        eventSinks = events
        if (screenshotCallback == null) {
            screenshotCallback = ScreenCaptureCallback {
                Log.d("ScreenshotCallbackController", "Take Screenshot")
                AlertDialog.Builder(activity)
                    .setMessage("You have taken a screenshot...")
                    .setTitle("Warning")
                    .show()
                eventSinks?.success(true)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                Log.d("ScreenshotCallbackController", "registerScreenCaptureCallback")
                activity.registerScreenCaptureCallback(activity.mainExecutor, screenshotCallback!!)
            }
        }
    }

    override fun onCancel(arguments: Any?) {
        eventSinks = null
    }
}