package com.tonysoman.flutter_screenshot_detection

import android.app.Activity
import android.os.Build

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
  private lateinit var channel : MethodChannel
  private var activity: Activity? = null
  private var screenshotCallbackController: ScreenshotCallbackController? = null
  private var eventChannel: EventChannel? = null
  private lateinit var binaryMessenger: BinaryMessenger

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
          Logger.shared.debug("FlutterScreenshotDetectionPlugin startDetection")
          /// register Android Detector Callback
          activity?.let {
            if (eventChannel == null && screenshotCallbackController == null) {
              Logger.shared.debug("FlutterScreenshotDetectionPlugin eventChannel $eventChannel screenshotCallbackController $screenshotCallbackController")
              eventChannel = EventChannel(binaryMessenger, "detectScreenShotEvent")
              Logger.shared.debug("FlutterScreenshotDetectionPlugin eventChannel $eventChannel setup")
              screenshotCallbackController = ScreenshotCallbackController(activity = it)
              eventChannel!!.setStreamHandler(screenshotCallbackController)
              Logger.shared.debug("FlutterScreenshotDetectionPlugin eventChannel $eventChannel setStreamHandler")
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
