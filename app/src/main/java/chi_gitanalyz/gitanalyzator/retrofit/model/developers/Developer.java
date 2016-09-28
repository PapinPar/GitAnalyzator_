
package chi_gitanalyz.gitanalyzator.retrofit.model.developers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import chi_gitanalyz.gitanalyzator.retrofit.model.project.Project;

public class Developer {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("projects")
    @Expose
    private List<Project> projects = new ArrayList<Project>();
    @SerializedName("commits_count")
    @Expose
    private Integer commitsCount;

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

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
     *     The email
     */
    public String getEmail() {
        return email;
    }

    /**
     * 
     * @param email
     *     The email
     */
    public void setEmail(String email) {
        this.email = email;
    }

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

    /**
     * 
     * @return
     *     The commitsCount
     */
    public Integer getCommitsCount() {
        return commitsCount;
    }

    /**
     * 
     * @param commitsCount
     *     The commits_count
     */
    public void setCommitsCount(Integer commitsCount) {
        this.commitsCount = commitsCount;
    }

}
