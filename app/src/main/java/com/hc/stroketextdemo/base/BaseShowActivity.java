package com.hc.stroketextdemo.base;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.hc.stroketextdemo.R;

public class BaseShowActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_show);
    }
}