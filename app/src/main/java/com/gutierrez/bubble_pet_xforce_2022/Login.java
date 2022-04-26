package com.gutierrez.bubble_pet_xforce_2022;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

        mAuth = FirebaseAuth.getInstance();
        mData = FirebaseDatabase.getInstance().getReference();


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

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent home = new Intent(getApplicationContext(), Home.class);
            startActivity(home);
        }else{
            MsgToast("Por favor inicie sesion");
        }
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