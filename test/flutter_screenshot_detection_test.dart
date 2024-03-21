import 'package:flutter_test/flutter_test.dart';
import 'package:flutter_screenshot_detection/flutter_screenshot_detection.dart';
import 'package:flutter_screenshot_detection/flutter_screenshot_detection_platform_interface.dart';
import 'package:flutter_screenshot_detection/flutter_screenshot_detection_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockFlutterScreenshotDetectionPlatform
    with MockPlatformInterfaceMixin
    implements FlutterScreenshotDetectionPlatform {

  @override
  Future<String?> getPlatformVersion() => Future.value('42');
}

void main() {
  final FlutterScreenshotDetectionPlatform initialPlatform = FlutterScreenshotDetectionPlatform.instance;

  test('$MethodChannelFlutterScreenshotDetection is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelFlutterScreenshotDetection>());
  });

  test('getPlatformVersion', () async {
    FlutterScreenshotDetection flutterScreenshotDetectionPlugin = FlutterScreenshotDetection();
    MockFlutterScreenshotDetectionPlatform fakePlatform = MockFlutterScreenshotDetectionPlatform();
    FlutterScreenshotDetectionPlatform.instance = fakePlatform;

    expect(await flutterScreenshotDetectionPlugin.getPlatformVersion(), '42');
  });
}
