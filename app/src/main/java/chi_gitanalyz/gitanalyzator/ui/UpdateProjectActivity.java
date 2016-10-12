package chi_gitanalyz.gitanalyzator.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.core.api.Net;
import chi_gitanalyz.gitanalyzator.retrofit.model.request.CreateProjectRequest;
import chi_gitanalyz.gitanalyzator.retrofit.model.data.Project;

/**
 * Created by Papin on 04.10.2016.
 */

public class UpdateProjectActivity extends BaseActivity {

    private String token;
    private String proectId;
    private String sName;
    private String sSsh;

    private MaterialEditText name;
    private MaterialEditText SSH;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_project_layout);
        Intent intent = getIntent();
        token = intent.getStringExtra("tokenId");
        proectId = intent.getStringExtra("projectId");

        name = (MaterialEditText) findViewById(R.id.upd_project_name);
        SSH = (MaterialEditText) findViewById(R.id.upd_projet_SSH);

        findViewById(R.id.upd_butUpdate).setOnClickListener((v ->
        {
            sName = name.getText().toString();
            sSsh = SSH.getText().toString();
            Project project = new Project();
            CreateProjectRequest create = new CreateProjectRequest();
            project.setLink(sSsh);
            project.setName(sName);
            create.setUser(project);

            if (isNetworkConnected())
                app.getNet().updateProject(proectId, create, token);
            else {
                Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public void onNetRequestDone(@Net.NetEvent int evetId, Object NetObjects) {
        super.onNetRequestDone(evetId, NetObjects);
        switch (evetId) {
            case Net.UPD_PROJECT:
                Toast.makeText(this, "Project Updated", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    @Override
    public void onNetRequestFail(@Net.NetEvent int evetId, Object NetObjects) {
        super.onNetRequestDone(evetId, NetObjects);
        String s = (String) NetObjects;
        Log.d("ERROR", "ERROR " + s);
        switch (evetId) {
            case Net.UPD_PROJECT:
                Toast.makeText(this, "" + s, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
