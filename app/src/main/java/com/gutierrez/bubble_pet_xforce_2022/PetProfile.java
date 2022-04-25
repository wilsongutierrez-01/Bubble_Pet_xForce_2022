package com.gutierrez.bubble_pet_xforce_2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PetProfile extends AppCompatActivity {
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;
    TextView tempV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pet_profile);
        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        dataPet();

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

                    String celPerson = snapshot.child("celPerson").getValue().toString();
                    tempV = findViewById(R.id.txtCel0);
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
    private void MsgToast(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }
}