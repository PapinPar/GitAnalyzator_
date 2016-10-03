package chi_gitanalyz.gitanalyzator.ui.adapter.ad_dev;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import chi_gitanalyz.gitanalyzator.R;

/**
 * Created by Papin on 28.09.2016.
 */

public class DevAdapter extends RecyclerView.Adapter<DevAdapter.DevViewHolder> {

    public static class DevViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView devName;
        TextView email;
        TextView comitCount;
        TextView projects;

        DevViewHolder(View itemView){
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv_dev);
            devName = (TextView) itemView.findViewById(R.id.developer_name);
            email = (TextView) itemView.findViewById(R.id.dev_email);
            comitCount = (TextView) itemView.findViewById(R.id.comits_count);
            projects = (TextView) itemView.findViewById(R.id.Projects);

        }
    }

    List<DevelopersInfo> developersInfoList;

    public DevAdapter(List<DevelopersInfo> developersInfoList) {
        this.developersInfoList = developersInfoList;
    }

    @Override
    public DevViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_developers_view, parent, false);
        DevViewHolder devViewHolder = new DevViewHolder(v);
        return devViewHolder;
    }

    @Override
    public void onBindViewHolder(DevViewHolder holder, int i) {
        holder.devName.setText(developersInfoList.get(i).name);
        holder.email.setText("Email : "+developersInfoList.get(i).email);
        holder.comitCount.setText("Comits count : "+developersInfoList.get(i).comit_count);
        holder.projects.setText("Projects : "+developersInfoList.get(i).projectList);
    }

    @Override
    public int getItemCount() {
        return developersInfoList.size();
    }

}
