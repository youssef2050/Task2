package com.menu.task4;

import android.content.Context;

public class Constant {
    public static final String ACTIVITY_NOT_FOUND = "Not Found!";
    public static final int MY_PERMISSIONS_REQUEST_PACKAGE_USAGE_STATS = 200;
    public static Context CONTEXT = null;
    public static final String[] packages = {
            "com.sec.android.app.launcher",
            "com.google.android.packageinstaller",
            "com.android.settings",
            "com.android.systemui",
            ACTIVITY_NOT_FOUND,
    };
}
