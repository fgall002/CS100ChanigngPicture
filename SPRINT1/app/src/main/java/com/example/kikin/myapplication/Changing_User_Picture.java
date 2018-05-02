package com.example.kikin.myapplication;

import android.content.Intent;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class Changing_User_Picture extends AppCompatActivity implements View.OnClickListener {
    Uri getImageUrl;
    private static final String TAG = "Updated";
    private static final int RESULT_LOAD_IMAGE = 1;
    ImageView imageToUpload;
    Button bUploadImage,bConfirmImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_changing__user__picture);

        imageToUpload = (ImageView) findViewById(R.id.imageToUpload);

        bUploadImage = (Button) findViewById(R.id.bUploadImage);
        bConfirmImage= (Button) findViewById(R.id.bConfirmImage);



        imageToUpload.setOnClickListener(this);
        bUploadImage.setOnClickListener(this);
    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.imageToUpload:
                break;
            case R.id.bUploadImage:
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(galleryIntent,RESULT_LOAD_IMAGE);
                break;
            case R.id.bConfirmImage:
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                if(user != null) {
                    UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                            .setPhotoUri(getImageUrl)
                            .build();
                    user.updateProfile(profileUpdates)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Log.d(TAG, "User profile update.");
                                    }
                                }
                            });

                }
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && data !=null){
            Uri selectedImage= data.getData();
            getImageUrl = data.getData();
            imageToUpload.setImageURI(selectedImage);


        }
    }
}
