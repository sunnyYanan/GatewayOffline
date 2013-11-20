package senseHuge.gateway.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import senseHuge.gateway.model.PackagePattern;
import senseHuge.gateway.model.TelosbPackage;
import senseHuge.gateway.ui.Fragment_serialconfig;
import senseHuge.gateway.ui.MainActivity;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

public class SerialportDataProcess extends Thread {
	// List<String> list = new ArrayList<String>();//ȫ����
	private ContentResolver contentResolver;

	public SerialportDataProcess() {

	}

	public SerialportDataProcess(ContentResolver resolver) {
		this.contentResolver = resolver;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		super.run();
		// ��ȡ�������ݰ�
		String headTest = "00FFFF";
		TelosbPackage telosbPackage = new TelosbPackage();
		int i;
		while (MainActivity.serialPortConnect) {
			i = Fragment_serialconfig.serialUtil.findhead(headTest);
			// System.out.println("��ͷλ�ã�"+i);
			// System.out.println("δ����ǰ��"+Fragment_serialconfig.serialUtil.stringBuffer.toString());
			if (i < 0
					&& Fragment_serialconfig.serialUtil.stringBuffer.length() > 6) {
				Fragment_serialconfig.serialUtil
						.delete(Fragment_serialconfig.serialUtil.stringBuffer
								.length() - 6);
			} else if (i > 0) {
				Fragment_serialconfig.serialUtil.delete(i);
			}
			// System.out.println("������"+Fragment_serialconfig.serialUtil.stringBuffer.toString());
			if (Fragment_serialconfig.serialUtil.stringBuffer.length() > 300) {
				System.out.println("���������+++++++++++++++++++++++++++++++++++");
				System.out
						.println(Fragment_serialconfig.serialUtil.stringBuffer
								.toString());
				System.out
						.println("+++++++++++++++++++++++++++++++++++++++++++++");
				String telosbData = Fragment_serialconfig.serialUtil
						.getFirstData();
				System.out.println("receive package:" + telosbData);

				if (telosbData == null) {
					System.out.println("��Ϊempty");

				} else {
					PackagePattern telosbPackagePattern = null;
					try {
						// ���ݽ���
						telosbPackagePattern = MainActivity.xmlTelosbPackagePatternUtil
								.parseTelosbPackage(telosbData);
					} catch (Exception e) {
						System.out.println("�쳣");
						e.printStackTrace();
						continue;

					}
					/*if (MainActivity.httpClientUtil.PostTelosbData("",
							telosbData)) {
						telosbPackage.setStatus("���ϴ�");
					} else {
						telosbPackage.setStatus("δ�ϴ�");
					}*/

					telosbPackage.setCtype(telosbPackagePattern.ctype);
					telosbPackage.setMessage(telosbData);
					telosbPackage.setNodeID(telosbPackagePattern.nodeID);

					ContentValues values = new ContentValues(); // �൱��map
					values.put("message", telosbPackage.getMessage());
					values.put("Ctype", telosbPackage.getCtype());
					values.put("NodeID", telosbPackage.nodeID);
//					values.put("status", telosbPackage.getStatus());
					Date date = new Date();
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
							"yyyy-MM-dd hh:mm:ss");
					values.put("receivetime", simpleDateFormat.format(date));

					/*
					 * db = MainActivity.mDbhelper.getWritableDatabase();
					 * db.insert("Telosb", null, values); db.close();
					 */
					this.contentResolver.insert(DataProvider.CONTENT_URI,
							values);
					/*
					 * if (telosbData != null) { list.add(telosbData); //
					 * Packagesingnal(); }
					 */
					// System.out.println(list.size() + "_____________");
					System.gc();// ��������
				}
			}
		}
	}
}