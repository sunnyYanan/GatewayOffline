package senseHuge.gateway.util;

import senseHuge.gateway.ui.MainActivity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;

import com.example.testgateway.R;

public class SelectDialog extends AlertDialog {
	Button eternetSetting;
	Button wifiSetting;
	Context mContext;

	public SelectDialog(Context context, int theme) {
		super(context, theme);
		mContext = context;
	}

	public SelectDialog(Context context) {
		super(context);
		mContext = context;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		View view = this.getLayoutInflater().inflate(R.layout.link_type, null);
		setContentView(view);

		eternetSetting = (Button) view.findViewById(R.id.eternetSetting);
		wifiSetting = (Button) view.findViewById(R.id.wifiSetting);

		eternetSetting.setOnClickListener(new MyOnClickListener());
		wifiSetting.setOnClickListener(new MyOnClickListener());

	}

	private class MyOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.eternetSetting:
				mContext.startActivity(new Intent(
						android.provider.Settings.ACTION_SETTINGS));
				break;
			case R.id.wifiSetting:
				mContext.startActivity(new Intent(
						android.provider.Settings.ACTION_WIFI_SETTINGS));
				break;
			}
		}

	}
}
