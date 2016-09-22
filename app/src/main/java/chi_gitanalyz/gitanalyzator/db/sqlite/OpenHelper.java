package chi_gitanalyz.gitanalyzator.db.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.db.sqlite.entity.ManagerEntity;

/**
 * Created by Papin on 20.09.2016.
 */
public class OpenHelper extends SQLiteOpenHelper
{
    public OpenHelper(Context context)
    {
        super(context, context.getString(R.string.db_name),null,
                context.getResources().getInteger(R.integer.db_version));
    }
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL(ManagerEntity.CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
