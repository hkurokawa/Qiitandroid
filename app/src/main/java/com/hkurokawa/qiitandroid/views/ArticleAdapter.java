package com.hkurokawa.qiitandroid.views;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hkurokawa.qiitandroid.databinding.ItemArticleBinding;
import com.hkurokawa.qiitandroid.model.Article;

import java.util.ArrayList;
import java.util.List;

/**
 * An ArrayAdapter to display a list of {@link com.hkurokawa.qiitandroid.model.Article}s.
 * Created by hiroshi on 2015/06/10.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private static final int VIEW_TYPE_FOOTER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private List<Article> articles = new ArrayList<>();
    private View footerView;

    @Override
    public int getItemViewType(int position) {
        if (position >= this.articles.size()) {
            return VIEW_TYPE_FOOTER;
        }
        return VIEW_TYPE_ITEM;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        switch (viewType) {
            case VIEW_TYPE_FOOTER:
                return new FooterViewHolder(this.footerView);
            case VIEW_TYPE_ITEM:
                final ItemArticleBinding binding = ItemArticleBinding.inflate(inflater);
                return new ItemViewHolder(binding);
            default:
                throw new IllegalArgumentException("Invalid view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            final Article a = this.articles.get(position);
            ((ItemViewHolder) holder).binding.setArticle(a);
        }
    }

    @Override
    public int getItemCount() {
        return this.articles.size() + (this.footerView != null ? 1 : 0);
    }

    public void add(Article article) {
        if (this.articles == null) {
            this.articles = new ArrayList<>();
        }
        this.articles.add(article);
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
    }

    public abstract static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class ItemViewHolder extends ViewHolder {
        private ItemArticleBinding binding;

        public ItemViewHolder(ItemArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private static class FooterViewHolder extends ViewHolder {
        public FooterViewHolder(View footer) {
            super(footer);
        }
    }
}
