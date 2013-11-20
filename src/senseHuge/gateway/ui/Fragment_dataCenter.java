package senseHuge.gateway.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import senseHuge.gateway.model.PackagePattern;
import senseHuge.gateway.service.DataProvider;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.testgateway.R;

public class Fragment_dataCenter extends ListFragment {
	ListView list;
	Button showAll;
	Button showAsTime;
	private ContentResolver contentResolver;
	EditText editText;
	TextView dataParse;
	List<Map<String, String>> data;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_data_center, container,
				false);
		list = (ListView) view.findViewById(android.R.id.list);
		contentResolver = this.getActivity().getContentResolver();
		showAll = (Button) view.findViewById(R.id.showAll);
		showAsTime = (Button) view.findViewById(R.id.searchTitle);
		editText = (EditText) view.findViewById(R.id.searchRecentNum);
		dataParse = (TextView) view.findViewById(R.id.dataParse);

		// list.setItemsCanFocus(false);

		showAllData();

		showAll.setOnClickListener(new MyButtonClickListener());
		showAsTime.setOnClickListener(new MyButtonClickListener());
		// list.setFocusable(true);
		// list.setFocusableInTouchMode(true);

		// showAllData();
		// list.requestFocus();
		return view;
	}

	private class MyItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			Map<String, String> packageMessage = data.get(arg2);
			dataParse.setText("dffdfd " + arg2);
			Iterator<?> it = packageMessage.entrySet().iterator();
			String message;
			PackagePattern pp;
			// 取出当前需要解析的数据
			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry) it.next();
				if (pairs.getKey().equals("message")) {
					message = pairs.getValue().toString();
					try {
						pp = MainActivity.xmlTelosbPackagePatternUtil
								.parseTelosbPackage(message);
						showTheParsedPackage(pp);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}

		}

		private void showTheParsedPackage(PackagePattern pp) {
			// TODO Auto-generated method stub
			Iterator<?> it = pp.DataField.entrySet().iterator();
			StringBuffer sb = new StringBuffer();
			while (it.hasNext()) {
				Map.Entry pairs = (Map.Entry) it.next();
				sb.append(pairs.getKey().toString() + ": "
						+ pairs.getValue().toString());
				sb.append("\n");
			}
			dataParse.setText(sb.toString());
		}

	}

	private class MyButtonClickListener implements OnClickListener {

		@Override
		public void onClick(View arg0) {
			// TODO Auto-generated method stub
			switch (arg0.getId()) {
			case R.id.showAll:
				showAllData();
				break;
			case R.id.searchTitle:
				showRecent();
				break;
			}
		}

	}

	private void showRecent() {
		// TODO Auto-generated method stub
//		list.setEnabled(true);
		Cursor cursor = contentResolver.query(DataProvider.CONTENT_URI,
				new String[] { "_id", "message", "Ctype", "NodeID",
						"receivetime" }, null, null, "receivetime DESC");
		int i = getTheInputNum();
		if (i == 0) {
			i = 1;
		}
		data = new ArrayList<Map<String, String>>();
		Map<String, String> item;
		while (cursor.moveToNext() && i > 0) {
			item = new HashMap<String, String>();
			item.put("message",
					cursor.getString(cursor.getColumnIndex("message")));
			item.put("Ctype", cursor.getString(cursor.getColumnIndex("Ctype")));
			item.put("NodeID",
					cursor.getString(cursor.getColumnIndex("NodeID")));
			/*
			 * item.put("status",
			 * cursor.getString(cursor.getColumnIndex("status")));
			 */
			item.put("receivetime",
					cursor.getString(cursor.getColumnIndex("receivetime")));
			data.add(item);
			i--;
		}
		SimpleAdapter adapter = new SimpleAdapter(this.getActivity(), data,
				R.layout.data_center_style, new String[] { "message", "Ctype",
						"NodeID", "receivetime" }, new int[] {
						R.id.DBmessageShow, R.id.DBCtypeShow,
						R.id.DBNodeIDShow, R.id.DBreceivetimeShow });
		/*
		 * ( this.getActivity(), R.layout.data_center_style, cursor, new
		 * String[] { "message", "Ctype", "NodeID", "status", "receivetime" },
		 * new int[] { R.id.DBmessageShow, R.id.DBCtypeShow, R.id.DBNodeIDShow,
		 * R.id.DBstatusShow, R.id.DBreceivetimeShow },
		 * CursorAdapter.FLAG_AUTO_REQUERY);
		 */
		list.setAdapter(adapter);
		list.setOnItemClickListener(new MyItemClickListener());
		this.getActivity().startManagingCursor(cursor); // 查找后关闭游标
	}

	@SuppressLint("NewApi")
	private int getTheInputNum() {
		// TODO Auto-generated method stub
		String s = editText.getText().toString();
		if (s.isEmpty())
			return 0;
		else if (isNum(s)) {
			return Integer.parseInt(s);
		} else
			return 0;
	}

	private boolean isNum(String s) {
		// TODO Auto-generated method stub
		for (int i = 0; i < s.length(); i++) {
			if (!Character.isDigit(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	private void showAllData() {
		// TODO Auto-generated method stub

		// Cursor cursor = MainActivity.mDb.query("Telosb", new String[] {
		// "message", "Ctype", "NodeID", "status", "receivetime" }, null,
		// null, null, null, "receivetime DESC");
//		list.setEnabled(false);
		Cursor cursor = contentResolver.query(DataProvider.CONTENT_URI,
				new String[] { "_id", "message", "Ctype", "NodeID",
						"receivetime" }, null, null, "receivetime DESC");

		SimpleCursorAdapter adapter = new SimpleCursorAdapter(
				this.getActivity(), R.layout.data_center_style, cursor,
				new String[] { "message", "Ctype", "NodeID", "receivetime" },
				new int[] { R.id.DBmessageShow, R.id.DBCtypeShow,
						R.id.DBNodeIDShow, R.id.DBreceivetimeShow },
				CursorAdapter.FLAG_AUTO_REQUERY);
		list.setAdapter(adapter);
		list.setOnItemClickListener(null);
		dataParse.setText("");
		this.getActivity().startManagingCursor(cursor); // 查找后关闭游标
	}
}
