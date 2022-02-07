package netscope.mango.educy;
import android.app.ProgressDialog;
import android.content.Intent;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import netscope.mango.educy.newmodels.ModelSignUp;
import netscope.mango.educy.newmodels.SharedPrefrence;

public class Login extends GABaseActivity {

    private boolean Registered;


    @NonNull
    public static String type;

    ModelSignUp modelSignUp = new ModelSignUp();
    private EditText email_signin;
    private EditText password_signin;
    private TextView tv;
    private Button btn_signin;
    private RadioButton tutor, student;
    private DatabaseReference databaseReference;
    private FirebaseAuth mauth;
    private FirebaseUser user;
    private ProgressDialog mRegDialog;
    private DatabaseReference databaseReference1;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseReference1 = FirebaseDatabase.getInstance().getReference();
        mRegDialog = new ProgressDialog(this);
        mRegDialog.setMessage("Please wait...");
        mRegDialog.setCancelable(false);

//        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        Registered = preferences.getBoolean("Registered", false);
//
//
//        if (Registered) {
//
//            Intent i=new Intent(Login.this,Priority.class);
//            startActivity(i);
//            finish();
//        } else {
//
//        }


        tutor = findViewById( R.id.tutorBtn );
        student = findViewById( R.id.studentBtn );
        email_signin = (EditText) findViewById( R.id.email );
        password_signin = (EditText) findViewById( R.id.password );
        tv = (TextView) findViewById( R.id.tv );
        btn_signin = (Button) findViewById( R.id.signin );

        mauth = FirebaseAuth.getInstance();

        tv.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( Login.this, SignupActivity.class );
                startActivity( intent );
                finish();

            }
        } );

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(validate()){
                    mRegDialog.show();
                    final String email = email_signin.getText().toString();
                    final String password = password_signin.getText().toString();

                    mauth.signInWithEmailAndPassword( email, password )
                            .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    if (task.isSuccessful()) {
                                        mRegDialog.dismiss();
                                        user = FirebaseAuth.getInstance().getCurrentUser();
                                        uid = user.getUid();
                                        Toast.makeText(Login.this, ""+uid, Toast.LENGTH_SHORT).show();
                                        modelSignUp.setEmail( email );
                                        modelSignUp.setPassword( password );
                                        modelSignUp.setId(uid);
                                        if (tutor.isChecked()){
                                            modelSignUp.setType("Tutor");
                                        }else {
                                            modelSignUp.setType("Student");
                                        }

                                        databaseReference = FirebaseDatabase.getInstance().getReference().child( "User" );
                                        if (tutor.isChecked()) {
                                            databaseReference.addValueEventListener( new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    type = dataSnapshot.child(uid).child( "type" ).getValue( String.class );
                                                    if ("Tutor".equals( type )) {
                                                        Toast.makeText( Login.this, "Login Sucessful", Toast.LENGTH_LONG ).show();
                                                        Intent newintent = new Intent( Login.this, Category.class );//yahan change kiyua hai
                                                        newintent.putExtra("teacher","teacher");
                                                        SharedPrefrence.getInstance( Login.this ).setParentUser( modelSignUp );
                                                        startActivity( newintent );
                                                        finish();
                                                    } else {
                                                        Toast.makeText( Login.this, " this email is not signup as tutor", Toast.LENGTH_LONG ).show();
                                                    }
                                                }
                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            } );
                                        }
                                        else {
                                            databaseReference.addValueEventListener( new ValueEventListener() {
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    type = dataSnapshot.child(uid).child( "type" ).getValue( String.class );
                                                    if ("Student".equals( type )) {
                                                        Toast.makeText( Login.this, "Login Sucessful", Toast.LENGTH_LONG ).show();
                                                        Intent newintent = new Intent( Login.this, Category.class );//yahan change kiyua hai
                                                        newintent.putExtra("student","student");
                                                        SharedPrefrence.getInstance( Login.this ).setParentUser( modelSignUp );
                                                        startActivity( newintent );
                                                        finish();
                                                    } else {
                                                        Toast.makeText( Login.this, " this email is not signup as Student", Toast.LENGTH_LONG ).show();
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                                }
                                            } );
                                        }
                                    } else {
                                        mRegDialog.hide();
                                        Toast.makeText(Login.this, "please register first!!!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
        });

    }

    private boolean validate() {
        String email = email_signin.getText().toString();
        String password = password_signin.getText().toString();
        boolean valid = true;

        if(TextUtils.isEmpty(email)){
            email_signin.setError("Enter email first");
            valid = false;

            if (Patterns.EMAIL_ADDRESS.matcher( email ).matches()){
                email_signin.setError("Kindly enter valid email address");
                valid = false;
            }
        }

        else{
            email_signin.setError(null);
        }
        if(TextUtils.isEmpty(password)){
            password_signin.setError("Enter password");
            valid=false;

            if(password.length() <=6){
                password_signin.setError("Password min length 6");
                valid =false;
            }
        }
        else{
            password_signin.setError(null);
        }
        return valid;
    }
}
