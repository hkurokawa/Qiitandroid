package com.hkurokawa.qiitandroid.screens.board.anonymous;

import android.content.Context;
import android.util.AttributeSet;

import com.hkurokawa.qiitandroid.domain.article.AnonymousArticle;
import com.hkurokawa.qiitandroid.domain.board.Board;
import com.hkurokawa.qiitandroid.screens.ActivityRouter;
import com.hkurokawa.qiitandroid.screens.board.ArticleBoardPresenter;
import com.hkurokawa.qiitandroid.screens.board.BoardListAdapter;
import com.hkurokawa.qiitandroid.screens.board.BoardPresenter;
import com.hkurokawa.qiitandroid.screens.board.BoardView;

/**
 * Custom view to list anonymous articles.
 * Created by hiroshi on 8/23/15.
 */
public class AnonymousBoardView extends BoardView<AnonymousArticle> {
    public AnonymousBoardView(Context context) {
        super(context);
    }

    public AnonymousBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public AnonymousBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected BoardListAdapter createAdapter() {
        return new AnonymousArticleAdapter();
    }

    @Override
    protected BoardPresenter<AnonymousArticle> createPresenter(Board board) {
        return new ArticleBoardPresenter<>(board, new ActivityRouter());
    }
}
