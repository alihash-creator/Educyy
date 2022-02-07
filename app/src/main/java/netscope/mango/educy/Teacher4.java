package netscope.mango.educy;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Anonymous on 11/28/2018.
 */

public class Teacher4 extends Fragment {
    Button selectfile, upload, fetchfiles;
    TextView notification;
    ProgressDialog progressDialog;
    Uri pdfUri = null;//Uri are actually URLs that are meant for local storage
    FirebaseStorage storage;//used for uploading files .. Ex//pdf
    FirebaseDatabase database;//used to store URLs of uploading files...
    String url;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher4, container, false);
        storage = FirebaseStorage.getInstance(); //return an object of firebase storage
        database = FirebaseDatabase.getInstance(); //return an object of firebase database


        selectfile = (Button) view.findViewById(R.id.selectFile);
        upload = (Button) view.findViewById(R.id.upload);
        notification = (TextView) view.findViewById(R.id.notification);

        selectfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                    selectPdf();
                }else {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},9);
                }

            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pdfUri != null) {//the user has selected the file
                    uploadFile(pdfUri);
                } else {
                    Toast.makeText(getActivity(), "please select a file..", Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        return super.shouldShowRequestPermissionRationale(permission);
    }

    private void uploadFile(Uri pdfUri) {

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setTitle("Uploading File...");
        progressDialog.setProgress(0);
        progressDialog.show();

        final String fileName = System.currentTimeMillis() + ".pdf";
        final String fileName1 = System.currentTimeMillis() + "";

        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("uploads");//return root path

        if (pdfUri != null){
            final StorageReference filepath = storageReference.child( fileName );
            filepath.putFile(pdfUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            url = uri.toString();

                            DatabaseReference reference = database.getReference();

                            reference.child("Notes").child(fileName1).setValue(url).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(getContext(), "File Uploaded", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(getContext(), "File Got Error", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getContext(), "File Got Error Failure", Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
                    int currentProgress = (int) (100*taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                    progressDialog.setProgress(currentProgress);
                }
            });
        }

    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 9 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            selectPdf();
        } else {
            Toast.makeText(getActivity(), "please provide permission..", Toast.LENGTH_SHORT).show();
        }
    }

    private void selectPdf() {
        //to offer user to select a file using file manager
        //we will be using an intent
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 86);
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        //check whether user has selected a file or not (ex:pdf)
//        if (requestCode == 86 && requestCode == RESULT_OK && data != null) {
//
//            pdfUri = data.getData();//return the uri of selected files
//            notification.setText("A file is selected : " + data.getData().getLastPathSegment());
//        } else {
//            Toast.makeText(getActivity(), "please select a file.", Toast.LENGTH_SHORT).show();
//        }
//    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 86 && resultCode == RESULT_OK && data != null) {

            pdfUri = data.getData();//return the uri of selected files
            notification.setText("A file is selected : " + data.getData().getLastPathSegment());
        } else {
            Toast.makeText(getActivity(), "please select a file.", Toast.LENGTH_SHORT).show();
        }
    }
}
