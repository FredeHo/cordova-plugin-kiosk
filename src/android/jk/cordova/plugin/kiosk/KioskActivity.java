package jk.cordova.plugin.kiosk;

import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import org.apache.cordova.*;

import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ViewAnimator;

import java.lang.reflect.Method;

public class KioskActivity extends CordovaActivity {
    public static boolean running = false;
    String TAG = "KioskActivity";

    protected void onStart() {
        Log.d(TAG, "onStart");
        super.onStart();
        running = true;
    }

    protected void onStop() {
        Log.d(TAG, "onStop");
        super.onStop();
        running = false;
    }

    public void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        super.init();

        if(running) {
          Log.d(TAG, "Already running!");
          finish();
        }

        loadUrl(launchUrl);
    }

    public void onResume() {
      Log.d(TAG, "onResume");
      super.onResume();
    }

    public void onPause()
    {
        Log.d(TAG, "onPause");
        super.onPause();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        System.exit(0);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
      Log.d(TAG, "onWindowFocusChanged(" + hasFocus + ")");
        super.onWindowFocusChanged(hasFocus);
    }
}
