package com.gutierrez.bubble_pet_xforce_2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
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

public class PetProfile extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;
    TextView tempV;
    Button btn;
    String nameMascota, edadMascota, colorMascota, razaMascota, saludMascota, celPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);
        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        dataPet();

        btn = findViewById(R.id.btnBack0);
        btn.setOnClickListener(v -> {
            Intent back = new Intent(getApplicationContext(), Home.class);
            startActivity(back);
        });


    }
    private void dataPet(){
        String id = mAuth.getCurrentUser().getUid();

        mDatabaseReference.child("dataPet").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String namePet = snapshot.child("namePet").getValue().toString();
                    tempV = findViewById(R.id.txtNamePet0);
                    tempV.setText(namePet);

                    String agePet = snapshot.child("agePet").getValue().toString();
                    tempV = findViewById(R.id.txtAge0);
                    tempV.setText(agePet);

                    String colorPet = snapshot.child("colorPet").getValue().toString();
                    tempV = findViewById(R.id.txtColor0);
                    tempV.setText(colorPet);

                    String razaPet = snapshot.child("razaPet").getValue().toString();
                    tempV = findViewById(R.id.txtRaza0);
                    tempV.setText(razaPet);

                    String saludPet = snapshot.child("saludPet").getValue().toString();
                    tempV = findViewById(R.id.txtSalud0);
                    tempV.setText(saludPet);

                    String urlImg = snapshot.child("urlPhotoPet").getValue().toString();
                    ImageView imgPet = findViewById(R.id.imgPhotoPet1);
                    Glide.with(getApplicationContext()).load(urlImg).into(imgPet);


                }else{
                    MsgToast("No References");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void update(){
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
                    MsgToast("Datos Actualizados");
                  /* Intent home = new Intent(getApplicationContext(), Home.class);
                    startActivity(home);*/
                }

            }

        });
    }

    private void getFormData(){
        tempV = findViewById(R.id.txtNamePet0);
        nameMascota = tempV.getText().toString();

        tempV = findViewById(R.id.txtAge0);
        edadMascota = tempV.getText().toString();

        tempV = findViewById(R.id.txtColor0);
        colorMascota = tempV.getText().toString();

        tempV = findViewById(R.id.txtRaza0);
        razaMascota = tempV.getText().toString();

        tempV = findViewById(R.id.txtSalud0);
        saludMascota = tempV.getText().toString();
    }
    private void MsgToast(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }
}