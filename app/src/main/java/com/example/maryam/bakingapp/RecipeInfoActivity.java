package com.example.maryam.bakingapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.maryam.bakingapp.Utils.AppUtils;
import com.example.maryam.bakingapp.model.recipe;

public class RecipeInfoActivity extends AppCompatActivity {


    static Boolean isTwoPane = false;
    String recipesName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_info);
        Bundle selectedRecipeBundle = getIntent().getExtras();
        recipe selectedRecipe = selectedRecipeBundle.getParcelable(AppUtils.SEND_RECIPE);
        recipesName = selectedRecipe.getName();
        getSupportActionBar().setTitle(recipesName);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final RecipeDetailsInfoFragment infoFragment = new RecipeDetailsInfoFragment();
        infoFragment.setArguments(selectedRecipeBundle);

        if (findViewById(R.id.two_pane_linerlayout) != null) {
            isTwoPane = true;
            selectedRecipeBundle.putBoolean(AppUtils.TWO_PANE_LAYOUT, isTwoPane);
            RecipeDetailsInfoFragment infoFragment1 = new RecipeDetailsInfoFragment();
            infoFragment1.setArguments(selectedRecipeBundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.recipe_container, infoFragment1).addToBackStack(null).commit();
        } else {
            isTwoPane = false;
            selectedRecipeBundle.putBoolean(AppUtils.TWO_PANE_LAYOUT, isTwoPane);
            getSupportFragmentManager().beginTransaction().replace(R.id.recipe_container, infoFragment).addToBackStack(null).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(RecipeInfoActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Title", recipesName);
    }


}

