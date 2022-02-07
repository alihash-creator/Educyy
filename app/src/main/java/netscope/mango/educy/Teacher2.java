package netscope.mango.educy;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import netscope.mango.educy.newjobsportal.JobsAdapter;
import netscope.mango.educy.newjobsportal.MDPost;

/**
 * Created by Anonymous on 10/8/2018.
 */

public class Teacher2 extends Fragment {

    Button jobBtn;
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    ArrayList<MDPost> modleList;
    private FirebaseAuth mAuth;
    FirebaseUser user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher2, container, false);
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                new Teacher2.ReadJSON().execute("http://www.mocky.io/v2/5c1a5997320000630064b0df");
//            }
//        });

        init(view);
        return view;
    }

    private void init(View view) {
        recyclerView = view.findViewById(R.id.recycler);
        mAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference().child( "Jobs" );
        modleList = new ArrayList<>();
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
                        MDPost modle = userSnapShot.getValue(MDPost.class);
                        modleList.add(modle);
                    }

                    recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
                    JobsAdapter adapterJobs = new JobsAdapter(modleList, getContext());
                    recyclerView.setAdapter(adapterJobs);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
}