package com.example.background_service;

import io.flutter.plugin.common.EventChannel;

public class AirplaneModeStreamChanger implements EventChannel.StreamHandler {

    EventChannel.EventSink sink = null;
    boolean isAirplaneModeEnabled = false;

    public void setValue(boolean value){
        isAirplaneModeEnabled = value;
        sink.success(isAirplaneModeEnabled);
    }

    @Override
    public void onListen(Object arguments, EventChannel.EventSink events) {
        sink = events;
    }

    @Override
    public void onCancel(Object arguments) {
        sink = null;
    }
}
