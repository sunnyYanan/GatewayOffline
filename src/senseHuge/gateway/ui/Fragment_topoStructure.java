package senseHuge.gateway.ui;

import senseHuge.gateway.service.ListNodePrepare;
import com.example.testgateway.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_topoStructure extends Fragment {
	TopoStructureView nodeView;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_topo_structure,
				container, false);
		nodeView = (TopoStructureView) view.findViewById(R.id.topoStructureView);
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
				if (ListNodePrepare.changed)
					nodeView.invalidate();
			}

		}
	};

	public class MyThread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				try {
					Thread.sleep(1000);// �߳���ͣ2�룬��λ����
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
}
