package chi_gitanalyz.gitanalyzator.core;

import android.app.Application;
import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import chi_gitanalyz.gitanalyzator.core.api.App;
import chi_gitanalyz.gitanalyzator.core.api.Db;
import chi_gitanalyz.gitanalyzator.core.api.Net;
import chi_gitanalyz.gitanalyzator.db.sqlite.SqliteManager;
import chi_gitanalyz.gitanalyzator.retrofit.ConnectionManager;

/**
 * Created by Papin on 20.09.2016.
 */
public class GitAplication extends Application implements App {

    private Db db;
    private Executor executor;
    private Net net;

    @Override
    public void onCreate() {
        super.onCreate();
        executor = Executors.newFixedThreadPool(2);
        db = new SqliteManager(this);
        net = new ConnectionManager(executor);
    }

    @Override
    public Net getNet() {
        return net;
    }

    public Db getDb(){return db;}

    public Executor getExecutor() {
        return executor;
    }

    public static App getApp(Context context) {
        return (App) context.getApplicationContext();
    }

}
