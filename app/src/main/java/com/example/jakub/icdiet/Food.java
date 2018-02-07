package com.example.jakub.icdiet;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class Food extends RealmObject {
//public class Food {


    @PrimaryKey
    private long id;

    /**
     * Name of the food.
     */
    private String name;  //nazwa produktu
    /**
     * The foods rating (on a scale, how healthy it is).
     */
    private int rating;  //jak bardzo niezdrowe (na skali)
    /**
     * The foods histamine_level.
     */
    private int histamine_level;  //ile histaminy

    /**
     * How much of the food was used.
     * It's only needed when adding food to a FoodDish or a FoodNote.
     */
    private int amount;

    public Food() {
        rating = -1;
        histamine_level = -1;
        amount = -1;
    }

    /**
     * Parametrised contructor for the class Food.
      * @param food_name Foods name.
     * @param food_rating Foods rating.
     * @param food_histamine_level Foods histamine level.
     */
    public Food(String food_name, int food_rating, int food_histamine_level) {
        this.name = food_name;
        this.rating = food_rating;
        this.histamine_level = food_histamine_level;
        this.amount = -1;
    }

    public Food(String food_name, int food_rating, int food_histamine_level, int amount) {
        this.name = food_name;
        this.rating = food_rating;
        this.histamine_level = food_histamine_level;
        this.amount = amount;
    }

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

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getHistamine_level() {
        return histamine_level;
    }

    public void setHistamine_level(int histamine_level) {
        this.histamine_level = histamine_level;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}