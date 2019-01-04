package com.example.maryam.bakingapp;


import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.example.maryam.bakingapp.Adapter.RecipeInfoAdapter;
import com.example.maryam.bakingapp.Adapter.StepsAdapter;
import com.example.maryam.bakingapp.Utils.AppUtils;
import com.example.maryam.bakingapp.model.Ingredients;
import com.example.maryam.bakingapp.model.Step;
import com.example.maryam.bakingapp.model.recipe;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecipeDetailsInfoFragment extends Fragment {
    private Boolean isTwoPane;
    Bundle bundle;
    StepsAdapter stepsAdapter;
    RecipeInfoAdapter recipeInfoAdapter;
    ScrollView scrollView;

    public RecipeDetailsInfoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ingredients_steps, container, false);
        RecyclerView ingredientsRecyclerView = view.findViewById(R.id.ingredients_rv);
        RecyclerView stepsRecyclerView = view.findViewById(R.id.steps_rv);
        scrollView = view.findViewById(R.id.scrollView);
        FloatingActionButton addToWidgetFab = view.findViewById(R.id.add_to_widget_fab);
        ingredientsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        stepsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        bundle = this.getArguments();
        recipe sendRecipe = bundle.getParcelable(AppUtils.SEND_RECIPE);
        recipeInfoAdapter = new RecipeInfoAdapter(sendRecipe.getIngredients());
        getStep(sendRecipe);
        isTwoPane = bundle.getBoolean(AppUtils.TWO_PANE_LAYOUT);
        ingredientsRecyclerView.setAdapter(recipeInfoAdapter);
        stepsRecyclerView.setAdapter(stepsAdapter);
        addToWidgetFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder = stringBuilder
                        .append(sendRecipe.getName()).append("\n");
                String widgetRecipe = null;
                for (Ingredients ingredients : sendRecipe.getIngredients()) {
                    widgetRecipe = stringBuilder.append(ingredients.getIngredient()).append("\n").toString();
                }
                SharedPreferences.Editor editor = getActivity().
                        getSharedPreferences(AppUtils.SHARED_PREFERENCE, Context.MODE_PRIVATE)
                        .edit();
                editor.putString(AppUtils.PREFERENCES_WIDGET_CONTENT, widgetRecipe).apply();
                // Put changes on the Widget
                ComponentName provider = new ComponentName(getContext(), BakingWidget.class);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(getContext());
                int[] ids = appWidgetManager.getAppWidgetIds(provider);
                BakingWidget bakingWidgetProvider = new BakingWidget();
                bakingWidgetProvider.onUpdate(getContext(), appWidgetManager, ids);
                Snackbar.make(scrollView, "Recipe ingredients are added to the widget", Snackbar.LENGTH_LONG).show();
            }
        });
        return view;
    }

    public void getStep(recipe recipe) {

        stepsAdapter = new StepsAdapter(getContext(), recipe.getSteps(), clickedItemIndex -> {
            Step steps = stepsAdapter.getStepIndex(clickedItemIndex);
            Bundle bundle1 = new Bundle();
            bundle1.putParcelable(AppUtils.SEND_STEP, steps);
            StepsDetailsFragment stepsDetailsFragment = new StepsDetailsFragment();
            stepsDetailsFragment.setArguments(bundle1);
            if (isTwoPane == true) {
                getFragmentManager().beginTransaction().replace(R.id.recipe_container2, stepsDetailsFragment).commit();
            } else {
                Intent i = new Intent(getActivity(), StepsActivity.class);
                i.putExtras(bundle1);
                startActivity(i);
                //getFragmentManager().beginTransaction().replace(R.id.recipe_container, stepsDetailsFragment).commit();
            }
        });

    }


}
