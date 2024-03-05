
import 'flutter_screenshot_detection_platform_interface.dart';

class FlutterScreenshotDetection {

  FlutterScreenshotDetection () {
    initChannel();
  }

  Future<void> initChannel () async{
    bool? result = await FlutterScreenshotDetectionPlatform.instance.startDetection();
    print("initChannel result $result");
  }

  Future<String?> getPlatformVersion() {
    return FlutterScreenshotDetectionPlatform.instance.getPlatformVersion();
  }

  Stream<bool?> onDetect() {
    return FlutterScreenshotDetectionPlatform.instance.onDetect();
  }
}
