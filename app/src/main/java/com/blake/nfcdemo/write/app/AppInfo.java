package com.blake.nfcdemo.write.app;

import android.graphics.drawable.Drawable;

/**
 * Create by Pidan
 */
public class AppInfo {
    private Drawable appIcon;
    private String appName;
    private String packageName;

    public AppInfo(Drawable appIcon, String appName, String packageName) {
        this.appIcon = appIcon;
        this.appName = appName;
        this.packageName = packageName;
    }

    public String getAppName() {
        return appName;
    }

    public Drawable getAppIcon() {
        return appIcon;
    }

    public String getPackageName() {
        return packageName;
    }
}
