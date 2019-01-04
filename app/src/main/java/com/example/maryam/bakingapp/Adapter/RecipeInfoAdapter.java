package com.example.maryam.bakingapp.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.maryam.bakingapp.R;
import com.example.maryam.bakingapp.model.Ingredients;

import java.util.List;

public class RecipeInfoAdapter extends RecyclerView.Adapter<RecipeInfoAdapter.ViewHolder> {

    private List<Ingredients> ingredients;

    public RecipeInfoAdapter(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.ingredients_list_item, parent, false);
        return new RecipeInfoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeInfoAdapter.ViewHolder holder, int position) {
        holder.ingredient.setText(ingredients.get(position).getIngredient());
        holder.quantity.setText(ingredients.get(position).getStr());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView quantity, ingredient;

        public ViewHolder(View itemView) {
            super(itemView);
            quantity = itemView.findViewById(R.id.quantity_tv);
            ingredient = itemView.findViewById(R.id.ingredient_tv);


        }
    }
}
