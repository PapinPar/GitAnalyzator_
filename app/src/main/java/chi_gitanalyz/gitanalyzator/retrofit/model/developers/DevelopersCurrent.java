package chi_gitanalyz.gitanalyzator.retrofit.model.developers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Papin on 22.09.2016.
 */

public class DevelopersCurrent {

    @SerializedName("developers")
    @Expose
    private List<Developers > developers = new ArrayList<Developers>();

    public List<Developers> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developers> developers) {
        this.developers = developers;
    }

}