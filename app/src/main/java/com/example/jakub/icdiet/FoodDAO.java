package com.example.jakub.icdiet;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmQuery;
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

        if(generateId() == 1){
            insertFood(new Food("Gruszka",1,5));
            insertFood(new Food("Japko",4,80));
            insertFood(new Food("Bamam",2,15));
            insertFood(new Food("Amanas",4,65));
            insertFood(new Food("Ślifka",4,72));
            insertFood(new Food("Vinko",5,96));
        }
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

    public Food getFood(String name)
    {
        return realm.where(Food.class).equalTo("name", name).findFirst();
    }

    public RealmResults<Food> getAllFood()
    {
        return realm.where(Food.class).findAll().sort("name");
    }

    public RealmResults<Food> getAllFoodWhere(String text)
    {
        int inted = -1;
        boolean canParseInt = true;
        try {
            inted = Integer.parseInt(text);

        } catch (NumberFormatException exept){
            canParseInt = false;
        }



        if(!text.isEmpty()){

            if(canParseInt && inted > 0)
            {
                RealmQuery<Food> query = realm.where(Food.class).contains("name", text).or().equalTo("histamine_level", inted).or().equalTo("rating", inted);
                Log.d("I.C.Diet", query.findFirst().getName() + String.valueOf(query.findFirst().getId()));
                return query.findAll();
            } else {
                RealmQuery<Food> query = realm.where(Food.class).contains("name", text);
                Log.d("I.C.Diet", query.findFirst().getName() + String.valueOf(query.findFirst().getId()));
                return  query.findAll();
            }

        }
        return realm.where(Food.class).findAll().sort("name");
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
        long id;
        try {
            id = (long) realm.where(Food.class).max("id") + 1;
        } catch (RuntimeException e) {
            id = 1;
        }
        return id;
    }

}
