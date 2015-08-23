package com.hkurokawa.qiitandroid.screens.board;

/**
 * A listener for board list.
 * Created by hiroshi on 8/23/15.
 */
public interface OnBoardListItemClickListener<T> {
    void onItemClick(T item, int position);
}
