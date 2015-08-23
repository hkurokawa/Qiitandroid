package com.hkurokawa.qiitandroid;

import com.hkurokawa.qiitandroid.domain.application.Application;
import com.hkurokawa.qiitandroid.domain.repository.NetworkAnonymousArticlesRepository;
import com.hkurokawa.qiitandroid.domain.repository.NetworkPerceivedArticlesRepository;
import com.hkurokawa.qiitandroid.domain.repository.PreferenceApplicationRepository;

/**
 * Application represents the global state of the application.
 * Created by hiroshi on 8/22/15.
 */
public class QiitaApplication extends android.app.Application {
    private Application app;

    @Override
    public void onCreate() {
        super.onCreate();
        this.app = new Application(new PreferenceApplicationRepository(), new NetworkAnonymousArticlesRepository(), new NetworkPerceivedArticlesRepository());
    }

    public Application getApp() {
        return this.app;
    }
}
