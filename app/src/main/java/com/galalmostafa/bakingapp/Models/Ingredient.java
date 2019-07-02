package com.galalmostafa.bakingapp.Models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Ingredient implements Parcelable {

    @SerializedName("quantity")
    private double Quantity;

    @SerializedName("measure")
    private String meassure;

    @SerializedName("ingredient")
    private String ingredient;

    protected Ingredient(Parcel in) {
        Quantity = in.readDouble();
        meassure = in.readString();
        ingredient = in.readString();
    }

    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            return new Ingredient(in);
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(Quantity);
        dest.writeString(meassure);
        dest.writeString(ingredient);
    }

    public double getQuantity() {
        return Quantity;
    }

    public String getMeassure() {
        return meassure;
    }

    public String getIngredient() {
        return ingredient;
    }
}
