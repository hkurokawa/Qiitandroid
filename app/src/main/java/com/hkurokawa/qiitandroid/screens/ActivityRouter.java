package com.hkurokawa.qiitandroid.screens;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.hkurokawa.qiitandroid.screens.aritcleview.ArticleViewActivity;
import com.hkurokawa.qiitandroid.screens.login.LoginActivity;

import rx.Observable;

/**
 * A router from activity to activity.
 * Created by hiroshi on 15/08/19.
 */
public class ActivityRouter implements Router {
    @Override
    public void toArticleScreen(Screen src, String title, String content) {
        ensureIsActivity(src);
        final Context context = (Context) src;
        final Intent intent = new Intent(context, ArticleViewActivity.class);
        intent.putExtra(ArticleViewActivity.INTENT_KEY_TITLE, title);
        intent.putExtra(ArticleViewActivity.INTENT_KEY_CONTENT, content);
        context.startActivity(intent);
    }

    private static void ensureIsActivity(Screen screen) {
        if (!(screen instanceof Activity)) {
            throw new IllegalArgumentException("The source screen must be an activity: " + screen);
        }
    }
}
