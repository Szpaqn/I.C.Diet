package com.example.jakub.icdiet;

public class Food {

    private String food_name;  //nazwa produktu
    private int food_rating;  //jak bardzo niezdrowe (na skali)
    private int food_histamine_level;  //ile histaminy

    public Food(String food_name, int food_rating, int food_histamine_level) {
        this.food_name = food_name;
        this.food_rating = food_rating;
        this.food_histamine_level = food_histamine_level;
    }

    public String getFood_name() {
        return food_name;
    }

    public void setFood_name(String food_name) {
        this.food_name = food_name;
    }

    public int getFood_rating() {
        return food_rating;
    }

    public void setFood_rating(int food_rating) {
        this.food_rating = food_rating;
    }

    public int getFood_histamine_level() {
        return food_histamine_level;
    }

    public void setFood_histamine_level(int food_histamine_level) {
        this.food_histamine_level = food_histamine_level;
    }
}