package com.example.jakub.icdiet;

import io.realm.Case;
import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;
import io.realm.RealmQuery;
import io.realm.RealmResults;

//import android.content.Context;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Filter;
//import android.widget.Filterable;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by jakub on 09.02.18.//    private Filter myFilter;
//    private boolean isFiltered;
 */

public class ArrayListFoodAdapter extends RealmBaseAdapter<Food> implements ListAdapter, Filterable {

    private FoodDAO foodDAO;
    private OrderedRealmCollection<Food> mResults;
//    private Filter myFilter;
//    private boolean isFiltered;
//    private boolean inDeletionMode = false;
//    private Set<Integer> countersToDelete = new HashSet<>();

    public ArrayListFoodAdapter(Context context, FoodDAO foodDAO, AutoCompleteTextView refAutocomplete) {
        super(foodDAO.getAllFood());
        this.foodDAO = foodDAO;
        mResults = foodDAO.getAllFood();
//        refAutocomplete.setAdapter(this);
    }

    @Override
    public int getCount() {
        return mResults == null ? 0 : mResults.size();
    }

    @Override
    public Food getItem(int position) {
        return mResults == null ? null : mResults.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        // Check if an existing view is being reused, otherwise inflate the view
//        if (convertView == null ){
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
//        }
        // Lookup view for data population

        // Return the completed view to render on screen
        return convertView;
    }

    public boolean filterResoults(CharSequence constraint){
        if (constraint != null) {
            String text = constraint.toString();
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
                    RealmQuery<Food> query = foodDAO.getAllFood().where().contains("name", text, Case.INSENSITIVE).or().equalTo("histamine_level", inted).or().equalTo("rating", inted);
                    if(query.findFirst() != null){
                        Log.d("I.C.Diet", query.findFirst().getName() + String.valueOf(query.findFirst().getId()));
                    }
                    mResults = query.findAll();
                } else {
                    RealmQuery<Food> query = foodDAO.getAllFood().where().contains("name", text, Case.INSENSITIVE);
                    if(query.findFirst() != null){
                        Log.d("I.C.Diet", query.findFirst().getName() + String.valueOf(query.findFirst().getId()));
                    }
                    mResults = query.findAll();
                }

            } else {

                mResults = foodDAO.getAllFood();
            }
        } else {
            mResults = foodDAO.getAllFood();
        }
        Log.d("I.C.Diet", String.valueOf(mResults));
        this.updateData(mResults);
        notifyDataSetChanged();
        return mResults.size() > 0;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {

            private boolean mHasResults = false;

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults results = new FilterResults();
                results.count = mHasResults ? 1 : 0; // AutoCompleteTextView already hides dropdown here if count is 0, so correct it.
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mHasResults = filterResoults(constraint);
            }
        };
    }
}
