package chi_gitanalyz.gitanalyzator.retrofit.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import chi_gitanalyz.gitanalyzator.retrofit.model.response.InResponse;
import chi_gitanalyz.gitanalyzator.retrofit.model.data.User;

/**
 * Created by Papin on 22.09.2016.
 */

public class InRequest extends InResponse {

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
