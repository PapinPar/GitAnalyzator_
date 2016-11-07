package chi_gitanalyz.gitanalyzator.retrofit;

import chi_gitanalyz.gitanalyzator.retrofit.model.data.Project;
import chi_gitanalyz.gitanalyzator.retrofit.model.response.CurrentDevResponse;
import chi_gitanalyz.gitanalyzator.retrofit.model.response.DevelopersResponse;
import chi_gitanalyz.gitanalyzator.retrofit.model.request.CreateProjectRequest;
import chi_gitanalyz.gitanalyzator.retrofit.model.request.ProjectsRequest;
import chi_gitanalyz.gitanalyzator.retrofit.model.response.HomeResponse;
import chi_gitanalyz.gitanalyzator.retrofit.model.response.ProjectsIdResponse;
import chi_gitanalyz.gitanalyzator.retrofit.model.request.InRequest;
import chi_gitanalyz.gitanalyzator.retrofit.model.data.User;
import chi_gitanalyz.gitanalyzator.retrofit.model.request.UpRequset;
import chi_gitanalyz.gitanalyzator.retrofit.model.request.UpdateUserRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Papin on 20.09.2016.
 */

public interface MyApi {

    //*********************** AUTH **************************
    @POST("auth/sign_up")
    Call<UpRequset> signUp(@Body UpRequset user);

    @POST("auth/sign_in")
    Call<InRequest> signIn(@Body InRequest user);

    @DELETE("auth/sign_out")
    Call<String> signOut(@Query("token") String token);

    @GET("auth/validate_token")
    Call<User> validateToken(@Query("token") String token);

    @PUT("update_profile")
    Call<InRequest> updateProfile(@Body UpdateUserRequest user);

    //*********************** PROJECT *************************
    @GET("projects/{id}/home")
    Call<HomeResponse> getHome(@Path("id") String id, @Query("token") String token);

    @GET("projects/{id}/")
    Call<ProjectsIdResponse> projectFilter(@Path("id") String id, @Query("token") String token
            , @Query("branch_id") Integer branch, @Query("developer_id") Integer dev,@Query("language") String language);

    @GET("projects")
    Call<ProjectsRequest> projectList(@Query("token") String token);

    @GET("projects/{id}/")
    Call<ProjectsIdResponse> projectAnalyz(@Path("id") String id, @Query("token") String token);

    @POST("projects")
    Call<CreateProjectRequest> createProject(@Body CreateProjectRequest project, @Query("token") String token);

    //*********************** DEVELOPER *************************
    @GET("developers")
    Call<DevelopersResponse> getDevelopers(@Query("token") String token);

    @GET("developers/{id}")
    Call<CurrentDevResponse> getCurrDeveloper(@Path("id") String id, @Query("token") String token);

    @DELETE("projects/{id}/")
    Call<String> deleteProject(@Path("id") String id, @Query("token") String token);

    @PUT("projects/{id}")
    Call<CreateProjectRequest> updateProject(@Path("id") String id, @Body CreateProjectRequest projectsID, @Query("token") String token);

    @PATCH("projects/{id}/select_analyzers")
    Call<Project> selectAnalyzator(@Path("id") Integer id,@Query("languages") String languages,@Query("token") String token);
}
