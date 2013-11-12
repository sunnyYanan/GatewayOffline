package senseHuge.gateway.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.testgateway.R;

public class Fragment_statusValueCheck extends Fragment {
	StatusValueCheckView statusView;
	Button humidity;
	Button temperature;
	Button light;
	Button co2;

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
		
		temperature.setOnClickListener(new MyButtonOnClickListener());
		humidity.setOnClickListener(new MyButtonOnClickListener());
		light.setOnClickListener(new MyButtonOnClickListener());
		co2.setOnClickListener(new MyButtonOnClickListener());
		statusView.setData(new String[] { "7-11", "7-12", "7-13", "7-14", "7-15",
				"7-16", "7-17" }, // X轴刻度
				new String[] { "0", "50", "100", "150", "200", "250" ,"300"}, // Y轴刻度
				new float[] { 15, 23, 10, 36, 45, 40, 12 });// 数据);
		return view;
	}
	public class MyButtonOnClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.humidity:
				statusView.setData(new String[] { "7-11", "7-12", "7-13", "7-14", "7-15",
						"7-16", "7-17" }, // X轴刻度
						new String[] { "0", "50", "100", "150", "200", "250" ,"300"}, // Y轴刻度
						new float[] { 15, 23, 10, 36, 45, 40, 12 });// 数据);
				statusView.invalidate();
				break;
			case R.id.temperature:
				statusView.setData(new String[] { "11", "12", "13", "14", "15",
						"16", "17" }, // X轴刻度
						new String[] { "0", "50", "100", "150", "200", "250" ,"300"}, // Y轴刻度
						new float[] { 15, 23, 10, 36, 45, 40, 12 });// 数据);
				statusView.invalidate();
				break;
			case R.id.co2:
				statusView.setData(new String[] { "1", "2", "3", "4", "5",
						"6", "7" }, // X轴刻度
						new String[] { "0", "50", "100", "150", "200", "250" ,"300"}, // Y轴刻度
						new float[] { 15, 23, 10, 36, 45, 40, 12 });// 数据);
				statusView.invalidate();
				break;
			case R.id.light:
				statusView.setData(new String[] { "111", "112", "113", "114", "115",
						"116", "117" }, // X轴刻度
						new String[] { "0", "50", "100", "150", "200", "250" ,"300"}, // Y轴刻度
						new float[] { 15, 23, 10, 36, 45, 40, 12 });// 数据);
				statusView.invalidate();
				break;
			}
			
		}
		
	}
}
