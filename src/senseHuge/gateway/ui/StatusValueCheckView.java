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
 * ����ͼ
 */
@SuppressLint({ "DrawAllocation", "FloatMath" })
public class StatusValueCheckView extends View {

	private int XOrigin = 40;// X�����
	private int YOrigin = 320;// y�����
	private int XLength = 550;// X�᳤��
	private int YLength = 300;// Y�᳤��
	private String[] XLabel; // X�Ŀ̶���ʾ
	private String[] YLabel; // Y�Ŀ̶���ʾ
	private float[] Data; // ����
	private String Title; // ��ʾ�ı���
	private String xTitle;
	private String yTitle;
	private float screenW, screenH;
	float xScale;
	float yScale;

	float s = XOrigin + XLength / 2 + 30;// Ĭ���м���λ��
	boolean isTouched = false;// �м��ߵ��λ�ñ仯����

	private Paint backLinePaint = new Paint();// �����ߡ�
	private Paint textPaint = new Paint();// ����
	private Paint titlePaint = new Paint();// ���⡣
	private Paint circleRGBPaint = new Paint();// ���λ��ʡ�
	// private Paint circelPaint = new Paint();// �յ�ԲȦ��
	// private Paint innerCircelPaint = new Paint();// ��Բ��
	private Paint linePaint = new Paint();// ��һ����
	private Paint centerLinePaint = new Paint();// �м����ߡ�

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
		// ��y��
		canvas.drawLine(XOrigin, YOrigin - YLength, XOrigin, YOrigin,
				backLinePaint); // ����
		yScale = getYScale();
		for (int i = 0; i < this.YLabel.length; i++) {
			canvas.drawLine(XOrigin, YOrigin - i * yScale, XOrigin + 5, YOrigin
					- i * yScale, backLinePaint); // �̶�
			try {
				canvas.drawText(YLabel[i], XOrigin - 20, YOrigin - i * yScale
						+ 5, textPaint); // ����
			} catch (Exception e) {
			}
		}
		canvas.drawLine(XOrigin, YOrigin - YLength, XOrigin - 3, YOrigin
				- YLength + 6, backLinePaint); // ��ͷ
		canvas.drawLine(XOrigin, YOrigin - YLength, XOrigin + 3, YOrigin
				- YLength + 6, backLinePaint);
		canvas.drawText(yTitle, XOrigin, YOrigin - YLength - 5, titlePaint);
		// ����X��
		xScale = getXScale();
		canvas.drawLine(XOrigin, YOrigin, XOrigin + XLength, YOrigin,
				backLinePaint); // ����
		for (int i = 0; i < this.XLabel.length; i++) {
			canvas.drawLine(XOrigin + i * xScale, YOrigin,
					XOrigin + i * xScale, YOrigin - 5, backLinePaint); // �̶�
			try {
				canvas.drawText(XLabel[i], XOrigin + i * xScale - 10,
						YOrigin + 20, textPaint); // ����
				// ����ֵ
				if (i > 0 && YCoord(Data[i - 1]) != -999
						&& YCoord(Data[i]) != -999) // ��֤��Ч����
					canvas.drawLine(XOrigin + (i - 1) * xScale,
							YCoord(Data[i - 1]), XOrigin + i * xScale,
							YCoord(Data[i]), linePaint);
				canvas.drawText(String.valueOf(Data[i]), XOrigin + i * xScale,
						YCoord(Data[i]) - 10, textPaint);
			} catch (Exception e) {
			}
		}
		canvas.drawText(xTitle, XOrigin + XLength + 20, YOrigin, titlePaint);
		// ���۵�
		for (int i = 0; i < this.XLabel.length; i++) {
			canvas.drawCircle(XOrigin + i * xScale, YCoord(Data[i]), 2,
					circleRGBPaint);
		}
		canvas.drawLine(XOrigin + XLength, YOrigin, XOrigin + XLength - 6,
				YOrigin - 3, backLinePaint); // ��ͷ
		canvas.drawLine(XOrigin + XLength, YOrigin, XOrigin + XLength - 6,
				YOrigin + 3, backLinePaint);
		// �м���
		canvas.drawLine(s, YOrigin + 5, s, YOrigin - YLength, centerLinePaint);
		canvas.drawLine(s - 10, YOrigin + 5, s + 10, YOrigin + 5,
				centerLinePaint);
		canvas.drawLine(s - 10, YOrigin - YLength, s + 10, YOrigin - YLength,
				centerLinePaint);

		// �ж��м����Ƿ���ʾ��ǰ����
		for (int i = 0; i < this.XLabel.length; i++) {
			float distance = Math.abs(XOrigin + i * xScale - s);
			if (distance < 5) {
				canvas.drawText(String.valueOf(Data[i]), s + 10, YOrigin
						- YLength + 20, textPaint);
				canvas.drawText(XLabel[i], s + 10, YOrigin - YLength + 20 + 20,
						textPaint); // ����
			}
		}

		canvas.drawText(Title, XOrigin + XLength / 2, YOrigin - YLength,
				titlePaint);

	}

	private float YCoord(float data2) // �������ʱ��Y���꣬������ʱ����-999
	{
		float y;
		try {
			y = data2;
		} catch (Exception e) {
			return -999; // �����򷵻�-999
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
	 * ���Ƽ���
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
				s = event.getX();// ����ʱ����x�����¼����, ���ݴ�x�ػ��м��ߡ�
				invalidate();
			}
			break;
		case MotionEvent.ACTION_UP:
			Log.i("action-up", " action-up");
			// if(!MainLSPKPIActivity.doubleClick) {
			if (isTouched) {
				s = event.getX();// ��¼����λ������
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
		backLinePaint.setAntiAlias(true);// ��ݲ���ʾ
		textPaint.setStyle(Style.FILL);// ���÷����
		textPaint.setStrokeWidth(1);// �ʿ�5����
		textPaint.setColor(Color.BLACK);// ����Ϊ����
		textPaint.setAntiAlias(true);// ��ݲ���ʾ
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
		linePaint.setColor(Color.rgb(255, 210, 0));// (1)��ɫ
		linePaint.setAntiAlias(true);

		centerLinePaint.setColor(Color.parseColor("#000000"));// �м䶯̬�ߡ�7DA62D
		centerLinePaint.setStrokeWidth(3);

		circleRGBPaint.setStrokeWidth(6);
		circleRGBPaint.setAntiAlias(true);// ������ݡ�
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
