package com.example.jakub.icdiet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;

import io.realm.RealmList;

public class EditNoteActivity extends AppCompatActivity {

    private Button btnEdit;
    private Button btnRemove;
    private EditText editTextDate;
//    private EditText editTextTime;
    private Spinner spinnerMealType;
    private MultiAutoCompleteTextView textViewIngredients;
    private EditText editTextWellbeing;
    private String[] arrayForSpinner;

    private NoteDAO noteDAO;

    private FoodDAO foodDAO;

    private FoodNote tempNote;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        noteDAO = new NoteDAO(this);
        foodDAO = new FoodDAO(this);
        tempNote = new FoodNote();
        btnEdit = (Button) findViewById(R.id.buttonEditNote);
        btnRemove = (Button) findViewById(R.id.buttonDeleteNote);
        editTextDate = (EditText) findViewById(R.id.editTextDateTime);
//        editTextTime = (EditText)findViewById(R.id.editTextTime);
        spinnerMealType = (Spinner) findViewById(R.id.spinnerMealType);
        textViewIngredients = (MultiAutoCompleteTextView) findViewById(R.id.multiAutoCompleteTextViewIngredients);
        editTextWellbeing = (EditText) findViewById(R.id.editTextWellbeing);

        this.arrayForSpinner = new String[]{
                "Snack", "Breakfast", "Lunch", "Dinner", "Supper"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayForSpinner);

        spinnerMealType.setAdapter(adapter);

        Intent intent = getIntent();
        int requestCode = intent.getIntExtra("requestCode", 0);
        if (requestCode == 1)  //Add new
        {
            tempNote = null;
            btnEdit.setText("Add");
            spinnerMealType.setSelection(0, true);
        } else if (requestCode == 2) //Edit
        {
            long noteId = intent.getLongExtra("food_id", 0);
            if (noteId == 0) {
                Toast.makeText(EditNoteActivity.this, "No id provided", Toast.LENGTH_SHORT).show();
                tempNote = null;
            } else {
                FoodNote foodNote = noteDAO.getFoodNoteById(noteId);

                btnEdit.setText("Edit");

                editTextDate.setText(foodNote.getDateOfMeal());
                switch (foodNote.getTypeOfMeal()) {
                    case breakfast:
                        spinnerMealType.setSelection(0, true);
                        break;
                    case lunch:
                        spinnerMealType.setSelection(1, true);
                        break;
                    case dinner:
                        spinnerMealType.setSelection(2, true);
                        break;
                    case snack:
                        spinnerMealType.setSelection(3, true);
                        break;
                    case supper:
                        spinnerMealType.setSelection(4, true);
                        break;
                    default:
                        spinnerMealType.setSelection(0, true);
                }
                String listOfIngredients = new String();
                List<Food> list = foodNote.getListOfEatenFood();
                boolean first = true;
                for (Food s : list) {
                    if(first){
                        listOfIngredients = s.toString();
                        first = false;
                    } else {
                        listOfIngredients = listOfIngredients + ", " + s.toString();
                    }
                }
                textViewIngredients.setText(listOfIngredients);
                editTextWellbeing.setText(String.valueOf(foodNote.getWellbeing()));

                tempNote.setId(foodNote.getId());
                tempNote.setTypeOfMeal(foodNote.getTypeOfMeal());
                tempNote.setDate(foodNote.getDate());
                tempNote.setTypeOfMeal(foodNote.getTypeOfMeal());
                tempNote.setListOfEatenFood(foodNote.getListOfEatenFood());
                tempNote.setWellbeing(foodNote.getWellbeing());
            }
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnEditClicked();
            }
        });
        btnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onRemove();
            }
        });
    }

        private void onBtnEditClicked()
        {
            Intent intent = getIntent();
            int requestCode = intent.getIntExtra("requestCode", 0);
            if( requestCode == 1)  //Add new
            {
                //adapter - add
                addNote();
            }
            else if (requestCode == 2 ) //Edit
            {
                //adapter - update
                if(tempNote != null){
                    updateNote(tempNote);
                }

            }
            //TODO send info to MainActivity, to update the listView
        }

    private void addNote() {
        String dateOfMeal = editTextDate.getText().toString();
        if(dateOfMeal.isEmpty()){
            Toast.makeText(EditNoteActivity.this, "Failed to add. No name provided", Toast.LENGTH_LONG).show();
        } else {
            if(tempNote == null){
                tempNote = new FoodNote();
            }

            tempNote.setDateOfMeal(dateOfMeal);

            List<String> list = Arrays.asList(textViewIngredients.getText().toString().split("\\s*,\\s*"));
            RealmList<Food> listOfFood = new RealmList<>();
            for(String s : list)
            {
                Food food = foodDAO.getFood(s);

                if(food != null){
                    listOfFood.add(food);
                }
            }
            tempNote.setListOfEatenFood(listOfFood);

            tempNote.setTypeOfMeal(getCurrentTypeOfMeal());

            String wellbeing = editTextWellbeing.getText().toString();
            if (!wellbeing.isEmpty()) {
                tempNote.setWellbeing(Integer.parseInt(wellbeing));
            }
            String str = "Added new note " + dateOfMeal;
            noteDAO.insertNote(tempNote);
            Toast.makeText(EditNoteActivity.this, str , Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void updateNote(FoodNote foodNote) {
        String dateOfMeal = editTextDate.getText().toString();
        String str = "Updated note " + foodNote.getDate();
        if(dateOfMeal.isEmpty()){
            Toast.makeText(EditNoteActivity.this, "Failed to Update. No dateOfMeal provided", Toast.LENGTH_LONG).show();
        } else {
            tempNote.setDateOfMeal(dateOfMeal);


            List<String> list = Arrays.asList(textViewIngredients.getText().toString().split("\\s*,\\s*"));
            RealmList<Food> listOfFood = new RealmList<>();
            for(String s : list)
            {
                Food food = foodDAO.getFood(s);

                if(food != null){
                    listOfFood.add(food);
                }
            }

            tempNote.setTypeOfMeal(getCurrentTypeOfMeal());

            tempNote.setListOfEatenFood(listOfFood);

            String wellbeing = editTextWellbeing.getText().toString();
            if (!wellbeing.isEmpty()) {
                tempNote.setWellbeing(Integer.parseInt(wellbeing));
            }

            noteDAO.updateFoodNote(tempNote);
            Toast.makeText(EditNoteActivity.this, str, Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private void onRemove() {
        if (tempNote != null) {
            String str = "Removed food " + tempNote.getDate();
            noteDAO.removeFoodNote(tempNote);
            Toast.makeText(EditNoteActivity.this, str, Toast.LENGTH_SHORT).show();
        }
        finish();
    }

    private TypeOfMeal getCurrentTypeOfMeal()
    {
        switch (spinnerMealType.getSelectedItemPosition()) {
            case 0:
                return TypeOfMeal.breakfast;
            case 1:
                return TypeOfMeal.lunch;
            case 2:
                return TypeOfMeal.dinner;
            case 3:
                return TypeOfMeal.snack;
            case 4:
                return TypeOfMeal.supper;
            default:
                return TypeOfMeal.breakfast;
        }
    }

//"Snack", "Breakfast", "Lunch", "Dinner", "Supper"
    private int getIntFromSpinnerTypeOfMeal(){
        String rating = spinnerMealType.getSelectedItem().toString();
        if(rating.startsWith("Breakfast")){
            return 0;
        } else if(rating.startsWith("Lunch")){
            return 1;
        } else if(rating.startsWith("Dinner")){
            return 2;
        } else if(rating.startsWith("Snack")){
            return 3;
        } else if(rating.startsWith("Supper")){
            return 4;
        } else {  //Unknown
            return 0;
        }
    }

    
}
