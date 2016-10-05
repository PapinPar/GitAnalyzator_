package chi_gitanalyz.gitanalyzator.ui.project;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

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
import chi_gitanalyz.gitanalyzator.ui.UpdateProjectActivity;
import chi_gitanalyz.gitanalyzator.ui.adapter.ad_project.ProjectAdapter;
import chi_gitanalyz.gitanalyzator.ui.adapter.ad_project.ProjectNames;
import chi_gitanalyz.gitanalyzator.ui.developer.DevelopersActivity;
import dmax.dialog.SpotsDialog;

/**
 * Created by Papin on 26.09.2016.
 */

public class ProjectsActivity extends BaseActivity implements ProjectAdapter.NameOnClickListener {

    String TOKEN = "_NULL_";
    String PROJECT_ID;
    String TOKEN_ID;
    String MANAGER_ID;
    private List<ProjectNames> projectNames = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Project> projectList;
    LinearLayout view;
    FragmentDialog fragmentDialog;
    AlertDialog.Builder ad;
    android.app.AlertDialog dialog;

    boolean check;

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
                    if (isNetworkConnected() == true) {
                        Intent startDevActivity = new Intent(this, DevelopersActivity.class);
                        startDevActivity.putExtra("TOKEN", TOKEN_ID);
                        startActivity(startDevActivity);
                    } else
                        Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();
                }
        );


        loadProjects();
    }


    private void loadProjects() {
        if (isNetworkConnected() == true)
            app.getNet().projectList(TOKEN_ID);
        else {
            Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    @Override
    public void onDbDataUpdated(@I_Db.DbEvent int eventId, Object dbObject) {
        switch (eventId) {
        }
    }


    private void deleteUser() {
        if (isNetworkConnected() == true) {
            app.getNet().signOUT(TOKEN_ID);
            app.getDb().deleteUser();
            Log.d("DB", TOKEN);
        } else {
            Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
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
                break;
            case I_Net.DEL_PROJECT:
                onRestart();
                break;
        }

    }

    private void fillList(Projects projectsList) {

        projectNames.clear();
        projectList = projectsList.getProjects();
        for (int i = 0; i < projectList.size(); i++) {
            if (projectsList.getProjects().get(i).getStatus().equals("completed"))
                projectNames.add(new ProjectNames(projectList.get(i).getName()));
        }
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
        if (isNetworkConnected() == true) {
            String id = String.valueOf(projectList.get(position).getId());
            Intent startActivity_Graph = new Intent(this, GraphProjectActivity.class);
            startActivity_Graph.putExtra("_TOKEN_", TOKEN_ID);
            startActivity_Graph.putExtra("ID_PROJECT", id);
            startActivity(startActivity_Graph);
        } else
            Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void getLongClick(int position) {
        PROJECT_ID = String.valueOf(projectList.get(position).getId());
        showDialog(0);
    }

    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Choose Action")
                .setCancelable(false)
                .setPositiveButton("Delete Project",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                if (isNetworkConnected() == true)
                                    app.getNet().deleteProject(PROJECT_ID, TOKEN_ID);
                                else
                                    showToast();
                                dialog.cancel();
                            }
                        })
                .setNegativeButton("Update Project",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                updateProject();
                                dialog.cancel();
                            }
                        });
        return builder.create();
    }

    private void showToast() {
        Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();
        dialog.dismiss();
    }

    private void updateProject() {
        Intent updProject = new Intent(this, UpdateProjectActivity.class);
        updProject.putExtra("TOKEN_ID", TOKEN_ID);
        updProject.putExtra("PROJECT_ID", PROJECT_ID);
        startActivity(updProject);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Intent intent = new Intent(this, ProjectsActivity.class);
        overridePendingTransition(0, 0);//4
        intent.putExtra("TOKEN_ID", TOKEN_ID);
        intent.putExtra("MANAGER_ID", MANAGER_ID);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        startActivity(intent);
    }
}


