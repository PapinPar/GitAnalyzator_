package chi_gitanalyz.gitanalyzator.core.observer;

import chi_gitanalyz.gitanalyzator.core.api.Net;

/**
 * Created by Papin on 22.09.2016.
 */

public interface NetSubscriber extends Subscriber {

    void onNetRequestDone(@Net.NetEvent int evetId, Object NetObjects);

    void onNetRequestFail(@Net.NetEvent int evetId, Object NetObjects);
}
