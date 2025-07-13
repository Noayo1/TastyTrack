package com.example.finalproject.models;

import java.io.Serializable;
import java.util.List;

public class Recipe implements Serializable {
    private String name;
    private String description;
    private List<String> categories;
    private int imageResId;
    private boolean isFavorite;
    private List<String> ingredients;
    private String instructions;
    private int prepTimeMinutes;
    private String difficulty;
    private List<String> tags;
    private String imageUrl;
    private String id;

    public Recipe() {}
    public Recipe(String name, String description, List<String> categories, int imageResId, boolean isFavorite) {
        this.name = name;
        this.description = description;
        this.categories = categories;
        this.imageResId = imageResId;
        this.isFavorite = isFavorite;
    }

    public Recipe(String name, String imageUrl, List<String> ingredients, String instructions, String id) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.id = id;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getCategories() { return categories; }
    public void setCategories(List<String> categories) { this.categories = categories; }

    public int getImageResId() { return imageResId; }
    public void setImageResId(int imageResId) { this.imageResId = imageResId; }

    public boolean isFavorite() { return isFavorite; }
    public void setFavorite(boolean favorite) { isFavorite = favorite; }

    public List<String> getIngredients() { return ingredients; }
    public void setIngredients(List<String> ingredients) { this.ingredients = ingredients; }

    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; }

    public int getPrepTimeMinutes() { return prepTimeMinutes; }
    public void setPrepTimeMinutes(int prepTimeMinutes) { this.prepTimeMinutes = prepTimeMinutes; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
}
