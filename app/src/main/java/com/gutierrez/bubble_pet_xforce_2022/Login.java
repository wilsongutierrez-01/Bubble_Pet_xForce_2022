package com.gutierrez.bubble_pet_xforce_2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;

public class Login extends AppCompatActivity {
Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        buttonStyle();

        btn = findViewById(R.id.btnSingUp);
        btn.setOnClickListener(v->{
            Intent re = new Intent(getApplicationContext(), PeopleRegister.class);
            startActivity(re);
        });

        btn = findViewById(R.id.btnSingIn);
        btn.setOnClickListener(v->{
            Intent lo = new Intent(getApplicationContext(),PeopleLogin.class);
            startActivity(lo);
        });
    }

    private void buttonStyle (){
        Button btn = findViewById(R.id.btnSingIn);
        btn.setBackgroundColor(Color.parseColor("#eeeeee"));
        btn.setTextColor(Color.parseColor("#212121"));

        btn = findViewById(R.id.btnSingUp);
        btn.setBackgroundColor(Color.parseColor("#eeeeee"));
        btn.setTextColor(Color.parseColor("#212121"));
    }
}