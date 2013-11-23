package senseHuge.gateway.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
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

public class topoStructureView extends View {
	private Paint linePaint = new Paint();// 节点连线
	private Paint textPaint = new Paint();// 文字
	private float screenW, screenH;
	private NodeTree nodeTree = ListNodePrepare.nodeTree;
	private List<String> nodeName;
	private List<float[]> nodePosition;

	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		initPaint();

		screenW = this.getWidth();
		screenH = this.getHeight();
		nodePosition = new ArrayList<float[]>();
		nodeName = new ArrayList<String>();
		

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
	}

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

		for (int i = 0; i < num; i++) {
			String nodeName = list.get(i).getNode().getName();

			float left = (i + 1) * wInterval - leftOffset;
			float top = layer * hInterval;
			canvas.drawBitmap(bmp, left, top, null);
			canvas.drawText(nodeName, left + leftOffset, top + bmp.getHeight()
					+ 9, textPaint);
			// 找到父节点的坐标
			if (!nodeName.equals("0000")) {
				String parentName = nodeTree.findNodeParentByName(nodeName)
						.getNode().getName();
				for (int j = 0; j < tempPosition.size(); j++) {
					if (tempPosition.get(j).containsKey(parentName)) {
						float[] parent = new float[2];
						parent = tempPosition.get(j).get(parentName);
						canvas.drawLine(left + leftOffset, top, parent[0],
								parent[1], linePaint);
						break;
					}
				}

			}

			temp[0] = left + leftOffset;// 横坐标
			temp[1] = top + bmp.getHeight() + 9;// 纵坐标

			Map<String, float[]> position = new HashMap<String, float[]>();
			position.put(nodeName, temp);
			nodePosition.add(position);
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
	}

	public topoStructureView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public topoStructureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public topoStructureView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		// TODO Auto-generated constructor stub
	}

}
