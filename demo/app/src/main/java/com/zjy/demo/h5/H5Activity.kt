package com.zjy.demo.h5

import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Button
import android.widget.TextView
import com.zjy.demo.R


class H5Activity : AppCompatActivity(), View.OnClickListener {

    private fun startH5() {
        var intent = Intent(this@H5Activity, H5Activity::class.java)
        startActivity(intent)
    }

    private fun clickAlert() {
        mWebView?.loadUrl("javascript:callJS()")
//        mWebView?.evaluateJavascript("javascript:callJS()", object: ValueCallback<String> {
//            override fun onReceiveValue(value: String?) {
//
//            }
//        })
//        mWebView?.loadDataWithBaseURL()


        // load data
//        val open = assets.open("h5/timecountdown.html")
//        var bufferedReader = BufferedInputStream(open)
//        var byte:ByteArray = ByteArray(1024)
//        var i = 0;
//        var stringBuffer = StringBuffer()
//        while((open.read(byte, 0, byte.size)) != -1) {
//            stringBuffer.append(String(byte))
//        }
//        Log.e("zjy","stringbuffer = "+stringBuffer.toString())
//        mWebView?.loadData(stringBuffer.toString(), "text/html", "utf-8")
    }

    var mWebView : WebView ?= null
    var mBtnCallAlert : TextView ?= null
    var mBtnStartNewH5: TextView ?= null
    var mBtnResume: TextView ?= null
    var mBtnPause: TextView ?= null
    var mBtnResumeTimer: TextView ?= null
    var mBtnPauseTimer: TextView ?= null
    var mBtnGoBack: TextView ?= null
    var mBtnGoGorward: TextView?= null
    var mBtnClearCache: TextView?= null



    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("zjy","H5Activity onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h5)


        mBtnCallAlert = findViewById(R.id.callalert)
        mBtnCallAlert?.setOnClickListener(this)
        mBtnStartNewH5 = findViewById(R.id.start_h5)
        mBtnStartNewH5?.setOnClickListener(this)
        mBtnResume = findViewById(R.id.resume)
        mBtnResume?.setOnClickListener(this)
        mBtnPause = findViewById(R.id.pause)
        mBtnPause?.setOnClickListener(this)
        mBtnResumeTimer = findViewById(R.id.resumetimer)
        mBtnResumeTimer?.setOnClickListener(this)
        mBtnPauseTimer = findViewById(R.id.pausetimer)
        mBtnPauseTimer?.setOnClickListener(this)
        mBtnGoBack = findViewById(R.id.goback)
        mBtnGoBack?.setOnClickListener(this)
        mBtnGoGorward = findViewById(R.id.goforward)
        mBtnGoGorward?.setOnClickListener(this)
        mBtnClearCache = findViewById(R.id.clearcache)
        mBtnClearCache?.setOnClickListener(this)



        mWebView = findViewById(R.id.webview)
        mWebView?.settings?.cacheMode = WebSettings.LOAD_DEFAULT
        mWebView?.settings?.javaScriptEnabled = true
        mWebView?.settings?.javaScriptCanOpenWindowsAutomatically = true
        mWebView?.settings?.allowFileAccessFromFileURLs = false


        mWebView?.settings?.loadsImagesAutomatically = true// 自动加载图片
        //设置自适应屏幕，两者合用
        mWebView?.settings?.useWideViewPort = false//将图片调整到适合webview的大小
        mWebView?.settings?.loadWithOverviewMode = false// 缩放至屏幕的大小

//        mWebView?.settings?.databaseEnabled = true// 不再维护 database/webview.db 没有了
        mWebView?.settings?.domStorageEnabled = true// 打开domcache

        mWebView?.settings?.setAppCacheEnabled(true)
        mWebView?.settings?.setAppCachePath(cacheDir.path)
        mWebView?.settings?.setAppCacheMaxSize(5*1024*1024)



//        mWebView?.loadUrl("file:///android_asset/h5/javascript.html")
//        mWebView?.loadUrl("file:///android_asset/h5/timecountdown.html")
//        mWebView?.loadUrl("http://www.huajiao.com")
//        mWebView?.loadUrl("http://www.qq.com")
        mWebView?.loadUrl("https://activity.huajiao.com/web/share/banner/2018/pgcBuy/index.html?userId=")
//        mWebView?.loadUrl("https://test.huajiao.com/h5_plugin/test.html")
        mWebView?.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                Log.e("zjy","shouldOverrideUrlLoading url = "+url)
                return super.shouldOverrideUrlLoading(view, url)
            }

            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                Log.e("zjy","onPageStarted url = "+url)
                super.onPageStarted(view, url, favicon)
            }

            override fun onPageFinished(view: WebView?, url: String?) {
                Log.e("zjy","onPageFinished url = "+url)
                super.onPageFinished(view, url)
                writeData()
            }

            override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
                Log.e("zjy","shouldInterceptRequest url = "+url)
                return super.shouldInterceptRequest(view, url)
            }

            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
                return super.shouldInterceptRequest(view, request)
            }

            override fun onLoadResource(view: WebView?, url: String?) {
                super.onLoadResource(view, url)
                Log.e("zjy","onLoadResource = "+url)
            }
        }

        // load H5
//        WebViewPreLoadHelper.getInstance(this)?.loadUrl("https://www.163.com/")

        // 由于设置了弹窗检验调用结果,所以需要支持js对话框
        // webview只是载体，内容的渲染需要使用webviewChromClient类去实现
        // 通过设置WebChromeClient对象处理JavaScript的对话框
        //设置响应js 的Alert()函数
        mWebView?.setWebChromeClient(object : WebChromeClient() {
            override fun onJsAlert(view: WebView, url: String, message: String, result: JsResult): Boolean {
                val b = AlertDialog.Builder(this@H5Activity)
                b.setTitle("Alert")
                b.setMessage(message)
                b.setPositiveButton(android.R.string.ok,
                    DialogInterface.OnClickListener { dialog, which -> result.confirm() })
                b.setCancelable(false)
                b.create().show()
                return true
            }

        })

    }

    fun writeData() {
        val key = "token"
        val val1 = "1234455678"
        val key2 = "uid"
        val val2 = "10000001"
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            mWebView?.evaluateJavascript("window.localStorage.setItem('$key','$val1');", null)
            mWebView?.evaluateJavascript("window.localStorage.setItem('$key2','$val2');", null)
        } else {
            mWebView?.loadUrl("javascript:localStorage.setItem('$key','$val1');")
            mWebView?.loadUrl("javascript:localStorage.setItem('$key2','$val2');")
        }
    }

    override fun onResume() {
        super.onResume()
//        mWebView?.onResume()
    }

    override fun onPause() {
        super.onPause()
//        mWebView?.onPause()
    }


    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.callalert -> clickAlert()
            R.id.start_h5 -> startH5()
            R.id.resumetimer -> mWebView?.resumeTimers()
            R.id.pausetimer -> mWebView?.pauseTimers()

            R.id.resume -> mWebView?.onResume()
            R.id.pause -> mWebView?.onPause()

            R.id.goback -> mWebView?.goBackOrForward(-1)
            R.id.goforward -> mWebView?.goBackOrForward(2)
            R.id.clearcache -> mWebView?.clearCache(true)
        }

    }

    override fun onDestroy() {
        super.onDestroy()
//        WebViewPreLoadHelper.getInstance(this)!!.clear()
    }
}
