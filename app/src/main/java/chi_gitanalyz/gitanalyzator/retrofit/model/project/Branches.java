
package chi_gitanalyz.gitanalyzator.retrofit.model.project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import chi_gitanalyz.gitanalyzator.retrofit.model.developers.Commits;

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

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The projectId
     */
    public Integer getProjectId() {
        return projectId;
    }

    /**
     * 
     * @param projectId
     *     The project_id
     */
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    /**
     * 
     * @return
     *     The commits
     */
    public Commits getCommits() {
        return commits;
    }

    /**
     * 
     * @param commits
     *     The commits
     */
    public void setCommits(Commits commits) {
        this.commits = commits;
    }

}
