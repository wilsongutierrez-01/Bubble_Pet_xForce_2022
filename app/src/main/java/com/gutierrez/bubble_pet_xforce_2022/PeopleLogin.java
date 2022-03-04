package com.gutierrez.bubble_pet_xforce_2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

public class PeopleLogin extends AppCompatActivity {
Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_login);

        btn = findViewById(R.id.btnBack);
        btn.setOnClickListener(v->{
            Intent back = new Intent(getApplicationContext(), Login.class);
            startActivity(back);
        });
    }
}