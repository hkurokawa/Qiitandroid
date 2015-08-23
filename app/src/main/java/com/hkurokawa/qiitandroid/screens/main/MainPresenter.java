package com.hkurokawa.qiitandroid.screens.main;

import com.hkurokawa.qiitandroid.domain.application.Application;
import com.hkurokawa.qiitandroid.domain.deck.Deck;
import com.hkurokawa.qiitandroid.screens.Presenter;
import com.hkurokawa.qiitandroid.screens.Router;

/**
 * Presentation logic for the Main screen.
 * Created by hiroshi on 8/23/15.
 */
public class MainPresenter extends Presenter {
    private final Application app;
    private final Router router;
    private MainScreen screen;

    public MainPresenter(Application app, Router router) {
        this.app = app;
        this.router = router;
    }

    public void takeView(MainScreen screen) {
        final Deck deck = this.app.getDeck();
        this.screen = screen;
        this.screen.publish(deck);
    }

    public void onLoginRequested() {
        this.router.toLoginScreen(this.screen);
    }
}
