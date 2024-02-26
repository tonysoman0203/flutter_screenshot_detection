package com.tonysoman.flutter_screenshot_detection

import android.app.Activity
import android.app.AlertDialog
import android.os.Build
import android.util.Log
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.activity.ActivityAware
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding
import io.flutter.plugin.common.BinaryMessenger
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** FlutterScreenshotDetectionPlugin */
class FlutterScreenshotDetectionPlugin: FlutterPlugin, MethodCallHandler, ActivityAware{
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private lateinit var channel : MethodChannel
  private var activity: Activity? = null
  private var screenshotCallbackController: ScreenshotCallbackController? = null
  private var eventChannel: EventChannel? = null
  private lateinit var binaryMessenger: BinaryMessenger

  private val screenCaptureCallback = Activity.ScreenCaptureCallback {
    // Add logic to take action in your app.
    AlertDialog.Builder(activity)
      .setMessage("You have taken a screenshot...")
      .setTitle("Warning")
      .show()
  }

  override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
    binaryMessenger = flutterPluginBinding.binaryMessenger
    channel = MethodChannel(flutterPluginBinding.binaryMessenger, "flutter_screenshot_detection")
    channel.setMethodCallHandler(this)

  }

  override fun onMethodCall(call: MethodCall, result: Result) {
    when (call.method) {
        "getPlatformVersion" -> {
          result.success("Android ${Build.VERSION.RELEASE}")
        }
        "startDetection" -> {
          Log.d("FlutterScreenshotDetectionPlugin", "startDetection")
          /// register Android Detector Callback
          activity?.let {
            if (eventChannel == null && screenshotCallbackController == null) {
              Log.d("FlutterScreenshotDetectionPlugin", "eventChannel $eventChannel screenshotCallbackController $screenshotCallbackController")
              eventChannel = EventChannel(binaryMessenger, "detectScreenShotEvent")
              Log.d("FlutterScreenshotDetectionPlugin", "eventChannel $eventChannel setup")
              screenshotCallbackController = ScreenshotCallbackController(activity = it)
              eventChannel!!.setStreamHandler(screenshotCallbackController)
              Log.d("FlutterScreenshotDetectionPlugin", "eventChannel $eventChannel setStreamHandler")
              result.success(true)
            }
          }
        }
        "stopDetection" -> {
          /// destroy Android detector Callback
          activity?.let {
            screenshotCallbackController = null
            eventChannel?.setStreamHandler(null)
          }
        }
        else -> {
          result.notImplemented()
        }
    }
  }

  override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
    channel.setMethodCallHandler(null)
    eventChannel?.setStreamHandler(null)
  }

  override fun onAttachedToActivity(binding: ActivityPluginBinding) {
    activity = binding.activity
  }

  override fun onDetachedFromActivityForConfigChanges() {
    activity = null
  }

  override fun onReattachedToActivityForConfigChanges(binding: ActivityPluginBinding) {
    activity = binding.activity
  }

  override fun onDetachedFromActivity() {
    activity = null
  }
}
