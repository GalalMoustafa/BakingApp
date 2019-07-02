package com.galalmostafa.bakingapp.Adapters;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.galalmostafa.bakingapp.Models.Recipe;
import com.galalmostafa.bakingapp.R;
import com.galalmostafa.bakingapp.Utils.RecipeClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

public class recipeAdapter extends RecyclerView.Adapter<recipeAdapter.myViewHolder> {

    private List<Recipe> recipesList;
    private Context context;
    private RecipeClickListener recipeClickListener;
    private TypedArray imgs;

    public recipeAdapter(List<Recipe> recipesList, Context context, RecipeClickListener recipeClickListener) {
        this.recipesList = recipesList;
        this.context = context;
        this.recipeClickListener = recipeClickListener;
        imgs = context.getResources().obtainTypedArray(R.array.images);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater Inflater = LayoutInflater.from(context);
        View rootview = Inflater.inflate(R.layout.recipe_item, viewGroup, false);
        return new myViewHolder(rootview);
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {
        myViewHolder.recipeName.setText(recipesList.get(i).getName());
        myViewHolder.recipeName.requestLayout();
        Picasso.get().load(imgs.getResourceId(i, -1)).into(myViewHolder.recipeImage);
        myViewHolder.recipeImage.requestLayout();
    }

    @Override
    public int getItemCount() {
        return recipesList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView recipeName;
        ImageView recipeImage;

        private myViewHolder(View v) {
            super(v);
            recipeName = v.findViewById(R.id.recipe_name);
            recipeImage = v.findViewById(R.id.recipe_img);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            recipeClickListener.onItemClickListener(clickedPosition, recipesList.get(clickedPosition));
        }
    }
}
