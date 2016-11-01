package chi_gitanalyz.gitanalyzator.retrofit.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Papin on 28.10.2016.
 */
public class Language implements Serializable {
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("percentage")
    @Expose
    private Float percentage;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("can_be_analyzed")
    @Expose
    private Boolean canBeAnalyzed;


    public Boolean getCanBeAnalyzed() {
        return canBeAnalyzed;
    }

    public void setCanBeAnalyzed(Boolean canBeAnalyzed) {
        this.canBeAnalyzed = canBeAnalyzed;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPercentage() {
        return percentage;
    }

    public void setPercentage(Float percentage) {
        this.percentage = percentage;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
