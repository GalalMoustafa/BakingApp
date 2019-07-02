package com.galalmostafa.bakingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.galalmostafa.bakingapp.Adapters.IngredientAdapter;
import com.galalmostafa.bakingapp.Adapters.StepsAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeFragment extends Fragment {

    private View rootView;
    @BindView(R.id.ingredientsRecycler) RecyclerView ingredients_recycler;
    @BindView(R.id.stepsRecycler) RecyclerView stepsRecycler;
    private IngredientAdapter ingredientAdapter;
    private StepsAdapter stepsAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.recipe_fragment, container, false);
        ButterKnife.bind(this, rootView);
        Initialize();
        return rootView;
    }


    private void Initialize(){
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL);
        dividerItemDecoration.setDrawable(getActivity().getResources().getDrawable(R.drawable.recycler_divider));

        ingredients_recycler.setHasFixedSize(true);
        ingredients_recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        ingredients_recycler.addItemDecoration(dividerItemDecoration);
        ingredientAdapter = new IngredientAdapter( ((RecipeActivity)getActivity()).recipe.getIngredients() , getContext());
        ingredients_recycler.setAdapter(ingredientAdapter);



        stepsRecycler.setHasFixedSize(true);
        stepsRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        stepsRecycler.addItemDecoration(dividerItemDecoration);
        Log.d("steps", ((RecipeActivity)getActivity()).recipe.getSteps().get(0).getDescription() + "");
        stepsAdapter = new StepsAdapter( ((RecipeActivity)getActivity()).recipe.getSteps() , getActivity());
        stepsRecycler.setAdapter(stepsAdapter);
    }
}
