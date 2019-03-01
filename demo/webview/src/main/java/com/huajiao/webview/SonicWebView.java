package com.huajiao.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.webkit.DownloadListener;
import android.webkit.GeolocationPermissions;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.huajiao.webview.bridge.IJSBridgeMethod;
import com.huajiao.webview.bridge.IJSCallDefault;
import com.huajiao.webview.bridge.JSBridge;
import com.tencent.sonic.sdk.SonicEngine;
import com.tencent.sonic.sdk.SonicSession;
import com.tencent.sonic.sdk.SonicSessionConfig;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SonicWebView {

    private RoundCornerWebView webView;
    private String userAgentString;
    // webview sonic 加载
    private SonicSession sonicSession;
    SonicSessionClientImpl sonicSessionClient = null;

    private HashMap<String, Object> javaScriptInterfaceMap = new HashMap<>();

    private String url;
    private String defaultTitle;

    public String getUrl() {
        return url;
    }

    private JSBridge mJSBridge;

    public SonicWebView(Context context, String url) {
        initSonic(context, url);
    }

    private void initSonic(Context context, String url) {
        SonicHelper.getInstance(context).initSonicEngine();
        createSonicSession(url);
    }

    private void createSonicSession(String url) {
        this.url = url;
        // step 2: Create SonicSession
//        HashMap<String, String> request = new HashMap<>();
//        request.put(SonicSessionConnection.CUSTOM_HEAD_FILED_LINK,"https://p0.ssl.qhimg.com/t01ccc252b5c41c50b8.png;https://s0.ssl.qhres.com/static/07f86b850e524942.css;https://s3.ssl.qhres.com/static/c1355281ada25510.js");

        SonicSessionConfig sessionConfigBuilder = new SonicSessionConfig.Builder().setSupportLocalServer(true)
                .setReloadInBadNetwork(true)
//                .setCustomResponseHeaders(request)
                .build();
        sonicSession = SonicEngine.getInstance().createSession(url, sessionConfigBuilder);
        if (null != sonicSession) {
            sonicSession.bindClient(sonicSessionClient = new SonicSessionClientImpl());
        } else {
            // this only happen when a same sonic session is already running,
            // u can comment following codes to feedback as a default mode.
//            throw new UnknownError("create session fail!");
        }
    }

    private void initJSBridge() {
        if(mJSBridge == null) {
            mJSBridge = new JSBridge();
            if(webView != null) {
                mJSBridge.registerCallback(webView);
            }
        }
    }

    public SonicWebView setJSBridgeMethod(HashMap<String, IJSBridgeMethod> methodHashMap) {
        initJSBridge();
        if(methodHashMap != null) {
            Iterator<Map.Entry<String, IJSBridgeMethod>> iterator = methodHashMap.entrySet().iterator();
            while(iterator.hasNext()) {
                Map.Entry<String, IJSBridgeMethod> next = iterator.next();
                if(next != null) {
                    mJSBridge.registerMethod(next.getKey(), next.getValue());
                }
            }
        }
        return this;
    }

    public SonicWebView setDefaultTitle(String title) {
        this.defaultTitle = title;
        return this;
    }

    public SonicWebView setJSBridgeDefaultJSCall(IJSCallDefault preloadJSCall) {
        initJSBridge();
        mJSBridge.setPreloadJSCall(preloadJSCall);
        return this;
    }

    public SonicWebView setUserAgentString(String userAgentString) {
        this.userAgentString = userAgentString;
        return this;
    }

    public SonicWebView addJavascriptInterface(Object obj, String interfaceName) {
        javaScriptInterfaceMap.put(interfaceName, obj);
        return this;
    }

    public RoundCornerWebView buildWebView(final Context context) {
        if(webView == null) {
            addJavascriptInterface(new JsCallShareInterface(), "CallShare");
            webView = new RoundCornerWebView(context.getApplicationContext());
            webView.setBackgroundColor(Color.TRANSPARENT);
            // 启用javascript
            webView.getSettings().setJavaScriptEnabled(true);

            if(mJSBridge != null) {
                mJSBridge.registerCallback(webView);
            }
            if (!TextUtils.isEmpty(userAgentString)) {
                webView.getSettings().setUserAgentString(userAgentString);
            }
            webView.getSettings().setLoadWithOverviewMode(true);
            webView.getSettings().setAllowFileAccess(true);  //设置可以访问文件
            webView.getSettings().setDomStorageEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                webView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW); //设置 缓存模式 // 开启 DOM storage API 功能
            }

            webView.removeJavascriptInterface("searchBoxJavaBridge_");
            webView.addJavascriptInterface(new SonicJavaScriptInterface(sonicSessionClient), "sonic");
            webView.getSettings().setAllowContentAccess(true);
            webView.getSettings().setDatabaseEnabled(true);
            webView.getSettings().setAppCacheEnabled(true);
            webView.getSettings().setSaveFormData(false);

            // 允许屏幕尺寸适配
            webView.getSettings().setUseWideViewPort(true);
            //支持放大
            webView.getSettings().setSupportZoom(true);
            webView.getSettings().setTextZoom(100);
            webView.getSettings().setBuiltInZoomControls(true);
            webView.getSettings().setDisplayZoomControls(false);
            webView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);

            webView.setHorizontalScrollBarEnabled(false);//水平不显示
            webView.setVerticalScrollBarEnabled(false); //垂直不显示
            webView.setWebChromeClient(new WebChromeClientImpl());
            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    if (mSonicClient != null) {
                        if (mSonicClient.shouldOverrideUrlLoading(SonicWebView.this, view, url)) {
                            return true;
                        }
                    }
                    return super.shouldOverrideUrlLoading(view, url);
                }

                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    if (mSonicClient != null) {
                        mSonicClient.onPageStarted(SonicWebView.this, view, url, favicon);
                    }
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    if (mSonicClient != null) {
                        mSonicClient.onPageFinished(SonicWebView.this, view, url);
                    }
                    if (sonicSession != null) {
                        sonicSession.getSessionClient().pageFinish(url);
                    }
                    setWebViewInterface(webView);
                }

                public void onReceivedError(WebView view, int errorCode,
                                            String description, String failingUrl) {
                    if (mSonicClient != null) {
                        mSonicClient.onReceivedError(SonicWebView.this, view, errorCode, description, failingUrl);
                    }
                }

                @Override
                public void onLoadResource(WebView view, String url) {
                    super.onLoadResource(view, url);
                }

                @Nullable
                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                    return super.shouldInterceptRequest(view, request);
                }

                @Nullable
                @Override
                public WebResourceResponse shouldInterceptRequest(WebView view, String url) {
                    if (sonicSession != null) {
                        // to return the local data .
                        return (WebResourceResponse) sonicSession.getSessionClient().requestResource(url);
                    }
                    return super.shouldInterceptRequest(view, url);
                }
            });

            webView.setDownloadListener(new DownloadListener() {
                @Override
                public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                    // 跳转浏览器下载文件
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            setWebViewInterface(webView);
        }
        return webView;
    }

    @SuppressLint("JavascriptInterface")
    private void setWebViewInterface(WebView webView) {
        if(webView != null) {
            if(javaScriptInterfaceMap != null && javaScriptInterfaceMap.size() > 0) {
                Iterator<Map.Entry<String, Object>> iterator = javaScriptInterfaceMap.entrySet().iterator();
                while(iterator.hasNext()) {
                    Map.Entry<String, Object> next = iterator.next();
                    if(next != null) {
//                        webView.addJavascriptInterface(new JsCallShareInterface(), "CallShare");
                        webView.addJavascriptInterface(next.getValue(), next.getKey());
                    }
                }
            }
        }
    }

    /**
     * load url
     * @param url
     */
    public void loadUrl(String url) {
        if(!TextUtils.equals(url, this.url)) {
            Log.e(SonicHelper.TAG,"url different, session recreate");
            // 如果URL发生变化。重新跑一下sonic
            if (sonicSession != null) {
                sonicSession.destroy();
                sonicSession = null;
            }
            createSonicSession(url);
        }
        if (sonicSessionClient != null) {
            sonicSessionClient.bindWebView(webView);
            sonicSessionClient.clientReady();
        } else { // default mode
            webView.loadUrl(url);
        }
    }

    public boolean goBack() {
        if(webView != null && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }

    class WebChromeClientImpl extends WebChromeClient {

        private String title;

        public String getTitle() {
            if (TextUtils.isEmpty(title)) {
                return defaultTitle;
            }
            return title;
        }

        @Override
        public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
            return super.onJsConfirm(view, url, message, result);
        }

        @Override
        public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
            if(message.startsWith(CommonWebView.MSG_PROMPT_HEADER)){   //老的JavaScriptInterface的调用，以MyApp开头
                if(sonicWebChromeClient != null) {
                    sonicWebChromeClient.onJsPrompt(view, url, message, defaultValue, result);
                }
            } else {   //否则为JSBridge的调用
                if(mJSBridge != null) {
                    mJSBridge.handleJSCall(message);
                }
            }
            result.cancel();
            return true;
        }

        //扩展支持alert事件
        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            if(sonicWebChromeClient != null) {
                sonicWebChromeClient.onJsAlert(view, url, message, result);
            }
            return true;
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            this.title = title;
            if(sonicWebChromeClient != null) {
                sonicWebChromeClient.onReceivedTitle(view, title);
            }
            super.onReceivedTitle(view, title);
        }

        @Override
        public void onCloseWindow(WebView window) {
            if(sonicWebChromeClient != null) {
                sonicWebChromeClient.onCloseWindow(window);
            }
            super.onCloseWindow(window);
        }

        //扩展浏览器上传文件
        //3.0++版本
        @JavascriptInterface
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            if(sonicWebChromeClient != null) {
                sonicWebChromeClient.openFileChooser(uploadMsg);
            }
        }

        //3.0--版本
        @JavascriptInterface
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            if(sonicWebChromeClient != null) {
                sonicWebChromeClient.openFileChooser(uploadMsg);
            }
        }

        // For Android  > 4.1.1
        @JavascriptInterface
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            if(sonicWebChromeClient != null) {
                sonicWebChromeClient.openFileChooser(uploadMsg);
            }
        }

        @JavascriptInterface
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            if(sonicWebChromeClient != null) {
                return sonicWebChromeClient.onShowFileChooser(webView, filePathCallback, fileChooserParams);
            }
            return true;
        }


        @Override
        public void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback) {
            callback.invoke(origin, true, true);
            super.onGeolocationPermissionsShowPrompt(origin, callback);
        }
    }

    public void onDestroy() {
        try {
            if(mJSBridge != null) {
                mJSBridge.destroy();
                mJSBridge = null;
            }
            if (webView != null) {
                webView.stopLoading();
                webView.getSettings().setJavaScriptEnabled(false);
                webView.clearView();
                webView.removeAllViews();
                ViewParent parent = webView.getParent();
                if(parent != null && parent instanceof ViewGroup) {
                    ((ViewGroup) parent).removeAllViews();
                }
                webView.setVisibility(View.GONE);
                webView.destroy();
                webView = null;
                mSonicClient = null;
                url = null;
            }
        } catch (Exception e) {

        }
        if (sonicSession != null) {
            sonicSession.destroy();
            sonicSession = null;
        }
    }

    private SonicClient mSonicClient;

    public void setSonicClient(SonicClient sonicClient) {
        this.mSonicClient = sonicClient;
    }

    public interface SonicClient {
        boolean shouldOverrideUrlLoading(SonicWebView sonic, WebView view, String url);

        void onPageStarted(SonicWebView sonic, WebView view, String url, Bitmap favicon);

        void onPageFinished(SonicWebView sonic, WebView view, String url);

        void onReceivedError(SonicWebView sonic, WebView view, int errorCode, String description, String failingUrl);
    }
    private SonicWebChromeClient sonicWebChromeClient;
    public void setSonicWebChromeClient(SonicWebChromeClient webChromeClient) {
        this.sonicWebChromeClient = webChromeClient;
    }
    public interface SonicWebChromeClient {
        boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result);
        boolean onJsAlert(WebView view, String url, String message, JsResult result);
        void onReceivedTitle(WebView view, String title);
        void onCloseWindow(WebView window);
        void openFileChooser(ValueCallback<Uri> uploadMsg);
        boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, WebChromeClient.FileChooserParams fileChooserParams);
        void onGeolocationPermissionsShowPrompt(String origin, GeolocationPermissions.Callback callback);
        void onPermissionRequest(PermissionRequest request);
        void onPermissionRequestCanceled(PermissionRequest request);
    }

}
