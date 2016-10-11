package chi_gitanalyz.gitanalyzator.service;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Papin on 07.10.2016.
 */

public class MessageNotif {

    @SerializedName("project_id")
    @Expose
    private String project_id;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("project_name")
    @Expose
    private String project_name;

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }
}
