package com.umkc.smartqr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {
    String email = "";
    String id = "";
    List<String> contacts;
    ContactListAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        email = getIntent().getStringExtra("email");
        id = getIntent().getStringExtra("id");
        Query query = FirebaseDatabase.getInstance().getReference(id);
        query.addListenerForSingleValueEvent(valueEventListener);
        listView = findViewById(R.id.contactList);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                contacts = (ArrayList<String>) dataSnapshot.child("contacts").getValue();
                if (contacts != null) {
                    adapter = new ContactListAdapter(HomeActivity.this, contacts);
                    listView.setAdapter(adapter);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public void scanQR(View view) {
        Intent redirect = new Intent(HomeActivity.this, ScanQRActivity.class).putExtra("email", email).putExtra("id", id);
        startActivity(redirect);
    }

    public void scanCard(View view) {
        Intent redirect = new Intent(HomeActivity.this, ScanCardActivity.class).putExtra("email", email).putExtra("id", id);
        startActivity(redirect);
    }

    public void generateQR(View view) {
        Intent redirect = new Intent(HomeActivity.this, GenerateQRActivity.class).putExtra("email", email).putExtra("id", id);
        startActivity(redirect);
    }

    public void userDetails(View view) {
        Intent redirect = new Intent(HomeActivity.this, UserDetails.class).putExtra("email", email).putExtra("id", id);
        startActivity(redirect);
    }

    public void logout(View view) {
        Intent redirect = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(redirect);
    }
}
