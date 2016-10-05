package chi_gitanalyz.gitanalyzator.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.core.api.I_Net;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.CreateProject;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.Project;

/**
 * Created by Papin on 04.10.2016.
 */

public class UpdateProjectActivity extends BaseActivity {

    String TOKEN;
    String PROECT_ID;
    String S_name;
    String S_ssh;

    MaterialEditText name;
    MaterialEditText SSH;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_project_layout);
        Intent intent = getIntent();
        TOKEN = intent.getStringExtra("TOKEN_ID");
        PROECT_ID = intent.getStringExtra("PROJECT_ID");

        name = (MaterialEditText) findViewById(R.id.upd_project_name);
        SSH = (MaterialEditText) findViewById(R.id.upd_projet_SSH);

        findViewById(R.id.upd_butUpdate).setOnClickListener((v ->
        {
            S_name = name.getText().toString();
            S_ssh = SSH.getText().toString();
            Project project = new Project();
            CreateProject create = new CreateProject();
            project.setSsh(S_ssh);
            project.setName(S_name);
            create.setUser(project);

            if (isNetworkConnected() == true)
                app.getNet().updateProject(PROECT_ID, create, TOKEN);
            else {
                Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();
            }
        }));
    }

    @Override
    public void onNetRequestDone(@I_Net.NetEvent int evetId, Object NetObjects) {
        super.onNetRequestDone(evetId, NetObjects);
        switch (evetId) {
            case I_Net.UPD_PROJECT:
                Toast.makeText(this, "Project Updated", Toast.LENGTH_SHORT).show();
                finish();
                break;
        }
    }

    @Override
    public void onNetRequestFail(@I_Net.NetEvent int evetId, Object NetObjects) {
        super.onNetRequestDone(evetId, NetObjects);
        String s = (String) NetObjects;
        Log.d("ERROR", "ERROR " + s);
        switch (evetId) {
            case I_Net.UPD_PROJECT:
                Toast.makeText(this, "" + s, Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
