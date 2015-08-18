package com.hkurokawa.qiitandroid;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hkurokawa.qiitandroid.network.QiitaApiV1;
import com.hkurokawa.qiitandroid.network.QiitaApiV2;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * A builder for Qiita API.
 * Created by hiroshi on 8/16/15.
 */
public class QiitaApi {
    public static QiitaApiV1 createV1() {
        final RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint("https://qiita.com");
        if (BuildConfig.DEBUG) {
            builder.setLogLevel(RestAdapter.LogLevel.BASIC);
        }
        final Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd HH:mm:ss z") // RFC 822 format
                .create();
        builder.setConverter(new GsonConverter(gson));

        return builder.build().create(QiitaApiV1.class);
    }

    public static QiitaApiV2 createV2() {
        final RestAdapter.Builder builder = new RestAdapter.Builder().setEndpoint("https://qiita.com");
        if (BuildConfig.DEBUG) {
            builder.setLogLevel(RestAdapter.LogLevel.FULL);
        }
        final Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd HH:mm:ss z") // RFC 822 format
                .create();
        builder.setConverter(new GsonConverter(gson));

        return builder.build().create(QiitaApiV2.class);
    }
}
