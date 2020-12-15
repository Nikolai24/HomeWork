package com.example.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import androidx.annotation.Nullable;
import java.util.Random;
import static java.lang.Math.abs;

public class CustomView extends View {
    private Paint paint;
    private Paint paint1 = new Paint();
    private Paint paint2 = new Paint();
    private Paint paint3 = new Paint();
    private Paint paint4 = new Paint();
    private int centerX;
    private int centerY;
    public static final int radius = 350;
    public static final int center_radius = 120;
    private RectF rectf;

    interface Listener{
        void onClick(int x, int y);
    }

    private Listener listener;

    public void setListener(Listener listener){
        this.listener = listener;
    }

    public CustomView(Context context) {
        super(context);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CustomView);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(typedArray.getColor(R.styleable.CustomView_circleColor, 0));
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint1.setColor(Color.BLUE);
        paint2.setColor(Color.RED);
        paint3.setColor(Color.GREEN);
        paint4.setColor(Color.YELLOW);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        centerX = MeasureSpec.getSize(widthMeasureSpec) / 2;
        centerY = MeasureSpec.getSize(heightMeasureSpec) / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        rectf = new RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius);
        canvas.drawArc(rectf, 90, 90, true, paint1);
        canvas.drawArc(rectf, 180, 90, true, paint2);
        canvas.drawArc(rectf, 270, 90, true, paint3);
        canvas.drawArc(rectf, 360, 90, true, paint4);
        canvas.drawCircle(centerX, centerY, center_radius, paint);
        super.onDraw(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x2 = centerX - x;
            int y2 = centerY - y;
            if ((abs(x2) <= 120 ) && (abs(y2) <= 120) ) {
                listener.onClick(x, y);
                paint1.setColor(randomColor());
                paint2.setColor(randomColor());
                paint3.setColor(randomColor());
                paint4.setColor(randomColor());
                invalidate();
            }
            else if ((abs(x2) <= 350 ) && (abs(y2) <= 350) && (x < centerX) && (y < centerY) ) {
                listener.onClick(x, y);
                paint2.setColor(randomColor());
                invalidate();
            }
            else if ((abs(x2) <= 350 ) && (abs(y2) <= 350) && (x < centerX) && (y > centerY) ) {
                listener.onClick(x, y);
                paint1.setColor(randomColor());
                invalidate();
            }
            else if ((abs(x2) <= 350 ) && (abs(y2) <= 350) && (x > centerX) && (y < centerY) ) {
                listener.onClick(x, y);
                paint3.setColor(randomColor());
                invalidate();
            }
            else if ((abs(x2) <= 350 ) && (abs(y2) <= 350) && (x > centerX) && (y > centerY) ) {
                listener.onClick(x, y);
                paint4.setColor(randomColor());
                invalidate();
            }
        }
        return super.onTouchEvent(event);
    }

    public int randomColor(){
        Random rand = new Random();
        int r = rand.nextInt(254)+1;
        int g = rand.nextInt(254)+1;
        int b = rand.nextInt(254)+1;
        int randomColor = Color.rgb(r,g,b);
        return randomColor;
    }
}
