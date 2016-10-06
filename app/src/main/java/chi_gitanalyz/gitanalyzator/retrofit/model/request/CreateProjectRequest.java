package chi_gitanalyz.gitanalyzator.retrofit.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import chi_gitanalyz.gitanalyzator.retrofit.model.data.Project;

/**
 * Created by Papin on 30.09.2016.
 */

public class CreateProjectRequest {
    public Project getUser() {
        return user;
    }

    public void setUser(Project user) {
        this.user = user;
    }

    @SerializedName("project")
    @Expose
    private Project user;
}
