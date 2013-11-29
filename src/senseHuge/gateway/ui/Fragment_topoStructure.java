package senseHuge.gateway.ui;

import java.util.ArrayList;
import java.util.List;

import senseHuge.gateway.model.NodeTree;
import senseHuge.gateway.model.TreeNode;
import senseHuge.gateway.service.ListNodePrepare;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.testgateway.R;

public class Fragment_topoStructure extends Fragment {
	TopoStructureView nodeView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_topo_structure,
				container, false);
		nodeView = (TopoStructureView) view
				.findViewById(R.id.topoStructureView);
		Thread draw = new Thread(new MyThread());
		if (MainActivity.serialPortConnect)
			draw.start();
		else
			draw.interrupt();

		return view;
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// 要做的事情
			super.handleMessage(msg);
			switch (msg.arg1) {
			case 1:
				if (ListNodePrepare.changed && NodeTree.pathComplete) {
					Thread draw = new Thread(new DrawThread());
					draw.start();
				}
			case 2:
				update();
			}
				

		}

	};
	public class DrawThread implements Runnable {

		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message message = new Message();
			message.arg1 = 2;
			handler.sendMessage(message);// 发送消息
		}
		
	}
	
	private void update() {
		// TODO Auto-generated method stub
		List<TreeNode> temp = new ArrayList<TreeNode>();
		for(int i=temp.size()-1; i>=0; i--) {
			temp.add(NodeTree.curPath.get(i));
			System.out.println("当前路径："+i+" "+temp.get(i).getNode().getName());
		}
		for (int i = 0; i < temp.size(); i++) {
			float[] pos = temp.get(i).getNode().getPosition();
			nodeView.px = pos[0];
			nodeView.py = pos[1];
			nodeView.invalidate();
		}
	}

	public class MyThread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				try {
					Thread.sleep(1000);// 线程暂停2秒，单位毫秒
					Message message = new Message();
					message.arg1 = 1;
					handler.sendMessage(message);// 发送消息
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
