package com.example.maryam.bakingapp.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.maryam.bakingapp.R;
import com.example.maryam.bakingapp.model.recipe;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainActivityAdapter extends RecyclerView.Adapter<MainActivityAdapter.ViewHolder> {
    public interface RecipeItemClickListener {
        void onRecipeItemClick(int clickedItemIndex);
    }

    final private RecipeItemClickListener mOnClickListener;
    private List<recipe> recipes;
    private Context context;

    public MainActivityAdapter(Context context, List<recipe> recipes, RecipeItemClickListener clickListener) {
        this.context = context;
        this.recipes = recipes;
        this.mOnClickListener = clickListener;
    }

    @NonNull
    @Override
    public MainActivityAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.recipe_list_item, parent, false);
        return new MainActivityAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MainActivityAdapter.ViewHolder holder, int position) {
        holder.recipeNameTv.setText(recipes.get(position).getName());
        holder.setImage(recipes.get(position).getImage());
    }

    public recipe getRecipeAtIndex(int index) {
        return recipes.get(index);
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView recipeNameTv;
        ImageView recipeImageView;

        ViewHolder(View itemView) {
            super(itemView);
            recipeNameTv = itemView.findViewById(R.id.recipes_name_tv);
            recipeImageView = itemView.findViewById(R.id.recipes_image);
            context = itemView.getContext();

            itemView.setOnClickListener(this);
        }

        void setImage(String image) {
            if (image.trim().equals("")) {
                recipeImageView.setImageResource(R.drawable.bakinghat);
            } else {
                Picasso.with(context)
                        .load(image)
                        .into(recipeImageView);
            }
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onRecipeItemClick(clickedPosition);
        }
    }
}
