package chi_gitanalyz.gitanalyzator.ui;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.core.api.I_Net;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.InRequest;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.InResult;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.User;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signup.UpRequset;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signup.UpResult;


public class MainActivity extends BaseActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        findViewById(R.id.butSignIn).setOnClickListener((view) ->
        {

            User user = new User();
            InRequest request = new InRequest();
            user.setEmail("test@ui.com");
            user.setPassword("password");
            request.setUser(user);
            app.getNet().signIN(request);

        }
        );

        findViewById(R.id.butSignUp).setOnClickListener((view) ->
        {
            User NewUser = new User();
            UpRequset requset = new UpRequset();
            NewUser.setEmail("test@android.com");
            NewUser.setPassword("password");
            NewUser.setPassword_confirmation("password");
            requset.setUser(NewUser);
            app.getNet().signUP(requset);
        }
        );

    }

    @Override
    public void onNetRequestDone(@I_Net.NetEvent int evetId, Object NetObjects) {
        switch (evetId)
        {
            case I_Net.Sign_IN:
                InSuccess((InResult) NetObjects);
                break;
            case I_Net.Sign_UP:
                UpSuccess((UpResult) NetObjects);
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
            case I_Net.Sign_UP:
                UpError((String) NetObjects);
                break;
        }
    }

    //      IN
    private void InSuccess(InResult response) {
        Log.d("TOKEN", "" + response.getToken());
    }
    private void InError(String netObjects) {
        Log.d("Error", "" + netObjects);
        Toast.makeText(this, ""+netObjects, Toast.LENGTH_SHORT).show();
    }

    //      UP
    private void UpError(String netObjects) {
        Log.d("Error", "" + netObjects);
        Toast.makeText(this, ""+netObjects, Toast.LENGTH_SHORT).show();
    }
    private void UpSuccess(UpResult response)
    {
        Log.d("ID", "" + response.getId());
    }
}

