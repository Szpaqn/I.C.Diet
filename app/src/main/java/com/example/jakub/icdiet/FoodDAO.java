package com.example.jakub.icdiet;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

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
     * Inserts one Food item to the database.
     * @param f
     */
    public void insertFood(Food f)
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

//    public Food getFood(String name)
//    {
//        return realm.where(Food.class).equalTo("name", name).findFirst();
//    }

    public RealmResults<Food> getAllFood()
    {
        return realm.where(Food.class).findAll();
    }

    public void updateFood(Food f){
        realm.beginTransaction();

        Food food = realm.where(Food.class).equalTo("id", f.getId()).findFirst();
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

    public void removeFood(Food food){
        realm.beginTransaction();

        RealmResults<Food> result = realm.where(Food.class).equalTo("id", food.getId()).findAll();
        result.deleteAllFromRealm();

        realm.commitTransaction();
    }

    private long generateId() {
        return (long) realm.where(Food.class).max("id") + 1;
    }

}
