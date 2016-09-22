package chi_gitanalyz.gitanalyzator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import chi_gitanalyz.gitanalyzator.retrofit.RestApiWrapper;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.User;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.UserRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity
{
    Button sign_in;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sign_in = (Button)findViewById(R.id.butSignIn);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                switch (v.getId())
                {
                    case R.id.butSignIn:
                        request();
                        break;
                }
            }
        });

    }


    private void request() {

        User request = new User();
        UserRequest user = new UserRequest();
        request.setEmail("test@ui.com");
        request.setPassword("password");
        user.setUser(request);

        RestApiWrapper.getInstance().signIn(user, new Callback<UserRequest>() {
            @Override
            public void onResponse(Call<UserRequest> call, Response<UserRequest> response) {
                UserRequest result = response.body();
                Log.d("TOKEN", "" +  result.getToken());
            }

            @Override
            public void onFailure(Call<UserRequest> call, Throwable t) {

            }
        });

    }
}

