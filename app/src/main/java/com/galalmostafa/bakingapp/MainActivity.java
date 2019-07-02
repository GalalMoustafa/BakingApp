package com.galalmostafa.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.idling.CountingIdlingResource;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.galalmostafa.bakingapp.Adapters.recipeAdapter;
import com.galalmostafa.bakingapp.Models.Ingredient;
import com.galalmostafa.bakingapp.Models.Recipe;
import com.galalmostafa.bakingapp.Utils.ApiPlaceholder;
import com.galalmostafa.bakingapp.Utils.RecipeClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements RecipeClickListener {

    @BindView(R.id.recipe_recycler) RecyclerView recyclerView;
    private List<Recipe> recipes;

    // only used for testing
    private CountingIdlingResource idlingResource= new CountingIdlingResource("fetching");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        } else {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        }
        recyclerView.setHasFixedSize(true);

        if (savedInstanceState == null){
            idlingResource.increment();
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiPlaceholder.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiPlaceholder apiPlaceholder = retrofit.create(ApiPlaceholder.class);

        Call<List<Recipe>> call = apiPlaceholder.getRecipes();

        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                Log.d("Data", "success");
                recipes = response.body();
                setRecyclerView(recipes);
                idlingResource.decrement();
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
                Log.d("Data", "failed" + t.getMessage());
            }
        });
    }


    private void setRecyclerView(List<Recipe> recipes) {

        recipeAdapter adapter = new recipeAdapter(recipes, this, this);
        recyclerView.setAdapter(adapter);
    }

    private void updateWidget(int position) {
        RecipeDetailsWidget.sendRefreshBroadcast(this, (ArrayList<Ingredient>) recipes.get(position).getIngredients(), recipes.get(position).getName(), position);
    }

    @Override
    public void onItemClickListener(int clickIndex, Recipe recipe) {
        updateWidget(clickIndex);

        Intent i = new Intent(this, RecipeActivity.class);
        i.putExtra("Recipe", recipes.get(clickIndex));
        startActivity(i);
    }

    // used for testing
    @VisibleForTesting
    public CountingIdlingResource getIdlingResource(){
        return idlingResource;
    }
}
