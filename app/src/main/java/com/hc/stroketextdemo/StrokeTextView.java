package com.hc.stroketextdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;

import com.hc.stroketextdemo.utils.DensityUtil;


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
public class StrokeTextView extends AppCompatTextView {

    private TextPaint strokePaint;

    public StrokeTextView(Context context) {
        super(context);
    }

    public StrokeTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public StrokeTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onDraw(Canvas canvas) {

        // lazy load
        if (strokePaint == null) {
            strokePaint = new TextPaint();
        }
        // 复制原来TextViewg画笔中的一些参数
        TextPaint paint = getPaint();
        strokePaint.setTextSize(paint.getTextSize());
        strokePaint.setTypeface(paint.getTypeface());
        strokePaint.setFlags(paint.getFlags());
        strokePaint.setAlpha(paint.getAlpha());

        // 自定义描边效果
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(getResources().getColor(R.color.black));
        int strokeWidth = DensityUtil.dp2px(getContext(), 3);
        strokePaint.setStrokeWidth(strokeWidth);

        String text = getText().toString();

        //在文本底层画出带描边的文本
        int gravity = getGravity();

        if ((gravity & Gravity.LEFT) == Gravity.LEFT) {
            canvas.drawText(text, 0f,
                    getBaseline(), strokePaint);
        } else if ((gravity & Gravity.RIGHT) == Gravity.RIGHT) {
            canvas.drawText(text, getWidth() - strokePaint.measureText(text),
                     getBaseline(), strokePaint);
        } else {
            canvas.drawText(text, (getWidth() - strokePaint.measureText(text)) / 2,
                    getBaseline(), strokePaint);
        }


        super.onDraw(canvas);
    }

}
