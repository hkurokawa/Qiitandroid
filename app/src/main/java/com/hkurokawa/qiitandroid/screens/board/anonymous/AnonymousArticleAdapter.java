package com.hkurokawa.qiitandroid.screens.board.anonymous;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hkurokawa.qiitandroid.databinding.ItemAnonymousArticleBinding;
import com.hkurokawa.qiitandroid.domain.article.AnonymousArticle;

import java.util.ArrayList;
import java.util.List;

/**
 * An Adapter to display a list of {@link AnonymousArticle}s.
 * Created by hiroshi on 2015/06/10.
 */
public class AnonymousArticleAdapter extends RecyclerView.Adapter<AnonymousArticleAdapter.ViewHolder> {
    private static final int VIEW_TYPE_FOOTER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private List<AnonymousArticle> articles = new ArrayList<>();
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
                final ItemAnonymousArticleBinding binding = ItemAnonymousArticleBinding.inflate(inflater);
                return new ItemViewHolder(binding);
            default:
                throw new IllegalArgumentException("Invalid view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder instanceof ItemViewHolder) {
            final AnonymousArticle a = this.articles.get(position);
            final ItemViewHolder h = ((ItemViewHolder) holder);
            h.binding.setArticle(a);
            h.setOnClickListener(v -> {
                if (AnonymousArticleAdapter.this.listener != null) {
                    AnonymousArticleAdapter.this.listener.onItemClick(a, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return this.articles.size() + (this.footerView != null ? 1 : 0);
    }

    @Override
    public long getItemId(int position) {
        if (position >= this.articles.size()) {
            return -1L;
        } else {
            return this.articles.get(position).getId();
        }
    }

    public void set(List<AnonymousArticle> articles) {
        this.articles = articles;
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(AnonymousArticle article, int position);
    }

    public abstract static class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    private static class ItemViewHolder extends ViewHolder {
        private ItemAnonymousArticleBinding binding;

        public ItemViewHolder(ItemAnonymousArticleBinding binding) {
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
