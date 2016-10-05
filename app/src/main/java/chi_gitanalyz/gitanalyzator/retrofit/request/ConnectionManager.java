package chi_gitanalyz.gitanalyzator.retrofit.request;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import chi_gitanalyz.gitanalyzator.core.api.I_Net;
import chi_gitanalyz.gitanalyzator.core.observer.NetSubscriber;
import chi_gitanalyz.gitanalyzator.retrofit.RestApiWrapper;
import chi_gitanalyz.gitanalyzator.retrofit.model.developers.Developers;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.CreateProject;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.Projects;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.home.Home;
import chi_gitanalyz.gitanalyzator.retrofit.model.project.project_id.ProjectsID;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.InRequest;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.InResult;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.User;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signup.UpRequset;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signup.UpResult;
import retrofit2.Response;

/**
 * Created by Papin on 23.09.2016.
 */

public class ConnectionManager implements I_Net {

    private final List<NetSubscriber> observers = new ArrayList<>();
    private Handler mHandler;
    Executor executor;

    public ConnectionManager(Executor executor) {
        this.executor = executor;
        mHandler = new Handler(Looper.getMainLooper());
    }

    @Override
    public void signIN(@NonNull InRequest user) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<InRequest> response = RestApiWrapper.getInstance().signIn(user);
                    if (response.isSuccessful()) {
                        InResult result = response.body();
                        notifySuccessSubscribers(Sign_IN, result);
                    } else {
                        String message = response.raw().message();
                        notifyErrorSubscribers(Sign_IN, message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void signUP(@NonNull UpRequset user) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<UpRequset> response = RestApiWrapper.getInstance().signUp(user);
                    if (response.isSuccessful()) {
                        UpResult result = response.body();
                        notifySuccessSubscribers(Sign_UP, result);
                    } else {
                        String message = response.raw().message();
                        notifyErrorSubscribers(Sign_UP, message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void signOUT(@NonNull String token) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<String> response = RestApiWrapper.getInstance().signOut(token);
                    if (response.isSuccessful()) {
                        notifySuccessSubscribers(Sign_OUT, "OK");
                    } else {
                        String message = response.raw().message();
                        notifyErrorSubscribers(Sign_OUT, message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void validateToken(@NonNull String token) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<User> response = RestApiWrapper.getInstance().validateToken(token);
                    if (response.isSuccessful()) {
                        User result = response.body();
                        notifySuccessSubscribers(Validate_Token, result);
                    } else {
                        String message = response.raw().message();
                        notifyErrorSubscribers(Validate_Token, message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void projectHome(@NonNull String id, @NonNull String token) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<Home> response = RestApiWrapper.getInstance().getHome(id, token);
                    if (response.isSuccessful()) {
                        Home resp = response.body();
                        notifySuccessSubscribers(HOME_PROJECT, resp);
                    }
                    else {
                        notifyErrorSubscribers(HOME_PROJECT,response.raw().message().toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    public void projectFilter(@NonNull String id, @NonNull String token, @NonNull Integer branch, @NonNull Integer dev) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<ProjectsID> response = RestApiWrapper.getInstance().projectFilter(id, token, branch, dev);
                    if (response.isSuccessful()) {
                        ProjectsID result = response.body();
                        notifySuccessSubscribers(FILT_PROJECT, result);
                    }
                    else
                    {
                        notifyErrorSubscribers(FILT_PROJECT,response.raw().message().toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void projectList(@NonNull String token) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<Projects> response = RestApiWrapper.getInstance().projectList(token);
                    if (response.isSuccessful()) {
                        Projects result = response.body();
                        notifySuccessSubscribers(PROJECT_LIST, result);
                    }
                    else{
                        notifyErrorSubscribers(PROJECT_LIST,response.raw().message().toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void createProject(@NonNull CreateProject project, @NonNull String token) {
        executor.execute(new Runnable() {
            @Override
            public void run() {

                try {
                    Response<CreateProject> response = RestApiWrapper.getInstance().createProject(project, token);
                    if (response.isSuccessful()) {
                        notifySuccessSubscribers(CREATE_PROJECT, response);
                    }
                    else
                    {
                        String message = response.raw().message().toString();
                        notifyErrorSubscribers(CREATE_PROJECT,message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });
    }

    @Override
    public void deleteProject(@NonNull String id, @NonNull String token) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<String> response = RestApiWrapper.getInstance().deleteProject(id, token);
                    if (response.isSuccessful()) {
                        notifySuccessSubscribers(DEL_PROJECT, response);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void updateProject(@NonNull String id, @NonNull CreateProject projectsID, @NonNull String token) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<CreateProject> response = RestApiWrapper.getInstance().updateProject(id, projectsID, token);
                    if (response.isSuccessful()) {
                        CreateProject result = response.body();
                        notifySuccessSubscribers(UPD_PROJECT, result);
                    } else {
                        String message = response.raw().message();
                        notifyErrorSubscribers(UPD_PROJECT, message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void getAllDev(@NonNull String token) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<Developers> response = RestApiWrapper.getInstance().getAllDev(token);
                    if (response.isSuccessful()) {
                        Developers result = response.body();
                        notifySuccessSubscribers(ALL_DEV, result);
                    }
                    else {
                        notifyErrorSubscribers(ALL_DEV,response.raw().message().toString());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void Subscribe(NetSubscriber observer) {
        if (!observers.contains(observer))
            observers.add(observer);
    }

    @Override
    public void UnSubscribe(NetSubscriber observer) {
        if (observers.contains(observer))
            observers.remove(observer);

    }

    @Override
    public boolean IsSubscribe(NetSubscriber observer) {
        return observers.contains(observer);
    }

    @Override
    public void notifySuccessSubscribers(int eventId, Object object) {
        for (NetSubscriber observer : observers)
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    observer.onNetRequestDone(eventId, object);
                }
            });
    }

    @Override
    public void notifyErrorSubscribers(int eventId, Object object) {
        for (NetSubscriber observer : observers)
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    observer.onNetRequestFail(eventId, object);
                }
            });
    }
}
