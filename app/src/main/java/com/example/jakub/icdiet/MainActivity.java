package com.example.jakub.icdiet;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnRecipes;
    private Button btnNotes;
    private Button btnFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnRecipes = (Button)findViewById(R.id.buttonRecipes);
        btnNotes = (Button)findViewById(R.id.buttonNotes);
        btnFood = (Button)findViewById(R.id.buttonFood);

        btnRecipes.setVisibility(View.GONE);
        btnRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentEdit = new Intent("android.intent.action.ListFood");
                startActivity(intentEdit);
            }
        });

        btnNotes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentEdit = new Intent("android.intent.action.ListNotes");
                startActivity(intentEdit);
            }
        });

        btnFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentEdit = new Intent("android.intent.action.ListFood");
                startActivity(intentEdit);
            }
        });

    }
}
