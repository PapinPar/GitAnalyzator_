package chi_gitanalyz.gitanalyzator.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.core.api.I_Db;
import chi_gitanalyz.gitanalyzator.core.api.I_Net;
import chi_gitanalyz.gitanalyzator.db.sqlite.model.Manager;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.Project;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.Projects;

/**
 * Created by Papin on 26.09.2016.
 */

public class ProjectsActivity extends BaseActivity {

    String TOKEN = "_NULL_";
    String TOKEN_ID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_layout);

        Intent intent = getIntent();
        TOKEN_ID = intent.getStringExtra("TOKEN_ID");

        loadProjects();


    }

    private void loadProjects() {
        app.getNet().projectList(TOKEN_ID);
    }

    @Override
    public void onDbDataUpdated(@I_Db.DbEvent int eventId, Object dbObject) {
        switch (eventId) {
            case I_Db.USER_LOAD:
                dbLoadUser((Manager) dbObject);
        }
    }


    private void dbLoadUser(Manager manager) {
        TOKEN = manager.getToken();
        if (!TOKEN.equals("_NULL_")) {
            app.getNet().signOUT(TOKEN);
            app.getDb().deleteUser();
        }
        Log.d("DB", TOKEN);
    }


    //     *************************************** NET ***************************************
    @Override
    public void onNetRequestDone(@I_Net.NetEvent int evetId, Object NetObjects) {
        switch (evetId) {
            case I_Net.Sign_OUT:
                signOutSucces();
                break;
            case I_Net.PROJECT_LIST:
                fillList((Projects) NetObjects);
        }

    }

    private void fillList(Projects projectsList) {
                List<Project> s = projectsList.getProjects();
        Log.d("Project","Project" + s);
        Log.d("Project","Project SSH" + s.get(1).getSsh());
}

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "You selected Sign OUT", Toast.LENGTH_SHORT).show();
                app.getDb().loadUser();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOutSucces() {
        finish();
    }
}
