package com.example.finalproject.fragments;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.finalproject.R;
import com.example.finalproject.models.Recipe;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.*;

import java.util.ArrayList;
import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class UploadRecipe extends Fragment {

    private LinearLayout ingredientsContainer;
    private Button addIngredientButton, saveButton, pickImageButton;
    private EditText recipeNameEditText, imageUrlEditText, instructionsEditText;
    private EditText prepTimeEditText;
    private Spinner difficultySpinner;

    private DatabaseReference recipesRef;
    private String userId;

    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_PERMISSION = 100;
    private Uri selectedImageUri;

    private Recipe recipeToEdit = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        recipesRef = FirebaseDatabase.getInstance().getReference("Recipes").child(userId);

        if (getArguments() != null) {
            recipeToEdit = (Recipe) getArguments().getSerializable("editRecipe");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_upload_recipe, container, false);

        ingredientsContainer = view.findViewById(R.id.ingredientsContainer);
        addIngredientButton = view.findViewById(R.id.addIngredientButton);
        saveButton = view.findViewById(R.id.saveButton);
        pickImageButton = view.findViewById(R.id.pickImageButton);
        recipeNameEditText = view.findViewById(R.id.recipeNameEditText);
        imageUrlEditText = view.findViewById(R.id.imageUrlEditText);
        instructionsEditText = view.findViewById(R.id.instructionsEditText);
        prepTimeEditText = view.findViewById(R.id.prepTimeEditText);
        difficultySpinner = view.findViewById(R.id.difficultySpinner);

        if (recipeToEdit != null) {
            recipeNameEditText.setText(recipeToEdit.getName());
            imageUrlEditText.setText(recipeToEdit.getImageUrl());
            instructionsEditText.setText(recipeToEdit.getInstructions());
            prepTimeEditText.setText(String.valueOf(recipeToEdit.getPrepTimeMinutes()));

            String[] difficulties = getResources().getStringArray(R.array.difficulty_levels);
            for (int i = 0; i < difficulties.length; i++) {
                if (difficulties[i].equalsIgnoreCase(recipeToEdit.getDifficulty())) {
                    difficultySpinner.setSelection(i);
                    break;
                }
            }

            for (String ing : recipeToEdit.getIngredients()) {
                View ingredientView = getLayoutInflater().inflate(R.layout.ingredient_row, null);
                EditText ingredientEdit = ingredientView.findViewById(R.id.ingredientEditText);
                EditText quantityEdit = ingredientView.findViewById(R.id.quantityEditText);

                if (ing.contains(" ")) {
                    int firstSpace = ing.indexOf(" ");
                    quantityEdit.setText(ing.substring(0, firstSpace));
                    ingredientEdit.setText(ing.substring(firstSpace + 1));
                } else {
                    ingredientEdit.setText(ing);
                }

                ingredientsContainer.addView(ingredientView);
            }
        }

        addIngredientButton.setOnClickListener(v -> {
            View ingredientView = getLayoutInflater().inflate(R.layout.ingredient_row, null);
            ingredientsContainer.addView(ingredientView);
        });

        pickImageButton.setOnClickListener(v -> {
            if (hasStoragePermission()) {
                openGallery();
            } else {
                requestPermissions(
                        new String[]{getStoragePermissionName()},
                        REQUEST_PERMISSION
                );
            }
        });

        saveButton.setOnClickListener(v -> saveRecipe());

        return view;
    }

    private boolean hasStoragePermission() {
        String permission = getStoragePermissionName();
        return ContextCompat.checkSelfPermission(requireContext(), permission) == PERMISSION_GRANTED;
    }

    private String getStoragePermissionName() {
        return Build.VERSION.SDK_INT >= 33 ?
                android.Manifest.permission.READ_MEDIA_IMAGES :
                android.Manifest.permission.READ_EXTERNAL_STORAGE;
    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
            openGallery();
        } else {
            Toast.makeText(getContext(), "Permission denied to access gallery", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            imageUrlEditText.setText(selectedImageUri.toString());
            Toast.makeText(getContext(), "Image selected", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveRecipe() {
        String name = recipeNameEditText.getText().toString().trim();
        String imageUrl = imageUrlEditText.getText().toString().trim();
        String instructions = instructionsEditText.getText().toString().trim();
        String difficulty = difficultySpinner.getSelectedItem().toString().trim();
        int prepTime = 0;

        try {
            prepTime = Integer.parseInt(prepTimeEditText.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast.makeText(getContext(), "Enter valid preparation time", Toast.LENGTH_SHORT).show();
            return;
        }

        if (name.isEmpty() || instructions.isEmpty()) {
            Toast.makeText(getContext(), "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        List<String> ingredientsList = new ArrayList<>();
        for (int i = 0; i < ingredientsContainer.getChildCount(); i++) {
            View row = ingredientsContainer.getChildAt(i);
            EditText ingredientEdit = row.findViewById(R.id.ingredientEditText);
            EditText quantityEdit = row.findViewById(R.id.quantityEditText);

            String ingredient = ingredientEdit.getText().toString().trim();
            String quantity = quantityEdit.getText().toString().trim();

            if (!ingredient.isEmpty() && !quantity.isEmpty()) {
                ingredientsList.add(quantity + " " + ingredient);
            }
        }

        String id = recipeToEdit != null ? recipeToEdit.getId() : recipesRef.push().getKey();
        Recipe recipe = new Recipe(name, imageUrl, ingredientsList, instructions, id);
        recipe.setPrepTimeMinutes(prepTime);
        recipe.setDifficulty(difficulty);

        recipesRef.child(id).setValue(recipe)
                .addOnSuccessListener(unused -> {
                    Toast.makeText(getContext(), "Recipe saved!", Toast.LENGTH_SHORT).show();
                    Navigation.findNavController(requireView()).popBackStack();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Failed to save", Toast.LENGTH_SHORT).show();
                });
    }
}
