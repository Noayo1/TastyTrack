package com.example.finalproject.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.finalproject.R;
import com.example.finalproject.models.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.List;

public class UploadAdapter extends RecyclerView.Adapter<UploadAdapter.RecipeViewHolder> {

    private Context context;
    private List<Recipe> recipeList;

    public UploadAdapter(Context context, List<Recipe> recipeList) {
        this.context = context;
        this.recipeList = recipeList;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_my_recipes, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);

        holder.nameTextView.setText(recipe.getName());
        if (recipe.getImageUrl() != null && !recipe.getImageUrl().isEmpty()) {
            Glide.with(context)
                    .load(recipe.getImageUrl())
                    .placeholder(R.drawable.logo)
                    .into(holder.imageView);
        } else {
            holder.imageView.setImageResource(R.drawable.logo);
        }


        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("recipe", recipe);
            androidx.navigation.Navigation.findNavController(v)
                    .navigate(R.id.action_recipesRecycleView_to_recipe_details, bundle);
        });


        holder.editButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("editRecipe", recipe);
            androidx.navigation.Navigation.findNavController(v)
                    .navigate(R.id.action_MyRecipes_to_uploadRecipe, bundle);
        });


        holder.deleteButton.setOnClickListener(v -> {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference recipeRef = FirebaseDatabase.getInstance().getReference("Recipes")
                    .child(userId)
                    .child(recipe.getId());

            recipeRef.removeValue()
                    .addOnSuccessListener(unused -> {
                        int pos = holder.getAdapterPosition();
                        if (pos != RecyclerView.NO_POSITION && pos < recipeList.size()) {
                            recipeList.remove(pos);
                            notifyItemRemoved(pos);
                        }
                        Toast.makeText(context, "Recipe deleted", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(context, "Failed to delete", Toast.LENGTH_SHORT).show()
                    );
        });
    }


    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        ImageView imageView;
        ImageView deleteButton;
        ImageView editButton;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.name);
            imageView = itemView.findViewById(R.id.image);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton = itemView.findViewById(R.id.editButton);
        }
    }
}
