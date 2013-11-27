package senseHuge.gateway.service;

import senseHuge.gateway.ui.MainActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class BootBroadcastReceiver extends BroadcastReceiver {
	static final String ACTION = "android.intent.action.BOOT_COMPLETED";

	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		if (intent.getAction().equals(ACTION)) {
			Intent mainIntent = new Intent(context, MainActivity.class);
			mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

			context.startActivity(mainIntent);
		}
	}

}
