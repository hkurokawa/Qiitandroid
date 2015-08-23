package com.hkurokawa.qiitandroid.screens.board;

import com.hkurokawa.qiitandroid.screens.Screen;

import java.util.List;

import rx.Observable;

/**
 * Scree to display list of items.
 * Created by hiroshi on 8/23/15.
 */
public interface BoardScreen<T> extends Screen {
    void publish(List<T> items);
    Observable<Void> getBottomHitObservable();
    void dismissBottomProgressBar();
    void logErr(Throwable e, String msg);
}
