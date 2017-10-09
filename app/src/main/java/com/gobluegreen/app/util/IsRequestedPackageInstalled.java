package com.gobluegreen.app.util;

import android.app.Activity;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

import java.lang.ref.WeakReference;

/**
 * Created by David on 11/4/16.
 */

public class IsRequestedPackageInstalled {

    public static boolean execute(WeakReference<Activity> activityWeakReference, String packageUri) {
        boolean packageInstalled = false;

        Activity activity = activityWeakReference.get();

        PackageManager packageManager = activity.getPackageManager();


        try {

            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(packageUri, 0);

            if (!applicationInfo.enabled){
                return false;
            }

            packageManager.getPackageInfo(packageUri, PackageManager.GET_ACTIVITIES);


            packageInstalled = true;
        } catch (PackageManager.NameNotFoundException e) {
            packageInstalled = false;
        }

        return packageInstalled;
    }
}
