package senseHuge.gateway.ui;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import senseHuge.gateway.model.NodeTree;
import senseHuge.gateway.model.TreeNode;
import senseHuge.gateway.service.ListNodePrepare;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;

import com.example.testgateway.R;

public class TopoStructureView extends View {
	private Paint linePaint = new Paint();// 节点连线
	private Paint textPaint = new Paint();// 文字
	private Paint circlePaint = new Paint();// 包
	private float screenW, screenH;
	private NodeTree nodeTree = ListNodePrepare.nodeTree;
	private List<String> nodeNamePosition;
	private List<float[]> nodePosition;
	float px = 0, py = 0;
	String path = "";

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		initPaint();

		screenW = this.getWidth();
		screenH = this.getHeight();
		nodePosition = new ArrayList<float[]>();
		nodeNamePosition = new ArrayList<String>();

		if (nodeTree == null) {
			System.out.println("empty tree");
			return;
		}
		TreeNode root = nodeTree.getTreeNode();
		// Map<Integer, TreeNode> node = new HashMap<Integer, TreeNode>();
		// node.put(0, root);
		List<TreeNode> list = new ArrayList<TreeNode>();
		List<TreeNode> temp;
		list.add(root);
		int layer = 0;

		while (!list.isEmpty()) {
			draw(list, layer, canvas);
			temp = new ArrayList<TreeNode>();
			for (int i = 0; i < list.size(); i++) {
				temp.add(list.get(i));
			}
			list.clear();
			for (int i = 0; i < temp.size(); i++) {
				for (TreeNode child : temp.get(i).getChildTree()) {
					list.add(child);
				}
			}
			layer++;
		}

		// printNodePosition();
		// 画当前包的运动
		canvas.drawText(path, 10, 10, textPaint);
		//(px, py, 5, circlePaint);
//		if (NodeTree.pathComplete) {
//			update();
//		}
	}

//	private void update() {
//		// TODO Auto-generated method stub
//		List<TreeNode> temp = new ArrayList<TreeNode>();// 包的路径
//		for (int i = NodeTree.curPath.size() - 1; i >= 0; i--) {
//			temp.add(NodeTree.curPath.get(i));
//			System.out.println("当前路径：" + i + " "
//					+ temp.get(i).getNode().getName());
//		}
//		for (int i = 0; i < temp.size(); i++) {
//			float[] pos = temp.get(i).getNode().getPosition();
//			px = pos[0];
//			py = pos[1];
//			invalidate();
//		}
//	}

	// 一层一层的画节点
	private void draw(List<TreeNode> list, int layer, Canvas canvas) {
		// TODO Auto-generated method stub
		Resources res = getResources();
		Bitmap bmp = BitmapFactory.decodeResource(res, R.drawable.ic_launcher);

		int leftOffset = bmp.getWidth() / 2;
		// int topOffset = bmp.getHeight() / 2;
		// float wCenter = screenW / 2;
		// float hCenter = screenH / 2;

		int num = list.size();
		float wInterval = screenW / (num + 1);
		float hInterval = 70;
		int startPos = 0;// 当当前行节点ID有重复时避免路线有误的措施

		List<String> nodeNamePositionTemp = new ArrayList<String>();
		List<float[]> nodePositionTemp = new ArrayList<float[]>();
		for (int i = 0; i < nodePosition.size(); i++) {
			nodeNamePositionTemp.add(nodeNamePosition.get(i));
			nodePositionTemp.add(nodePosition.get(i));
		}
		nodeNamePosition.clear();
		nodePosition.clear();
		// Map<String, float[]> position;

		for (int i = 0; i < num; i++) {
			// position = new HashMap<String, float[]>();
			String nodeName = list.get(i).getNode().getName();

			float left = (i + 1) * wInterval - leftOffset;
			float top = layer * hInterval;
			canvas.drawBitmap(bmp, left, top, null);
			canvas.drawText(nodeName, left + leftOffset, top + bmp.getHeight()
					+ 9, textPaint);

			float[] temp = new float[2];
			temp[0] = left + leftOffset;// 横坐标
			temp[1] = top + bmp.getHeight() + 9;// 纵坐标
			nodeNamePosition.add(nodeName);
			nodePosition.add(temp);
			// System.out.println(nodeName + "的坐标是：" + temp[0] + " " + temp[1]);
			TreeNode aa = nodeTree.findNodeByName(nodeName);
			if (nodeName.endsWith("0000"))
				nodeTree.getTreeNode().getNode().setPosition(temp);
			if (aa != null)
				aa.getNode().setPosition(temp);

			// 找到父节点的坐标
			if (!nodeName.equals("0000")) {
				String parentName = nodeTree.findNodeParentByName(nodeName)
						.getNode().getName();
				for (int j = startPos; j < nodePositionTemp.size(); j++) {
					if (nodeNamePositionTemp.get(j).equals(parentName)) {
						float[] parent = new float[2];
						// if (j == nodePositionTemp.size() - 2)
						// startPos = nodePositionTemp.size() - 1;
						// else
						startPos = j;
						parent = nodePositionTemp.get(j);
						canvas.drawLine(left + leftOffset, top, parent[0],
								parent[1], linePaint);
						break;
					}
				}

			}
		}

	}

	private void initPaint() {
		// TODO Auto-generated method stub
		linePaint.setStyle(Style.STROKE);
		linePaint.setStrokeWidth((float) 0.7);
		linePaint.setColor(Color.BLACK);
		linePaint.setAntiAlias(true);// 锯齿不显示

		textPaint.setStyle(Style.FILL);// 设置非填充
		textPaint.setStrokeWidth(1);// 笔宽5像素
		textPaint.setColor(Color.BLACK);// 设置为蓝笔
		textPaint.setAntiAlias(true);// 锯齿不显示
		textPaint.setTextAlign(Align.CENTER);
		textPaint.setTextSize(15);

		circlePaint.setStyle(Style.FILL);
		circlePaint.setStrokeWidth(2);
		circlePaint.setColor(Color.YELLOW);
		circlePaint.setAntiAlias(true);
	}

	public TopoStructureView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public TopoStructureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public TopoStructureView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

}
