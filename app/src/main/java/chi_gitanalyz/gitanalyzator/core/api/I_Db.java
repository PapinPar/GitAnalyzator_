package chi_gitanalyz.gitanalyzator.core.api;

import android.support.annotation.IntDef;

import chi_gitanalyz.gitanalyzator.core.observer.DbSubscriber;
import chi_gitanalyz.gitanalyzator.core.observer.Subjcet;
import chi_gitanalyz.gitanalyzator.db.sqlite.model.Manager;

/**
 * Created by Papin on 21.09.2016.
 */

public interface I_Db extends Subjcet<DbSubscriber>
{
    @IntDef({USER_UPD, USER_LOAD})
    @interface DbEvent {

    }

    int USER_UPD = 101;
    int USER_LOAD = 102;

    void saveUser(Manager user);

    void loadUser();
}
