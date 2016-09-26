package chi_gitanalyz.gitanalyzator.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import chi_gitanalyz.gitanalyzator.R;

/**
 * Created by Papin on 26.09.2016.
 */

public class ProjectsActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.project_layout);


    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item1:
                Toast.makeText(this, "You selected the camera option", Toast.LENGTH_SHORT).show();

                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
