package com.hkurokawa.qiitandroid;

/**
 * Represents an exception on WebView.
 * Created by hiroshi on 8/16/15.
 */
public class WebViewException extends Exception {
    private final int errorCode;
    private final String failingUrl;

    public WebViewException(String detailMessage, int errorCode, String failingUrl) {
        super(detailMessage);
        this.errorCode = errorCode;
        this.failingUrl = failingUrl;
    }

    public int getErrorCode() {
        return this.errorCode;
    }

    public String getFailingUrl() {
        return this.failingUrl;
    }
}
