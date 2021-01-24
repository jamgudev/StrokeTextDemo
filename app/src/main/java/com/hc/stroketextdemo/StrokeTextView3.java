package com.hc.stroketextdemo;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;

import com.hc.stroketextdemo.utils.DensityUtil;

/**
 * 重写onDraw()：使stroke文字描边支持padding
 *
 * 重写onMeasure():
 *      优化：
 *      1.  当width = 1dp | height = 1dp（布局文件中设置的宽高小于我们实际需要的大小）时，内容显示不全
 *      2.  当width = wrap_content && height = wrap_content时，左右两边的描边效果显示不全
 *         【BUG: width = wrap_content && height = match_parent时，如果strokeWidth过大
 *          仍然还有左右描边显示不全的问题，可以通过向左右两边填充一定的padding来解决】
 *
 *      注：宽或高纠正后，{@link StrokeTextView#setGravity(int)}会被调用【更新为Gravity.CENTER】，布局文件中设置的Gravity会无效
 */
public class StrokeTextView3 extends AppCompatTextView {

    private TextPaint mStrokePaint;

    private float mStrokeWidth;

    private Rect mTextRect;

    private int mCallCount = 0;

    public StrokeTextView3(Context context) {
        super(context);
        init();
    }

    public StrokeTextView3(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public StrokeTextView3(Context context, AttributeSet attrs, int defStyleAttr) {
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

    // 适配：当设置的宽高不够显示时（width = 1dp），自动扩充为wrap_content的区域大小，并居中显示，同时保留padding(如果有)
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        mCallCount++;

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int modifiedWidth = widthSize;
        int modifiedHeight = heightSize;

        String text = getText().toString();
        // 每次重新计算text的宽度，避免list复用问题
        float textWidth = getPaint().measureText(text);

        if (textWidth == 0) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        } else {
            // 获取text的宽高信息
            getPaint().getTextBounds(text, 0, text.length(), mTextRect);
        }

        float widthWeNeed = getCompoundPaddingRight() + getCompoundPaddingLeft() +
                mStrokeWidth + textWidth;

        float heightWeNeed = getCompoundPaddingTop() + getCompoundPaddingBottom() +
                mStrokeWidth + mTextRect.height() + DensityUtil.dp2px(getContext(), 4);
        Paint.FontMetrics fontMetrics = getPaint().getFontMetrics();

        Log.d("jamgu", "mTextRect.height = " + mTextRect.height()
                + ", fontMetrics.height = " + (fontMetrics.bottom - fontMetrics.top));

        Log.d("jamgu", "onMeasure() baseline = " + getBaseline());

        // specific size or match_parent，but we only handle specific size here
        if (widthMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.EXACTLY) {

            // 第二次onMeasure()时，传回来的widthMode = MeasureSpec.EXACTLY
            // 会导致最终Stroke的描边位置不准确，所以这里控制第二次onMeasure()时，不对width进行特殊处理
            if (widthMode == MeasureSpec.EXACTLY && mCallCount < 2) {
                modifiedWidth = (int) Math.max(widthSize, widthWeNeed);

                // 如果值没有改变，说明是match_parent，足够位置显示，所以不特殊处理，否则居中显示
                if (modifiedWidth != widthSize) {
                    setGravity(Gravity.CENTER);
                }
            }

            if (heightMode == MeasureSpec.EXACTLY) {
                modifiedHeight = (int) Math.max(heightSize, heightWeNeed);

                // 如果值没有改变，说明是match_parent，足够位置显示，所以不特殊处理，否则居中显示
                if (modifiedHeight != heightSize) {
                    setGravity(Gravity.CENTER);
                }
            }

            super.onMeasure(MeasureSpec.makeMeasureSpec(modifiedWidth, widthMode),
                    MeasureSpec.makeMeasureSpec(modifiedHeight, heightMode));
//            setMeasuredDimension(modifiedWidth, modifiedHeight);
        }

        // wrap_content
        else if (widthMode == MeasureSpec.AT_MOST || heightMode == MeasureSpec.AT_MOST) {

            if (widthMode == MeasureSpec.AT_MOST) {
                modifiedWidth = (int) (widthWeNeed);

                setGravity(Gravity.CENTER);
            }

            if (heightMode == MeasureSpec.AT_MOST) {
                modifiedHeight = (int) (heightWeNeed);

                setGravity(Gravity.CENTER);
            }

//            setMeasuredDimension(modifiedWidth, modifiedHeight);
            super.onMeasure(MeasureSpec.makeMeasureSpec(modifiedWidth, MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(modifiedHeight, MeasureSpec.EXACTLY));
        }

        // mCallCount = 2时重置，避免list复用问题
        mCallCount %= 2;
        Log.d("jamgu", "onMeasure() after baseline = " + getBaseline());
    }

    @Override
    public void onDraw(Canvas canvas) {

        String text = getText().toString();

        int baseline = getBaseline();
        Log.d("jamgu", "onDraw() baseline = " + baseline);
        if (!TextUtils.isEmpty(text)) {

            //在文本底层画出带描边的文本
            int gravity = getGravity();

            // 优化描边位置的计算，同时支持左、右padding
            if ((gravity & Gravity.LEFT) == Gravity.LEFT) {
                canvas.drawText(text, getCompoundPaddingLeft(),
                        baseline, mStrokePaint);
            } else if ((gravity & Gravity.RIGHT) == Gravity.RIGHT) {
                canvas.drawText(text, getWidth() - getCompoundPaddingRight() - getPaint().measureText(text),
                        baseline, mStrokePaint);
            } else {
                // 除去左、右padding后，在剩下的空间中paint落笔的位置
                float xInLeftSpace = (getWidth() - getCompoundPaddingRight() - getCompoundPaddingLeft() - getPaint().measureText(text)) / 2;
                // 最终落笔点位置 [x = paddingLeft + xInLeftSpace, y = getBaseLine()]
                canvas.drawText(text, getPaddingLeft() + xInLeftSpace,
                        baseline, mStrokePaint);
            }

        }

        super.onDraw(canvas);

        Log.d("jamgu", "onDraw() after baseline = " + getBaseline());

    }

}
