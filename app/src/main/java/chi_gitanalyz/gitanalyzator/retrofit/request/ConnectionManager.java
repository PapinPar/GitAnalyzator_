package chi_gitanalyz.gitanalyzator.retrofit.request;

import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;

import chi_gitanalyz.gitanalyzator.core.api.I_Net;
import chi_gitanalyz.gitanalyzator.core.observer.NetSubscriber;
import chi_gitanalyz.gitanalyzator.retrofit.RestApiWrapper;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.SignInResult;
import chi_gitanalyz.gitanalyzator.retrofit.model.user.signin.UserRequest;
import retrofit2.Response;

/**
 * Created by Papin on 23.09.2016.
 */

public class ConnectionManager implements I_Net {

    private final List<NetSubscriber> observers = new ArrayList<>();
    Executor executor;

    public ConnectionManager(Executor executor) {
        this.executor = executor;
    }

    @Override
    public void signIN(@NonNull UserRequest user) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    Response<UserRequest> response = RestApiWrapper.getInstance().signIn(user);
                    if (response.isSuccessful()){
                        SignInResult result = response.body();
                        notifySuccessSubscribers(Sign_IN, response);
                    }else {
                        String message = response.raw().message();
                        notifyErrorSubscribers(Sign_IN,message);
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
            observer.onNetRequestDone(eventId, object);
    }

    @Override
    public void notifyErrorSubscribers(int eventId, Object object) {
        for (NetSubscriber observer : observers)
            observer.onNetRequestFail(eventId, object);
    }
}
