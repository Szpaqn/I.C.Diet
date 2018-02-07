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
     * Inserts one foodNote item to the database.
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

    private long generateId() {
        return (realm.where(FoodNote.class).max("id") == null) ? 1 : ((long)realm.where(FoodNote.class).max("id") + 1);

    }
}
