package com.hkurokawa.qiitandroid;

import com.hkurokawa.qiitandroid.domain.application.Application;

/**
 * Application represents the global state of the application.
 * Created by hiroshi on 8/22/15.
 */
public class QiitaApplication extends android.app.Application {
    private Application app;

    @Override
    public void onCreate() {
        super.onCreate();
        this.app = new Application();
    }

    public Application getApp() {
        return this.app;
    }
}
