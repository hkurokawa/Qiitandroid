package com.hkurokawa.qiitandroid;

import android.net.Uri;
import android.util.Log;

import com.hkurokawa.qiitandroid.model.AccessTokensRequest;
import com.hkurokawa.qiitandroid.model.AuthToken;

import java.util.EnumSet;
import java.util.Locale;
import java.util.UUID;

import rx.functions.Action1;
import timber.log.Timber;

/**
 * Presentation logic for login panel.
 * Created by hiroshi on 8/16/15.
 */
public class LoginPresenter extends Presenter {
    private final String clientId;
    private final String clientSecret;
    private final String callbackUrl;
    private final EnumSet<AuthToken.Scope> scopes;
    private LoginScreen screen;
    private String state;

    public LoginPresenter(String clientId, String clientSecret, EnumSet<AuthToken.Scope> scopes, String callbackUrl) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.scopes = scopes;
        this.callbackUrl = callbackUrl;
    }

    public void takeView(LoginScreen screen) {
        this.screen = screen;
        this.login();
    }

    private void login() {
        if (this.screen != null) {
            this.state = UUID.randomUUID().toString();
            Uri uri = new Uri.Builder().scheme("http").authority("qiita.com").path("/api/v2/oauth/authorize")
                    .appendQueryParameter("client_id", this.clientId)
                    .appendQueryParameter("scope", stringify(this.scopes))
                    .appendQueryParameter("state", this.state)
                    .build();
            this.screen.loadRequestUrl(uri.toString());
        }
    }

    private static String stringify(EnumSet<AuthToken.Scope> scopes) {
        final StringBuilder sb = new StringBuilder();
        for (AuthToken.Scope sc : scopes) {
            sb.append(sc.name().toLowerCase(Locale.US)).append("+");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    public void onPageLoaded(String url) {
        if (this.screen != null) {
            this.screen.showWebView(true);
            this.screen.showProgressBar(false);
        }
    }

    public boolean onRedirect(String url) {
        if (url.startsWith(this.callbackUrl)) {
            final Uri uri = Uri.parse(url);
            final String code = uri.getQueryParameter("code");
            final String state = uri.getQueryParameter("state");
            if (this.state.equals(state)) {
                this.accessRequestSuccess(code);
                return true;
            }
            this.accessRequestFailure();
        }
        return false;
    }

    private void accessRequestSuccess(String code) {
        final AccessTokensRequest request = new AccessTokensRequest(this.clientId, this.clientSecret, code);
        QiitaApi.createV2().accessTokens(request).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Timber.e("Failed to get access token.", throwable);
            }
        }).subscribe(new Action1<AuthToken>() {
            @Override
            public void call(AuthToken authToken) {
                final String token = authToken.getToken();
                Timber.d("token = [%s].", token);
                // TODO store auth token.
                if (LoginPresenter.this.screen != null) {
                    LoginPresenter.this.screen.close();
                }
            }
        });
    }

    private void accessRequestFailure() {

    }

    public void onPageError(WebViewException error) {

    }
}
