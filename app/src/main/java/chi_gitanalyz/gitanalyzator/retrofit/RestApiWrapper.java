package chi_gitanalyz.gitanalyzator.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;

import chi_gitanalyz.gitanalyzator.retrofit.model.response.CurrentDevResponse;
import chi_gitanalyz.gitanalyzator.retrofit.model.response.DevelopersResponse;
import chi_gitanalyz.gitanalyzator.retrofit.model.request.CreateProjectRequest;
import chi_gitanalyz.gitanalyzator.retrofit.model.request.ProjectsRequest;
import chi_gitanalyz.gitanalyzator.retrofit.model.response.HomeResponse;
import chi_gitanalyz.gitanalyzator.retrofit.model.response.ProjectsIdResponse;
import chi_gitanalyz.gitanalyzator.retrofit.model.request.InRequest;
import chi_gitanalyz.gitanalyzator.retrofit.model.data.User;
import chi_gitanalyz.gitanalyzator.retrofit.model.request.UpRequset;
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


    public Response<ProjectsRequest> projectList(String TOKEN) throws IOException {
        Call<ProjectsRequest> projectList = api.projectList(TOKEN);
        Response<ProjectsRequest> response = projectList.execute();
        return response;
    }

    public Response<ProjectsIdResponse> projectAnalyz(String id, String token) throws IOException {
        Call<ProjectsIdResponse> projectsIDCall = api.projectAnalyz(id, token);
        Response<ProjectsIdResponse> response = projectsIDCall.execute();
        return response;
    }

    public Response<DevelopersResponse> getAllDev(String token) throws IOException {
        Call<DevelopersResponse> developerCall = api.getDevelopers(token);
        Response<DevelopersResponse> response = developerCall.execute();
        return response;
    }

    public Response<CurrentDevResponse> getCurrDev(String id, String token) throws IOException {
        Call<CurrentDevResponse> currentDevCall = api.getCurrDeveloper(id, token);
        Response<CurrentDevResponse> response = currentDevCall.execute();
        return response;
    }


    public Response<CreateProjectRequest> createProject(CreateProjectRequest project, String token) throws IOException {
        Call<CreateProjectRequest> projectCall = api.createProject(project, token);
        Response<CreateProjectRequest> response = projectCall.execute();
        return response;
    }

    public Response<HomeResponse> getHome(String id, String token) throws IOException {
        Call<HomeResponse> homeCall = api.getHome(id, token);
        Response<HomeResponse> response = homeCall.execute();
        return response;
    }

    public Response<ProjectsIdResponse> projectFilter(String id, String token, Integer branch, Integer dev,String language) throws IOException {
        Call<ProjectsIdResponse> idCall = api.projectFilter(id, token, branch, dev,language);
        Response<ProjectsIdResponse> response = idCall.execute();
        return response;
    }

    public Response<String> deleteProject(String id, String token) throws IOException {
        Call<String> call = api.deleteProject(id, token);
        Response<String> response = call.execute();
        return response;
    }

    public Response<CreateProjectRequest> updateProject(String id, CreateProjectRequest projectsID, String token)throws IOException {
        Call<CreateProjectRequest> projectsIDCall = api.updateProject(id, projectsID, token);
        Response<CreateProjectRequest> response = projectsIDCall.execute();
        return response;
    }
}
