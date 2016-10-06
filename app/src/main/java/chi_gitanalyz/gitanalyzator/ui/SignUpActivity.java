package chi_gitanalyz.gitanalyzator.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.core.api.Net;
import chi_gitanalyz.gitanalyzator.retrofit.model.data.User;
import chi_gitanalyz.gitanalyzator.retrofit.model.request.UpRequset;
import chi_gitanalyz.gitanalyzator.retrofit.model.response.UpResponse;

/**
 * Created by Papin on 26.09.2016.
 */

public class SignUpActivity extends BaseActivity {

    private String email;
    private String password;
    private String confirmPassword;

    private MaterialEditText etEmail;
    private MaterialEditText etPassword;
    private MaterialEditText etConfirmPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        etEmail = (MaterialEditText) findViewById(R.id.email);
        etPassword = (MaterialEditText) findViewById(R.id.password);
        etConfirmPassword = (MaterialEditText) findViewById(R.id.confirmPass);

        findViewById(R.id.butRegister).setOnClickListener((view) ->
                {
                    email = etEmail.getText().toString();
                    password = etPassword.getText().toString();
                    confirmPassword = etConfirmPassword.getText().toString();

                    User NewUser = new User();
                    UpRequset requset = new UpRequset();
                    NewUser.setEmail(email);
                    NewUser.setPassword(password);
                    NewUser.setPassword_confirmation(confirmPassword);
                    requset.setUser(NewUser);
                    if (isNetworkConnected())
                        app.getNet().signUP(requset);
                    else {
                        Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public void onNetRequestDone(@Net.NetEvent int evetId, Object NetObjects) {
        switch (evetId) {
            case Net.Sign_UP:
                UpSuccess((UpResponse) NetObjects);
                break;
        }
    }

    @Override
    public void onNetRequestFail(@Net.NetEvent int evetId, Object NetObjects) {
        switch (evetId) {
            case Net.Sign_UP:
                Toast.makeText(this, "Chech our internet connection and input data", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void UpSuccess(UpResponse response) {
        Log.d("ID", "" + response.getId());
    }

    private void UpError(String netObjects) {
        Log.d("Error", "" + netObjects);
        Toast.makeText(this, "" + netObjects, Toast.LENGTH_SHORT).show();
    }

}
