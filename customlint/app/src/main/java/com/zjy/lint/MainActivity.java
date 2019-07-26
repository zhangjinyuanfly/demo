package com.zjy.lint;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView ddd;

//    @SuppressLint("LogUsage")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("1","222");
        Log.e("1","ffff");
        Log.v("1","ffff");
        new Thread().run();

    }
}
