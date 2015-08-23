package com.hkurokawa.qiitandroid.screens.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.hkurokawa.qiitandroid.QiitaApplication;
import com.hkurokawa.qiitandroid.R;
import com.hkurokawa.qiitandroid.domain.article.AnonymousArticle;
import com.hkurokawa.qiitandroid.domain.article.PerceivedArticle;
import com.hkurokawa.qiitandroid.domain.board.Board;
import com.hkurokawa.qiitandroid.domain.deck.Deck;
import com.hkurokawa.qiitandroid.domain.team.Team;
import com.hkurokawa.qiitandroid.screens.ActivityRouter;
import com.hkurokawa.qiitandroid.screens.Router;
import com.hkurokawa.qiitandroid.screens.board.anonymous.AnonymousBoardView;
import com.hkurokawa.qiitandroid.screens.board.perceived.PerceivedBoardView;
import com.hkurokawa.qiitandroid.screens.login.LoginActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnItemClick;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MainScreen {
    private static final int REQ_LOGIN = 10;
    private MainPresenter presenter;
    @InjectView(R.id.content)
    ViewGroup root;
    @InjectView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @InjectView(R.id.left_drawer)
    ListView drawerList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.plant(new Timber.DebugTree());
        this.setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        this.drawerList.setAdapter(new ArrayAdapter<>(this, R.layout.item_drawer_list, R.id.item_title, new String[]{"Login"}));
        this.presenter = new MainPresenter(((QiitaApplication)this.getApplication()).getApp());
        this.presenter.takeView(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQ_LOGIN:
                this.presenter.onLoginFinished(resultCode == Activity.RESULT_OK);
                return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void publish(Deck deck) {
        final Team team = deck.getCurrentTeam();
        final Board board = team.getBoards().get(0);
        if (board.getItemClass() == AnonymousArticle.class) {
            final AnonymousBoardView view = new AnonymousBoardView(this);
            this.replaceView(view);
            view.setBoard(board);
        } else if (board.getItemClass() == PerceivedArticle.class) {
            final PerceivedBoardView view = new PerceivedBoardView(this);
            this.replaceView(view);
            view.setBoard(board);
        } else {
            throw new IllegalArgumentException("Unknown item class type: " + board.getItemClass());
        }
    }

    @Override
    public void moveToLoginScreen() {
        this.startActivityForResult(new Intent(this, LoginActivity.class), REQ_LOGIN);
    }

    @OnItemClick(R.id.left_drawer)
    @SuppressWarnings("unused")
    void onClickDrawerItem(ListView list, View view, int pos, long id) {
        switch (pos) {
            case 0:
                this.drawerList.setItemChecked(pos, true);
                this.drawerLayout.closeDrawer(this.drawerList);
                this.presenter.onLoginRequested();
        }
    }

    private void replaceView(View view) {
        this.root.removeAllViews();
        this.root.addView(view);
    }
}
