package chi_gitanalyz.gitanalyzator.core.api;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import chi_gitanalyz.gitanalyzator.core.observer.NetSubscriber;
import chi_gitanalyz.gitanalyzator.core.observer.Subjcet;
import chi_gitanalyz.gitanalyzator.retrofit.model.request.CreateProjectRequest;
import chi_gitanalyz.gitanalyzator.retrofit.model.request.InRequest;
import chi_gitanalyz.gitanalyzator.retrofit.model.request.UpRequset;

/**
 * Created by Papin on 21.09.2016.
 */

public interface Net extends Subjcet<NetSubscriber> {

    @IntDef({Sign_UP, Sign_IN, Sign_OUT, VALIDATE_TOKEN, UPD_PROFILE,
            PROJECT_LIST, PROJECT_ANALYZ
            , ALL_DEV, CURR_DEV,
            CREATE_PROJECT, HOME_PROJECT, FILT_PROJECT, DEL_PROJECT, UPD_PROJECT})
    @interface NetEvent {
    }

    int Sign_UP = 101;
    int Sign_IN = 102;
    int Sign_OUT = 103;
    int VALIDATE_TOKEN = 104;
    int UPD_PROFILE = 105;

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

    void createProject(@NonNull CreateProjectRequest project, @NonNull String token);

    void deleteProject(@NonNull String id, @NonNull String token);

    void updateProject(@NonNull String id, @NonNull CreateProjectRequest projectsID, @NonNull String token);

    //*************************************** DEVELOPERS ***************************************
    void getAllDev(@NonNull String token);


}
