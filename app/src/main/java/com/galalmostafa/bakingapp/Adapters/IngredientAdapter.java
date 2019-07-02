package com.galalmostafa.bakingapp.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.galalmostafa.bakingapp.Models.Ingredient;
import com.galalmostafa.bakingapp.R;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.myViewHolder> {

    private List<Ingredient> ingredientsList;
    private Context context;

    public IngredientAdapter(List<Ingredient> ingredientsList, Context context) {
        this.ingredientsList = ingredientsList;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater Inflater = LayoutInflater.from(context);
        View rootview = Inflater.inflate(R.layout.ingredient_item, viewGroup, false);
        return new myViewHolder(rootview);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {
        myViewHolder.measure.setText(ingredientsList.get(i).getQuantity() + " " + ingredientsList.get(i).getMeassure());
        myViewHolder.ingredient_text.setText(ingredientsList.get(i).getIngredient());
        myViewHolder.measure.requestLayout();
        myViewHolder.ingredient_text.requestLayout();
    }

    @Override
    public int getItemCount() {
        return ingredientsList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView measure;
        TextView ingredient_text;

        private myViewHolder(View v) {
            super(v);
            measure = v.findViewById(R.id.measure_text);
            ingredient_text = v.findViewById(R.id.ingredient_tv);
        }

    }
}
