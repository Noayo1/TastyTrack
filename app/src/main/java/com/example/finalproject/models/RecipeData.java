package com.example.finalproject.models;

import com.example.finalproject.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RecipeData {

    public static List<Recipe> getRecipes() {
        List<Recipe> recipes = new ArrayList<>();

        // Pasta
        Recipe pasta = new Recipe("Pasta", "Delicious Italian pasta. Quantities are for 1 person. Multiply accordingly.", Arrays.asList("Italian" , "Vegetarian"), R.drawable.pasta, false);
        pasta.setIngredients(Arrays.asList("250g pasta", "1 tbsp olive oil", "2 garlic cloves", "Salt", "Pepper"));
        pasta.setInstructions("1. Boil pasta until al dente.\n2. Sauté garlic in olive oil.\n3. Mix with pasta, season and serve.");
        pasta.setPrepTimeMinutes(20);
        pasta.setDifficulty("Easy");
        pasta.setTags(Arrays.asList("Vegetarian"));

        // Pizza
        Recipe pizza = new Recipe("Pizza", "Cheesy pizza with toppings. Quantities are for 1 person. Multiply accordingly.", Arrays.asList("Italian"), R.drawable.pizza, false);
        pizza.setIngredients(Arrays.asList("Pizza dough", "3 tbsp tomato sauce", "100g mozzarella", "Optional toppings"));
        pizza.setInstructions("1. Spread sauce on dough.\n2. Add cheese and toppings you like.\n3. Bake at 220°C for 10-15 minutes.");
        pizza.setPrepTimeMinutes(25);
        pizza.setDifficulty("Medium");
        pizza.setTags(Arrays.asList("Vegetarian", "Contains Gluten"));

        // Greek Salad
        Recipe greekSalad = new Recipe("Greek Salad", "Classic Greek salad. Quantities are for 1 person. Multiply accordingly.", Arrays.asList("Salads"), R.drawable.greek_salad, false);
        greekSalad.setIngredients(Arrays.asList("1 tomato", "1/2 cucumber", "50g feta cheese", "5 olives", "1 tbsp olive oil", "1 tsp lemon juice", "Salt"));
        greekSalad.setInstructions("1. Chop vegetables.\n2. Crumble feta.\n3. Add olives, oil & lemon juice.");
        greekSalad.setPrepTimeMinutes(10);
        greekSalad.setDifficulty("Easy");
        greekSalad.setTags(Arrays.asList("Vegetarian", "Gluten-Free"));

        // Caesar Salad
        Recipe caesar = new Recipe("Caesar Salad", "Caesar salad with homemade dressing. Quantities are for 1 person.", Arrays.asList("Salads"), R.drawable.caesar_salad, false);
        caesar.setIngredients(Arrays.asList("1 cup romaine lettuce", "1 tbsp grated parmesan", "4–5 croutons", "Caesar dressing"));
        caesar.setInstructions("1. Mix lettuce, parmesan, croutons.\n2. Toss with dressing.\n\nDressing:\n- Mustard, anchovy, garlic, lemon, mayo.");
        caesar.setPrepTimeMinutes(12);
        caesar.setDifficulty("Easy");
        caesar.setTags(Arrays.asList("Contains Gluten", "Contains Egg", "Not Vegan"));

        // Risotto
        Recipe risotto = new Recipe("Truffle Risotto", "Creamy risotto with truffle aroma. Quantities are for 1 person.", Arrays.asList("Italian"), R.drawable.risotto, false);
        risotto.setIngredients(Arrays.asList("1/4 cup arborio rice", "1 tbsp butter", "1 tbsp chopped onion", "1/2 cup vegetable broth", "2 tbsp parmesan", "Truffle oil"));
        risotto.setInstructions("1. Sauté onion in butter.\n2. Add rice and toast 1 min.\n3. Gradually add broth while stirring.\n4. Stir in parmesan and drizzle truffle oil.");
        risotto.setPrepTimeMinutes(30);
        risotto.setDifficulty("Medium");
        risotto.setTags(Arrays.asList("Vegetarian", "Gourmet", "Gluten-Free"));

        // Lasagna
        Recipe lasagna = new Recipe("Lasagna", "Layered pasta with meat and béchamel. Quantities are for 1 person.", Arrays.asList("Italian"), R.drawable.lasagna, false);
        lasagna.setIngredients(Arrays.asList("2 lasagna sheets", "1/4 cup meat sauce", "1/4 cup béchamel", "2 tbsp cheese"));
        lasagna.setInstructions("1. Layer: pasta, meat, béchamel.\n2. Repeat, top with cheese.\n3. Bake 25–30 min at 180°C.");
        lasagna.setPrepTimeMinutes(40);
        lasagna.setDifficulty("Hard");
        lasagna.setTags(Arrays.asList("Meat", "Contains Gluten", "Not Vegetarian"));

        // Shakshuka
        Recipe shakshuka = new Recipe("Shakshuka", "Israeli tomato & egg dish. Quantities are for 1 person.", Arrays.asList("Middle Eastern", "Breakfast"), R.drawable.shakshuka, false);
        shakshuka.setIngredients(Arrays.asList("2 eggs", "1/2 onion", "1/2 bell pepper", "1 tomato", "2 tbsp tomato paste", "Cumin", "Salt", "Olive oil"));
        shakshuka.setInstructions("1. Sauté onion & pepper.\n2. Add tomato & paste.\n3. Crack eggs and cook covered.");
        shakshuka.setPrepTimeMinutes(15);
        shakshuka.setDifficulty("Easy");
        shakshuka.setTags(Arrays.asList("Vegetarian", "Gluten-Free"));

        // Pancakes
        Recipe pancakes = new Recipe("Pancakes", "Fluffy pancakes. Quantities for 1 person (2–3).", Arrays.asList("Breakfast"), R.drawable.pancakes, false);
        pancakes.setIngredients(Arrays.asList("1/2 cup flour", "1 egg", "1/2 cup milk", "1 tbsp sugar", "1/2 tsp baking powder", "Butter for pan"));
        pancakes.setInstructions("1. Mix ingredients.\n2. Cook in buttered pan until golden.");
        pancakes.setPrepTimeMinutes(10);
        pancakes.setDifficulty("Easy");
        pancakes.setTags(Arrays.asList("Contains Gluten", "Contains Egg"));

        // Hummus
        Recipe hummus = new Recipe("Hummus Plate", "Classic creamy hummus. Quantities are for 1 person.", Arrays.asList("Middle Eastern", "Vegan", "Vegetarian"), R.drawable.hummus, false);
        hummus.setIngredients(Arrays.asList("1/2 cup chickpeas", "1 tbsp tahini", "1 garlic clove", "1 tbsp lemon juice", "Salt", "Olive oil", "Paprika"));
        hummus.setInstructions("1. Blend all ingredients.\n2. Plate, drizzle oil, sprinkle paprika.");
        hummus.setPrepTimeMinutes(8);
        hummus.setDifficulty("Easy");
        hummus.setTags(Arrays.asList("Vegan", "Gluten-Free", "High Protein"));

        Recipe padThai = new Recipe("Pad Thai", "Classic Thai stir-fried noodles. Quantities are for 1 person.", Arrays.asList("Thai"), R.drawable.pad_thai, false);
        padThai.setIngredients(Arrays.asList("100g rice noodles", "1 egg", "1/4 cup tofu or chicken", "1 tbsp fish sauce (or soy sauce)", "1 tbsp tamarind paste", "1 tbsp sugar", "2 tbsp crushed peanuts", "1/2 lime", "Bean sprouts", "Green onion"));
        padThai.setInstructions("1. Soak noodles in warm water.\n2. Stir-fry protein, add egg.\n3. Add noodles, sauce mix, and toss.\n4. Serve with peanuts, lime, and sprouts.");
        padThai.setPrepTimeMinutes(20);
        padThai.setDifficulty("Medium");
        padThai.setTags(Arrays.asList("Savory", "Street Food", "Authentic"));

        Recipe mangoStickyRice = new Recipe("Mango Sticky Rice", "Popular Thai dessert with coconut and mango.", Arrays.asList("Thai", "Dessert", "Gluten-Free"), R.drawable.mango_sticky_rice, false);
        mangoStickyRice.setIngredients(Arrays.asList("1/2 cup sticky rice", "1/2 cup coconut milk", "2 tbsp sugar", "Pinch of salt", "1/2 ripe mango (sliced)", "Sesame seeds for garnish"));
        mangoStickyRice.setInstructions("1. Cook sticky rice until soft.\n2. Heat coconut milk, sugar, salt.\n3. Mix with rice and let absorb.\n4. Serve with mango and sesame.");
        mangoStickyRice.setPrepTimeMinutes(30);
        mangoStickyRice.setDifficulty("Easy");
        mangoStickyRice.setTags(Arrays.asList("Sweet", "Vegan", "Tropical"));

        Recipe papayaSalad = new Recipe("Som Tam (Papaya Salad)", "Tangy, spicy, and refreshing Thai salad.", Arrays.asList("Thai", "Salad", "Vegan"), R.drawable.som_tam, false);
        papayaSalad.setIngredients(Arrays.asList("1 cup shredded green papaya", "1 cherry tomato", "1 tbsp lime juice", "1 tsp soy sauce", "1 clove garlic", "1 chili", "1 tbsp peanuts", "1/2 tsp sugar"));
        papayaSalad.setInstructions("1. Crush garlic and chili in mortar.\n2. Add sugar, lime, soy sauce, mix.\n3. Add papaya, tomato, peanuts. Toss well.");
        papayaSalad.setPrepTimeMinutes(10);
        papayaSalad.setDifficulty("Easy");
        papayaSalad.setTags(Arrays.asList("Tangy", "Spicy", "Healthy"));




        // הוספת כל המתכונים
        recipes.addAll(Arrays.asList(
                pasta, pizza, greekSalad, caesar, risotto,
                lasagna, shakshuka, pancakes, hummus, padThai, mangoStickyRice, papayaSalad
        ));

        return recipes;
    }
}
