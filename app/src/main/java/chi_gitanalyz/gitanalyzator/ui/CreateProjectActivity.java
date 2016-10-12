package chi_gitanalyz.gitanalyzator.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.core.api.Net;
import chi_gitanalyz.gitanalyzator.retrofit.model.request.CreateProjectRequest;
import chi_gitanalyz.gitanalyzator.retrofit.model.data.Project;
import chi_gitanalyz.gitanalyzator.service.MyService;

/**
 * Created by Papin on 30.09.2016.
 */

public class CreateProjectActivity extends BaseActivity {

    private MaterialEditText name;
    private MaterialEditText SSH;
    private String managerId;
    private String token;
    private String sName;
    private String sSsh;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_project_layout);

        Intent intent = getIntent();
        managerId = intent.getStringExtra("USER_ID");
        token = intent.getStringExtra("token");

        name = (MaterialEditText) findViewById(R.id.project_name);
        SSH = (MaterialEditText) findViewById(R.id.projet_SSH);

        findViewById(R.id.butCreate).setOnClickListener((view) ->
                {
                    sName = name.getText().toString();
                    sSsh = SSH.getText().toString();

                    Project project = new Project();
                    CreateProjectRequest create = new CreateProjectRequest();
                    project.setUserId(Integer.valueOf(managerId));
                    project.setLink(sSsh);
                    project.setName(sName);
                    create.setUser(project);

                    if (isNetworkConnected())
                        app.getNet().createProject(create, token);
                    else
                        Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();
                }
        );

    }

    @Override
    public void onNetRequestDone(@Net.NetEvent int evetId, Object NetObjects) {
        switch (evetId) {
            case Net.CREATE_PROJECT:
                startService(new Intent(this, MyService.class));
                finish();
                break;
        }
    }

    @Override
    public void onNetRequestFail(@Net.NetEvent int evetId, Object NetObjects) {
        switch (evetId) {
            case Net.CREATE_PROJECT:
                Toast.makeText(this, "Chech our internet connection and input data", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
