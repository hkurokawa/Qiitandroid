package com.hkurokawa.qiitandroid.screens;

import rx.Observable;

/**
 * Router represents screen transitions.
 * Created by hiroshi on 15/08/19.
 */
public interface Router {
    void toArticleScreen(Screen src, String title, String body);
}
