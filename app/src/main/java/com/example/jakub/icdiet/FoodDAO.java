package com.example.jakub.icdiet;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by jakub on 07.02.18.
 */

public class FoodDAO {

    private Realm realm;

    /**
     * FoodDAO constructor.
     * Constructs the FoodDAO and initialises the Real ORM.
     * @param context current context
     */
    public FoodDAO(Context context)
    {
        Realm.init(context);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);

        realm = Realm.getInstance(realmConfig);
    }

    /**
     * Inserts one foodNote item to the database.
     * @param f
     */
    public void insertNote(Food f)
    {
        realm.beginTransaction();

        Food food = realm.createObject(Food.class, generateId());

        if(!f.getName().isEmpty()){
            food.setName(f.getName());
        }

        if(f.getHistamine_level() > -1){
            food.setHistamine_level(f.getHistamine_level());
        }

        if(f.getRating() > -1){
            food.setRating(f.getRating());
        }

        if(f.getAmount() > -1){
            food.setAmount(f.getAmount());
        }


        realm.commitTransaction();
    }

    public Food getFood(long id)
    {
        return realm.where(Food.class).equalTo("id", id).findFirst();
    }

    public void updateFoodNote(Food f){
        Food food = realm.where(Food.class).equalTo("id", f.getId()).findFirst();
        realm.beginTransaction();

        if(!f.getName().isEmpty()){
            food.setName(f.getName());
        }

        if(f.getHistamine_level() > -1){
            food.setHistamine_level(f.getHistamine_level());
        }

        if(f.getRating() > -1){
            food.setRating(f.getRating());
        }

        if(f.getAmount() > -1){
            food.setAmount(f.getAmount());
        }


        realm.commitTransaction();
    }

    private long generateId() {
        return (realm.where(FoodNote.class).max("id") == null) ? 1 : ((long)realm.where(FoodNote.class).max("id") + 1);

    }

}
