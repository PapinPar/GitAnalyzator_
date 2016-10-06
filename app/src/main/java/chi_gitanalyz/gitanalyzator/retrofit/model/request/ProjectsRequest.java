
package chi_gitanalyz.gitanalyzator.retrofit.model.request;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import chi_gitanalyz.gitanalyzator.retrofit.model.data.Project;

public class ProjectsRequest {

    @SerializedName("projects")
    @Expose
    private List<Project> projects = new ArrayList<Project>();

    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

}
