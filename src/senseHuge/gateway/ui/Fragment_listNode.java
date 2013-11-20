package senseHuge.gateway.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import senseHuge.gateway.Dao.MySQLiteDbHelper;
import senseHuge.gateway.model.PackagePattern;
import senseHuge.gateway.service.ListNodePrepare;
import android.app.Activity;
import android.app.AlertDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.testgateway.R;

public class Fragment_listNode extends Fragment {
	// ������ʾ�Ľڵ�����
	public static List<Map<String, Object>> nodeList = new ArrayList<Map<String, Object>>();
	// ���ҳ�������ʾ�Ĳ��ظ��Ľڵ�id
	public static List<String> nodeId = new ArrayList<String>();
	SimpleAdapter adapter;
	// public static boolean isChange = false;
	ListView packageList;
	View dialog;
	TextView packageAfterParse;
	// Button refreshButton;
	// dialog��ʾ�����ĵ�ǰ�ڵ�����а�������
	List<Map<String, String>> content;
	GridView gridview;
	MySQLiteDbHelper mdbhelper;
	SQLiteDatabase db;
	ListNodePrepare nodePrepare;

	// �Ҳ��������ʾ
	/*
	 * FragmentManager fManager; Fragment packageDetialFragment;
	 * FragmentTransaction fTransaction;
	 */

	// List<String> powerList = new ArrayList<String>();
	@Override
	public void onAttach(Activity a) {
		super.onAttach(a);
		// init();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// fManager = this.getChildFragmentManager();
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_list_node, container,
				false);
		gridview = (GridView) view.findViewById(R.id.gridview);
		nodePrepare = new ListNodePrepare();
		mdbhelper = new MySQLiteDbHelper(this.getActivity());
		/*
		 * refreshButton = (Button) view.findViewById(R.id.refresh);
		 * refreshButton.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { // TODO Auto-generated
		 * method stub adapter.notifyDataSetChanged(); }
		 * 
		 * });
		 */
		show();
		// ʵʱˢ��
		if (MainActivity.serialPortConnect)
			new Thread(new MyThread()).start();
		return view;
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// Ҫ��������
			super.handleMessage(msg);
			switch (msg.arg1) {
			case 1:
				nodePrepare.prepare();
				adapter.notifyDataSetChanged();
			}

		}
	};

	public class MyThread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				try {
					Thread.sleep(2000);// �߳���ͣ2�룬��λ����
					Message message = new Message();
					message.arg1 = 1;
					handler.sendMessage(message);// ������Ϣ
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	private void show() {
		// TODO Auto-generated method stub
		// ʵ����һ��������
		adapter = new SimpleAdapter(this.getActivity(), nodeList,
				R.layout.list_node_page_style, new String[] { "ͼƬ", "Դ�ڵ���",
						"�ڵ��ѹ" }, new int[] { R.id.listNodeImage,
						R.id.listNodeId, R.id.listNodePower });
		gridview.setAdapter(adapter);
		gridview.setOnItemClickListener(new MyItemClickListener());
	}

	class MyItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			dialog = LayoutInflater.from(arg1.getContext()).inflate(
					R.layout.list_node_package_style, null);

			packageAfterParse = (TextView) dialog
					.findViewById(R.id.packageRight);

			getThePackage(nodeId.get(arg2));
			AlertDialog.Builder d = new AlertDialog.Builder(arg1.getContext());
			d.setTitle("��" + (arg2 + 1) + "���ڵ��еİ����ڵ�ID�ǣ�" + nodeId.get(arg2))
					.setView(dialog).setPositiveButton("ȷ��", null);

			// ��ʼ���Ҳ����ʾ

			/*
			 * packageDetialFragment = Fragment_packageDetail.newInstance(0);
			 * fTransaction = getFragmentManager().beginTransaction();
			 * fTransaction.add(R.id.packageRight, packageDetialFragment);
			 * fTransaction.commit();
			 */
			d.show();

		}

		// �õ���Ӧ�ڵ��ȫ����������Ϊ�ڵ�ID
		private void getThePackage(String string) {
			// TODO Auto-generated method stub
			db = mdbhelper.getReadableDatabase();
			Cursor cursor = db.query("Telosb", new String[] { "Ctype",
					"message", "receivetime" }, "NodeID=?",
					new String[] { string }, null, null, "receivetime DESC");
			content = new ArrayList<Map<String, String>>();
			Map<String, String> data;
			while (cursor.moveToNext()) {
				data = new HashMap<String, String>();
				String message = cursor.getString(cursor
						.getColumnIndex("message"));
				// String status = cursor.getString(cursor
				// .getColumnIndex("status"));
				String type = cursor.getString(cursor.getColumnIndex("Ctype"));
				String receicvetime = cursor.getString(cursor
						.getColumnIndex("receivetime"));

				data.put("type", type);
				// data.put("status", status);
				data.put("message", message);
				data.put("receivetime", receicvetime);
				content.add(data);
			}
			putDataIntoPackage(content);
			db.close();
		}

		private void putDataIntoPackage(List<Map<String, String>> content) {
			// TODO Auto-generated method stub
			packageList = (ListView) dialog.findViewById(android.R.id.list);
			SimpleAdapter adapter = new SimpleAdapter(dialog.getContext(),
					content, R.layout.list_node_package, new String[] { "type",
							"message", "receivetime" }, new int[] {
							R.id.packageType, R.id.packageMessage,
							R.id.packageReceivetime });
			packageList.setAdapter(adapter);

			// �����б�����¼�
			packageList.setOnItemClickListener(new MyListItemClickListener());
		}

		class MyListItemClickListener implements OnItemClickListener {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Map<String, String> packageMessage = content.get(arg2);
				Iterator<?> it = packageMessage.entrySet().iterator();
				String message;
				PackagePattern pp;
				// ȡ����ǰ��Ҫ����������
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
				packageAfterParse.setText(sb.toString());
			}
		}

	}

}