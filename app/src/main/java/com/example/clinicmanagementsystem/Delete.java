package com.example.clinicmanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Delete extends AppCompatActivity {

    EditText u_id;
    TextView e_ap_id, e_ap_name, e_ap_contact, btn_date, btn_time, ap_spinner, e_ap_check;
    Button ap_search, ap_delete, ap_clear;
    DatabaseReference ref, ref1;
    private FirebaseAuth firebaseAuth;
    String search_ID;
    String input;
    String key_get;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        u_id= (EditText) findViewById(R.id.sd_ap_id);

        e_ap_id= (TextView) findViewById(R.id.d_ap_id);
        ap_spinner= (TextView) findViewById(R.id.d_ap_spinner);
        e_ap_name= (TextView) findViewById(R.id.d_ap_name);
        e_ap_contact= (TextView) findViewById(R.id.d_ap_contact);
        btn_date= (TextView) findViewById(R.id.d_date);
        btn_time= (TextView) findViewById(R.id.d_time);
        e_ap_check= (TextView) findViewById(R.id.d_check);

        ap_search= (Button) findViewById(R.id.d_ap_search);
        ap_delete= (Button) findViewById(R.id.ap_delete);
        ap_clear= (Button) findViewById(R.id.d_ap_clear);

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
                        for (DataSnapshot child: snapshot.getChildren()) {
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

        ap_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    ref= FirebaseDatabase.getInstance().getReference("Appointments").child(key_get);
                    ref.removeValue();
                    Toast.makeText(Delete.this, "Appointment Successfully Deleted", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Delete.this, Home.class));
                finish();
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

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Delete.this, Home.class));
        finish();
    }
}