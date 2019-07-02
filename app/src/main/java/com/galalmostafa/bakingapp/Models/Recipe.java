package com.galalmostafa.bakingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Recipe implements Parcelable {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String Name;

    @SerializedName("servings")
    private int Servings;

    @SerializedName("image")
    private String Image;

    @SerializedName("ingredients")
    private List<Ingredient> ingredients;

    @SerializedName("steps")
    private List<Steps> steps;

    protected Recipe(Parcel in) {
        id = in.readInt();
        Name = in.readString();
        Servings = in.readInt();
        Image = in.readString();
        ingredients = in.createTypedArrayList(Ingredient.CREATOR);
        steps = in.createTypedArrayList(Steps.CREATOR);
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(Name);
        dest.writeInt(Servings);
        dest.writeString(Image);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public int getServings() {
        return Servings;
    }

    public String getImage() {
        return Image;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public List<Steps> getSteps() {
        return steps;
    }
}
