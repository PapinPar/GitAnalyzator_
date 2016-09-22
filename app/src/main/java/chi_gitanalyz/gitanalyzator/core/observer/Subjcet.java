package chi_gitanalyz.gitanalyzator.core.observer;

/**
 * Created by Papin on 20.09.2016.
 */
public interface Subjcet<O extends Subscriber>
{
    void Subscribe(O observer);

    void UnSubscribe(O observer);

    boolean IsSubscribe(O observer);

    void notifySuccessSubscribers(int eventId,Object object);

    void notifyErrorSubscribers(int eventId,Object object);

}
