package com.gutierrez.bubble_pet_xforce_2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class Data_Pet extends AppCompatActivity {
Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pet);

        btn = findViewById(R.id.btnVolverDogPerfil);
        btn.setOnClickListener(v->{
            Intent back = new Intent(getApplicationContext(), PeopleRegister.class);
            startActivity(back);
        });
    }
}