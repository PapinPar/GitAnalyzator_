package chi_gitanalyz.gitanalyzator.service;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.Gson;
import com.pusher.client.Pusher;
import com.pusher.client.PusherOptions;
import com.pusher.client.channel.Channel;
import com.pusher.client.channel.SubscriptionEventListener;
import com.pusher.client.connection.ConnectionEventListener;
import com.pusher.client.connection.ConnectionStateChange;

import chi_gitanalyz.gitanalyzator.R;
import chi_gitanalyz.gitanalyzator.ui.ChoseAnalyzator;
import chi_gitanalyz.gitanalyzator.ui.project.ProjectsActivity;

/**
 * Created by Papin on 10.10.2016.
 */

public class MyService extends Service {
    private Pusher pusher;
    private Channel channel;
    private MessageNotif message;
    private NotificationManager manager;
    private Notification myNotication;
    private String channelName, eventName, key;
    private PusherOptions options;
    private SharedPreferences sPref;
    private String projectName;

    @Nullable
    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("sd", "onCreate");

        key = "bfc90c9acd56196c01ba";
        channelName = "ProjectsStatusesChannel";
        eventName = "project_status_change";

        manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("sd", "onStartCommand");
        pusher();
        return super.onStartCommand(intent, flags, startId);
    }

    public void onDestroy() {
        super.onDestroy();
        pusher.unsubscribe(channelName);
        Log.d("sd", "onDestroy");
    }

    public IBinder onBind(Intent intent) {
        Log.d("sd", "onBind");
        return null;
    }

    void pusher() {
        options = new PusherOptions();
        options.setCluster("eu");

        pusher = new Pusher(key, options);
        channel = pusher.subscribe(channelName);

        channel.bind(eventName, new SubscriptionEventListener() {
            @Override
            public void onEvent(String channelName, String eventName, final String data) {
                sPref = getSharedPreferences("Names", MODE_PRIVATE);
                projectName = sPref.getString("Project_name", "0");

                Thread t = new Thread(new Runnable() {
                    public void run() {
                        Gson gson = new Gson();
                        message = gson.fromJson(data, MessageNotif.class);
                        Log.d("asd", "" + message.getStatus());
                        if (message.getStatus().equals("selecting_analyzers")) {
                            if (projectName.equals(message.getProject_name()))
                                startActivity(message.getProject_id());
                        } else
                            sendNotif();
                    }
                });
                t.start();
            }

        });
        pusher.connect(new ConnectionEventListener() {
            @Override
            public void onConnectionStateChange(ConnectionStateChange connectionStateChange) {
                Log.d("PAPIN_TAG", "Pusher Conected");
            }

            @Override
            public void onError(String s, String s1, Exception e) {
                Log.d("error", "error" + s);
            }
        });
    }

    private void startActivity(String id) {
        Intent choseAnalyz = new Intent(this, ChoseAnalyzator.class);
        choseAnalyz.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        choseAnalyz.putExtra("myID", id);
        startActivity(choseAnalyz);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    void sendNotif() {
        Intent intent = new Intent(this, ProjectsActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        Notification.Builder builder = new Notification.Builder(this);

        builder.setAutoCancel(true);
        builder.setTicker("Project :" + message.getProject_name() + " was changed status");
        builder.setContentTitle("GitAnalizator");
        builder.setContentText("Project : " + message.getProject_name() + " is " + message.getStatus());
        builder.setLargeIcon(BitmapFactory.decodeResource(this.getResources(),
                R.mipmap.my_launcher));
        builder.setSmallIcon(R.drawable.my_notification);
        builder.setContentIntent(pendingIntent);
        builder.setOngoing(false);
        builder.setSubText("Click to open");
        builder.build();

        Notification notification = builder.build();
        android.app.NotificationManager notificationManager =
                (android.app.NotificationManager) this.getSystemService(Context.NOTIFICATION_SERVICE);

        long[] vib = new long[]{10, 1500};
        notification.vibrate = vib;

        notification.defaults |= Notification.DEFAULT_SOUND;
        notification.defaults |= Notification.DEFAULT_LIGHTS;

        notificationManager.notify(101, notification);
    }
}