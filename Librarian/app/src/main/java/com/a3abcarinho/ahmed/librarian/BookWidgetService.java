package com.a3abcarinho.ahmed.librarian;

import android.content.Intent;
import android.widget.RemoteViewsService;

/**
 * Created by ahmed on 05/02/18.
 */

public class BookWidgetService extends RemoteViewsService{
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new WidgetDataProvider(this,intent);
    }
}
