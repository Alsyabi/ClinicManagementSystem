package com.example.clinicmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Update extends AppCompatActivity {

    EditText u_id;
    EditText e_ap_id, e_ap_name, e_ap_contact, btn_date, btn_time, ap_spinner, e_ap_check;
    Button ap_search, ap_update, ap_clear;
    DatabaseReference ref, ref1;
    private FirebaseAuth firebaseAuth;
    String st_specialist, st_id, st_name, st_contact, st_date, st_time, st_before;
    String search_ID;
    String input;
    String key_get;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        u_id= (EditText) findViewById(R.id.u_id);

        e_ap_id= (EditText) findViewById(R.id.e_ap_id);
        ap_spinner= (EditText) findViewById(R.id.ap_spinner);
        e_ap_name= (EditText) findViewById(R.id.e_ap_name);
        e_ap_contact= (EditText) findViewById(R.id.e_ap_contact);
        btn_date= (EditText) findViewById(R.id.btn_date);
        btn_time= (EditText) findViewById(R.id.btn_time);
        e_ap_check= (EditText) findViewById(R.id.e_ap_check);

        ap_search= (Button) findViewById(R.id.ap_search);
        ap_update= (Button) findViewById(R.id.ap_update);
        ap_clear= (Button) findViewById(R.id.ap_clear);

        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        ref= FirebaseDatabase.getInstance().getReference("Appointments");
        ap_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                input= u_id.getText().toString();

                String fetch_id= e_ap_id.getText().toString();
                ref= FirebaseDatabase.getInstance().getReference("Appointments");
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot child: snapshot.getChildren()) { // 👈 loop over the child nodes
                            if (child.exists()){
                                search_ID = child.child("pID").getValue().toString();
                                if (search_ID.equals(input)) {
                                    key_get = child.getKey();
                                    e_ap_id.setText(key_get);
                                    ref1 = FirebaseDatabase.getInstance().getReference("Appointments").child(key_get);
                                    ref1.addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            UploadData up = snapshot.getValue(UploadData.class);
                                            ap_spinner.setText(up.getpDoc());
                                            e_ap_id.setText(up.getpID());
                                            e_ap_name.setText(up.getpName());
                                            e_ap_contact.setText(up.getpNumber());
                                            btn_date.setText(up.getpDate());
                                            btn_time.setText(up.getpTime());
                                            e_ap_check.setText(up.getpCheck());
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                }
                            } //exist end

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        throw error.toException();
                    }
                });
            }
        });

        ap_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validate()){
                    ref= FirebaseDatabase.getInstance().getReference("Appointments");
                    UploadData upload = new UploadData(st_specialist, st_id, st_name, st_contact, st_date, st_time, st_before);
                    ref.child(key_get).setValue(upload);
                    Toast.makeText(Update.this, "Appointment details Updated", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ap_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                u_id.setText("");
                e_ap_id.setText("");
                ap_spinner.setText("");
                e_ap_name.setText("");
                e_ap_contact.setText("");
                btn_date.setText("");
                btn_time.setText("");
                e_ap_check.setText("");
            }
        });
    }

    private Boolean validate(){
        boolean result= false;

        st_specialist= ap_spinner.getText().toString();
        st_id = e_ap_id.getText().toString();
        st_name= e_ap_name.getText().toString();
        st_contact=e_ap_contact.getText().toString();
        st_date= btn_date.getText().toString();
        st_time= btn_time.getText().toString();
        st_before= e_ap_check.getText().toString();

        if(st_specialist.isEmpty() || st_id.isEmpty() || st_name.isEmpty() || st_contact.isEmpty() ||
                st_date.isEmpty() || st_time.isEmpty() || st_before.isEmpty()){
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Update.this, Home.class));
        finish();
    }
}