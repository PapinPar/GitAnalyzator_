package chi_gitanalyz.gitanalyzator.retrofit.model.user.signin;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Papin on 23.09.2016.
 */

public class InResult {

    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}
