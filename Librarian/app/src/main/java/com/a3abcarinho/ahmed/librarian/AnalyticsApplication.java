package com.a3abcarinho.ahmed.librarian;

import android.app.Application;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Tracker;

/**
 * Created by ahmed on 03/02/18.
 */

public class AnalyticsApplication extends Application {
    private static GoogleAnalytics sAnalytics;
    private static Tracker sTracker;

    @Override
    public void onCreate() {
        super.onCreate();
        sAnalytics = GoogleAnalytics.getInstance(this);
    }
    synchronized public Tracker getDefaultTracker(){
        if(sTracker==null){
            sTracker = sAnalytics.newTracker(R.xml.global_tracker);
        }
        return sTracker;
    }
}
