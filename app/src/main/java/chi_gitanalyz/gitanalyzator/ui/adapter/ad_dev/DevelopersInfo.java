package chi_gitanalyz.gitanalyzator.ui.adapter.ad_dev;

/**
 * Created by Papin on 28.09.2016.
 */

public class DevelopersInfo {
    String name;
    int comit_count;
    String email;
    String projectList;

    public DevelopersInfo(int comit_count, String email, String name, String projectList) {
        this.comit_count = comit_count;
        this.email = email;
        this.name = name;
        this.projectList = projectList;
    }
}
