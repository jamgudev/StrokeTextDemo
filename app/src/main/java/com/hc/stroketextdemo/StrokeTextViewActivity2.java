package com.hc.stroketextdemo;

import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

/**
 * 番外篇
 */
public class StrokeTextViewActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stroke_text_view_2);

        initTextViews();

    }

    private void initTextViews() {
        Typeface typeface = Typeface.createFromAsset(getAssets(), "Dressel Medium Regular.ttf");
        if (typeface != null) {
            TextView tvOld = findViewById(R.id.tv_old);
            TextView tvOld2 = findViewById(R.id.tv_old_2);

            tvOld2.setTypeface(typeface);
        }
    }
}