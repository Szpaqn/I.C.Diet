package com.example.jakub.icdiet;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import java.util.ArrayList;

import static com.example.jakub.icdiet.FoodTableContract.FoodEntrys.COLUMN_NAME_FOOD_HEALTHY_RANK;
import static com.example.jakub.icdiet.FoodTableContract.FoodEntrys.COLUMN_NAME_FOOD_HISTAMINE_LEVEL;
import static com.example.jakub.icdiet.FoodTableContract.FoodEntrys.COLUMN_NAME_FOOD_NAME;
import static com.example.jakub.icdiet.FoodTableContract.FoodEntrys.COLUMN_NAME_ID;
import static com.example.jakub.icdiet.FoodTableContract.FoodEntrys.TABLE_FOOD_NAME;

/**
 * Created by jakub on 18.09.17.
 */

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "icdiet.db";

    public SQLiteDatabase db;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        db = this.getWritableDatabase();

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_FOOD_NAME +
                " ( id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                COLUMN_NAME_FOOD_NAME + " TEXT, " +
                COLUMN_NAME_FOOD_HEALTHY_RANK + " INTEGER, " +
                COLUMN_NAME_FOOD_HISTAMINE_LEVEL + " INTEGER) "
        );
//        onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) { //na wypadek zmiany wersji bazy danych
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FOOD_NAME);  //usuń tabelę
        onCreate(sqLiteDatabase); //stwórz na nowo
    }

    public boolean insertFood(String name, int histamine, int rank) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_FOOD_NAME, name);
        contentValues.put(COLUMN_NAME_FOOD_HEALTHY_RANK, rank);
        contentValues.put(COLUMN_NAME_FOOD_HISTAMINE_LEVEL, histamine);
        long row = db.insert(TABLE_FOOD_NAME, null, contentValues);

        return row >= 0;
    }

    public boolean updateFood(int index,String name, int histamine, int rank) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_NAME_FOOD_NAME, name);
        contentValues.put(COLUMN_NAME_FOOD_HEALTHY_RANK, rank);
        contentValues.put(COLUMN_NAME_FOOD_HISTAMINE_LEVEL, histamine);
        String[] idValue = {String.valueOf(index)};
        long row = db.update(TABLE_FOOD_NAME, contentValues, "WHERE "+COLUMN_NAME_ID, idValue);

        return row >= 0;
    }

    public boolean removeFood(Food food) {
        String whereClause = COLUMN_NAME_FOOD_NAME + " = ? AND " + COLUMN_NAME_FOOD_HISTAMINE_LEVEL + " = ? AND " + COLUMN_NAME_FOOD_HEALTHY_RANK + " = ?";
        String[] args = { food.getName(), String.valueOf(food.getHistamine_level()), String.valueOf(food.getRating()) };
        long row = db.delete(TABLE_FOOD_NAME, whereClause, args);
        return row >= 0;
    }

    public ArrayList<Food> getAll() {
        ArrayList<Food> allFood = new ArrayList<>();
        Cursor resoult = db.rawQuery("SELECT * FROM " + TABLE_FOOD_NAME, null);
        while (resoult.moveToNext()) {
            try {
//                int idIndex = resoult.getColumnIndexOrThrow(COLUMN_NAME_ID);
                int nameIndex = resoult.getColumnIndexOrThrow(COLUMN_NAME_FOOD_NAME);
                int histamineIndex = resoult.getColumnIndexOrThrow(COLUMN_NAME_FOOD_HISTAMINE_LEVEL);
                int rankIndex = resoult.getColumnIndexOrThrow(COLUMN_NAME_FOOD_HEALTHY_RANK);
                allFood.add(new Food(resoult.getString(nameIndex), resoult.getInt(rankIndex), resoult.getInt(histamineIndex)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return allFood;
    }
}