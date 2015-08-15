package com.hkurokawa.qiitandroid.network;

import com.hkurokawa.qiitandroid.model.AccessTokensRequest;
import com.hkurokawa.qiitandroid.model.Article;
import com.hkurokawa.qiitandroid.model.AuthToken;

import java.util.List;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;
import rx.Observable;

/**
 * QiitaApiV1 represents a REST interface for Qiita API v1 (https://qiita.com/api/v1/docs).
 * Created by hiroshi on 2015/06/09.
 */
public interface QiitaApiV1 {
    @GET("/api/v1/items")
    Observable<List<Article>> items(@Query("page") int page);
}
