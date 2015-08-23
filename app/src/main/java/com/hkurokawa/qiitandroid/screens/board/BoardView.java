package com.hkurokawa.qiitandroid.screens.board;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ViewAnimator;

import com.hkurokawa.qiitandroid.R;
import com.hkurokawa.qiitandroid.domain.article.ListItem;
import com.hkurokawa.qiitandroid.domain.board.Board;

import java.util.List;

import rx.Observable;
import timber.log.Timber;

/**
 * BoardView is a view to display a list of items.
 * Created by hiroshi on 8/23/15.
 */
public abstract class BoardView<T extends ListItem> extends FrameLayout implements BoardScreen<T>, OnBoardListItemClickListener<T> {
    private BoardPresenter<T> presenter;
    private RecyclerView list;
    private BoardListAdapter<T> adapter;
    private ViewAnimator footerView;
    private Observable<Void> bottomHitObservable;

    public BoardView(Context context) {
        super(context);
        this.init(context);
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init(context);
    }

    public BoardView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init(context);
    }

    private void init(Context context) {
        final View root = LayoutInflater.from(context).inflate(R.layout.view_board, this, true);
        this.list = (RecyclerView) root.findViewById(R.id.list);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        this.list.setLayoutManager(layoutManager);

        this.footerView = (ViewAnimator) LayoutInflater.from(context).inflate(R.layout.item_footer, this.list, false);
        this.adapter = this.createAdapter();
        this.adapter.setOnItemClickListener(this);
        this.adapter.setFooterView(this.footerView);
        this.list.setAdapter(this.adapter);
        this.bottomHitObservable = createBottomHitObservable(this.list, layoutManager);
    }

    public void setBoard(Board<T> board) {
        this.presenter = this.createPresenter(board);
        this.presenter.takeView(this);
    }

    @Override
    public void publish(List<T> items) {
        this.adapter.set(items);
        this.adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClick(T item, int position) {
        this.presenter.onItemClick(item, position);
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
                            subscriber.onNext((Void) null);
                        }
                        this.onBottom = isBottom;
                    }
                }));
    }

    protected abstract BoardPresenter<T> createPresenter(Board<T> board);

    protected abstract BoardListAdapter<T> createAdapter();
}
