package chi_gitanalyz.gitanalyzator.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.rengwuxian.materialedittext.MaterialEditText;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.core.api.I_Net;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.CreateProject;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.Project;

/**
 * Created by Papin on 30.09.2016.
 */

public class CreateProjectActivity extends BaseActivity {

    MaterialEditText name;
    MaterialEditText SSH;
    String MANAGER_ID;
    String TOKEN;
    String S_name;
    String S_ssh;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_project_layout);

        Intent intent = getIntent();
        MANAGER_ID = intent.getStringExtra("USER_ID");
        TOKEN = intent.getStringExtra("TOKEN");

        name = (MaterialEditText) findViewById(R.id.project_name);
        SSH = (MaterialEditText) findViewById(R.id.projet_SSH);

        findViewById(R.id.butCreate).setOnClickListener((view) ->
                {
                    S_name = name.getText().toString();
                    S_ssh = SSH.getText().toString();

                    Project project = new Project();
                    CreateProject create = new CreateProject();
                    project.setUserId(Integer.valueOf(MANAGER_ID));
                    project.setSsh(S_ssh);
                    project.setName(S_name);
                    create.setUser(project);

                    app.getNet().createProject(create,TOKEN);
                }
        );

    }

    @Override
    public void onNetRequestDone(@I_Net.NetEvent int evetId, Object NetObjects) {
        finish();
    }

    @Override
    public void onNetRequestFail(@I_Net.NetEvent int evetId, Object NetObjects) {

    }
}
