package chi_gitanalyz.gitanalyzator.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import chi_gitanalyz.gitanalyzator.R;

/**
 * Created by Papin on 04.11.2016.
 */

public class ChoseAnalyzator extends BaseActivity implements View.OnClickListener,CompoundButton.OnCheckedChangeListener {
    private CheckBox RB, JS;
    private Button okBut;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chose_analyzator_layout);

        RB = (CheckBox) findViewById(R.id.boxRuby);
        JS = (CheckBox) findViewById(R.id.boxJS);
        okBut = (Button) findViewById(R.id.butChoseAnalyz);

        RB.setOnCheckedChangeListener(this);
        JS.setOnCheckedChangeListener(this);

        okBut.setOnClickListener(this);
        okBut.setClickable(false);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.butChoseAnalyz:
                Toast.makeText(this, "OK", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId())
        {
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
