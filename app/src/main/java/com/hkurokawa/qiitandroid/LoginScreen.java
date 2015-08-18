package com.hkurokawa.qiitandroid;

/**
 * Represents a screen for login panel.
 * Created by hiroshi on 8/16/15.
 */
public interface LoginScreen {
    void loadRequestUrl(String url);
    void showWebView(boolean show);
    void showProgressBar(boolean show);
    void close();
}
