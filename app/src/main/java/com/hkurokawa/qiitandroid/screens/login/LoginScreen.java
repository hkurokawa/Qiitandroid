package com.hkurokawa.qiitandroid.screens.login;

/**
 * Represents a screen for login panel.
 * Created by hiroshi on 8/16/15.
 */
public interface LoginScreen {
    void setResult(boolean success);

    void loadRequestUrl(String url);
    void showWebView(boolean show);
    void showProgressBar(boolean show);
    void close();
}
