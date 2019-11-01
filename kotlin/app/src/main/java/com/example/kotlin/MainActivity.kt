package com.example.kotlin

import android.os.Bundle
import android.os.SystemClock
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
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
        btn_async.setOnClickListener {
            startAsync()
//            startAsyncThread()
        }
        btn_let.setOnClickListener {
            letTest()
//            startAsyncThread()
        }
    }

    private fun letTest() {
        var s:String? = null
        val a = s?.let {
            Log.e("zjy","s = $s")
        }?:run {
            println()
        }
        Log.e("zjy","a = $a")
        s?.takeIf { s.isNotEmpty() }?.let {

        }?:run {
            Log.e("zjy","s = $s")
        }
        var list  = listOf<String>("a","b","c")

        var index = list.indexOf("c")
        if(index > 0) {
            // DO SOMETHING
            list.get(index)
        } else {
            //
        }

        list.indexOf("c").takeIf {
            it > 0
        }?.run {
            list.get(this)
        }?:run {

        }
    }
    fun println() :Unit {
        Log.e("zjy","sss")
    }


    private fun startAsyncThread() {
        thread {
            val thread1 = thread { asyncTask3() }
            val thread2 = thread { asyncTask4() }
            thread1.join()
            thread2.join()
            Log.e("zjy","task all finish")
        }
    }

    private fun startAsync() {
        GlobalScope.launch {
            val job1 = async(start = CoroutineStart.LAZY) { asyncTask1() }
            val job2 = async(start = CoroutineStart.LAZY) { asyncTask2() }

            job1.await()
            job2.await()
            Log.e("zjy","task all finish")
        }
    }
    suspend fun asyncTask1() {
        Log.e("zjy","task1 start")
        delay(1000)
        Log.e("zjy","task1 finish")
    }
    suspend fun asyncTask2() {
        Log.e("zjy","task2 start")
        delay(3000)
        Log.e("zjy","task2 finish")
    }
    fun asyncTask3() {
        sleep(1000)
        Log.e("zjy","task1 finish")
    }
    fun asyncTask4() {
        sleep(3000)
        Log.e("zjy","task2 finish")
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
