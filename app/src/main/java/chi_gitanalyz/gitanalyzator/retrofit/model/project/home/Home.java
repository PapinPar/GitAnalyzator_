
package chi_gitanalyz.gitanalyzator.retrofit.model.project.home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Home {

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
    private List<Branch_h> branches = new ArrayList<Branch_h>();
    @SerializedName("developers")
    @Expose
    private List<Developer_h> developers = new ArrayList<Developer_h>();

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
     *     The branches
     */
    public List<Branch_h> getBranches() {
        return branches;
    }

    /**
     * 
     * @param branches
     *     The branches
     */
    public void setBranches(List<Branch_h> branches) {
        this.branches = branches;
    }

    /**
     * 
     * @return
     *     The developers
     */
    public List<Developer_h> getDevelopers() {
        return developers;
    }

    /**
     * 
     * @param developers
     *     The developers
     */
    public void setDevelopers(List<Developer_h> developers) {
        this.developers = developers;
    }

}
