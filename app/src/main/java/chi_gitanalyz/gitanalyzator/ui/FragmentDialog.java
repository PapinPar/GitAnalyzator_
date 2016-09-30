package chi_gitanalyz.gitanalyzator.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.Projects;
import chi_gitanalyz.gitanalyzator.ui.adapter.ad_project.ProjectNames;

/**
 * Created by Papin on 30.09.2016.
 */

public class FragmentDialog extends DialogFragment {

    private GetOnspinListner getOnspinListner;
    private Projects projects;
    private List<ProjectNames> projectNames = new ArrayList<>();

    public interface GetOnspinListner {
        void getList(String project, String dev, String branch, String filter);
    }

    public void getListner(GetOnspinListner getOnspinListner, Projects projects) {
        this.getOnspinListner = getOnspinListner;
        this.projects = projects;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Chose filters");

        View v = inflater.inflate(R.layout.custom_dialog_layout, null);

       /* List<Project> projectList;
        projectList = projects.getProjects();
        for (int i = 0; i < projectList.size(); i++)
            projectNames.add(new ProjectNames(projectList.get(i).getName()));
        ProjectAdapter adapter = new ProjectAdapter(projectNames, this);
        recyclerView.setAdapter(adapter);*/

        // getOnspinListner.getList();  // передача на активити

        return v;
    }

}
