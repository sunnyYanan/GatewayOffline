package senseHuge.gateway.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

//import com.broadtext.lspkpi.MainLSPKPIActivity;

/**
 * 折线图
 */
@SuppressLint({ "DrawAllocation", "FloatMath" })
public class StatusValueCheckView extends View {

	private int XOrigin = 40;// X轴起点
	private int YOrigin = 320;// y轴起点
	private int XLength = 550;// X轴长度
	private int YLength = 300;// Y轴长度
	private String[] XLabel; // X的刻度显示
	private String[] YLabel; // Y的刻度显示
	private float[] Data; // 数据
	private String Title; // 显示的标题
	private String xTitle;
	private String yTitle;
	private float screenW, screenH;
	float xScale;
	float yScale;

	float s = XOrigin + XLength / 2 + 30;// 默认中间线位置
	boolean isTouched = false;// 中间线点击位置变化控制

	private Paint backLinePaint = new Paint();// 背景线。
	private Paint textPaint = new Paint();// 文字
	private Paint titlePaint = new Paint();// 标题。
	private Paint circleRGBPaint = new Paint();// 环形画笔。
	// private Paint circelPaint = new Paint();// 拐点圆圈。
	// private Paint innerCircelPaint = new Paint();// 内圆。
	private Paint linePaint = new Paint();// 第一条线
	private Paint centerLinePaint = new Paint();// 中间竖线。

	public void setTitle(String title, String xTitle, String yTitle) {
		this.setTitle(title);
		this.setxTitle(xTitle);
		this.setyTitle(yTitle);
	}

	public void setData(String[] xLabel, String[] yLabel, float[] data) {
		this.setXLabel(xLabel);
		this.setYLabel(yLabel);
		this.setData(data);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		initPaint();
		// 画y轴
		canvas.drawLine(XOrigin, YOrigin - YLength, XOrigin, YOrigin,
				backLinePaint); // 轴线
		yScale = getYScale();
		for (int i = 0; i < this.YLabel.length; i++) {
			canvas.drawLine(XOrigin, YOrigin - i * yScale, XOrigin + 5, YOrigin
					- i * yScale, backLinePaint); // 刻度
			try {
				canvas.drawText(YLabel[i], XOrigin - 20, YOrigin - i * yScale
						+ 5, textPaint); // 文字
			} catch (Exception e) {
			}
		}
		canvas.drawLine(XOrigin, YOrigin - YLength, XOrigin - 3, YOrigin
				- YLength + 6, backLinePaint); // 箭头
		canvas.drawLine(XOrigin, YOrigin - YLength, XOrigin + 3, YOrigin
				- YLength + 6, backLinePaint);
		canvas.drawText(yTitle, XOrigin, YOrigin - YLength - 5, titlePaint);
		// 设置X轴
		xScale = getXScale();
		canvas.drawLine(XOrigin, YOrigin, XOrigin + XLength, YOrigin,
				backLinePaint); // 轴线
		for (int i = 0; i < this.XLabel.length; i++) {
			canvas.drawLine(XOrigin + i * xScale, YOrigin,
					XOrigin + i * xScale, YOrigin - 5, backLinePaint); // 刻度
			try {
				canvas.drawText(XLabel[i], XOrigin + i * xScale - 10,
						YOrigin + 20, textPaint); // 文字
				// 数据值
				if (i > 0 && YCoord(Data[i - 1]) != -999
						&& YCoord(Data[i]) != -999) // 保证有效数据
					canvas.drawLine(XOrigin + (i - 1) * xScale,
							YCoord(Data[i - 1]), XOrigin + i * xScale,
							YCoord(Data[i]), linePaint);
				canvas.drawText(String.valueOf(Data[i]), XOrigin + i * xScale,
						YCoord(Data[i]) - 10, textPaint);
			} catch (Exception e) {
			}
		}
		canvas.drawText(xTitle, XOrigin + XLength + 20, YOrigin, titlePaint);
		// 画折点
		for (int i = 0; i < this.XLabel.length; i++) {
			canvas.drawCircle(XOrigin + i * xScale, YCoord(Data[i]), 2,
					circleRGBPaint);
		}
		canvas.drawLine(XOrigin + XLength, YOrigin, XOrigin + XLength - 6,
				YOrigin - 3, backLinePaint); // 箭头
		canvas.drawLine(XOrigin + XLength, YOrigin, XOrigin + XLength - 6,
				YOrigin + 3, backLinePaint);
		// 中间线
		canvas.drawLine(s, YOrigin + 5, s, YOrigin - YLength, centerLinePaint);
		canvas.drawLine(s - 10, YOrigin + 5, s + 10, YOrigin + 5,
				centerLinePaint);
		canvas.drawLine(s - 10, YOrigin - YLength, s + 10, YOrigin - YLength,
				centerLinePaint);

		// 判断中间线是否显示当前读数
		for (int i = 0; i < this.XLabel.length; i++) {
			float distance = Math.abs(XOrigin + i * xScale - s);
			if (distance < 5) {
				canvas.drawText(String.valueOf(Data[i]), s + 10, YOrigin
						- YLength + 20, textPaint);
				canvas.drawText(XLabel[i], s + 10, YOrigin - YLength + 20 + 20,
						textPaint); // 文字
			}
		}

		canvas.drawText(Title, XOrigin + XLength / 2, YOrigin - YLength,
				titlePaint);

	}

	private float YCoord(float data2) // 计算绘制时的Y坐标，无数据时返回-999
	{
		float y;
		try {
			y = data2;
		} catch (Exception e) {
			return -999; // 出错则返回-999
		}
		try {
			return YOrigin - y * yScale / Integer.parseInt(YLabel[1]);
		} catch (Exception e) {
		}
		return y;
	}

	private float getXScale() {
		// TODO Auto-generated method stub
		return this.getXLength() / this.getXLabel().length;
	}

	private float getYScale() {
		// TODO Auto-generated method stub
		return this.getYLength() / this.getYLabel().length;
	}

	/**
	 * 手势监听
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			if (event.getX() >= (s - 30) && event.getX() <= (s + 30)) {
				isTouched = true;
			}
			break;
		case MotionEvent.ACTION_MOVE:
			Log.i("action-move", " action-move");
			// if(!MainLSPKPIActivity.doubleClick)
			if (isTouched) {
				s = event.getX();// 将此时手势x坐标记录下来, 根据此x重绘中间线、
				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
			Log.i("action-up", " action-up");
			// if(!MainLSPKPIActivity.doubleClick) {
			if (isTouched) {
				s = event.getX();// 记录当下位置坐标
				isTouched = false;
				invalidate();
			}
			// }
			break;
		}
		return true;
	}

	private void initPaint() {
		// TODO Auto-generated method stub
		backLinePaint.setStyle(Style.STROKE);
		backLinePaint.setStrokeWidth((float) 0.7);
		backLinePaint.setColor(Color.BLACK);
		backLinePaint.setAntiAlias(true);// 锯齿不显示
		textPaint.setStyle(Style.FILL);// 设置非填充
		textPaint.setStrokeWidth(1);// 笔宽5像素
		textPaint.setColor(Color.BLACK);// 设置为蓝笔
		textPaint.setAntiAlias(true);// 锯齿不显示
		textPaint.setTextAlign(Align.CENTER);
		textPaint.setTextSize(15);
		titlePaint.setStyle(Style.FILL);
		titlePaint.setStrokeWidth(1);
		titlePaint.setColor(Color.parseColor("#4692B1"));
		titlePaint.setAntiAlias(true);
		titlePaint.setTextAlign(Align.CENTER);
		titlePaint.setTextSize(18);
		linePaint.setStyle(Style.FILL);
		linePaint.setStrokeWidth(3);
		linePaint.setColor(Color.rgb(255, 210, 0));// (1)黄色
		linePaint.setAntiAlias(true);

		centerLinePaint.setColor(Color.parseColor("#000000"));// 中间动态线。7DA62D
		centerLinePaint.setStrokeWidth(3);

		circleRGBPaint.setStrokeWidth(6);
		circleRGBPaint.setAntiAlias(true);// 消除锯齿。
		circleRGBPaint.setStyle(Style.STROKE);
		circleRGBPaint.setColor(Color.parseColor("#4692B1"));//
	}

	public StatusValueCheckView(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
		screenW = this.getWidth();
		screenH = this.getHeight();
	}

	public StatusValueCheckView(Context context, AttributeSet attrs) {
		super(context, attrs);
		screenW = this.getWidth();
		screenH = this.getHeight();
	}

	public StatusValueCheckView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		screenW = this.getWidth();
		screenH = this.getHeight();
	}

	public int getXOrigin() {
		return XOrigin;
	}

	public void setXOrigin(int xOrigin) {
		XOrigin = xOrigin;
	}

	public int getYOrigin() {
		return YOrigin;
	}

	public void setYOrigin(int yOrigin) {
		YOrigin = yOrigin;
	}

	public int getXLength() {
		return XLength;
	}

	public void setXLength(int xLength) {
		XLength = xLength;
	}

	public int getYLength() {
		return YLength;
	}

	public void setYLength(int yLength) {
		YLength = yLength;
	}

	public String[] getXLabel() {
		return XLabel;
	}

	public void setXLabel(String[] xLabel) {
		XLabel = xLabel;
	}

	public String[] getYLabel() {
		return YLabel;
	}

	public void setYLabel(String[] yLabel) {
		YLabel = yLabel;
	}

	public float[] getData() {
		return Data;
	}

	public void setData(float[] data) {
		Data = data;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getxTitle() {
		return xTitle;
	}

	public void setxTitle(String xTitle) {
		this.xTitle = xTitle;
	}

	public String getyTitle() {
		return yTitle;
	}

	public void setyTitle(String yTitle) {
		this.yTitle = yTitle;
	}

}
