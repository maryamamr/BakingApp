package com.example.maryam.bakingapp;

import com.example.maryam.bakingapp.model.recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Servicenterface {
    public static final String JSON_LOC = "/topher/2017/May/59121517_baking/baking.json";
    @GET(JSON_LOC)
    Call<ArrayList<recipe>> getJSON();
}
