package chi_gitanalyz.gitanalyzator.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.core.api.Net;

/**
 * Created by Papin on 04.11.2016.
 */

public class ChoseAnalyzator extends BaseActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    private CheckBox RB, JS;
    private Button okBut;
    private Intent intent;
    private SharedPreferences sPref;
    private String id, tokenId;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chose_analyzator_layout);
        intent = getIntent();
        id = intent.getStringExtra("myID");

        RB = (CheckBox) findViewById(R.id.boxRuby);
        JS = (CheckBox) findViewById(R.id.boxJS);
        okBut = (Button) findViewById(R.id.butChoseAnalyz);

        RB.setOnCheckedChangeListener(this);
        JS.setOnCheckedChangeListener(this);

        okBut.setOnClickListener(this);
        okBut.setClickable(false);

        sPref = getSharedPreferences("TOKENS", MODE_PRIVATE);
        tokenId = sPref.getString("tokenId", "tokenId");

    }

    @Override
    public void onNetRequestDone(@Net.NetEvent int evetId, Object NetObjects) {
        super.onNetRequestDone(evetId, NetObjects);
        Toast.makeText(this, "Project will be analyzed", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onNetRequestFail(@Net.NetEvent int evetId, Object NetObjects) {
        super.onNetRequestFail(evetId, NetObjects);
        Toast.makeText(this, ""+NetObjects.toString(), Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.butChoseAnalyz:
                if (RB.isChecked() && JS.isChecked())
                    app.getNet().selectAnalyzator(Integer.valueOf(id), "[\"JS\",\"Ruby\"]", tokenId);
                if (RB.isChecked() && JS.isChecked() == false)
                    app.getNet().selectAnalyzator(Integer.valueOf(id), "[\"Ruby\"]", tokenId);
                if(JS.isChecked() && RB.isChecked()==false)
                    app.getNet().selectAnalyzator(Integer.valueOf(id), "[\"JS\"]", tokenId);
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.boxJS:
                if (RB.isChecked() == false && JS.isChecked() == false) {
                    okBut.setAlpha((float) 0.4);
                    okBut.setClickable(false);
                } else {
                    okBut.setAlpha(1);
                    okBut.setClickable(true);
                }
                break;
            case R.id.boxRuby:
                if (RB.isChecked() == false && JS.isChecked() == false) {
                    okBut.setAlpha((float) 0.4);
                    okBut.setClickable(false);
                } else {
                    okBut.setAlpha(1);
                    okBut.setClickable(true);
                }
                break;
        }
    }
}
