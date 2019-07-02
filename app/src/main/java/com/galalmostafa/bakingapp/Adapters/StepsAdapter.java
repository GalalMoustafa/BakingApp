package com.galalmostafa.bakingapp.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.galalmostafa.bakingapp.Models.Steps;
import com.galalmostafa.bakingapp.R;
import com.galalmostafa.bakingapp.RecipeActivity;
import com.galalmostafa.bakingapp.StepFragment;

import java.util.List;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.myViewHolder>{

    private List<Steps> stepsList;
    private Context context;


    public StepsAdapter(List<Steps> stepsList, Context context) {
        this.stepsList = stepsList;
        this.context = context;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,final int i) {
        LayoutInflater Inflater = LayoutInflater.from(context);
        View rootview = Inflater.inflate(R.layout.step_item, viewGroup, false);
        rootview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        return new myViewHolder(rootview);
    }


    @Override
    public void onBindViewHolder(@NonNull myViewHolder myViewHolder, int i) {
        myViewHolder.step_text.setText(stepsList.get(i).getShortDescription());
        myViewHolder.step_text.requestLayout();
    }

    @Override
    public int getItemCount() {
        return stepsList.size();
    }


    public class myViewHolder extends RecyclerView.ViewHolder{

        TextView step_text;

        private myViewHolder(View v) {
            super(v);
            step_text = v.findViewById(R.id.step_description);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StepFragment stepFragment = new StepFragment();
                    Bundle b = new Bundle();
                    b.putInt("count", getAdapterPosition());
                    stepFragment.setArguments(b);
                    FragmentTransaction fragmentTransaction = ((RecipeActivity)context).getSupportFragmentManager().beginTransaction();
                    if (((RecipeActivity)context).isTwoPane){
                        fragmentTransaction.replace(R.id.details_container, stepFragment, "detailsFragment");
                    }else {
                        fragmentTransaction.replace(R.id.container, stepFragment, "recipeFragment");
                    }
                    ((RecipeActivity)context).getSupportFragmentManager().popBackStack();
                    fragmentTransaction.commit();
                }
            });
        }
    }
}
