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
import chi_gitanalyz.gitanalyzator.core.api.Net;
import chi_gitanalyz.gitanalyzator.retrofit.model.response.DevelopersResponse;
import chi_gitanalyz.gitanalyzator.retrofit.model.data.Developer;
import chi_gitanalyz.gitanalyzator.ui.BaseActivity;
import chi_gitanalyz.gitanalyzator.ui.adapter.DevAdapter;
import chi_gitanalyz.gitanalyzator.ui.adapter.DevelopersInfo;

/**
 * Created by Papin on 28.09.2016.
 */

public class DevelopersActivity extends BaseActivity
{
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;

    private String tokenId;
    private DevAdapter adapter;
    private List<Developer> devList;
    private List<DevelopersInfo> developersInfoList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.developers_layout);

        Intent intent = getIntent();
        tokenId = intent.getStringExtra("token");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_developers);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new DevAdapter(developersInfoList);
        recyclerView.setAdapter(adapter);

        loadDevelopers();
    }

    private void loadDevelopers() {
        if(isNetworkConnected())
        app.getNet().getAllDev(tokenId);
        else{
            Toast.makeText(this, "Chech our internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    private void fillListDevelopers(DevelopersResponse netObjects) {
        devList = netObjects.getDevelopers();
        String projects="";
        for(int i =0;i<devList.size();i++) {
            for (int j = 0; j < devList.get(i).getProjects().size(); j++)
                projects = projects + devList.get(i).getProjects().get(j).getName()+"; ";
            developersInfoList.add(new DevelopersInfo(devList.get(i).getCommitsCount(), devList.get(i).getEmail(), devList.get(i).getName(), projects));
            projects = "";
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNetRequestDone(@Net.NetEvent int evetId, Object NetObjects) {
        switch (evetId){
            case Net.ALL_DEV:
                fillListDevelopers((DevelopersResponse) NetObjects);
                break;
        }
    }

    @Override
    public void onNetRequestFail(@Net.NetEvent int evetId, Object NetObjects) {
    }
}
