package com.example.background_service;

import android.content.Intent;
import android.content.IntentFilter;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.util.Observable;
import java.util.Observer;

import io.flutter.embedding.android.FlutterActivity;
import io.flutter.embedding.engine.FlutterEngine;
import io.flutter.plugin.common.EventChannel;
import io.flutter.plugin.common.MethodChannel;

public class MainActivity extends FlutterActivity implements Observer {

    private static final String CHANNEL_METHOD = "com.example.background_service/test";

    private AirplaneModeChangeReceiver receiver = new AirplaneModeChangeReceiver();

    private boolean isAirplaneModeEnabled = false;
    private AirplaneModeStreamChanger airplaneModeStreamChanger = new AirplaneModeStreamChanger();

    @Override
    public void onStop() {
        super.onStop();

        unregisterReceiver(receiver);
    }

    @Override
    public void configureFlutterEngine(@NonNull FlutterEngine flutterEngine) {
        super.configureFlutterEngine(flutterEngine);

        IntentFilter intentFilter = new IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED);
        registerReceiver(receiver, intentFilter);

        ObservableObject.getInstance().addObserver(this);

        new MethodChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), CHANNEL_METHOD)
                .setMethodCallHandler((call, result) -> {
                            if (call.method.equals("getAirplaneMode")) {
                                result.success(isAirplaneModeEnabled);
                            }
                        }
                );

        new EventChannel(flutterEngine.getDartExecutor().getBinaryMessenger(), "random_number_channel")
                .setStreamHandler(airplaneModeStreamChanger);
    }

    @Override
    public void update(Observable observable, Object o) {
        isAirplaneModeEnabled = (boolean) o;
        airplaneModeStreamChanger.setValue(isAirplaneModeEnabled);
        Toast.makeText(this, ("AirplaneMode enabled: " + isAirplaneModeEnabled), Toast.LENGTH_LONG).show();
    }
}
