package senseHuge.gateway.ui;

import java.io.IOException;

import senseHuge.gateway.Dao.MySQLiteDbHelper;
import senseHuge.gateway.service.FileChooserActivity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.testgateway.R;

public class Fragment_nodeSetting extends Fragment {
	// 节点设置
	// public MainActivity ma;
	private boolean isAvalable;// 标识串口是否已连接
	private int cycle;// 标识设置的周期时间
	Spinner sendCycleSpinner;
	Spinner powerSettingSpinner;
	Button musicChooseButton;
	Button settingOkButton;
	RadioGroup rg;
	RadioButton radioYesButton;
	RadioButton radioNoButton;
	int alertPower = 15;// 预警值的设置
	String alertMusicPath = "/mnt/1.mp3";// 预警音乐设置
	MySQLiteDbHelper mdbHelper;
	SQLiteDatabase db;
	View v;

	private static final int REQUEST_CODE = 1; // 请求码
	public static final String EXTRA_FILE_CHOOSER = "file_chooser";
	private boolean isMusicAlert = true;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		v = inflater.inflate(R.layout.fragment_node_setting, container, false);

		mdbHelper = new MySQLiteDbHelper(v.getContext(), "MyData.db", null, 1);
		// 默认发包周期是5秒
		cycle = 5;
		if (Fragment_serialconfig.mSerialPort == null) {
			isAvalable = false;
		} else {
			isAvalable = true;
		}
		rg = (RadioGroup) v.findViewById(R.id.radioGroup);
		radioYesButton = (RadioButton) v.findViewById(R.id.yes);
		radioNoButton = (RadioButton) v.findViewById(R.id.no);
		sendCycleSpinner = (Spinner) v.findViewById(R.id.sendCycleSelect);
		powerSettingSpinner = (Spinner) v
				.findViewById(R.id.powerSettingSpinner);
		musicChooseButton = (Button) v.findViewById(R.id.musicSelect);
		settingOkButton = (Button) v.findViewById(R.id.settingOKButton);

		ArrayAdapter<CharSequence> sendCycleAdapter = ArrayAdapter
				.createFromResource(getActivity(), R.array.nodeSettingCycle,
						android.R.layout.simple_spinner_item);
		sendCycleAdapter
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

		ArrayAdapter<CharSequence> powerSettingAdapter = ArrayAdapter
				.createFromResource(getActivity(), R.array.powerSetting,
						android.R.layout.simple_spinner_item);
		powerSettingAdapter
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

		sendCycleSpinner.setAdapter(sendCycleAdapter);
		powerSettingSpinner.setAdapter(powerSettingAdapter);

		sendCycleSpinner
				.setOnItemSelectedListener(new SendCycleSelectedListener());
		powerSettingSpinner
				.setOnItemSelectedListener(new PowerSettingListener());
		musicChooseButton.setOnClickListener(new MyButtonListener());
		settingOkButton.setOnClickListener(new MyButtonListener());
		rg.setOnCheckedChangeListener(new MyOnCheckedChangeListener());
		return v;
	}

	public class MyOnCheckedChangeListener implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup arg0, int arg1) {
			// TODO Auto-generated method stub
			if (arg1 == radioYesButton.getId()) {
				isMusicAlert = true;
			}
			if (arg1 == radioNoButton.getId()) {
				isMusicAlert = false;
			}
		}

	}

	public class MyButtonListener implements OnClickListener {
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.musicSelect:
				Intent fileChooserIntent = new Intent(arg0.getContext(),
						FileChooserActivity.class);
				startActivityForResult(fileChooserIntent, REQUEST_CODE);
				break;
			case R.id.settingOKButton:
				writeIntoNode();
				saveIntoDB();
				Toast.makeText(v.getContext(), "设置成功", Toast.LENGTH_SHORT)
						.show();

				break;
			}
		}

	}

	// 将预警设置写入数据库
	private void saveIntoDB() {
		// TODO Auto-generated method stub
		db = mdbHelper.getWritableDatabase();

		ContentValues values = new ContentValues();
		values.put("type", "预警电量");
		values.put("value", alertPower + "%");
		values.put("path", alertMusicPath);
		values.put("alert", isMusicAlert);

		if (checkIfHas()) {
			db.update("AlertSetting", values, "type=?", new String[] { "预警电量" });
		} else {
			db.insert("AlertSetting", null, values);
		}
		db.close();
	}

	// 检查数据库中是否已经有该类型
	private boolean checkIfHas() {
		// TODO Auto-generated method stub
		Cursor cursor = db.query("AlertSetting", new String[] { "type" }, null,
				null, null, null, null);
		while (cursor.moveToNext()) {
			String type = (cursor.getString(cursor.getColumnIndex("type")));
			if (type.equalsIgnoreCase("预警电量")) {
				cursor.close();
				return true;
			}
		}
		cursor.close();
		return false;
	}

	// 将发包周期与电量设置写入sink节点
	private void writeIntoNode() {
		// TODO Auto-generated method stub
		if (isAvalable) {
			try {
				String message = ("预警电量：" + alertPower + " 发包周期：" + cycle);
				Fragment_serialconfig.mSerialPort.getOutputStream().write(
						message.getBytes("gb2312"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		/*
		 * Log.v(TAG, "onActivityResult#requestCode:" + requestCode +
		 * "#resultCode:" + resultCode); this.getActivity();
		 */
		if (resultCode == FragmentActivity.RESULT_CANCELED) {
			toast("没有打开文件");
			return;
		}
		// this.getActivity();
		if (resultCode == FragmentActivity.RESULT_OK
				&& requestCode == REQUEST_CODE) {
			// 获取路径名
			String musicPath = data.getStringExtra(EXTRA_FILE_CHOOSER);
			// Log.v(TAG, "onActivityResult # musicPath : " + musicPath);
			if (musicPath != null) {
				toast("Choose File : " + musicPath);
				alertMusicPath = musicPath;// 预警音乐路径设置
			} else
				toast("打开文件失败");
		}
	}

	private void toast(CharSequence hint) {
		Toast.makeText(this.getActivity().getBaseContext(), hint,
				Toast.LENGTH_SHORT).show();
	}

	public class PowerSettingListener implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			System.out.println("powerSettingpos---->" + arg2);
			setAlertPower(arg2);
			System.out.println("powerSettingValue---->" + alertPower);

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}

	/**
	 * @param arg2
	 *            用户选择 设置预警点，5代表5%
	 */
	private void setAlertPower(int arg2) {
		// TODO Auto-generated method stub
		if (arg2 == 0) {
			alertPower = 5;
		} else if (arg2 == 1) {
			alertPower = 10;
		} else if (arg2 == 2) {
			alertPower = 15;
		} else if (arg2 == 3) {
			alertPower = 20;
		} else if (arg2 == 4) {
			alertPower = 25;
		} else if (arg2 == 5) {
			alertPower = 30;
		} else if (arg2 == 6) {
			alertPower = 35;
		} else if (arg2 == 7) {
			alertPower = 40;
		}
	}

	public class SendCycleSelectedListener implements OnItemSelectedListener {
		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			System.out.println("SendCyclepos---->" + arg2);
			setCycle(arg2);
			System.out.println("SendCycleValue---->" + cycle);

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
		}
	}

	/**
	 * @param arg2
	 *            用户选择 设置发包周期，以秒为单位
	 */
	private void setCycle(int arg2) {
		// TODO Auto-generated method stub
		if (arg2 == 0) {
			cycle = 5;
		} else if (arg2 == 1) {
			cycle = 10;
		} else if (arg2 == 2) {
			cycle = 30;
		} else if (arg2 == 3) {
			cycle = 1 * 60;
		} else if (arg2 == 4) {
			cycle = 2 * 60;
		} else if (arg2 == 5) {
			cycle = 5 * 60;
		} else if (arg2 == 6) {
			cycle = 10 * 60;
		}
	}
}
