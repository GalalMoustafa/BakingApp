package com.galalmostafa.bakingapp;

import android.content.Intent;
import android.widget.RemoteViewsService;

public class RecipeWidgetService extends RemoteViewsService {

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return (new WidgetFactory(intent, getApplicationContext()));
    }

}
