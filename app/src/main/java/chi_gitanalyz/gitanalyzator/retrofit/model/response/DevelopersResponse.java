
package chi_gitanalyz.gitanalyzator.retrofit.model.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import chi_gitanalyz.gitanalyzator.retrofit.model.data.Developer;

public class DevelopersResponse {

    @SerializedName("developers")
    @Expose
    private List<Developer> developers = new ArrayList<Developer>();

    public List<Developer> getDevelopers() {
        return developers;
    }

    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

}
