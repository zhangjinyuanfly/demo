# WebView

## 1.主要方法:

| 类名 |  作用 |
|---|---|
| WebSettings |  配置
| WebViewClient | 请求控制，webview回调通知
| WebChromeClient | 辅助：js，title，进度，权限

## 2.load
+ loadUrl 支持http file content scheme
+ loadData 加载一段html data 不常用
+ loadDataWithBaseURL 使用cache部分，8.0以上有坑，会导致goBack失效。

## 3.状态
+ onResume 单独webview使用
+ onPause
+ resumeTimers 全局webview使用
+ pauseTimers

## 4.前进后退
+ canGoBack  goBack
+ canGoForward goForward
+ goBackOrForward(step) step 负数：goBack，正数：goForward。 数字表示步数

## 5.WebSettings
+ setJavaScriptEnabled
+ setUseWideViewPort 将图片调整到适合webview的大小 
+ setLoadWithOverviewMode 缩放至屏幕的大小
+ setLoadsImagesAutomatically 自动加载图片

## 4.缓存
+ setCacheMode 设置webview cacheMode 4种mode
+ chrome cache 自动创建 /data/data/包名/cache/org.chromium.android_webview/
+ setJavaScriptEnabled
+ 



WebView(Context context, AttributeSet attrs, int defStyleAttr) defStyleAttr不能传入0，否则会导致软键盘无法弹出

webview.addJavascriptInterface 在8.0手机上。只是第一个add进入的会生效。

webview.loadDataWithBaseURL 使用cache部分，8.0以上有坑，会导致goBack失效。

透明webview在oppo vivo 8.0系统白屏问题：http和https切换的时候，http或者https内部切换的时候没问题 