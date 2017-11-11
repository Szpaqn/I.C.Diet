package com.example.jakub.icdiet;

import android.provider.BaseColumns;

/**
 * Created by jakub on 22.09.17.
 */

public final class FoodTableContract {

    private FoodTableContract() {}

    public static class FoodEntrys implements BaseColumns {
        public static final String TABLE_FOOD_NAME = "food";
        public static final String COLUMN_NAME_ID = "rowid";
        public static final String COLUMN_NAME_FOOD_NAME = "food_name";  //nazwa produktu
        public static final String COLUMN_NAME_FOOD_HEALTHY_RANK = "food_rank";  //jak bardzo niezdrowe (na skali)
        public static final String COLUMN_NAME_FOOD_HISTAMINE_LEVEL = "food_histamine_level";  //ile histaminy

    }
}