package senseHuge.gateway.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.testgateway.R;

public class Fragment_linkSetting extends Fragment {
	Button eternetSetting, wifiSetting;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_link_setting, container,
				false);
		eternetSetting = (Button) view.findViewById(R.id.eternetSetting);
		wifiSetting = (Button) view.findViewById(R.id.wifiSetting);

		eternetSetting.setOnClickListener(new MyButtonClickListener());
		wifiSetting.setOnClickListener(new MyButtonClickListener());
		return view;
	}

	class MyButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.eternetSetting:
				startActivity(new Intent(
						android.provider.Settings.ACTION_SETTINGS));
				break;
			case R.id.wifiSetting:
				startActivity(new Intent(
						android.provider.Settings.ACTION_WIFI_SETTINGS));
				break;
			}
		}

	}
}
