package chi_gitanalyz.gitanalyzator.db.sqlite.mapping;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.Nullable;

import chi_gitanalyz.gitanalyzator.db.sqlite.entity.ManagerEntity;
import chi_gitanalyz.gitanalyzator.db.sqlite.model.Manager;

/**
 * Created by Papin on 20.09.2016.
 */
public class ManagerMapper
{

    public static ContentValues parse(Manager user) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(ManagerEntity.OBJECT_ID, user.getObjectId());
        contentValues.put(ManagerEntity.TOKEN,user.getToken());
        contentValues.put(ManagerEntity.LOGIN, user.getLogin());
        contentValues.put(ManagerEntity.PASSWORD, user.getPassword());
        contentValues.put(ManagerEntity.EMAIL, user.getEmail());
        contentValues.put(ManagerEntity.NAME, user.getName());
        return contentValues;
    }

    @Nullable
    public static Manager parse(Cursor cursor) {
        if(cursor.moveToLast()) {
            Manager user = new Manager();
            user.setObjectId(cursor.getString(cursor.getColumnIndex(ManagerEntity.OBJECT_ID)));
            user.setToken(cursor.getString(cursor.getColumnIndex(ManagerEntity.TOKEN)));
            user.setLogin(cursor.getString(cursor.getColumnIndex(ManagerEntity.LOGIN)));
            user.setPassword(cursor.getString(cursor.getColumnIndex(ManagerEntity.PASSWORD)));
            user.setEmail(cursor.getString(cursor.getColumnIndex(ManagerEntity.EMAIL)));
            user.setName(cursor.getString(cursor.getColumnIndex(ManagerEntity.NAME)));
            return user;
        }
        return null;
    }
}
