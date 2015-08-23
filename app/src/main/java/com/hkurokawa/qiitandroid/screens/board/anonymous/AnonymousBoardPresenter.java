package com.hkurokawa.qiitandroid.screens.board.anonymous;

import com.hkurokawa.qiitandroid.domain.article.AnonymousArticle;
import com.hkurokawa.qiitandroid.domain.board.Board;
import com.hkurokawa.qiitandroid.screens.Router;
import com.hkurokawa.qiitandroid.screens.board.BoardPresenter;

/**
 * Presentation logic for anonymous articles list.
 * Created by hiroshi on 8/23/15.
 */
public class AnonymousBoardPresenter extends BoardPresenter<AnonymousArticle> {
    private final Router router;

    public AnonymousBoardPresenter(Board<AnonymousArticle> board, Router router) {
        super(board);
        this.router = router;
    }

    @Override
    public void onItemClick(AnonymousArticle item, int position) {
        this.router.toArticleScreen(this.getScreen(), item.getTitle(), item.getBody());
    }
}
