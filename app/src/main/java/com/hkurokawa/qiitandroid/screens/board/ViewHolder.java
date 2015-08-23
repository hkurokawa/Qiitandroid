package com.hkurokawa.qiitandroid.screens.board;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.hkurokawa.qiitandroid.domain.article.ListItem;

/**
 * ViewHolder.
 * Created by hiroshi on 8/23/15.
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    public ViewHolder(View itemView) {
        super(itemView);
    }

    public abstract static class ItemViewHolder<T extends ListItem> extends ViewHolder {
        public ItemViewHolder(View itemView) {
            super(itemView);
        }

        public abstract void setItem(T item);
        public abstract void setOnClickListener(View.OnClickListener listener);
    }
}
