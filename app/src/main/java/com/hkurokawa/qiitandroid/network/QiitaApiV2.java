package com.hkurokawa.qiitandroid.network;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import rx.Observable;

/**
 * QiitaApiV2 represents a REST interface for Qiita API v2 (https://qiita.com/api/v2/docs).
 * Created by hiroshi on 8/16/15.
 */
public interface QiitaApiV2 {
    @POST("/api/v2/access_tokens")
    AuthToken accessTokens(@Body AccessTokensRequest request);

    @GET("/api/v2/authenticated_user")
    User authenticatedUser(@Header("Authorization") String authToken);
}
