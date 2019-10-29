package com.example.kotlin

import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.lang.Thread.sleep
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_thread.setOnClickListener {
            startThread()
        }
        btn_xiecheng.setOnClickListener {
            startXiecheng()
        }
    }

    var startTime:Long = 0
    var count:Int = 0
    lateinit var name:String
    override fun onResume() {
        super.onResume()
    }

    fun startThread(){
        count = 0
        startTime = SystemClock.uptimeMillis()
        for(i in 0..1) {
            thread {
                Log.e("zjy","name $i =${Thread.currentThread().id}")
                sleep(2000)
                count ++
                Log.e("zjy","name $i =${Thread.currentThread().id}")
                Log.e("zjy","count = $count")
                if(count == 10000) {
                    runOnUiThread {
                        Toast.makeText(this, "end time 线程 = ${SystemClock.uptimeMillis() - startTime}", Toast.LENGTH_LONG).show()
                    }
                    Log.e("zjy","end time 线程 = ${SystemClock.uptimeMillis() - startTime}")
                }
            }
        }
    }

    fun startXiecheng(){
        count = 0
        startTime = SystemClock.uptimeMillis()
        for(i in 0..1) {
            GlobalScope.launch {
                Log.e("zjy","name $i =${Thread.currentThread().id}")
                delay(2000)
                count++
                Log.e("zjy","name $i =${Thread.currentThread().id}")
                Log.e("zjy","count = $count")
                if (count == 10000) {
                    runOnUiThread{
                        Toast.makeText(this@MainActivity, "end time 协程 = ${SystemClock.uptimeMillis() - startTime}", Toast.LENGTH_LONG).show()
                    }
                    Log.e("zjy", "end time 协程 = ${SystemClock.uptimeMillis() - startTime}")
                }
            }
        }
    }

}
