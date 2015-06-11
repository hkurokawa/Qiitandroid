package com.hkurokawa.qiitandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hkurokawa.qiitandroid.model.Article;
import com.hkurokawa.qiitandroid.network.QiitaApiV1;
import com.hkurokawa.qiitandroid.views.ArticleAdapter;

import java.io.IOException;
import java.io.InputStreamReader;

import butterknife.ButterKnife;
import butterknife.InjectView;
import retrofit.RestAdapter;
import retrofit.client.Header;
import retrofit.client.Response;
import retrofit.converter.GsonConverter;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
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
        this.listView.setAdapter(this.adapter);

        final Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss z").create();
        final Observable<Response> response = this.buildQiitaApiV1().items().subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread());
        response.flatMap(new Func1<Response, Observable<Article>>() {
            @Override
            public Observable<Article> call(Response response) {
                try {
                    return Observable.from(gson.fromJson(new InputStreamReader(response.getBody().in()), Article[].class));
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        }).subscribe(new Observer<Article>() {
            @Override
            public void onCompleted() {
                Timber.d("Retrofit call for Qiita items completed.");
                MainActivity.this.adapter.notifyDataSetChanged();
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
        response.map(new Func1<Response, Pair<String, String>>() {
            @Override
            public Pair<String, String> call(Response response) {
                String next = null, last = null;
                for (Header h : response.getHeaders()) {
                    if (h.getName() != null) {
                        switch (h.getName()) {
                            case "Link":
                                final String val = h.getValue();
                                final String[] urls = val.split(",");
                                if (urls.length == 2) {
                                    next = urls[0].substring(1, urls[0].indexOf(">; rel=\"next\""));
                                    last = urls[1].substring(2, urls[1].indexOf(">; rel=\"last\""));
                                }
                                break;
                        }
                    }
                }
                return new Pair<>(next, last);
            }
        }).subscribe(new Observer<Pair<String, String>>() {
            @Override
            public void onCompleted() {
                Timber.d("Found header.");
            }

            @Override
            public void onError(Throwable e) {
                Timber.e(e, "Error while getting next and last paging URL for Qiita items.");
            }

            @Override
            public void onNext(Pair<String, String> pair) {
                Timber.d("Next: %s, Last: %s", pair.first, pair.second);
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
}
