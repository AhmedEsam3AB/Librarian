package com.a3abcarinho.ahmed.librarian;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.widget.RemoteViews;

/**
 * Implementation of App Widget functionality.
 */
public class NewAppWidget extends AppWidgetProvider {

   private static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

       RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.wigdet);
       setRemoteAdapter(context,views);
       Intent intent = new Intent(context,MainActivity.class);
       PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);
       views.setOnClickPendingIntent(R.id.widget,pendingIntent);
       appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId,R.id.widget_listView);
       appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
        super.onUpdate(context,appWidgetManager,appWidgetIds);

    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
    private static void setRemoteAdapter(Context context, @NonNull final RemoteViews views){
       views.setRemoteAdapter(R.id.widget_listView,new Intent(context, BookWidgetService.class));
    }
}

