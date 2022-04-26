package com.gutierrez.bubble_pet_xforce_2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PersonProfile extends AppCompatActivity {
    Button btn;
    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;
    TextView tempV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_profile);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        getData();


        btn = findViewById(R.id.btnRegresar);
        btn.setOnClickListener(v -> {
            Intent back = new Intent(getApplicationContext(), Home.class);
            startActivity(back);
        });

        btn = findViewById(R.id.btnGetOut);
        btn.setOnClickListener(v -> {
            mAuth.signOut();
            Intent login = new Intent(getApplicationContext(), Login.class);
            startActivity(login);
        });
    }

    private void getData (){
        String id = mAuth.getCurrentUser().getUid();
        mDatabaseReference.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    String name = snapshot.child("name").getValue().toString();
                    String lastName = snapshot.child("lastName").getValue().toString();
                    tempV = findViewById(R.id.lblNamePerson);
                    tempV.setText(name + " " + lastName);

                    String email = snapshot.child("email").getValue().toString();
                    tempV = findViewById(R.id.lblEmailPerson);
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

    private void MsgToast(String message) {
        Toast.makeText(getApplicationContext(),message, Toast.LENGTH_LONG).show();
    }
}