package com.hkurokawa.qiitandroid.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.hkurokawa.qiitandroid.databinding.ItemArticleBinding;
import com.hkurokawa.qiitandroid.model.Article;

/**
 * An ArrayAdapter to display a list of {@link com.hkurokawa.qiitandroid.model.Article}s.
 * Created by hiroshi on 2015/06/10.
 */
public class ArticleAdapter extends ArrayAdapter<Article> {

    public ArticleAdapter(Context context) {
        super(context, -1);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            final ItemArticleBinding binding = ItemArticleBinding.inflate(LayoutInflater.from(this.getContext()));
            convertView = binding.getRoot();
            convertView.setTag(binding);
        }

        final ItemArticleBinding binding = (ItemArticleBinding) convertView.getTag();
        final Article article = this.getItem(position);
        binding.setArticle(article);
        return convertView;
    }
}
