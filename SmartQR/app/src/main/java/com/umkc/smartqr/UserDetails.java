package com.umkc.smartqr;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class UserDetails extends AppCompatActivity {
    public DatabaseReference userd;
    public TextView txtName;
    public TextView txtPhone;
    public TextView txtEmail;
    public TextView txtAddress;
    private ImageView qrView;
    String email="";
    String id="";
    List<UserDetailsModel> contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);
        email=getIntent().getStringExtra("email");
        id=getIntent().getStringExtra("id");

        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtAddress = findViewById(R.id.txtAddress);
        qrView = findViewById(R.id.qrView);
        userd = FirebaseDatabase.getInstance().getReference(id);
        Query query = FirebaseDatabase.getInstance().getReference(id);
        query.addListenerForSingleValueEvent(valueEventListener);
    }

    ValueEventListener valueEventListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                contacts = (ArrayList<UserDetailsModel>)dataSnapshot.child("contacts").getValue();
                UserDetailsModel userDetailsModel = dataSnapshot.child("userdetails").getValue(UserDetailsModel.class);
                if(userDetailsModel != null){
                    txtName.setText(userDetailsModel.name);
                    txtPhone.setText(userDetailsModel.phone);
                    txtEmail.setText(userDetailsModel.email);
                    txtAddress.setText(userDetailsModel.address);
                }
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };

    public void save(View view) {

        String name = txtName.getText().toString();
        String phone = txtPhone.getText().toString();
        String email = txtEmail.getText().toString();
        String address = txtAddress.getText().toString();

        if (!name.isEmpty() && !phone.isEmpty()) {
            UserDetailsModel userDetailsModel = new UserDetailsModel(id, name, phone,email,address);
            userd.child("userdetails").setValue(userDetailsModel);
            Toast.makeText(this, "Saved Successfully.", Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(this, "Please fill all the details and save.", Toast.LENGTH_LONG).show();
        }
    }

    public void home(View view) {
        Intent redirect = new Intent(UserDetails.this,HomeActivity.class).putExtra("email",email).putExtra("id",id);
        startActivity(redirect);
    }

    public void generateQR(View view) {
        String name = txtName.getText().toString();
        String phone = txtPhone.getText().toString();
        String email = txtEmail.getText().toString();
        String address = txtAddress.getText().toString();
        if (!name.isEmpty() && !phone.isEmpty()&& !email.isEmpty()&& !address.isEmpty()) {
            String value = name+"\n"+phone+"\n"+email+"\n"+address;
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try {
                BitMatrix bitMatrix = multiFormatWriter.encode(value, BarcodeFormat.QR_CODE,300,300);
                BarcodeEncoder barcodeEncoder =  new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                qrView.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }
        }else{
            Toast.makeText(this, "Please fill all the details and save.", Toast.LENGTH_LONG).show();
        }
    }
}
