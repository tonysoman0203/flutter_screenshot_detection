import Flutter
import UIKit

public class FlutterScreenshotDetectionPlugin: NSObject, FlutterPlugin, FlutterStreamHandler {
    

    var eventSink: FlutterEventSink?
    
  public static func register(with registrar: FlutterPluginRegistrar) {
    let channel = FlutterMethodChannel(name: "flutter_screenshot_detection", binaryMessenger: registrar.messenger())
    let eventChannel = FlutterEventChannel(name: "detectScreenShotEvent", binaryMessenger: registrar.messenger())
      
    let instance = FlutterScreenshotDetectionPlugin()
      
    registrar.addMethodCallDelegate(instance, channel: channel)
    eventChannel.setStreamHandler(instance)
      
    NotificationCenter.default.addObserver(instance, selector: #selector(screenDidToken), name: UIApplication.userDidTakeScreenshotNotification, object: nil)
  }

  public func handle(_ call: FlutterMethodCall, result: @escaping FlutterResult) {
    switch call.method {
    case "getPlatformVersion":
      result("iOS " + UIDevice.current.systemVersion)
    default:
      result(FlutterMethodNotImplemented)
    }
  }
    
    @objc func screenDidToken() {
        if let eventSink = eventSink {
            eventSink(true)
        }
    }
    
    public func onListen(withArguments arguments: Any?, eventSink events: @escaping FlutterEventSink) -> FlutterError? {
        eventSink = events
        return nil
    }
    
    public func onCancel(withArguments arguments: Any?) -> FlutterError? {
        return nil
    }
}
