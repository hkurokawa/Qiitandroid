package com.hkurokawa.qiitandroid.screens.main;

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

import timber.log.Timber;

public class MainActivity extends AppCompatActivity implements MainScreen {
    private MainPresenter mainPresenter;
    private Router router;
    private ViewGroup root;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Timber.plant(new Timber.DebugTree());
        this.setContentView(R.layout.activity_main);

        this.root = (ViewGroup) this.findViewById(R.id.content);
        this.router = new ActivityRouter();
        this.mainPresenter = new MainPresenter(((QiitaApplication)this.getApplication()).getApp(), this.router);
        this.mainPresenter.takeView(this);
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
            this.mainPresenter.onLoginRequested();
            return true;
        }

        return super.onOptionsItemSelected(item);
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

    private void replaceView(View view) {
        this.root.removeAllViews();
        this.root.addView(view);
    }
}
