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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Data_Pet extends AppCompatActivity {
Button btn;
FirebaseAuth mAuth;
DatabaseReference mDatabaseReference;
String nameMascota, edadMascota, colorMascota, razaMascota, saludMascota, celPerson;
TextView tempV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pet);

        //Instancia de FireBase
        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();


        btn = findViewById(R.id.btnVolverDogPerfil);
        btn.setOnClickListener(v->{
            Intent back = new Intent(getApplicationContext(), PeopleRegister.class);
            startActivity(back);
        });

        btn = findViewById(R.id.btnFinalizarDogPerfil);
        btn.setOnClickListener(v->{
            getData();

            sendData();



        });
    }

    private void getData(){
        tempV = findViewById(R.id.txtNamePet);
        nameMascota = tempV.getText().toString();

        tempV = findViewById(R.id.txtAge);
        edadMascota = tempV.getText().toString();

        tempV = findViewById(R.id.txtColor);
        colorMascota = tempV.getText().toString();

        tempV = findViewById(R.id.txtRaza);
        razaMascota = tempV.getText().toString();

        tempV = findViewById(R.id.txtSalud);
        saludMascota = tempV.getText().toString();

        tempV = findViewById(R.id.txtCel);
        celPerson = tempV.getText().toString();
    }

    private void sendData (){
        String id = mAuth.getCurrentUser().getUid();
        Map<String, Object> dataPet = new HashMap<>();
        dataPet.put("namePet", nameMascota);
        dataPet.put("agePet", edadMascota);
        dataPet.put("colorPet", colorMascota);
        dataPet.put("razaPet", razaMascota);
        dataPet.put("saludPet", saludMascota);
        dataPet.put("celPerson", celPerson);

        mDatabaseReference.child("dataPet").child(id).setValue(dataPet).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    MsgToast("Guardado con exito");
                    Intent home = new Intent(getApplicationContext(), Home.class);
                    startActivity(home);
                }

            }

        });
    }

    private void MsgToast(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }
}