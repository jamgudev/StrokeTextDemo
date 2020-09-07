package com.hc.stroketextdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;

//<com.example.helloworld.stroketext.StrokeTextView
//        android:id="@+id/dnf_right_attribute"
//        android:layout_width="wrap_content"
//        android:layout_height="wrap_content"
//        android:text="18"
//        tools:text="8"
//        android:layout_marginEnd="3dp"
//        android:layout_marginBottom="1dp"
//        android:gravity="end"
//        android:background="#8000"
//        android:textColor="@color/white"
//        android:layout_alignBottom="@id/iv_bg"
//        android:layout_alignEnd="@id/iv_bg"
//        android:textSize="14sp"
//        android:textStyle="bold"
//        tools:ignore="RtlCompat" />
public class StrokeTextView2 extends AppCompatTextView {
    private static final String TAG = "StrokeTextView2";

    private TextPaint mStrokePaint;

    private float mStrokeWidth;

    private Rect mTextRect;

    public StrokeTextView2(Context context) {
        super(context);
        init();
    }

    public StrokeTextView2(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StrokeTextView2(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        // lazy load
        if (mStrokePaint == null) {
            mStrokePaint = new TextPaint();
        }

        if (mTextRect == null) {
            mTextRect = new Rect();
        }

        // 复制原来TextViewg画笔中的一些参数
        TextPaint paint = getPaint();
        mStrokePaint.setTextSize(paint.getTextSize());
        mStrokePaint.setTypeface(paint.getTypeface());
        mStrokePaint.setFlags(paint.getFlags());
        mStrokePaint.setAlpha(paint.getAlpha());

//        TLog.d(TAG, "textLength in init() = " + getText().toString().length());
//        mTextWidth = getPaint().measureText(getText().toString());  初始化时获取的textLength = 0 ？？
        mStrokeWidth = getResources().getDimensionPixelSize(R.dimen.stroke_text_size);

        // 自定义描边效果
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setColor(getResources().getColor(R.color.black));
        mStrokePaint.setStrokeWidth(mStrokeWidth);
    }

    @Override
    public void onDraw(Canvas canvas) {

        String text = getText().toString();

        if (!TextUtils.isEmpty(text)) {

            //在文本底层画出带描边的文本
            int gravity = getGravity();

            // 优化描边位置的计算，同时支持左、右padding
            if ((gravity & Gravity.LEFT) == Gravity.LEFT) {
                canvas.drawText(text, getCompoundPaddingLeft(),
                        getBaseline(), mStrokePaint);
            } else if ((gravity & Gravity.RIGHT) == Gravity.RIGHT) {
                canvas.drawText(text, getWidth() - getCompoundPaddingRight() - getPaint().measureText(text),
                        getBaseline(), mStrokePaint);
            } else {
                // 除去左、右padding后，在剩下的空间中paint落笔的位置
                float xInLeftSpace = (getWidth() - getCompoundPaddingRight() - getCompoundPaddingLeft()
                        - getPaint().measureText(text)) / 2;
                // 最终落笔点位置 [x = paddingLeft + xInLeftSpace, y = getBaseLine()]
                canvas.drawText(text, getPaddingLeft() + xInLeftSpace,
                        getBaseline(), mStrokePaint);
            }

        }

        super.onDraw(canvas);
    }

}
