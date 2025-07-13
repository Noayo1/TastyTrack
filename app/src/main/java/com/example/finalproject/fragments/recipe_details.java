package com.example.finalproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.models.Recipe;

import java.util.List;

public class recipe_details extends Fragment {

    public recipe_details() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recipe_details, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView recipeName = view.findViewById(R.id.recipeName);
        TextView recipeDescription = view.findViewById(R.id.recipeDescription);
        TextView recipeInstructions = view.findViewById(R.id.recipeInstructions);
        ImageView recipeImage = view.findViewById(R.id.recipeImage);
        LinearLayout ingredientsList = view.findViewById(R.id.ingredientsList);
        TextView prepTime = view.findViewById(R.id.prepTime);
        TextView difficulty = view.findViewById(R.id.difficulty);
        TextView tags = view.findViewById(R.id.tags);


        Bundle args = getArguments();
        if (args != null && args.containsKey("recipe")) {
            Recipe recipe = (Recipe) args.getSerializable("recipe");

            if (recipe == null) {
                Toast.makeText(getContext(), "Error: Recipe is null", Toast.LENGTH_LONG).show();
                return;
            }
            Toast.makeText(getContext(), "Loaded: " + recipe.getName(), Toast.LENGTH_SHORT).show();
            recipeName.setText(recipe.getName());

            if (recipe.getDescription() != null && !recipe.getDescription().isEmpty()) {
                recipeDescription.setText(recipe.getDescription());
                recipeDescription.setVisibility(View.VISIBLE);
            } else {
                recipeDescription.setVisibility(View.GONE);
            }

            if (recipe.getInstructions() != null && !recipe.getInstructions().isEmpty()) {
                recipeInstructions.setText(recipe.getInstructions());
                recipeInstructions.setVisibility(View.VISIBLE);
            } else {
                recipeInstructions.setVisibility(View.GONE);
            }

            if (recipe.getImageUrl() != null && !recipe.getImageUrl().isEmpty()) {
                Glide.with(this)
                        .load(recipe.getImageUrl())
                        .placeholder(R.drawable.logo)
                        .into(recipeImage);
            } else if (recipe.getImageResId() != 0) {
                recipeImage.setImageResource(recipe.getImageResId());
            } else {
                recipeImage.setVisibility(View.GONE);
            }

            List<String> ingredients = recipe.getIngredients();
            if (ingredients != null && !ingredients.isEmpty()) {
                for (String ingredient : ingredients) {
                    TextView ingredientView = new TextView(getContext());
                    ingredientView.setText("- " + ingredient);
                    ingredientView.setTextSize(16);
                    ingredientView.setPadding(0, 4, 0, 4);
                    ingredientsList.addView(ingredientView);
                }
            } else {
                ingredientsList.setVisibility(View.GONE);
            }

            if (recipe.getPrepTimeMinutes() > 0) {
                prepTime.setText("Prep time: " + recipe.getPrepTimeMinutes() + " min");
                prepTime.setVisibility(View.VISIBLE);
            } else {
                prepTime.setVisibility(View.GONE);
            }

            if (recipe.getDifficulty() != null && !recipe.getDifficulty().isEmpty()) {
                difficulty.setText("Difficulty: " + recipe.getDifficulty());
                difficulty.setVisibility(View.VISIBLE);
            } else {
                difficulty.setVisibility(View.GONE);
            }

            List<String> tagList = recipe.getTags();
            if (tagList != null && !tagList.isEmpty()) {
                tags.setText("Tags: " + String.join(", ", tagList));
                tags.setVisibility(View.VISIBLE);
            } else {
                tags.setVisibility(View.GONE);
            }

        } else {
            Toast.makeText(getContext(), "Error: No recipe found in Bundle", Toast.LENGTH_LONG).show();
        }
    }
}
