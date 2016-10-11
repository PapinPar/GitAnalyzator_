
package chi_gitanalyz.gitanalyzator.retrofit.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Branch {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("project_id")
    @Expose
    private Integer projectId;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("commits")
    @Expose
    private List<Commit> commits = new ArrayList<Commit>();

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

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

    public List<Commit> getCommits() {
        return commits;
    }

    public void setCommits(List<Commit> commits) {
        this.commits = commits;
    }

}
