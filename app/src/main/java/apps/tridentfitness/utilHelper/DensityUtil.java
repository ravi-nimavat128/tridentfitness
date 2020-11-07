package apps.tridentfitness.utilHelper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.IdRes;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class DensityUtil {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    public static String activity = "CURRENT_ACTIVITY";
    public static String activityName = "CURRENT_ACTIVITY_NAME";
    public static boolean hasCalculate = false;
    public static boolean isPad = false;

    public static int dip2px(Context context, float f) {
        return (int) ((context.getResources().getDisplayMetrics().density * f) + (0.5f * ((float) (f >= 0.0f ? 1 : -1))));
    }

    public static boolean isPad(Context context) {
        if (hasCalculate) {
            return isPad;
        }
        Display defaultDisplay = ((WindowManager) context.getSystemService("window")).getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        defaultDisplay.getMetrics(displayMetrics);
        if (Math.sqrt(Math.pow((double) (((float) displayMetrics.widthPixels) / displayMetrics.xdpi), 2.0d) + Math.pow((double) (((float) displayMetrics.heightPixels) / displayMetrics.ydpi), 2.0d)) > 6.0d) {
            isPad = true;
        }
        hasCalculate = true;
        return isPad;
    }

    public static void setHelvetica(TextView textView, Context context) {
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/Helvetica_1.ttf"));
    }

    public static void setOswaldMedium(TextView textView, Context context) {
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/Oswald-Medium.ttf"));
    }

    public static void setOswaldRegular(Context context, TextView... textViewArr) {
        if (context != null) {
            for (TextView oswaldRegular : textViewArr) {
                setOswaldRegular(oswaldRegular, context);
            }
        }
    }

    public static void setOswaldRegular(TextView textView, Context context) {
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/Oswald-Regular.ttf"));
    }

    public static void setDinRegular(Context context, TextView... textViewArr) {
        if (context != null) {
            for (TextView dinRegular : textViewArr) {
                setDinRegular(dinRegular, context);
            }
        }
    }

    public static void setDinRegular(TextView textView, Context context) {
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/DIN-Regular.ttf"));
    }

    public static void setNunitoRegularFont(Context context, TextView... textViewArr) {
        if (context != null) {
            for (TextView nunitoRegularFont : textViewArr) {
                setNunitoRegularFont(nunitoRegularFont, context);
            }
        }
    }

    public static void setNunitoRegularFont(TextView textView, Context context) {
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/Nunito-Regular.ttf"));
    }

    public static void setOswaldLight(TextView textView, Context context) {
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/OSWALD-LIGHT.TTF"));
    }

    public static void setFiraSansExtraCondensed(TextView textView, Context context) {
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/FiraSansExtraCondensed-Thin.ttf"));
    }

    public static void setNotoSans(TextView textView, Context context) {
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/NotoSans-CondensedThin.ttf"));
    }

    public static void NunitoRegular(TextView textView, Context context) {
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/Nunito-Regular.ttf"));
    }

    public static void setAirbnbCerealMedium(TextView textView, Context context) {
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/AirbnbCereal-Medium.ttf"));
    }

    public static void setFontAirbnbCerealMedium(@IdRes int i, Context context, Activity activity) {
        setAirbnbCerealMedium((TextView) activity.findViewById(i), context);
    }

    public static void setFontAirbnbCerealMedium(Context context, View view, @IdRes int... iArr) {
        for (int findViewById : iArr) {
            setAirbnbCerealMedium((TextView) view.findViewById(findViewById), context);
        }
    }

    public static void setFontAirbnbCerealMedium(Context context, Activity activity, @IdRes int... iArr) {
        for (int findViewById : iArr) {
            setAirbnbCerealMedium((TextView) activity.findViewById(findViewById), context);
        }
    }

    public static void setAirbnbNumber(TextView textView, Context context) {
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/arbnbnumber6.otf"));
    }

    public static void setAirbnbCerealBook(TextView textView, Context context) {
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/AirbnbCereal-Book.ttf"));
    }

    public static void setFontAirbnbCerealBook(@IdRes int i, Context context, Activity activity) {
        setAirbnbCerealBook((TextView) activity.findViewById(i), context);
    }

    public static void setFontAirbnbCerealBook(Context context, Activity activity, @IdRes int... iArr) {
        for (int findViewById : iArr) {
            setAirbnbCerealBook((TextView) activity.findViewById(findViewById), context);
        }
    }

    public static void setFontAirbnbCerealBook(Context context, View view, @IdRes int... iArr) {
        for (int findViewById : iArr) {
            setAirbnbCerealBook((TextView) view.findViewById(findViewById), context);
        }
    }

    public static void setFontAirbnbCerealBook(@IdRes int i, Context context, View view) {
        setAirbnbCerealBook((TextView) view.findViewById(i), context);
    }

    public static void setAirbnbCerealBold(TextView textView, Context context) {
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/AirbnbCereal-Bold.ttf"));
    }

    public static void setFontAirbnbCerealBold(@IdRes int i, Context context, Activity activity) {
        setAirbnbCerealMedium((TextView) activity.findViewById(i), context);
    }

    public static void setFontAirbnbCerealBold(Context context, Activity activity, @IdRes int... iArr) {
        for (int findViewById : iArr) {
            setAirbnbCerealBold((TextView) activity.findViewById(findViewById), context);
        }
    }

    public static void setFontAirbnbCerealBold(@IdRes int i, Context context, View view) {
        setAirbnbCerealBold((TextView) view.findViewById(i), context);
    }

    public static void setAirbnbCerealLight(TextView textView, Context context) {
        textView.setTypeface(Typeface.createFromAsset(context.getAssets(), "font/AirbnbCereal-Light.ttf"));
    }

    public static void setFontAirbnbCerealLight(@IdRes int i, Context context, Activity activity) {
        setAirbnbCerealLight((TextView) activity.findViewById(i), context);
    }

    public static void setFontAirbnbCerealLight(@IdRes int i, Context context, View view) {
        setAirbnbCerealLight((TextView) view.findViewById(i), context);
    }

    public static void setFontAirbnbCerealLight(Context context, View view, @IdRes int... iArr) {
        for (int findViewById : iArr) {
            setAirbnbCerealLight((TextView) view.findViewById(findViewById), context);
        }
    }
}
