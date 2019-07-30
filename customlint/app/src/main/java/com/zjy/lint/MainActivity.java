package com.zjy.lint;

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

//        WebView webView = new WebView(this);
//        webView.evaluateJavascript("fff", null);
        Log.d("1","222");
        Log.e("1","ffff");
        Log.v("1","ffffdd");
        new Thread();

    }
}
