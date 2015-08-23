package com.hkurokawa.qiitandroid.screens.main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hkurokawa.qiitandroid.QiitaApplication;
import com.hkurokawa.qiitandroid.R;
import com.hkurokawa.qiitandroid.domain.article.AnonymousArticle;
import com.hkurokawa.qiitandroid.domain.board.Board;
import com.hkurokawa.qiitandroid.domain.deck.Deck;
import com.hkurokawa.qiitandroid.domain.team.Team;
import com.hkurokawa.qiitandroid.screens.ActivityRouter;
import com.hkurokawa.qiitandroid.screens.Router;
import com.hkurokawa.qiitandroid.screens.board.anonymous.AnonymousBoardView;
import com.hkurokawa.qiitandroid.screens.login.LoginActivity;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MainScreen {
    private static final int REQ_LOGIN = 10;
    private MainPresenter presenter;
    private Router router;
    private ViewGroup root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.plant(new Timber.DebugTree());
        this.setContentView(R.layout.activity_main);

        this.root = (ViewGroup) this.findViewById(R.id.content);
        this.router = new ActivityRouter();
        this.presenter = new MainPresenter(((QiitaApplication)this.getApplication()).getApp());
        this.presenter.takeView(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        this.getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        final int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            this.presenter.onLoginRequested();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
            view.setBoard(board, this.router);
        } else {
            throw new IllegalArgumentException("Unknown item class type: " + board.getItemClass());
        }
    }

    @Override
    public void moveToLoginScreen() {
        this.startActivityForResult(new Intent(this, LoginActivity.class), REQ_LOGIN);
    }

    private void replaceView(View view) {
        this.root.removeAllViews();
        this.root.addView(view);
    }
}
