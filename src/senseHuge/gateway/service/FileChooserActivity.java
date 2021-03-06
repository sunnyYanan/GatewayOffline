package senseHuge.gateway.service;

import java.io.File;
import java.util.ArrayList;

import senseHuge.gateway.model.FileInfo;
import senseHuge.gateway.ui.Fragment_nodeSetting;
import senseHuge.gateway.util.FileChooserAdapter;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testgateway.R;

public class FileChooserActivity extends Activity {

	private GridView mGridView;
	private View mBackView;
	private View mBtExit;
	private TextView mTvPath;

	private String mRootPath;
	private String mLastFilePath;
	private ArrayList<FileInfo> mFileLists;
	private FileChooserAdapter mAdatper;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.filechooser_show);

		// mSdcardRootPath =
		// Environment.getExternalStorageDirectory().getAbsolutePath();//
		// �õ�sdcardĿ¼
		mRootPath = "/";// ��Ŀ¼
		mBackView = findViewById(R.id.imgBackFolder);
		mBackView.setOnClickListener(mClickListener);
		mBtExit = findViewById(R.id.btExit);
		mBtExit.setOnClickListener(mClickListener);

		mTvPath = (TextView) findViewById(R.id.tvPath);

		mGridView = (GridView) findViewById(R.id.gvFileChooser);
		mGridView.setEmptyView(findViewById(R.id.tvEmptyHint));
		mGridView.setOnItemClickListener(mItemClickListener);
		setGridViewAdapter(mRootPath);
	}

	private void setGridViewAdapter(String filePath) {
		updateFileItems(filePath);
		mAdatper = new FileChooserAdapter(this, mFileLists);
		mGridView.setAdapter(mAdatper);
	}

	private void updateFileItems(String filePath) {
		mLastFilePath = filePath;
		mTvPath.setText(mLastFilePath);

		if (mFileLists == null)
			mFileLists = new ArrayList<FileInfo>();
		if (!mFileLists.isEmpty())
			mFileLists.clear();

		File[] files = folderScan(filePath);
		if (files == null)
			return;

		for (int i = 0; i < files.length; i++) {
			if (files[i].isHidden())
				continue;
			String fileAbsolutePath = files[i].getAbsolutePath();
			String fileName = files[i].getName();
			boolean isDirectory = false;
			if (files[i].isDirectory()) {
				isDirectory = true;
			}
			FileInfo fileInfo = new FileInfo(fileAbsolutePath, fileName,
					isDirectory);
			mFileLists.add(fileInfo);
		}
		// When first enter , the object of mAdatper don't initialized
		if (mAdatper != null)
			mAdatper.notifyDataSetChanged();
	}

	private File[] folderScan(String path) {
		File file = new File(path);
		File[] files = file.listFiles();
		return files;
	}

	private View.OnClickListener mClickListener = new OnClickListener() {
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.imgBackFolder:
				backProcess();
				break;
			case R.id.btExit:
				setResult(RESULT_CANCELED);
				finish();
				break;
			default:
				break;
			}
		}
	};

	private AdapterView.OnItemClickListener mItemClickListener = new OnItemClickListener() {
		public void onItemClick(AdapterView<?> adapterView, View view,
				int position, long id) {
			FileInfo fileInfo = (FileInfo) (((FileChooserAdapter) adapterView
					.getAdapter()).getItem(position));
			if (fileInfo.isDirectory())
				updateFileItems(fileInfo.getFilePath());
			else if (fileInfo.isMp3File()) {
				Intent intent = new Intent();
				intent.putExtra(Fragment_nodeSetting.EXTRA_FILE_CHOOSER,
						fileInfo.getFilePath());
				setResult(RESULT_OK, intent);
				finish();
			} else {
				toast("�ļ����ʹ���");
			}
		}
	};

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN
				&& event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			backProcess();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	public void backProcess() {
		if (!mLastFilePath.equals(mRootPath)) {
			File thisFile = new File(mLastFilePath);
			String parentFilePath = thisFile.getParent();
			updateFileItems(parentFilePath);
		} else {
			setResult(RESULT_CANCELED);
			finish();
		}
	}

	private void toast(CharSequence hint) {
		Toast.makeText(this, hint, Toast.LENGTH_SHORT).show();
	}
}