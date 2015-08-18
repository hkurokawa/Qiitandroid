package com.hkurokawa.qiitandroid.screens.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hkurokawa.qiitandroid.domain.article.Article;
import com.hkurokawa.qiitandroid.databinding.ItemArticleBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * An ArrayAdapter to display a list of {@link Article}s.
 * Created by hiroshi on 2015/06/10.
 */
public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder> {
    private static final int VIEW_TYPE_FOOTER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private List<Article> articles = new ArrayList<>();
    private View footerView;
    private OnItemClickListener listener;

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
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final Article a = this.articles.get(position);
            final ItemViewHolder h = ((ItemViewHolder) holder);
            h.binding.setArticle(a);
            h.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ArticleAdapter.this.listener != null) {
                        ArticleAdapter.this.listener.onItemClick(a, position);
                    }
                }
            });
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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public static interface OnItemClickListener {
        public void onItemClick(Article article, int position);
    }

    public abstract static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class ItemViewHolder extends ViewHolder {
        private ItemArticleBinding binding;

        public ItemViewHolder(ItemArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setOnClickListener(View.OnClickListener listener) {
            this.binding.getRoot().setOnClickListener(listener);
        }
    }

    private static class FooterViewHolder extends ViewHolder {
        public FooterViewHolder(View footer) {
            super(footer);
        }
    }
}
