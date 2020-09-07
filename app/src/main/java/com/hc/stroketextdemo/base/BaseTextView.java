package com.hc.stroketextdemo.base;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

public class BaseTextView extends AppCompatTextView {

    private Paint mLinePaint;

    private TextPaint mTextPaint;

    public BaseTextView(Context context) {
        super(context);
    }

    public BaseTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (mLinePaint == null) {
            mLinePaint = new Paint();
            mLinePaint.setStrokeWidth(2);
            mLinePaint.setStyle(Paint.Style.FILL);
        }

        if (mTextPaint == null)
            mTextPaint = getPaint();

        String text = "理解TextView三部曲";

        // ------------设置TextAlign ------------
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        // 在(400, 400)位置画文本
        canvas.drawText(text, 400, 400, mTextPaint);

        // 标记TextView落笔点
        mLinePaint.setColor(Color.RED);
        canvas.drawLine(0, 400, getWidth(), 400, mLinePaint);
        mLinePaint.setColor(Color.BLUE);
        canvas.drawLine(400, 0, 400, getHeight(), mLinePaint);

        super.onDraw(canvas);

        // 作图用
//        String text = "Oops gjm are coding.";
//        TextPaint paint = getPaint();
//        canvas.drawText(text, 400, 400, getPaint());

//        Paint.FontMetrics fontMetrics = paint.getFontMetrics();
//        float top = fontMetrics.top;
//        float ascent = fontMetrics.ascent;
//        int baseline = 0;
//        float descent = fontMetrics.descent;
//        float bottom = fontMetrics.bottom;
//
//
//        mLinePaint.setColor(Color.parseColor("#852DCB"));
//        canvas.drawLine(150, 350 + fontMetrics.top, 250, 350 + fontMetrics.top, mLinePaint);
//        canvas.drawLine(300, 400 + fontMetrics.top, getWidth(), 400 + fontMetrics.top, mLinePaint);
//        mLinePaint.setColor(Color.parseColor("#47E132"));
//        canvas.drawLine(150, 375 + fontMetrics.ascent, 250, 375 + fontMetrics.ascent, mLinePaint);
//        canvas.drawLine(300, 400 + fontMetrics.ascent, getWidth(), 400 + fontMetrics.ascent, mLinePaint);
//        mLinePaint.setColor(Color.parseColor("#EB1418"));
//        canvas.drawLine(150, 365, 250, 365, mLinePaint);
//        canvas.drawLine(300, 400, getWidth(), 400, mLinePaint);
//        mLinePaint.setColor(Color.parseColor("#4072DD"));
//        canvas.drawLine(150, 375 + fontMetrics.descent, 250, 375 + fontMetrics.descent, mLinePaint);
//        canvas.drawLine(300, 400 + fontMetrics.descent, getWidth(), 400 + fontMetrics.descent, mLinePaint);
//        mLinePaint.setColor(Color.parseColor("#FB8826"));
//        canvas.drawLine(150, 405 + fontMetrics.bottom, 250, 405 + fontMetrics.bottom, mLinePaint);
//        canvas.drawLine(300, 405 + fontMetrics.bottom, getWidth(), 405 + fontMetrics.bottom, mLinePaint);
//

    }


}
