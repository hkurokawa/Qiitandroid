package com.hkurokawa.qiitandroid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.UiThread;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ViewAnimator;

import com.hkurokawa.qiitandroid.model.Article;
import com.hkurokawa.qiitandroid.views.ArticleAdapter;
import com.hkurokawa.qiitandroid.views.DividerItemDecoration;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements ArticleAdapter.OnItemClickListener {
    private ArticleAdapter adapter;
    @InjectView(R.id.list)
    RecyclerView listView;
    private ViewAnimator footerView;

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

        createBottomHitObservable(this.listView, layoutManager).startWith((Void)null).subscribe(new Action1<Void>() {
            private boolean loading;
            private int page = 1;

            @Override
            public void call(Void val) {
                if (!this.loading) {
                    this.loading = true;
                    // Query the next page
                    QiitaApi.createV1().items(this.page)
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .flatMap(new Func1<List<Article>, Observable<Article>>() {
                                @Override
                                public Observable<Article> call(List<Article> response) {
                                    return Observable.from(response);
                                }
                            }).subscribe(new Observer<Article>() {
                        @Override
                        @UiThread
                        public void onCompleted() {
                            Timber.d("Retrofit call for Qiita items completed.");
                            MainActivity.this.adapter.notifyDataSetChanged();
                            loading = false;
                            page++;
                        }

                        @Override
                        @UiThread
                        public void onError(Throwable e) {
                            Timber.e(e, "Error while getting a list of Qiita items.");
                            loading = false;
                        }

                        @Override
                        @UiThread
                        public void onNext(Article article) {
                            MainActivity.this.adapter.add(article);
                        }
                    });
                }
            }
        });
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
            this.startActivity(new Intent(this, LoginActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(Article article, int position) {
        final String body = article.getBody();
        final Intent intent = new Intent(this, ArticleViewActivity.class);
        intent.putExtra(ArticleViewActivity.INTENT_KEY_TITLE, article.getTitle());
        intent.putExtra(ArticleViewActivity.INTENT_KEY_CONTENT, body);
        this.startActivity(intent);
    }

    private static Observable<Void> createBottomHitObservable(final RecyclerView lv, final LinearLayoutManager layoutManager) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(final Subscriber<? super Void> subscriber) {
                lv.addOnScrollListener(new RecyclerView.OnScrollListener() {
                    @Override
                    public void onScrolled(RecyclerView view, int dx, int dy) {
                        int totalItemCount = layoutManager.getItemCount();
                        int visibleItemCount = layoutManager.getChildCount();
                        int pastItemCount = layoutManager.findFirstVisibleItemPosition();

                        if (!subscriber.isUnsubscribed() && pastItemCount + visibleItemCount >= totalItemCount) {
                            Timber.d("Emitting an onBottom event.");
                            subscriber.onNext(null);
                        }
                    }
                });
            }
        });
    }
}
