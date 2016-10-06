package chi_gitanalyz.gitanalyzator.db.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import chi_gitanalyz.gitanalyzator.core.GitAplication;
import chi_gitanalyz.gitanalyzator.core.api.App;
import chi_gitanalyz.gitanalyzator.core.api.Db;
import chi_gitanalyz.gitanalyzator.core.observer.DbSubscriber;
import chi_gitanalyz.gitanalyzator.db.sqlite.entity.ManagerEntity;
import chi_gitanalyz.gitanalyzator.db.sqlite.mapping.ManagerMapper;
import chi_gitanalyz.gitanalyzator.db.sqlite.model.DbError;
import chi_gitanalyz.gitanalyzator.db.sqlite.model.Manager;

/**
 * Created by Papin on 20.09.2016.
 */
public class SqliteManager implements Db {
    private final List<DbSubscriber> observers = new ArrayList<>();
    private final SQLiteDatabase database;
    private final Handler handler;
    private final Executor executor;

    public SqliteManager(Context context) {
        App app = GitAplication.getApp(context);
        SQLiteOpenHelper openHelper = new OpenHelper(context);
        database = openHelper.getWritableDatabase();
        handler = new Handler(Looper.getMainLooper());
        executor = app.getExecutor();
    }


    @Override
    public void saveUser(final Manager user) {
        executor.execute(() -> {
            long id = database.insert(ManagerEntity.TABLE_NAME, null, ManagerMapper.parse(user));
            if (id != -1) {
                Log.d("DB", "DataBaseVersion" + id);
                handler.post(() -> notifySuccessSubscribers(Db.USER_UPD, user));
            } else {
                DbError dbError = new DbError();
                dbError.setMessage("Insert method return -1");
                handler.post(() -> notifyErrorSubscribers(Db.USER_UPD, dbError));
            }
        });
    }

    @Override
    public void deleteUser() {
        executor.execute(() ->
                {
                    database.delete(ManagerEntity.TABLE_NAME, null, null);
                    handler.post(() -> notifySuccessSubscribers(Db.USER_DELETE, null));
                }
        );
    }

    @Override
    public void loadUser() {
        executor.execute(() -> {
                    Cursor cursor = database.query(ManagerEntity.TABLE_NAME, null, null, null, null, null, null);
                    if (cursor != null) {
                        Manager manager = ManagerMapper.parse(cursor);
                        cursor.close();
                        handler.post(() -> notifySuccessSubscribers(Db.USER_LOAD, manager));
                    } else {
                        DbError dbError = new DbError();
                        dbError.setMessage("Cursor null");
                        handler.post(() -> notifyErrorSubscribers(Db.USER_LOAD, dbError));
                    }
                }
        );

    }

    @Override
    public void Subscribe(DbSubscriber observer) {
        if (!observers.contains(observer))
            observers.add(observer);
    }

    @Override
    public void UnSubscribe(DbSubscriber observer) {
        if (observers.contains(observer))
            observers.remove(observer);
    }

    @Override
    public boolean IsSubscribe(DbSubscriber observer) {
        return observers.contains(observer);
    }

    @Override
    public void notifySuccessSubscribers(int eventId, Object object) {
        for (DbSubscriber observer : observers)
            handler.post(new Runnable() {
                @Override
                public void run() {
                    observer.onDbDataUpdated(eventId, object);
                }
            });


    }

    @Override
    public void notifyErrorSubscribers(int eventId, Object object) {
        for (DbSubscriber observer : observers) {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    observer.onDbErrorError(eventId, object);
                }
            });
        }
    }


}
