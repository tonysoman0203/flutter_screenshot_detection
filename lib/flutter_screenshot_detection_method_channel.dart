import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'flutter_screenshot_detection_platform_interface.dart';

/// An implementation of [FlutterScreenshotDetectionPlatform] that uses method channels.
class MethodChannelFlutterScreenshotDetection extends FlutterScreenshotDetectionPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('flutter_screenshot_detection');

  @override
  Future<String?> getPlatformVersion() async {
    final version = await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }
}
