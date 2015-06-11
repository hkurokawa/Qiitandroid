package com.hkurokawa.qiitandroid.model;

import com.squareup.moshi.JsonReader;
import com.squareup.moshi.JsonWriter;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * JSON serializer/deserializer for Date in RFC 822 format.
 * Created by hiroshi on 2015/06/11.
 */
public class RFC822DateAdapter extends com.squareup.moshi.JsonAdapter<Date> {
    private static final SimpleDateFormat RFC822FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss z", Locale.US);

    @Override
    public Date fromJson(JsonReader reader) throws IOException {
        final String str = reader.nextString();
        try {
            return RFC822FORMAT.parse(str);
        } catch (ParseException e) {
            throw new IOException("Failed to parse [" + str + "] as RFC 822 Date.", e);
        }
    }

    @Override
    public void toJson(JsonWriter writer, Date value) throws IOException {
        final String str = RFC822FORMAT.format(value);
        writer.value(str);
    }
}
