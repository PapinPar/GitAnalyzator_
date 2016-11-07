package chi_gitanalyz.gitanalyzator.ui.project;

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
    private Spinner spinLang;
    private Button butOK;

    private Integer idBr, idDev, idFilt;
    private Integer[] idLeng;
    private String selectedLenguage;

    private ArrayList<String> branch;
    private ArrayList<String> dev;
    private ArrayList<String> filter;
    private ArrayList<Integer> branchID;
    private ArrayList<Integer> devID;
    private ArrayList<String> language;

    private GetOnSpinListner getOnSpinListner;
    private HomeResponse projects;

    public interface GetOnSpinListner {
        void getList(Integer dev, Integer branch, Integer filter, String language);
    }

    public void getListner(GetOnSpinListner getOnSpinListner, HomeResponse projects) {
        this.getOnSpinListner = getOnSpinListner;
        this.projects = projects;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getDialog().setTitle("Chose filters");

        View v = inflater.inflate(R.layout.custom_dialog_layout, null);

        spinBranch = (Spinner) v.findViewById(R.id.spinBranch_);
        spinDev = (Spinner) v.findViewById(R.id.spinDev_);
        spinFilt = (Spinner) v.findViewById(R.id.spinFilter_);
        spinLang = (Spinner) v.findViewById(R.id.spinLanguage_);

        dev = new ArrayList<>();
        filter = new ArrayList<>();
        branch = new ArrayList<>();
        language = new ArrayList<>();

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

        idLeng = new Integer[projects.getLanguages().size()];
        int tmp = 0;
        for (int i = 0; i < projects.getLanguages().size(); i++) {
            if (projects.getLanguages().get(i).getStatus().equals("analyzed")) {
                language.add(String.valueOf(projects.getLanguages().get(i).getName()));
                idLeng[tmp] = i;
                tmp++;
            }
        }

        ArrayAdapter<String> adapterBranch = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_dropdown_item_1line, branch);
        adapterBranch.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinBranch.setAdapter(adapterBranch);

        ArrayAdapter<String> adapterFilter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_dropdown_item_1line, filter);
        adapterBranch.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinFilt.setAdapter(adapterFilter);

        ArrayAdapter<String> adapterDev = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_dropdown_item_1line, dev);
        adapterBranch.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinDev.setAdapter(adapterDev);

        ArrayAdapter<String> adapterLanguage = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_dropdown_item_1line, language);
        adapterLanguage.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinLang.setAdapter(adapterLanguage);


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

        spinLang.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //  idLeng = position;
                selectedLenguage = projects.getLanguages().get(idLeng[position]).getName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        butOK = (Button) v.findViewById(R.id.butOK_dialog);
        v.findViewById(R.id.butOK_dialog).setOnClickListener((view) ->
                {
                    getOnSpinListner.getList(idBr, idDev, idFilt, selectedLenguage);
                    dismiss();
                }
        );

        return v;
    }

}
