package com.umkc.smartqr;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ResultActivity extends AppCompatActivity {
    TextView txtOutput;
    String id = "";
    String email="";
    public DatabaseReference userd;
    List<String> contacts;
    String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        result = getIntent().getStringExtra("result");
        txtOutput = findViewById(R.id.txtOutput);
        txtOutput.setText(result);
        id = getIntent().getStringExtra("id");
        email=getIntent().getStringExtra("email");
        Query query = FirebaseDatabase.getInstance().getReference(id);
        query.addListenerForSingleValueEvent(valueEventListener);
        userd = FirebaseDatabase.getInstance().getReference(id);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                contacts = (ArrayList<String>) dataSnapshot.child("contacts").getValue();
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    public void Save(View view) {
        if(contacts == null){
            contacts = new ArrayList<>();
        }
        contacts.add(result);
        userd.child("contacts").setValue(contacts);
        Toast.makeText(this, "Saved Successfully.", Toast.LENGTH_LONG).show();
    }

    public void home(View view) {
        Intent redirect = new Intent(ResultActivity.this,HomeActivity.class).putExtra("email",email).putExtra("id",id);
        startActivity(redirect);
    }
}
