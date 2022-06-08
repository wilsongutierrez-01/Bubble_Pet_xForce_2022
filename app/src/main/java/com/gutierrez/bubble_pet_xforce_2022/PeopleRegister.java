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
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONObject;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PeopleRegister extends AppCompatActivity {
Button btn;
//ImageButton btnImg;
TextView tempVal;
//Photo
Intent tomarFotoIntent;

//
ImageView imgFotoP;
FirebaseAuth mAuth;
DatabaseReference mData;
String email, password, passwordConfirm, name, lastName, urlPhoto;

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

        imgFotoP = findViewById(R.id.imgPhotoPerson);
        imgFotoP.setOnClickListener(view ->{
            tomarFoto();
        });

        btn = findViewById(R.id.btnVolver);
        btn.setOnClickListener(v ->{
            Intent ok = new Intent(getApplicationContext(), Login.class);
            startActivity(ok);
        });

        btn = findViewById(R.id.btnRegistrar);
        btn.setOnClickListener(v -> {


            formData();

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

    private void formData(){
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
                            data.put("urlPhoto", urlPhoto);

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
                    Uri uriPhotoPer = FileProvider.getUriForFile(PeopleRegister.this, "com.gutierrez.bubble_pet_xforce_2022.fileprovider", photoP);
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