package com.zjy.demo.h5

import android.content.DialogInterface
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.webkit.*
import android.widget.Button
import com.zjy.demo.R


class H5Activity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.callalert -> clickAlert()
        }

    }

    private fun clickAlert() {
        mWebView?.loadUrl("javascript:callJS()")
    }

    var mWebView : WebView ?= null
    var mBtnCallAlert : Button ?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("zjy","H5Activity onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_h5)


        mBtnCallAlert = findViewById(R.id.callalert)
        mBtnCallAlert?.setOnClickListener(this)

        mWebView = findViewById(R.id.webview)

        mWebView?.settings?.javaScriptEnabled = true
        mWebView?.settings?.javaScriptCanOpenWindowsAutomatically = true
        mWebView?.settings?.allowFileAccessFromFileURLs = false

        mWebView?.loadUrl("file:///android_asset/h5/javascript.html")
//        mWebView?.loadUrl("http://www.huajiao.com")

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
            }

            override fun shouldInterceptRequest(view: WebView?, url: String?): WebResourceResponse? {
                Log.e("zjy","shouldInterceptRequest url = "+url)
                return super.shouldInterceptRequest(view, url)
            }

            override fun shouldInterceptRequest(view: WebView?, request: WebResourceRequest?): WebResourceResponse? {
                return super.shouldInterceptRequest(view, request)
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

    override fun onDestroy() {
        super.onDestroy()
//        WebViewPreLoadHelper.getInstance(this)!!.clear()
    }
}
