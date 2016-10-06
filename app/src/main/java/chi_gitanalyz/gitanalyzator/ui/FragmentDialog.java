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
import chi_gitanalyz.gitanalyzator.retrofit.model.response.HomeResponse;

/**
 * Created by Papin on 30.09.2016.
 */

public class FragmentDialog extends DialogFragment {


    private Spinner spinBranch;
    private Spinner spinFilt;
    private Spinner spinDev;
    private Button butOK;

    private Integer idBr, idDev, idFilt;

    private ArrayList<String> branch;
    private ArrayList<String> dev;
    private ArrayList<String> filter;
    private ArrayList<Integer> branchID;
    private ArrayList<Integer> devID;

    private GetOnspinListner getOnspinListner;
    private HomeResponse projects;

    public interface GetOnspinListner {
        void getList(Integer dev, Integer branch, Integer filter);
    }

    public void getListner(GetOnspinListner getOnspinListner, HomeResponse projects) {
        this.getOnspinListner = getOnspinListner;
        this.projects = projects;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Chose filters");

        View v = inflater.inflate(R.layout.custom_dialog_layout, null);

        spinBranch = (Spinner) v.findViewById(R.id.spinBranch_);
        spinDev = (Spinner) v.findViewById(R.id.spinDev_);
        spinFilt = (Spinner) v.findViewById(R.id.spinFilter_);

        dev = new ArrayList<>();
        filter = new ArrayList<>();
        branch = new ArrayList<>();

        devID = new ArrayList<>();
        branchID = new ArrayList<>();

        dev.add("All developers");
        branch.add("All branches");

        filter.add("Score");
        filter.add("Duplications");
        filter.add("Smells");

        for (int i = 0; i < projects.getBranches().size(); i++) {
            branch.add(projects.getBranches().get(i).getName());
            branchID.add(projects.getBranches().get(i).getId());
        }

        for (int i = 0; i < projects.getDevelopers().size(); i++) {
            dev.add(projects.getDevelopers().get(i).getName());
            devID.add(projects.getDevelopers().get(i).getId());
        }

        ArrayAdapter<String> adapterBranch = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, branch);
        adapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinBranch.setAdapter(adapterBranch);

        ArrayAdapter<String> adapterFilter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, filter);
        adapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinFilt.setAdapter(adapterFilter);

        ArrayAdapter<String> adapterDev = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, dev);
        adapterBranch.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinDev.setAdapter(adapterDev);

        spinBranch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0)
                    idBr = branchID.get(position - 1);
                else
                    idBr = -5;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinDev.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0)
                    idDev = devID.get(position - 1);
                else
                    idDev = -5;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinFilt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                idFilt = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        butOK = (Button) v.findViewById(R.id.butOK_dialog);
        v.findViewById(R.id.butOK_dialog).setOnClickListener((view) ->
                {
                    getOnspinListner.getList(idBr, idDev, idFilt);
                    dismiss();
                }
        );

        return v;
    }

}
