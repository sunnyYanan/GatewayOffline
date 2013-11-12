package senseHuge.gateway.ui;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.example.testgateway.R;

import senseHuge.gateway.model.Serial;
import senseHuge.gateway.service.SerialportDataProcess;
import senseHuge.gateway.util.SerialUtil;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android_serialport_api.SerialPort;

/**
 * serial configure
 * 
 * @author jiangnanEdu
 * 
 */
public class Fragment_serialconfig extends Fragment {
	protected static final String tag = "Fragment_serialconfig";
	SerialportDataProcess dataProcess;
	Serial serial = new Serial();
	Spinner spinner = null;
	Spinner devSpinner = null;
	InputStream mInputStream;
	OutputStream mOutputStream;
	SerialPort serialPort = null;
	public static SerialUtil serialUtil = new SerialUtil();
	byte[] mBuffer;
	public static SerialPort mSerialPort = null;
	Button connectButton = null;
	Button closeButton = null;
	ReadThread readThread = null;
	MainActivity ma;
	View view1;
	private ContentResolver contentResolver;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		ma = (MainActivity) getActivity();

		view1 = LayoutInflater.from(getActivity()).inflate(
				R.layout.serialconfig, null);

		connectButton = (Button) view1.findViewById(R.id.connect);
		closeButton = (Button) view1.findViewById(R.id.close);
		contentResolver = this.getActivity().getContentResolver();

		changeButtonStatus();

		connectButton.setOnClickListener(new ButtonClickListener());
		closeButton.setOnClickListener(new ButtonClickListener());

		devSpinner = (Spinner) view1.findViewById(R.id.spinner_device);
		spinner = (Spinner) view1.findViewById(R.id.spinner_baud);

		ArrayAdapter<CharSequence> bauldadapter = ArrayAdapter
				.createFromResource(getActivity(), R.array.baudrates_name,
						android.R.layout.simple_dropdown_item_1line);
		ArrayAdapter<CharSequence> devadapter = ArrayAdapter
				.createFromResource(getActivity(), R.array.dev_path,
						android.R.layout.simple_dropdown_item_1line);

		spinner.setAdapter(bauldadapter);
		devSpinner.setAdapter(devadapter);
		// USB0
		devSpinner.setSelection(4);
		spinner.setSelection(16);

		serial.setFilePath(getActivity().getResources().getStringArray(
				R.array.dev_path)[3]);
		serial.setBuandrate(Integer.parseInt((getActivity().getResources()
				.getStringArray(R.array.baudrates_name)[12])));
		serial.setState(false);

		devSpinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long id) {
				Log.i(tag, "" + position);

				serial.setFilePath(getActivity().getResources().getStringArray(
						R.array.dev_path)[position]);
				Log.i(tag, serial.getFilePath());

			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {

			}
		});
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int position, long id) {
				Log.i(tag, "" + position);
				serial.setBuandrate(Integer
						.parseInt((getActivity().getResources().getStringArray(
								R.array.baudrates_name)[position])));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub

			}
		});
		return view1;
	}

	private void changeButtonStatus() {
		// TODO Auto-generated method stub
		if (MainActivity.serialPortConnect) {
			connectButton.setEnabled(false);
			closeButton.setEnabled(true);
		} else {
			connectButton.setEnabled(true);
			closeButton.setEnabled(false);
		}
	}

	class ButtonClickListener implements OnClickListener {
		FragmentTransaction transaction;

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.connect: {
				if (mSerialPort == null || !serial.getState()) {
					try {
						Log.i(tag, serial.getFilePath() + serial.getBuandrate());
						mSerialPort = new SerialPort(new File(
								serial.getFilePath()), serial.getBuandrate(), 0);
						mOutputStream = mSerialPort.getOutputStream();
						mInputStream = mSerialPort.getInputStream();
					} catch (SecurityException e1) {
						// TODO Auto-generated catch block
						Log.i(tag, "打开串口失败：");

						linkStatus("打开串口失败");

						e1.printStackTrace();
						break;
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						Log.i(tag, "打开串口失败：");
						linkStatus("打开串口失败");
						break;
					}
					Log.i(tag, "打开串口sucess：");
					linkStatus("打开串口成功");

					MainActivity.serialPortConnect = true;
					serial.setState(true);

					// 串口读取数据
					readThread = new ReadThread();
					readThread.start();

					// 数据处理
					dataProcess = new SerialportDataProcess(contentResolver);
					dataProcess.start();

					ma.serialState.setValue(true);// 应该改进为用这个！！

					changeButtonStatus();
				}

				break;
			}
			case R.id.close: {
				System.out.println((mSerialPort != null) + "   "
						+ serial.getState());
				// if (mSerialPort != null && serial.getState()) {
				if (mSerialPort != null) {
					try {
						mSerialPort.close();
						mOutputStream.close();
						mInputStream.close();

						readThread.interrupt();

						serial.setState(false);

						// Log.i(tag, ma.havadata.getState().toString());
						MainActivity.serialPortConnect = false;

						dataProcess.interrupt();
						ma.serialState.setValue(false);// 应该改进为用这个！！

						if (dataProcess.isInterrupted()) {
							Log.i(tag, "被中断：");
						}
						Log.i(tag, dataProcess.getState().toString());
						changeButtonStatus();
						// readThread.stop();
						linkStatus("关闭串口成功");

					} catch (SecurityException e1) {
						// TODO Auto-generated catch block
						linkStatus("关闭串口失败");
						e1.printStackTrace();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						linkStatus("关闭串口失败");
					}
				}
				break;
			}
			default: {

				break;
			}
			}
		}

		private void linkStatus(String string) {
			// TODO Auto-generated method stub
			AlertDialog.Builder builder = new AlertDialog.Builder(
					view1.getContext());
			// 2. Chain together various setter methods to set the dialog
			// characteristics
			builder.setMessage(string).setTitle("串口消息");
			// 3. Get the AlertDialog from create()
			builder.show();

		}
	}

	/**
	 * 串口读数据线程
	 * 
	 * @author jiangnanEdu
	 * 
	 */
	private class ReadThread extends Thread {
		byte[] buffer = new byte[64];

		@Override
		public void run() {
			super.run();
			/*
			 * System.out.println("^^^^^^^^^" +
			 * ma.serialUtil.stringBuffer.toString());
			 */
			while (MainActivity.serialPortConnect) {
				int size;
				try {

					if (mInputStream == null)
						return;
					size = mInputStream.read(buffer);
					/*
					 * if (size > 0) { //onDataReceived(buffer, size);
					 * Log.i(tag, "start+"+size); }
					 */
					for (int j = 0; j < size; j++) {
						serialUtil.stringBuffer.append(byteToHex(buffer[j]));
					}

				} catch (IOException e) {
					e.printStackTrace();
					return;
				}
			}
		}

		private String byteToHex(byte byteData) {
			// TODO Auto-generated method stub
			String hex = "";
			hex = Integer.toHexString(byteData & 0xFF);
			if (hex.length() == 1)
				hex = "0" + hex;
			return hex.toUpperCase();
		}
	}
}
