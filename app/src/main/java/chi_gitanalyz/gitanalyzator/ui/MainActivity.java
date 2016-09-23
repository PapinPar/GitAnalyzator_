package chi_gitanalyz.gitanalyzator.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.core.api.I_Net;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.User;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.UserRequest;
import retrofit2.Response;


public class MainActivity extends BaseActivity
{
    Button sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.butSignIn).setOnClickListener((view) ->
        {

            User user = new User();
            UserRequest request = new UserRequest();
            user.setEmail("test@ui.com");
            user.setPassword("passwor1d");
            request.setUser(user);
            app.getNet().signIN(request);

        }
        );

    }

    @Override
    public void onNetRequestDone(@I_Net.NetEvent int evetId, Object NetObjects) {
        switch (evetId)
        {
            case I_Net.Sign_IN:
                InSuccess((Response<UserRequest>) NetObjects);
                break;
        }
    }

    @Override
    public void onNetRequestFail(@I_Net.NetEvent int evetId, Object NetObjects)
    {
        switch (evetId)
        {
            case I_Net.Sign_IN:
                InError((String) NetObjects);
                break;
        }
    }

    private void InSuccess(Response<UserRequest> response) {
        Log.d("TOKEN", "" + response.body().getToken());
    }

    private void InError(String netObjects) {
        Log.d("Error", "" + netObjects);
    }
}

