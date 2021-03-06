package com.zjy.demo.h5

import android.content.Context
import android.content.Intent
import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient

class WebViewPreLoadHelper(context: Context) {
    var mWebView:WebView? = null
    companion object {
        private var helper : WebViewPreLoadHelper? = null
        @Synchronized
        fun getInstance(context:Context): WebViewPreLoadHelper? {
            if(helper == null) {
                helper = WebViewPreLoadHelper(context)
            }
            return helper
        }
    }
    init {
        mWebView = WebView(context)
        mWebView?.setBackgroundColor(context.resources.getColor(android.R.color.transparent))
        // 启用javascript
        mWebView?.settings?.javaScriptEnabled = true

        mWebView?.settings?.loadWithOverviewMode = true
        mWebView?.settings?.allowFileAccess = true  //设置可以访问文件
        mWebView?.settings?.domStorageEnabled = true
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mWebView?.settings?.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW //设置 缓存模式 // 开启 DOM storage API 功能
        }

        // 允许屏幕尺寸适配
        mWebView?.settings?.useWideViewPort = true
        //支持放大
        mWebView?.settings?.setSupportZoom(true)
        mWebView?.settings?.textZoom = 100
        mWebView?.settings?.builtInZoomControls = true
        mWebView?.settings?.displayZoomControls = false
        mWebView?.settings?.cacheMode = WebSettings.LOAD_DEFAULT
        mWebView?.settings?.domStorageEnabled = true
        mWebView?.isHorizontalScrollBarEnabled = false//水平不显示
        mWebView?.isVerticalScrollBarEnabled = false //垂直不显示
        mWebView?.webViewClient = object : WebViewClient(){
//            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
//                return super.shouldOverrideUrlLoading(view, request)
//            }
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                if(TextUtils.equals(url,"about:blank") || TextUtils.isEmpty(url)) {
                    return
                }
                Log.e("zjy","webView finish")
                var intent = Intent(context, H5PreloadActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
    context.startActivity(intent)
            }

        }
    }

    fun loadUrl(url: String) {
        mWebView?.loadUrl(url)
    }

    fun getWebView(): WebView? {
        if(mWebView?.parent is ViewGroup) {
            (mWebView?.parent as ViewGroup).removeAllViews()
        }
        return mWebView
    }

    fun clear() {
        mWebView?.loadUrl("about:blank")
    }
}