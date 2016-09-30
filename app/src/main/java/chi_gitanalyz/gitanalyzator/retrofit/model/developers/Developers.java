
package chi_gitanalyz.gitanalyzator.retrofit.model.developers;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import chi_gitanalyz.gitanalyzator.retrofit.model.project.project_id.Developer;

public class Developers {

    @SerializedName("developers")
    @Expose
    private List<Developer> developers = new ArrayList<Developer>();

    /**
     * 
     * @return
     *     The developers
     */
    public List<Developer> getDevelopers() {
        return developers;
    }

    /**
     * 
     * @param developers
     *     The developers
     */
    public void setDevelopers(List<Developer> developers) {
        this.developers = developers;
    }

}
