package chi_gitanalyz.gitanalyzator.db.sqlite.entity;

/**
 * Created by Papin on 20.09.2016.
 */
public class ManagerEntity
{
    public static final String TABLE_NAME = "Manager";
    public static final String OBJECT_ID= "objectId";
    public final static String NAME = "name";
    public final static String TOKEN = "token";
    public final static String LOGIN = "login";
    public final static String EMAIL = "email";
    public final static String PASSWORD = "password";

    public final static String CREATE_SCRIPT = "CREATE TABLE " + TABLE_NAME +" ("+
            OBJECT_ID + " TEXT PRIMARY KEY, " +
            NAME + " TEXT, " +
            TOKEN + " TEXT, " +
            LOGIN + " TEXT, " +
            EMAIL + " TEXT, " +
            PASSWORD + " TEXT " +
            ");";

}
