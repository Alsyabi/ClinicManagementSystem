package com.example.clinicmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Read extends AppCompatActivity {

    private ListView listview2;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    ArrayList<String> list2;
    ArrayAdapter<String> adapter;
    UploadData upload;
    FirebaseAuth firebaseAuth;
    private ProgressBar mProgressCircle;
    TextView label;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        listview2= (ListView)findViewById(R.id.listview2);
        list2= new ArrayList<>();
        adapter= new ArrayAdapter<String>(this,R.layout.user_info2,R.id.infoTxt2, list2);
        upload = new UploadData();
        mProgressCircle = (ProgressBar) findViewById(R.id.progress_circle);

        firebaseAuth= FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        final FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        label= (TextView) findViewById(R.id.label);
        label.setPaintFlags(label.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        databaseReference = firebaseDatabase.getReference("Appointments");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds: snapshot.getChildren()){
                    upload = ds.getValue(UploadData.class);
                    list2.add("Appointment ID: "+upload.getpID()+ "\n" +"Specialist: "+ upload.getpDoc() +"\n"
                            + "Patient Name: "+ upload.getpName() +" \n" + "Contact Number: "+upload.getpNumber() +"\n"
                            + "Date: " +upload.getpDate()+ "\n" + "Time: " + upload.getpTime());
                }
                listview2.setAdapter(adapter);
                mProgressCircle.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Read.this, Home.class));
        finish();
    }
}