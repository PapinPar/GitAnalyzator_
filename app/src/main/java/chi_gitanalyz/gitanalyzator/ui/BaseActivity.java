package chi_gitanalyz.gitanalyzator.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import chi_gitanalyz.gitanalyzator.core.CApplication;
import chi_gitanalyz.gitanalyzator.core.api.App;
import chi_gitanalyz.gitanalyzator.core.api.I_Net;
import chi_gitanalyz.gitanalyzator.core.observer.NetSubscriber;

/**
 * Created by Papin on 23.09.2016.
 */

public class BaseActivity extends AppCompatActivity implements NetSubscriber {

    protected App app;

    @Override
    protected void onStart() {
        super.onStart();
        if(!app.getNet().IsSubscribe(this))
            app.getNet().Subscribe(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        app.getNet().UnSubscribe(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        app = CApplication.getApp(this);
    }

    @Override
    public void onNetRequestDone(@I_Net.NetEvent int evetId, Object NetObjects) {

    }

    @Override
    public void onNetRequestFail(@I_Net.NetEvent int evetId, Object NetObjects) {

    }
}
