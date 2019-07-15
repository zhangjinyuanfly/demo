package com.zjy.demo.views;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import android.view.View;
import com.zjy.demo.R;


/**
 * 
 * @author xiaanming
 *奶糖自定义ProgressBar，2017/10/12
 */
public class BarRoundProgressBar extends View {
	/**
	 * 画笔对象的引用
	 */
	private Paint paint;

	/**
	 * 圆环的颜色
	 */
	private int roundColor;

	/**
	 * 圆环进度的颜色
	 */
	private int roundProgressColor;

	/**
	 * 中间进度百分比的字符串的颜色
	 */
	private int roundTextColor;

	/**
	 * 中间进度百分比的字符串的字体
	 */
	private float textSize;

	/**
	 * 圆环的宽度
	 */
	private float roundWidth;

	/**
	 * 最大进度
	 */
	private int roundMax;

	/**
	 * 当前进度
	 */
	private int progress;
	/**
	 * 是否显示中间的进度文案
	 */
	private boolean textIsDisplayable;

	/**
	 * 进度的风格，实心或者空心
	 */
	private int style;

	private int lineColor;

	private float lineWidth;

	public static final int STROKE = 0;
	public static final int FILL = 1;

	RectF mOval = new RectF();

	public BarRoundProgressBar(Context context) {
		this(context, null);
	}

	public BarRoundProgressBar(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public BarRoundProgressBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		paint = new Paint();
		paint.setAntiAlias(true); // 消除锯齿

		TypedArray mTypedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundProgressBar);

		// 获取自定义属性和默认值
		roundColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundColor, Color.RED);
		roundProgressColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundProgressColor, Color.GREEN);
		roundTextColor = mTypedArray.getColor(R.styleable.RoundProgressBar_roundTextColor, Color.GREEN);
		roundWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_roundWidth, 5);
		roundMax = mTypedArray.getInteger(R.styleable.RoundProgressBar_roundMax, 100);
		textIsDisplayable = mTypedArray.getBoolean(R.styleable.RoundProgressBar_textIsDisplayable, true);
		style = mTypedArray.getInt(R.styleable.RoundProgressBar_style, 0);
		textSize = mTypedArray.getDimension(R.styleable.RoundProgressBar_rpTextSize, 0);
		lineWidth = mTypedArray.getDimension(R.styleable.RoundProgressBar_lineWidth, 0);
		lineColor = mTypedArray.getColor(R.styleable.RoundProgressBar_lineColor, Color.WHITE);

		mTypedArray.recycle();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		//1、先画最外层的边
		int centre = getWidth() / 2; // 获取圆心的x坐标
		float radius = (int) (centre - lineWidth / 2); // 圆环的半径
		paint.setColor(lineColor); // 设置圆环的颜色
		paint.setStyle(Paint.Style.STROKE); // 设置空心
		paint.setStrokeWidth(lineWidth); // 设置圆环的宽度
        paint.setAntiAlias(true);
        canvas.drawCircle(centre, centre, radius, paint); // 画出圆环
//		//3、画内层的边框
		radius = (int) (centre - roundWidth); // 圆环的半径
		paint.setColor(lineColor); // 设置圆环的颜色
		paint.setStyle(Paint.Style.FILL); // 设置空心
		paint.setAntiAlias(true); // 消除锯齿
		canvas.drawCircle(centre, centre, radius, paint); // 画出圆环
		/**
		 * 画进度百分比
		 */
		paint.setStrokeWidth(0);
		paint.setColor(roundTextColor);
		paint.setTextSize(textSize);
        paint.setAntiAlias(true);
		paint.setTypeface(Typeface.DEFAULT_BOLD); // 设置字体
		int percent = (int) (((float) progress / (float) roundMax) * 100); // 中间的进度百分比，先转换成float在进行除法运算，不然都为0
		
		if (textIsDisplayable && percent != 0 && style == STROKE) {
			float textWidth = paint.measureText(percent + "%"); // 测量字体宽度，我们需要根据字体的宽度设置在圆环中间
			canvas.drawText(percent + "%", centre - textWidth / 2, centre + textSize / 2, paint); // 画出进度百分比
		}

		/**
		 * 画圆弧 ，画圆环的进度
		 */

		// 设置进度是实心还是空心
		paint.setStrokeWidth(roundWidth); // 设置圆环的宽度
		paint.setColor(roundProgressColor); // 设置进度的颜色
		paint.setStrokeCap(Paint.Cap.ROUND);

		radius = (int) (centre - roundWidth); // 圆环的半径
		mOval.set(centre - radius, centre - radius, centre + radius, centre + radius); // 用于定义的圆弧的形状和大小的界限

		switch (style) {
			case STROKE: {
					paint.setStyle(Paint.Style.STROKE);
					canvas.drawArc(mOval, -90, 360 * progress / roundMax, false, paint); // 根据进度画圆弧
				break;
			}
			case FILL: {
				paint.setStyle(Paint.Style.FILL);
				if (progress != 0)
					canvas.drawArc(mOval, -90, 360 * progress / roundMax, true, paint); // 根据进度画圆弧
				break;
			}
		}
	}

	public int getRoundMax() {
		return roundMax;
	}

	/**
	 * 设置进度的最大值
	 * 
	 * @param roundMax
	 */
	public void setRoundMax(int roundMax) {
		if (roundMax < 0) {
			throw new IllegalArgumentException("roundMax not less than 0");
		}
		this.roundMax = roundMax;
	}

	public int getProgress() {
		return progress;
	}

	/**
	 * 设置进度，此为线程安全控件，由于考虑多线的问题，需要同步 刷新界面调用postInvalidate()能在非UI线程刷新
	 * 
	 * @param progress
	 */
	public void setProgress(int progress) {
		if (progress < 0) {
			progress = 0;
		}
		if(progress == 0) {
			postInvalidate();
		}
		if (progress > roundMax) {
			progress = roundMax;
		}
		if (progress <= roundMax) {
			if (this.progress != progress) {
//				Log.i("xwc", "progress is not same");
				this.progress = progress;
				postInvalidate();
			}else{
//				Log.i("xwc", "progress is same , ##########");
			}
		}

	}

	public int getCricleColor() {
		return roundColor;
	}

	public void setCricleColor(int cricleColor) {
		this.roundColor = cricleColor;
	}

	public int getCricleProgressColor() {
		return roundProgressColor;
	}

	public void setCricleProgressColor(int cricleProgressColor) {
		this.roundProgressColor = cricleProgressColor;
	}

	public int getRoundTextColor() {
		return roundTextColor;
	}

	public void setRoundTextColor(int roundTextColor) {
		this.roundTextColor = roundTextColor;
	}

	public float getTextSize() {
		return textSize;
	}

	public void setTextSize(float textSize) {
		this.textSize = textSize;
	}

	public float getRoundWidth() {
		return roundWidth;
	}

	public void setRoundWidth(float roundWidth) {
		this.roundWidth = roundWidth;
	}

    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
    }

    public int getRoundColor() {
        return roundColor;
    }

    public void setRoundColor(int roundColor) {
        this.roundColor = roundColor;
    }
}
