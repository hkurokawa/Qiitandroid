package com.hkurokawa.qiitandroid.network;

import com.squareup.moshi.JsonQualifier;

import java.lang.annotation.Retention;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * An annotation to represent a Date in RFC 822 format.
 * Created by hiroshi on 2015/06/11.
 */
@Retention(RUNTIME)
@JsonQualifier
public @interface RFC822 {
}
