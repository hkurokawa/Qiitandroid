package com.hkurokawa.qiitandroid.screens.login;

import com.hkurokawa.qiitandroid.domain.application.Application;
import com.hkurokawa.qiitandroid.domain.user.User;
import com.hkurokawa.qiitandroid.network.AccessTokensRequest;
import com.hkurokawa.qiitandroid.network.AuthToken;
import com.hkurokawa.qiitandroid.network.QiitaApi;
import com.hkurokawa.qiitandroid.network.QiitaApiV2;
import com.hkurokawa.qiitandroid.screens.Presenter;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import rx.Observable;
import rx.Observer;
import rx.functions.Consumer;
import rx.schedulers.NewThreadScheduler;

/**
 * Presentation logic for login panel.
 * Created by hiroshi on 8/16/15.
 */
public class LoginPresenter extends Presenter {
    private final Application app;
    private final String clientId;
    private final String clientSecret;
    private final String callbackUrl;
    private final EnumSet<AuthToken.Scope> scopes;
    private LoginScreen screen;
    private String state;

    public LoginPresenter(Application app, String clientId, String clientSecret, EnumSet<AuthToken.Scope> scopes, String callbackUrl) {
        this.app = app;
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
            final Map<String, String> params = new HashMap<>();
            params.put("client_id", this.clientId);
            params.put("scope", stringify(this.scopes));
            params.put("state", this.state);
            final URI uri;
            try {
                uri = new URI("http", "qiita.com", "/api/v2/oauth/authorize", buildURLQuery(params), null);
                // TODO should use Android independent logger
                System.out.println("Authorize URL: [" + uri.toString() + "].");
                this.screen.loadRequestUrl(uri.toString());
            } catch (Exception e) {
                System.err.println("Failed to build URI: " + e.getMessage());
                //TODO show an alert message to user
            }
        }
    }

    private static String stringify(EnumSet<AuthToken.Scope> scopes) {
        final StringBuilder sb = new StringBuilder();
        for (AuthToken.Scope sc : scopes) {
            sb.append(sc.name().toLowerCase(Locale.US)).append(" ");
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
            try {
                final Map<String, String> params = parseURLQuery(url);
                final String code = params.get("code");
                final String state = params.get("state");
                if (code == null || state == null) {
                    throw new IllegalArgumentException("The required parameter 'code' or 'state' does not exist: " + url);
                }
                if (this.state.equals(state)) {
                    this.accessRequestSuccess(code);
                    return true;
                }
            } catch (Exception e) {
                e.printStackTrace();
                System.err.println("Failed to parse the redirect URL: " + e.getMessage());
            }
            this.accessRequestFailure();
        }
        return false;
    }

    private void accessRequestSuccess(String code) {
        final AccessTokensRequest request = new AccessTokensRequest(this.clientId, this.clientSecret, code);
        final QiitaApiV2 api = QiitaApi.createV2();
        Observable.create(new Consumer<Observer<? super User>>() {
            @Override
            public void accept(Observer<? super User> observer) {
                final String token;
                final com.hkurokawa.qiitandroid.network.User user;
                try {
                    final AuthToken authToken = api.accessTokens(request);
                    token = authToken.getToken();
                    // TODO should use Android independent logger
                    System.out.println("token = [" + token + "].");
                    user = api.authenticatedUser("Bearer " + token);
                    observer.onNext(new User(user.getUrlName(), user.getProfileImageUrl(), token));
                } catch (Exception e) {
                    observer.onError(e);
                    //TODO show an alert message to user
                    observer.onCompleted();
                } finally {
                    observer.onCompleted();
                }
            }
        }).subscribeOn(new NewThreadScheduler()).subscribe(LoginPresenter.this.app::setUser, throwable -> System.err.println("Failed to authenticate the user: " + throwable.getMessage()), this::close);
    }

    private void close() {
        if (LoginPresenter.this.screen != null) {
            LoginPresenter.this.screen.close();
        }
    }

    private void accessRequestFailure() {
        //TODO show an alert message to user
    }

    public void onPageError(WebViewException error) {
        //TODO show an alert message to user
    }

    private static String buildURLQuery(Map<String, String> params) throws UnsupportedEncodingException {
        final StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> e : params.entrySet()) {
            final String key = e.getKey();
            final String value = e.getValue();
            sb.append(URLEncoder.encode(key, "UTF-8"));
            if (value != null) {
                sb.append("=").append(URLEncoder.encode(value, "UTF-8"));
            }
            sb.append("&");
        }
        if (sb.length() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    // If there are more than one key-value pairs of which key name is same, it throws an error as it is not as expected.
    private static Map<String, String> parseURLQuery(String url) throws URISyntaxException, UnsupportedEncodingException {
        final String[] pairs = new URI(url).getQuery().split("&");
        final Map<String, String> ret = new HashMap<>();
        for (String p : pairs) {
            final String key, value;
            final int idx = p.indexOf('=');
            if (idx > 0) {
                key = URLDecoder.decode(p.substring(0, idx), "UTF-8");
                value = URLDecoder.decode(p.substring(idx + 1), "UTF-8");
            } else {
                key = p;
                value = null;
            }
            ret.put(key, value);
        }
        return ret;
    }
}
