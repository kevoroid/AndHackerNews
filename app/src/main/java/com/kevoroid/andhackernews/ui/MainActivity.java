package com.kevoroid.andhackernews.ui;

import android.os.Bundle;

import com.kevoroid.andhackernews.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
