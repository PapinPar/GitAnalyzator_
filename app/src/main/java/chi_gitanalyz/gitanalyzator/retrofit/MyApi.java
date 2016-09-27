package chi_gitanalyz.gitanalyzator.retrofit;

import chi_gitanalyz.gitanalyzator.retrofit.model.project.Projects;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.project_id.ProjectsID;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.InRequest;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signup.UpRequset;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.updateprofile.UpdateUserRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
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
    void validate_token(@Query("token") String token);

    @PUT("update_profile")
    Call<InRequest> updateProfile(@Body UpdateUserRequest user);

    //*********************** PROJECT *************************
    @GET("projects")
    Call<Projects> projectList(@Query("token") String token);

    @GET("projects/{id}/")
    Call<ProjectsID> projectAnalyz(@Path("id") String id, @Query("token") String token);

}
