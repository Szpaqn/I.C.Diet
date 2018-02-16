//package com.example.jakub.icdiet;
//
//import android.content.Context;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.Filter;
//import android.widget.Filterable;
//import android.widget.TextView;
//
//import java.util.ArrayList;
//
///**
// * Created by jakub on 15.10.17.
// */
//
//public class ListViewFoodAdapter extends ArrayAdapter<Food> implements Filterable {
//
//    private ArrayList<Food> mOriginalValues; // Original Values
//    private ArrayList<Food> mDisplayedValues;    // Values to be displayed
//    private Filter myFilter;
//    private boolean isFiltered;
//
//
//    public ListViewFoodAdapter(Context context, ArrayList<Food> foods) {
//        super(context, 0, foods);
//        this.mOriginalValues = foods;
//        this.mDisplayedValues = foods;
//
//
//        isFiltered = false;
//
//        myFilter = new Filter() {
//            private FilterResults previousResults = null;
//            private FilterResults results = null;
//            @Override
//            protected FilterResults performFiltering(CharSequence constraint) {
//                if(results == null) results = new FilterResults();        // Holds the results of a filtering operation in values
//
//                ArrayList<Food> FilteredArrList = new ArrayList<>();
//
//
//                if (mOriginalValues == null) {
//                    mOriginalValues = new ArrayList<Food>(mDisplayedValues); // saves the original data in mOriginalValues
//                }
//
//                /********
//                 *
//                 *  If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
//                 *  else does the Filtering and returns FilteredArrList(Filtered)
//                 *
//                 ********/
//                if (constraint == null || constraint.length() == 0) {
//
//                    // set the Original result to return
//                    results.count = mOriginalValues.size();
//                    results.values = mOriginalValues;
//                } else {
//                    constraint = constraint.toString().toLowerCase();
//                    for (int i = 0; i < mOriginalValues.size(); i++) {
//                        String data = mOriginalValues.get(i).getName();
//                        if (data.toLowerCase().startsWith(constraint.toString())) {
//                            FilteredArrList.add(new Food(mOriginalValues.get(i).getName(), mOriginalValues.get(i).getRating(), mOriginalValues.get(i).getHistamine_level()));
//                        }
//                    }
//                    // set the Filtered result to return
//                    results.count = FilteredArrList.size();
//                    results.values = FilteredArrList;
//                    if(previousResults == null){
////                        3previousResults = results;
//                    } else if(previousResults.values.equals(results.values)) {
//                        previousResults = results;
//                        isFiltered = false;
//                        return  previousResults;
//                    }
//
//                }
//
//                isFiltered = true;
//                return results;
//            }
//
//            @SuppressWarnings("unchecked")
//            @Override
//            protected void publishResults(CharSequence constraint, FilterResults results) {
//
//                mDisplayedValues = (ArrayList<Food>) results.values; // has the filtered values
//
////                Food item = (Food)mDisplayedValues.get(0);
//                notifyDataSetChanged();  // notifies the data with new filtered values
//            }
//        };
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//        // Get the data item for this position
//        // Check if an existing view is being reused, otherwise inflate the view
//        if (convertView == null || isFiltered){
//            Food food = (Food)getItem(position);
//            if (food.getRating() == 0){
//                convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_items, parent, false);
//            } else if(food.getRating() < 3){
//                convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_items1, parent, false);
//            } else if (food.getRating() > 3){
//                convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_items3, parent, false);
//            } else {
//                convertView = LayoutInflater.from(getContext()).inflate(R.layout.listview_items2, parent, false);
//            }
//            TextView foodName = (TextView) convertView.findViewById(R.id.name);
//            TextView foodHistamine = (TextView) convertView.findViewById(R.id.food_histamine);
//            // Populate the data into the template view using the data object
//            foodName.setText(food.getName());
//            foodHistamine.setText(String.valueOf(food.getHistamine_level()));
//        }
//        // Lookup view for data population
//
//        // Return the completed view to render on screen
//        return convertView;
//    }
//
//    public void updateArrayList(ArrayList<Food> foods) {
//        this.mOriginalValues = foods;
//        this.mDisplayedValues = foods;
//    }
//
//    @Override
//    public int getCount() {
//        return mDisplayedValues.size();
//    }
//
//    @Override
//    public Food getItem(int position) {
//        return mDisplayedValues.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    @Override
//    public Filter getFilter() {
//        return myFilter;
//    }
//}