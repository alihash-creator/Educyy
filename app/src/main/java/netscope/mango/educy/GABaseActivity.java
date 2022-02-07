package netscope.mango.educy;
import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Sannan on 9/7/2018.
 */

public class GABaseActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    private static final String TAG = "BaseActivity";
    protected FirebaseUser mFirebaseUser;
    protected GoogleApiClient mGoogleApiClient;
    protected FirebaseAuth mAuth;
    private ProgressDialog mProgressDialog;
    private GoogleSignInOptions mGso;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"onCreate: started");
        mAuth= FirebaseAuth.getInstance();
        if (mAuth != null){
            Log.d(TAG,"onCreate: checking");
            mFirebaseUser=mAuth.getCurrentUser();

        }
        mGso=new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient=new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,mGso)
                .build();
    }
    protected void showProgressDialog(){

        if (mProgressDialog==null){
            mProgressDialog=new ProgressDialog(this);
            mProgressDialog.setMessage(getString(R.string.loading));
            mProgressDialog.setIndeterminate(false);
        }
        mProgressDialog.show();
    }

    protected void dismissProgressDialog(){
        if (mProgressDialog!=null && mProgressDialog.isShowing()){

            mProgressDialog.dismiss();
        }


    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this,"Not connected",Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        dismissProgressDialog();
    }
}