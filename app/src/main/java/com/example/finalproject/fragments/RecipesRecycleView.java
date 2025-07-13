package com.example.finalproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import android.widget.Spinner;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;

import com.example.finalproject.R;
import com.example.finalproject.models.Recipe;
import com.example.finalproject.models.RecipeData;
import com.example.finalproject.Adapters.RecipeAdapter;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecipesRecycleView extends Fragment {

    private List<Recipe> allRecipes;
    private List<Recipe> filteredRecipes;
    private RecipeAdapter adapter;

    public RecipesRecycleView() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipes_recycle_view, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.favoritesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        SearchView searchView = view.findViewById(R.id.searchView);
        Spinner spinner = view.findViewById(R.id.spinner);

        allRecipes = RecipeData.getRecipes();
        filteredRecipes = new ArrayList<>();

        adapter = new RecipeAdapter(getContext(), filteredRecipes, recipe -> {
            recipe.setFavorite(!recipe.isFavorite());

            String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
            DatabaseReference favRef = FirebaseDatabase.getInstance()
                    .getReference("users").child(userId).child("favorites");

            String safeName = recipe.getName().replaceAll("[.#$\\[\\]/]", "_");

            if (recipe.isFavorite()) {
                Map<String, Object> map = new HashMap<>();
                map.put("name", recipe.getName());
                map.put("description", recipe.getDescription());
                map.put("category", recipe.getCategories());
                map.put("imageResId", recipe.getImageResId());
                map.put("isFavorite", true);
                map.put("instructions", recipe.getInstructions());
                map.put("ingredients", recipe.getIngredients());
                map.put("prepTimeMinutes", recipe.getPrepTimeMinutes());
                map.put("difficulty", recipe.getDifficulty());
                map.put("tags", recipe.getTags());
                favRef.child(safeName).setValue(map);
            } else {
                favRef.child(safeName).removeValue();
            }

            int position = filteredRecipes.indexOf(recipe);
            adapter.notifyItemChanged(position);

        }, R.id.action_recipesRecycleView_to_recipe_details);

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
                filterRecipes(searchView.getQuery().toString(), spinner.getSelectedItem().toString());
                return true;
            }
        });

        // Load favorites from Firebase and mark favorites
        loadFavoritesAndMark();

        View backButton = view.findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> requireActivity().onBackPressed());

        return view;
    }

    private void loadFavoritesAndMark() {
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        DatabaseReference favRef = FirebaseDatabase.getInstance()
                .getReference("users").child(userId).child("favorites");

        favRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<String> favoriteNames = new ArrayList<>();

                for (DataSnapshot favSnap : snapshot.getChildren()) {
                    String name = favSnap.child("name").getValue(String.class);
                    if (name != null) favoriteNames.add(name);
                }

                for (Recipe recipe : allRecipes) {
                    recipe.setFavorite(favoriteNames.contains(recipe.getName()));
                }

                filteredRecipes.clear();
                filteredRecipes.addAll(allRecipes);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                filteredRecipes.clear();
                filteredRecipes.addAll(allRecipes);
                adapter.notifyDataSetChanged();
            }
        });
    }

    private void filterRecipes(String searchText, String category) {
        String text = searchText.toLowerCase().trim();
        filteredRecipes.clear();

        for (Recipe recipe : allRecipes) {
            boolean matchesText = recipe.getName().toLowerCase().contains(text)
                    || recipe.getDescription().toLowerCase().contains(text);
            boolean matchesCategory = category.equals("All")
                    || recipe.getCategories().contains(category);


            if (matchesText && matchesCategory) {
                filteredRecipes.add(recipe);
            }
        }

        adapter.notifyDataSetChanged();
    }
}
