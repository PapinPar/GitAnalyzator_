package chi_gitanalyz.gitanalyzator.ui.adapter;

/**
 * Created by Papin on 27.09.2016.
 */

public class ProjectNames {
    public String name;
    public String hosting;
    public int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ProjectNames(String name,String hosting,int id) {

        this.hosting = hosting;
        this.id = id;
        this.name = name;
    }
}
