package chi_gitanalyz.gitanalyzator.ui.developer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.core.api.I_Net;
import chi_gitanalyz.gitanalyzator.retrofit.model.developers.Developers;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.project_id.Developer;
import chi_gitanalyz.gitanalyzator.ui.BaseActivity;
import chi_gitanalyz.gitanalyzator.ui.adapter.ad_dev.DevAdapter;
import chi_gitanalyz.gitanalyzator.ui.adapter.ad_dev.DevelopersInfo;

/**
 * Created by Papin on 28.09.2016.
 */

public class DevelopersActivity extends BaseActivity
{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    String TOKEN_ID;
    List<Developer> devList;
    private List<DevelopersInfo> DevelopersInfoList= new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developers_layout);

        Intent intent = getIntent();
        TOKEN_ID = intent.getStringExtra("TOKEN");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_developers);
        mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);

        loadDevelopers();
    }

    private void loadDevelopers() {
        if(isNetworkConnected()==true)
        app.getNet().getAllDev(TOKEN_ID);
        else{
            Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void fillListDevelopers(Developers netObjects) {
        devList = netObjects.getDevelopers();
        String projects="";
        for(int i =0;i<devList.size();i++) {
            for (int j = 0; j < devList.get(i).getProjects().size(); j++)
                projects = projects + devList.get(i).getProjects().get(j).getName()+"; ";
            DevelopersInfoList.add(new DevelopersInfo(devList.get(i).getCommitsCount(), devList.get(i).getEmail(), devList.get(i).getName(), projects));
            projects = "";
        }
        DevAdapter adapter = new DevAdapter(DevelopersInfoList);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onNetRequestDone(@I_Net.NetEvent int evetId, Object NetObjects) {
        switch (evetId){
            case I_Net.ALL_DEV:
                fillListDevelopers((Developers) NetObjects);
                break;
        }
    }

    @Override
    public void onNetRequestFail(@I_Net.NetEvent int evetId, Object NetObjects) {
    }
}
