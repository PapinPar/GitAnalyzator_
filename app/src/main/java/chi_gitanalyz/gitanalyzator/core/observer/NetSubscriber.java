package chi_gitanalyz.gitanalyzator.core.observer;

import chi_gitanalyz.gitanalyzator.core.api.I_Net;

/**
 * Created by Papin on 22.09.2016.
 */

public interface NetSubscriber extends Subscriber {

    void onNetRequestDone(@I_Net.NetEvent int evetId, Object NetObjects);

    void onNetRequestFail(@I_Net.NetEvent int evetId, Object NetObjects);
}
