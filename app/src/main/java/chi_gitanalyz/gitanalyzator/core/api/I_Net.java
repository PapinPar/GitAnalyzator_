package chi_gitanalyz.gitanalyzator.core.api;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import chi_gitanalyz.gitanalyzator.core.observer.NetSubscriber;
import chi_gitanalyz.gitanalyzator.core.observer.Subjcet;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.InRequest;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signup.UpRequset;

/**
 * Created by Papin on 21.09.2016.
 */

public interface I_Net extends Subjcet<NetSubscriber> {

    @IntDef({Sign_UP,Sign_IN,Sign_OUT, Validate_Token,UPD_Profile})
    @interface NetEvent{}

    int Sign_UP = 101;
    int Sign_IN = 102;
    int Sign_OUT = 103;
    int Validate_Token = 104;
    int UPD_Profile = 105;

    void signIN(@NonNull InRequest user);
    void signUP(@NonNull UpRequset user);


}
