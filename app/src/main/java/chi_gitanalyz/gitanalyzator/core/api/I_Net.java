package chi_gitanalyz.gitanalyzator.core.api;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import chi_gitanalyz.gitanalyzator.core.observer.NetSubscriber;
import chi_gitanalyz.gitanalyzator.core.observer.Subjcet;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.CreateProject;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.InRequest;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signup.UpRequset;

/**
 * Created by Papin on 21.09.2016.
 */

public interface I_Net extends Subjcet<NetSubscriber> {

    @IntDef({Sign_UP, Sign_IN, Sign_OUT, Validate_Token, UPD_Profile,
            PROJECT_LIST, PROJECT_ANALYZ
            , ALL_DEV, CURR_DEV,
            CREATE_PROJECT, HOME_PROJECT, FILT_PROJECT, DEL_PROJECT, UPD_PROJECT})
    @interface NetEvent {
    }

    int Sign_UP = 101;
    int Sign_IN = 102;
    int Sign_OUT = 103;
    int Validate_Token = 104;
    int UPD_Profile = 105;

    int PROJECT_LIST = 106;
    int PROJECT_ANALYZ = 107;

    int ALL_DEV = 108;
    int CURR_DEV = 109;

    int CREATE_PROJECT = 200;
    int HOME_PROJECT = 201;
    int FILT_PROJECT = 202;
    int DEL_PROJECT = 203;
    int UPD_PROJECT = 204;

    //*************************************** AUTH ***************************************
    void signIN(@NonNull InRequest user);

    void signUP(@NonNull UpRequset user);

    void signOUT(@NonNull String token);

    void validateToken(@NonNull String token);

    //*************************************** PROJECTS ***************************************
    void projectHome(@NonNull String id, @NonNull String token);

    void projectFilter(@NonNull String id, @NonNull String token, Integer branch, Integer dev);

    void projectList(@NonNull String token);

    void createProject(@NonNull CreateProject project, @NonNull String token);

    void deleteProject(@NonNull String id, @NonNull String token);

    void updateProject(@NonNull String id, @NonNull CreateProject projectsID, @NonNull String token);

    //*************************************** DEVELOPERS ***************************************
    void getAllDev(@NonNull String token);


}
