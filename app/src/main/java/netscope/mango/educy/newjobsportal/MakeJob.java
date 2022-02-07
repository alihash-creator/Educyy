package netscope.mango.educy.newjobsportal;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import netscope.mango.educy.R;

public class MakeJob extends AppCompatActivity {

    ImageView logo;
    EditText title, description, contact;
    Button post;
    private Uri ImageUri = null;
    private static final int GALLERY_REQUEST = 1;
    private ProgressDialog mRegDialog;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private StorageReference storageImage;
    String downloadUrl, tit, des, cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_job);


        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mRegDialog = new ProgressDialog(this);

        logo = findViewById(R.id.imageView);
        title = findViewById(R.id.jobTitle);
        description = findViewById(R.id.jobDescription);
        contact = findViewById(R.id.jobContact);
        post = findViewById(R.id.post);

        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tit = title.getText().toString();
                des = description.getText().toString();
                cont = contact.getText().toString();

                if (validate()){
                    saveUserInformation();
                }
            }
        });
    }


    private boolean validate() {

        boolean valid = true;

        String titleS, descrip, cont;

        titleS = title.getText().toString();
        descrip = description.getText().toString();
        cont = contact.getText().toString();

        if(TextUtils.isEmpty(titleS)){
            title.setError("Please Enter Job Title");
            valid = false;
        }
        else{
            title.setError(null);
        }

        if(TextUtils.isEmpty(descrip)){
            description.setError("Please Enter Job Description");
            valid = false;
        }
        else{
            description.setError(null);
        }

        if(TextUtils.isEmpty(cont)){
            contact.setError("Please Enter Contact Number");
            valid = false;
        }
        else{
            contact.setError(null);
        }
        return valid;
    }

    private void saveUserInformation(){

        storageImage = FirebaseStorage.getInstance().getReference().child( "Company Images" );

        if (ImageUri != null){
            final StorageReference filepath = storageImage.child( ImageUri.getLastPathSegment() );
            filepath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl = uri.toString();

                            databaseReference.child("Jobs").child(mAuth.getUid()).child( "image" ).setValue( downloadUrl );
                        }
                    });
                }
            });
        }

        final MDPost modle = new MDPost(tit, des, cont);
        databaseReference.child("Jobs").child(mAuth.getUid()).setValue(modle);
        databaseReference.child("My Jobs").child(mAuth.getUid()).child(databaseReference.push().getKey()).setValue(modle);

        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
        title.setText("");
        description.setText("");
        contact.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK) {
            //start picker to get image for cropping and then use the image in cropping activity
            Uri imageUri = data.getData();
            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }
        //get the result of crop image
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            //if result will ok
            if (resultCode == RESULT_OK) {
                ImageUri = result.getUri();
                logo.setImageURI(ImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }
}
