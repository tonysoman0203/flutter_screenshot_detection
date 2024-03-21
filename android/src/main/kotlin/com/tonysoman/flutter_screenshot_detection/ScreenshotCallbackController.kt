package com.tonysoman.flutter_screenshot_detection

import android.app.Activity
import android.app.Activity.ScreenCaptureCallback
import android.os.Build
import io.flutter.plugin.common.EventChannel

class ScreenshotCallbackController(val activity: Activity): EventChannel.StreamHandler  {

    private var screenshotCallback: ScreenCaptureCallback? = null
    private var eventSinks: EventChannel.EventSink? = null

    init {
        Logger.shared.debug("ScreenshotCallbackController init!")
    }

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        Logger.shared.debug("ScreenshotCallbackController onListen!")
        eventSinks = events
        if (screenshotCallback == null) {
            screenshotCallback = ScreenCaptureCallback {
                Logger.shared.debug("ScreenshotCallbackController Take Screenshot")
//                AlertDialog.Builder(activity)
//                    .setMessage("You have taken a screenshot...")
//                    .setTitle("Warning")
//                    .show()
                eventSinks?.success(true)
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                Logger.shared.debug("ScreenshotCallbackController registerScreenCaptureCallback")
                activity.registerScreenCaptureCallback(activity.mainExecutor, screenshotCallback!!)
            }
        }
    }

    override fun onCancel(arguments: Any?) {
        eventSinks = null
    }
}