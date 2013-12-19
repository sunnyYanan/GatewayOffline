package senseHuge.gateway.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import senseHuge.gateway.Dao.MySQLiteDbHelper;
import senseHuge.gateway.model.NodeTree;
import senseHuge.gateway.model.PackagePattern;
import senseHuge.gateway.ui.Fragment_listNode;
import senseHuge.gateway.ui.MainActivity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;

import com.example.testgateway.R;

public class ListNodePrepare {
	SQLiteDatabase db;
	public static String currentId = null;
	public static boolean changed = true;
	public static NodeTree nodeTree = new NodeTree();

	public void prepare() {
		Thread listNodeThread = new Thread(new MyThread());
		listNodeThread.start();
	}

	public synchronized void prepare(String nodeId, String message, String type) {
		changed = false;
		db = MainActivity.mDbhelper.getReadableDatabase();
		addNodeIntoList(nodeId);
		if (type.equals("C1")) {
			formNodeStructureTree(nodeId, message);
			findIfTrige(nodeId, message);
		}
		changed = true;
		// findIfTriger(nodeId);// ��ǰ�յ��Ľڵ������Ƿ񳬳����ݿ���Ԥ������
		/*
		 * IntentFilter filter = new IntentFilter();
		 * filter.addAction("android.provider.Telephony.SMS_RECEIVED");
		 * this.registerReceiver(new TestReceiver(), filter);
		 */
	}

	private void findIfTrige(String nodeId, String message) {
		// TODO Auto-generated method stub
		Cursor cursor = db.query(MySQLiteDbHelper.TABLEALERTSETTING,
				new String[] { "type" }, null, null, null, null, null);
		PackagePattern mpp = null;
		// �����������
		try {
			mpp = MainActivity.xmlTelosbPackagePatternUtil
					.parseTelosbPackage(message);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		while (cursor.moveToNext()) {
			String alertType = cursor.getString(cursor.getColumnIndex("type"));
			float alertTypeValue = findTheAlertTypeValue(alertType, mpp);
			if(alertTypeValue!=0) {
				trigerTheAlert(alertTypeValue,alertType);
			}
		}
		cursor.close();
	}

	private float findTheAlertTypeValue(String alertType, PackagePattern mpp) {
		// TODO Auto-generated method stub
		float value = 0;
		Iterator<?> it = mpp.DataField.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			if (pairs.getKey().equals(alertType)) {
				return Float.parseFloat(pairs.getValue().toString());
			}
		}
		return value;
	}

	class MyThread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			getTheNodeInfo();
			formNodeStructureTree();
		}
	}

	// �ڽ��������������ȡ���ڵ��ţ������ظ��ʹ���Ҫ��ʾ�Ľڵ��б���
	private synchronized void getTheNodeInfo() {
		// TODO Auto-generated method stub
		db = MainActivity.mDbhelper.getReadableDatabase();
		Cursor cursor = db.query(MySQLiteDbHelper.TABLEMESSAGE,
				new String[] { "NodeID" }, null, null, null, null,
				"receivetime DESC");
		while (cursor.moveToNext()) {
			String id = cursor.getString(cursor.getColumnIndex("NodeID"));
			if (!Fragment_listNode.nodeId.contains(id)) {
				Fragment_listNode.nodeId.add(id);
			}
		}
		// Fragment_listNode.nodeList.clear();
		// ��ʼ��ʾ����
		Collections.sort(Fragment_listNode.nodeId);
		for (int i = 0; i < Fragment_listNode.nodeId.size(); i++) {
			System.out.println("�ڵ㣺" + Fragment_listNode.nodeId.get(i));
			addNodeIntoList(Fragment_listNode.nodeId.get(i));
		}
		cursor.close();
	}

	// ���ڵ���뵽��ʾ�б���
	private void addNodeIntoList(String nodeId) {
		// TODO Auto-generated method stub
		// Fragment_listNode.nodeList.remove(Fragment_listNode.nodeList.)
		List<Map<String, Object>> nodes = Fragment_listNode.getNodes();
		List<Map<String, Object>> tempNodes = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < nodes.size(); i++) {
			tempNodes.add(nodes.get(i));
		}
		for (int i = 0; i < tempNodes.size(); i++) {
			if (tempNodes.get(i).get("Դ�ڵ���").equals(nodeId)) {
				tempNodes.remove(i);
				break;
			}
		}
		if (!Fragment_listNode.nodeId.contains(nodeId)) {
			Fragment_listNode.nodeId.add(nodeId);
		}
		Collections.sort(Fragment_listNode.nodeId);

		Map<String, Object> item = new HashMap<String, Object>();
		item.put("ͼƬ", R.drawable.ic_launcher);
		item.put("Դ�ڵ���", nodeId);
		// System.out.println("����ڵ㣺"+nodeId);
		computeTheNodePower(nodeId, item);
		// item.put("�ڵ��ѹ", "11");
		tempNodes.add(item);
		Collections.sort(tempNodes, new MyComparator());
		nodes.clear();
		for (int i = 0; i < tempNodes.size(); i++) {
			nodes.add(tempNodes.get(i));
		}
	}

	/*
	 * private void ifTrigeTheAlert(String nodeId) { // TODO Auto-generated
	 * method stub currentId = nodeId; Cursor alertCursor =
	 * db.query(MySQLiteDbHelper.TABLEALERTSETTING, new String[] { "type" },
	 * null, null, null, null, null); Cursor cursor =
	 * db.query(MySQLiteDbHelper.TABLEMESSAGE, new String[] { "message" },
	 * "NodeID=? AND CType=?", new String[] { nodeId, "C1" }, null, null,
	 * "receivetime DESC"); }
	 */

	private class MyComparator implements Comparator<Object> {

		@Override
		public int compare(Object arg0, Object arg1) {
			// TODO Auto-generated method stub
			return ((Map<String, String>) arg0).get("Դ�ڵ���").compareTo(
					((Map<String, String>) arg1).get("Դ�ڵ���"));
		}

	}

	/**
	 * @param string
	 *            �ڵ��ŵ��ַ�����ʶ
	 * @param item
	 *            �������map �õ��ڵ�ĵ�����������������
	 */
	private void computeTheNodePower(String string, Map<String, Object> item) {
		// TODO Auto-generated method stub
		// �õ��ýڵ�����4����¼,�����������ƽ��ֵ
		currentId = string;
		Cursor cursor = db.query(MySQLiteDbHelper.TABLEMESSAGE,
				new String[] { "message" }, "NodeID=? AND CType=?",
				new String[] { string, "C1" }, null, null, "receivetime DESC");
		int i = 4;
		int cur = 0;
		float[] powers = new float[i];
		/*
		 * if(cursor.equals(null)) { System.out.println("δ���ҵ��ýڵ��c1��"); }
		 */

		while (cursor.moveToNext() && i > 0) {
			String message = cursor.getString(cursor.getColumnIndex("message"));
			try {
				// �����������
				PackagePattern mpp = MainActivity.xmlTelosbPackagePatternUtil
						.parseTelosbPackage(message);
				float power = getTheNodePower(mpp);
				System.out.println("power after " + cur + ":" + power);
				// ��������ȷ�����ݣ��������ֵ4096
				if (power > 4096 || power <= 0) {
					System.out
							.println("a bad data that has power bigger than Max or smaller than 0 happens");
					continue;
				}
				powers[cur++] = power;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			i--;
		}
		for (int j = 0; j < powers.length; j++) {
			System.out.println(string + " powers" + j + ":" + powers[j]);
		}
		float resultPower = getTheAverage(powers);
		if (resultPower == 0.0f)
			item.put("�ڵ��ѹ", "unknown");
		else
			item.put("�ڵ��ѹ", resultPower + "v");
		cursor.close();
	}

	// �����ѹƽ��ֵ
	private float getTheAverage(float[] powers) {
		// TODO Auto-generated method stub
		float powerSum = 0;
		int num = powers.length;
		for (int i = 0; i < powers.length; i++) {
			if (powers[i] != 0)
				powerSum += powers[i];
			else
				num = num - 1;

		}
		float power = (float) (powerSum / num / 4096 * 2.5);
		float b = (float) (Math.round(power * 1000)) / 1000;// ��ǰ�ڵ����4�����ݵ�ƽ������ֵ
		// (�����100����2λС����,���Ҫ����λ,��4λ,��������100�ĳ�10000)
		System.out.println("average power��" + b);

		String trige = findIfTriger("Ԥ������");
		if (trige != null && b != 0 && trige.equals("1")) {
			trigerTheAlert(b, "Ԥ������");
		}
		return b;
	}

	private String findIfTriger(String type) {
		// TODO Auto-generated method stub
		String alert = null;
		Cursor cursor = db.query(MySQLiteDbHelper.TABLEALERTSETTING,
				new String[] { "alert" }, "type=?", new String[] { type },
				null, null, null);
		while (cursor.moveToNext()) {
			// ����д��Ĺ����趨����ʱ��ʵֻ��1������
			alert = cursor.getString(cursor.getColumnIndex("alert"));
		}
		cursor.close();
		return alert;
	}

	private void trigerTheAlert(float b, String type) {
		// TODO Auto-generated method stub
		String alert = findTheAlertValueSetting(type);
		// ��������
		if (alert != null) {
			// Ԥ����ֵ
			int pos = alert.indexOf("%");
			if (pos > 0) {
				// ���õ��ǰٷֱ�ֵ
				String valueStr = alert.substring(0, pos);
				float value = Float.parseFloat(valueStr);
				if (type.equals("Ԥ������")) {
					if (b / 2.5 <= (value / 100)) {
						System.out.println("������ֵ:" + b / 2.5);
						// ��������
						String musicPath = findTheAlertMusicPath(type);
						System.out.println("musicPath " + musicPath);
						playAlertMusic(musicPath);
					}
				}
			} else {
				// ���õ���ֵ����д
				if(b<Float.parseFloat(alert)) {//��ǰֵС��Ԥ��ֵ
					String musicPath = findTheAlertMusicPath(type);
					System.out.println("musicPath " + musicPath);
					playAlertMusic(musicPath);
				}
			}

		}
	}

	private void playAlertMusic(String musicPath) {
		// TODO Auto-generated method stub
		try {
			MainActivity.mp.setDataSource(musicPath);
			MainActivity.mp.prepareAsync();
			MainActivity.mp
					.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
						@Override
						public void onPrepared(MediaPlayer mp) {
							// TODO Auto-generated method stub
							mp.start();// �첽׼�����ݵķ�����service�ǿ������û�������Ӧ�ý���ʱ�����У���ʱ��Ҫwake
						}

					});
			MainActivity.mp.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer arg0) {
					// TODO Auto-generated method stub
					arg0.release();
				}
			});
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private String findTheAlertMusicPath(String type) {
		// TODO Auto-generated method stub
		String path = null;
		Cursor cursor = db.query(MySQLiteDbHelper.TABLEALERTSETTING,
				new String[] { "path" }, "type=?", new String[] { type }, null,
				null, null);
		while (cursor.moveToNext())
			path = cursor.getString(cursor.getColumnIndex("path"));
		cursor.close();
		return path;
	}

	private String findTheAlertValueSetting(String type) {
		// TODO Auto-generated method stub
		String alertSetting = null;
		Cursor cursor = db.query(MySQLiteDbHelper.TABLEALERTSETTING,
				new String[] { "value" }, "type=?", new String[] { type },
				null, null, null);
		while (cursor.moveToNext()) {
			// ����д��Ĺ����趨����ʱ��ʵֻ��1������
			alertSetting = cursor.getString(cursor.getColumnIndex("value"));
		}
		cursor.close();
		return alertSetting;
	}

	private float getTheNodePower(PackagePattern mpp) {
		// TODO Auto-generated method stub
		Iterator<?> it = mpp.DataField.entrySet().iterator();
		String power = null;// ʮ�����Ʊ�ʾ
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			if (pairs.getKey().equals("�ڵ��ѹ")) {
				power = pairs.getValue().toString();
				System.out.println("power before:" + power);
			}
		}
		float powerDeci = 0;
		for (int i = power.length() - 1; i >= 0; i--) {
			char n = power.charAt(i);
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

			powerDeci += num * Math.pow(16, 3 - i);
		}
		return powerDeci;
	}

	public void formNodeStructureTree(String nodeId, String message) {
		List<String> path = new ArrayList<String>();
		if (!nodeId.equals("0000"))
			path.add(nodeId);

		try {
			// �����������
			PackagePattern mpp = MainActivity.xmlTelosbPackagePatternUtil
					.parseTelosbPackage(message);
			getPath(path, mpp);// �õ���ǰ�ڵ�C1���Ĵ���·��
			nodeTree.insert(path);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void formNodeStructureTree() {
		db = MainActivity.mDbhelper.getReadableDatabase();
		Cursor cursor = db.query(MySQLiteDbHelper.TABLEMESSAGE, new String[] {
				"message", "NodeID" }, "CType=?", new String[] { "C1" }, null,
				null, "receivetime DESC");
		// int num = 58;
		while (cursor.moveToNext()) {
			String message = cursor.getString(cursor.getColumnIndex("message"));
			String curNode = cursor.getString(cursor.getColumnIndex("NodeID"));
			List<String> path = new ArrayList<String>();
			if (!curNode.equals("0000"))
				path.add(curNode);
			try {
				// �����������
				PackagePattern mpp = MainActivity.xmlTelosbPackagePatternUtil
						.parseTelosbPackage(message);
				getPath(path, mpp);// �õ���ǰ�ڵ�C1���Ĵ���·��
				for (int i = 0; i < path.size(); i++) {
					System.out.println("path:" + i + " " + path.get(i));
				}
				nodeTree.insert(path);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// num--;
		}

	}

	private void getPath(List<String> path, PackagePattern mpp) {
		// TODO Auto-generated method stub
		Iterator<?> it = mpp.DataField.entrySet().iterator();
		String node = null;
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			if (pairs.getKey().equals("ÿ���ڵ�")) {
				node = pairs.getValue().toString();
				System.out.println("ȫ����" + node);
			}
		}
		for (int i = 0; i < node.length() - 4; i = i + 4) {
			String str = node.substring(i, i + 4);
			System.out.println("��·�����룺"+str);
			if (!str.equals("0000") && isValid(str)) {
				path.add(str);
			} else {
				break;
			}
		}
		int half = (path.size() - 1) / 2;
		int num = repeatPosition(path, half);
		System.out.println("�ظ�λ�ã�"+num);
		if (num != 0)
			removeRepeat(path, num);
	}

	private void removeRepeat(List<String> path, int num) {
		// TODO Auto-generated method stub
		List<String> temp = new ArrayList<String>();
		for (int i = 0; i <= num; i++) {
			temp.add(path.get(i));
		}
		path.clear();
		for (int i = 0; i < temp.size(); i++)
			path.add(temp.get(i));
	}

	private int repeatPosition(List<String> path, int pos) {
		// TODO Auto-generated method stub
		int num = pos;
		if (pos == 1)
			return 0;
		for (int i = 1; i < pos; i++) {
			if (!path.get(i).equals(path.get(i + pos))) {
				num = repeatPosition(path, --pos);
				break;
			}
		}
		return num;
	}

	// ��ǰ�ڵ��ǺϷ��ڵ㣬�����쳣
	private boolean isValid(String str) {
		// TODO Auto-generated method stub
		if (Fragment_listNode.nodeId.contains(str))
			return true;
		return false;
	}

	public NodeTree getNodeTree() {
		return nodeTree;
	}

	public void setNodeTree(NodeTree nodeTree) {
		this.nodeTree = nodeTree;
	}
}
