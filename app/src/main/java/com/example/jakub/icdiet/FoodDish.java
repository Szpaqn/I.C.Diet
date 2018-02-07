package com.example.jakub.icdiet;

import com.example.jakub.icdiet.Food;

import java.util.HashMap;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by jakub on 07.02.18.
 */

public class FoodDish extends RealmObject {

    @PrimaryKey
    private long id;

    /**
     * Name of the dish.
     */
    private String name;

    /**
     * Ingredients used in the dish.
     */
    private RealmList<Food> ingredients;

    private String recipe;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public RealmList<Food> getIngredients() {
        return ingredients;
    }

    public void setIngredients(RealmList<Food> ingredients) {
        this.ingredients = ingredients;
    }

    public String getRecipe() {
        return recipe;
    }

    public void setRecipe(String recipe) {
        this.recipe = recipe;
    }
}
