import 'dart:async';

import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'flutter_screenshot_detection_method_channel.dart';

abstract class FlutterScreenshotDetectionPlatform extends PlatformInterface {
  /// Constructs a FlutterScreenshotDetectionPlatform.
  FlutterScreenshotDetectionPlatform() : super(token: _token);

  static final Object _token = Object();

  static FlutterScreenshotDetectionPlatform _instance = MethodChannelFlutterScreenshotDetection();

  /// The default instance of [FlutterScreenshotDetectionPlatform] to use.
  ///
  /// Defaults to [MethodChannelFlutterScreenshotDetection].
  static FlutterScreenshotDetectionPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [FlutterScreenshotDetectionPlatform] when
  /// they register themselves.
  static set instance(FlutterScreenshotDetectionPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<bool?> startDetection () {
    throw UnimplementedError('startDetection() has not been implemented.');
  }

  Stream<bool?> onDetect () {
    throw UnimplementedError('onDetect() has not been implemented.');
  }
}
