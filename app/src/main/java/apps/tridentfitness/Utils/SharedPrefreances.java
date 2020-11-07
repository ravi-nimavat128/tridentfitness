package apps.tridentfitness.Utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefreances {


    private final static String PREF_FILE = "RawChicken";

    public static String Reg_F_NAME = "Reg_F_NAME";
    public static String Reg_L_NAME = "Reg_L_NAME";
    public static String Reg_BOD = "Reg_BOD";
    public static String Reg_USERNAME = "Reg_USERNAME";
    public static String Reg_PASSWORD = "Reg_PASSWORD";
    public static String Reg_GENDER = "Reg_GENDER";
    public static String Reg_MOBILE = "Reg_MOBILE";
    public static String PROFILE = "PROFILE";
    public static String Reg_OTP = "Reg_OTP";
    public static String BIO = "bio";
    public static String Reg_EMAil = "EMAil";

    public static String F_NAME = "F_NAME";
    public static String L_NAME = "L_NAME";
    public static String BOD = "BOD";
    public static String ID = "ID";
    public static String USERNAME = "USERNAME";
    public static String PASSWORD = "PASSWORD";
    public static String GENDER = "GENDER";
    public static String MOBILE = "MOBILE";
    public static String OTP = "OTP";
    public static String EMAil = "EMAil";
    public static String AUTH_TOKEN = "AUTH_TOKEN";

    public static void setSharedPreferenceString(Context context, String key, String value) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(key, value);
        editor.apply();
    }


    public static String getSharedPreferenceString(Context context, String key) {
        SharedPreferences settings = context.getSharedPreferences(PREF_FILE, 0);
        return settings.getString(key, "");
    }




}
