package com.hkurokawa.qiitandroid.screens.board.anonymous;

import android.view.LayoutInflater;
import android.view.View;

import com.hkurokawa.qiitandroid.databinding.ItemAnonymousArticleBinding;
import com.hkurokawa.qiitandroid.domain.article.AnonymousArticle;
import com.hkurokawa.qiitandroid.screens.board.BoardListAdapter;
import com.hkurokawa.qiitandroid.screens.board.ViewHolder;

/**
 * An Adapter to display a list of {@link AnonymousArticle}s.
 * Created by hiroshi on 2015/06/10.
 */
public class AnonymousArticleAdapter extends BoardListAdapter<AnonymousArticle> {
    @Override
    protected ViewHolder.ItemViewHolder createItemViewHolder(LayoutInflater inflater) {
        final ItemAnonymousArticleBinding binding = ItemAnonymousArticleBinding.inflate(inflater);
        return new AnonymousArticleViewHolder(binding);
    }

    private static class AnonymousArticleViewHolder extends ViewHolder.ItemViewHolder<AnonymousArticle> {
        private ItemAnonymousArticleBinding binding;

        public AnonymousArticleViewHolder(ItemAnonymousArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        @Override
        public void setItem(AnonymousArticle article) {
            this.binding.setArticle(article);
        }

        @Override
        public void setOnClickListener(View.OnClickListener listener) {
            this.binding.getRoot().setOnClickListener(listener);
        }
    }
}
