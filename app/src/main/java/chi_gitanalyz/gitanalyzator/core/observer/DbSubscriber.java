package chi_gitanalyz.gitanalyzator.core.observer;

import chi_gitanalyz.gitanalyzator.core.api.I_Db;

/**
 * Created by Papin on 23.09.2016.
 */

public interface DbSubscriber extends Subscriber {
    void onDbDataUpdated(@I_Db.DbEvent int tableId, Object dbObject);

    void onDbErrorError(@I_Db.DbEvent int tableId, Object error);
}
