package chi_gitanalyz.gitanalyzator.core;

import android.app.Application;
import android.content.Context;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import chi_gitanalyz.gitanalyzator.core.api.App;

/**
 * Created by Papin on 20.09.2016.
 */
public class CApplication extends Application implements App {

    private Executor executor;

    @Override
    public void onCreate() {
        super.onCreate();
        executor = Executors.newFixedThreadPool(2);
    }

    public Executor getExecutor() {
        return executor;
    }

    public static App getApp(Context context) {
        return (App) context.getApplicationContext();
    }

}
