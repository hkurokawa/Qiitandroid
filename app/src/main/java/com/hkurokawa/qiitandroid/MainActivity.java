package com.hkurokawa.qiitandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hkurokawa.qiitandroid.model.Article;
import com.hkurokawa.qiitandroid.network.QiitaApiV1;
import com.hkurokawa.qiitandroid.views.ArticleAdapter;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter<Article> adapter;
    @InjectView(R.id.list)
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.plant(new Timber.DebugTree());
        this.setContentView(R.layout.activity_main);

        ButterKnife.inject(this);

        this.adapter = new ArticleAdapter(this);

        final Observable<ListView> observable = createBottomHitObservable(this.listView);
        observable.subscribe(new Action1<ListView>() {
            private int page;

            @Override
            public void call(ListView listView) {
                // Query the next page
                buildQiitaApiV1().items(page)
                        .subscribeOn(Schedulers.newThread())
                        .observeOn(AndroidSchedulers.mainThread())
                        .flatMap(new Func1<List<Article>, Observable<Article>>() {
                            @Override
                            public Observable<Article> call(List<Article> response) {
                                return Observable.from(response);
                            }
                        }).subscribe(new Observer<Article>() {
                    @Override
                    public void onCompleted() {
                        Timber.d("Retrofit call for Qiita items completed.");
                        MainActivity.this.adapter.notifyDataSetChanged();
                        page++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Timber.e(e, "Error while getting a list of Qiita items.");
                    }

                    @Override
                    public void onNext(Article article) {
                        MainActivity.this.adapter.add(article);
                    }
                });
            }
        });
    }

    private QiitaApiV1 buildQiitaApiV1() {
        final RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint("https://qiita.com");
        if (BuildConfig.DEBUG) {
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        }
        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss z").create(); // RFC 822 format
        builder.setConverter(new GsonConverter(gson));

        return builder.build().create(QiitaApiV1.class);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static Observable<ListView> createBottomHitObservable(final ListView lv) {
        return Observable.create(new Observable.OnSubscribe<ListView>() {
            @Override
            public void call(final Subscriber<? super ListView> subscriber) {
                lv.setOnScrollListener(new AbsListView.OnScrollListener() {
                    private int scrollState;

                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {
                        this.scrollState = scrollState;
                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        Timber.d("onScroll. firstVisibleItem=%d, visibleItemCount=%d, totalItemCount=%d.", firstVisibleItem, visibleItemCount, totalItemCount);
                        if (!subscriber.isUnsubscribed() && this.scrollState == SCROLL_STATE_IDLE && firstVisibleItem + visibleItemCount >= totalItemCount) {
                            Timber.d("Emitting an onBottom event.");
                            subscriber.onNext(lv);
                        }
                    }
                });
            }
        });
    }
}
