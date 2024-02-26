
import 'flutter_screenshot_detection_platform_interface.dart';

class FlutterScreenshotDetection {
  Future<String?> getPlatformVersion() {
    return FlutterScreenshotDetectionPlatform.instance.getPlatformVersion();
  }
}
