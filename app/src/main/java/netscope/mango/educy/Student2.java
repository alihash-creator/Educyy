package netscope.mango.educy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import netscope.mango.educy.newadapters.ShowTutorAdapter;
import netscope.mango.educy.newmodels.ModelShowTutor;

public class Student2 extends Fragment {

    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference databaseReference;
    ProgressDialog PD;
    RecyclerView listView;
    List<ModelShowTutor> modleList;
    private ShowTutorAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student2,null);
        init(view);
        return view;
    }

    private void init(View view) {
        listView = view.findViewById(R.id.recycler);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child( "Tutor" );

        modleList = new ArrayList<>();

        PD = new ProgressDialog(getContext());
        PD.setMessage("Loading...");
        PD.setCancelable(true);
        PD.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onStart() {
        super.onStart();

        if (databaseReference != null){
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    modleList.clear();
                    for (DataSnapshot userSnapShot : dataSnapshot.getChildren()){
                        ModelShowTutor modle = userSnapShot.getValue(ModelShowTutor.class);
                        String uid = userSnapShot.getKey();
                        modle.setUid(uid);
                        modleList.add(modle);
                    }
                    listView.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new ShowTutorAdapter(getContext(), modleList);
                    listView.setAdapter(adapter);

                    adapter.setOnClickListener(new ShowTutorAdapter.OnClickListner() {
                        @Override
                        public void onClick(int position, ModelShowTutor modelShowTutor, int type) {
                            if (type == 1){
                                Toast.makeText(getContext(), "call", Toast.LENGTH_SHORT).show();
                                String x = "tel:" + modleList.get(position).getNumber();
                                Intent intent = new Intent(Intent.ACTION_DIAL);
                                intent.setData(Uri.parse(x));
                                startActivity(intent);
                            }
                            if (type == 2){
                                Toast.makeText(getContext(), "message", Toast.LENGTH_SHORT).show();
                                String x = "sms:" + modleList.get(position).getNumber();
                                Intent smsIntent = new Intent(Intent.ACTION_SENDTO);
                                smsIntent.addCategory(Intent.CATEGORY_DEFAULT);
                                smsIntent.setType("vnd.android-dir/mms-sms");
                                smsIntent.setData(Uri.parse(x));
                                startActivity(smsIntent);
                            }
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }


}
