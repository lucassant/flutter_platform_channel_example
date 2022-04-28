import 'package:flutter/services.dart';

class EventChannelApi {
  static const MethodChannel _channel =
      MethodChannel('com.example.background_service/test');

  static const EventChannel _eventChannel =
      EventChannel('random_number_channel');

  static Future<bool> get getAirplaneMode async {
    final bool result = await _channel.invokeMethod('getAirplaneMode');
    return result;
  }

  static Stream<bool> get getAirplaneModeStream {
    return _eventChannel.receiveBroadcastStream().cast();
  }
}
