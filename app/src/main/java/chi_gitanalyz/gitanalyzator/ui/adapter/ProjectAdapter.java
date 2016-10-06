package chi_gitanalyz.gitanalyzator.ui.adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import chi_gitanalyz.gitanalyzator.R;

/**
 * Created by Papin on 27.09.2016.
 */
public class ProjectAdapter extends RecyclerView.Adapter<ProjectAdapter.PersonViewHolder> {


   private List<ProjectNames> persons;
   private NameOnClickListener click;

    public interface NameOnClickListener {
        void getPosition(int position);

        void getLongClick(int position);
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        private CardView cv;
        private TextView projectName;
        private TextView projectHost;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            projectName = (TextView) itemView.findViewById(R.id.project_name);
            projectHost = (TextView) itemView.findViewById(R.id.project_host);
        }
    }


    public ProjectAdapter(List<ProjectNames> persons, NameOnClickListener click) {
        this.persons = persons;
        this.click = click;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_project_view, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.projectName.setText(persons.get(i).name);
        personViewHolder.projectHost.setText(persons.get(i).hosting);

        personViewHolder.projectName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.getPosition(i);
            }
        });

        personViewHolder.projectName.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                click.getLongClick(i);
                return true;
            }
        });

    }

    @Override
    public int getItemCount() {
        return persons.size();
    }
}
