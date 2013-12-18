package senseHuge.gateway.ui;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import senseHuge.gateway.Dao.MySQLiteDbHelper;
import senseHuge.gateway.listener.Listenable;
import senseHuge.gateway.listener.MyEvent;
import senseHuge.gateway.listener.MySource;
import senseHuge.gateway.model.PackagePattern;
import senseHuge.gateway.model.TelosbPackage;
import senseHuge.gateway.service.ListNodePrepare;
import senseHuge.gateway.service.LocalConfigService;
import senseHuge.gateway.util.OfflineBackupUtil;
import senseHuge.gateway.util.SelectDialog;
import senseHuge.gateway.util.XmlTelosbPackagePatternUtil;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.testgateway.R;

//import senseHuge.gateway.model.RingBuffer;

public class MainActivity extends FragmentActivity {

	private static final int capibity = 50;
	private static final String tag = "sensehuge:";
	protected static final String tags = "sensehuge:";
	public static MediaPlayer mp;

	public static MySQLiteDbHelper mDbhelper;
	// SerialUtil serialUtil = new SerialUtil();
	// public static HttpClientUtil httpClientUtil;
	PackagePattern packagePattern = null;
	public static XmlTelosbPackagePatternUtil xmlTelosbPackagePatternUtil;
	ListNodePrepare listNodePrepare;

	// public static boolean serverConnect = false;// 服务器是否连接
	public static boolean serialPortConnect = false;// 串口是否连接

	// public RingBuffer<String> ringBuffer = new RingBuffer<String>(capibity);

	// 调用以下资源的getvalue方法也可以判断当前的连接状态，当监听多个时方便使用的，目前没用
	// MySource ms;// 这个变量应该是多余的，待删除
	MySource httpserverState;
	MySource serialState;
	// MySource havePackage;

	FragmentManager manager;
	LinearLayout layout;
	Fragment f_serialPort, f_listnode, f_nodeSetting, f_dataCenter, f_aboutUs,
			f_linkSetting, f_statusValueCheck, f_topoStructure;
	Button serialPortSetting;
	Button eternetSetting;
	Button wifiSetting;
	// Button serverSetting;
	Button sinkSetting;
	Button sinkCheck;
	/*
	 * Button internetSetting; Button wifiSetting;
	 */
	Button statusValueCheck;
	Button linkSetting;
	Button dataCenter;
	Button topoStructure;
	Button aboutUs;
	Button quit;

	PendingIntent m_restartIntent;

	private UncaughtExceptionHandler m_handler = new UncaughtExceptionHandler() {
		@Override
		public void uncaughtException(Thread thread, Throwable ex) {
			Log.d("wrong", "uncaught exception is catched!");
			AlarmManager mgr = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
			mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 3000,
					m_restartIntent);
			System.exit(2);
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// fListNode = new Fragment_listNode();

		Intent intent = getIntent();
		m_restartIntent = PendingIntent.getActivity(getApplicationContext(), 0,
				intent, Intent.FLAG_ACTIVITY_NEW_TASK);

		// 串口，服务器，节点监听事务
		manager = getSupportFragmentManager();
		listNodePrepare = new ListNodePrepare();
		FragmentTransaction transaction = manager.beginTransaction();
		f_serialPort = new Fragment_serialconfig();
		f_listnode = new Fragment_listNode();
		f_nodeSetting = new Fragment_nodeSetting();
		f_dataCenter = new Fragment_dataCenter();
		f_aboutUs = new Fragment_aboutUs();
		// f_linkSetting = new Fragment_linkSetting();
		f_statusValueCheck = new Fragment_statusValueCheck();
		f_topoStructure = new Fragment_topoStructure();

		// 得到按钮以及设置按钮监听器
		serialPortSetting = (Button) findViewById(R.id.serialPortSetting);
		sinkSetting = (Button) findViewById(R.id.sinkSetting);
		sinkCheck = (Button) findViewById(R.id.sinkCheck);
		linkSetting = (Button) findViewById(R.id.linkSetting);
		statusValueCheck = (Button) findViewById(R.id.statusValueCheck);
		dataCenter = (Button) findViewById(R.id.dataCenter);
		aboutUs = (Button) findViewById(R.id.aboutUs);
		quit = (Button) findViewById(R.id.quit);
		topoStructure = (Button) findViewById(R.id.topoStructure);

		serialPortSetting.setOnClickListener(new ButtonClickListener());
		sinkSetting.setOnClickListener(new ButtonClickListener());
		sinkCheck.setOnClickListener(new ButtonClickListener());
		linkSetting.setOnClickListener(new ButtonClickListener());
		statusValueCheck.setOnClickListener(new ButtonClickListener());
		dataCenter.setOnClickListener(new ButtonClickListener());
		aboutUs.setOnClickListener(new ButtonClickListener());
		quit.setOnClickListener(new ButtonClickListener());
		topoStructure.setOnClickListener(new ButtonClickListener());

		// 默认启动事务：节点
		transaction.add(R.id.fragment_container, f_serialPort);
		transaction.commit();

		init();
		System.out.println("inited:");

		Thread.setDefaultUncaughtExceptionHandler(m_handler);
	}

	class ButtonClickListener implements OnClickListener {
		FragmentTransaction transaction;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.serialPortSetting: {
				transaction = manager.beginTransaction();
				transaction.replace(R.id.fragment_container, f_serialPort);
				Log.i(tag, "button1");
				transaction.commit();
				break;
			}
			/*
			 * case R.id.serverSetting: { transaction =
			 * manager.beginTransaction();
			 * transaction.replace(R.id.fragment_container, f_server);
			 * transaction.commit(); break; }
			 */
			case R.id.sinkSetting:
				transaction = manager.beginTransaction();
				transaction.replace(R.id.fragment_container, f_nodeSetting);
				transaction.commit();
				break;
			case R.id.sinkCheck: {
				transaction = manager.beginTransaction();
				transaction.replace(R.id.fragment_container, f_listnode);
				transaction.commit();
				break;
			}

			case R.id.linkSetting: {
				// remember change
				// transaction = manager.beginTransaction();
				// transaction.replace(R.id.fragment_container, f_linkSetting);
				// transaction.commit();
				SelectDialog selectDialog = new SelectDialog(MainActivity.this,
						R.style.dialog);// 创建Dialog并设置样式主题
				Window win = selectDialog.getWindow();
				LayoutParams params = new LayoutParams();
				params.x = -250;// 设置x坐标
				params.y = -70;// 设置y坐标
				win.setAttributes(params);
				// win.setBackgroundDrawable(getResources().getDrawable(R.drawable.file_unknown)
				// );
				selectDialog.setCanceledOnTouchOutside(true);// 设置点击Dialog外部任意区域关闭Dialog
				selectDialog.show();
				
//				View view =  View.inflate(MainActivity.this,R.layout.link_type, null);
//				eternetSetting = (Button) view.findViewById(R.id.eternetSetting);
//				wifiSetting = (Button) view.findViewById(R.id.wifiSetting);
//				System.out.println("ddddd"+eternetSetting.getText());
//				eternetSetting.setOnClickListener(new MyOnClickListener());
				break;

			}
			case R.id.statusValueCheck: {
				transaction = manager.beginTransaction();
				transaction
						.replace(R.id.fragment_container, f_statusValueCheck);
				transaction.commit();
				break;
			}
			case R.id.dataCenter: {
				transaction = manager.beginTransaction();
				transaction.replace(R.id.fragment_container, f_dataCenter);
				transaction.commit();
				break;
			}
			case R.id.aboutUs:
				transaction = manager.beginTransaction();
				transaction.replace(R.id.fragment_container, f_aboutUs);
				transaction.commit();
				break;
			case R.id.quit: {
				finish();
				break;
			}
			case R.id.topoStructure:
				transaction = manager.beginTransaction();
				transaction.replace(R.id.fragment_container, f_topoStructure);
				transaction.commit();
				break;
			default: {
				transaction.replace(R.id.fragment_container, f_listnode);
				transaction.commit();
				break;
			}
			}
		}
	}

	private class MyOnClickListener implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.eternetSetting:
				System.out.println("dakaile");
//				startActivity(new Intent(
//						android.provider.Settings.ACTION_SETTINGS));
				break;
			case R.id.wifiSetting:
				startActivity(new Intent(
						android.provider.Settings.ACTION_WIFI_SETTINGS));
				break;
			}
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main, menu);
		// menu.add(0,1,1,"quit");//类型于asshow = never
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.serialPort_check:
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setTitle("串口连接状态");
			if (serialPortConnect) {
				dialog.setMessage("已连接");
			} else {
				dialog.setMessage("未连接");
			}
			dialog.show();
			break;
		case R.id.quit:
			finish();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem item = menu.findItem(R.id.serialPort_check);
		String s = null;
		if (MainActivity.serialPortConnect) {
			s = "已连接";
		} else
			s = "未连接";
		item.setTitle(item.getTitle() + ":" + s);
		return true;
	}

	/**
	 * mainactivity 初始化
	 */
	public void init() {

		LocalConfigService localConfigService = new LocalConfigService(
				getBaseContext());
		localConfigService.setConfig("webserver", "192.168.10.145");
		// 初始化xml数据包格式并放入packagepattern中
		System.out.println("1");
		xmlTelosbPackagePatternUtil = new XmlTelosbPackagePatternUtil(
				getFilesDir().toString());
		System.out.println("2");
		// 客户端，服务器，串口，等资源的初始化
		// httpClientUtil = new HttpClientUtil(getBaseContext());
		httpserverState = new MySource();
		serialState = new MySource();
		// ms = new MySource();
		// havePackage = new MySource();

		HttpserverState hl = new HttpserverState();
		SerialListener ml = new SerialListener();
		// PackageListener pl = new PackageListener();
		httpserverState.addListener(hl);
		serialState.addListener(ml);
		// havePackage.addListener(pl);

		// havePackage.setValue(false);

		// 创建数据库表
		mDbhelper = new MySQLiteDbHelper(MainActivity.this);
		/*
		 * httpserverState.setValue(true); serialState.setValue(true);
		 * httpserverState.setValue(false);
		 */
		mp = new MediaPlayer();
		// 准备节点信息
		listNodePrepare.prepare();
		/*
		 * //节点逻辑结构树形成 listNodePrepare.formNodeStructureTree();
		 * System.out.println("节点逻辑结构树");
		 * System.out.println(listNodePrepare.getNodeTree
		 * ().iteratorTree(listNodePrepare.getNodeTree().getTreeNode()));
		 */
		// 监听预警音乐是否播放
		Runnable runnable = new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				// 要做的事情
				if (mp.isPlaying()) {
					alertNoticeDialog();
				}
			}
		};
		handler.postDelayed(runnable, 4000);
		// testBackup();
	}

	Handler handler = new Handler();

	private void alertNoticeDialog() {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(this)
				.setTitle("预警:" + ListNodePrepare.currentId + "节点电量低于阈值")
				.setMessage("是否关闭预警")
				.setPositiveButton("是", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						mp.stop();
						mp.release();
					}
				}).setNegativeButton("否", null).show();
	}

	public void testBackup() {
		List<TelosbPackage> list = new ArrayList<TelosbPackage>();
		for (int i = 0; i < 10; i++) {
			TelosbPackage telosbPackage = new TelosbPackage();
			telosbPackage.setCtype("C1");
			telosbPackage.setId(i);
			telosbPackage.setMessage("XXXXXXXXXXXXXXXX" + i);
			telosbPackage.setReceivetime(new Date());
			telosbPackage.setNodeID("1");
			list.add(telosbPackage);
		}
		OfflineBackupUtil offlineBackupUtil = new OfflineBackupUtil();
		offlineBackupUtil.CreatBackUpFile(list, getFilesDir().toString());
	}

	public class SerialListener implements Listenable {
		@Override
		public void eventChanged(MyEvent e) {
			// TODO Auto-generated method stub
			System.out.println("串口‘s e:" + e.getValue());
			if (serialState.value) {
				System.out.println("串口打开：XXXXXXXXXXXXXXXXXXXXXXXXXXX");
			} else {
				System.out.println("串口关闭中。。。。。XXXXXXXXXXXXXXXXXXXXS");
			}
		}
	}

	/*
	 * public class PackageListener implements Listenable {
	 * 
	 * @Override public void eventChanged(MyEvent e) { // TODO Auto-generated
	 * method stub System.out.println("包's e:" + e.getValue()); if (ms.value) {
	 * System.out.println("接收到数据，数据正在出处理。。。。"); //ProcessData(); } else {
	 * System.out.println("数据接收完毕，等待中。。。。"); } } }
	 */

	public class HttpserverState implements Listenable {
		@Override
		public void eventChanged(MyEvent e) {
			// TODO Auto-generated method stub
			System.out.println("server's e:" + e.getValue());
			if (e.getValue()) {
				System.out.println("服务器已连接：");
			} else {
				System.out.println("服务器已断开：");
			}
		}
	}

}
