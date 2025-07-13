package com.example.finalproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.models.Recipe;
import com.example.finalproject.Adapters.RecipeAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class FavoriesRecipes extends Fragment {

    private List<Recipe> allFavorites;
    private List<Recipe> filteredFavorites;
    private RecipeAdapter adapter;

    private SearchView searchView;
    private Spinner spinner;

    public FavoriesRecipes() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favories_recipes, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.favoritesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        searchView = view.findViewById(R.id.searchView);
        spinner = view.findViewById(R.id.spinner);

        allFavorites = new ArrayList<>();
        filteredFavorites = new ArrayList<>();

        adapter = new RecipeAdapter(getContext(), filteredFavorites, recipe -> {
            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference favRef = FirebaseDatabase.getInstance()
                    .getReference("users").child(userId).child("favorites");

            String safeName = recipe.getName().replaceAll("[.#$\\[\\]/]", "_");

            favRef.child(safeName).removeValue()
                    .addOnSuccessListener(aVoid -> {
                        allFavorites.remove(recipe);
                        filterRecipes(searchView.getQuery().toString(), spinner.getSelectedItem().toString());
                        Toast.makeText(getContext(), "The recipe has been removed from favorites.", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e ->
                            Toast.makeText(getContext(), "Recipe removal failed.", Toast.LENGTH_SHORT).show());
        }, R.id.action_favoriesRecipes_to_recipe_details);

        recyclerView.setAdapter(adapter);

        ArrayAdapter<CharSequence> spinnerAdapter = ArrayAdapter.createFromResource(
                requireContext(),
                R.array.recipe_categories,
                android.R.layout.simple_spinner_item
        );
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                filterRecipes(searchView.getQuery().toString(), spinner.getSelectedItem().toString());
            }
            @Override public void onNothingSelected(AdapterView<?> parent) {}
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override public boolean onQueryTextSubmit(String query) { return false; }
            @Override public boolean onQueryTextChange(String newText) {
                filterRecipes(newText, spinner.getSelectedItem().toString());
                return true;
            }
        });

        loadFavoritesFromFirebase();

        View backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> requireActivity().onBackPressed());


        return view;
    }

    private void filterRecipes(String searchText, String category) {
        String text = searchText.toLowerCase().trim();
        filteredFavorites.clear();

        for (Recipe recipe : allFavorites) {
            boolean matchesText = recipe.getName().toLowerCase().contains(text)
                    || recipe.getDescription().toLowerCase().contains(text);
            boolean matchesCategory = category.equals("All")
                    || recipe.getCategories().contains(category);

            if (matchesText && matchesCategory) {
                filteredFavorites.add(recipe);
            }
        }

        adapter.notifyDataSetChanged();
    }

    private void loadFavoritesFromFirebase() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference favRef = FirebaseDatabase.getInstance()
                .getReference("users").child(userId).child("favorites");

        favRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                allFavorites.clear();

                for (DataSnapshot recipeSnap : snapshot.getChildren()) {
                    String name = recipeSnap.child("name").getValue(String.class);
                    String description = recipeSnap.child("description").getValue(String.class);
                    Long imageResIdLong = recipeSnap.child("imageResId").getValue(Long.class);
                    String instructions = recipeSnap.child("instructions").getValue(String.class);

                    // מצרכים
                    List<String> ingredients = new ArrayList<>();
                    for (DataSnapshot ingredientSnap : recipeSnap.child("ingredients").getChildren()) {
                        String ingredient = ingredientSnap.getValue(String.class);
                        if (ingredient != null) ingredients.add(ingredient);
                    }

                    // תגיות
                    List<String> tags = new ArrayList<>();
                    for (DataSnapshot tagSnap : recipeSnap.child("tags").getChildren()) {
                        String tag = tagSnap.getValue(String.class);
                        if (tag != null) tags.add(tag);
                    }

                    // קטגוריות — תמיכה גם ב-List וגם ב-String
                    List<String> categories = new ArrayList<>();
                    DataSnapshot catSnap = recipeSnap.child("category");

                    if (catSnap.exists()) {
                        if (catSnap.hasChildren()) {
                            for (DataSnapshot child : catSnap.getChildren()) {
                                String cat = child.getValue(String.class);
                                if (cat != null) categories.add(cat);
                            }
                        } else {
                            String singleCat = catSnap.getValue(String.class);
                            if (singleCat != null) categories.add(singleCat);
                        }
                    }

                    // זמן הכנה וקושי
                    Integer prepTime = recipeSnap.child("prepTimeMinutes").getValue(Integer.class);
                    String difficulty = recipeSnap.child("difficulty").getValue(String.class);

                    if (name != null && imageResIdLong != null) {
                        Recipe recipe = new Recipe(name, description, categories, imageResIdLong.intValue(), true);
                        recipe.setInstructions(instructions);
                        recipe.setIngredients(ingredients);
                        recipe.setTags(tags);
                        recipe.setPrepTimeMinutes(prepTime != null ? prepTime : 0);
                        recipe.setDifficulty(difficulty);

                        allFavorites.add(recipe);
                    }
                }

                filterRecipes("", "All");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Error loading favorites", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
