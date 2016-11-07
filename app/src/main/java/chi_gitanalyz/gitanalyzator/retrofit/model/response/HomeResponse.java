
package chi_gitanalyz.gitanalyzator.retrofit.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import chi_gitanalyz.gitanalyzator.retrofit.model.data.BranchHome;
import chi_gitanalyz.gitanalyzator.retrofit.model.data.DeveloperHome;
import chi_gitanalyz.gitanalyzator.retrofit.model.data.Language;

public class HomeResponse {

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
    @SerializedName("branches")
    @Expose
    private List<BranchHome> branches = new ArrayList<BranchHome>();
    @SerializedName("developers")
    @Expose
    private List<DeveloperHome> developers = new ArrayList<DeveloperHome>();
    @SerializedName("languages")
    @Expose
    private List<Language> languages = new ArrayList<Language>();

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
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

    public List<BranchHome> getBranches() {
        return branches;
    }

    public void setBranches(List<BranchHome> branches) {
        this.branches = branches;
    }

    public List<DeveloperHome> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<DeveloperHome> developers) {
        this.developers = developers;
    }

}
