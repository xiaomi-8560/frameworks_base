package com.android.internal.util.halcyon;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.os.RemoteException;

import com.android.internal.R;

import java.util.ArrayList;
import java.util.List;

public class HalcyonUtils {

    public static List<String> launchablePackages(Context context) {
        List<String> list = new ArrayList<>();

        Intent filter = new Intent(Intent.ACTION_MAIN, null);
        filter.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> apps = context.getPackageManager().queryIntentActivities(filter,
                PackageManager.GET_META_DATA);

        int numPackages = apps.size();
        for (int i = 0; i < numPackages; i++) {
            ResolveInfo app = apps.get(i);
            list.add(app.activityInfo.packageName);
        }

        return list;
    }

    // Check to see if a package is installed
    public static boolean isPackageInstalled(Context context, String pkg, boolean ignoreState) {
        if (pkg != null) {
            try {
                PackageInfo pi = context.getPackageManager().getPackageInfo(pkg, 0);
                if (!pi.applicationInfo.enabled && !ignoreState) {
                    return false;
                }
            } catch (NameNotFoundException e) {
                return false;
            }
        }

        return true;
    }

    public static boolean isPackageInstalled(Context context, String pkg) {
        return isPackageInstalled(context, pkg, true);
    }
}
