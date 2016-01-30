package AppLogic;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by andyschlunz on 30.01.16.
 */
public class PreferenceData {
    static final String PREF_LOGGEDIN_USER_ID = "logged_in_id";
    static final String PREF_USER_LOGGEDIN_STATUS = "logged_in_status";


public static SharedPreferences getSharedPreferences(Context ctx){
    return PreferenceManager.getDefaultSharedPreferences(ctx);
}

    public static String getPrefLoggedinUserId(Context ctx){
        return getSharedPreferences(ctx).getString(PREF_LOGGEDIN_USER_ID, "");
    }

    public static void setPrefLoggedinUserId(Context ctx, String Id){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_LOGGEDIN_USER_ID, Id);
        editor.commit();
    }

    public static Boolean getPrefUserLoggedinStatus(Context ctx) {
        return getSharedPreferences(ctx).getBoolean(PREF_USER_LOGGEDIN_STATUS, false);
    }
    public static void setPrefUserLoggedinStatus(Context ctx, boolean status){
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(PREF_USER_LOGGEDIN_STATUS, status);
        editor.commit();
    }

    public static void clearLoggedInId(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.remove(PREF_LOGGEDIN_USER_ID);
        editor.remove(PREF_USER_LOGGEDIN_STATUS);
        editor.commit();
    }

}
