
package com.example.jakub.icdiet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button btnAdd;
    //    private Button btnRemove;
    private TextView textView;
    private ListView listView;
    //    private ArrayList<String> list;
//    private ArrayAdapter<Food> adapter;
//    private FoodViewAdapter adapter;
    private ListViewFoodAdapter adapter;
    private DatabaseHelper dbHelper;

    private static final int addNewIntentValue = 1;
    private static final int editIntentValue = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = (Button)findViewById(R.id.btnAdd);
//        btnRemove = (Button)findViewById(R.id.btnRemove);
        listView = (ListView)findViewById(R.id.lstItems);
        textView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView);
        textView.setSingleLine(true);

//        list = new ArrayList<String>();
//        for(int i = 0; i < 20; i++)
//        {
//            list.add("Item " + i);

        dbHelper = new DatabaseHelper(this);

//        dbHelper.insertFood("Gruszka",1,5);
//        dbHelper.insertFood("Japko",4,80);
//        dbHelper.insertFood("Bamam",2,15);
//        dbHelper.insertFood("Amanas",4,65);
//        dbHelper.insertFood("Ślifka",4,72);
//        dbHelper.insertFood("Vinko",5,96);


//        Cursor cursor = db.rawQuery("SELECT " + COLUMN_NAME_FOOD_NAME + " , "+ COLUMN_NAME_FOOD_HISTAMINE_LEVEL + " FROM "+ TABLE_FOOD_NAME, null);

//        adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, list);
//        adapter = new ArrayAdapter<Food>(this,android.R.layout.simple_list_item_1, android.R.id.text1, dbHelper.getAll());
        adapter = new ListViewFoodAdapter(this, dbHelper.getAll());

//        adapter.setNotifyOnChange(true);



//        adapter = new FoodViewAdapter(this, cursor);

        listView.setAdapter(adapter);

        textView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s.toString());
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
//                AutoCompleteTextView tv = (AutoCompleteTextView)s;
//                adapter.getFilter().filter(tv.getText());
//                adapter.getFilter().filter(textView.getText());
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Food food = (Food) listView.getItemAtPosition(i);
                String itemValue = food.getFood_name() + " " + String.valueOf(food.getFood_histamine_level());
                Toast.makeText(MainActivity.this, itemValue, Toast.LENGTH_SHORT).show();
            }
        });

        listView.setOnItemLongClickListener( new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                onEdit(i);
                return true;
            }
        });
//
//        btnRemove.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                onRemove();
//            }
//        });
//
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onAdd();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();


    }

    private void onAdd()
    {
//        dbHelper.insertFood("Jakieś",1,1);
//        adapter.updateArrayList(dbHelper.getAll());
//        adapter.notifyDataSetChanged();
        Intent intentEdit = new Intent("android.intent.action.EditFood");
        startActivityForResult(intentEdit, addNewIntentValue);
    }
//
//    private void onRemove(int i)
//    {
//        if(adapter.getCount() == 0) return;
//        Food food = adapter.getItem(i);
//        dbHelper.removeFood(food);android spinner set
//        adapter.updateArrayList(dbHelper.getAll());
//        adapter.notifyDataSetChanged();
//    }

    private void onEdit(int i)
    {
        if(adapter.getCount() == 0) return;
        Food food = adapter.getItem(i);

        Intent intentEdit = new Intent("android.intent.action.EditFood");
        intentEdit.putExtra("food_name",food.getFood_name());
        intentEdit.putExtra("food_histamine",food.getFood_histamine_level());
        intentEdit.putExtra("food_rating",food.getFood_rating());

        startActivityForResult(intentEdit, editIntentValue);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request we're responding to
        if (requestCode == addNewIntentValue) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
            }
        } else if (requestCode == editIntentValue) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // The user picked a contact.
                // The Intent's data Uri identifies which contact was selected.

                // Do something with the contact here (bigger example below)
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
