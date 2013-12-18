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
	    setContentView(R.layout.link_type);
//	    LayoutInflater lay = (LayoutInflater) this.getLayoutInflater()getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		View v =  this.getLayoutInflater().inflate(R.layout.link_type, null);
//		eternetSetting = (Button) v.findViewById(R.id.eternetSetting);
//		wifiSetting = (Button) v.findViewById(R.id.wifiSetting);
//		System.out.println("mingziL"+eternetSetting.getText());
		
//		eternetSetting.setOnClickListener(new MyOnClickListener());
//		wifiSetting.setOnClickListener(new MyOnClickListener());
//		setContentView(v);
	}
	
}
