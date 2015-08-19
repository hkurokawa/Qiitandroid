package com.hkurokawa.qiitandroid.screens.main;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewAnimator;

import com.hkurokawa.qiitandroid.R;
import com.hkurokawa.qiitandroid.domain.article.Article;
import com.hkurokawa.qiitandroid.domain.repository.NetworkArticlesRepository;
import com.hkurokawa.qiitandroid.screens.ActivityRouter;
import com.hkurokawa.qiitandroid.views.DividerItemDecoration;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Subscriber;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements ArticleAdapter.OnItemClickListener, LatestArticlesScreen {
    private ArticleAdapter adapter;
    @InjectView(R.id.list)
    RecyclerView listView;
    private ViewAnimator footerView;
    private LatestArticlesPresenter presenter;
    private Observable<Void> bottomHitObservable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.plant(new Timber.DebugTree());
        this.setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        this.listView.setHasFixedSize(true);
        this.listView.addItemDecoration(new DividerItemDecoration(this));

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        this.listView.setLayoutManager(layoutManager);

        this.adapter = new ArticleAdapter();
        this.adapter.setOnItemClickListener(this);
        this.footerView = (ViewAnimator) LayoutInflater.from(this).inflate(R.layout.item_footer, this.listView, false);
        this.adapter.setFooterView(this.footerView);
        this.listView.setAdapter(this.adapter);
        this.bottomHitObservable = createBottomHitObservable(this.listView, layoutManager);

        this.presenter = new LatestArticlesPresenter(new ActivityRouter(), new NetworkArticlesRepository());
        this.presenter.takeView(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            this.presenter.onLoginMenuClick();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(Article article, int position) {
        this.presenter.onItemClick(article, position);
    }

    @Override
    public void publish(List<Article> articles) {
        this.adapter.set(articles);
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

    private static Observable<Void> createBottomHitObservable(final RecyclerView lv, final LinearLayoutManager layoutManager) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
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
                            subscriber.onNext(null);
                        }
                        this.onBottom = isBottom;
                    }
                });
            }
        });
    }
}
