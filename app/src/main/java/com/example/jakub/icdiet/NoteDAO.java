package com.example.jakub.icdiet;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by jakub on 30.01.18.
 */

public class NoteDAO {

    private Realm realm;

    /**
     * NoteDAO constructor.
     * Constructs the NoteDAO and initialises the Real ORM.
     * @param context current context
     */
    public NoteDAO(Context context)
    {
        Realm.init(context);
        RealmConfiguration realmConfig = new RealmConfiguration.Builder().deleteRealmIfMigrationNeeded().build();
        Realm.setDefaultConfiguration(realmConfig);

        realm = Realm.getInstance(realmConfig);
    }

    /**
     * Inserts one foodNote item to the database.
     * @param foodNote
     */
    public void insertNote(FoodNote foodNote)
    {
        realm.beginTransaction();

        FoodNote note = realm.createObject(FoodNote.class, generateId());
        note.setDate(foodNote.getDate());
        note.setDateOfMeal(foodNote.getDateOfMeal());
        note.setListOfEatenFood(foodNote.getListOfEatenFood());
        note.setWellbeing(foodNote.getWellbeing());

        realm.commitTransaction();
    }

    public FoodNote getFoodNoteById(long id)
    {
        return realm.where(FoodNote.class).equalTo("id", id).findFirst();
    }

    public void updateFoodNote(FoodNote foodNote){
        FoodNote note = realm.where(FoodNote.class).equalTo("id", foodNote.getId()).findFirst();
        realm.beginTransaction();

        if(!foodNote.getDate().isEmpty()){
            note.setDate(foodNote.getDate());
        }
        if(!foodNote.getDateOfMeal().isEmpty()) {
            note.setDateOfMeal(foodNote.getDateOfMeal());
        }
        if(!foodNote.getListOfEatenFood().isEmpty()) {
            note.setListOfEatenFood(foodNote.getListOfEatenFood());
        }
        if(!(foodNote.getWellbeing() < 0) || !(foodNote.getWellbeing() > 10)) {
            note.setWellbeing(foodNote.getWellbeing());
        }

        realm.commitTransaction();
    }

    private long generateId() {
        return (realm.where(FoodNote.class).max("id") == null) ? 1 : ((long)realm.where(FoodNote.class).max("id") + 1);

    }

}
