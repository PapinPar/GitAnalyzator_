package chi_gitanalyz.gitanalyzator.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.InRequest;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signup.UpRequset;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Response;
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

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                .create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.2.212/api/v1/auth/")
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        api = retrofit.create(MyApi.class);

    }

    public Response<InRequest> signIn(InRequest user) throws IOException {

        Call<InRequest> usersCall = api.signIn(user);
        Response response = usersCall.execute();
        return response;
    }

    public Response<UpRequset> signUp (UpRequset newUser) throws IOException{

        Call<UpRequset> newUserCall = api.signUp(newUser);
        Response response = newUserCall.execute();
        return response;
    }



}
