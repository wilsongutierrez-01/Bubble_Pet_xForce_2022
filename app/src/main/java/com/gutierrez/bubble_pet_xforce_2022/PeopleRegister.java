package com.gutierrez.bubble_pet_xforce_2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PeopleRegister extends AppCompatActivity {
Button btn;
TextView tempVal;
FirebaseAuth mAuth;
DatabaseReference mData;
String email, password, passwordConfirm, name, lastName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_people_register);

        try{
            mAuth = FirebaseAuth.getInstance();
            mData = FirebaseDatabase.getInstance().getReference();
        }catch(Exception e){
            MsgToast("" + e);
        }



        btn = findViewById(R.id.btnVolver);
        btn.setOnClickListener(v ->{
            Intent ok = new Intent(getApplicationContext(), Login.class);
            startActivity(ok);
        });

       btn = findViewById(R.id.btnRegistrar);
        btn.setOnClickListener(v -> {
            tempVal = findViewById(R.id.edtEmail);
            email = tempVal.getText().toString();

            tempVal = findViewById(R.id.edPassword);
            password = tempVal.getText().toString();

            tempVal = findViewById(R.id.edPasswordConfirm);
            passwordConfirm = tempVal.getText().toString();

            tempVal = findViewById(R.id.edtName);
            name = tempVal.getText().toString();

            tempVal = findViewById(R.id.edtLastName);
            lastName = tempVal.getText().toString();

            if(!email.isEmpty() && !password.isEmpty() && !passwordConfirm.isEmpty() && !name.isEmpty() && !lastName.isEmpty()){

                if(password.equals(passwordConfirm)){
                    try{
                        savePerson();
                    }catch(Exception r){
                        MsgToast(r.getMessage());
                    }


                }else{
                    MsgToast("Las contraseñas no coinciden");
                }



            }else{
                MsgToast("Relle todos los campos");
            }


        });


    }

    private void savePerson (){
        //Obtener los datos desde los TextView
        /*tempVal = findViewById(R.id.edtEmail);
        String email = tempVal.getText().toString();

        tempVal = findViewById(R.id.edPassword);
        String password = tempVal.getText().toString();

        tempVal = findViewById(R.id.edPasswordConfirm);
        String passwordConfirm = tempVal.getText().toString();

        tempVal = findViewById(R.id.edtName);
        String name = tempVal.getText().toString();

        tempVal = findViewById(R.id.edtLastName);
        String lastName = tempVal.getText().toString();*/

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()){

                            Map<String, Object> data = new HashMap<>();
                            data.put("email", email);
                            data.put("password", password);
                            data.put("name", name);
                            data.put("lastName", lastName);

                            String id = mAuth.getCurrentUser().getUid();
                            mData.child("Users").child(id).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task task) {
                                    finish();
                                    MsgToast("Usuario creado con exito");

                                }
                            });
                            ResgitrarMascota();

                }else{
                    MsgToast("No es posible crear usuario");
                }
            }
        });



    }

    private void ResgitrarMascota(){
        Intent mascota = new Intent(getApplicationContext(), Data_Pet.class);
        startActivity(mascota);
    }
    private void MsgToast(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }
}