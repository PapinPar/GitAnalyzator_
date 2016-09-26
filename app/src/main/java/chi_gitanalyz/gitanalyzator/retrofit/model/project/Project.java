
package chi_gitanalyz.gitanalyzator.retrofit.model.project;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Project {

    @SerializedName("id")
    @Expose
    private Integer id;
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
    private Object gitlabProjectId;

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
    public Object getGitlabProjectId() {
        return gitlabProjectId;
    }

    /**
     * 
     * @param gitlabProjectId
     *     The gitlab_project_id
     */
    public void setGitlabProjectId(Object gitlabProjectId) {
        this.gitlabProjectId = gitlabProjectId;
    }

}
