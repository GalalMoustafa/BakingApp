package com.galalmostafa.bakingapp;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.galalmostafa.bakingapp.Models.Ingredient;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of App Widget functionality.
 */
public class RecipeDetailsWidget extends AppWidgetProvider {

    static ArrayList<Ingredient> ingredientList = new ArrayList<>();
    static String recipeName = ">> Clic Here To Open App<<";
    static int index = 0;

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_details_widget);
        views.setTextViewText(R.id.recipe_title_widget, recipeName);
        Intent i = new Intent(context, MainActivity.class);
        i.putExtra("index",index);
        Intent service = new Intent(context,RecipeWidgetService.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(context,0, i,PendingIntent.FLAG_CANCEL_CURRENT);
        views.setOnClickPendingIntent(R.id.recipe_title_widget, pendingIntent);
        views.setRemoteAdapter(R.id.recipe_list_widget, service);


        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {

            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    public static void sendRefreshBroadcast(Context context, ArrayList<Ingredient> list, String name, int i) {
        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
        ingredientList = list;
        recipeName = name;
        index = i;
        intent.setComponent(new ComponentName(context, RecipeDetailsWidget.class));
        context.sendBroadcast(intent);
    }


    @Override
    public void onReceive(Context context, Intent intent) {
        final String action = intent.getAction();
        if (action.equals(AppWidgetManager.ACTION_APPWIDGET_UPDATE)) {
            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
            ComponentName componentName = new ComponentName(context, RecipeDetailsWidget.class);
            RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_details_widget);
            views.setTextViewText(R.id.recipe_title_widget, recipeName);
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetManager.getAppWidgetIds(componentName), R.id.recipe_list_widget);
        }
        super.onReceive(context, intent);
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

