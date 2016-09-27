package chi_gitanalyz.gitanalyzator.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.core.api.I_Db;
import chi_gitanalyz.gitanalyzator.core.api.I_Net;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.Project;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.Projects;
import chi_gitanalyz.gitanalyzator.ui.adapter.ProjectNames;
import chi_gitanalyz.gitanalyzator.ui.adapter.RVAdapter;

/**
 * Created by Papin on 26.09.2016.
 */

public class ProjectsActivity extends BaseActivity implements RVAdapter.NameOnClickListener {

    String TOKEN = "_NULL_";
    String TOKEN_ID;
    private List<ProjectNames> projectNames= new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    List<Project> s;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_layout);

        Intent intent = getIntent();
        TOKEN_ID = intent.getStringExtra("TOKEN_ID");

        recyclerView=(RecyclerView)findViewById(R.id.rec_view453);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

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
                break;
            case I_Net.PROJECT_ANALYZ:
                Log.d("ANALYZ","ANALYZ" + NetObjects);
        }

    }

    private void fillList(Projects projectsList) {
        s = projectsList.getProjects();
        Log.d("Project", "Project" + s);
        Log.d("Project", "Project SSH" + s.get(1).getSsh());
        for(int i =0;i<s.size();i++)
            projectNames.add(new ProjectNames(s.get(i).getName()));
        RVAdapter adapter = new RVAdapter(projectNames,this);
        recyclerView.setAdapter(adapter);
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
        String id = String.valueOf(s.get(position).getId());
        Intent startActivity_Graph = new Intent(this, GraphActivity.class);
        startActivity_Graph.putExtra("_TOKEN_", TOKEN_ID);
        startActivity_Graph.putExtra("ID_PROJECT", id);
        startActivity(startActivity_Graph);

    }
}
