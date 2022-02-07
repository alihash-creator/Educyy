package netscope.mango.educy;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import netscope.mango.educy.newmodels.MDNotes;

/**
 * Created by Anonymous on 10/8/2018.
 */

public class Student1 extends Fragment {

    Button books;
    RecyclerView recyclerView;
    MDNotes mdNotes = new MDNotes();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.student1, container, false);

        books=view.findViewById(R.id.Books);
        books.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getActivity(),StudentBooks.class);
                startActivity(i);
            }
        });
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerView);
        //custom adapter always
        //popolate the recycler view with items

        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Notes");

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                //actually called for indvi items at the database refrence
                String fileName = dataSnapshot.getKey();//return the file name
                String urls = dataSnapshot.getValue(String.class);//return url for 'filename'

                mdNotes.setFileName(fileName);
                mdNotes.setUrls(urls);

                ((MyAdapter)recyclerView.getAdapter()).update(fileName,urls);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        MyAdapter myAdapter = new MyAdapter(recyclerView,getActivity() ,new ArrayList<String>(),new ArrayList<String>());
        recyclerView.setAdapter(myAdapter);


        return view;
    }


}
