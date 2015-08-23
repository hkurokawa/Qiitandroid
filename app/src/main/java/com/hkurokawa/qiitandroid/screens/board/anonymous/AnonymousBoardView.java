package com.hkurokawa.qiitandroid.screens.board.anonymous;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ViewAnimator;

import com.hkurokawa.qiitandroid.R;
import com.hkurokawa.qiitandroid.domain.article.AnonymousArticle;
import com.hkurokawa.qiitandroid.domain.board.Board;
import com.hkurokawa.qiitandroid.screens.Router;
import com.hkurokawa.qiitandroid.screens.board.BoardScreen;

import java.util.List;

import rx.Observable;
import timber.log.Timber;

/**
 * Custom view to list anonymous articles.
 * Created by hiroshi on 8/23/15.
 */
public class AnonymousBoardView extends FrameLayout implements BoardScreen<AnonymousArticle>, AnonymousArticleAdapter.OnItemClickListener {
    private AnonymousBoardPresenter presenter;
    private View root;
    private RecyclerView list;
    private AnonymousArticleAdapter adapter;
    private ViewAnimator footerView;
    private Observable<Void> bottomHitObservable;

    public AnonymousBoardView(Context context) {
        super(context);
        this.init(context);
    }

    public AnonymousBoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public AnonymousBoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    private void init(Context context) {
        this.root = LayoutInflater.from(context).inflate(R.layout.view_board, this, true);
        this.list = (RecyclerView) this.root.findViewById(R.id.list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        this.list.setLayoutManager(layoutManager);

        this.adapter = new AnonymousArticleAdapter();
        this.adapter.setOnItemClickListener(this);
        this.footerView = (ViewAnimator) LayoutInflater.from(context).inflate(R.layout.item_footer, this.list, false);
        this.adapter.setFooterView(this.footerView);
        this.list.setAdapter(this.adapter);
        this.bottomHitObservable = createBottomHitObservable(this.list, layoutManager);

    }

    public void setBoard(Board<AnonymousArticle> board, Router router) {
        this.presenter = new AnonymousBoardPresenter(board, router);
        this.presenter.takeView(this);
    }

    @Override
    public void onItemClick(AnonymousArticle article, int position) {
        this.presenter.onItemClick(article, position);
    }

    private static Observable<Void> createBottomHitObservable(final RecyclerView lv, final LinearLayoutManager layoutManager) {
        return Observable.create(subscriber ->
                lv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    boolean onBottom;

                    @Override
                    public void onScrolled(RecyclerView view, int dx, int dy) {
                        int totalItemCount = layoutManager.getItemCount();
                        int visibleItemCount = layoutManager.getChildCount();
                        int pastItemCount = layoutManager.findFirstVisibleItemPosition();

                        final boolean isBottom = pastItemCount + visibleItemCount >= totalItemCount;
                        if (!subscriber.isUnsubscribed() && (!this.onBottom && isBottom)) {
                            Timber.d("Emitting an onBottom event.");
                            subscriber.onNext((Void)null);
                        }
                        this.onBottom = isBottom;
                    }
                }));
    }

    @Override
    public void publish(List<AnonymousArticle> items) {
        this.adapter.set(items);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public Observable<Void> getBottomHitObservable() {
        return this.bottomHitObservable;
    }

    @Override
    public void dismissBottomProgressBar() {
        this.footerView.setDisplayedChild(1);
    }

    @Override
    public void logErr(Throwable e, String msg) {
        Timber.e(e, msg);
    }
}
