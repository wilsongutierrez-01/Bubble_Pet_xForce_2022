package com.gutierrez.bubble_pet_xforce_2022;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Data_Pet extends AppCompatActivity {
Button btn;
FirebaseAuth mAuth;
DatabaseReference mDatabaseReference;
String nameMascota, edadMascota, colorMascota, razaMascota, saludMascota, urlPhoto;
TextView tempV;
//Photo
Intent tomarFotoIntent;
ImageView imgFotoP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_pet);

        //Instancia de FireBase
        mAuth = FirebaseAuth.getInstance();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();


       /* btn = findViewById(R.id.btnVolverDogPerfil);
        btn.setOnClickListener(v->{
            Intent back = new Intent(getApplicationContext(), PeopleRegister.class);
            startActivity(back);
        });*/

        imgFotoP = findViewById(R.id.imgPhotoPet);
        imgFotoP.setOnClickListener(view ->{
            tomarFoto();
        });

        tempV = findViewById(R.id.btnVolverDogPerfil);
        tempV.setOnClickListener(v->{
            Intent back = new Intent(getApplicationContext(), PeopleRegister.class);
            startActivity(back);
        });
       /* btn = findViewById(R.id.btnFinalizarDogPerfil);
        btn.setOnClickListener(v->{
            getData();

            sendData();



        });*/

        tempV = findViewById(R.id.MakeProfile);
        tempV.setOnClickListener(v->{
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

       /* tempV = findViewById(R.id.txtCel);
        celPerson = tempV.getText().toString();*/
    }

    private void sendData (){
        String id = mAuth.getCurrentUser().getUid();
        Map<String, Object> dataPet = new HashMap<>();
        dataPet.put("namePet", nameMascota);
        dataPet.put("agePet", edadMascota);
        dataPet.put("colorPet", colorMascota);
        dataPet.put("razaPet", razaMascota);
        dataPet.put("saludPet", saludMascota);
        dataPet.put("urlPhotoPet", urlPhoto);

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


    ///For taking a Pic
    private File crearImagen() throws Exception{
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nombreimagen = "imagen_" + timeStamp + "_";
        File dirAlmacenamiento = getExternalFilesDir(Environment.DIRECTORY_DCIM);
        if ( dirAlmacenamiento.exists()==false){
            dirAlmacenamiento.mkdirs();
        }
        File image = File.createTempFile(nombreimagen,".jpg",dirAlmacenamiento);
        urlPhoto = image.getAbsolutePath();
        return image;
    }

    private void tomarFoto(){
        tomarFotoIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (tomarFotoIntent.resolveActivity(getPackageManager()) != null){
            File photoP = null;
            try {
                photoP = crearImagen();
            }catch (Exception e){
                MsgToast(e.getMessage());
            }

            if ( photoP!=null){
                try {
                    // Uri uriPhotoP = FileProvider.getUriForFile(PeopleRegister.this, "com.gutierrez.bubble_pet_xforce_2022.fileprovider", photoP);
                    Uri uriPhotoPer = FileProvider.getUriForFile(Data_Pet.this, "com.gutierrez.bubble_pet_xforce_2022.fileprovider", photoP);
                    tomarFotoIntent.putExtra(MediaStore.EXTRA_OUTPUT, uriPhotoPer);
                    startActivityForResult(tomarFotoIntent, 1);

                }catch ( Exception e){
                    MsgToast(e.getMessage());
                }

            }else {
                MsgToast("No fue posible tomar la foto");

            }
        }
    }
    protected void onActivityResult (int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode==1 && resultCode==RESULT_OK){
                Bitmap imagenBitmap = BitmapFactory.decodeFile(urlPhoto);
                imgFotoP.setImageBitmap(imagenBitmap);
            }

        }catch (Exception e){
            MsgToast(e.getMessage() + "Aca hay error xd xd xd");
        }
    }
}