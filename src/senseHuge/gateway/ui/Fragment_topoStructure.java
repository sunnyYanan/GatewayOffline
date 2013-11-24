package senseHuge.gateway.ui;

import java.util.ArrayList;
import java.util.List;

import senseHuge.gateway.model.NodeTree;
import senseHuge.gateway.service.ListNodePrepare;
import senseHuge.gateway.ui.Fragment_listNode.MyThread;

import com.example.testgateway.R;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Fragment_topoStructure extends Fragment {
	topoStructureView nodeView;
	private List<String> packagePath;
	List<float[]> position;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_topo_structure,
				container, false);
		nodeView = (topoStructureView) view
				.findViewById(R.id.topoStructureView);
		packagePath = new ArrayList<String>();
		position = new ArrayList<float[]>();
		
		Thread thread = new Thread(new MyThread());
		if (MainActivity.serialPortConnect)
			thread.start();
		else
			thread.isInterrupted();

		return view;
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			// 要做的事情
			super.handleMessage(msg);
			switch (msg.arg1) {
			case 1:
				if (ListNodePrepare.changed) {
					//画包，此时给出包的位置
					// 当前包，curPath是包的传播路径的反向
					if (!NodeTree.curPath.isEmpty()) {
						packagePath.clear();
						int depth = NodeTree.curPath.size() - 1;
						for (int i = depth; i >= 0; i--) {
							packagePath.add(NodeTree.curPath.get(i));
						}
					}
					position.clear();
					//包路径上各个节点的位置
					int current = nodeView.getNodePositionAll().size()-1;
					for(int i =0; i<packagePath.size(); i++) {
						for(int j = current ;i>=0; j--) {
							if(packagePath.get(i).equals(nodeView.getNodeNamePositionAll().get(j))) {
								float[] temp = new float[2];
								temp = nodeView.getNodePositionAll().get(j);
								position.add(temp);
								current = j;
								break;
							}
						}
					}
					//sink节点位置
					float[] tempSink = new float[2];
					tempSink = nodeView.getNodePositionAll().get(0);
					position.add(tempSink);
					
					for(int i = 0; i<position.size()-1; i++) {
						float[] temp = position.get(i);
						float[] parent = position.get(i+1);
						float hInter = Math.abs(temp[1]-parent[1])/4;
						float wInter = Math.abs(temp[0]-parent[0])/4;
						for(int j=0; j<=4;j++) {
							if(parent[0]>temp[0]) {
								nodeView.cx = temp[0]+wInter *j;
							}else
								nodeView.cx = parent[0]+wInter*j;
							if(parent[1]>temp[1]) {
								nodeView.cy = temp[1]+hInter *j;
							}else
								nodeView.cy = parent[1]+hInter*j;
							
							nodeView.invalidate();
							
						}
						
					}
					
				}
			}

		}
	};

	public class MyThread implements Runnable {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				try {
					Thread.sleep(2000);// 线程暂停2秒，单位毫秒
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
