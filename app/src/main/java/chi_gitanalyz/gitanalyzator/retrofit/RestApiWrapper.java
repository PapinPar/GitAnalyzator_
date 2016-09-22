package chi_gitanalyz.gitanalyzator.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.UserRequest;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Papin on 22.09.2016.
 */

public class RestApiWrapper {

    private MyApi api;
    private static RestApiWrapper instance;

    public static RestApiWrapper getInstance() {
        if (instance == null) {
            instance = new RestApiWrapper();
        }
        return instance;
    }

    public RestApiWrapper() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.2.212/api/v1/auth/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(MyApi.class);

    }

    public void signIn(UserRequest user, Callback<UserRequest> callback) {
        Call<UserRequest> usersCall = api.signIn(user);
        usersCall.enqueue(callback);
    }
}
