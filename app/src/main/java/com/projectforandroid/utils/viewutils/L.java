package com.projectforandroid.utils.viewutils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by exkulo on 9/16/2015.
 */
public class L {

    private static boolean debug = true;

    public static String TAG = "430project";

    private static Toast mToast = null;

    public static void i(String msg) {
        if (debug) {
            Log.i(TAG, (msg == null ? "null" : msg));
        }
    }

    public static void i(Throwable e) {
        if (debug) {
            Log.i(TAG, Log.getStackTraceString(e));
        }
    }


    public static void t(Context context, String msg) {
        if(mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        mToast.show();
    }
}
