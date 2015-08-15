package com.hkurokawa.qiitandroid.model;

/**
 * Represents a request for /api/v2/access_tokens
 * Created by hiroshi on 8/15/15.
 */
public class AccessTokensRequest {
    private String clientId;
    private String clientSecret;
    private String code;

    public AccessTokensRequest(String clientId, String clientSecret, String code) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.code = code;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
