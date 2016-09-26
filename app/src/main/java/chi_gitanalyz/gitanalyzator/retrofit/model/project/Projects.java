
package chi_gitanalyz.gitanalyzator.retrofit.model.project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Projects {

    @SerializedName("projects")
    @Expose
    private List<Project> projects = new ArrayList<Project>();

    /**
     * 
     * @return
     *     The projects
     */
    public List<Project> getProjects() {
        return projects;
    }

    /**
     * 
     * @param projects
     *     The projects
     */
    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

}
