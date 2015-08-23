package com.hkurokawa.qiitandroid.screens.board.perceived;

import android.view.LayoutInflater;
import android.view.View;

import com.hkurokawa.qiitandroid.databinding.ItemPerceivedArticleBinding;
import com.hkurokawa.qiitandroid.domain.article.PerceivedArticle;
import com.hkurokawa.qiitandroid.screens.board.BoardListAdapter;
import com.hkurokawa.qiitandroid.screens.board.ViewHolder;

/**
 * An Adapter to display a list of {@link PerceivedArticle}s.
 * Created by hiroshi on 8/23/15.
 */
public class PerceivedArticleAdapter extends BoardListAdapter<PerceivedArticle> {
    @Override
    protected ViewHolder.ItemViewHolder createItemViewHolder(LayoutInflater inflater) {
        ItemPerceivedArticleBinding binding = ItemPerceivedArticleBinding.inflate(inflater);
        return new PerceivedArticleViewHolder(binding);
    }

    private static class PerceivedArticleViewHolder extends ViewHolder.ItemViewHolder<PerceivedArticle> {
        private ItemPerceivedArticleBinding binding;

        public PerceivedArticleViewHolder(ItemPerceivedArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void setItem(PerceivedArticle article) {
            this.binding.setArticle(article);
        }

        @Override
        public void setOnClickListener(View.OnClickListener listener) {
            this.binding.getRoot().setOnClickListener(listener);
        }
    }
}
