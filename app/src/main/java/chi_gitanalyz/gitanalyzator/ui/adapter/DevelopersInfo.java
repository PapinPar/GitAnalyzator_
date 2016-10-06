package chi_gitanalyz.gitanalyzator.ui.adapter;

/**
 * Created by Papin on 28.09.2016.
 */

public class DevelopersInfo {
   public String name;
   public int comit_count;
   public String email;
   public String projectList;

    public DevelopersInfo(int comit_count, String email, String name, String projectList) {
        this.comit_count = comit_count;
        this.email = email;
        this.name = name;
        this.projectList = projectList;
    }
}
