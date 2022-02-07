package netscope.mango.educy;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import netscope.mango.educy.newmodels.ModelSignUp;

public class SignupActivity extends AppCompatActivity {

    private EditText email_signup, phone_number, name, qualification, experience, location, subject, fee, password_signup;
    private TextView tv;
    private Button btn_signup;
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private RadioButton tutor, student;
    private Uri ImageUri = null;
    private ImageView profileImage;
    private StorageReference storageImage;
    private static final int GALLERY_REQUEST = 1;

    String phone, email, downloadUrl, password, nameS, qualifi, experi, locat, subj, feeSub, token;
    //progress dialog
    private ProgressDialog mRegDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);


        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        mRegDialog = new ProgressDialog(this);
        mRegDialog.setMessage("Please wait...");
        mRegDialog.setCancelable(false);

        profileImage = findViewById(R.id.profile_image);
        email_signup = findViewById(R.id.email);
        phone_number = findViewById(R.id.phone_num);
        password_signup = findViewById(R.id.passwordd);
        tutor = findViewById( R.id.tutorBtn );
        name = findViewById(R.id.name);
        qualification = findViewById(R.id.qualification);
        location = findViewById(R.id.location);
        fee = findViewById(R.id.fee);
        experience = findViewById(R.id.experience);
        subject = findViewById(R.id.subject);
        student = findViewById( R.id.studentBtn );
        btn_signup = findViewById(R.id.signup);
        tv = findViewById(R.id.tv);

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GALLERY_REQUEST);
            }
        });

        tutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setVisibility(View.VISIBLE);
                phone_number.setVisibility(View.VISIBLE);
                qualification.setVisibility(View.VISIBLE);
                experience.setVisibility(View.VISIBLE);
                location.setVisibility(View.VISIBLE);
                subject.setVisibility(View.VISIBLE);
                fee.setVisibility(View.VISIBLE);
            }
        });

        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name.setVisibility(View.GONE);
                phone_number.setVisibility(View.GONE);
                qualification.setVisibility(View.GONE);
                experience.setVisibility(View.GONE);
                location.setVisibility(View.GONE);
                subject.setVisibility(View.GONE);
                fee.setVisibility(View.GONE);
            }
        });

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignupActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nameS = name.getText().toString();
                email = email_signup.getText().toString();
                phone = phone_number.getText().toString();
                password = password_signup.getText().toString();
                qualifi = qualification.getText().toString();
                locat = location.getText().toString();
                experi = experience.getText().toString();
                subj = subject.getText().toString();
                feeSub = fee.getText().toString();
//                token =

                if(validate()){

                    Toast.makeText(SignupActivity.this, "ok", Toast.LENGTH_SHORT).show();
                    mRegDialog.show();

                    if (tutor.isChecked()){
                        register_tutor( email, password);
                    }
                    if (student.isChecked()){
                        register_student(email, password);
                    }

                }
                else
                    Toast.makeText(SignupActivity.this, "not ok", Toast.LENGTH_SHORT).show();
            }
        });
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
                profileImage.setImageURI(ImageUri);
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }
        }
    }

    private boolean validate() {

        boolean valid = true;

        String email, password, nameS, qualifi, experi, locat, subj, feeSub;
        nameS = name.getText().toString();
        email = email_signup.getText().toString();
        password = password_signup.getText().toString();
        qualifi = qualification.getText().toString();
        locat = location.getText().toString();
        experi = experience.getText().toString();
        subj = subject.getText().toString();
        feeSub = fee.getText().toString();

        if (tutor.isChecked()){
            if(TextUtils.isEmpty(email)){
                email_signup.setError("Enter email first");
                valid = false;
                if (Patterns.EMAIL_ADDRESS.matcher( email ).matches()){
                    email_signup.setError("Kindly entyer valid email address");
                    valid = false;
                }
            }
            else{
                email_signup.setError(null);
            }

            if(TextUtils.isEmpty(nameS)) {
                name.setError("Enter the Field");
                valid = false;
            }else{
                name.setError(null);
            }

            if(TextUtils.isEmpty(qualifi)) {
                qualification.setError("Enter the Field");
                valid = false;
            }else{
                qualification.setError(null);
            }

            if(TextUtils.isEmpty(experi)) {
                experience.setError("Enter the Field");
                valid = false;
            }else{
                experience.setError(null);
            }

            if(TextUtils.isEmpty(locat)) {
                location.setError("Enter the Field");
                valid = false;
            }else{
                location.setError(null);
            }

            if(TextUtils.isEmpty(subj)) {
                subject.setError("Enter the Field");
                valid = false;
            }else{
                subject.setError(null);
            }

            if(TextUtils.isEmpty(feeSub)) {
                fee.setError("Enter the Field");
                valid = false;
            }else{
                fee.setError(null);
            }

            if(TextUtils.isEmpty(password)){
                password_signup.setError("Enter password first");
                valid=false;

                if(password.length() <=6){
                    password_signup.setError("Kindly choose password with characters more than 6");
                    valid =false;
                }
            }
            else{
                password_signup.setError(null);
            }
        }

        if (student.isChecked()){
            if(TextUtils.isEmpty(email)){
                email_signup.setError("Enter email first");
                valid = false;
                if (Patterns.EMAIL_ADDRESS.matcher( email ).matches()){
                    email_signup.setError("Kindly entyer valid email address");
                    valid = false;
                }
            }
            else{
                email_signup.setError(null);
            }

            if(TextUtils.isEmpty(password)){
                password_signup.setError("Enter password first");
                valid=false;

                if(password.length() <=6){
                    password_signup.setError("Kindly choose password with characters more than 6");
                    valid =false;
                }
            }
            else{
                password_signup.setError(null);
            }
        }

        return valid;
    }


    private void register_tutor(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //Dissmiss the progress Dialog
                            mRegDialog.dismiss();
                            FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                Toast.makeText(SignupActivity.this, "Email is sent to!" + FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                                        Toast.LENGTH_LONG).show();

                                                //user is registered
                                                saveUserInformationTutor();

                                                Intent mainIntent = new Intent(SignupActivity.this, Login.class);
                                                startActivity(mainIntent);
                                                finish();
                                            } else {
                                                Toast.makeText(SignupActivity.this, "Failed To Send Email!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        } else {
                            mRegDialog.hide();

                            //if user is not registered

                            Toast.makeText(SignupActivity.this, "Can't signup...Please enter password with more than 5 characters and try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void register_student(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //Dissmiss the progress Dialog
                            mRegDialog.dismiss();
                            FirebaseAuth.getInstance().getCurrentUser().sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                Toast.makeText(SignupActivity.this, "Email is sent to!" + FirebaseAuth.getInstance().getCurrentUser().getEmail(),
                                                        Toast.LENGTH_LONG).show();

                                                //user is registered
                                                saveUserInformationStudent();

                                                Intent mainIntent = new Intent(SignupActivity.this, Login.class);
                                                startActivity(mainIntent);
                                                finish();
                                            } else {
                                                Toast.makeText(SignupActivity.this, "Failed To Send Email!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });
                        } else {
                            mRegDialog.hide();

                            //if user is not registered

                            Toast.makeText(SignupActivity.this, "Can't signup...Please enter password with more than 5 characters and try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }


    private void  saveUserInformationTutor(){

        storageImage = FirebaseStorage.getInstance().getReference().child( "Profile Images(Tutor)" );

        if (ImageUri != null){
            final StorageReference filepath = storageImage.child( ImageUri.getLastPathSegment() );
            filepath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl = uri.toString();

                            databaseReference.child("Tutor").child(mAuth.getUid()).child( "image" ).setValue( downloadUrl );
                            databaseReference.child("User").child(mAuth.getUid()).child( "image" ).setValue( downloadUrl );
                        }
                    });
                }
            });
        }

        final ModelSignUp modle = new ModelSignUp(nameS, phone,downloadUrl,email, password, qualifi, experi, locat, subj, feeSub, "Tutor");
        databaseReference.child("Tutor").child(mAuth.getUid()).setValue(modle);
        databaseReference.child("User").child(mAuth.getUid()).setValue(modle);

        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }

    private void saveUserInformationStudent(){

        storageImage = FirebaseStorage.getInstance().getReference().child( "Profile Images(Student)" );

        if (ImageUri != null){
            final StorageReference filepath = storageImage.child( ImageUri.getLastPathSegment() );
            filepath.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener( new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            downloadUrl = uri.toString();

                            databaseReference.child("Student").child(mAuth.getUid()).child( "image" ).setValue( downloadUrl );
                            databaseReference.child("User").child(mAuth.getUid()).child( "image" ).setValue( downloadUrl );
                        }
                    });
                }
            });
        }

        final ModelSignUp modle = new ModelSignUp(downloadUrl, email, password, "Student");

        databaseReference.child("Student").child(mAuth.getUid()).setValue(modle);
        databaseReference.child("User").child(mAuth.getUid()).setValue(modle);

        Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,Login.class));
        finish();
    }
}
