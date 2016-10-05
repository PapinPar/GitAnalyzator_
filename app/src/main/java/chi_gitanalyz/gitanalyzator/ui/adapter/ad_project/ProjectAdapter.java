package chi_gitanalyz.gitanalyzator.ui.adapter.ad_project;

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

    public interface NameOnClickListener {
        void getPosition(int position);

        void getLongClick(int position);
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView personName;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            personName = (TextView) itemView.findViewById(R.id.developer_name);
        }
    }

    List<ProjectNames> persons;
    NameOnClickListener click;

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
        personViewHolder.personName.setText(persons.get(i).name);

        personViewHolder.personName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click.getPosition(i);
            }
        });

        personViewHolder.personName.setOnLongClickListener(new View.OnLongClickListener() {
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
