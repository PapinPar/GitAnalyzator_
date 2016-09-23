package chi_gitanalyz.gitanalyzator.retrofit;

import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.UserRequest;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.updateprofile.UpdateUserRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

/**
 * Created by Papin on 20.09.2016.
 */
public interface MyApi {

    @POST("auth/sign_up")
    Call<UserRequest> signUp (@Body UserRequest user);

    @POST("auth/sign_in")
    Call<UserRequest> signIn(@Body UserRequest user);

    @DELETE("sign_out")
    void sign_out(@Query("token") String token);

    @GET("validate_token")
    void validate_token(@Query("token") String token);

    @PUT("update_profile")
    Call<UserRequest> updateProfile(@Body UpdateUserRequest user);

}
