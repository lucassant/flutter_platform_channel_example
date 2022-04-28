package com.example.background_service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class AirplaneModeChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val isAirplaneModeEnabled = intent?.getBooleanExtra("state", false) ?: return

        ObservableObject.getInstance().updateValue(isAirplaneModeEnabled)
    }

}