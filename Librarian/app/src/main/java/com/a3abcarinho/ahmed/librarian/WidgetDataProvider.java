package com.a3abcarinho.ahmed.librarian;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

/**
 * Created by ahmed on 05/02/18.
 */

public class WidgetDataProvider implements RemoteViewsService.RemoteViewsFactory {
    private Context context;
    private Cursor cursor;
    private Intent intent;
    public WidgetDataProvider(Context context,Intent intent){
        this.context = context;
        this.intent = intent;
    }
    private void initCursor(){
        if(cursor != null){
            cursor.close();
        }
        final long idetityToken = Binder.clearCallingIdentity();
        cursor = context.getContentResolver().query(BookContract.BookEntry.CONTENT_URI,null,null,null,BookContract.BookEntry.COLUMN_ID);
        Binder.restoreCallingIdentity(idetityToken);
    }
    @Override
    public void onCreate() {
        initCursor();
        if(cursor != null){
            cursor.moveToFirst();
        }

    }

    @Override
    public void onDataSetChanged() {
        initCursor();

    }

    @Override
    public void onDestroy() {
        cursor.close();

    }

    @Override
    public int getCount() {
        return cursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int i) {
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),R.layout.list_widget_item);
        cursor.moveToPosition(i);
        remoteViews.setTextViewText(R.id.nameWidget,cursor.getString(cursor.getColumnIndex(BookContract.BookEntry.COLUMN_BOOKNAME)));
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}
