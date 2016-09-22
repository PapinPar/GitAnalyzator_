
package chi_gitanalyz.gitanalyzator.retrofit.model.developers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Branches {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("project_id")
    @Expose
    private Integer projectId;
    @SerializedName("commits")
    @Expose
    private Commits commits;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Commits getCommits() {
        return commits;
    }

    public void setCommits(Commits commits) {
        this.commits = commits;
    }

}
