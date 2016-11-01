package chi_gitanalyz.gitanalyzator.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.core.api.Db;
import chi_gitanalyz.gitanalyzator.core.api.Net;
import chi_gitanalyz.gitanalyzator.db.sqlite.model.Manager;
import chi_gitanalyz.gitanalyzator.retrofit.model.data.User;
import chi_gitanalyz.gitanalyzator.retrofit.model.request.InRequest;
import chi_gitanalyz.gitanalyzator.retrofit.model.response.InResponse;
import chi_gitanalyz.gitanalyzator.ui.project.ProjectsActivity;
import dmax.dialog.SpotsDialog;


public class MainActivity extends BaseActivity {

    private String email;
    private String password;
    private MaterialEditText etEmail;
    private MaterialEditText etPassword;
    private String myToken,myIdManager;

    private String token;

    private AlertDialog dialog;
    private SharedPreferences sPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        token = "NULL";
        dialog = new SpotsDialog(this);
        setContentView(R.layout.activity_main);
        etEmail = (MaterialEditText) findViewById(R.id.email);
        etPassword = (MaterialEditText) findViewById(R.id.password);
        findViewById(R.id.butSignIn).setOnClickListener((view) ->
                {

                    email = etEmail.getText().toString();
                    password = etPassword.getText().toString();
                    User user = new User();
                    InRequest request = new InRequest();
                    user.setEmail(email);
                    user.setPassword(password);
                    request.setUser(user);
                    if (isNetworkConnected()){
                        dialog.show();
                        app.getNet().signIN(request);
                    }
                    else
                        Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();
                }
        );
        findViewById(R.id.butSignUp).setOnClickListener((view) ->
                {
                    Intent intent = new Intent(this, SignUpActivity.class);
                    startActivity(intent);
                }
        );
    }

    //      DB
    @Override
    public void onDbDataUpdated(@Db.DbEvent int eventId, Object dbObject) {
        switch (eventId) {
            case Db.USER_UPD:
                dbSaveUser();
                break;
            case Db.USER_DELETE:
                Log.d("DB", "user deleted");
                break;
        }
    }


    private void dbSaveUser() {
        Log.d("DB", "user saved");
        Intent intent = new Intent(this, ProjectsActivity.class);
        sPref = getSharedPreferences("TOKENS",MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        ed.putString("tokenId", myToken);
        ed.putString("managerId", myIdManager);
        ed.commit();
        startActivity(intent);
    }

    @Override
    public void onDbErrorError(@Db.DbEvent int tableId, Object error) {
    }

    @Override
    public void onNetRequestDone(@Net.NetEvent int evetId, Object NetObjects) {
        switch (evetId) {
            case Net.Sign_IN:
                InSuccess((InResponse) NetObjects);
                break;
        }
    }

    @Override
    public void onNetRequestFail(@Net.NetEvent int evetId, Object NetObjects) {
        switch (evetId) {
            case Net.Sign_IN:
                dialog.dismiss();
                Toast.makeText(this, "Chech our internet connection and input data", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void InSuccess(InResponse response) {
        dialog.dismiss();
        Manager manager = new Manager();
        manager.setToken(response.getToken().toString());
        Log.d("PAPIN_TAG","token"+response.getToken().length());
        myToken = response.getToken().toString();
        myIdManager = response.getUserId().toString();
        app.getDb().saveUser(manager);
    }

}

