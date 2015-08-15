package com.hkurokawa.qiitandroid.model;

/**
 * Represents an auth token returned with /api/v2/access_tokens.
 * Created by hiroshi on 8/15/15.
 */
public class AuthToken {
    private String clientId;
    private Scope[] scopes;
    private String token;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public Scope[] getScopes() {
        return scopes;
    }

    public void setScopes(Scope[] scopes) {
        this.scopes = scopes;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public enum Scope {
        READ_QIITA, READ_QIITA_TEAM, WRITE_QIITA, WRITE_QIITA_TEAM
    }
}
