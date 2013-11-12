package senseHuge.gateway.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

//import com.broadtext.lspkpi.MainLSPKPIActivity;

/**
 * ����ͼ
 * 
 * @author 24K
 * @created 2013��8��14��15:11:52
 * @version 1.0
 */
@SuppressLint({ "DrawAllocation", "FloatMath" })
public class StatusValueCheckView extends View {

	public float s = 500;// Ĭ���м���λ��
	private boolean isTouched = false;// �м��ߵ��λ�ñ仯����

	private String[] SPPED_SCALES1 = new String[6];// { "0", "10", "20","30",
													// "40","120"};
	private String[] dates1 = new String[] { "1", "2", "3", "4", "5", "6", "7",
			"8", "9", "10", "11", "12", "13", "14", "15", "16", "17", "18",
			"19", "20", "21", "22", "23", "24", "25", "26", "27", "28", "29",
			"30", "31" };
	// ��3��ֻ��[�������]-����ʵ����,������Ϊ���ڻ�ͼ�����з���[�鿴Ч��]��
	// public float[] firstPoints = {33, 14, 21, 11, 40, 15, 39 ,8, 45, 35,33,
	// 14, 21, 11, 40,
	// 15, 39 ,8, 45, 35,33, 14, 21, 11, 40, 15, 39 ,8, 45, 35,22};
	public float[] firstPoints = { 0, 20, 30, 40, 50, 60, 70, 60, 70, 70, 70,
			40, 30, 20, 10, 10, 20, 30, 40, 50, 60, 70, 80, 70, 60, 50, 40, 30,
			20, 10, 10, 20, 30, 40, 50, 60, 70, 80, 70, 60, 50, 40, 30, 20, 10 };
	public float[] secondPoints = { 14, 31, 11, 21, 15, 40, 8, 30, 13, 45, 14,
			31, 11, 21, 15, 40, 8, 30, 13, 45, 14, 31, 11, 21, 15, 40, 8, 30,
			13, 45, 20 };
	public float[] thirdPoints = { 40, 47, 11, 38, 21, 14, 37, 29, 31, 32, 15,
			49, 20, 47, 11, 38, 21, 14, 37, 29, 31, 32, 15, 49, 20, 47, 11, 38,
			21, 14, 37 };

	private Paint linePaint = new Paint();// �����ߡ�
	private Paint textPaint = new Paint();// ����
	private Paint yChartPaint = new Paint();// Y����⡣
	private Paint circleRedBlueGreenPaint = new Paint();// ���λ��ʡ�
	private Paint circelPaint = new Paint();// �յ�ԲȦ��
	private Paint innerCircelPaint = new Paint();// ��Բ��
	private Paint chartLinePaint = new Paint();// ��һ����
	private Paint secondChartPaint = new Paint();// �ڶ����ߡ�
	private Paint thirdChartPaint = new Paint();// �������ߡ�
	private Paint centerLinePaint = new Paint();// �м����ߡ�

	// private String totalChartNum = "1132";
	// private String tongbiRatio = "0.19%";
	// private String huanbiRatio = "3.92%";

	private int leftDirectionId = 1;// �����ͷ����
	private int rightDirectionId = 1;// �����ͷ����

	public StatusValueCheckView(Context context) {
		// TODO Auto-generated constructor stub
		super(context);
	}

	public StatusValueCheckView(Context context, AttributeSet attrs) {
		super(context, attrs);

	}

	public StatusValueCheckView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	// setters
	// public void setTotalChartNum(String totalChartNum) {
	// this.totalChartNum = totalChartNum;
	// }

	// public void setTongbiRatio(String tongbiRatio) {
	// this.tongbiRatio = tongbiRatio;
	// }
	//
	// public void setHuanbiRatio(String huanbiRatio) {
	// this.huanbiRatio = huanbiRatio;
	// }

	// �������¼�ͷ��ָ��
	public void setLeftArrowDirection(int upDirectionId) {
		this.leftDirectionId = upDirectionId;
	}

	public void setRightArrowDirection(int downDirectionId) {
		this.rightDirectionId = downDirectionId;
	}

	// ������ʵ���ݸ�ֵ��
	public void setData(float[] firstPoints, float[] secondPoints,
			float[] thirdPoints) {
		this.firstPoints = firstPoints;
		this.secondPoints = secondPoints;
		this.thirdPoints = thirdPoints;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		// ����ģʽ-������Ϊ�����ߡ�
		PathEffect effect = new DashPathEffect(new float[] { 6, 6, 6, 6, 6 }, 2);
		// ��������·��.
		Path path = new Path();

		linePaint.setStyle(Style.STROKE);
		linePaint.setStrokeWidth((float) 0.7);
		linePaint.setColor(Color.BLACK);
		linePaint.setAntiAlias(true);// ��ݲ���ʾ
		textPaint.setStyle(Style.FILL);// ���÷����
		textPaint.setStrokeWidth(1);// �ʿ�5����
		textPaint.setColor(Color.BLACK);// ����Ϊ����
		textPaint.setAntiAlias(true);// ��ݲ���ʾ
		textPaint.setTextAlign(Align.CENTER);
		textPaint.setTextSize(15);
		yChartPaint.setStyle(Style.FILL);
		yChartPaint.setStrokeWidth(1);
		yChartPaint.setColor(Color.BLACK);
		yChartPaint.setAntiAlias(true);
		yChartPaint.setTextAlign(Align.CENTER);
		yChartPaint.setTextSize(18);
		circelPaint.setStyle(Style.FILL);
		circelPaint.setStrokeWidth(2);
		circelPaint.setColor(Color.YELLOW);
		circelPaint.setAntiAlias(true);
		innerCircelPaint.setStyle(Style.FILL);
		innerCircelPaint.setStrokeWidth(1);
		innerCircelPaint.setColor(Color.parseColor("#464646"));
		innerCircelPaint.setAntiAlias(true);
		chartLinePaint.setStyle(Style.FILL);
		chartLinePaint.setStrokeWidth(3);
		chartLinePaint.setColor(Color.rgb(255, 210, 0));// (1)��ɫ
		chartLinePaint.setAntiAlias(true);
		secondChartPaint.setStyle(Style.FILL);
		secondChartPaint.setStrokeWidth(3);
		secondChartPaint.setColor(Color.rgb(169, 222, 63));// (2)��ɫ
		secondChartPaint.setAntiAlias(true);
		thirdChartPaint.setColor(Color.rgb(108, 212, 255));// (3)��ɫ
		thirdChartPaint.setStrokeWidth(3);
		thirdChartPaint.setStyle(Style.FILL);
		thirdChartPaint.setAntiAlias(true);
		centerLinePaint.setColor(Color.parseColor("#000000"));// �м䶯̬�ߡ�7DA62D
		centerLinePaint.setStrokeWidth(3);

		circleRedBlueGreenPaint.setStrokeWidth(6);
		circleRedBlueGreenPaint.setAntiAlias(true);// ������ݡ�
		circleRedBlueGreenPaint.setStyle(Style.STROKE);
		circleRedBlueGreenPaint.setColor(Color.parseColor("#4692B1"));//

		// TODO ��Y��Ŀ̶ȡ�
		float tempLine1Max = getMaxNumFromArr(firstPoints);
		float tempLine2Max = getMaxNumFromArr(secondPoints);
		float tempLine3Max = getMaxNumFromArr(thirdPoints);

		// �̶ȵ����ֵ��
		float maxNum = getMaxNumOfThree(tempLine1Max, tempLine2Max,
				tempLine3Max);// ��������ֵ��
		float finalMaxNum = getRelativeNum(maxNum);// �������ֵ�ҳ��ٽ������ֵ--����Y�̶ȱ�ע��
		for (int i = 0; i <= 5; i++) {// ѭ����Y�̶ȸ�ֵ��������ʾ��
			if (maxNum >= 0 && maxNum < 1) {
				SPPED_SCALES1[i] = String
						.valueOf((int) ((float) finalMaxNum / 5 * i)) + "%";// �ٷ�����Y��̶ȡ�
			} else {
				SPPED_SCALES1[i] = String
						.valueOf(0 + (int) ((float) finalMaxNum / 5 * i));
			}
		}

		// ��׼�㡣
		float gridX = 30 + 10;
		float gridY = getHeight() - 30;
		// XY�����
		float xSpace = (float) ((getWidth() - 60) / 31 - 1.5);
		float ySpace = (getHeight() - 160) / (SPPED_SCALES1.length - 1) + 3;
		// ��Y��(����ͷ)��
		canvas.drawLine(gridX, gridY - 20 - 10, gridX, 30 + 20, linePaint);
		canvas.drawLine(gridX, 30 + 20, gridX - 6, 30 + 14 + 20, linePaint);// Y���ͷ��
		canvas.drawLine(gridX, 30 + 20, gridX + 6, 30 + 14 + 20, linePaint);
		// ��Y�����֡�
		canvas.drawText("������", gridX, 50 - 5, yChartPaint);
		// TODO �����Ϸ���һ����"1132"
		// canvas.drawText("�ϼ�:", gridX+300, 50-5, yChartPaint);
		// canvas.drawText(totalChartNum, gridX+300+50+5+3, 50-5, yChartPaint);
		// ��һ���ԱȻ�-yellow-green(1)
		// circleRedBlueGreenPaint.setColor(Color.rgb(169, 222, 63));//��ɫ(�׻�)��
		// canvas.drawCircle(gridX+300+180, 30+20-10, 8,
		// circleRedBlueGreenPaint);
		// circleRedBlueGreenPaint.setColor(Color.rgb(255, 210, 0));//��ɫ(�ϻ�)��
		// canvas.drawCircle(gridX+300+180-8, 30+20-10, 8,
		// circleRedBlueGreenPaint);
		// red-green-�Ҳ�ٷֱ�(1)"0.19%"
		// canvas.drawText(tongbiRatio, gridX+300+180-8+50+5, 50-5,
		// yChartPaint);
		// yChartPaint.setColor(Color.rgb(169, 222, 63));//��ͷ��ɫ--��ɫ--��
		// if(leftDirectionId > 0) {
		// canvas.drawText("��", gridX+300+180-8+85+10, 50-5, yChartPaint);
		// } else {
		// canvas.drawText("��", gridX+300+180-8+85+10, 50-5, yChartPaint);
		// }

		// �ڶ����ԱȻ�-red-blue(2)
		// circleRedBlueGreenPaint.setColor(Color.rgb(108, 212, 255));//��ɫ(�׻�)��
		// canvas.drawCircle(gridX+300+180+50+50+50+50-10, 30+20-10, 8,
		// circleRedBlueGreenPaint);
		// circleRedBlueGreenPaint.setColor(Color.rgb(255, 210, 0));//��ɫ(�ϻ�)��
		// canvas.drawCircle(gridX+300+180+50+50+50+50-10-8, 30+20-10, 8,
		// circleRedBlueGreenPaint);
		// //red-blue-�Ҳ�ٷֱ�(2)"3.92%"
		// yChartPaint.setColor(Color.parseColor("#ffffffff"));//�����ɫ��
		// canvas.drawText(huanbiRatio, gridX+300+180+50+50+50+50-10-8+50+5,
		// 50-5, yChartPaint);
		// yChartPaint.setColor(Color.rgb(108, 212, 255));//��ͷ��ɫ--��ɫ--��ɫ��
		// if(rightDirectionId > 0) {
		// canvas.drawText("��", gridX+300+180+50+50+50+50-10-8+85+10, 50-5,
		// yChartPaint);
		// } else {
		// canvas.drawText("��", gridX+300+180+50+50+50+50-10-8+85+10, 50-5,
		// yChartPaint);
		// }

		float y = 0;// Y�����
		// ��X��+�������ߡ�
		y = gridY - 20;
		canvas.drawLine(gridX, y - 10, getWidth() - 55 + 10, y - 10, linePaint);// X��.
		canvas.drawLine(getWidth() - 55 + 10, y - 10,
				getWidth() - 55 - 14 + 10, y - 6 - 10, linePaint);// X���ͷ��
		canvas.drawLine(getWidth() - 55 + 10, y - 10,
				getWidth() - 55 - 14 + 10, y + 6 - 10, linePaint);
		for (int n = 0; n < SPPED_SCALES1.length; n++) {
			y = gridY - 20 - n * ySpace;
			linePaint.setPathEffect(effect);// �跨���߼����ʽ��
			// ����X��֮���------��������-------
			if (n > 0) {
				path.moveTo(gridX, y - 10);// ������������㡿��
				path.lineTo(getWidth() - 55 + 10, y - 10);// �����������յ㡿��
				canvas.drawPath(path, linePaint);
			}
			// ��Y��̶ȡ�
			canvas.drawText(SPPED_SCALES1[n], gridX - 6 - 7, y, textPaint);
		}
		// ����X�̶����ꡣ
		float x = 0;
		if (dates1[0] != null) {
			for (int n = 0; n < dates1.length; n++) {
				// ȡX�̶�����.
				x = gridX + (n + 1) * xSpace;// ��ԭ��(0,0)�������̶�,�����ƶ�һ����ȡ�
				// ��X�����̶�ֵ��
				if (dates1[n] != null) {
					canvas.drawLine(x, gridY - 30, x, gridY - 18, linePaint);// ��X�̶ȡ�
					canvas.drawText(dates1[n], x, gridY + 5, textPaint);// X����̶�ֵ��
				}
			}
		}

		// ��ʼ�㡣
		float lastPointX = 0;
		float lastPointY = 0;
		float currentPointX = 0;
		float currentPointY = 0;
		if (firstPoints != null) {
			// 1.���Ƶ�һ�����ߡ�
			for (int n = 0; n < dates1.length; n++) {
				// get current point
				currentPointX = n * xSpace + xSpace + xSpace + 2;
				// currentPointY = (float)(getHeight()-ySpace*3/4) -
				// (float)firstPoints[n] / 60 * (this.getHeight() -
				// ySpace)-(float)2.5;
				// currentPointY = (1 - (float)firstPoints[n]/80) * (gridY-40);
				currentPointY = (float) (getHeight() - 40) - 15 - 5
						- (float) firstPoints[n] / ((float) 1.6 * maxNum)
						* (getHeight() - 40);
				// draw line
				if (n > 0) {
					canvas.drawLine(lastPointX, lastPointY, currentPointX,
							currentPointY, chartLinePaint);// ��һ����[��ɫ]
				}
				lastPointX = currentPointX;
				lastPointY = currentPointY;
			}
			for (int n = 0; n < dates1.length; n++) {
				// get current point
				currentPointX = n * xSpace + xSpace + xSpace + 2;
				// currentPointY = (float)(getHeight()-ySpace*3/4) -
				// (float)firstPoints[n] / 60 * (this.getHeight() -
				// ySpace)-(float)2.5;
				// currentPointY = (1 - (float)firstPoints[n]/80) * (gridY-30);
				currentPointY = (float) (getHeight() - 40) - 15 - 5
						- (float) firstPoints[n] / ((float) 1.6 * maxNum)
						* (getHeight() - 40);
				// draw line
				circelPaint.setColor(Color.rgb(255, 210, 0));// (1)��ɫ
				canvas.drawCircle(lastPointX, lastPointY, 8, circelPaint);
				canvas.drawCircle(lastPointX, lastPointY, 5, innerCircelPaint);
				lastPointX = currentPointX;
				lastPointY = currentPointY;
			}
		}

		// ������ʼ�㡣
		lastPointX = 0;// gridX+10;
		lastPointY = 0;// gridY-90;
		currentPointX = 0;
		currentPointY = 0;

		if (secondPoints != null) {
			// 2.���Ƶڶ������ߡ�
			for (int n = 0; n < dates1.length; n++) {
				// get current point
				currentPointX = n * xSpace + xSpace + xSpace + 2;
				// currentPointY = (float)(getHeight()-ySpace*3/4) -
				// (float)secondPoints[n] / 60 * (this.getHeight() -
				// ySpace)-(float)2.5;
				currentPointY = (float) (getHeight() - 40) - 15 - 5
						- (float) secondPoints[n] / ((float) 1.6 * maxNum)
						* (getHeight() - 40);
				// draw line
				if (n > 0) {
					canvas.drawLine(lastPointX, lastPointY, currentPointX,
							currentPointY, secondChartPaint);// �ڶ�����[��ɫ]
				}
				lastPointX = currentPointX;
				lastPointY = currentPointY;
			}
			for (int n = 0; n < dates1.length; n++) {
				// get current point
				currentPointX = n * xSpace + xSpace + xSpace + 2;
				// currentPointY = (float)(getHeight()-ySpace*3/4) -
				// (float)secondPoints[n] / 60 * (this.getHeight() -
				// ySpace)-(float)2.5;
				currentPointY = (float) (getHeight() - 40) - 15 - 5
						- (float) secondPoints[n] / ((float) 1.6 * maxNum)
						* (getHeight() - 40);
				// draw line
				circelPaint.setColor(Color.rgb(169, 222, 63));// (2)��ɫ
				canvas.drawCircle(lastPointX, lastPointY, 8, circelPaint);
				canvas.drawCircle(lastPointX, lastPointY, 5, innerCircelPaint);
				lastPointX = currentPointX;
				lastPointY = currentPointY;
			}
		}

		// ������ʼ�㡣
		lastPointX = 0;// gridX+10;
		lastPointY = 0;// gridY-240;
		currentPointX = 0;
		currentPointY = 0;

		if (thirdPoints != null) {
			// 3.���Ƶ��������ߡ�
			for (int n = 0; n < dates1.length; n++) {
				// get current point
				currentPointX = n * xSpace + xSpace + xSpace + 2;
				// currentPointY = (float)(getHeight()-ySpace*3/4) -
				// (float)thirdPoints[n] / 60 * (this.getHeight() -
				// ySpace)+(float)4.0;
				currentPointY = (float) (getHeight() - 40) - 15 - 5
						- (float) thirdPoints[n] / ((float) 1.6 * maxNum)
						* (getHeight() - 40);
				// draw line
				if (n > 0) {
					canvas.drawCircle(lastPointX, lastPointY, 5,
							thirdChartPaint);
					canvas.drawLine(lastPointX, lastPointY, currentPointX,
							currentPointY, thirdChartPaint);// ��������[��ɫ]
				}
				lastPointX = currentPointX;
				lastPointY = currentPointY;
			}
			for (int n = 0; n < dates1.length; n++) {
				// get current point
				currentPointX = n * xSpace + xSpace + xSpace + 2;
				// currentPointY = (float)(getHeight()-ySpace*3/4) -
				// (float)thirdPoints[n] / 60 * (this.getHeight() -
				// ySpace)+(float)4.0;
				currentPointY = (float) (getHeight() - 40) - 15 - 5
						- (float) thirdPoints[n] / ((float) 1.6 * maxNum)
						* (getHeight() - 40);
				// draw line
				circelPaint.setColor(Color.rgb(108, 212, 255));// (3)��ɫ
				canvas.drawCircle(lastPointX, lastPointY, 8, circelPaint);
				canvas.drawCircle(lastPointX, lastPointY, 5, innerCircelPaint);
				lastPointX = currentPointX;
				lastPointY = currentPointY;
			}
		}

		// �м���ֱ��--
		canvas.drawLine(s, gridY - 40, s, gridY - 5 * ySpace - 40,
				centerLinePaint);
		canvas.drawLine(s - 10, gridY - 40, s + 10, gridY - 40, centerLinePaint);
		canvas.drawLine(s - 10, gridY - 5 * ySpace - 40, s + 10, gridY - 5
				* ySpace - 40, centerLinePaint);
	}

	int clickCount = 0;
	long firstClickTime = 0;
	long secondClickTime = 0;

	/**
	 * ���Ƽ���--����˫���¼���
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			// if(MainLSPKPIActivity.isForbidClick)
			// {//��4��titleBtn������,��ֹ������ͼ��һ�в�����
			// clickCount++;
			// if(clickCount == 1) {
			// firstClickTime = System.currentTimeMillis();
			// MainLSPKPIActivity.doubleClick = false;
			// } else if(clickCount == 2) {
			// secondClickTime = System.currentTimeMillis();
			// if(secondClickTime - firstClickTime < 1000) {
			// MainLSPKPIActivity.doubleClick = true;
			// clickCount = 0;
			// firstClickTime = 0;
			// secondClickTime = 0;
			// } else {
			// // MainLSPKPIActivity.doubleClick = false;
			// clickCount = 0;
			// firstClickTime = 0;
			// secondClickTime = 0;
			// }
			// }
			// Log.i("action-down", " action-down");
			// }
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
		// if(!MainLSPKPIActivity.doubleClick)
		// return true;//û˫��ʱ-�ӿؼ��н��㡣
		// else
		// return false;//�����㴫�ݸ����ؼ���
		return true;
	}

	/**
	 * ȡ�����е����Ԫ��
	 * 
	 * @param numArr
	 *            ȡֵ������
	 * @return ���������е����ֵ��
	 */
	private float getMaxNumFromArr(float[] numArr) {
		float maxNum = numArr[0];
		for (int i = 0; i < numArr.length; i++) {
			if (numArr[i] > maxNum) {
				maxNum = numArr[i];
			}
		}
		return maxNum;
	}

	/**
	 * ����3��ֵ�е����ֵ��
	 * 
	 * @param firstNum
	 *            ��һ����ֵ
	 * @param secondNum
	 *            �ڶ�����ֵ
	 * @param thirdNum
	 *            ��������ֵ
	 * @return ���ش����3��ֵ�е����ֵ��
	 */
	private float getMaxNumOfThree(float firstNum, float secondNum,
			float thirdNum) {
		float maxNum = 0;
		if (firstNum >= secondNum && firstNum >= thirdNum) {
			maxNum = firstNum;
		}
		if (secondNum >= firstNum && secondNum >= thirdNum) {
			maxNum = secondNum;
		}
		if (thirdNum >= firstNum && thirdNum >= secondNum) {
			maxNum = thirdNum;
		}
		return maxNum;
	}

	/**
	 * �����������ֵ����ȡ�ٽ����ֵ--����Ϊ�̶ȵ����ֵ----�������̶ȡ�
	 * 
	 * @param num
	 *            �����ֵ
	 * @return ����num����������ֵ, ����̶ȷ��䡣
	 */
	private float getRelativeNum(float num) {
		float desNum = 0;
		if (num >= 0 && num < 1) {
			desNum = 100;
		} else if (num >= 1 && num <= 100) {
			desNum = 100;
		} else if (num > 100 && num < 1000) {
			int num1 = (int) num % 100;// ����-��ʮλ��
			int num2 = (int) num / 100;// ��λ-��λ��
			if (num1 > 0 && num1 <= 50) {
				desNum = num2 * 100 + 50;// ȡ�ٽ�������ֵ��ΪY��̶ȡ�
			} else if (num1 > 50 && num1 < 100) {
				desNum = num2 * 100 + 100;
			}
		} else if (num >= 1000 && num < 1500) {
			desNum = handleY(num, desNum, 1000);
		} else if (num >= 1500 && num < 2000) {
			desNum = handleY(num, desNum, 1000);
		} else if (num >= 2000 && num < 3000) {
			desNum = handleY(num, desNum, 1000);
		} else if (num >= 3000 && num < 4000) {
			desNum = handleY(num, desNum, 1000);
		} else if (num >= 4000 && num < 5000) {
			desNum = handleY(num, desNum, 1000);
		} else if (num >= 5000 && num < 10000) {
			desNum = handleY(num, desNum, 1000);
		}
		return desNum;
	}

	/**
	 * ���崦��Y��̶�
	 * 
	 * @param sourceNum
	 *            �����ʵ��ֵ
	 * @param desNum
	 *            Ҫ���ص�Ŀ��ֵ
	 * @param highPostionNum
	 *            ʵ��ֵ�����λ
	 * @return �������Ŀ��ֵ��ֵ�󷵻�
	 */
	private float handleY(float sourceNum, float desNum, int highPostionNum) {
		int num1 = (int) sourceNum % highPostionNum;// ����-��ʮλ��
		int num2 = (int) sourceNum / highPostionNum;// ��λ-��λ��
		if (num1 > 0 && num1 <= highPostionNum / 2) {
			desNum = num2 * highPostionNum + highPostionNum / 2;// ȡ�ٽ�������ֵ��ΪY��̶ȡ�
		} else if (num1 > highPostionNum / 2 && num1 < highPostionNum) {
			desNum = num2 * highPostionNum + highPostionNum;
		}
		return desNum;
	}

}
