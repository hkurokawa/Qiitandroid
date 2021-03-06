package com.hkurokawa.qiitandroid.screens.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.hkurokawa.qiitandroid.QiitaApplication;
import com.hkurokawa.qiitandroid.R;
import com.hkurokawa.qiitandroid.network.AuthToken;

import java.util.EnumSet;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class LoginActivity extends AppCompatActivity implements LoginScreen, OAuthWebViewClient.Listener {
    private static LoginPresenter presenter;

    @InjectView(R.id.login_webview)
    WebView webView;
    @InjectView(R.id.login_progressbar)
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_login);

        ButterKnife.inject(this);

        final WebSettings webSettings = webView.getSettings();
        webSettings.setAllowFileAccess(false);
        webSettings.setJavaScriptEnabled(false);
        webSettings.setSaveFormData(false);
        this.webView.setVerticalScrollBarEnabled(false);
        this.webView.setHorizontalScrollBarEnabled(false);
        final OAuthWebViewClient webViewClient = new OAuthWebViewClient();
        webViewClient.setListener(this);
        this.webView.setWebViewClient(webViewClient);
        this.webView.setWebViewClient(webViewClient);

        if (presenter == null) {
            presenter = new LoginPresenter(
                    ((QiitaApplication)this.getApplication()).getApp(),
                    this.getString(R.string.qiita_client_id),
                    this.getString(R.string.qiita_client_secret),
                    EnumSet.of(AuthToken.Scope.READ_QIITA, AuthToken.Scope.READ_QIITA_TEAM),
                    this.getString(R.string.qiita_callback_url));
        }
        presenter.takeView(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        presenter.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.takeView(null);
        if (this.isFinishing()) {
            presenter = null;
        }
    }

    @Override
    public void setResult(boolean success) {
        this.setResult(success ? RESULT_OK : RESULT_CANCELED);
    }

    @Override
    public void loadRequestUrl(String url) {
        this.webView.loadUrl(url);
    }

    @Override
    public void showWebView(boolean show) {
        this.webView.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void showProgressBar(boolean show) {
        this.progressBar.setVisibility(show ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void close() {
        this.finish();
    }

    @Override
    public void onPageFinished(WebView webView, String url) {
        presenter.onPageLoaded(url);
    }

    @Override
    public boolean onRedirect(WebView view, String url) {
        return presenter.onRedirect(url);
    }

    @Override
    public void onError(WebViewException error) {
        presenter.onPageError(error);
    }
}
