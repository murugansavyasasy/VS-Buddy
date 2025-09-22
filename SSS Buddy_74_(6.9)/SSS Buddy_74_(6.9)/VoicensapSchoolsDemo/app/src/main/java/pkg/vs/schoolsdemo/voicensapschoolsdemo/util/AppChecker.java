package pkg.vs.schoolsdemo.voicensapschoolsdemo.util;

import android.content.Context;
import android.content.pm.PackageManager;

public class AppChecker {
    public static boolean isAppInstalled(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();
        try {
            packageManager.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
}
