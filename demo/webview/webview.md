# WebView SonicHelper

## 1.集成vas Sonic
[vas sonic github](https://github.com/Tencent/VasSonic/)


## 2.代码接入

#### sonic初始化
sonic会在第一次调用<code>SonicHelper.getInstance(this).buildSonic(url)</code>时候初始化，sonic配置参数在<code>SonicHelper.java</code>类中，暂时没有开放很多

```java
SonicHelper.getInstance(this)
    .setCacheFile(new File("cachefile"))// cache目录，不设置会使用默认的
    .setUserAccount("useraccount")// 用户信息。暂时没什么用，不设置为空
    .setUserAgent("ua");// userAgent，不设置为空
```

#### 创建SoniceWebView.webView，SonicWebView并不是真正的WebView，获取真正的WebView需要buildWebView

```java
sonicWebView = SonicHelper.getInstance(this).buildSonic(url);// sonic开始预加载url，并创建对应session
if(sonicWebView != null) {
    sonicWebView.setUserAgentString(HttpUtils.getWebViewUserAgent());// 预设UA
    sonicWebView.addJavascriptInterface(new JSCallInterface, "custom");// 预设javascripInterface。内部已经添加:JsCallShareInterface(), "CallShare"
    mWebView = sonicWebView.buildWebView(this);// 创建真正的webView
    // TODO 把webview动态addView到当前activity
}
```

设置JSBridge，jsbridge包括注册的IJSBridgeMethod，和预埋的方法IJSCallDefault（H5js调用，native反射调用），
```java
sonicWebView.setJSBridgeMethod("H5Inner", HashMap<String, IJSBridgeMethod map>)// 注册的JSBridge
            .setJSBridgeDefaultJSCall("H5Inner", IJSCallDefault);
```

#### 使用SonicWebView.SonicClient，封装起来主要是和Sonic绑定
```java
sonicWebView.setSonicClient(sonicClient);
```

#### 使用SonicWebView.SonicWebChromeClient，封装起来主要是和JSBridge绑定调用
```java
sonicWebView.setSonicWebChromeClient(sonicWebChromeClient);
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

#### preload部分代码使用。
+ 1 sonic preload：
```java
SonicHelper.getInstance(context).preloadUrl(url)
```
+ 2 WebView preload：关联key是SonicWebView中的preloadTag，preloadTag是自动生成<code>"preloadTag://"+url+System.currentTimeMillis()</code>
   + 配置：
   ```java
   SonicHelper.getInstance(context).setPreloadWebViewCount(3);// 允许webview cache数
   ```
   + preload url：
   ```java
   SonicHelper.getInstance(context).preloadWebView(url, new IPreloadWebViewListener() {
            @Override
            public void syncCookie(String url) {
                // todo 每次加载webviews，如果需要同步cookie，可以写在这里
            }
            @Override
            public void onPageStarted(SonicWebView sonic) {
                // 开始load
            }
            @Override
            public void onPageFinished(SonicWebView sonic) {
                preloadTag = sonic.getPreloadTag();// 关联tag
                url = sonic.getUrl();// 请求url
                // load 结束，可以跳转页面，在相应页面获取对应的 SoniceWebView
            }
            @Override
            public void onReceivedError(SonicWebView sonic, int errorCode, String description, String failingUrl) {
                // 失败
            }
        });
   ```
   + 页面加载preload SonicWebView
   ```java
   // 获取SonicWebView
    sonicWebView = SonicHelper.getInstance(this).getPreloadWebView(preloadTag);
    if(sonicWebView == null) {
        sonicWebView = SonicHelper.getInstance(this).buildSonic(url);
    }

    // 结束后释放资源。很重要，否则webview池子满了就不再会preload其他webview了
    SonicHelper.getInstance(this).removePreloadWebView(tag);
   ```

### <font style="color:#ff0000">坑
+ 1 SonicHelper.getInstance(this).buildSonic(url)
url参数要正确。否则在一个session会话周期中会，产生错误的session，导致无法更新。需要session.destroy后重新创建。SonicWebView.loadUrl已经添加
+ 2 SonicHelper.preLoad(url)预加载，暂时没有找到预加载完毕的回调函数，都封装在内部没有对外开放
### </font>


#### <font color=#ff0000>TODO 其他暂未整理部分，后续整理后补充

#### </font>

