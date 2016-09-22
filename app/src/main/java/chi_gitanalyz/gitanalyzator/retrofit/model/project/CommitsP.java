
package chi_gitanalyz.gitanalyzator.retrofit.model.project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CommitsP {

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
    @SerializedName("score")
    @Expose
    private Double score;

    public String getGitId() {
        return gitId;
    }

    public void setGitId(String gitId) {
        this.gitId = gitId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getDeveloperId() {
        return developerId;
    }

    public void setDeveloperId(Integer developerId) {
        this.developerId = developerId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }

}
