package com.hkurokawa.qiitandroid.screens.board.perceived;

import android.content.Context;
import android.util.AttributeSet;

import com.hkurokawa.qiitandroid.domain.article.PerceivedArticle;
import com.hkurokawa.qiitandroid.domain.board.Board;
import com.hkurokawa.qiitandroid.screens.ActivityRouter;
import com.hkurokawa.qiitandroid.screens.board.ArticleBoardPresenter;
import com.hkurokawa.qiitandroid.screens.board.BoardListAdapter;
import com.hkurokawa.qiitandroid.screens.board.BoardPresenter;
import com.hkurokawa.qiitandroid.screens.board.BoardView;

/**
 * Custom view to display a list of {@link PerceivedArticle}s.
 * Created by hiroshi on 8/23/15.
 */
public class PerceivedBoardView extends BoardView<PerceivedArticle> {
    public PerceivedBoardView(Context context) {
        super(context);
    }

    public PerceivedBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public PerceivedBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected BoardPresenter<PerceivedArticle> createPresenter(Board<PerceivedArticle> board) {
        return new ArticleBoardPresenter<>(board, new ActivityRouter());
    }

    @Override
    protected BoardListAdapter<PerceivedArticle> createAdapter() {
        return new PerceivedArticleAdapter();
    }
}
