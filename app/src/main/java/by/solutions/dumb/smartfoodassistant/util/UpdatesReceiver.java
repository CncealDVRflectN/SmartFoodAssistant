package by.solutions.dumb.smartfoodassistant.util;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class UpdatesReceiver extends BroadcastReceiver {

    final String LOG_TAG = "UpdatesReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(LOG_TAG, "onReceive: start UpdatesCheckerService");
        context.startService(new Intent(context, UpdatesChecker.class));
    }
}