package com.galalmostafa.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.galalmostafa.bakingapp.Models.Ingredient;

import java.util.ArrayList;

public class WidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    ArrayList<Ingredient> ingredientsList;
    Context context;
    int _id;

    public WidgetFactory(Intent intent, Context context) {
        _id = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, 1);
        this.context = context;
        ingredientsList = new ArrayList<>();
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        ingredientsList = RecipeDetailsWidget.ingredientList;
        Log.d("numOfIngredients", ingredientsList.size() + "");
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return ingredientsList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.recipe_widget_item);
        views.setTextViewText(R.id.widget_recipe_name, RecipeDetailsWidget.recipeName + " (Ingredient No. " + (position+1) + ")" );
        views.setTextViewText(R.id.widget_recipe_measure, ingredientsList.get(position).getMeassure());
        views.setTextViewText(R.id.widget_recipe_ingredient, ingredientsList.get(position).getIngredient());
        return views;
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
