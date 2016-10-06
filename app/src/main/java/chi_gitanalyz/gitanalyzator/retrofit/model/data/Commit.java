
package chi_gitanalyz.gitanalyzator.retrofit.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
    @SerializedName("score")
    @Expose
    private float score;
    @SerializedName("duplications")
    @Expose
    private Integer duplications;
    @SerializedName("smells")
    @Expose
    private Integer smells;

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

    public float getScore() {
        return score;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public Integer getDuplications() {
        return duplications;
    }

    public void setDuplications(Integer duplications) {
        this.duplications = duplications;
    }

    public Integer getSmells() {
        return smells;
    }

    public void setSmells(Integer smells) {
        this.smells = smells;
    }

}
