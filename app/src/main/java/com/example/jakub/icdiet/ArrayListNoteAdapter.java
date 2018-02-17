package com.example.jakub.icdiet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListAdapter;
import android.widget.TextView;

import io.realm.OrderedRealmCollection;
import io.realm.RealmBaseAdapter;
import io.realm.Sort;

/**
 * Created by jakub on 17.02.18.
 */

public class ArrayListNoteAdapter extends RealmBaseAdapter<FoodNote> implements ListAdapter /*, Filterable */ {

    private NoteDAO noteDAO;
    private OrderedRealmCollection<FoodNote> mResults;

    public ArrayListNoteAdapter(Context context, NoteDAO noteDAO)
    {
        super(noteDAO.getAllFoodNotes().sort("dateOfMeal", Sort.DESCENDING));
        this.noteDAO = noteDAO;
        mResults = noteDAO.getAllFoodNotes().sort("dateOfMeal", Sort.DESCENDING);

    }

    @Override
    public int getCount() {
        return mResults == null ? 0 : mResults.size();
    }

    @Override
    public FoodNote getItem(int position) {
        return mResults == null ? null : mResults.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FoodNote note = getItem(position);
        convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.notelist_item, parent, false);
        TextView noteDate = (TextView) convertView.findViewById(R.id.textViewDateTime);
        TextView noteType = (TextView) convertView.findViewById(R.id.textViewMealType);

        noteDate.setText(note.getDateOfMeal());
        noteType.setText(NoteDAO.getTypeOfMealStr(note.getTypeOfMeal()));

        return convertView;
    }




}
