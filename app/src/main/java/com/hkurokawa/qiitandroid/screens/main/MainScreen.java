package com.hkurokawa.qiitandroid.screens.main;

import com.hkurokawa.qiitandroid.domain.deck.Deck;
import com.hkurokawa.qiitandroid.screens.Screen;

/**
 * Main screen.
 * Created by hiroshi on 8/23/15.
 */
public interface MainScreen extends Screen {
    void publish(Deck deck);
}
