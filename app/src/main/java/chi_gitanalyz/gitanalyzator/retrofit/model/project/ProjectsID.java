
package chi_gitanalyz.gitanalyzator.retrofit.model.project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ProjectsID {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("user_id")
    @Expose
    private Integer userId;
    @SerializedName("secret_token")
    @Expose
    private String secretToken;
    @SerializedName("ssh")
    @Expose
    private String ssh;
    @SerializedName("webhook_name")
    @Expose
    private String webhookName;
    @SerializedName("gitlab_project_id")
    @Expose
    private Integer gitlabProjectId;
    @SerializedName("developers")
    @Expose
    private Developers developers;
    @SerializedName("branches")
    @Expose
    private Branches branches;

    /**
     * 
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(String id) {
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
     *     The userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 
     * @param userId
     *     The user_id
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * 
     * @return
     *     The secretToken
     */
    public String getSecretToken() {
        return secretToken;
    }

    /**
     * 
     * @param secretToken
     *     The secret_token
     */
    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }

    /**
     * 
     * @return
     *     The ssh
     */
    public String getSsh() {
        return ssh;
    }

    /**
     * 
     * @param ssh
     *     The ssh
     */
    public void setSsh(String ssh) {
        this.ssh = ssh;
    }

    /**
     * 
     * @return
     *     The webhookName
     */
    public String getWebhookName() {
        return webhookName;
    }

    /**
     * 
     * @param webhookName
     *     The webhook_name
     */
    public void setWebhookName(String webhookName) {
        this.webhookName = webhookName;
    }

    /**
     * 
     * @return
     *     The gitlabProjectId
     */
    public Integer getGitlabProjectId() {
        return gitlabProjectId;
    }

    /**
     * 
     * @param gitlabProjectId
     *     The gitlab_project_id
     */
    public void setGitlabProjectId(Integer gitlabProjectId) {
        this.gitlabProjectId = gitlabProjectId;
    }

    /**
     * 
     * @return
     *     The developers
     */
    public Developers getDevelopers() {
        return developers;
    }

    /**
     * 
     * @param developers
     *     The developers
     */
    public void setDevelopers(Developers developers) {
        this.developers = developers;
    }

    /**
     * 
     * @return
     *     The branches
     */
    public Branches getBranches() {
        return branches;
    }

    /**
     * 
     * @param branches
     *     The branches
     */
    public void setBranches(Branches branches) {
        this.branches = branches;
    }

}
