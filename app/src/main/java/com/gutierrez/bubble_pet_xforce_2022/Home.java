package com.gutierrez.bubble_pet_xforce_2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class Home extends AppCompatActivity {

    FirebaseAuth mAuth;
    DatabaseReference mDatabaseReference;
    String nameMascota, edadMascota, colorMascota, razaMascota, saludMascota, celPerson, namePerson;

    Button btn;
    TextView tempV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        //gettting data
        getDataPet();
        getDataUser();


        tempV = findViewById(R.id.btnProfilePerson);
        tempV.setOnClickListener(v -> {
            Intent person = new Intent(getApplicationContext(), PersonProfile.class);
            startActivity(person);
        });


            /* btn = findViewById(R.id.btnProfilePerson);
        btn.setOnClickListener(v -> {
           Intent person = new Intent(getApplicationContext(), PersonProfile.class);
           startActivity(person);
        });*/
    }


    //Cuadro de texto para salir de la aplicacion
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==event.KEYCODE_BACK){
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Desea salir de BubblePet?")
                    .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_HOME);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            builder.show();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void getDataPet(){
        String id = mAuth.getCurrentUser().getUid();

        mDatabaseReference.child("dataPet").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String namePet = snapshot.child("namePet").getValue().toString();
                    tempV = findViewById(R.id.homeNamePet);
                    tempV.setText(namePet);
                    tempV = findViewById(R.id.petNameT);
                    tempV.setText(namePet);

                    String agePet = snapshot.child("agePet").getValue().toString();
                    tempV = findViewById(R.id.homeEdadPet);
                    tempV.setText(agePet);

                    String colorPet = snapshot.child("colorPet").getValue().toString();
                    tempV = findViewById(R.id.homeColorPet);
                    tempV.setText(colorPet);

                    String razaPet = snapshot.child("razaPet").getValue().toString();
                    tempV = findViewById(R.id.homeRazaPet);
                    tempV.setText(razaPet);

                    String saludPet = snapshot.child("saludPet").getValue().toString();
                    tempV = findViewById(R.id.homeSaludPet);
                    tempV.setText(saludPet);

                    String uriPhoto = Objects.requireNonNull(snapshot.child("urlPhotoPet").getValue()).toString();

                    ImageView imgPet = findViewById(R.id.imagePet);
                    Glide.with(getApplicationContext()).load(uriPhoto).into(imgPet);

                   /* String celPerson = snapshot.child("celPerson").getValue().toString();
                    tempV = findViewById(R.id.txtCel0);
                    tempV.setText(celPerson);*/

                }else{
                    MsgToast("No References");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getDataUser (){
        String id = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mDatabaseReference.child("Users").child(id).addValueEventListener(new ValueEventListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    String namePersono = Objects.requireNonNull(snapshot.child("name").getValue()).toString();
                    String lastName = Objects.requireNonNull(snapshot.child("lastName").getValue()).toString();
                    tempV = findViewById(R.id.userName);
                    tempV.setText(namePersono + " " + lastName );

                    String email = Objects.requireNonNull(snapshot.child("email").getValue()).toString();
                    tempV = findViewById(R.id.userMail);
                    tempV.setText(email);

                    String uriPhoto = Objects.requireNonNull(snapshot.child("urlPhoto").getValue()).toString();

                    ImageView imgProfile = findViewById(R.id.imgPhoto);
                    Glide.with(getApplicationContext()).load(uriPhoto).into(imgProfile);
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