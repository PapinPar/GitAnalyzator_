
package chi_gitanalyz.gitanalyzator.retrofit.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import chi_gitanalyz.gitanalyzator.retrofit.model.data.Branch;
import chi_gitanalyz.gitanalyzator.retrofit.model.data.Developer;
import chi_gitanalyz.gitanalyzator.retrofit.model.data.Language;
import chi_gitanalyz.gitanalyzator.retrofit.model.data.LanguagePercentage;

public class ProjectsIdResponse {

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

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("developers")
    @Expose
    private List<Developer> developers = new ArrayList<Developer>();

    @SerializedName("branches")
    @Expose
    private List<Branch> branches = new ArrayList<Branch>();
    @SerializedName("language_percentage")
    @Expose
    private LanguagePercentage languagePercentage;
    @SerializedName("languages")
    @Expose
    private ArrayList<Language> languages = new ArrayList<Language>();

    public ArrayList<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(ArrayList<Language> languages) {
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


    public String getLink() {
        return link;
    }

    public void setLink(String link) {
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

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LanguagePercentage getLanguagePercentage() {
        return languagePercentage;
    }

    public void setLanguagePercentage(LanguagePercentage languagePercentage) {
        this.languagePercentage = languagePercentage;
    }
}
