package chi_gitanalyz.gitanalyzator.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.roger.catloadinglibrary.CatLoadingView;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.core.api.Db;
import chi_gitanalyz.gitanalyzator.core.api.Net;
import chi_gitanalyz.gitanalyzator.db.sqlite.model.Manager;
import chi_gitanalyz.gitanalyzator.retrofit.model.data.User;
import chi_gitanalyz.gitanalyzator.ui.project.ProjectsActivity;

/**
 * Created by Papin on 13.10.2016.
 */

public class Splach extends BaseActivity {

    private String token;
    private SharedPreferences sPref;
    CatLoadingView mView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.spalch_activity);
        mView = new CatLoadingView();
        mView.setCancelable(false);
        mView.show(getSupportFragmentManager(), "");
        app.getDb().loadUser();
    }

    @Override
    public boolean isNetworkConnected() {
        return super.isNetworkConnected();
    }

    @Override
    public void onNetRequestDone(@Net.NetEvent int evetId, Object NetObjects) {
        super.onNetRequestDone(evetId, NetObjects);
        switch (evetId) {
            case Net.VALIDATE_TOKEN:
                Success((User) NetObjects);
                break;
        }
    }

    private void Success(User netObjects) {
        mView.dismiss();
        Manager manager = new Manager();
        manager.setToken(token);
        app.getDb().saveUser(manager);
        Intent intent = new Intent(this, ProjectsActivity.class);
        sPref = getSharedPreferences("TOKENS",MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("tokenId", token);
        ed.putString("managerId", netObjects.getId().toString());
        ed.commit();
        startActivity(intent);
    }

    @Override
    public void onDbDataUpdated(@Db.DbEvent int eventID, Object dbObject) {
        super.onDbDataUpdated(eventID, dbObject);
        switch (eventID) {
            case Db.USER_LOAD:
                loaded((Manager) dbObject);
                break;
        }
    }

    private void loaded(Manager dbObject) {
        if (dbObject != null) {
            token = dbObject.getToken().toString();
            if (isNetworkConnected()) {
                app.getNet().validateToken(dbObject.getToken().toString());
            } else {
                Intent startMain = new Intent(this, MainActivity.class);
                startActivity(startMain);
                Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();
            }
        }
        else{ Intent startMain = new Intent(this, MainActivity.class);
            startActivity(startMain);}

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        finish();
    }
}
