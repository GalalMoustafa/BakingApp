package com.galalmostafa.bakingapp.Utils;

import com.galalmostafa.bakingapp.Models.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiPlaceholder {

    String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";

    @Headers("Content-Type: application/json")
    @GET("baking.json")
    Call<List<Recipe>> getRecipes();
}
