package com.hkurokawa.qiitandroid.screens.board;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hkurokawa.qiitandroid.domain.article.ListItem;

import java.util.ArrayList;
import java.util.List;

/**
 * PagingListAdapter specifies a behaviour required on BoardView.
 * Created by hiroshi on 8/23/15.
 */
public abstract class BoardListAdapter<T extends ListItem> extends RecyclerView.Adapter<ViewHolder> {
    private static final int VIEW_TYPE_FOOTER = 0;
    private static final int VIEW_TYPE_ITEM = 1;
    private List<T> articles = new ArrayList<>();
    private View footerView;
    private OnBoardListItemClickListener<T> listener;

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
                return this.createItemViewHolder(inflater);
            default:
                throw new IllegalArgumentException("Invalid view type: " + viewType);
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        if (holder instanceof ViewHolder.ItemViewHolder) {
            final T a = this.articles.get(position);
            final ViewHolder.ItemViewHolder<T> h = ((ViewHolder.ItemViewHolder) holder);
            h.setItem(a);
            h.setOnClickListener(v -> {
                if (BoardListAdapter.this.listener != null) {
                    BoardListAdapter.this.listener.onItemClick(a, position);
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

    public void set(List<T> articles) {
        this.articles = articles;
    }

    public void setFooterView(View footerView) {
        this.footerView = footerView;
    }

    public void setOnItemClickListener(OnBoardListItemClickListener<T> listener) {
        this.listener = listener;
    }

    private static class FooterViewHolder extends ViewHolder {
        public FooterViewHolder(View footer) {
            super(footer);
        }
    }

    protected abstract ViewHolder.ItemViewHolder createItemViewHolder(LayoutInflater inflater);
}
