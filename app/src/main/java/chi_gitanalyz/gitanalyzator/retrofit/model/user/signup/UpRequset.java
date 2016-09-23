package chi_gitanalyz.gitanalyzator.retrofit.model.user.signup;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.User;

/**
 * Created by Papin on 23.09.2016.
 */

public class UpRequset extends UpResult {
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
