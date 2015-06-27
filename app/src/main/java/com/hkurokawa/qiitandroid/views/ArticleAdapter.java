package com.hkurokawa.qiitandroid.views;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.hkurokawa.qiitandroid.databinding.ItemArticleBinding;
import com.hkurokawa.qiitandroid.model.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * An ArrayAdapter to display a list of {@link com.hkurokawa.qiitandroid.model.Article}s.
 * Created by hiroshi on 2015/06/10.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private List<Article> articles;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ItemArticleBinding binding = ItemArticleBinding.inflate(LayoutInflater.from(parent.getContext()));
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Article a = this.articles.get(position);
        holder.binding.setArticle(a);
    }

    @Override
    public int getItemCount() {
        return this.articles == null ? 0 : this.articles.size();
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public void add(Article article) {
        if (this.articles == null) {
            this.articles = new ArrayList<>();
        }
        this.articles.add(article);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ItemArticleBinding binding;
        public ViewHolder(ItemArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
