package jk.cordova.plugin.kiosk;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import org.apache.cordova.*;
import android.widget.*;
import android.view.Window;
import android.view.View;
import android.view.WindowManager;
import android.view.KeyEvent;
import android.view.ViewGroup.LayoutParams;
import android.util.Log;
import java.util.Timer;
import java.util.TimerTask;
import org.json.JSONArray;
import org.json.JSONException;
import jk.cordova.plugin.kiosk.KioskActivity;
import org.json.JSONObject;
import android.preference.PreferenceManager;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

public class KioskPlugin extends CordovaPlugin {

    public static final String EXIT_KIOSK = "exitKiosk";

    public static final String ENTER_KIOSK = "enterKiosk";

    public static final String IS_IN_KIOSK = "isInKiosk";

    public static final String START_EXTERN_APP = "startExternApp";

    private static final String PREF_KIOSK_MODE = "pref_kiosk_mode";
    private static final String ALLOW_APP_START = "allow_app_start";

    String TAG = "KioskActivity";

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            if (IS_IN_KIOSK.equals(action)) {

                callbackContext.success(Boolean.toString(KioskActivity.running));
                return true;

            } else if (EXIT_KIOSK.equals(action)) {
                Log.d(TAG, "Exiting KioskMode...");
                Intent intent = new Intent(Intent.ACTION_MAIN);
                intent.addCategory(Intent.CATEGORY_HOME);
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.cordova.getActivity().getApplicationContext());
                sp.edit().putBoolean(PREF_KIOSK_MODE, false).commit();

                Intent chooser = Intent.createChooser(intent, "Wählen Sie Ihr Ziel");
                if (intent.resolveActivity(cordova.getActivity().getPackageManager()) != null) {
                    cordova.getActivity().startActivity(chooser);
                }

                callbackContext.success();
                return true;
            } else if (ENTER_KIOSK.equals(action)) {
                Log.d(TAG, "Entering KioskMode...");
                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.cordova.getActivity().getApplicationContext());
                sp.edit().putBoolean(PREF_KIOSK_MODE, true).commit();
                //sp.edit().putBoolean(ALLOW_APP_START, true).commit();

                callbackContext.success();
                return true;
            } else if (START_EXTERN_APP.equals(action)) {
                String package_name = args.getString(0);
                Log.d(TAG, "Trying to start " + package_name + "...");

                SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this.cordova.getActivity().getApplicationContext());
                sp.edit().putBoolean(ALLOW_APP_START, true).commit();

                /*
                Intent LaunchIntent = new Intent();
                LaunchIntent.setPackage(package_name);
                */

                PackageManager manager = cordova.getActivity().getApplicationContext().getPackageManager();
                Intent LaunchIntent = manager.getLaunchIntentForPackage(package_name);

                cordova.getActivity().startActivity(LaunchIntent);

                callbackContext.success();
                return true;
            }
            callbackContext.error("Invalid action");
            return false;
        } catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        }
    }
}
