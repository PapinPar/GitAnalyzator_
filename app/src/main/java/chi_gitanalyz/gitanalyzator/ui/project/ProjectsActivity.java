package chi_gitanalyz.gitanalyzator.ui.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.core.api.I_Db;
import chi_gitanalyz.gitanalyzator.core.api.I_Net;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.Project;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.Projects;
import chi_gitanalyz.gitanalyzator.ui.BaseActivity;
import chi_gitanalyz.gitanalyzator.ui.CreateProjectActivity;
import chi_gitanalyz.gitanalyzator.ui.FragmentDialog;
import chi_gitanalyz.gitanalyzator.ui.adapter.ad_project.ProjectAdapter;
import chi_gitanalyz.gitanalyzator.ui.adapter.ad_project.ProjectNames;
import chi_gitanalyz.gitanalyzator.ui.developer.DevelopersActivity;
import dmax.dialog.SpotsDialog;

/**
 * Created by Papin on 26.09.2016.
 */

public class ProjectsActivity extends BaseActivity implements ProjectAdapter.NameOnClickListener{

    String TOKEN = "_NULL_";
    String TOKEN_ID;
    String MANAGER_ID;
    private List<ProjectNames> projectNames = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Project> projectList;
    LinearLayout view;
    FragmentDialog fragmentDialog;

    android.app.AlertDialog dialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_layout);

        dialog = new SpotsDialog(this);
        dialog.show();

        Intent intent = getIntent();
        TOKEN_ID = intent.getStringExtra("TOKEN_ID");
        MANAGER_ID = intent.getStringExtra("MANAGER_ID");
        recyclerView = (RecyclerView) findViewById(R.id.rec_view453);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        fragmentDialog = new FragmentDialog();
        findViewById(R.id.but_all_dev).setOnClickListener((view) ->
                {
                    Intent startDevActivity = new Intent(this, DevelopersActivity.class);
                    startDevActivity.putExtra("TOKEN", TOKEN_ID);
                    startActivity(startDevActivity);
                }
        );


        loadProjects();
    }


    private void loadProjects() {
        app.getNet().projectList(TOKEN_ID);
    }

    @Override
    public void onDbDataUpdated(@I_Db.DbEvent int eventId, Object dbObject) {
        switch (eventId) {
        }
    }


    private void deleteUser() {
            app.getNet().signOUT(TOKEN_ID);
            app.getDb().deleteUser();
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
                break;
            case I_Net.PROJECT_ANALYZ:
                Log.d("ANALYZ", "ANALYZ" + NetObjects);
        }

    }

    private void fillList(Projects projectsList) {

        projectList = projectsList.getProjects();
        for (int i = 0; i < projectList.size(); i++)
            projectNames.add(new ProjectNames(projectList.get(i).getName()));
        ProjectAdapter adapter = new ProjectAdapter(projectNames, this);
        recyclerView.setAdapter(adapter);
        dialog.dismiss();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                deleteUser();
                break;
            case R.id.item2:
                Intent intent = new Intent(this, CreateProjectActivity.class);
                intent.putExtra("USER_ID", MANAGER_ID);
                intent.putExtra("TOKEN", TOKEN_ID);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void signOutSucces() {
        finish();
    }

    @Override
    public void getPosition(int position) {
        String id = String.valueOf(projectList.get(position).getId());
        Intent startActivity_Graph = new Intent(this, GraphProjectActivity.class);
        startActivity_Graph.putExtra("_TOKEN_", TOKEN_ID);
        startActivity_Graph.putExtra("ID_PROJECT", id);
        startActivity(startActivity_Graph);

    }

}
