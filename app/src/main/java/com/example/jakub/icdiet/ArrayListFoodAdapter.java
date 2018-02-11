package com.example.jakub.icdiet;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;

//import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
import android.widget.Filter;
//import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by jakub on 09.02.18.//    private Filter myFilter;
//    private boolean isFiltered;
 */

public class ArrayListFoodAdapter extends RealmBaseAdapter<Food> implements ListAdapter {

    private Filter myFilter;
    private boolean isFiltered;
    private boolean inDeletionMode = false;
    private Set<Integer> countersToDelete = new HashSet<>();

    public ArrayListFoodAdapter(OrderedRealmCollection<Food> realmResults) {
        super(realmResults);


    }

    void enableDeletionMode(boolean enabled) {
        inDeletionMode = enabled;
        if (!enabled) {
            countersToDelete.clear();
        }
        notifyDataSetChanged();
    }

    Set<Integer> getCountersToDelete() {
        return countersToDelete;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null ){
            Food food = (Food)getItem(position);
            if (food.getRating() == 0){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_items, parent, false);
            } else if(food.getRating() < 3){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_items1, parent, false);
            } else if (food.getRating() > 3){
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_items3, parent, false);
            } else {
                convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listview_items2, parent, false);
            }
            TextView foodName = (TextView) convertView.findViewById(R.id.name);
            TextView foodHistamine = (TextView) convertView.findViewById(R.id.food_histamine);
            // Populate the data into the template view using the data object
            foodName.setText(food.getName());
            foodHistamine.setText(String.valueOf(food.getHistamine_level()));
        }
        // Lookup view for data population

        // Return the completed view to render on screen
        return convertView;
    }
}
