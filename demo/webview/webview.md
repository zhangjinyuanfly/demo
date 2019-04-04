# WebView SonicHelper

## 1.集成vas Sonic
[vas sonic github](https://github.com/Tencent/VasSonic/)

```
implementation 'com.tencent.sonic:sdk:3.1.0'// vas sonic webView cache
```

## 2.代码接入

#### sonic初始化
sonic会在第一次调用<code>SonicHelper.getInstance(this).buildSonic(url)</code>时候初始化，sonic配置参数在<code>SonicHelper.java</code>类中，暂时没有开放很多

```java
SonicHelper.getInstance(this)
    .setCacheFile(new File("cachefile"))// cache目录，不设置会使用默认的
    .setUserAccount("useraccount")// 用户信息。暂时没什么用，不设置为空
    .setUserAgent("ua");// userAgent，不设置为空
```

#### 创建SoniceWebView。webView

```java
sonicWebView = SonicHelper.getInstance(this).buildSonic(url);// sonic开始预加载url，并创建对应session
if(sonicWebView != null) {
    sonicWebView.setUserAgentString(HttpUtils.getWebViewUserAgent());// 预设UA
    sonicWebView.setUseTouchIntercept(true);// 是否支持H5热区，直播间互动设置为true。配合setHotList使用
    sonicWebView.setRoundWebView(false);// 是否支持圆角webview。默认false
    sonicWebView.addJavascriptInterface(new JSCallInterface, "custom");// 预设javascripInterface。内部已经添加:JsCallShareInterface(), "CallShare"
    mWebView = sonicWebView.buildWebView(this);// 创建真正的webView
    // TODO 把webview动态addView到当前activity
}
```

#### 使用SonicWebView.SonicClient
```java
sonicWebView.setSonicClient(new SonicWebView.SonicClient() {
    @Override
    public boolean shouldOverrideUrlLoading(SonicWebView sonic, WebView view, String url) {
    }
    @Override
    public void onPageStarted(SonicWebView sonic, WebView view, String url, Bitmap favicon) {
    }
    @Override
    public void onPageFinished(SonicWebView sonic, WebView view, String url) {
    }
    @Override
    public void onReceivedError(SonicWebView sonic, WebView view, int errorCode, String description, String failingUrl) {
    }
}
```

#### 调用loadUrl
```java
if(sonicWebView != null) {
    sonicWebView.loadUrl(url);
}
```

#### 关闭activity，释放资源
```java
if(sonicWebView != null) {
    sonicWebView.onDestroy();
    sonicWebView = null;
}
```

### <font color=#ff0000>坑
+ 1 SonicHelper.getInstance(this).buildSonic(url)
两个url参数要正确。否则在一个session会话周期中会，产生错误的session，导致无法更新。待验证
+ 2 SonicHelper.preLoad(url)预加载，暂时没有找到预加载完毕的回调函数，都封装在内部没有对外开放
### </font>


#### <font color=#ff0000>TODO 其他暂未整理部分，后续整理后补充
* 1 JSBridge 目前是需要在activity中实现
* 2 activity中，WebView.goBack
* 3 WebView.setWebChromeClient()
#### </font>

