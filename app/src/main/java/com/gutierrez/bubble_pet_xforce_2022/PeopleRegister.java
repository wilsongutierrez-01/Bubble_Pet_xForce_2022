package com.gutierrez.bubble_pet_xforce_2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class PeopleRegister extends AppCompatActivity {
Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_register);

        btn = findViewById(R.id.btnVolver);
        btn.setOnClickListener(v ->{
            Intent ok = new Intent(getApplicationContext(), Login.class);
            startActivity(ok);
        });

        btn = findViewById(R.id.btnSiguiente);
        btn.setOnClickListener(v ->{
            Intent go = new Intent(getApplicationContext(), Data_Pet.class);
            startActivity(go);
        });
    }
}