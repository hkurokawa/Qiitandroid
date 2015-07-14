package com.hkurokawa.qiitandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ArticleViewActivity extends AppCompatActivity {
    public static final String INTENT_KEY_TITLE = "title";
    public static final String INTENT_KEY_CONTENT = "content";
    @InjectView(R.id.text)
    TextView textView;
    private String title;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view);

        ButterKnife.inject(this);

        String t = null;
        String c = null;
        if (this.getIntent() != null) {
            t = this.getIntent().getStringExtra(INTENT_KEY_TITLE);
            c = this.getIntent().getStringExtra(INTENT_KEY_CONTENT);
        }
        if (t == null) {
            t = savedInstanceState.getString(INTENT_KEY_TITLE);
        }
        if (c == null) {
            c = savedInstanceState.getString(INTENT_KEY_CONTENT);
        }
        if (t != null) {
            this.title = t;
            this.setTitle(t);
        }
        if (c != null) {
            this.content = c;
            this.textView.setText(Html.fromHtml(c));
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (outState != null) {
            outState.putString(INTENT_KEY_TITLE, this.title);
            outState.putString(INTENT_KEY_CONTENT, this.content);
        }
    }
}
