package com.example.jakub.icdiet;

import android.content.Context;
import android.util.Log;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmList;
import io.realm.RealmQuery;
import io.realm.RealmResults;

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
        RealmList <Food> finalList = new RealmList<Food>();
        RealmResults<Food> result = realm.where(Food.class).findAll();
        finalList.addAll(result);
        if(generateId() == 1){
            insertNote(new FoodNote("2018-01-12 10:34:00", TypeOfMeal.breakfast, finalList, 32));
            insertNote(new FoodNote("2018-01-14 11:35:00", TypeOfMeal.lunch, finalList, 32));
            insertNote(new FoodNote("2018-01-15 12:36:00", TypeOfMeal.snack, finalList, 32));
            insertNote(new FoodNote("2018-01-16 14:37:00", TypeOfMeal.dinner, finalList, 32));
            insertNote(new FoodNote("2018-01-17 15:38:00", TypeOfMeal.snack, finalList, 32));
            insertNote(new FoodNote("2018-01-11 16:39:00", TypeOfMeal.supper, finalList, 32));
        }
    }

    /**
     * Inserts one foodNote item to the database.
     * @param foodNote
     */
    public void insertNote(FoodNote foodNote)
    {
        realm.beginTransaction();
        FoodNote note = realm.createObject(FoodNote.class, generateId());
        note.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime()));
        note.setDateOfMeal(foodNote.getDateOfMeal());
        note.setTypeOfMeal(foodNote.getTypeOfMeal());
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

    public RealmResults<FoodNote> getAllFoodNotes()
    {
        return realm.where(FoodNote.class).findAll().sort("date");
    }

    public void removeFoodNote(FoodNote foodNote){
        realm.beginTransaction();

        RealmResults<FoodNote> result = realm.where(FoodNote.class).equalTo("id", foodNote.getId()).findAll();
        result.deleteAllFromRealm();

        realm.commitTransaction();
    }

    private long generateId() {
        long id;
        try {
            id = (long) realm.where(FoodNote.class).max("id") + 1;
        } catch (RuntimeException e) {
            id = 1;
        }
        return id;

    }

    public static String getTypeOfMealStr(TypeOfMeal type)
    {
        switch (type){

            case breakfast:
                return "breakfast";

            case lunch:
                return "lunch";

            case dinner:
                return "dinner";

            case snack:
                return "snack";

            case supper:
                return "supper";

            default:
                return "breakfast";

        }
    }
}
