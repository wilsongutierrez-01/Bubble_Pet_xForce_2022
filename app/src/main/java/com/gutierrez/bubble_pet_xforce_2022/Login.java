package com.gutierrez.bubble_pet_xforce_2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Login extends AppCompatActivity {
Button btn;
FirebaseAuth mAuth;
DatabaseReference mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        buttonStyle();
        try{
            mAuth = FirebaseAuth.getInstance();
            mData = FirebaseDatabase.getInstance().getReference();
        }catch(Exception e){
            MsgToast("" + e);
        }


        btn = findViewById(R.id.btnSingUp);
        btn.setOnClickListener(v->{
            try {
                Intent re = new Intent(getApplicationContext(), PeopleRegister.class);
                startActivity(re);
            }catch(Exception e){
             MsgToast("" + e);
            }

        });

        btn = findViewById(R.id.btnSingIn);
        btn.setOnClickListener(v->{
            try {
                Intent lo = new Intent(getApplicationContext(),PeopleLogin.class);
                startActivity(lo);
            }catch(Exception e){
                MsgToast(e.getMessage());
            }

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
    private void MsgToast(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }
}