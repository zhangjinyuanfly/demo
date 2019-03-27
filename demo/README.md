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
+ onResume
+ onPause
+ resumeTimers
+ pauseTimers