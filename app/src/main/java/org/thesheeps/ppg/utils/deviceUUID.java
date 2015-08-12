package org.thesheeps.ppg.utils;

import android.content.Context;
import android.provider.Settings;
import android.util.Log;

import java.io.File;
import java.util.UUID;

/**
 * Returns a unique UUID for the current android device.
 */

public class deviceUUID {

    private static final String LOGTAG = "PPG_deviceUUID";
    private static String sID = null;

    public static String getUUID(Context context) {
        return id(context) + Settings.Secure.ANDROID_ID;
    }

    private static String id(Context context) {
        if (sID == null) {
            File pid = new File(context.getFilesDir(), "PID");
            try {
                if (!pid.exists())
                    fileHelper.writeToFile(pid, UUID.randomUUID().toString(), false);
                sID = fileHelper.readFromFile(pid).get(0);
            } catch (Exception e) {
                Log.e(LOGTAG, "Can't access file: ", e);
            }
        }
        return sID;
    }
}
