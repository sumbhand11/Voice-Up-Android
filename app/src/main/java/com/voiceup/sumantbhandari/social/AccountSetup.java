package com.voiceup.sumantbhandari.social;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.squareup.picasso.Picasso;
import com.voiceup.sumantbhandari.social.R;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Random;

public class AccountSetup extends AppCompatActivity {


    private ImageButton setupImageButton;
    private EditText setupUserName;
    private Button setupBtn;

    private static final int GALLERY_REQUEST =1;

    private Uri PostimageUri = null;
    private DatabaseReference mdatabaseUser;
    private FirebaseAuth mAuth;
    private StorageReference mStrorageImage;
    private ProgressDialog mprogress;

    private String passedUserName;

    //private  Uri passedPhotoUrl = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.accountsetup);

        passedUserName = getIntent().getExtras().getString(SignInActivity.user_name);
        //passedPhotoUrl = Uri.parse(getIntent().getExtras().getString(SignInActivity.photoUrl));

        setupImageButton = (ImageButton) findViewById(R.id.setupImageButton);
        setupUserName =(EditText) findViewById(R.id.setupUserName);
        setupUserName.setText(passedUserName);

        setupBtn =(Button) findViewById(R.id.setupSubmitBtn);

        mdatabaseUser= FirebaseDatabase.getInstance().getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();
        mStrorageImage = FirebaseStorage.getInstance().getReference().child("Profile_Images");
        mprogress = new ProgressDialog(this);



        setupImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent();
                gallery.setAction(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, GALLERY_REQUEST);
            }
        });

        setupBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startsetupAccount();
            }


        });

    }
    public String random() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        //int randomLength = generator.nextInt(30);
        int length = 20;
        char tempChar;
        for (int i = 0; i < length; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }

    private  void startsetupAccount(){

        final String name = setupUserName.getText().toString().trim();
        final String user_id = mAuth.getCurrentUser().getUid();
        if( !TextUtils.isEmpty(name) && PostimageUri != null ){

            mprogress.setMessage("Finishing Setup");
            mprogress.show();

            StorageReference filepath =  mStrorageImage.child(random());
            filepath.putFile(PostimageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                    String downloadUri = taskSnapshot.getDownloadUrl().toString();
                    mdatabaseUser.child(user_id).child("name").setValue(name);
                    mdatabaseUser.child(user_id).child("image").setValue(downloadUri);

                    mprogress.dismiss();
                    startActivity(new Intent(AccountSetup.this, Activity1.class));


                }
            });





        }
        else
        {
            mdatabaseUser.child(user_id).child("name").setValue("Anonymous");
            mdatabaseUser.child(user_id).child("image").setValue("No Url");

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri imageUri = data.getData();

       /* if(passedPhotoUrl != null){

            Picasso.with(getApplicationContext()).load(passedPhotoUrl).centerInside().into(setupImageButton);
        }*/

        if(requestCode ==GALLERY_REQUEST && resultCode ==RESULT_OK){
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setMinCropResultSize(600,600)
                    .start(this);
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                PostimageUri = result.getUri();
                setupImageButton.setImageURI(PostimageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
