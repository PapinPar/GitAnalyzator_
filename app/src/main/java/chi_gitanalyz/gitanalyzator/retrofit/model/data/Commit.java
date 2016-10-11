
package chi_gitanalyz.gitanalyzator.retrofit.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Commit {

    @SerializedName("git_id")
    @Expose
    private String gitId;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("developer_id")
    @Expose
    private Integer developerId;
    @SerializedName("project_id")
    @Expose
    private Integer projectId;
    @SerializedName("branch_id")
    @Expose
    private Integer branchId;
    @SerializedName("analytics")
    @Expose
    private List<Analytic> analytics = new ArrayList<Analytic>();

    public List<Analytic> getAnalytics() {
        return analytics;
    }

    public void setAnalytics(List<Analytic> analytics) {
        this.analytics = analytics;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Integer developerId) {
        this.developerId = developerId;
    }

    public String getGitId() {
        return gitId;
    }

    public void setGitId(String gitId) {
        this.gitId = gitId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
