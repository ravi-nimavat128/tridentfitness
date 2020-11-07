package apps.tridentfitness.recivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import apps.tridentfitness.activities.SettingsActivity;
import apps.tridentfitness.R;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmReceiver extends BroadcastReceiver {
    private NotificationManager mNotificationManager;
    public static final String NOTIFICATION_CHANNEL_ID = "10001" ;
    private final static String default_notification_channel_id = "default" ;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO Auto-generated method stub
        Intent notificationIntent = new Intent(context, SettingsActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        notificationIntent.putExtra( "fromNotification" , true ) ;
        PendingIntent pendingIntent = PendingIntent. getActivity ( context, 0 , notificationIntent , PendingIntent.FLAG_UPDATE_CURRENT ) ;
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
         mNotificationManager = (NotificationManager) context.getSystemService( NOTIFICATION_SERVICE ) ;
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, default_notification_channel_id ) ;
        mBuilder.setContentTitle( "workout" ) ;
        mBuilder.setSound(alarmSound);
        mBuilder.setContentIntent(pendingIntent) ;
        mBuilder.setContentText( "your workout time is up" ) ;
        mBuilder.setSmallIcon(R.drawable.excises ) ;
        mBuilder.setAutoCancel( true ) ;
        if (android.os.Build.VERSION. SDK_INT >= android.os.Build.VERSION_CODES. O ) {
            int importance = NotificationManager. IMPORTANCE_HIGH ;
            NotificationChannel notificationChannel = new
                    NotificationChannel( NOTIFICATION_CHANNEL_ID , "NOTIFICATION_CHANNEL_NAME" , importance) ;
            mBuilder.setChannelId( NOTIFICATION_CHANNEL_ID ) ;
            assert mNotificationManager != null;
            mNotificationManager.createNotificationChannel(notificationChannel) ;
        }
        assert mNotificationManager != null;
        mNotificationManager.notify(( int ) System. currentTimeMillis () , mBuilder.build()) ;
        throw new UnsupportedOperationException( "Not yet implemented" ) ;
    }

}