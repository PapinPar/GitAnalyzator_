package chi_gitanalyz.gitanalyzator.ui;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import java.util.ArrayList;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.home.Home;

/**
 * Created by Papin on 30.09.2016.
 */

public class FragmentDialog extends DialogFragment {


    Spinner SpinBranch;
    Spinner SpinFilt;
    Spinner SpinDev;
    Button butOK;

    Integer id_br, id_dev, id_filt;

    ArrayList<String> branch;
    ArrayList<String> dev;
    ArrayList<String> filter;
    ArrayList<Integer> branch_ID;
    ArrayList<Integer> dev_ID;

    private GetOnspinListner getOnspinListner;
    private Home projects;

    public interface GetOnspinListner {
        void getList(Integer dev, Integer branch, Integer filter);
    }

    public void getListner(GetOnspinListner getOnspinListner, Home projects) {
        this.getOnspinListner = getOnspinListner;
        this.projects = projects;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Chose filters");

        View v = inflater.inflate(R.layout.custom_dialog_layout, null);

        SpinBranch = (Spinner) v.findViewById(R.id.spinBranch_);
        SpinDev = (Spinner) v.findViewById(R.id.spinDev_);
        SpinFilt = (Spinner) v.findViewById(R.id.spinFilter_);

        dev = new ArrayList<>();
        filter = new ArrayList<>();
        branch = new ArrayList<>();

        dev_ID = new ArrayList<>();
        branch_ID = new ArrayList<>();

        dev.add("All developers");
        branch.add("All branches");

        filter.add("Score");
        filter.add("Duplications");
        filter.add("Smells");

        for (int i = 0; i < projects.getBranches().size(); i++) {
            branch.add(projects.getBranches().get(i).getName());
            branch_ID.add(projects.getBranches().get(i).getId());
        }

        for (int i = 0; i < projects.getDevelopers().size(); i++) {
            dev.add(projects.getDevelopers().get(i).getName());
            dev_ID.add(projects.getDevelopers().get(i).getId());
        }

        ArrayAdapter<String> adapterBranch = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, branch);
        adapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinBranch.setAdapter(adapterBranch);

        ArrayAdapter<String> adapterFilter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, filter);
        adapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinFilt.setAdapter(adapterFilter);

        ArrayAdapter<String> adapterDev = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, dev);
        adapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        SpinDev.setAdapter(adapterDev);

        SpinBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0)
                    id_br = branch_ID.get(position - 1);
                else
                    id_br = -5;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpinDev.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0)
                    id_dev = dev_ID.get(position - 1);
                else
                    id_dev = -5;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpinFilt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                id_filt = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        butOK = (Button) v.findViewById(R.id.butOK_dialog);
        v.findViewById(R.id.butOK_dialog).setOnClickListener((view) ->
                {
                    getOnspinListner.getList(id_br, id_dev, id_filt);
                    dismiss();
                }
        );

        return v;
    }

}
