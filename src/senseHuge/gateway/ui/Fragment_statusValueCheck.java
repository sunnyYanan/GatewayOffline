package senseHuge.gateway.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import senseHuge.gateway.Dao.MySQLiteDbHelper;
import senseHuge.gateway.model.PackagePattern;
import senseHuge.gateway.util.StatusDatePick;
import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.testgateway.R;

public class Fragment_statusValueCheck extends Fragment {
	StatusValueCheckView statusView;
	Button humidity;
	Button temperature;
	Button light;
	Button co2;
	Button setting;
	Button statusDateSet;
	public static TextView statusDateShow;
	Spinner nodeSelectSpinner;
	Spinner granularitySpinner;
	EditText itemText;
	String curNodeId;
	String curShowType;
	MySQLiteDbHelper mdbHelper;
	SQLiteDatabase mdb;

	private static final String TEMPERATURE = "传感器采样温度";
	private static final String HUMIDITY = "传感器采样湿度";
	private static final String CO2 = "二氧化碳浓度";
	private static final String LIGHT = "光照强度";

	int granularity;
	int number;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// setContentView(new ReDrawLineChartView(this.getActivity()));
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_status_value, container,
				false);
		statusView = (StatusValueCheckView) view.findViewById(R.id.myView);
		temperature = (Button) view.findViewById(R.id.temperature);
		humidity = (Button) view.findViewById(R.id.humidity);
		light = (Button) view.findViewById(R.id.light);
		co2 = (Button) view.findViewById(R.id.co2);
		statusDateSet = (Button) view.findViewById(R.id.statueDateSet);
		statusDateShow = (TextView) view.findViewById(R.id.statusDateShow);
		nodeSelectSpinner = (Spinner) view.findViewById(R.id.nodeSelectSpinner);
		granularitySpinner = (Spinner) view.findViewById(R.id.granularity);
		itemText = (EditText) view.findViewById(R.id.itemNum);
		setting = (Button) view.findViewById(R.id.statusButton);

		curNodeId = "0000";
		curShowType = TEMPERATURE;// 传感器采样湿度，光照强度，二氧化碳浓度
		statusView.setTitle("温度", "时间", "度数");
		number = 2;

		mdbHelper = new MySQLiteDbHelper(this.getActivity());

		temperature.setOnClickListener(new MyButtonOnClickListener());
		humidity.setOnClickListener(new MyButtonOnClickListener());
		light.setOnClickListener(new MyButtonOnClickListener());
		co2.setOnClickListener(new MyButtonOnClickListener());
		setting.setOnClickListener(new MyButtonOnClickListener());
		statusDateSet.setOnClickListener(new MyButtonOnClickListener());

		setShowData();

		ArrayAdapter adapter = new ArrayAdapter(this.getActivity()
				.getBaseContext(), android.R.layout.simple_spinner_item,
				Fragment_listNode.nodeId);
		// Specify the layout to use when the list of choices appears
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		nodeSelectSpinner.setAdapter(adapter);
		nodeSelectSpinner
				.setOnItemSelectedListener(new MyItemSelectedListener());

		ArrayAdapter<CharSequence> itemAdapter = ArrayAdapter
				.createFromResource(this.getActivity(), R.array.granularity,
						android.R.layout.simple_spinner_item);
		// Specify the layout to use when the list of choices appears
		itemAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		// Apply the adapter to the spinner
		granularitySpinner.setAdapter(itemAdapter);
		granularitySpinner
				.setOnItemSelectedListener(new MyItemSelectedListener());
		return view;
	}

	private void setShowData() {
		// TODO Auto-generated method stub
		mdb = mdbHelper.getReadableDatabase();
		Cursor cursor = mdb.query(MySQLiteDbHelper.TABLEMESSAGE,
				new String[] { "message,receivetime" }, "NodeID=? AND CType=?",
				new String[] { curNodeId, "C1" }, null, null,
				"receivetime DESC");
		int num = number;
		List<Float> dataTemp = new ArrayList<Float>();
		List<String> xLabelTemp = new ArrayList<String>();
		while (cursor.moveToNext() && num > 0) {
			String message = cursor.getString(cursor.getColumnIndex("message"));
			String time = cursor
					.getString(cursor.getColumnIndex("receivetime"));
			try {
				// 解析后的数据
				PackagePattern mpp = MainActivity.xmlTelosbPackagePatternUtil
						.parseTelosbPackage(message);
				String value = getTypeValue(mpp);
				if (value != null) {
					float curData = transformValue(value, mpp);
					dataTemp.add(curData);
					xLabelTemp.add(time);
					num--;
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		int temp = dataTemp.size();
		float[] data = new float[temp];
		String[] xLabel = new String[temp];
		for (int i = temp - 1, j = 0; i >= 0; i--, j++) {
			data[j] = dataTemp.get(i);
			xLabel[j] = xLabelTemp.get(i);
			System.out.println("横坐标：" + xLabelTemp.get(i));
		}
		String[] yLabel = setYLabel(data, granularity);
		statusView.setData(xLabel, // X轴刻度
				yLabel, // Y轴刻度
				data);// 数据);
		cursor.close();
		mdb.close();
	}

	private void setShowData(String date) {
		List<Integer> inputDate = new ArrayList<Integer>();
		getDate(inputDate, date);

		mdb = mdbHelper.getReadableDatabase();
		Cursor cursor = mdb.query(MySQLiteDbHelper.TABLEMESSAGE,
				new String[] { "message,receivetime" }, "NodeID=? AND CType=?",
				new String[] { curNodeId, "C1" }, null, null,
				"receivetime DESC");
		List<Float> dataTemp = new ArrayList<Float>();
		List<String> xLabelTemp = new ArrayList<String>();
		while (cursor.moveToNext()) {
			String time = cursor
					.getString(cursor.getColumnIndex("receivetime"));
			List<Integer> curDate = new ArrayList<Integer>();
			getDate(curDate, time);
			if (findIt(inputDate, curDate)) {//找到比当前输入日期小的那条记录
				String message = cursor.getString(cursor.getColumnIndex("message"));
				try {
					// 解析后的数据
					PackagePattern mpp = MainActivity.xmlTelosbPackagePatternUtil
							.parseTelosbPackage(message);
					String value = getTypeValue(mpp);
					if (value != null) {
						float curData = transformValue(value, mpp);
						dataTemp.add(curData);
						xLabelTemp.add(time);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				break;
			}
		}
		int num = 14;//显示用户输入日期之前的15条记录
		while (cursor.moveToNext() && num > 0) {
			String message = cursor.getString(cursor.getColumnIndex("message"));
			String time = cursor
					.getString(cursor.getColumnIndex("receivetime"));
			try {
				// 解析后的数据
				PackagePattern mpp = MainActivity.xmlTelosbPackagePatternUtil
						.parseTelosbPackage(message);
				String value = getTypeValue(mpp);
				if (value != null) {
					float curData = transformValue(value, mpp);
					dataTemp.add(curData);
					xLabelTemp.add(time);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			num--;
		}
		int temp = dataTemp.size();
		float[] data = new float[temp];
		String[] xLabel = new String[temp];
		for (int i = temp - 1, j = 0; i >= 0; i--, j++) {
			data[j] = dataTemp.get(i);
			xLabel[j] = xLabelTemp.get(i);
			System.out.println("横坐标：" + xLabelTemp.get(i));
		}
		String[] yLabel = setYLabel(data, granularity);
		statusView.setData(xLabel, // X轴刻度
				yLabel, // Y轴刻度
				data);// 数据);
		cursor.close();
		mdb.close();
	}

	private boolean findIt(List<Integer> inputDate, List<Integer> curDate) {
		// TODO Auto-generated method stub
		int inputYear = inputDate.get(0);
		int inputMonth = inputDate.get(1);
		int inputDay = inputDate.get(2);
		int inputHour = inputDate.get(3);
		int inputMinute = inputDate.get(4);
		int curYear = curDate.get(0);
		int curMonth = curDate.get(1);
		int curDay = curDate.get(2);
		int curHour = curDate.get(3);
		int curMinute = curDate.get(4);
		if (inputYear < curYear)
			return false;
		else if (inputYear == curYear && inputMonth < curMonth)
			return false;
		else if (inputYear == curYear && inputMonth == curMonth
				&& inputDay < curDay)
			return false;
		else if (inputYear == curYear && inputMonth == curMonth
				&& inputDay == curDay && inputHour < curHour)
			return false;
		if (inputYear == curYear && inputMonth == curMonth
				&& inputDay == curDay && inputHour == curHour
				&& inputMinute < curMinute)
			return false;
		return true;
	}

	private void getDate(List<Integer> temp, String date) {
		// TODO Auto-generated method stub
		String[] str = date.split(" ");
		String[] dateStr = str[0].split("-");
		String[] timeStr = str[1].split(":");
		int year = Integer.parseInt(dateStr[0]);
		int month = Integer.parseInt(dateStr[1]);
		int day = Integer.parseInt(dateStr[2]);
		int hour = Integer.parseInt(timeStr[0]);
		int minute = Integer.parseInt(timeStr[1]);
		temp.add(year);
		temp.add(month);
		temp.add(day);
		temp.add(hour);
		temp.add(minute);

	}

	private float transformValue(String value, PackagePattern mpp) {
		// TODO Auto-generated method stub
		float transValue = 0;
		System.out.println(curShowType + " before:" + value);
		float beforeTran = hex2Dec(value);
		if (curShowType == TEMPERATURE) {
			transValue = (float) (beforeTran * 0.01 - 40);
		} else if (curShowType == HUMIDITY) {
			// float temp = hex2Dec(value);
			float humidity_temp = (float) (-4 + 0.0405 * beforeTran - 2.8
					* 0.000001 * beforeTran * beforeTran);
			String curTemp = this.getCurTempValue(mpp);
			float curTempPrac = hex2Dec(curTemp);
			float curTemperature = (float) (curTempPrac * 0.01 - 40);
			if (curTemperature == 25.0) {
				transValue = humidity_temp;
			} else {
				transValue = (float) ((curTemperature - 25)
						* (0.01 + 0.00008 * beforeTran) + humidity_temp);
			}

		} else if (curShowType == CO2) {
			transValue = beforeTran;
		} else if (curShowType == LIGHT) {
			transValue = (float) (beforeTran * 0.085);
		}
		System.out.println(curShowType + " after:" + transValue);
		return transValue;
	}

	private String getCurTempValue(PackagePattern mpp) {
		// TODO Auto-generated method stub
		Iterator<?> it = mpp.DataField.entrySet().iterator();
		String value = null;
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			if (pairs.getKey().equals(TEMPERATURE)) {
				value = pairs.getValue().toString();
			}
		}
		return value;
	}

	private float hex2Dec(String value) {
		// TODO Auto-generated method stub
		float powerDeci = 0;
		for (int i = value.length() - 1; i >= 0; i--) {
			char n = value.charAt(i);
			int num = 0;
			if (n == 'A') {
				num = 10;
			} else if (n == 'B') {
				num = 11;
			} else if (n == 'C') {
				num = 12;
			} else if (n == 'D') {
				num = 13;
			} else if (n == 'E') {
				num = 14;
			} else if (n == 'F') {
				num = 15;
			} else
				num = Integer.parseInt(String.valueOf(n));

			powerDeci += num * Math.pow(16, value.length() - 1 - i);
		}
		return powerDeci;
	}

	private String[] setYLabel(float[] data, int size) {
		String[] yLabel = new String[size + 1];
		// TODO Auto-generated method stub
		double max = 0;
		for (int i = 0; i < data.length; i++) {
			if (max < data[i]) {
				max = data[i];
			}
		}
		System.out.println("max:" + max);
		for (int i = 0; i < size + 1; i++) {
			yLabel[i] = String.valueOf((int) ((float) max / size * i));
			System.out.println("Ylabel " + i + ": " + yLabel[i]);
		}
		return yLabel;
	}

	private String getTypeValue(PackagePattern mpp) {
		// TODO Auto-generated method stub
		Iterator<?> it = mpp.DataField.entrySet().iterator();
		String value = null;
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			if (pairs.getKey().equals(curShowType)) {
				value = pairs.getValue().toString();
			}
		}
		return value;
	}

	public class MyItemSelectedListener implements OnItemSelectedListener {

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.nodeSelectSpinner:
				curNodeId = (String) arg0.getItemAtPosition(arg2);
				setShowData();
				statusView.invalidate();
				break;
			case R.id.granularity:
				granularity = Integer.parseInt((String) arg0
						.getItemAtPosition(arg2));
				setShowData();
				statusView.invalidate();
				break;
			}

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub

		}

	}

	public class MyButtonOnClickListener implements OnClickListener {

		@SuppressLint("NewApi")
		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.humidity:
				curShowType = HUMIDITY;
				statusDateShow.setText("");
				setShowData();
				statusView.setTitle("湿度", "时间", "度数");
				statusView.invalidate();
				break;
			case R.id.temperature:
				curShowType = TEMPERATURE;
				statusDateShow.setText("");
				setShowData();
				statusView.setTitle("温度", "时间", "度数");
				statusView.invalidate();
				break;
			case R.id.co2:
				curShowType = CO2;
				statusDateShow.setText("");
				setShowData();
				statusView.setTitle("CO2", "时间", "度数");
				statusView.invalidate();
				break;
			case R.id.light:
				curShowType = LIGHT;
				statusDateShow.setText("");
				setShowData();
				statusView.setTitle("光照", "时间", "度数");
				statusView.invalidate();
				break;
			case R.id.statusButton:
				String temp = itemText.getText().toString();
				String dateTemp = statusDateShow.getText().toString();
				if (temp.isEmpty() && dateTemp.isEmpty()) {
					number = 2;
					setShowData();
				} else if (!temp.isEmpty()) {
					number = Integer.parseInt(temp);
					setShowData();
				} else if (!dateTemp.isEmpty()) {
					setShowData(dateTemp);
				}
				statusView.invalidate();
				break;
			case R.id.statueDateSet:
				StatusDatePick picker = new StatusDatePick();
				picker.show(getFragmentManager(), "datePicker");
				itemText.setText("");
				break;
			}

		}

	}
}
