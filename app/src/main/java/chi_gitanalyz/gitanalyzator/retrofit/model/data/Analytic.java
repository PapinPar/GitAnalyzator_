package chi_gitanalyz.gitanalyzator.retrofit.model.data;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Papin on 07.10.2016.
 */
public class Analytic {

    @SerializedName("score")
    @Expose
    private Float score;
    @SerializedName("smells")
    @Expose
    private Integer smells;
    @SerializedName("duplications")
    @Expose
    private Integer duplications;
    @SerializedName("language")
    @Expose
    private String language;

    public Integer getDuplications() {
        return duplications;
    }

    public void setDuplications(Integer duplications) {
        this.duplications = duplications;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    public Integer getSmells() {
        return smells;
    }

    public void setSmells(Integer smells) {
        this.smells = smells;
    }
}
