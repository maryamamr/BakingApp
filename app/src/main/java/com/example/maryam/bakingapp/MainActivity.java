package com.example.maryam.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.maryam.bakingapp.Adapter.MainActivityAdapter;
import com.example.maryam.bakingapp.Utils.AppUtils;
import com.example.maryam.bakingapp.model.recipe;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    private MainActivityAdapter adapter;
    private Context context;
    private RecyclerView mRecyclerView;
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        mRecyclerView = findViewById(R.id.recipes_rv);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        //get the data from the server
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BASE_URL).client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create()).build();
        Servicenterface requestInterface = retrofit.create(Servicenterface.class);
        Call<ArrayList<recipe>> call = requestInterface.getJSON();
        call.enqueue(new Callback<ArrayList<recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<recipe>> call, Response<ArrayList<recipe>> response) {
                ArrayList<recipe> recipeList = response.body();
                adapter = new MainActivityAdapter(context, recipeList, clickedItemIndex -> {
                    recipe recipe = adapter.getRecipeAtIndex(clickedItemIndex);
                    Bundle bundle = new Bundle();
                    bundle.putParcelable(AppUtils.SEND_RECIPE, recipe);
                    final Intent intent = new Intent(context, RecipeInfoActivity.class);
                    intent.putExtras(bundle);
                    startActivity(intent);
                });

                mRecyclerView.setAdapter(adapter);

            }

            @Override
            public void onFailure(Call<ArrayList<recipe>> call, Throwable t) {
                Toast.makeText(context, "error while connecting", Toast.LENGTH_LONG).show();
            }
        });

    }
}
