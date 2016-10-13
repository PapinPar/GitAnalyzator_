package chi_gitanalyz.gitanalyzator.ui.project;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.core.api.Db;
import chi_gitanalyz.gitanalyzator.core.api.Net;
import chi_gitanalyz.gitanalyzator.retrofit.model.data.Project;
import chi_gitanalyz.gitanalyzator.retrofit.model.request.ProjectsRequest;
import chi_gitanalyz.gitanalyzator.ui.BaseActivity;
import chi_gitanalyz.gitanalyzator.ui.CreateProjectActivity;
import chi_gitanalyz.gitanalyzator.ui.UpdateProjectActivity;
import chi_gitanalyz.gitanalyzator.ui.adapter.ProjectAdapter;
import chi_gitanalyz.gitanalyzator.ui.adapter.ProjectNames;
import chi_gitanalyz.gitanalyzator.ui.developer.DevelopersActivity;
import dmax.dialog.SpotsDialog;

/**
 * Created by Papin on 26.09.2016.
 */

public class ProjectsActivity extends BaseActivity implements ProjectAdapter.NameOnClickListener {

    private String token = "_NULL_";
    private String projectId;
    private String tokenId;
    private String managerId;
    private List<ProjectNames> projectNames = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Project> projectList;
    private FragmentDialog fragmentDialog;
    private android.app.AlertDialog dialog;
    private ProjectAdapter adapter;
    private SharedPreferences sPref;
    private boolean permision;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_layout);
        dialog = new SpotsDialog(this);
        dialog.show();
        sPref = getSharedPreferences("TOKENS", MODE_PRIVATE);
        tokenId = sPref.getString("tokenId", "tokenId");
        managerId = sPref.getString("managerId", "managerId");
        recyclerView = (RecyclerView) findViewById(R.id.rec_view453);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ProjectAdapter(projectNames, this);
        recyclerView.setAdapter(adapter);
        fragmentDialog = new FragmentDialog();
        findViewById(R.id.but_all_dev).setOnClickListener((view) ->
                {
                    if (isNetworkConnected()) {
                        Intent startDevActivity = new Intent(this, DevelopersActivity.class);
                        startDevActivity.putExtra("token", tokenId);
                        startActivity(startDevActivity);
                    } else
                        Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();
                }
        );

        loadProjects();
    }

    private void loadProjects() {
        if (isNetworkConnected())
            app.getNet().projectList(tokenId);
        else {
            Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }

    @Override
    public void onDbDataUpdated(@Db.DbEvent int eventId, Object dbObject) {
        switch (eventId) {
        }
    }


    private void deleteUser() {
        if (isNetworkConnected()) {
            app.getNet().signOUT(tokenId);
            app.getDb().deleteUser();
            Log.d("DB", token);
        } else {
            Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();
            dialog.dismiss();
        }
    }


    //     *************************************** NET ***************************************
    @Override
    public void onNetRequestDone(@Net.NetEvent int evetId, Object NetObjects) {
        switch (evetId) {
            case Net.Sign_OUT:
                signOutSucces();
                break;
            case Net.PROJECT_LIST:
                fillList((ProjectsRequest) NetObjects);
                break;
            case Net.PROJECT_ANALYZ:
                Log.d("ANALYZ", "ANALYZ" + NetObjects);
                break;
            case Net.DEL_PROJECT:
                onRestart();
                break;
        }

    }

    private void fillList(ProjectsRequest projectsList) {

        projectNames.clear();
        projectList = projectsList.getProjects();
        for (int i = 0; i < projectList.size(); i++) {
            if (projectsList.getProjects().get(i).getStatus().equals("completed"))
                projectNames.add(new ProjectNames(projectList.get(i).getName(), projectList.get(i).getHosting(), projectsList.getProjects().get(i).getId()));
        }
        adapter.notifyDataSetChanged();
        dialog.dismiss();
        checkPirmission();
    }

    private void checkPirmission()
    {
      if (ContextCompat.checkSelfPermission(this, Manifest.permission.VIBRATE)
              == PackageManager.PERMISSION_GRANTED)
      {
          permision= true;
      }
      else
      {
      ActivityCompat.requestPermissions(this,
              new String[]{Manifest.permission.VIBRATE},
              21);
      }
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
                intent.putExtra("USER_ID", managerId);
                intent.putExtra("token", tokenId);
                startActivity(intent);
                overridePendingTransition(R.anim.activity_down_up_close_enter, R.anim.activity_down_up_exit);
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
        if (isNetworkConnected()) {
            String id = String.valueOf(projectNames.get(position).getId());
            Intent startActivity_Graph = new Intent(this, GraphProjectActivity.class);
            startActivity_Graph.putExtra("_TOKEN_", tokenId);
            startActivity_Graph.putExtra("ID_PROJECT", id);
            startActivity(startActivity_Graph);
        } else
            Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void getLongClick(int position) {
        projectId = String.valueOf(projectNames.get(position).getId());
        showDialog(0);
    }

    protected Dialog onCreateDialog(int id) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Choose Action")
                .setCancelable(true)
                .setPositiveButton("Delete Project",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,
                                                int id) {
                                if (isNetworkConnected())
                                    app.getNet().deleteProject(projectId, tokenId);
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
        updProject.putExtra("tokenId", tokenId);
        updProject.putExtra("projectId", projectId);
        startActivity(updProject);
        overridePendingTransition(R.anim.activity_down_up_close_enter, R.anim.activity_down_up_exit);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        dialog.show();
        loadProjects();
    }

}


