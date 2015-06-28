package com.hkurokawa.qiitandroid.views;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * A collection of static methods annotated with {@link android.databinding.BindingAdapter}.
 * Created by hiroshi on 6/25/15.
 */
@SuppressWarnings("unused")
public class CustomBindings {
    @BindingAdapter({"bind:imageUrl", "bind:error"})
    public static void loadImage(ImageView view, String url, Drawable error) {
        Picasso.with(view.getContext()).load(url).transform(new CircleTransformation()).error(error).into(view);
    }
}
