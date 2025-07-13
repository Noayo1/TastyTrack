package com.example.finalproject.Adapters;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.models.Recipe;

import java.util.List;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private Context context;
    private List<Recipe> recipeList;
    private OnFavoriteClickListener listener;
    private int navActionId;

    public interface OnFavoriteClickListener {
        void onFavoriteClick(Recipe recipe);
    }

    public RecipeAdapter(Context context, List<Recipe> recipeList, OnFavoriteClickListener listener, int navActionId) {
        this.context = context;
        this.recipeList = recipeList;
        this.listener = listener;
        this.navActionId = navActionId;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_row, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        Recipe recipe = recipeList.get(position);
        holder.name.setText(recipe.getName());
        holder.image.setImageResource(recipe.getImageResId());

        holder.favoriteButton.setImageResource(recipe.isFavorite()
                ? R.drawable.favorit_icon_pink
                : R.drawable.favorit_icon_gray);

        holder.favoriteButton.setOnClickListener(v -> {
            listener.onFavoriteClick(recipe);
            notifyItemChanged(position);
        });

        // ניווט לדף פרטים
        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("recipe", recipe);
            NavController navController = Navigation.findNavController(v);
            navController.navigate(navActionId, bundle);
        });

        // הצגת תגיות כאייקונים
        holder.tagsContainer.removeAllViews();
        List<String> tags = recipe.getTags();
        if (tags != null && !tags.isEmpty()) {
            holder.tagsContainer.setVisibility(View.VISIBLE);

            for (String tag : tags) {
                int iconRes = getIconForTag(tag);
                if (iconRes != 0) {
                    ImageView icon = new ImageView(context);
                    icon.setImageResource(iconRes);

                    int size = (int) (24 * context.getResources().getDisplayMetrics().density);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
                    params.setMarginEnd(8);
                    icon.setLayoutParams(params);

                    holder.tagsContainer.addView(icon);
                }
            }
        } else {
            holder.tagsContainer.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return recipeList.size();
    }

    public static class RecipeViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        ImageView image, favoriteButton;
        LinearLayout tagsContainer;

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            image = itemView.findViewById(R.id.image);
            favoriteButton = itemView.findViewById(R.id.favoriteButton);
            tagsContainer = itemView.findViewById(R.id.tagsContainer);
        }
    }

    // מזהה אייקון לפי תגית
    private int getIconForTag(String tag) {
        switch (tag.toLowerCase()) {
            case "gluten-free":
                return R.drawable.ic_gluten_free;
            case "vegetarian":
                return R.drawable.ic_vegan;
            default:
                return 0;
        }
    }
}
