package com.huajiao.webview;

import android.webkit.JavascriptInterface;

/**
 * 分享接口
 */
public class JsCallShareInterface {
    public JsCallShareInterface() {
    }

    @JavascriptInterface
    public void callShare(String relateId, String url, String title, String desc, String imageUrl) {
    }

    @JavascriptInterface
    public void callShareTo(String relateId, String url, String title, String desc, String imageUrl, String shareTo) {
    }

    @JavascriptInterface
    public void follow(String userID, int isFollow) {
    }

    @JavascriptInterface
    public void login() {
    }

    @JavascriptInterface
    public void zhima(String url) {//认证成功后load新的url
    }

    @JavascriptInterface
    public void forceFinish(String urlPath) {//对url路径的页面，按back直接finish当前页
    }

    @JavascriptInterface
    public void PageLoaded() {//特定的H5页面告知客户端已成功加载，客户端响应隐藏titlebar
    }

    @JavascriptInterface
    public void showMyIncome() {//提现页右上角显示"我的收益"
    }

    @JavascriptInterface
    public void reloadUrl(String url) {//提现流程认证成功后，重定向url页面
    }

    @JavascriptInterface
    public void activeFollow(String uid) {//首页活动中心-关注成功
    }

    @JavascriptInterface
    public void activeFollowCancel(String uid) {//首页活动中心-取消关注成功
    }

    @JavascriptInterface
    public void modifyUser() {
    }

    @JavascriptInterface
    public void auth(String source) {//h5调起native的第三方授权认证，source代码渠道

    }
}