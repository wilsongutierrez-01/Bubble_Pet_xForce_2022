package com.gutierrez.bubble_pet_xforce_2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PersonProfile extends AppCompatActivity {
    Button btn;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;
    TextView tempV;
    String nameMascota, edadMascota, colorMascota, razaMascota, saludMascota, celPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_profile);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        //Traemos los datos desde la base de datos
        getData();
        dataPet();

        //Cerramos la sesion
        btn = findViewById(R.id.btnGetOut);
        btn.setOnClickListener(v -> {
            mAuth.signOut();
            Intent login = new Intent(getApplicationContext(), Login.class);
            startActivity(login);
        });

        //Actualizamos los datos
        btn = findViewById(R.id.btnActualizar);
        btn.setOnClickListener(v -> {
            getFormData();
            update();
        });

    }

    //OBTENEMOS LA INFORMACION DEL USUARIO (NOMBRE, CORREO)
    private void getData (){
        String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mDatabaseReference.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    String name = Objects.requireNonNull(snapshot.child("name").getValue()).toString();
                    String lastName = Objects.requireNonNull(snapshot.child("lastName").getValue()).toString();
                    tempV = findViewById(R.id.homeNamePerson);
                    tempV.setText(name + " " + lastName);

                    String email = Objects.requireNonNull(snapshot.child("email").getValue()).toString();
                    tempV = findViewById(R.id.homeEmailPerson);
                    tempV.setText(email);
                }else{
                    MsgToast("Null Reference");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //OBTENEMOS LA INFORMACION DE LA MASCOTA
    //(NOMBRE, TELEFONO, EDAD, COLOR, RAZA, ESTADO SALUD)
    private void dataPet(){
        String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();

        mDatabaseReference.child("dataPet").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String namePet = Objects.requireNonNull(snapshot.child("namePet").getValue()).toString();
                    tempV = findViewById(R.id.homeNamePet);
                    tempV.setText(namePet);

                    String agePet = Objects.requireNonNull(snapshot.child("agePet").getValue()).toString();
                    tempV = findViewById(R.id.homeEdadPet);
                    tempV.setText(agePet);

                    String colorPet = Objects.requireNonNull(snapshot.child("colorPet").getValue()).toString();
                    tempV = findViewById(R.id.homeColorPet);
                    tempV.setText(colorPet);

                    String razaPet = Objects.requireNonNull(snapshot.child("razaPet").getValue()).toString();
                    tempV = findViewById(R.id.homeRazaPet);
                    tempV.setText(razaPet);

                    String saludPet = Objects.requireNonNull(snapshot.child("saludPet").getValue()).toString();
                    tempV = findViewById(R.id.homeSaludPet);
                    tempV.setText(saludPet);

                    String celPerson = Objects.requireNonNull(snapshot.child("celPerson").getValue()).toString();
                    tempV = findViewById(R.id.homeCelular);
                    tempV.setText(celPerson);

                }else{
                    MsgToast("No References");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //OBTENEMOS LOS DATOS ACTUALES PARA CAMBIARLOS Y GUARDARLOS
    private void update(){
        String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
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
                    MsgToast("Datos Actualizados");
                  /* Intent home = new Intent(getApplicationContext(), Home.class);
                    startActivity(home);*/
                }

            }

        });
    }

    //OBTENEMOS LOS DATOS DEL FORMULARIO
    private void getFormData(){
        tempV = findViewById(R.id.homeNamePet);
        nameMascota = tempV.getText().toString();

        tempV = findViewById(R.id.homeEdadPet);
        edadMascota = tempV.getText().toString();

        tempV = findViewById(R.id.homeColorPet);
        colorMascota = tempV.getText().toString();

        tempV = findViewById(R.id.homeRazaPet);
        razaMascota = tempV.getText().toString();

        tempV = findViewById(R.id.homeSaludPet);
        saludMascota = tempV.getText().toString();

        tempV = findViewById(R.id.homeCelular);
        celPerson = tempV.getText().toString();
    }

    //PERMITE GENERAR MENSATE TOAST
    private void MsgToast(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }

}