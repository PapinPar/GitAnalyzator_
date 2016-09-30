package chi_gitanalyz.gitanalyzator.retrofit.model.project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Papin on 30.09.2016.
 */

public class CreateProject {
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
