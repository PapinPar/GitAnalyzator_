package chi_gitanalyz.gitanalyzator.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import chi_gitanalyz.gitanalyzator.retrofit.model.developers.CurrentDev;
import chi_gitanalyz.gitanalyzator.retrofit.model.developers.Developers;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.CreateProject;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.Projects;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.home.Home;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.project_id.ProjectsID;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.InRequest;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.User;
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
                .baseUrl("http://192.168.2.212/api/v1/")
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

    public Response<UpRequset> signUp(UpRequset newUser) throws IOException {

        Call<UpRequset> newUserCall = api.signUp(newUser);
        Response response = newUserCall.execute();
        return response;
    }

    public Response<String> signOut(String TOKEN) throws IOException {
        Call<String> out = api.signOut(TOKEN);
        Response response = out.execute();
        return response;
    }

    public Response<User> validateToken(String token) throws IOException {
        Call<User> call = api.validateToken(token);
        Response response = call.execute();
        return response;
    }


    public Response<Projects> projectList(String TOKEN) throws IOException {
        Call<Projects> projectList = api.projectList(TOKEN);
        Response<Projects> response = projectList.execute();
        return response;
    }

    public Response<ProjectsID> projectAnalyz(String id, String token) throws IOException {
        Call<ProjectsID> projectsIDCall = api.projectAnalyz(id, token);
        Response<ProjectsID> response = projectsIDCall.execute();
        return response;
    }

    public Response<Developers> getAllDev(String token) throws IOException {
        Call<Developers> developerCall = api.getDevelopers(token);
        Response<Developers> response = developerCall.execute();
        return response;
    }

    public Response<CurrentDev> getCurrDev(String id, String token) throws IOException {
        Call<CurrentDev> currentDevCall = api.getCurrDeveloper(id, token);
        Response<CurrentDev> response = currentDevCall.execute();
        return response;
    }


    public Response<CreateProject> createProject(CreateProject project, String token) throws IOException {
        Call<CreateProject> projectCall = api.createProject(project, token);
        Response<CreateProject> response = projectCall.execute();
        return response;
    }

    public Response<Home> getHome(String id, String token) throws IOException {
        Call<Home> homeCall = api.getHome(id, token);
        Response<Home> response = homeCall.execute();
        return response;
    }

    public Response<ProjectsID> projectFilter(String id, String token, Integer branch, Integer dev) throws IOException {
        Call<ProjectsID> idCall = api.projectFilter(id, token, branch, dev);
        Response<ProjectsID> response = idCall.execute();
        return response;
    }
}
