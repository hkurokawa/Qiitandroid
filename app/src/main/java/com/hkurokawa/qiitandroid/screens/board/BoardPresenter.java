package com.hkurokawa.qiitandroid.screens.board;

import com.applied_duality.rxmobile_android.AndroidSchedulers;
import com.hkurokawa.qiitandroid.domain.board.Board;
import com.hkurokawa.qiitandroid.screens.Presenter;
import com.hkurokawa.qiitandroid.screens.Screen;

import java.util.ArrayList;
import java.util.List;

/**
 * Presentation logic for each board.
 * Created by hiroshi on 8/23/15.
 */
public abstract class BoardPresenter<T> extends Presenter {
    private final Board<T> board;
    private BoardScreen<T> screen;
    private List<T> items;

    public BoardPresenter(Board<T> board) {
        this.items = new ArrayList<>();
        this.board = board;
    }

    public void takeView(BoardScreen<T> screen) {
        this.screen = screen;

        this.board.list(screen.getBottomHitObservable().startWith((Void) null))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        BoardPresenter.this::addItems,
                        e -> BoardPresenter.this.screen.logErr(e, "Failed to list articles."),
                        BoardPresenter.this.screen::dismissBottomProgressBar);
    }

    private void addItems(List<T> items) {
        this.items.addAll(items);
        this.screen.publish(this.items);
    }

    public abstract void onItemClick(T item, int position);

    protected Screen getScreen() {
        return this.screen;
    }
}
