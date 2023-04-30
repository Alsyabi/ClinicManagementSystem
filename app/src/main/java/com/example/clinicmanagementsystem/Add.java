package com.example.clinicmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class Add extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    Spinner spinner_issues;
    EditText p_id,contact, name, date, time , check;
    Button submit_btn, calculate_btn, clear_btn, s_date, s_time;

    private int mYear, mMonth, mDay, mHour, mMinute;
    String selectedSpecialist;
    private DatabaseReference ref;
    private FirebaseAuth firebaseAuth;
    TextView cost;
    String pe_id, pe_name, pe_contact, pe_date, pe_time, pe_check, pe_cost;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        firebaseAuth= FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        spinner_issues = (Spinner) findViewById(R.id.spinner_issue);
        String[] comps = getResources().getStringArray(R.array.typeOfIssue);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, comps);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_issues.setAdapter(arrayAdapter);
        spinner_issues.setOnItemSelectedListener(this);

        p_id= (EditText) findViewById(R.id.p_id);
        name= (EditText) findViewById(R.id.name);
        contact= (EditText) findViewById(R.id.contact);
        cost= (TextView) findViewById(R.id.cost);
        date= (EditText) findViewById(R.id.date);
        time= (EditText) findViewById(R.id.time);
        check= (EditText) findViewById(R.id.check);

        submit_btn= (Button) findViewById(R.id.submit);
        calculate_btn= (Button) findViewById(R.id.calculate);
        clear_btn= (Button) findViewById(R.id.clear);
        s_date= (Button) findViewById(R.id.s_date);
        s_time= (Button) findViewById(R.id.s_time);

        s_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(Add.this,
                        new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {

                                date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        s_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                // Launch Time Picker Dialog
                TimePickerDialog timePickerDialog = new TimePickerDialog(Add.this,
                        new TimePickerDialog.OnTimeSetListener() {

                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay,
                                                  int minute) {

                                time.setText(hourOfDay + ":" + minute);
                            }
                        }, mHour, mMinute, false);
                timePickerDialog.show();
            }
        });



        calculate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (val()){
                if (validate()) {
                    cost.setText("Fee is: 50 OMR");
                }
            }
            }
        });
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p_id.setText("");
                name.setText("");
                contact.setText("");
                date.setText("");
                time.setText("");
                cost.setText("");
                check.setText("");
            }
        });

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (val()){
                if (validate1()) {
                    ref = FirebaseDatabase.getInstance().getReference("Appointments");
                    UploadData uploadData = new UploadData(selectedSpecialist, pe_id, pe_name, pe_contact, pe_date, pe_time, pe_check);
                    ref.child(ref.push().getKey()).setValue(uploadData);
                    Toast.makeText(Add.this, "Appointment is submitted", Toast.LENGTH_SHORT).show();
                }
                }
            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedSpecialist = parent.getItemAtPosition(position).toString();
        Toast.makeText(parent.getContext(), selectedSpecialist, Toast.LENGTH_SHORT);

    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private Boolean val(){
        boolean result= false;

        if (selectedSpecialist.equals("Select Specialist")){
            Toast.makeText(this, "Select any Specialist Doctor", Toast.LENGTH_SHORT).show();
        }else{
            result=true;
        }
        return result;

    }
    private Boolean validate(){
        boolean result= false;

        pe_id = p_id.getText().toString();
        pe_name= name.getText().toString();
        pe_contact= contact.getText().toString();
        pe_check= check.getText().toString();
        pe_date= date.getText().toString();
        pe_time= time.getText().toString();

        if(pe_id.isEmpty() || pe_name.isEmpty() || pe_contact.isEmpty() || pe_date.isEmpty()
                || pe_check.isEmpty() || pe_time.isEmpty()){
            Toast.makeText(this, "Enter all details", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }

    private Boolean validate1(){
        boolean result= false;

        pe_id = p_id.getText().toString();
        pe_name= name.getText().toString();
        pe_contact= contact.getText().toString();
        pe_check= check.getText().toString();
        pe_date= date.getText().toString();
        pe_time= time.getText().toString();
        pe_cost= cost.getText().toString();

        if(pe_id.isEmpty() || pe_name.isEmpty() || pe_contact.isEmpty() || pe_date.isEmpty() ||
                pe_check.isEmpty() || pe_time.isEmpty() || pe_cost.isEmpty()){
            Toast.makeText(this, "Enter all details and calculate cost", Toast.LENGTH_SHORT).show();
        }else {
            result= true;
        }
        return result;
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(Add.this, Home.class));
        finish();
    }
}