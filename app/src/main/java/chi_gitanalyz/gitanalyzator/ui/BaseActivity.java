package chi_gitanalyz.gitanalyzator.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import chi_gitanalyz.gitanalyzator.core.CApplication;
import chi_gitanalyz.gitanalyzator.core.api.App;
import chi_gitanalyz.gitanalyzator.core.api.I_Db;
import chi_gitanalyz.gitanalyzator.core.api.I_Net;
import chi_gitanalyz.gitanalyzator.core.observer.DbSubscriber;
import chi_gitanalyz.gitanalyzator.core.observer.NetSubscriber;
import chi_gitanalyz.gitanalyzator.db.sqlite.model.DbError;

/**
 * Created by Papin on 23.09.2016.
 */

public class BaseActivity extends AppCompatActivity implements DbSubscriber,NetSubscriber {

    protected App app;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = CApplication.getApp(this);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!app.getNet().IsSubscribe(this))
            app.getNet().Subscribe(this);

        if (!app.getDb().IsSubscribe(this))
            app.getNet().Subscribe(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        app.getNet().UnSubscribe(this);
        app.getDb().UnSubscribe(this);
    }

    @Override
    public void onNetRequestDone(@I_Net.NetEvent int evetId, Object NetObjects) {

    }

    @Override
    public void onNetRequestFail(@I_Net.NetEvent int evetId, Object NetObjects) {

    }

    @Override
    public void onDbDataUpdated(@I_Db.DbEvent int tableId, Object dbObject) {

    }

    @Override
    public void onDbErrorError(@I_Db.DbEvent int tableId, Object error) {
        DbError dbError = (DbError) error;
        toast(dbError.getMessage());
    }

    protected void toast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
