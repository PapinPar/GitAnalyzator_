package chi_gitanalyz.gitanalyzator.ui;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.core.api.I_Db;
import chi_gitanalyz.gitanalyzator.core.api.I_Net;
import chi_gitanalyz.gitanalyzator.db.sqlite.model.Manager;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.InRequest;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.InResult;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.User;
import chi_gitanalyz.gitanalyzator.ui.project.ProjectsActivity;
import dmax.dialog.SpotsDialog;


public class MainActivity extends BaseActivity {

    String Email;
    String Password;
    MaterialEditText etEmail;
    MaterialEditText etPassword;

    String TOKEN;

    AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TOKEN = "NULL";
        dialog = new SpotsDialog(this);
        dialog.show();
        app.getDb().loadUser();
        setContentView(R.layout.activity_main);
        etEmail = (MaterialEditText) findViewById(R.id.email);
        etPassword = (MaterialEditText) findViewById(R.id.password);

        findViewById(R.id.butSignIn).setOnClickListener((view) ->
                {
                    Email = etEmail.getText().toString();
                    Password = etPassword.getText().toString();
                    User user = new User();
                    InRequest request = new InRequest();
                    user.setEmail(Email);
                    user.setPassword(Password);
                    request.setUser(user);
                    app.getNet().signIN(request);
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
    public void onDbDataUpdated(@I_Db.DbEvent int eventId, Object dbObject) {
        switch (eventId) {
            case I_Db.USER_UPD:
                dbSaveUser();
                break;
            case I_Db.USER_DELETE:
                Log.d("DB", "user deleted");
                break;
            case I_Db.USER_LOAD:
                loaded((Manager) dbObject);
                break;
        }
    }

    private void loaded(Manager dbObject) {
        if (dbObject != null) {
            TOKEN = dbObject.getToken().toString();
            app.getNet().validateToken(dbObject.getToken().toString());
        }
        else
            dialog.dismiss();
    }


    private void dbSaveUser() {
        Log.d("DB", "user saved");
    }

    @Override
    public void onDbErrorError(@I_Db.DbEvent int tableId, Object error) {
    }

    @Override
    public void onNetRequestDone(@I_Net.NetEvent int evetId, Object NetObjects) {
        switch (evetId) {
            case I_Net.Sign_IN:
                InSuccess((InResult) NetObjects);
                break;
            case I_Net.Validate_Token:
                Success((User) NetObjects);
                break;
        }
    }

    private void Success(User netObjects) {
        Manager manager = new Manager();
        manager.setToken(TOKEN);
        app.getDb().saveUser(manager);
        Intent intent = new Intent(this, ProjectsActivity.class);
        intent.putExtra("TOKEN_ID", TOKEN);
        intent.putExtra("MANAGER_ID", netObjects.getId().toString());
        dialog.dismiss();
        startActivity(intent);
    }

    @Override
    public void onNetRequestFail(@I_Net.NetEvent int evetId, Object NetObjects) {
        switch (evetId) {
            case I_Net.Sign_IN:
                InError((String) NetObjects);
                break;
        }
    }

    //      IN
    private void InSuccess(InResult response) {
        Log.d("TOKEN", "" + response.getToken());
        Log.d("TOKEN", "" + response.getUserId());
        Manager manager = new Manager();
        manager.setToken(response.getToken());
        app.getDb().saveUser(manager);
        Intent intent = new Intent(this, ProjectsActivity.class);
        intent.putExtra("TOKEN_ID", response.getToken());
        intent.putExtra("MANAGER_ID", response.getUserId().toString());
        startActivity(intent);
    }

    private void InError(String netObjects) {
        Log.d("Error", "" + netObjects);

        Toast.makeText(this, "" + netObjects, Toast.LENGTH_SHORT).show();
    }


}

