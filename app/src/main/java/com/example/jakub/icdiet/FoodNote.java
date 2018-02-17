package com.example.jakub.icdiet;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.realm.Realm;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class FoodNote extends RealmObject {

    @PrimaryKey
    private long id;

    /**
     * Date of the note
     */
    private String date;

    /**
     * Date of the last meal (that probobly will be described in the note).
     */
    private String dateOfMeal;
    /**
     * List of food that is mentioned in the note.
     */
    private RealmList<Food> listOfEatenFood;

    /**
     * From 0 to 10. The wellbeing of the user, when he's writing this.
     */
    private int wellbeing;

    /**
     * Type of meal that is being described.
     */
//    private TypeOfMeal typeOfMeal;

    private String enumDescription;


    public TypeOfMeal getTypeOfMeal() {
        return TypeOfMeal.valueOf(enumDescription);
    }

    public void setTypeOfMeal(TypeOfMeal type)
    {
        this.enumDescription = type.toString();
    }

    public FoodNote()
    {
        wellbeing = -1;
    }

    public FoodNote(String dateTime, TypeOfMeal mealType, RealmList<Food> listOfFood, int wellbeing)
    {
        this.dateOfMeal = dateTime;
        this.enumDescription = getTypeOfMealStr(mealType);
        this.listOfEatenFood = listOfFood;
        this.wellbeing = wellbeing;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDateOfMeal() {
        return dateOfMeal;
    }

    public void setDateOfMeal(String dateOfMeal) {
        this.dateOfMeal = dateOfMeal;
    }

    public RealmList<Food> getListOfEatenFood() {
        return listOfEatenFood;
    }

    public void setListOfEatenFood(RealmList<Food> listOfEatenFood) {
        this.listOfEatenFood = listOfEatenFood;
    }

    public int getWellbeing() {
        return wellbeing;
    }

    public void setWellbeing(int wellbeing) {
        this.wellbeing = wellbeing;
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
