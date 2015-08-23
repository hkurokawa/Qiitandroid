package com.hkurokawa.qiitandroid.screens.board;

import com.hkurokawa.qiitandroid.domain.article.AnonymousArticle;
import com.hkurokawa.qiitandroid.domain.board.Board;
import com.hkurokawa.qiitandroid.screens.Router;
import com.hkurokawa.qiitandroid.screens.board.BoardPresenter;

/**
 * Presentation logic for anonymous articles list.
 * Created by hiroshi on 8/23/15.
 */
public class ArticleBoardPresenter<T extends AnonymousArticle> extends BoardPresenter<T> {
    private final Router router;

    public ArticleBoardPresenter(Board<T> board, Router router) {
        super(board);
        this.router = router;
    }

    @Override
    public void onItemClick(T item, int position) {
        this.router.toArticleScreen(this.getScreen(), item.getTitle(), item.getBody());
    }
}
