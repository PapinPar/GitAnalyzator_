
package chi_gitanalyz.gitanalyzator.retrofit.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

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
    @SerializedName("link")
    @Expose
    private String link;
    @SerializedName("webhook_name")
    @Expose
    private String webhookName;
    @SerializedName("gitlab_project_id")
    @Expose
    private Object gitlabProjectId;
    private List<Developer> developers = new ArrayList<Developer>();
    @SerializedName("branches")
    @Expose
    private List<Branch> branches = new ArrayList<Branch>();
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("hosting")
    @Expose
    private String hosting;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getLink() {
        return link;
    }

    public void setLink(String ling) {
        this.link = link;
    }

    public String getWebhookName() {
        return webhookName;
    }

    public void setWebhookName(String webhookName) {
        this.webhookName = webhookName;
    }

    public Object getGitlabProjectId() {
        return gitlabProjectId;
    }

    public void setGitlabProjectId(Object gitlabProjectId) {
        this.gitlabProjectId = gitlabProjectId;
    }

    public String getHosting() {
        return hosting;
    }

    public void setHosting(String hosting) {
        this.hosting = hosting;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }
}
