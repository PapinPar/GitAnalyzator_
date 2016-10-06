package chi_gitanalyz.gitanalyzator.retrofit.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import chi_gitanalyz.gitanalyzator.retrofit.model.data.User;
import chi_gitanalyz.gitanalyzator.retrofit.model.response.UpResponse;

/**
 * Created by Papin on 23.09.2016.
 */

public class UpRequset extends UpResponse {
    @SerializedName("user")
    @Expose
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
