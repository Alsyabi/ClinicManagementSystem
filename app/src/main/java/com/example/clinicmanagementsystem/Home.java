package com.example.clinicmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    Button appointment, delete, update, exit, read;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        appointment= (Button) findViewById(R.id.appointment);
        delete= (Button) findViewById(R.id.delete);
        update= (Button) findViewById(R.id.update);
        read= (Button) findViewById(R.id.read);
        exit = (Button) findViewById(R.id.exit);

        firebaseAuth= FirebaseAuth.getInstance();

        appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Add.class));
                finish();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Delete.class));
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Update.class));
                finish();
            }
        });

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Home.this, Read.class));
                finish();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Toast.makeText(Home.this, "Account Logout", Toast.LENGTH_SHORT).show();
                startActivity(new Intent( Home.this, Login.class));
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        backButtonHandler();
        return;
    }
    public void backButtonHandler() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(
                Home.this);
        alertDialog.setTitle("Exit application?");
        alertDialog.setMessage("Are you sure you want to exit from application?");
        alertDialog.setIcon(R.drawable.logo);
        alertDialog.setPositiveButton("Exit",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        alertDialog.setNegativeButton("NO",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        alertDialog.show();
    }
}