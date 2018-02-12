package com.example.jakub.icdiet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class EditFoodActivity extends AppCompatActivity {

    private Button btnEdit;
    private Button btnRemove;
    private EditText editTextFoodName;
    private EditText editTextFoodHistamine;
    private Spinner spinnerRating;
    private String[] arrayForSpinner;
//    private DatabaseHelper dbHelper;
    private FoodDAO foodDAO;

    private Food tempFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

//        dbHelper = new DatabaseHelper(this);
        foodDAO = new FoodDAO(this);

        btnEdit = (Button)findViewById(R.id.buttonEdit);
        btnRemove = (Button)findViewById(R.id.buttonRemove);
        editTextFoodName = (EditText)findViewById(R.id.editTextFoodName);
        editTextFoodHistamine = (EditText)findViewById(R.id.editTextFoodHistamine);
        spinnerRating = (Spinner)findViewById(R.id.spinnerRating);

        this.arrayForSpinner = new String[] {
                "Unknown", "Healthy", "Risky", "Unhealthy"
        };

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, arrayForSpinner);

        spinnerRating.setAdapter(adapter);

        Intent intent = getIntent();
        int requestCode = intent.getIntExtra("requestCode", 0);
        if( requestCode == 1)  //Add new
        {
            tempFood = null;
            btnEdit.setText("Add");
            spinnerRating.setSelection(0, true);
        }
        else if (requestCode == 2 ) //Edit
        {
            long foodId = intent.getLongExtra("food_id", 0);
            if(foodId == 0){
                //TODO show info, that you need a name
                tempFood = null;
            } else {
                tempFood = foodDAO.getFood(foodId);

                btnEdit.setText("Edit");
                editTextFoodName.setText(tempFood.getName());
                editTextFoodHistamine.setText(String.valueOf(tempFood.getHistamine_level()));
                switch (tempFood.getRating())
                {
                    case 0:
                        spinnerRating.setSelection(0, true);
                        break;
                    case 1:
                    case 2:
                        spinnerRating.setSelection(1, true);
                        break;
                    case 3:
                        spinnerRating.setSelection(2, true);
                        break;
                    case 4:
                    case 5:
                        spinnerRating.setSelection(3, true);
                        break;
                    default:
                        spinnerRating.setSelection(0,true);
                }
            }
        }

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBtnEditClicked();
            }
        });
        btnRemove.setOnClickListener( new View.OnClickListener() {
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
            addFood();
        }
        else if (requestCode == 2 ) //Edit
        {
            //adapter - update
            if(tempFood != null){
                updateFood(tempFood);
            }

        }

        //TODO send info to MainActivity, to update the listView
    }

    private void addFood() {
        if(editTextFoodName.getText().toString().isEmpty()){
            //TODO show info, that you need a name
        } else {
        int foodHistamineLevel = 0;
            if (!editTextFoodHistamine.getText().toString().isEmpty()) {
                foodHistamineLevel = Integer.parseInt(editTextFoodHistamine.getText().toString());
            }
//            dbHelper.insertFood(editTextFoodName.getText().toString(), foodHistamineLevel, getIntFromSpinnerRating());
            foodDAO.insertFood(new Food(editTextFoodName.getText().toString(), getIntFromSpinnerRating(), foodHistamineLevel));
        }
    }

    private void updateFood(Food food) {
        String name = editTextFoodName.getText().toString();
        if(name.isEmpty()){
            //TODO show info, that you need a name
        } else {
            food.setName(name);

            if (!editTextFoodHistamine.getText().toString().isEmpty()) {
                food.setHistamine_level(Integer.parseInt(editTextFoodHistamine.getText().toString()));
            }

            food.setRating(getIntFromSpinnerRating());

            foodDAO.updateFood(food);
        }
    }

    private void onRemove()
    {
        if(tempFood != null){
            foodDAO.removeFood(tempFood);
        }
    }

    private int getIntFromSpinnerRating(){
        String rating = spinnerRating.getSelectedItem().toString();
        if(rating.startsWith("Hea")){
            return 1;
        } else if(rating.startsWith("Risky")){
            return 3;
        } else if(rating.startsWith("Unh")){
            return 5;
        } else {  //Unknown
            return 0;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        //send intent return info

    }

}
