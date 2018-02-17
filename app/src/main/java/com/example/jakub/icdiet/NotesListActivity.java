package com.example.jakub.icdiet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class NotesListActivity extends AppCompatActivity {

    private Button btnAdd;
    private ListView listView;

    private ArrayListNoteAdapter adapter;

    private NoteDAO noteDAO;

    private static final int addNewIntentValue = 1;
    private static final int editIntentValue = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        btnAdd = (Button) findViewById(R.id.btnAddNote);
//        btnRemove = (Button)findViewById(R.id.btnRemove);
        listView = (ListView) findViewById(R.id.listViewNotes);

        noteDAO = new NoteDAO(this);

        adapter = new ArrayListNoteAdapter(this, noteDAO);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FoodNote foodNote = (FoodNote) listView.getItemAtPosition(i);
                String itemValue = foodNote.getDate() + " " + String.valueOf(NoteDAO.getTypeOfMealStr(foodNote.getTypeOfMeal()));
                Toast.makeText(NotesListActivity.this, itemValue, Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                onEdit(i);
                return true;
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAdd();
            }
        });
    }

    private void onAdd()
    {
//        dbHelper.insertNote("Jakieś",1,1);
//        adapter.updateArrayList(dbHelper.getAll());
//        adapter.notifyDataSetChanged();
        Intent intentEdit = new Intent("android.intent.action.EditNotes");
//        intentEdit.putExtra("food_name", textView.getText().toString());
        startActivityForResult(intentEdit, addNewIntentValue);
    }

    private void onEdit(int i)
    {
        if(adapter.getCount() == 0) return;
        FoodNote foodNote = adapter.getItem(i);

        if(foodNote != null){

            Intent intentEdit = new Intent("android.intent.action.EditNotes");
            intentEdit.putExtra("food_id",foodNote.getId());
//        intentEdit.putExtra("food_name",food.getName());
//        intentEdit.putExtra("food_histamine",food.getHistamine_level());
//        intentEdit.putExtra("food_rating",food.getRating());

            startActivityForResult(intentEdit, editIntentValue);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == addNewIntentValue) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here
            }
        } else if (requestCode == editIntentValue) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here
            }
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        intent.putExtra("requestCode", requestCode);
        super.startActivityForResult(intent, requestCode);
    }
}
