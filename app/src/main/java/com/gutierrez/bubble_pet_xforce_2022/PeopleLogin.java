package com.gutierrez.bubble_pet_xforce_2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class PeopleLogin extends AppCompatActivity {
Button btn;
FirebaseAuth mAuth;
TextView tempV, btnCreatCount;
String email, password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_login);

        mAuth = FirebaseAuth.getInstance();

        btn = findViewById(R.id.btnLoginUser);
        btn.setOnClickListener(v -> {
            tempV = findViewById(R.id.txtEmailLog);
            email = tempV.getText().toString();

            tempV = findViewById(R.id.txtPasswordLog);
            password = tempV.getText().toString();

            if(!email.isEmpty() && !password.isEmpty()){
                login();
            }else{
                if(email.isEmpty() && !password.isEmpty()){
                    MsgToast("Ingrese un correo electronico");
                }else if (!email.isEmpty() && password.isEmpty()){
                    MsgToast("Ingrese la contraseña");
                }else {
                    MsgToast("Rellene los campos");
                }
            }

        });

        btnCreatCount = findViewById(R.id.btnSingupLogin);
        btnCreatCount.setOnClickListener(v->{

            Intent re = new Intent(getApplicationContext(), PeopleRegister.class);
            startActivity(re);


        });
    }

    private void login (){
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    Intent home = new Intent(getApplicationContext(), Home.class);
                    startActivity(home);

                }else{
                    MsgToast("Ingrese los datos correctos");
                }

            }
        });
    }

    private void MsgToast(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }
}