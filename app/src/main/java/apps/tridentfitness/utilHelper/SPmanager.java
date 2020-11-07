package apps.tridentfitness.utilHelper;

import android.content.Context;
import android.content.SharedPreferences;

public class SPmanager {
    public static String preferenceName = "WorkOut";

    public static void saveValue(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getPreference(Context context, String key) {
        return context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE).getString(key, " ");
    }

    public static void setFirstTimeWeight(Context context, boolean b) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("firstweight", b);
        editor.apply();
    }

    public static void setChecked(Context context, boolean b) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("checked", b);
        editor.apply();
    }

    public static void setGuidence(Context context, boolean b) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("guidence", b);
        editor.apply();
    }

    public static void isFirstTime(Context context, boolean b) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isFirstTime", b);
        editor.apply();
    }

    public static boolean getFirstTime(Context context) {
        return context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE).getBoolean("isFirstTime", false);
    }

    public static void setMuted(Context context, boolean b) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("muted", b);
        editor.apply();
    }

    public static boolean getMuted(Context context) {
        return context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE).getBoolean("muted", false);
    }

    public static void setCountdown(Context context, boolean b) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("countdown", b);
        editor.apply();
    }

    public static boolean getCountdown(Context context) {
        return context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE).getBoolean("countdown", false);
    }

    public static boolean getChecked(Context context) {
        return context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE).getBoolean("checked", false);
    }

    public static boolean getGuidence(Context context) {
        return context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE).getBoolean("guidence", false);
    }

    public static boolean getFirstTimeWeight(Context context) {
        return context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE).getBoolean("firstweight", false);
    }



    public static boolean getSkipAddressPreferences(Context context) {
        return context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE).getBoolean("userSkipAddress", false);
    }

    public static void setSkipPreference(Context context, boolean b) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceName, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("userSkipAddress", b);
        editor.apply();
    }


}
