package prjs.adriano.com.sherlock.Services;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Objects;

import prjs.adriano.com.sherlock.Activities.MainActivity;
import prjs.adriano.com.sherlock.Activities.NotificationInfo;
import prjs.adriano.com.sherlock.R;

public class FcmMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.i("NOTIFICATION",remoteMessage.getData().toString());

            Map<String, String> tmp = remoteMessage.getData();
            String type = tmp.get("type");
            String title = tmp.get("title");
            String message = tmp.get("message");

            Intent intent;
            switch (type) {
                case "global":
                    intent = new Intent(this, MainActivity.class);
                    break;
                case "single":
                    intent = new Intent(this, NotificationInfo.class);
                    intent.putExtra("DATA", message);
                    break;
                case "newversion":
                    intent = new Intent(Intent.ACTION_VIEW,
                        Uri.parse(getResources().getString(R.string.websiteURL)+"/resources/sherlock.apk"));
                    break;
                default:
                    intent = new Intent(this, MainActivity.class);
                    break;
            }

            intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder notificationBuilder =
                new NotificationCompat.Builder(this.getApplicationContext(), "notify_001");

            if (Objects.equals(type, "global")) {
                notificationBuilder.setContentTitle(title);
                notificationBuilder.setContentText(message);
                notificationBuilder.setSmallIcon(R.drawable.ic_cloud);
                notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
            } else if (Objects.equals(type, "attention")) {
                notificationBuilder.setContentTitle(title);
                notificationBuilder.setContentText(message);
                notificationBuilder.setSmallIcon(R.drawable.ic_attention);
                notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText(message));
            } else if (Objects.equals(type, "lock")) {
                notificationBuilder.setContentTitle("Sherlock");
                notificationBuilder.setContentText("Sherlock application temporary locked.");
                notificationBuilder.setSmallIcon(R.drawable.ic_lock);
                notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText("Sherlock is temporary locked."));
                Global.setLocked(this, true);
                Toast.makeText((Activity)getApplicationContext(), "Application temporaty locked.", Toast.LENGTH_LONG).show();
            }else if (Objects.equals(type, "unlock")) {
                notificationBuilder.setContentTitle("Sherlock");
                notificationBuilder.setContentText("Sherlock unlocked");
                notificationBuilder.setSmallIcon(R.drawable.ic_unlock);
                notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText("Sherlock unlocked."));
                Global.setLocked(this, false);
            }else if (Objects.equals(type, "newversion")) {
                String version = title.split("_")[1];
                notificationBuilder.setContentTitle("Sherlock");
                notificationBuilder.setContentText("Version "+version+" is now aviable!");
                notificationBuilder.setSmallIcon(R.drawable.ic_update);
                notificationBuilder.setStyle(new NotificationCompat.BigTextStyle().bigText("Version "+version+" is now aviable!"));
                Global.editLatestVersion(version, this);
            } else {
                notificationBuilder.setContentTitle("Sherlock");
                notificationBuilder.setContentText("Your route info!");
                notificationBuilder.setSmallIcon(R.drawable.ic_plane);
            }

            notificationBuilder.setAutoCancel(true);
            notificationBuilder.setContentIntent(pendingIntent);
            notificationBuilder.setVibrate(new long[]{500, 500});
            notificationBuilder.setPriority(Notification.PRIORITY_MAX);
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("notify_001",
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        notificationManager.notify(0, notificationBuilder.build());

    }


}
