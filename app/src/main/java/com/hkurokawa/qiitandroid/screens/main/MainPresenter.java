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
    private MainScreen screen;

    public MainPresenter(Application app) {
        this.app = app;
    }

    public void takeView(MainScreen screen) {
        this.screen = screen;
        this.refresh();
    }

    public void onLoginRequested() {
        this.screen.moveToLoginScreen();
    }

    public void onLoginFinished(boolean success) {
        if (success) {
            this.refresh();
        }
    }

    private void refresh() {
        final Deck deck = this.app.getDeck();
        this.screen.publish(deck);
    }
}
