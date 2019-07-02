package com.galalmostafa.bakingapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.galalmostafa.bakingapp.Models.Recipe;

import butterknife.BindView;

public class RecipeActivity extends AppCompatActivity {

    public boolean isTwoPane;
    public Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        if (findViewById(R.id.details_container) != null) {
            isTwoPane = true;
        } else {
            isTwoPane = false;
        }


        if (savedInstanceState == null){
            Bundle b = getIntent().getExtras();
            if(b != null && b.getParcelable("Recipe") != null){
                recipe = b.getParcelable("Recipe");

            }
            setFragment(new RecipeFragment(), R.id.container, "recipeFragment");
        } else {
            recipe = savedInstanceState.getParcelable("recipe");
                setFragment(getSupportFragmentManager().getFragment(savedInstanceState, "recipeFragment"), R.id.container, "recipeFragment");
            if (isTwoPane){
                setFragment(getSupportFragmentManager().getFragment(savedInstanceState, "detailsFragment"), R.id.details_container, "detailsFragment");
            }
        }

    }

    public void setFragment(Fragment fragment, int id, String tag) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(id, fragment, tag);
        getSupportFragmentManager().popBackStack();
        fragmentTransaction.commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        getSupportFragmentManager().putFragment(outState, "recipeFragment", getSupportFragmentManager().findFragmentByTag("recipeFragment"));
        if (isTwoPane){
            getSupportFragmentManager().putFragment(outState, "detailsFragment",getSupportFragmentManager().findFragmentByTag("detailsFragment"));
        }
        outState.putParcelable("recipe", recipe);
    }
}
