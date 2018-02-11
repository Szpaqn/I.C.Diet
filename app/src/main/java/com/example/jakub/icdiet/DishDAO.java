package com.example.jakub.icdiet;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by jakub on 07.02.18.
 */

public class DishDAO {
    private Realm realm;

    /**
     * DishDAO constructor.
     * Constructs the DishDAO and initialises the Real ORM.
     * @param context current context
     */
    public DishDAO(Context context)
    {
        Realm.init(context);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);
        realm = Realm.getInstance(realmConfig);
    }

    /**
     * Inserts one foodDish item to the database.
     * @param foodDish
     */
    public void insertDish(FoodDish foodDish)
    {
        realm.beginTransaction();

        foodDish.setId(generateId());
        FoodDish dish = realm.createObject(FoodDish.class, foodDish.getId());
        dish.setName(foodDish.getName());
        dish.setIngredients(foodDish.getIngredients());
        dish.setRecipe(foodDish.getRecipe());
        realm.commitTransaction();
    }

    public FoodDish getFoodDishById(long id)
    {
        return realm.where(FoodDish.class).equalTo("id", id).findFirst();
    }

    public void updateFoodDish(FoodDish foodDish){
        FoodDish note = realm.where(FoodDish.class).equalTo("id", foodDish.getId()).findFirst();
        realm.beginTransaction();

        if(!foodDish.getName().isEmpty()){
            note.setName(foodDish.getName());
        }
        if(!foodDish.getRecipe().isEmpty()) {
            note.setRecipe(foodDish.getRecipe());
        }
        if(!foodDish.getIngredients().isEmpty()) {
            note.setIngredients(foodDish.getIngredients());
        }

        realm.commitTransaction();
    }

    private long generateId() {
        return (realm.where(FoodDish.class).max("id") == null) ? 1 : ((long)realm.where(FoodDish.class).max("id") + 1);

    }
}
