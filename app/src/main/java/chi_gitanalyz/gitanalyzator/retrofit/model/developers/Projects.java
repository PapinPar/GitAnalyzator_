
package chi_gitanalyz.gitanalyzator.retrofit.model.developers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Projects {

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getSecretToken() {
        return secretToken;
    }

    public void setSecretToken(String secretToken) {
        this.secretToken = secretToken;
    }

    public String getSsh() {
        return ssh;
    }

    public void setSsh(String ssh) {
        this.ssh = ssh;
    }

    public String getWebhookName() {
        return webhookName;
    }

    public void setWebhookName(String webhookName) {
        this.webhookName = webhookName;
    }

    public Integer getGitlabProjectId() {
        return gitlabProjectId;
    }

    public void setGitlabProjectId(Integer gitlabProjectId) {
        this.gitlabProjectId = gitlabProjectId;
    }

    public Developers getDevelopers() {
        return developers;
    }

    public void setDevelopers(Developers developers) {
        this.developers = developers;
    }

    public Branches getBranches() {
        return branches;
    }

    public void setBranches(Branches branches) {
        this.branches = branches;
    }

}
