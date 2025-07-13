package com.example.finalproject.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.finalproject.R;
import com.example.finalproject.models.Recipe;
import com.example.finalproject.Adapters.UploadAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

public class MyRecipes extends Fragment {

    private RecyclerView recyclerView;
    private UploadAdapter adapter;
    private List<Recipe> recipeList;

    private DatabaseReference recipesRef;

    public MyRecipes() {}

    public static MyRecipes newInstance(String param1, String param2) {
        MyRecipes fragment = new MyRecipes();
        Bundle args = new Bundle();
        args.putString("param1", param1);
        args.putString("param2", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        recipesRef = FirebaseDatabase.getInstance().getReference("Recipes").child(userId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_recipes, container, false);

        Button uploadButton = view.findViewById(R.id.uploadRecipeButton);
        uploadButton.setOnClickListener(v -> {
            Navigation.findNavController(view).navigate(R.id.action_MyRecipes_to_uploadRecipe);
        });

        recyclerView = view.findViewById(R.id.MyRecipesRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recipeList = new ArrayList<>();
        adapter = new UploadAdapter(getContext(), recipeList);
        recyclerView.setAdapter(adapter);

        loadRecipesFromFirebase();

        return view;
    }

    private void loadRecipesFromFirebase() {
        recipesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                recipeList.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    Recipe recipe = child.getValue(Recipe.class);
                    if (recipe != null) {
                        recipeList.add(recipe);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Failed to load recipes", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
