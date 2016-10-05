package chi_gitanalyz.gitanalyzator.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.rengwuxian.materialedittext.MaterialEditText;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.core.api.I_Net;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.User;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signup.UpRequset;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signup.UpResult;

/**
 * Created by Papin on 26.09.2016.
 */

public class SignUpActivity extends BaseActivity {

    String Email;
    String Password;
    String ConfirmPassword;

    MaterialEditText etEmail;
    MaterialEditText etPassword;
    MaterialEditText etConfirmPassword;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        etEmail = (MaterialEditText) findViewById(R.id.email);
        etPassword = (MaterialEditText) findViewById(R.id.password);
        etConfirmPassword = (MaterialEditText) findViewById(R.id.confirmPass);

        findViewById(R.id.butRegister).setOnClickListener((view) ->
                {
                    Email = etEmail.getText().toString();
                    Password = etPassword.getText().toString();
                    ConfirmPassword = etConfirmPassword.getText().toString();

                    User NewUser = new User();
                    UpRequset requset = new UpRequset();
                    NewUser.setEmail(Email);
                    NewUser.setPassword(Password);
                    NewUser.setPassword_confirmation(ConfirmPassword);
                    requset.setUser(NewUser);
                    if (isNetworkConnected() == true)
                        app.getNet().signUP(requset);
                    else {
                        Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public void onNetRequestDone(@I_Net.NetEvent int evetId, Object NetObjects) {
        switch (evetId) {
            case I_Net.Sign_UP:
                UpSuccess((UpResult) NetObjects);
                break;
        }
    }

    @Override
    public void onNetRequestFail(@I_Net.NetEvent int evetId, Object NetObjects) {
        switch (evetId) {
            case I_Net.Sign_UP:
                Toast.makeText(this, "Chech our internet connection and input data", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void UpSuccess(UpResult response) {
        Log.d("ID", "" + response.getId());
    }

    private void UpError(String netObjects) {
        Log.d("Error", "" + netObjects);
        Toast.makeText(this, "" + netObjects, Toast.LENGTH_SHORT).show();
    }

}
