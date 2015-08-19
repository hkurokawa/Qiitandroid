package com.hkurokawa.qiitandroid.screens.login;

import android.net.http.SslError;
import android.support.annotation.NonNull;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * A {@link android.webkit.WebViewClient} for OAuth.
 * Created by hiroshi on 8/16/15.
 */
public class OAuthWebViewClient extends WebViewClient {
    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        if (this.listener != null) {
            this.listener.onPageFinished(view, url);
        }
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (this.listener != null) {
            return this.listener.onRedirect(view, url);
        }
        return super.shouldOverrideUrlLoading(view, url);
    }

    @Override
    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
        super.onReceivedError(view, errorCode, description, failingUrl);
        if (this.listener != null) {
            this.listener.onError(new WebViewException(description, errorCode, failingUrl));
        }
    }

    @Override
    public void onReceivedSslError(WebView view, @NonNull SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
        if (this.listener != null) {
            this.listener.onError(new WebViewException("SSL Failure.", error.getPrimaryError(), error.getUrl()));
        }
    }

    interface Listener {
        void onPageFinished(WebView webView, String url);
        boolean onRedirect(WebView view, String url);
        void onError(WebViewException error);
    }

}
