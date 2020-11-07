package apps.tridentfitness;

import android.app.Application;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import apps.tridentfitness.Utils.SharedPrefreances;

public class WorkOutApps extends Application {

    String token;


    @Override
    public void onCreate() {
        super.onCreate();


      /*  OneSignal.init(this, null,"1d547fd4-5d98-4394-93db-67bedaa7683a");

        OneSignal.startInit(this)
                .setNotificationReceivedHandler(new NotificationReceiver(getApplicationContext()))
                .setNotificationOpenedHandler(new ExampleNotificationOpenedHandler(getApplicationContext()))
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();*/
        //registerActivityLifecycleCallbacks(this);
        FirebaseApp.initializeApp(this);

        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            return;
                        }
                        if (SharedPrefreances.getSharedPreferenceString(getApplicationContext(), "token").equals("")) {
                            if (task.getResult() != null) {
                                token = task.getResult().getToken();
                                //Utils.showLog("==== token "+token);
                                Log.w("ravi_testing_token", token);
                            }
                            SharedPrefreances.setSharedPreferenceString(getApplicationContext(), "token", token);
                        }
                    }
                });


        // OneSignal Initialization


  /*      OneSignal.startInit(this)
                .setNotificationReceivedHandler(new NotificationReceiver())
                .setNotificationOpenedHandler(new NotificationOpenedReceiver())
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.None)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();*/


   /*     try {

            OSPermissionSubscriptionState status = OneSignal.getPermissionSubscriptionState();
            SharedPrefsUtils.setSharedPreferenceString(getApplicationContext(), "token_new", status.getSubscriptionStatus().getUserId());
            Log.w("token_new", status.getSubscriptionStatus().getUserId());
            // login(String.valueOf(mobileNumber), String.valueOf(otpGenerated), status.getSubscriptionStatus().getUserId());

        } catch (Exception e) {
            e.printStackTrace();
        }
*/

    }



}
